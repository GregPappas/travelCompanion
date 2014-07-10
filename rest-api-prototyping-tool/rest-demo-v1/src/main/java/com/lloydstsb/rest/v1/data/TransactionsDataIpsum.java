
package com.lloydstsb.rest.v1.data;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import au.com.bytecode.opencsv.CSVReader;

import com.lloydstsb.rest.v1.helpers.ArrangementHelper;
import com.lloydstsb.rest.v1.helpers.MiscHelper;
import com.lloydstsb.rest.v1.helpers.ObjectGenerator;
import com.lloydstsb.rest.v1.valueobjects.CurrencyAmount;
import com.lloydstsb.rest.v1.valueobjects.Transaction;
import com.lloydstsb.rest.v1.valueobjects.TransactionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransactionsDataIpsum {
    private Logger logger = LoggerFactory.getLogger(TransactionsDataIpsum.class);
    private ArrangementHelper arrangementHelper = new ArrangementHelper();
	private ObjectGenerator objectGenerator = new ObjectGenerator();
	private MiscHelper miscHelper = new MiscHelper();
	private String sortCode = "123456"; 
	private String currentArrangement;
	public ArrayList<Transaction> getTransactionList (String arrangementId)
	{
		ArrayList<Transaction> transactionList = PersistenceContainer.getInstance().getTransactionMap().get(arrangementId);
		return transactionList;
	}
	
	public ArrayList<Transaction> getTransactionListFilteredByDate (String arrangementId, String dateFrom, String dateTo)
	{
		ArrayList<Transaction> transactionList = PersistenceContainer.getInstance().getTransactionMap().get(arrangementId);
		ArrayList<Transaction> filteredTransactionList = filterTransactionsByDate(transactionList, dateFrom, dateTo);
		return filteredTransactionList;
	}
	
	public ArrayList<Transaction> getTransactionListFilteredByName(String arrangementId, String query)
	{
		ArrayList<Transaction> transactionList = PersistenceContainer.getInstance().getTransactionMap().get(arrangementId);
		ArrayList<Transaction> filteredTransactionList = filterTransactionsByName(transactionList, query);
		return filteredTransactionList;
	}
	
	public ArrayList<Transaction> filterTransactionsByName(ArrayList<Transaction> unsortedList, String query) 
	{
		ArrayList<Transaction> sortedList = new ArrayList<Transaction>();
		for(Transaction transaction : unsortedList)
		{
			String description = transaction.getDescription();
			description = description.toUpperCase();
			if(description.contains(query.toUpperCase()))
			{
				sortedList.add(transaction);
			}
		}
		return sortedList;
	}
	
	public ArrayList<Transaction> filterTransactionsByDate(ArrayList<Transaction> transactions, String dateFrom, String dateTo) 
	{
		ArrayList<Transaction> sortedList = new ArrayList<Transaction>();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date dateStart;
		Date dateEnd;
		try {
			dateStart = sdf.parse(dateFrom);
			dateEnd = sdf.parse(dateTo);
		} catch (ParseException e) {throw new IllegalArgumentException("The date supplied is invalid",e);}
		for(Transaction transaction : transactions)
		{
			Date transDate = transaction.getDate();
			if((transDate.after(dateStart) && transDate.before(dateEnd))||sdf.format(transDate).equals(dateFrom)||sdf.format(transDate).equals(dateTo))
			{
				sortedList.add(transaction);
			}
		}
		
		return sortedList;
	}
	
	public HashMap<String , ArrayList<Transaction>> getTransactionMap()
	{
		return PersistenceContainer.getInstance().getTransactionMap();
	}
	
	public String getNewTransactionId()
	{
		return PersistenceContainer.getInstance().getNewTransactionID();
	}
	
	public void readAndParseCSVFile() throws IOException {
			CSVReader reader;
			HashMap<String, ArrayList<Transaction>> customersTransaction = null;
            URL transactionsFile = this.getClass().getClassLoader().getResource("transactions.csv");
            if(transactionsFile != null)
            {
                logger.info("Loading transactions from: "+ transactionsFile);
            }
            else
            {
                throw new IllegalStateException("Could not find transactions.csv file in classpath.");
            }
			InputStream input = transactionsFile.openStream();
			Charset cs = Charset.forName("UTF8");
			InputStreamReader isr = new InputStreamReader(input, cs);
			reader = new CSVReader(isr); 
			String[] nextLine;
			customersTransaction=new HashMap<String, ArrayList<Transaction>>() ;
			int lineCounter =1;
			try {
				while ((nextLine = reader.readNext()) != null) 
				{
					if(!nextLine[0].equals("ACCOUNT NUMBER"))
					{
						nextLine[0] += this.sortCode;

						customersTransaction = fillNewTransaction(nextLine, customersTransaction, lineCounter);
					}
					lineCounter++;
					
				}
				reader.close();	
				} catch (IOException e) 
				{
					throw new IllegalStateException("Unable to read file.");
				}

			PersistenceContainer.getInstance().setTransactionMap(customersTransaction);
	}
	
	public HashMap<String, ArrayList<Transaction>> fillNewTransaction(String[] nextLine, HashMap<String, ArrayList<Transaction>> customersTransaction, int lineCounter)
	{
		Transaction transaction;
		try{
				miscHelper.validateArrangementId(nextLine[0]);
				transaction=readLineFromReader(nextLine);
			}
		catch(IllegalArgumentException e)
			{
				throw new IllegalStateException("Illegal argument found on line " + lineCounter,e);
			}
		
		if (customersTransaction.containsKey(nextLine[0]))//if contains the arrangement id we retrieve the list and add the new transaction
		{
			customersTransaction.get(nextLine[0]).add(transaction);
		}
		else // we create the new list if the arrangementId doesn't exist 
		{
			ArrayList<Transaction> transactionsList =new ArrayList<Transaction>();
			transactionsList.add(transaction);
			customersTransaction.put(nextLine[0], transactionsList);
		}
		
		return customersTransaction;
	}	
	
	
	public Transaction readLineFromReader(String[] nextLine)
	{
		// nextLine[] is an array of values from the line
		Transaction transaction=new Transaction();
		miscHelper.validateNextLineTransactionDataIpsum(nextLine);
		transaction.setId(nextLine[6]);
		transaction.setAmount(objectGenerator.createCurrencyAmount(nextLine[2],nextLine[3]));
		transaction.setDescription(nextLine[5]);
			CurrencyAmount runningBalance = objectGenerator.createCurrencyAmount(nextLine[4],nextLine[3]);
		transaction.setRunningBalance(runningBalance);
		transaction.setType(TransactionType.from(miscHelper.generateType(nextLine[2])));
		this.currentArrangement = nextLine[0];		
		arrangementHelper.updateBalance(this.currentArrangement, runningBalance);
	
			Date date;
			try {
				date = new SimpleDateFormat("dd/MM/yyyy").parse(nextLine[1]);
				transaction.setDate(date);
			} catch (ParseException e) {
				throw new IllegalStateException();
			}
		return transaction;
	}

	public BigDecimal getTransactionLimitAmount() 
	{
		return PersistenceContainer.getInstance().getTransactionLimitAmount();
	}


	
	
	
}