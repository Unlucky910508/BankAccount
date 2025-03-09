import java.util.*;

public class Application {
	
    public static void main( String args []) throws BankingException {
        Account a;
        double ret;
        
        a = new CheckingAccount("John Smith", 1500.0);
        
        try {
            ret = a.withdraw(100.00);
            System.out.println ("Account <" + a.name() + "> now has $" + ret + " balance\n");
        } catch (Exception e) {
           stdExceptionPrinting(e, a.balance());	
        }
        
        a = new CheckingAccount("John Smith", 1500.0);
        
        try {
            ret = a.withdraw(600.00);
            System.out.println ("Account <" + a.name() + "> now has $" + ret + " balance\n");
        } catch (Exception e) {
           stdExceptionPrinting(e, a.balance());	
        }
        
        /* put your own tests here ....... */
        
        // buid 4 different accounts in the same array
        Account[] testList;
        testList = new Account[4];

        //test date
        Calendar c = Calendar.getInstance();

        c.set(2022, 11, 9, 0, 0, 0);
        Date date1 = c.getTime();

        c.set(2022, 11, 10, 0, 0, 0);
        Date date2 = c.getTime();

        c.set(2022, 11, 11, 0, 0, 0);
        Date date3 = c.getTime();

        c.set(2022, 11, 12, 0, 0, 0);
        Date date4 = c.getTime();

        c.set(2023, 9, 13, 0, 0, 0);
        Date date5 = c.getTime();

        c.set(2024, 9, 14, 0, 0, 0);
        Date date6 = c.getTime();

        //////////////////////////////////// Checking account////////////////////////////////////////////////////////////////
            System.out.println("//////////////////////////////////// Checking account////////////////////////////////////////////////////////////////");
            testList[0] = new CheckingAccount("John Smith", 1500.0);
            // withdraw
            try {
                ret = testList[0].withdraw(600.00,  date1);
                System.out.println ("Account <" + testList[0].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[0].balance());	
            }

            try {
                ret = testList[0].withdraw(300.00,  date1);
                System.out.println ("Account <" + testList[0].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[0].balance());	
            }
            
        ////////////////////////////////////// Saving Account //////////////////////////////////////////////////////////////////
        System.out.println("////////////////////////////////////// Saving Account //////////////////////////////////////////////////////////////////");
            testList[1] = new SavingAccount("William Hurt", 40000.0);
            //withdraw 0
            try {
                ret = testList[1].withdraw(600.00,  date1);
                System.out.println ("Account <" + testList[1].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[1].balance());	
            }

            //withdraw 1
            try {
                ret = testList[1].withdraw(600.00,  date2);
                System.out.println ("Account <" + testList[1].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[1].balance());	
            }

            //withdraw 2
            try {
                ret = testList[1].withdraw(600.00,  date3);
                System.out.println ("Account <" + testList[1].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[1].balance());	
            }

            //withdraw 3 
            // transaction time > 3
            try {
                ret = testList[1].withdraw(600.00,  date4);
                System.out.println ("Account <" + testList[1].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[1].balance());	
            }

            // deposit
            // transaction time > 3
            try {
                ret = testList[1].deposit(600.00,  date5);
                System.out.println ("Account <" + testList[1].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[1].balance());	
            }

        ////////////////////////////////////// CD Account //////////////////////////////////////////////////////////////////
            System.out.println("////////////////////////////////////// CD Account //////////////////////////////////////////////////////////////////");
            testList[2] = new CDAccount("Woody Allison", 19000.0);
        
            //withdraw
            try {
                ret = testList[2].withdraw(600.00,  date1);
                System.out.println ("Account <" + testList[2].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[2].balance());	
            }

            //open CD
            try {
                ret = testList[2].open_CD(5000, date2, 12);
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[2].balance());
            }
            //deposit
            try {
                ret = testList[2].deposit(5000, date2);
                System.out.println ("Account <" + testList[2].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[2].balance());	
            }
            //withdraw
            try {
                ret = testList[2].withdraw(750.00,  date2);
                System.out.println ("Account <" + testList[2].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[2].balance());	
            }

            //withdraw after cd end
            try {
                ret = testList[2].withdraw(750.00,  date3);
                System.out.println ("Account <" + testList[2].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[2].balance());	
            }
        ////////////////////////////////////// Loan Account //////////////////////////////////////////////////////////////////
            System.out.println("////////////////////////////////////// Loan Account //////////////////////////////////////////////////////////////////");
            testList[3] = new LoanAccount("Judi Foster", -15000.0);
            //withdraw
            try {
                ret = testList[3].withdraw(600.00,  date4);
                System.out.println ("Account <" + testList[3].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[3].balance());	
            }
            //deposit
            try {
                ret = testList[3].deposit(600.00,  date5);
                System.out.println ("Account <" + testList[3].name() + "> now has $" + ret + " balance\n");
            } catch (Exception e) {
                stdExceptionPrinting(e, testList[3].balance());	
            }
            
        // compute interest for all accounts

        System.out.println("///////////////////////////////////////// Interest Test////////////////////////////////////////////////////////////////////");
        for (int count = 0; count < testList.length; count++) {
            double newBalance = testList[count].computeInterest(date4);
            System.out.println ("Account <" + a.name() + "> now has $" + newBalance + " balance\n");
        }


        /* if your implementaion is correct, you can do the following with polymorphic array accountList*/

        System.out.println("////////////////////////////////////////////////// Given Test////////////////////////////////////////////////");
            Account[] accountList;
            
            accountList = new Account[4];
            
            // buid 4 different accounts in the same array
            accountList[0] = new CheckingAccount("John Smith", 1500.0);
            accountList[1] = new SavingAccount("William Hurt", 1200.0);
            accountList[2] = new CDAccount("Woody Allison", 1000.0);
            accountList[3] = new LoanAccount("Judi Foster", -1500.0);
            
            // compute interest for all accounts
            for (int count = 0; count < accountList.length; count++) {
                double newBalance = accountList[count].computeInterest();
                System.out.println ("Account <" + a.name() + "> now has $" + newBalance + " balance\n");
            }
        
    }
    
    static void stdExceptionPrinting(Exception e, double balance) {
        System.out.println("EXCEPTION: Banking system throws a " + e.getClass() +
                           " with message: \n\t" +
                           "MESSAGE: " + e.getMessage());
        System.out.println("\tAccount balance remains $" + balance + "\n");
    }
}          