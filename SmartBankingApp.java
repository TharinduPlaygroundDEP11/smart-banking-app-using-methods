import java.util.Arrays;
import java.util.Scanner;

public class SmartBankingApp {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static String[][] bankAccounts = new String[0][];
    private static final String CLEAR = "\033[H\033[2J";
    private static final String COLOR_RED_BOLD = "\033[31;1m";
    private static final String COLOR_GREEN_BOLD = "\033[33;1m";
    private static final String RESET = "\033[0;0m";
    private static final String ERROR_MSG = String.format("\t%s%s%s\n", COLOR_RED_BOLD, "%s", RESET);
    private static final String SUCCESS_MSG = String.format("\t%s%s%s\n", COLOR_GREEN_BOLD, "%s", RESET);
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
            case 6: dropAccount("Drop Account"); break;
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

        for (int i = 0; i < bankAccounts.length; i++) {
            System.out.println(Arrays.toString(bankAccounts[i]));
        }

        System.out.print("\nDo you want to go back? (Y/n) : ");
        if(SCANNER.nextLine().strip().toUpperCase().equals("Y")){
            dashboard("Welcome to Smart Banking App");
        } else openAccount("Open New Account");
    }


    public static void depositMoney(String title) {
        appTitle(title);
    }


    public static void withdrawMoney(String title) {
        appTitle(title);
    }


    public static void transferMoney(String title) {
        appTitle(title);
    }
    
    public static void checkBalance(String title) {
        appTitle(title);
    }


    public static void dropAccount(String title) {
        appTitle(title);
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
}