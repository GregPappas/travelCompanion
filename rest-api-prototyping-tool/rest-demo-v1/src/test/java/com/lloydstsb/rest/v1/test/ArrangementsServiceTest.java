package com.lloydstsb.rest.v1.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Response;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.lloydstsb.rest.v1.data.ArrangementServiceDataIpsum;
import com.lloydstsb.rest.v1.data.ArrangementStateType;
import com.lloydstsb.rest.v1.data.ArrangementWrapper;
import com.lloydstsb.rest.v1.data.BeneficiaryDataIpsum;
import com.lloydstsb.rest.v1.data.CustomersDataIpsum;
import com.lloydstsb.rest.v1.data.ErrorHelper;
import com.lloydstsb.rest.v1.data.PersistenceContainer;
import com.lloydstsb.rest.v1.data.PhoneNumbersDataIpsum;
import com.lloydstsb.rest.v1.data.TransactionsDataIpsum;
import com.lloydstsb.rest.v1.demo.ArrangementsServiceImpl;
import com.lloydstsb.rest.v1.helpers.ArrangementHelper;
import com.lloydstsb.rest.v1.helpers.ObjectGenerator;
import com.lloydstsb.rest.v1.helpers.SessionHelper;
import com.lloydstsb.rest.v1.valueobjects.Arrangements;
import com.lloydstsb.rest.v1.valueobjects.Beneficiaries;
import com.lloydstsb.rest.v1.valueobjects.Beneficiary;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.Error;
import com.lloydstsb.rest.v1.valueobjects.Page;
import com.lloydstsb.rest.v1.valueobjects.Statement;
import com.lloydstsb.rest.v1.valueobjects.Transaction;
import com.lloydstsb.rest.v1.valueobjects.TransactionType;
import com.lloydstsb.rest.v1.valueobjects.arrangement.Arrangement;
import com.lloydstsb.rest.v1.valueobjects.arrangement.CurrentAccount;


public class ArrangementsServiceTest {

    private ObjectGenerator objectGenerator = new ObjectGenerator();
    private ErrorHelper errorHelper = new ErrorHelper();
    ArrangementHelper arrangementHelper = new ArrangementHelper();
    PhoneNumbersDataIpsum phoneNumbers = new PhoneNumbersDataIpsum();
    CustomersDataIpsum customers = new CustomersDataIpsum();
    ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
    TransactionsDataIpsum transactions = new TransactionsDataIpsum();
    BeneficiaryDataIpsum beneficiaries = new BeneficiaryDataIpsum();
    private String userId = "69000";
    private String arrangementId = "20756882123456";
    private String targetArrangementId = "28682942123456";
    private String currentDate;
    private String futureDate;
    private String invalidPreviousDate;
    private String validPreviousDate;

    @Before
    public void initialise() throws IOException {
        phoneNumbers.instantiatePhoneNumbers();
        customers.instantiateCustomers();
        arrangementData.instantiateExchangeLockCustomersArrangements();
        transactions.readAndParseCSVFile();
        beneficiaries.generateHashMapOfBeneficiaries();
        currentDate = generateStringDate(0);
        validPreviousDate = generateStringDate(-1);
        invalidPreviousDate = generateStringDate(-12);
        futureDate = generateStringDate(12);
    }

    public String generateStringDate(int addMonth)
    {
        Calendar calendar = new GregorianCalendar();
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        calendar.add(Calendar.MONTH,addMonth);
        Date date = calendar.getTime();
        return sdf.format(date);
    }

    @After
    public void deconstruct() {
        arrangementData.clearArrangementWrapperMap();
        transactions.getTransactionMap().clear();
        beneficiaries.getBeneficiaryMap().clear();
        customers.getCustomers().clear();
        phoneNumbers.getPhoneNumbersMap().clear();
    }

    @Test
    public void getArrangementsShouldReturnResponse200AndPageOfArrangementObjectsWhenRequestSuccessful() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        //Act
        Response returnedResponse = arrangement.getArrangements(request, response, 25, 0);

