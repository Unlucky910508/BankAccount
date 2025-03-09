
/*****************************************************************
    CS4001301 Programming Languages                   
    
    Programming Assignment #1
    
    Java programming using subtype, subclass, and exception handling
    
    To compile: %> javac Application.java
    
    To execute: %> java Application

******************************************************************/

import java.util.*;

class BankingException extends Exception {
    BankingException () { super(); }
    BankingException (String s) { super(s); }
} 

interface BasicAccount {
    String name();
    double balance();	
}

interface WithdrawableAccount extends BasicAccount {
    double withdraw(double amount) throws BankingException;	
}

interface DepositableAccount extends BasicAccount {
    double deposit(double amount) throws BankingException;	
}

interface InterestableAccount extends BasicAccount {
    double computeInterest() throws BankingException;	
}

interface FullFunctionalAccount extends WithdrawableAccount,
                                        DepositableAccount,
                                        InterestableAccount {
}

public abstract class Account {
	
    // protected variables to store commom attributes for every bank accounts	
    
    protected String accountName;
    protected double accountBalance;
    protected double accountInterestRate;
    protected Date openDate;
    protected Date lastInterestDate;
    
    // public methods for every bank accounts
    public String name() {
    	return(accountName);	
    }	
    
    public double balance() {
        return(accountBalance);
    }
    
    abstract double deposit(double amount, Date depositDate) throws BankingException;

    public double deposit(double amount) throws BankingException{
        accountBalance += amount;
        return(accountBalance);
    } 
    
    abstract double withdraw(double amount, Date withdrawDate) throws BankingException;
    
    public double withdraw(double amount) throws BankingException {
        Date withdrawDate = new Date();
        return(withdraw(amount, withdrawDate));
    }
    
    abstract double computeInterest(Date interestDate) throws BankingException;
    
    public double computeInterest() throws BankingException {
        Date interestDate = new Date();
        return(computeInterest(interestDate));
    }

    public double open_CD(double CD_num, Date startDate, int CD_month) throws BankingException {
        return 0;
    }
}

/*
 *  Derived class: CheckingAccount
 *
 *  Description:
 *      Interest is computed daily; there's no fee for
 *      withdraw; there is a minimum balance of $1000.
 */
                          
class CheckingAccount extends Account implements FullFunctionalAccount {

    CheckingAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;	
    }
    
    CheckingAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;	
    }	
    
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
    // minimum balance is 1000, raise exception if violated
        if ((accountBalance  - amount) < 1000) {
            throw new BankingException ("Underfraft from checking account name:" + accountName);
        } 
        accountBalance -= amount;	
        return(accountBalance);
    }
    
    public double computeInterest (Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException ("Invalid date to compute interest for account name" +
                                        accountName);                            	
        }
        
        int numberOfDays = (int) ((interestDate.getTime() 
                                   - lastInterestDate.getTime())
                                   / 86400000.0);
        System.out.println("Number of days since last interest is " + numberOfDays);
        double interestEarned = (double) numberOfDays / 365.0 *
                                      accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + (double)Math.round(interestEarned * 100) / 100.0); 
         if(interestEarned  != 0){
            lastInterestDate = interestDate;
        }
        accountBalance += interestEarned;
        accountBalance *= 100;
        accountBalance = Math.round(accountBalance) / 100.0;
        return(accountBalance);
    }

    public double deposit(double amount, Date depositDate) throws BankingException {
        accountBalance += amount;
        return(accountBalance);
    }  	
}

/*
 *  Derived class: SavingAccount
 *
 *  Description:
 *      monthly interest; fee of $1 for every transaction, except
 *      the first three per month are free; no minimum balance.
 */

class SavingAccount extends Account implements FullFunctionalAccount {
    // check the transaction time
    private int monthlyTransactionTime;
    private Date monthlyStartDate;
    private Date monthlyEndDate;

    SavingAccount(String s, double firstDeposit) {
        Calendar tmp = Calendar.getInstance();
        monthlyTransactionTime = 0;
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();

        // set the date to the first day to the month
        tmp.setTime(openDate);
        tmp.set(Calendar.DATE, 1);
        monthlyStartDate = tmp.getTime();

        // set the date to the last day to the month
        tmp.add(Calendar.MONTH, 1);
        monthlyEndDate = tmp.getTime();

        lastInterestDate = openDate;
    }
    
