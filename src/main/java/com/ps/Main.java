package com.ps;
import java.io.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.io.BufferedReader;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        int mainMenuCommand;
        do {
            System.out.println("***************************************");
            System.out.println("Welcome! Please select an option below:");
            System.out.println("***************************************");
            System.out.println("1) Deposit");
            System.out.println("2) Make payment");
            System.out.println("3) Ledger");
            System.out.println("0) Exit");
            System.out.println("What would you like to do?");

            mainMenuCommand = scanner.nextInt();
            scanner.nextLine();

            switch (mainMenuCommand) {//switch for the main menu
                case 1:
                    depositInfo();
                    break;
                case 2:
                    makePaymentInfo();
                    break;
                case 3:
                    ledgerDisplay();
                    break;
                case 0:
                    System.out.println("Exiting.");
                    break;
                default:
                    System.out.println("Invalid, try again!");


            }


        } while(mainMenuCommand != 0);


    }
    private static void depositInfo() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formattedDate = date.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);

        System.out.println("Please enter your deposit information below:");
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        BigDecimal amount = getValidAmount();

        String record = String.join("|", formattedDate, formattedTime, description, vendor, amount.toString());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            writer.write(record);
            writer.newLine();
            System.out.println("Saved!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static BigDecimal getValidAmount() {
        BigDecimal amount;
        while (true) {
            System.out.print("Amount: ");
            try {
                amount = new BigDecimal(scanner.nextLine().trim());
                if (amount.compareTo(BigDecimal.ZERO) < 0) {
                    System.out.println("Must be a positive amount.");
                } else {
                    return amount;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid, please enter a valid number.");
            }
        }
    }
    private static void makePaymentInfo() {
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

        String formattedDate = date.format(dateFormatter);
        String formattedTime = time.format(timeFormatter);

        System.out.println("Please enter your payment information below:");
        System.out.print("Description: ");
        String description = scanner.nextLine();

        System.out.print("Vendor: ");
        String vendor = scanner.nextLine();

        BigDecimal amount = getValidAmount().negate();  // Payments are stored as negative amounts

        String record = String.join("|", formattedDate, formattedTime, description, vendor, amount.toString());

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.csv", true))) {
            writer.write(record);
            writer.newLine();
            System.out.println("Saved!");
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void ledgerDisplay() {
        ArrayList<String> transactions = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.csv"))){
            String line;
            while ((line = reader.readLine()) != null){
                transactions.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error");
            return;
        }
        Collections.reverse(transactions);

        int command = 0;
        do {
            System.out.println("1) All transaction entries");
            System.out.println("2) Deposits only");
            System.out.println("3) Payments only");
            System.out.println("4) Reports/Custom search");
            System.out.println("5) Home");
            System.out.print("Choose an option: ");
            try {
                 command = Integer.parseInt(scanner.nextLine());

            } catch (NumberFormatException e) {
                System.out.println("Invalid" + e.getMessage());
                continue;
            }

            switch (command){
                case 1:
                    displayTransactions(transactions);
                    break;
                case 2:
                    displayDepositsOnly(transactions);
                    break;
                case 3:
                    displayPaymentsOnly(transactions);
                    break;
                case 4:
                    showAllReports(transactions);
                    break;
                case 5:
                    System.out.println("Returning...");
                    break;
                default:
                    System.out.println("Invalid, try again!");
            }
        } while (command != 5);

    }
    private static void displayTransactions(ArrayList<String> transactions) {
        System.out.println("\nAll Transactions:");
        for (int i = transactions.size() -1; i >=0; i--){
            System.out.println(transactions.get(i));
        }
    }

    private static void displayDepositsOnly(ArrayList<String> transactions) {
        System.out.println("\nDeposits only:");
        for (String line : transactions) {
            String[] fields = line.split("\\|");

            if (fields.length != 5) {
                continue;
            }

            try {
                BigDecimal amount = new BigDecimal(fields[4].trim());

                if (amount.compareTo(BigDecimal.ZERO) > 0) {
                    System.out.println(line);
                }
            } catch (NumberFormatException ignored) {

            }

        }

    }

    private static void displayPaymentsOnly(ArrayList<String> transactions) {
        System.out.println("\nPayments only:");
        for (String line : transactions) {
            String[] fields = line.split("\\|");

            if (fields.length != 5) {
                continue;
            }
            try {
                BigDecimal amount = new BigDecimal(fields[4].trim());

                if(amount.compareTo(BigDecimal.ZERO) < 0){
                    System.out.println(line);
                }

            } catch(NumberFormatException ignored){

            }
        }
    }

    private static void showAllReports(ArrayList<String> transactions)   {
        int reportCommand = -1;

        do {
            System.out.println("\n===== Reports =====");
            System.out.println("1) Month to date");
            System.out.println("2) Previous month");
            System.out.println("3) Year to date");
            System.out.println("4) Previous year");
            System.out.println("5) Vendor search");
            System.out.println("0) Back");
            System.out.print("Please select an option: ");

        }while (reportCommand != 0);

    }

}