        //Verify
        assertEquals(returnedResponse.getStatus(), 200);
        assertNotNull(arrangement);
    }

    @Test
    public void getArrangementsShouldReturnResponse403SessionOrArrangementCannotBeFound() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        HttpSession sessionMock = mock(HttpSession.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getSession()).thenReturn(sessionMock);
        //Act
        Response returnedResponse = arrangement.getArrangements(request, response, 25, 0);
        //Verify
        assertEquals(403, returnedResponse.getStatus());

    }

    @Test
    public void getArrangementsShouldReturnOnlyArrangementsNotDormant() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        HttpSession sessionMock = mock(HttpSession.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getSession()).thenReturn(sessionMock);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        ArrayList<ArrangementWrapper> wrapers = arrangementData.getArrangementWrappers(userId);
        wrapers.get(0).setState(ArrangementStateType.DORMANT);
        String expectedSize = String.valueOf(wrapers.size() - 1);
        //Act
        Response returnedResponse = arrangement.getArrangements(request, response, 25, 0);
        //Verify
        Arrangements arrangements = (Arrangements) returnedResponse.getEntity();
        Page<Arrangement> pageObtained = arrangements.getArrangements();
        assertEquals(expectedSize, pageObtained.getTotal().toString());

    }

    @Test
    public void getIndividualDetailsShouldReturnTheArrangementWithTheArrengementIdPassed() {

        //Set up
        SessionHelper session = mock(SessionHelper.class);
        ArrangementServiceDataIpsum arrangementData = new ArrangementServiceDataIpsum();
        Mockito.when(session.returnSessionVariable("userId")).thenReturn(userId);

        //Act

        ArrangementWrapper arrangementWrapper = arrangementData.getArrangementWrapper(arrangementId);
        CurrentAccount arrangementReturned = (CurrentAccount) objectGenerator.createCurrentAccount(arrangementWrapper);
        //Verify
        assertNotNull(arrangementReturned);
        assertEquals(arrangementId, arrangementReturned.getId());
    }

    @Test
    public void getIndividualDetailsShoulrReturnErrorResponseCode403WithForcedLogoutMessageWhenAccountIsDisabled() {
        //Set up
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);

        SessionHelper session = mock(SessionHelper.class);
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        Mockito.when(session.getSession()).thenReturn(httpSession);

        //Act
        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        wrapper.setState(ArrangementStateType.DISABLED);
        Response returnedResponse = arrangement.getArrangement(request, response, arrangementId);
        //Verify
        assertEquals(ArrangementStateType.DISABLED, wrapper.getState());
        assertEquals(403, returnedResponse.getStatus());

    }

    @Test
    public void getIndividualDetailsShouldReturn404IfTheArrangementIdIsNotFound() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.returnSessionVariable("userId")).thenReturn(userId);
        String arrangementId = "Incorrect Arrengement id"; // arrangement id of credit card account belonging to the selected user
        //Act
        Response returnedResponse = arrangement.getArrangement(request, response, arrangementId);

        //Verify
        assertEquals(403, returnedResponse.getStatus());
    }

    @Test
    public void getIndividualDetailsShouldReturn404IfArrangementIsDormant() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        arrangementData.getArrangementWrapper(arrangementId).setState(ArrangementStateType.DORMANT);

        //Act
        Response returnedResponse = arrangement.getArrangement(request, response, arrangementId);

        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void getArrangementShouldReturnResponse200AndOneAccountWithSameUserIdAsSessionAndSameArrangementIdAsParameter() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);

        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);

        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act
        Response returnedResponse = arrangement.getArrangement(request, response, arrangementId);
        //Verify
        assertNotNull(returnedResponse);
        assertEquals(200, returnedResponse.getStatus());
    }

    @Test
    public void GetStatementsShouldReturnResponse200AndPageOfTransactionsObjectsWhenRequestSuccessful() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act
        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 50, "", "", "");

        //Verify
        assertEquals(returnedResponse.getStatus(), 200);

    }

    @Test
    public void GetStatementsShouldReturnEmptyListIfArrangementHasNone() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        ArrangementWrapper wrapper = new ArrangementWrapper();
        wrapper.setAcceptsTransfers(true);
        wrapper.setAccountName("dummy name");
        wrapper.setAccountNumber("14141414");
        wrapper.setAvailableBalance(objectGenerator.createCurrencyAmount("100", "GBP"));
        wrapper.setBalance(objectGenerator.createCurrencyAmount("200", "GBP"));
        wrapper.setCreateBeneficiaryAvailable(true);
        wrapper.setId("14141414141414");
        wrapper.setMakePaymentAvailable(true);
        wrapper.setMakeTransferAvailable(true);
        wrapper.setMessages(null);
        wrapper.setNonMigratedAccount(true);
        wrapper.setOtherBrand(true);
        wrapper.setOtherBrandBankName("HSBC");
        wrapper.setOverdraftLimit(objectGenerator.createCurrencyAmount("100", "GBP"));
        wrapper.setReferenceNumber("");
        wrapper.setSortCode("141414");
        wrapper.setStartDate(Calendar.getInstance().getTime());
        wrapper.setState(ArrangementStateType.ENABLED);
        wrapper.setStatementValiditySpan("12");

        arrangementData.getArrangementWrappers(userId).add(wrapper);

        //Act

        Response returnedResponse = arrangement.getStatement(request, response, wrapper.getId(), 0, 25, null, null, null);


        //Verify
        assertEquals(200, returnedResponse.getStatus());
        Statement<Transaction> statement = (Statement<Transaction>) returnedResponse.getEntity();
        Page<Transaction> page = statement.getTransactions();
        assertEquals(0, page.getItems().size());
    }

    @Test
    public void GetStatementsShouldRunDateFilterWhenGivenValidDateParameters() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        TransactionsDataIpsum transactions = mock(TransactionsDataIpsum.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.transactionData = transactions;
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String dateFrom = "22/07/2013";
        String dateTo = "23/07/2013";
        //Act
        arrangement.getStatement(request, response, arrangementId, 0, 50, dateFrom, dateTo, null);
        //Verify
        Mockito.verify(transactions).getTransactionListFilteredByDate(arrangementId, dateFrom, dateTo);
    }

    @Test
    public void GetStatementsShouldReturnResponse404IfSessionOrArrangementCannotBeFound() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(null);//We mock invalid userId by returning null

        //Act

        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 25, "", "", "");

        //Verify
        assertEquals(403, returnedResponse.getStatus());
    }

    @Test
    public void GetStatementShouldReturnResponse403IfArrangementIsDisabled() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);

        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        wrapper.setState(ArrangementStateType.DISABLED);
        //Act
        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 25, "", "", "");
        //Verify
        assertEquals(ArrangementStateType.DISABLED, wrapper.getState());
        assertEquals(403, returnedResponse.getStatus());
    }


    @Test
    public void GetStatementsShouldReturnResponse423IfArrangementIsDormant() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);//We mock userID with dormant arrangement

        arrangementData.getArrangementWrapper(arrangementId).setState(ArrangementStateType.DORMANT);
        //Act
        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 25, "", "", "");
        //Verify
        assertEquals(423, returnedResponse.getStatus());

    }

    @Test
    public void GetStatementsShouldReturnResponse400IfOneDateIsOnlyGiven() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act

        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 25, "10/10/2010", null, null);

        //Verify
        assertEquals(400, returnedResponse.getStatus());
    }

    @Test
    public void GetStatementsShouldReturnResponse400IfTwoDatesAreGivenButDateFromGreaterThanDateTo() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act

        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 25, "10/10/2013", "15/20/2010", "");

        //Verify
        assertEquals(400, returnedResponse.getStatus());
    }

    @Test
    public void GetStatementsShouldReturnResponse400IfTwoDatesAreGivenButDateToGreaterThanToday() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);



        //Act
        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 25, validPreviousDate, futureDate, "");
        //Verify
        assertEquals(400, returnedResponse.getStatus());
    }

    @Test
    public void GetStatementsShouldReturnResponse410IfArrangementHasStatementExpiredAndRequestIsMadeForTransactionsBeforeExpirationDate() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act

        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 25, "10/01/2008", "15/03/2013", null);


        //Verify
        assertEquals(410, returnedResponse.getStatus());
    }

    @Test
    public void GetStatementsShouldReturnResponse200IfTwoDatesAreGivenAndDatesAreEquals() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act

        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 25, "10/07/2013", "10/07/2013", "");

        //Verify
        assertEquals(200, returnedResponse.getStatus());
    }

    @Test
    public void GetStatementsShouldReturnResponse200IfDateFromIsEqualStartDate() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        ArrayList<ArrangementWrapper> wrapperArrayList = PersistenceContainer.getInstance().getArrangementWrapper().get(userId);
        ArrangementWrapper wrapper = wrapperArrayList.get(0);

        wrapper.setStartDate(GregorianCalendar.getInstance().getTime());
        //Act

        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 25, currentDate, currentDate, "");

        //Verify
        assertEquals(200, returnedResponse.getStatus());
    }

    @Test
    public void GetStatementsShouldReturnResponse200AndPageOfTransactionsObjectsWhenRequestSuccessfulAndTwoDatesNotGiven() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act
        Response returnedResponse = arrangement.getStatement(request, response, arrangementId, 0, 50, "", "", "James");

        //Verify
        assertEquals(returnedResponse.getStatus(), 200);
        assertNotNull(arrangement);
    }


    @Test
    public void GetStatementsShouldReturnResponse200AndPageOfTransactionsContainingTheQueryString() {
        //SetUp
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        TransactionsDataIpsum transactionsData = mock(TransactionsDataIpsum.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.transactionData = transactionsData;
        arrangement.setSessionHelper(session);

        Mockito.when(session.getUserId(request)).thenReturn(userId);
        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        ArrayList<Transaction> unsortedList = new ArrayList<Transaction>();
        unsortedList.add(objectGenerator.createNewTransaction("50", "50", "01/01/2000", "GBP", arrangementId, "DEPOSIT", "James", wrapper));
        unsortedList.add(objectGenerator.createNewTransaction("50", "50", "02/01/2001", "GBP", arrangementId, "DEPOSIT", "Frank", wrapper));
        unsortedList.add(objectGenerator.createNewTransaction("50", "50", "01/02/2000", "GBP", arrangementId, "DEPOSIT", "sophie", wrapper));
        unsortedList.add(objectGenerator.createNewTransaction("50", "50", "", "GBP", arrangementId, "DEPOSIT", "James's", wrapper));
        unsortedList.add(objectGenerator.createNewTransaction("50", "50", "", "GBP", arrangementId, "DEPOSIT", "James?", wrapper));        //Act

        Mockito.when(transactionsData.getTransactionList(arrangementId)).thenReturn(unsortedList);
        arrangement.getStatement(request, response, arrangementId, 0, 50, null, null, "James");
        //Verify

        Mockito.verify(transactionsData).filterTransactionsByName(unsortedList, "James");
    }

    @Test
    public void GetBeneficiariesShouldReturnResponse200AndPageOfBeneficiariesObjectsWhenRequestSuccessful() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        //Act
        Response returnedResponse = arrangement.getBeneficiaries(request, response, arrangementId, 0, 50);

        //Verify
        assertEquals(returnedResponse.getStatus(), 200);
        assertNotNull(arrangement);
    }

    @Test
    public void GetBeneficiariesShouldReturnEmptyListIfArrangementHasNone() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String arrangementId = "14141414141414";
        int pageIndex = 0;
        int size = 25;
        ArrangementWrapper wrapper = new ArrangementWrapper();
        wrapper.setAcceptsTransfers(true);
        wrapper.setAccountName("dummy name");
        wrapper.setAccountNumber("14141414");
        wrapper.setAvailableBalance(objectGenerator.createCurrencyAmount("100", "GBP"));
        wrapper.setBalance(objectGenerator.createCurrencyAmount("200", "GBP"));
        wrapper.setCreateBeneficiaryAvailable(true);
        wrapper.setId(arrangementId);
        wrapper.setMakePaymentAvailable(true);
        wrapper.setMakeTransferAvailable(true);
        wrapper.setMessages(null);
        wrapper.setNonMigratedAccount(true);
        wrapper.setOtherBrand(true);
        wrapper.setOtherBrandBankName("HSBC");
        wrapper.setOverdraftLimit(objectGenerator.createCurrencyAmount("100", "GBP"));
        wrapper.setReferenceNumber("");
        wrapper.setSortCode("141414");
        wrapper.setStartDate(Calendar.getInstance().getTime());
        wrapper.setState(ArrangementStateType.ENABLED);
        wrapper.setStatementValiditySpan("12");

        arrangementData.getArrangementWrappers(userId).add(wrapper);

        //Act

        Response returnedResponse = arrangement.getBeneficiaries(request, response, arrangementId, pageIndex, size);


        //Verify
        assertEquals(200, returnedResponse.getStatus());

        Beneficiaries beneficiaries = (Beneficiaries) returnedResponse.getEntity();
        Page page = beneficiaries.getBeneficiaries();
        assertEquals(0, page.getItems().size());
    }

    @Test
    public void GetBeneficiariesShouldReturnResponse403WhenArrangementIsDisabled() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);

        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        Mockito.when(session.getSession()).thenReturn(httpSession);

        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        wrapper.setState(ArrangementStateType.DISABLED);
        //Act
        Response returnedResponse = arrangement.getBeneficiaries(request, response, arrangementId, 0, 25);
        //Verify
        assertEquals(ArrangementStateType.DISABLED, wrapper.getState());
        assertEquals(403, returnedResponse.getStatus());
    }

    @Test
    public void GetBeneficiariesShouldReturnResponse404WhenArrangementIsDormant() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);

        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        wrapper.setState(ArrangementStateType.DORMANT);
        //Act
        Response returnedResponse = arrangement.getBeneficiaries(request, response, arrangementId, 0, 25);
        //Verify
        assertEquals(ArrangementStateType.DORMANT, wrapper.getState());
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void GetBeneficiariesShouldReturnResponse404IfSessionOrArrangementCannotBeFound() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(null);//We mock invalid userId by returning null

        //Act
        Response returnedResponse = arrangement.getBeneficiaries(request, response, arrangementId, 0, 25);
        //Verify
        assertEquals(403, returnedResponse.getStatus());
    }


    @Test
    public void confirmBeneficiaryShouldReturnResponse401IfUserIdAndPasswordCannotBeAuthenticated() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);//We mock invalid userId by returning null
        String password = "WRONG-PASSWORD";

        String beneficiaryId = "11111111111111";
        //Act
        Response returnedResponse = arrangement.confirmBeneficiary(request, response, arrangementId, beneficiaryId, password);
        //Verify
        assertEquals(401, returnedResponse.getStatus());
    }

    @Test
    public void confirmBeneficiaryShouldReturnResponse404IfUserIdOrArrangementIdOrTransactionIdCannotBeFound() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);//We mock invalid userId by returning null
        String password = userId;

        String beneficiaryId = "WRONG-BENEFICIARYID";
        //Act

        Response returnedResponse = arrangement.confirmBeneficiary(request, response, arrangementId, beneficiaryId, password);

        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void confirmBeneficiaryShouldReturnResponse200IfConfirmationIsSuccessful() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        //setting up beneficiary
        Beneficiary beneficiary = new Beneficiary();

        String sortCode = "sd2530";
        String accountNumber = "613455551";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        session.setBeneficiary(beneficiary);
        String beneficiaryId = sortCode + accountNumber;
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);//We mock invalid userId by returning null
        String password = userId;
        Mockito.when(session.returnBeneficiarySessionVariable(beneficiaryId)).thenReturn(beneficiary);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        //Act
        Response returnedResponse = arrangement.confirmBeneficiary(request, response, arrangementId, beneficiaryId, password);
        //Verify
        assertEquals(200, returnedResponse.getStatus());
    }

    @Test
    public void confirmBeneficiaryShouldCreateListOfBeneficiariesIfThereIsntOneAssociatedWithTheArrangementItTriesToAddBeneficiaryTo() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();

        String arrangementId = "14141414141414";
        ArrangementWrapper wrapper = new ArrangementWrapper();
        wrapper.setAcceptsTransfers(true);
        wrapper.setAccountName("dummy name");
        wrapper.setAccountNumber("14141414");
        wrapper.setAvailableBalance(objectGenerator.createCurrencyAmount("100", "GBP"));
        wrapper.setBalance(objectGenerator.createCurrencyAmount("200", "GBP"));
        wrapper.setCreateBeneficiaryAvailable(true);
        wrapper.setId(arrangementId);
        wrapper.setMakePaymentAvailable(true);
        wrapper.setMakeTransferAvailable(true);
        wrapper.setMessages(null);
        wrapper.setNonMigratedAccount(true);
        wrapper.setOtherBrand(true);
        wrapper.setOtherBrandBankName("HSBC");
        wrapper.setOverdraftLimit(objectGenerator.createCurrencyAmount("100", "GBP"));
        wrapper.setReferenceNumber("");
        wrapper.setSortCode("141414");
        wrapper.setStartDate(Calendar.getInstance().getTime());
        wrapper.setState(ArrangementStateType.ENABLED);
        wrapper.setStatementValiditySpan("12");

        arrangementData.getArrangementWrappers(userId).add(wrapper);

        //setting up beneficiary
        Beneficiary beneficiary = new Beneficiary();
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        session.setBeneficiary(beneficiary);
        String beneficiaryId = sortCode + accountNumber;

        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);//We mock invalid userId by returning null
        String password = userId;
        Mockito.when(session.returnBeneficiarySessionVariable(beneficiaryId)).thenReturn(beneficiary);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        //Act
        Response returnedResponse = arrangement.confirmBeneficiary(request, response, arrangementId, beneficiaryId, password);
        //Verify
        assertEquals(200, returnedResponse.getStatus());
        ArrayList<Beneficiary> list = beneficiaries.getBeneficiaryList(arrangementId);
        assertEquals(1, list.size());
    }

    @Test
    public void ConfirmBeneficiaryShouldReturn403WhenArrangementIsDisabled() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);

        Beneficiary beneficiary = new Beneficiary();


        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);

        //BENEFICIARY_DETAILS
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        wrapper.setState(ArrangementStateType.DISABLED);
        //Act
        Response returnedResponse = arrangement.confirmBeneficiary(request, response, arrangementId, "BENEFICIARYID", userId);
        //Verify
        assertEquals(ArrangementStateType.DISABLED, wrapper.getState());
        assertEquals(403, returnedResponse.getStatus());
    }

    @Test
    public void confirmBeneficiaryShouldReturnResponse404IfArrangementIsDormant() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);//We mock userID with dormant arrangement
        String password = userId;

        String beneficiaryId = "BENEFICIARYID";

        //Act
        Response returnedResponse = arrangement.confirmBeneficiary(request, response, arrangementId, beneficiaryId, password);

        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void confirmBeneficiaryShouldReturnResponse400IfNoParametersAreGiven() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);//We mock userID with dormant arrangement

        String beneficiaryId = "BENEFICIARYID";

        //Act
        Response returnedResponse = arrangement.confirmBeneficiary(request, response, arrangementId, beneficiaryId, null);

        //Verify
        assertEquals(400, returnedResponse.getStatus());
    }

    @Test
    public void confirmBeneficiaryShouldReturnResponse404IfArrangementIdDoesntMatchWithUser() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        String password = userId;
        Mockito.when(session.getUserId(request)).thenReturn(userId);//We mock userID with dormant arrangement
        String arrangementId = "16349942000000";
        String beneficiaryId = "BENEFICIARYID";

        //Act
        Response returnedResponse = arrangement.confirmBeneficiary(request, response, arrangementId, beneficiaryId, password);

        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void confirmBeneficiaryShouldReturnResponse412WhenArrangementHasBeneficiaryDisabled() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        //setting up beneficiary
        Beneficiary beneficiary = new Beneficiary();
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        session.setBeneficiary(beneficiary);
        String beneficiaryId = sortCode + accountNumber;
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);//We mock invalid userId by returning null
        String password = userId;
        Mockito.when(session.returnBeneficiarySessionVariable(beneficiaryId)).thenReturn(beneficiary);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        arrangementData.getArrangementWrapper(arrangementId).setCreateBeneficiaryAvailable(false);
        //Act
        Response returnedResponse = arrangement.confirmBeneficiary(request, response, arrangementId, beneficiaryId, password);
        //Verify
        assertEquals(412, returnedResponse.getStatus());
    }

    @Test
    public void CreateBeneficiaryCreatesnewBeneficiaryObjectAndReturnsItWithResponseCode201() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        Beneficiary beneficiary = new Beneficiary();
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        String name = "Adam";
        String reference = "";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        //Act
        Response returnedResponse = arrangement.createBeneficiary(request, response, arrangementId, sortCode, accountNumber, name, reference);
        //Verify
        assertEquals(201, returnedResponse.getStatus());
    }

    @Test
    public void createBeneficiaryShoulrReturn400ErrorBadRequestIfBeneficiaryHasWhiteListedAccountAndReferenceIsMissing() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper sessionHelper = mock(SessionHelper.class);
        String accountNumber = "22222222";
        String sortCode = "222222";
        String name = "Vodafone";
        String reference = null;
        ArrangementsServiceImpl arrangementService = new ArrangementsServiceImpl();
        arrangementService.setSessionHelper(sessionHelper);
        Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

        //Act
        Response returnedResponse = arrangementService.createBeneficiary(request, response, arrangementId, sortCode, accountNumber, name, reference);
        //Verify
        assertEquals(400, returnedResponse.getStatus());
    }

    @Test
    public void createBeneficiaryShoulrReturn201IfBeneficiaryHasWhiteListedAccountAndReferenceIsIncluded() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper sessionHelper = mock(SessionHelper.class);
        String accountNumber = "22222222";
        String sortCode = "222222";
        String name = "Vodafone";
        String reference = "vodafone Pay Monthly";
        ArrangementsServiceImpl arrangementService = new ArrangementsServiceImpl();
        arrangementService.setSessionHelper(sessionHelper);
        Mockito.when(sessionHelper.getUserId(request)).thenReturn(userId);

        //Act
        Response returnedResponse = arrangementService.createBeneficiary(request, response, arrangementId, sortCode, accountNumber, name, reference);
        //Verify
        assertEquals(201, returnedResponse.getStatus());
    }

    @Test
    public void createBeneficiaryShoulrReturn403IfUserIsNotLoggedIn() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper sessionHelper = mock(SessionHelper.class);
        String accountNumber = "22222222";
        String sortCode = "222222";
        String name = "Vodafone";
        String reference = "vodafone Pay Monthly";
        ArrangementsServiceImpl arrangementService = new ArrangementsServiceImpl();
        arrangementService.setSessionHelper(sessionHelper);
        Mockito.when(sessionHelper.getUserId(request)).thenReturn(null);

        //Act
        Response returnedResponse = arrangementService.createBeneficiary(request, response, arrangementId, sortCode, accountNumber, name, reference);
        //Verify
        assertEquals(403, returnedResponse.getStatus());
    }

    @Test
    public void createBeneficiaryShouldReturn412ErrorBeneficiaryDisabledWhenArrangementHasBeneficiaryDisabled() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        Beneficiary beneficiary = new Beneficiary();
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        String name = "Adam";
        String reference = "";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        arrangementData.getArrangementWrapper(arrangementId).setCreateBeneficiaryAvailable(false);
        //Act
        Response returnedResponse = arrangement.createBeneficiary(request, response, arrangementId, sortCode, accountNumber, name, reference);
        //Verify
        assertEquals(412, returnedResponse.getStatus());
    }

    @Test
    public void CreateBeneficiaryShouldReturn403WhenArrangementIsDisabled() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);
        Beneficiary beneficiary = new Beneficiary();

        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);

        //BENEFICIARY_DETAILS
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        String name = "Adam";
        String reference = "";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        wrapper.setState(ArrangementStateType.DISABLED);
        //Act
        Response returnedResponse = arrangement.createBeneficiary(request, response, arrangementId, sortCode, accountNumber, name, reference);
        //Verify
        assertEquals(ArrangementStateType.DISABLED, wrapper.getState());
        assertEquals(403, returnedResponse.getStatus());
    }


    @Test
    public void CreateBeneficiaryReturnsResponseCode404WhenInvalidArrangementIDGiven() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);
        String invalidArrangementId = "1234";
        Beneficiary beneficiary = new Beneficiary();
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        String name = "greg";
        String reference = "";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(invalidArrangementId);
        beneficiary.setSortCode(sortCode);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        //Act
        Response returnedResponse = arrangement.createBeneficiary(request, response, invalidArrangementId, sortCode, accountNumber, name, reference);
        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void CreateBeneficiaryReturnsResponseCode404WhenAnyNullValueGivenExceptReference() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        String name = "greg";
        String reference = "";

        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        //Act
        Response returnedResponse1 = arrangement.createBeneficiary(request, response, arrangementId, null, accountNumber, name, reference);
        Response returnedResponse2 = arrangement.createBeneficiary(request, response, arrangementId, sortCode, null, name, reference);
        Response returnedResponse3 = arrangement.createBeneficiary(request, response, arrangementId, sortCode, accountNumber, null, reference);
        Response returnedResponse4 = arrangement.createBeneficiary(request, response, arrangementId, sortCode, accountNumber, name, null);
        //Verify
        assertEquals(400, returnedResponse1.getStatus());
        assertEquals(400, returnedResponse2.getStatus());
        assertEquals(400, returnedResponse3.getStatus());
        assertEquals(201, returnedResponse4.getStatus());
    }

    @Test
    public void CreateBeneficiaryReturnsResponseCode404WhenArrangementIsDormant() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        Beneficiary beneficiary = new Beneficiary();
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        String name = "greg";
        String reference = "";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        arrangementData.getArrangementWrapper(arrangementId).setState(ArrangementStateType.DORMANT);
        //Act
        Response returnedResponse = arrangement.createBeneficiary(request, response, arrangementId, sortCode, accountNumber, name, reference);
        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void CreatePaymentReturnsResponseCode404WhenBeneficiaryNotFound() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String beneficiaryId = "";
        String date = "";
        String amount = "";
        String currencyCode = "";
        String reference = "";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        //Act
        Response returnedResponse = arrangement.createPayment(request, response, arrangementId, beneficiaryId, date, amount, currencyCode, reference);
        //verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void CreatePaymentReturnsResponseCode404WhenArrangementIdNotFound() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String arrangementId = "";
        String beneficiaryId = "";
        String date = "";
        String amount = "";
        String currencyCode = "";
        String reference = "";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        //Act
        Response returnedResponse = arrangement.createPayment(request, response, arrangementId, beneficiaryId, date, amount, currencyCode, reference);
        //verify
        assertEquals(404, returnedResponse.getStatus());
    }


    @Test
    public void CreatePaymentReturnsResponseCode403WhenArrangementIsDisabled() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);
        String newBeneficiaryID = beneficiaries.getNewBeneficiaryID();
        Beneficiary beneficiaryTemp = objectGenerator.createBeneficiary("12354634", "1245658", "persone1", newBeneficiaryID);
        beneficiaries.getBeneficiaryList(arrangementId).add(beneficiaryTemp);
        String date = "";
        String amount = "";
        String currencyCode = "";
        String reference = "";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        wrapper.setState(ArrangementStateType.DISABLED);
        //Act
        Response returnedResponse = arrangement.createPayment(request, response, arrangementId, beneficiaryTemp.getId(), date, amount, currencyCode, reference);
        //verify
        assertEquals(ArrangementStateType.DISABLED, wrapper.getState());
        assertEquals(403, returnedResponse.getStatus());
    }


    @Test
    public void ConfirmPaymentReturnsResponseCode403WhenArrangementIsDisabled() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);
        String newBeneficiaryID = beneficiaries.getNewBeneficiaryID();
        Beneficiary beneficiaryTemp = objectGenerator.createBeneficiary("12354634", "1245658", "persone1", newBeneficiaryID);
        beneficiaries.getBeneficiaryList(arrangementId).add(beneficiaryTemp);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        wrapper.setState(ArrangementStateType.DISABLED);
        //Act
        Response returnedResponse = arrangement.confirmPayment(request, response, arrangementId, beneficiaryTemp.getId(), "698745123", userId);
        //verify
        assertEquals(ArrangementStateType.DISABLED, wrapper.getState());
        assertEquals(403, returnedResponse.getStatus());
    }


    @Test
    public void CreatePaymentReturnsResponseCode404WhenArrangementIsDormant() {
        //Set up
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        Beneficiary beneficiary = new Beneficiary();
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        String reference = "";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        String date = "10/05/2013";
        beneficiaries.getBeneficiaryList(arrangementId).add(beneficiary);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        arrangementData.getArrangementWrapper(arrangementId).setState(ArrangementStateType.DORMANT);
        //Act
        Response returnedResponse = arrangement.createPayment(request, response, arrangementId, beneficiary.getId(), date, "100.00", "GBP", reference);
        //verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void CreatePaymentReturnsResponseCode402WhenAmountExceedsBalance() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String beneficiaryId = "11111111111111";
        String date = "10/05/2013";
        String amount = "1000000";
        String currencyCode = "GBP";
        String reference = "reference";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act
        Response returnedResponse = arrangement.createPayment(request, response, arrangementId, beneficiaryId, date, amount, currencyCode, reference);
        //Verify
        assertEquals(402, returnedResponse.getStatus());
    }

    @Test
    public void CreatePaymentReturnsResponseCode412WhenPaymentsDisabled() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        arrangementData.getArrangementWrapper(arrangementId).setMakePaymentAvailable(false);
        String beneficiaryId = "11111111111111";
        String date = "10/05/2013";
        String amount = "10";
        String currencyCode = "GBP";
        String reference = "reference";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act
        Response returnedResponse = arrangement.createPayment(request, response, arrangementId, beneficiaryId, date, amount, currencyCode, reference);
        //Verify
        assertEquals(412, returnedResponse.getStatus());
    }

    @Test
    public void CreatePaymentCreatesPaymentObjectAndReturnsResponseCode201() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String beneficiaryId = "11111111111111";
        String date = "10/05/2013";
        String amount = "10";
        String currencyCode = "GBP";
        String reference = "reference";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);

        //Act
        Response returnedResponse = arrangement.createPayment(request, response, arrangementId, beneficiaryId, date, amount, currencyCode, reference);
        //Verify
        assertEquals(201, returnedResponse.getStatus());
    }


    @Test
    public void confirmPaymentShouldReturnResponse404IfUserIdOrArrangementIdOrPaymentIdCannotBeFound() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String beneficiaryId = "11111111111111";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String password = userId;
        String paymentId = "WRONG-PAYMENTID";
        //Act
        Response returnedResponse = arrangement.confirmPayment(request, response, arrangementId, beneficiaryId, paymentId, password);

        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void confirmPaymentShouldReturnResponse404IfArrangementIsDormant() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Beneficiary beneficiary = new Beneficiary();
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        beneficiaries.getBeneficiaryList(arrangementId).add(beneficiary);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String password = userId;
        String paymentId = "PAYMENTID";
        //Act
        Response returnedResponse = arrangement.confirmPayment(request, response, arrangementId, beneficiary.getId(), paymentId, password);

        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void confirmPaymentShouldReturnResponse202IfArrangementIsSuspicious() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Beneficiary beneficiary = new Beneficiary();
        String sortCode = "sd2530";
        String accountNumber = "613455551";
        beneficiary.setAccountNumber(accountNumber);
        beneficiary.setId(arrangementId);
        beneficiary.setSortCode(sortCode);
        beneficiaries.getBeneficiaryList(arrangementId).add(beneficiary);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String password = userId;
        String paymentId = PersistenceContainer.getInstance().getNewTransactionID();
        //setting up payment

        Transaction transaction = new Transaction();
        CurrencyAmount currencyAmount = new CurrencyAmount();
        BigDecimal decimal = new BigDecimal(25).setScale(2, BigDecimal.ROUND_HALF_UP);
        currencyAmount.setAmount(decimal);
        currencyAmount.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);
        transaction.setAmount(currencyAmount);
        Date newDate;
        try {
            newDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2010");
            transaction.setDate(newDate);
        } catch (ParseException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        transaction.setDescription("payment");
        transaction.setId(paymentId);
        CurrencyAmount runningBalance = new CurrencyAmount();
        BigDecimal newDecimal = new BigDecimal(Double.valueOf("1234")).setScale(2, BigDecimal.ROUND_HALF_UP);
        runningBalance.setAmount(newDecimal);
        runningBalance.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);
        transaction.setRunningBalance(runningBalance);
        transaction.setType(TransactionType.TRANSFER);
        Mockito.when(session.returnTransactionSessionVariable(paymentId)).thenReturn(transaction);
        arrangementData.getArrangementWrapper(arrangementId).setState(ArrangementStateType.SUSPICIOUS);
        //Act
        Response returnedResponse = arrangement.confirmPayment(request, response, arrangementId, beneficiary.getId(), paymentId, password);

        //Verify
        assertEquals(202, returnedResponse.getStatus());
    }

    @Test
    public void confirmPaymentShouldReturnResponse401PasswordIsIncorrect() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String beneficiaryId = "11111111111111";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String password = "WRONG-PASSWORD";
        String paymentId = "";
        //Act
        Response returnedResponse = arrangement.confirmPayment(request, response, arrangementId, beneficiaryId, paymentId, password);
        //Verify
        assertEquals(401, returnedResponse.getStatus());
    }

    @Test
    public void confirmPaymentShouldReturnResponse404ArrangementIsNotFound() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String arrangementId = "invalidArragnementId"; // arrangement id of credit card account belonging to the selected user
        String beneficiaryId = "11111111111111";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String password = userId;
        String paymentId = "";
        //Act
        Response returnedResponse = arrangement.confirmPayment(request, response, arrangementId, beneficiaryId, paymentId, password);
        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }


    @Test
    public void confirmPaymentShouldReturnResponse404BeneficiaryIsNotFound() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String beneficiaryId = "invalidBeneficiaryId";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String password = userId;
        String paymentId = "";
        //Act
        Response returnedResponse = arrangement.confirmPayment(request, response, arrangementId, beneficiaryId, paymentId, password);
        //Verify
        assertEquals(404, returnedResponse.getStatus());
    }

    @Test
    public void confirmPaymentShouldReturnResponse402IfPaymentIsDeclined() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String beneficiaryId = "11111111111111";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String password = userId;
        String paymentId = "123456";
        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        Transaction transactionFromSession = objectGenerator.createNewTransaction("400", paymentId, "25/7/2005", "GBP", arrangementId, "PAYMENT", "hello world", wrapper);
        arrangementHelper.updateBalance(arrangementId, objectGenerator.createCurrencyAmount("100", "GBP"));
        Mockito.when(session.returnTransactionSessionVariable(paymentId)).thenReturn(transactionFromSession);
        //Act
        Response returnedResponse = arrangement.confirmPayment(request, response, arrangementId, beneficiaryId, paymentId, password);
        //Verify
        assertEquals(402, returnedResponse.getStatus());
    }


    @Test
    public void confirmPaymentShouldReturnResponse200IfConfirmationIsSuccessful() {
        //Setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String beneficiaryId = "11111111111111";
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String password = userId;
        String paymentId = PersistenceContainer.getInstance().getNewTransactionID();

        //setting up payment

        Transaction transaction = new Transaction();
        CurrencyAmount currencyAmount = new CurrencyAmount();
        BigDecimal decimal = new BigDecimal(25).setScale(2, BigDecimal.ROUND_HALF_UP);
        currencyAmount.setAmount(decimal);
        currencyAmount.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);
        transaction.setAmount(currencyAmount);
        Date newDate;
        try {
            newDate = new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2010");
            transaction.setDate(newDate);
        } catch (ParseException e) {
            // Auto-generated catch block
            e.printStackTrace();
        }
        transaction.setDescription("payment");
        transaction.setId(paymentId);
        CurrencyAmount runningBalance = new CurrencyAmount();
        BigDecimal newDecimal = new BigDecimal(Double.valueOf("1234")).setScale(2, BigDecimal.ROUND_HALF_UP);
        runningBalance.setAmount(newDecimal);
        runningBalance.setCurrency(CurrencyAmount.CURRENCY_CODE.GBP);
        transaction.setRunningBalance(runningBalance);
        transaction.setType(TransactionType.TRANSFER);
        Mockito.when(session.returnTransactionSessionVariable(paymentId)).thenReturn(transaction);
        //Act
        Response returnedResponse = arrangement.confirmPayment(request, response, arrangementId, beneficiaryId, paymentId, password);
        //Verify
        assertEquals(200, returnedResponse.getStatus());
    }

    @Test
    public void makeTransferShouldReturnResponse400IfAmountExceedsAvailableBalance() {
        //setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "100000000";
        String currencyCode = "GBP";

        //act
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);

        //verify
        assertEquals(400, returnedResponse.getStatus());
        Error expected = errorHelper.getAmountExceedsBalanceError();
        Error obtained = (Error) returnedResponse.getEntity();
        assertEquals(expected.getMessage(), obtained.getMessage());
    }

    @Test
    public void makeTransferShouldReturnResponse400IfProhibitiveIndicatorIsFlaggedOnPayingArrangement() {
        //setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "100.50";
        String currencyCode = "GBP";
        arrangementData.getArrangementWrapper(arrangementId).setProhibitiveIndicator(true);
        //act
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);
        Error returned = (Error) returnedResponse.getEntity();
        //verify
        Error expectedError = errorHelper.getProhibitiveIndicatorError();
        assertEquals(400, returnedResponse.getStatus());
        assertEquals(expectedError.getMessage(), returned.getMessage());
    }

    @Test
    public void makeTransferShouldReturnResponse400IfProhibitiveIndicatorIsFlaggedonReceivingArrangement() {
        //setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "100.50";
        String currencyCode = "GBP";
        arrangementData.getArrangementWrapper(targetArrangementId).setProhibitiveIndicator(true);
        //act
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);
        Error returned = (Error) returnedResponse.getEntity();
        //verify
        Error expectedError = errorHelper.getProhibitiveIndicatorError();
        assertEquals(400, returnedResponse.getStatus());
        assertEquals(expectedError.getMessage(), returned.getMessage());
    }

    @Test
    public void makeTransferShouldReturnResponse400IfSameAccounts() {
        //setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "100.50";
        String currencyCode = "GBP";

        //act
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, arrangementId, amount, currencyCode);

        //verify
        assertEquals(400, returnedResponse.getStatus());
        Error expected = errorHelper.getSameAccountsError();
        Error obtained = (Error) returnedResponse.getEntity();
        assertEquals(expected.getMessage(), obtained.getMessage());
    }

    @Test
    public void makeTransferShouldReturnResponse404IfArrangementIdOrUserIdEitherDontExistOrDontMatch() {
        //SETUP
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        String arrangementId = "Arrangement Should not match"; // arrangement id of credit card account belonging to the selected user
        String arrangementId2 = "61345555132535"; // arrangement id of credit card account belonging to the selected user

        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String targetArrangementId = "61345555132535";
        String targetArrangementId2 = "Arrangement Should not match";
        String amount = "100.50";
        String currencyCode = "GBP";

        //ACT
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);
        Response returnedResponse2 = arrangement.makeTransfer(request, response, arrangementId2, targetArrangementId2, amount, currencyCode);
        //VERIFIY
        assertEquals(404, returnedResponse.getStatus());
        assertEquals(404, returnedResponse2.getStatus());

    }

    @Test
    public void makeTransferShouldReturnResponse400IfAmmountOrCurrencyOrTargetArrangementIdAreNotPassed() {
        //SETUP
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);

        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "100.50";
        String currencyCode = "GBP";

        //ACT
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, null, amount, currencyCode);
        Response returnedResponse2 = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, null, currencyCode);
        Response returnedResponse3 = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, null);
        //VERIFIY
        assertEquals(400, returnedResponse.getStatus());
        assertEquals(400, returnedResponse2.getStatus());
        assertEquals(400, returnedResponse3.getStatus());

    }

    @Test
    public void makeTransferShouldReturnResponse400TransactionSumIsBelowThreshold() {
        //setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "-1";
        String currencyCode = "GBP";

        //act
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);

        //verify
        assertEquals(400, returnedResponse.getStatus());
        Error expected = errorHelper.getBadRequest();
        Error obtained = (Error) returnedResponse.getEntity();
        assertEquals(expected.getMessage(), obtained.getMessage());
    }


    @Test
    public void makeTransferShouldReturnResponse412ifTransfersAreDisabledOnEitherAccount() {
        //setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangementData.getArrangementWrapper(arrangementId).setMakeTransferAvailable(false);
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "1";
        String currencyCode = "GBP";

        //act
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);
        Response returnedResponse2 = arrangement.makeTransfer(request, response, targetArrangementId, arrangementId, amount, currencyCode);

        //verify
        assertEquals(412, returnedResponse.getStatus());
        assertEquals(412, returnedResponse2.getStatus());

    }

    @Test
    public void makeTransferShouldReturnResponse404IfArrangementIsDormant() {
        //SETUP
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);

        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "100.50";
        String currencyCode = "GBP";
        arrangementData.getArrangementWrapper(arrangementId).setState(ArrangementStateType.DORMANT);
        //ACT
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);
        //VERIFIY
        assertEquals(404, returnedResponse.getStatus());

    }


    @Test
    public void makeTransferShouldReturnResponse403WhenArrangementIsDisabled() {
        //setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        SessionHelper session = mock(SessionHelper.class);


        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        Mockito.when(session.getSession()).thenReturn(httpSession);
        String amount = "20";
        String currencyCode = "GBP";
        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        wrapper.setState(ArrangementStateType.DISABLED);
        //act
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);

        //verify
        assertEquals(403, returnedResponse.getStatus());
        assertEquals(ArrangementStateType.DISABLED, wrapper.getState());
    }

    @Test
    public void makeTransferShouldReturnResponse400WhenTransactionLimitExceeded() {
        //setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);


        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "1000000.5";
        String currencyCode = "GBP";

        ArrangementWrapper wrapper = arrangementData.getArrangementWrapper(arrangementId);
        wrapper.setAvailableBalance(objectGenerator.createCurrencyAmount("10000001", currencyCode));

        //act
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);

        //verify
        Error returned = (Error) returnedResponse.getEntity();
        assertEquals(errorHelper.getExceedsReceivingLimitError().getMessage(), returned.getMessage());
        assertEquals(400, returnedResponse.getStatus());

    }


    @Test
    public void makeTransferShouldReturnResponse201IfTransferIsMadeAndArrangementBalancesAreAdjustedApropriatelyAndTransactionsAddedToRespectiveArrangementsTransactionLists() {
        //setup
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        SessionHelper session = mock(SessionHelper.class);
        ArrangementsServiceImpl arrangement = new ArrangementsServiceImpl();
        arrangement.setSessionHelper(session);
        Mockito.when(session.getUserId(request)).thenReturn(userId);
        String amount = "100.5";
        String currencyCode = "GBP";
        int transactionCountOfArrangementPayingOut = transactions.getTransactionList(arrangementId).size();
        int transactionCountOfArrangementPaidInTo = transactions.getTransactionList(targetArrangementId).size();
        double DoubleAmount = Double.parseDouble(amount);
        double expectedPayingArrangementAvailableBalanceAfterTransfer = Double.parseDouble(arrangementData.getArrangementWrapper(arrangementId).getAvailableBalance().getAmount().toString()) - DoubleAmount;
        double expectedTargetArrangementAvailableBalanceAfterTransfer = Double.parseDouble(arrangementData.getArrangementWrapper(targetArrangementId).getAvailableBalance().getAmount().toString()) + DoubleAmount;
        double expectedPayingArrangementBalanceAfterTransfer = Double.parseDouble(arrangementData.getArrangementWrapper(arrangementId).getBalance().getAmount().toString()) - DoubleAmount;
        double expectedTargetArrangementBalanceAfterTransfer = Double.parseDouble(arrangementData.getArrangementWrapper(targetArrangementId).getBalance().getAmount().toString()) + DoubleAmount;
        //act
        Response returnedResponse = arrangement.makeTransfer(request, response, arrangementId, targetArrangementId, amount, currencyCode);
        double actualPayingArrangementAvailableBalanceAfterTransfer = Double.parseDouble(arrangementData.getArrangementWrapper(arrangementId).getAvailableBalance().getAmount().toString());
        double actualTargetArrangementAvailableBalanceAfterTransfer = Double.parseDouble(arrangementData.getArrangementWrapper(targetArrangementId).getAvailableBalance().getAmount().toString());
        double actualPayingArrangementBalanceAfterTransfer = Double.parseDouble(arrangementData.getArrangementWrapper(arrangementId).getBalance().getAmount().toString());
        double actualTargetArrangementBalanceAfterTransfer = Double.parseDouble(arrangementData.getArrangementWrapper(targetArrangementId).getBalance().getAmount().toString());
        //verify
        assertEquals(201, returnedResponse.getStatus());
        assertEquals(expectedTargetArrangementBalanceAfterTransfer, actualTargetArrangementBalanceAfterTransfer, 0.0000000001);
        assertEquals(expectedPayingArrangementBalanceAfterTransfer, actualPayingArrangementBalanceAfterTransfer, 0.0000000001);
        assertEquals(expectedTargetArrangementAvailableBalanceAfterTransfer, actualTargetArrangementAvailableBalanceAfterTransfer, 0.0000000001);
        assertEquals(expectedPayingArrangementAvailableBalanceAfterTransfer, actualPayingArrangementAvailableBalanceAfterTransfer, 0.0000000001);
        assertEquals(transactionCountOfArrangementPayingOut + 1, transactions.getTransactionList(arrangementId).size());
        assertEquals(transactionCountOfArrangementPaidInTo + 1, transactions.getTransactionList(targetArrangementId).size());

    }


}
