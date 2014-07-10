/**********************************************************************
 * This source code is the property of Lloyds TSB Group PLC.  
 *   
 * All Rights Reserved.   
 *  
 * Class Name: ProductArrangement  
 *   
 * Author(s): Jesper Madsen
 *  
 * Date: 17 Dec 2012
 *
 ***********************************************************************/
package com.lloydstsb.rest.v1.valueobjects.arrangement;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.lloydstsb.rest.common.types.DateAdapter;

/**
 * <p>
 * </p>
 * 
 * @author Jesper Madsen (ct026780)
 */
public abstract class ProductArrangement extends Arrangement {

	@NotNull
	private String id;

	@XmlJavaTypeAdapter(DateAdapter.class)
	private Date startDate;

	private boolean dormant;
	private boolean acceptsTransfers;
	private boolean makeTransferAvailable;
	private boolean makePaymentAvailable;
	private boolean createBeneficiaryAvailable;	
	private boolean otherBrand;
	private String otherBrandBankName;
	private boolean nonMigratedAccount;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the dormant
	 */
	public boolean isDormant() {
		return dormant;
	}

	/**
	 * @param dormant the dormant to set
	 */
	public void setDormant(boolean dormant) {
		this.dormant = dormant;
	}

	/**
	 * @return the acceptsTransfers
	 */
	public boolean isAcceptsTransfers() {
		return acceptsTransfers;
	}

	/**
	 * @param acceptsTransfers the acceptsTransfers to set
	 */
	public void setAcceptsTransfers(boolean acceptsTransfers) {
		this.acceptsTransfers = acceptsTransfers;
	}

	/**
	 * @return the makeTransferAvailable
	 */
	public boolean isMakeTransferAvailable() {
		return makeTransferAvailable;
	}

	/**
	 * @param makeTransferAvailable the makeTransferAvailable to set
	 */
	public void setMakeTransferAvailable(boolean makeTransferAvailable) {
		this.makeTransferAvailable = makeTransferAvailable;
	}

	/**
	 * @return the makePaymentAvailable
	 */
	public boolean isMakePaymentAvailable() {
		return makePaymentAvailable;
	}

	/**
	 * @param makePaymentAvailable the makePaymentAvailable to set
	 */
	public void setMakePaymentAvailable(boolean makePaymentAvailable) {
		this.makePaymentAvailable = makePaymentAvailable;
	}

	/**
	 * @return the nonMigratedAccount
	 */
	public boolean isNonMigratedAccount() {
		return nonMigratedAccount;
	}

	/**
	 * @param nonMigratedAccount the nonMigratedAccount to set
	 */
	public void setNonMigratedAccount(boolean nonMigratedAccount) {
		this.nonMigratedAccount = nonMigratedAccount;
	}

	/**
	 * @return the otherBrand
	 */
	public boolean isOtherBrand() {
		return otherBrand;
	}

	/**
	 * @param otherBrand the otherBrand to set
	 */
	public void setOtherBrand(boolean otherBrand) {
		this.otherBrand = otherBrand;
	}

	/**
	 * @return the otherBrandBankName
	 */
	public String getOtherBrandBankName() {
		return otherBrandBankName;
	}

	/**
	 * @param otherBrandBankName the otherBrandBankName to set
	 */
	public void setOtherBrandBankName(String otherBrandBankName) {
		this.otherBrandBankName = otherBrandBankName;
	}

	/**
	 * @return the createBeneficiaryAvailable
	 */
	public boolean isCreateBeneficiaryAvailable() {
		return createBeneficiaryAvailable;
	}

	/**
	 * @param createBeneficiaryAvailable the createBeneficiaryAvailable to set
	 */
	public void setCreateBeneficiaryAvailable(boolean createBeneficiaryAvailable) {
		this.createBeneficiaryAvailable = createBeneficiaryAvailable;
	}
}
