package com.ps;
import java.io.FileReader;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        int mainMenuCommand;
        do {
            System.out.println("Welcome! Please select an option below:");
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
                default:
                    System.out.println("Invalid, try again!");


            }


        } while(mainMenuCommand != 0);


    }
    private static void depositInfo(){
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        System.out.println("Please enter your deposit information below:");

        System.out.println("Description");
        String description = scanner.nextLine();

        System.out.println("Vendor");
        String vendor = scanner.nextLine();



    }

    private static void ledgerDisplay() {
    }

    private static void makePaymentInfo() {
    }

}