    SavingAccount(String s, double firstDeposit, Date firstDate) {
        Calendar tmp = Calendar.getInstance();
        monthlyTransactionTime = 0;
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();

        // set the date to the first day to the month
        tmp.setTime(openDate);
        tmp.set(Calendar.DATE, 1);
        tmp.set(Calendar.HOUR, 0);
        tmp.set(Calendar.MINUTE, 0);
        tmp.set(Calendar.SECOND, 0);
        monthlyStartDate = tmp.getTime();

        // set the date to the last day to the month
        tmp.add(Calendar.MONTH, 1);
        monthlyEndDate = tmp.getTime();
        
        lastInterestDate = openDate;	
    }
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
        //check withdraw date
        if(monthlyStartDate.before(withdrawDate)&&monthlyEndDate.after(withdrawDate)){
            monthlyTransactionTime++;
        }
        else{
            Calendar tmp = Calendar.getInstance();
            // set the date to the first day to the month
            tmp.setTime(withdrawDate);
            tmp.set(Calendar.DATE, 1);
            tmp.set(Calendar.HOUR, 0);
            tmp.set(Calendar.MINUTE, 0);
            tmp.set(Calendar.SECOND, 0);
            monthlyStartDate = tmp.getTime();

            // set the date to the last day to the month
            tmp.add(Calendar.MONTH, 1);
            monthlyEndDate = tmp.getTime();

            // reset monthly transaction time
            monthlyTransactionTime = 1;
        }

        // check if monthly withdraw time < 3
        if(monthlyTransactionTime > 3){
            accountBalance -= 1;
        }

        accountBalance -= amount;
        return(accountBalance);
    }
    
    public double computeInterest (Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException ("Invalid date to compute interest for account name" +
                                        accountName);                            	
        }
        
        int numberOfMonths = (int) ((interestDate.getTime() 
                                   - lastInterestDate.getTime())
                                   / 86400000.0 / 30.0);
        System.out.println("Number of months since last interest is " + numberOfMonths);
        double interestEarned = (double) numberOfMonths / 12.0 *
                                      accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + (double)Math.round(interestEarned * 100) / 100.0); 
        if(interestEarned  != 0){
            lastInterestDate = interestDate;
        }
        
        accountBalance += interestEarned;
        accountBalance *= 100;
        accountBalance = Math.round(accountBalance) / 100.0;
        return(accountBalance);                            
    }

    
    public double deposit(double amount, Date depositDate) throws BankingException {
        //check deposit date
        if(monthlyStartDate.before(depositDate)&&monthlyEndDate.after(depositDate)){
            monthlyTransactionTime++;
        }
        else{
            Calendar tmp = Calendar.getInstance();
            // set the date to the first day to the month
            tmp.setTime(depositDate);
            tmp.set(Calendar.DATE, 1);
            tmp.set(Calendar.HOUR, 0);
            tmp.set(Calendar.MINUTE, 0);
            tmp.set(Calendar.SECOND, 0);
            monthlyStartDate = tmp.getTime();

            // set the date to the last day to the month
            tmp.add(Calendar.MONTH, 1);
            monthlyEndDate = tmp.getTime();

            // reset monthly transaction time
            monthlyTransactionTime = 1;
        }

        // check if monthly deposit time < 3
        if(monthlyTransactionTime >= 3){
            accountBalance -= 1;
        }
        accountBalance += amount;
        return(accountBalance);
    }  	
}

/*
 *  Derived class: CDAccount
 *
 *  Description:
 *      monthly interest; fixed amount and duration (e.g., you can open
 *      1 12-month CD for $5000; for the next 12 months you can't deposit
 *      anything and withdrawals cost a  $250 fee); at the end of the 
 *      duration the interest payments stop and you can withdraw w/o fee.
 */

