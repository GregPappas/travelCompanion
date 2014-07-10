package com.lloydstsb.rest.v1.data;

import com.lloydstsb.rest.v1.valueobjects.Customer;

/**
 * Created by gregorypappas on 10/07/2014.
 */
public class ExchangeLockCustomer extends Customer {

    private boolean accountFrozen = false;

    public void toggleAccountFrozen()
    {
        this.accountFrozen = !accountFrozen;
    }

    public boolean getAccountFrozen()
    {
        return accountFrozen;
    }

}
