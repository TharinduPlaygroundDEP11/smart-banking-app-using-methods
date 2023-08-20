import java.util.Scanner;

public class SmartBankingApp {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static String[][] bankAccounts = new String[0][];
    private static final String CLEAR = "\033[H\033[2J";
    private static final String COLOR_RED_BOLD = "\033[31;1m";
    private static final String COLOR_YELLOW_BOLD = "\033[33;1m";
    private static final String COLOR_BLUE_BOLD = "\033[34;1m";
    private static final String RESET = "\033[0;0m";
    private static final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
    private static final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_YELLOW_BOLD, "%s", RESET);
    public static void main(String[] args) {
        dashboard("Welcome to Smart Banking App");
        
    }

    public static void dashboard(String title) {
        appTitle(title);

        System.out.println("\t[1] Open New Account\n\t[2] Deposit Money\n\t[3] Withdraw Money\n\t[4] Transfer Money\n\t[5] Check Account Balance\n\t[6] Drop Existing Account\n\t[7] Exit");
        System.out.print("\nEnter an option to continue > ");
        int option = SCANNER.nextInt();
        SCANNER.nextLine();

        switch(option) {
            case 1: openAccount("Open New Account"); break;
            case 2: depositMoney("Deposit Money"); break;
            case 3: withdrawMoney("Withdraw Money"); break;
            case 4: transferMoney("Transfer Money"); break;
            case 5: checkBalance("Check Account Balance");break;
            case 6: dropAccount("Delete Existing Account"); break;
            case 7: System.exit(0); break;
            default: break;
        }
    }

    public static void openAccount(String title) {
        appTitle(title);
        boolean valid = true;
        String name;
        double amount;

        int number = (int) (Math.random() * Math.pow(3, 10));
        String formattedNumber = String.format("SDB-%05d", number);
        System.out.printf("\tNew Account Number : %s \n", formattedNumber);
        
        do {
            System.out.print("\n\tEnter Account Holder's Name : ");
            name = SCANNER.nextLine().strip();
            valid = nameValidation(name);
        } while (!valid);

        do {
            valid = true;
            System.out.print("\n\tEnter Initial Deposit Amount : Rs.");
            amount = SCANNER.nextDouble();
            SCANNER.nextLine();
        
            if (amount < 5000) {
                System.out.printf(ERROR_MSG, "Insufficient Initial Amount");
                valid = false;
                continue;
            }
        } while (!valid);
        String[][] newBankAccount = new String[bankAccounts.length + 1][3];
                    
        for (int i = 0; i < bankAccounts.length; i++) {
            newBankAccount[i] = bankAccounts[i];
        }

        newBankAccount[newBankAccount.length-1][0] = formattedNumber;
        newBankAccount[newBankAccount.length-1][1] = name;
        newBankAccount[newBankAccount.length-1][2] = Double.toString(amount);
        
        bankAccounts = newBankAccount;
        
        System.out.printf(SUCCESS_MSG, String.format("\n\t%s : %s Added Successfully!", formattedNumber, name));

        System.out.print("\nDo you want to go back? (Y/n) : ");
        if(SCANNER.nextLine().strip().toUpperCase().equals("Y")){
            dashboard("Welcome to Smart Banking App");
        } else openAccount("Open New Account");
    }


    public static void depositMoney(String title) {
        appTitle(title);
        String accNumber;
        int index;
        do{
            System.out.print("\tEnter the Account Number : ");
            accNumber = SCANNER.nextLine().toUpperCase().strip();
            index = accountNumberSearch(accNumber);
        } while (index == -1);
        
        System.out.printf("\n\tAccount Holder : %s\n",bankAccounts[index][1]);
        System.out.printf("\tCurrent Balance : Rs.%,.2f\n",Double.valueOf(bankAccounts[index][2]));

        boolean valid;
        double amount;
        do {
            valid = true;
            System.out.print("\n\tEnter Your Deposit Amount : ");
            
            amount = SCANNER.nextDouble();
            SCANNER.nextLine();
                        
            if (amount < 500) {
                System.out.printf(ERROR_MSG, "Insufficient Amount, Should be more than Rs.500/=");
                valid = false;
                continue;
            }
        } while (!valid);

        double currentBalance = Double.valueOf(bankAccounts[index][2]);
        double newbalance = currentBalance + amount;
        bankAccounts[index][2] = String.valueOf(newbalance);
        System.out.printf("\n\tNew Balance : Rs.%,.2f\n",Double.valueOf(bankAccounts[index][2]));
        System.out.println();
        System.out.printf(SUCCESS_MSG, String.format("%s : %s Deposited Successfully!", bankAccounts[index][0], bankAccounts[index][1]));

        System.out.print("\n\tDo you want to continue deposit (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            depositMoney("Deposit Money");
        } else {
            dashboard("Welcome to Smart Banking App");
        }
    }


    public static void withdrawMoney(String title) {
        appTitle(title);
        String accNumber;
        int index;
        do{
            System.out.print("\tEnter the Account Number : ");
            accNumber = SCANNER.nextLine().toUpperCase().strip();
            index = accountNumberSearch(accNumber);
        } while (index == -1);

        System.out.printf("\n\tAccount Holder : %s\n",bankAccounts[index][1]);
        System.out.printf("\tCurrent Balance : Rs.%,.2f\n",Double.valueOf(bankAccounts[index][2]));

        boolean valid;
        double amount;
        double currentBalance = Double.valueOf(bankAccounts[index][2]);
        do {
            valid = true;
            System.out.print("\n\tEnter Your Withdraw Amount : ");
            amount = SCANNER.nextDouble();
            SCANNER.nextLine();
            
            if(amount < 100) {
                System.out.printf(ERROR_MSG, "Invalid withdrawal, withdraw should be more than Rs.100/=");
                valid = false;
                continue;
            }
            if((currentBalance - amount) < 500){
                System.out.printf(ERROR_MSG, "Invalid withdrawal, Remaining balance should be more than Rs.500/=");
                valid = false;
                continue;
            }
        } while (!valid);

        double newbalance = currentBalance - amount;
        bankAccounts[index][2] = String.valueOf(newbalance);
        System.out.printf("\n\tNew Balance : Rs.%,.2f\n",Double.valueOf(bankAccounts[index][2]));
        System.out.println();
        System.out.printf(SUCCESS_MSG, String.format("%s : %s Withdrawed Successfully!", bankAccounts[index][0], bankAccounts[index][1]));

        System.out.print("\n\tDo you want to continue withdraw (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            withdrawMoney("Withdraw Money");
        } else {
            dashboard("Welcome to Smart Banking App");
        }
    }


    public static void transferMoney(String title) {
        appTitle(title);
        String fromAccountNumber;
        int indexFrom;
        do{
            System.out.print("\tEnter the From Account Number : ");
            fromAccountNumber = SCANNER.nextLine().toUpperCase().strip();
            indexFrom = accountNumberSearch(fromAccountNumber);
        } while (indexFrom == -1);

        String toAccountNumber;
        int indexTo;
        do{
            System.out.print("\tEnter the To Account Number : ");
            toAccountNumber = SCANNER.nextLine().toUpperCase().strip();
            indexTo = accountNumberSearch(toAccountNumber);
        } while (indexTo == -1);

        double fromAccBalance = Double.valueOf(bankAccounts[indexFrom][2]);
        double toAccBalance = Double.valueOf(bankAccounts[indexTo][2]);

        System.out.printf("\n\tFrom Account Holder : %s\n",bankAccounts[indexFrom][1]);
        System.out.printf("\tFrom Account Current Balance : Rs.%,.2f\n",fromAccBalance);
        System.out.println();
        System.out.printf("\n\tTo Account Holder : %s\n",bankAccounts[indexTo][2]);
        System.out.printf("\tTo Account Current Balance : Rs.%,.2f\n",toAccBalance);

        boolean valid;
        double amount;
        do {
            valid = true;
            System.out.print("\n\tEnter Your Transfer Amount : ");
            amount = SCANNER.nextDouble();
            SCANNER.nextLine();
            
            if(amount < 100) {
                System.out.printf(ERROR_MSG, "Invalid transfer, transfer should be more than Rs.100/=");
                valid = false;
                continue;
            }
            if(((fromAccBalance - amount) - (0.02 * amount)) < 500){
                System.out.printf(ERROR_MSG, "Invalid transfer, Remaining balance should be more than Rs.500/=");
                valid = false;
                continue;
            }
        } while (!valid);

        double newFromAccBalance = fromAccBalance - (amount + (0.02 * amount));
        double newToAccBalance = toAccBalance + amount;
        
        bankAccounts[indexFrom][2] = String.valueOf(newFromAccBalance);
        bankAccounts[indexTo][2] = String.valueOf(newToAccBalance);

        System.out.printf("\n\tFrom Account New Balance : Rs.%,.2f\n",newFromAccBalance);
        System.out.printf("\tTo Account New Balance : Rs.%,.2f\n",newToAccBalance);
        System.out.println();
        System.out.printf(SUCCESS_MSG, "Money Transfer Successfull! (2% bank chargers deducted)");
        
        System.out.print("\n\tDo you want to continue transfer (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            transferMoney("Transfer Money");
        }else {
            dashboard("Welcome to Smart Banking App");
        }
        
    }
    

    public static void checkBalance(String title) {
        appTitle(title);
        String accNumber;
        int index;
        do{
            System.out.print("\tEnter the Account Number to check balance : ");
            accNumber = SCANNER.nextLine().toUpperCase().strip();
            index = accountNumberSearch(accNumber);
        } while (index == -1);

        double accBalance = Double.valueOf(bankAccounts[index][2]);

        System.out.printf("\n\t%sAccount Holder : %s\n",COLOR_BLUE_BOLD,bankAccounts[index][1]);
        System.out.printf("\tAccount Current Balance : Rs.%,.2f\n",accBalance);
        System.out.println();
        System.out.printf("\tAvailable Balance to Withdraw : Rs.%,.2f%s\n", (accBalance - 500),RESET);
                    
        System.out.print("\n\tDo you want to continue checking balance (Y/n)? ");
        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            checkBalance("Check Account Balance");
        } else {
            dashboard("Welcome to Smart Banking App");
        }
    }


    public static void dropAccount(String title) {
        appTitle(title);
        String accNumber;
        int index;
        do{
            System.out.print("\tEnter the Account Number to Delete account : ");
            accNumber = SCANNER.nextLine().toUpperCase().strip();
            index = accountNumberSearch(accNumber);
        } while (index == -1);

        double accBalance = Double.valueOf(bankAccounts[index][2]);
        String[][] dropedBankAccounts = new String[bankAccounts.length - 1][3];

        System.out.printf("\n\tAccount Holder : %s\n",bankAccounts[index][1]);
        System.out.printf("\tAccount Balance : Rs.%,.2f\n",accBalance);
        System.out.println();
        System.out.print("\tAre you sure want to delete this account? (Y/n) ");

        if (SCANNER.nextLine().strip().toUpperCase().equals("Y")) {
            for (int i = 0; i < bankAccounts.length; i++) {
                if (i < index){
                    dropedBankAccounts[i] = bankAccounts[i];
                }else if (i == index){
                    continue;
                }else{
                    dropedBankAccounts[i-1] = bankAccounts[i];
                }
            }
            bankAccounts = dropedBankAccounts;

            System.out.println();
            System.out.printf(SUCCESS_MSG, "Account has been deleted successfully");
            System.out.print("\n\tDo you want to continue delete (Y/n)? ");
            if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                dropAccount("Delete Existing Account");
            }else {
                dashboard("Welcome to Smart Banking App");
            } 
        } else {
            dashboard("Welcome to Smart Banking App");
        }
    }
    
    
    public static void appTitle(String title) {
        final String APP_TITLE = String.format("%s", title);

        System.out.println(CLEAR);
        System.out.println("-".repeat(70));
        System.out.println("\033[32;1m ".repeat((70 - (APP_TITLE.length()))/2).concat(APP_TITLE).concat("\033[32;0m"));
        System.out.println("-".repeat(70));

    }

    
    public static boolean nameValidation(String name) {
        if (name.isBlank()){
            System.out.printf(ERROR_MSG, "Name can't be empty");
            return false;
        }
        for (int i = 0; i < name.length(); i++) {
            if (!(Character.isLetter(name.charAt(i)) || 
                Character.isSpaceChar(name.charAt(i))) ) {
                System.out.printf(ERROR_MSG, "Invalid Name");
                return false;
                
            }
        }
        return true;
    }

    public static int accountNumberSearch(String accNumber) {
        int index = -1;
        
        if (accNumber.isBlank()){
            System.out.printf(ERROR_MSG, "Account Number can't be empty");
            return index;
        }else if (!accNumber.startsWith("SDB-") || accNumber.length() < 5){
            System.out.printf(ERROR_MSG, "Invalid Account Number format");
            return index;
        }else{
            String number = accNumber.substring(4);
            for (int i = 0; i < number.length(); i++) {
                if (!Character.isDigit(number.charAt(i))){
                    System.out.printf(ERROR_MSG, "Invalid Account Number format");
                    return index;
                }
            }
            boolean exists = false;
            for (int i = 0; i < bankAccounts.length; i++) {
                if (bankAccounts[i][0].equals(accNumber)){
                    index = i;
                    exists = true;
                    break;
                }
            }
            if (!exists){
                System.out.printf(ERROR_MSG, "Account Number does not exist");
                System.out.print("\n\tDo you want to try again? (Y/n)");
                if (SCANNER.nextLine().strip().toUpperCase().equals("Y")){
                    return index;
                }else dashboard("Welcome to Smart Banking App");
            }
            
            return index;
        }
    }
}