class CDAccount extends Account implements FullFunctionalAccount {
    public double CD_amount;
    public Date CD_startDate;
    public Date CD_endDate;
    public int CD_continueDays;
    CDAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;	
        CD_startDate = new Date();
        CD_endDate = new Date();
    }
    
    CDAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;
        CD_startDate = new Date();
        CD_endDate = new Date();
    }
    public double open_CD(double CD_num, Date startDate, int CD_month) throws BankingException {
        // test if withdraw during CD
        if (startDate.after(CD_startDate) && startDate.before(CD_endDate)) {
            throw new BankingException("Open CD error: Cannot open a new CD during CD time. Account name: " + accountName);
        } 

        CD_amount = CD_num;
        CD_startDate = startDate;
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.MONTH, CD_month);
        CD_endDate  = c.getTime();
        //System.out.println("CDDDDDDDDDDDDDDDD     " + CD_endDate + "\n////////////////////////////////////////////");
        return 0;
    }
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
        // test if withdraw during CD
        if (withdrawDate.after(CD_startDate) && withdrawDate.before(CD_endDate)) {
            System.out.println("Warning: withdraw during CD time, cost 250 transaction fee.");
            accountBalance -= 250;
        } 

        accountBalance -= amount;	
        return(accountBalance); 	                                        	
    }
    public double deposit(double amount, Date depositDate) throws BankingException {
        // test if deposit during CD
        if (depositDate.after(CD_startDate) && depositDate.before(CD_endDate)) {
            throw new BankingException("Deposit error: Cannot deposit during CD time. Account name: " + accountName);
        }
        accountBalance += amount;
        return(accountBalance);
    }
    public double computeInterest (Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException ("Invalid date to compute interest for account name" +
                                        accountName);                            	
        }
        if(interestDate.after(CD_endDate)){
            interestDate = CD_endDate;
        }
        int numberOfMonths = (int) ((interestDate.getTime() 
                                   - lastInterestDate.getTime())
                                   / 86400000.0 / 30);
        System.out.println("Number of months since last interest is " + numberOfMonths);
        double interestEarned = (double) numberOfMonths / 12.0 *
                                      accountInterestRate * CD_amount;
        System.out.println("Interest earned is " + (double)Math.round(interestEarned * 100) / 100.0); 
         if(interestEarned  != 0){
            lastInterestDate = interestDate;
        }
        accountBalance += interestEarned;
        accountBalance *= 100;
        accountBalance = Math.round(accountBalance) / 100.0;
        return(accountBalance);                            
    }  	
}

/*
 *  Derived class: LoanAccount
 *
 *  Description:
 *      like a saving account, but the balance is "negative" (you owe
 *      the bank money, so a deposit will reduce the amount of the loan);
 *      you can't withdraw (i.e., loan more money) but of course you can 
 *      deposit (i.e., pay off part of the loan).
 */

class LoanAccount extends Account implements DepositableAccount, InterestableAccount {

    LoanAccount(String s, double firstDeposit) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = new Date();
        lastInterestDate = openDate;	
    }
    
    LoanAccount(String s, double firstDeposit, Date firstDate) {
        accountName = s;
        accountBalance = firstDeposit;
        accountInterestRate = 0.12;
        openDate = firstDate;
        lastInterestDate = openDate;	
    }	
    
    public double withdraw(double amount, Date withdrawDate) throws BankingException {
        // just throw exception
        throw new BankingException ("Withdraw error: It's a loan account. Acount name: " +
                                         accountName);                                  	
    }

    public double deposit(double amount, Date depositDate) throws BankingException {
        if (amount < 0) {
            throw new BankingException ("Amount error: Amount < 0. Account name:" +
                                             accountName);
        } else {
            accountBalance += amount;	
            return(accountBalance); 	
        }                                        	
    }
    
    public double computeInterest (Date interestDate) throws BankingException {
        if (interestDate.before(lastInterestDate)) {
            throw new BankingException ("Invalid date to compute interest for account name" +
                                        accountName);                            	
        }
        
        int numberOfDays = (int) ((interestDate.getTime() 
                                   - lastInterestDate.getTime())
                                   / 86400000.0);
        System.out.println("Number of days since last interest is " + numberOfDays);
        double interestEarned = (double) numberOfDays / 365.0 *
                                      accountInterestRate * accountBalance;
        System.out.println("Interest earned is " + (double)Math.round(interestEarned * 100) / 100.0); 
         if(interestEarned  != 0){
            lastInterestDate = interestDate;
        }
        accountBalance += interestEarned;
        accountBalance *= 100;
        accountBalance = Math.round(accountBalance) / 100.0;
        return(accountBalance);                            
    }  	
}