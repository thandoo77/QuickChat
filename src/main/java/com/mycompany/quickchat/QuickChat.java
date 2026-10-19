/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Student
 */

public class QuickChat {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to QuickChat.");

        // PART 1: REGISTRATION
        System.out.print("Enter your first name: ");
        String firstName = scanner.nextLine();

        System.out.print("Enter your last name: ");
        String lastName = scanner.nextLine();

        System.out.print("Enter your username: ");
        String username = scanner.nextLine();

        System.out.print("Enter your password: ");
        String password = scanner.nextLine();

        System.out.print("Enter your cell phone number: ");
        String cellPhoneNumber = scanner.nextLine();

        Login user = new Login(
                firstName,
                lastName,
                username,
                password,
                cellPhoneNumber
        );

        System.out.println();
        System.out.println(user.registerUser());

        if (user.checkUserName()
                && user.checkPasswordComplexity()
                && user.checkCellPhoneNumber()) {

            System.out.println("\n===== LOGIN =====");

            System.out.print("Enter your username: ");
            String loginUsername = scanner.nextLine();

            System.out.print("Enter your password: ");
            String loginPassword = scanner.nextLine();

            System.out.println(
                    user.returnLoginStatus(loginUsername, loginPassword)
            );

            if (loginUsername.equals(username)
                    && loginPassword.equals(password)) {

                System.out.println("\nWelcome to QuickChat, " + firstName + "!");

                // PART 2: MESSAGE MENU
                int numberOfMessages;

                while (true) {
                    System.out.print("\nHow many messages would you like to send? ");

                    try {
                        numberOfMessages = Integer.parseInt(scanner.nextLine());

                        if (numberOfMessages >= 0) {
                            break;
                        }

                        System.out.println("Please enter zero or a positive number.");

                    } catch (NumberFormatException e) {
                        System.out.println("Please enter a valid whole number.");
                    }
                }

                List<Message> messages = new ArrayList<>();
                int messagesSent = 0;
                int messagesCreated = 0;

                boolean running = true;

                while (running) {
                    System.out.println("\n===== QUICKCHAT MENU =====");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Show recently sent messages");
                    System.out.println("3. Quit");
                    System.out.print("Enter your choice: ");

                    String choice = scanner.nextLine();

                    switch (choice) {
                        case "1":

                            if (messagesCreated >= numberOfMessages) {
                                System.out.println(
                                        "You have reached your chosen message limit."
                                );
                                break;
                            }

                            System.out.print("Enter recipient cellphone number: ");
                            String recipient = scanner.nextLine();

                            System.out.print("Enter your message: ");
                            String messageText = scanner.nextLine();

                            if (messageText.length() > 250) {
                                System.out.println(
                                        "Please enter a message of less than 250 characters."
                                );
                                break;
                            }

                            Message message = new Message(
                                    recipient,
                                    messageText,
                                    messagesCreated
                            );

                            if (!message.checkRecipientCell()
                                    .equals("Cell phone number successfully captured.")) {
                                System.out.println(message.checkRecipientCell());
                                break;
                            }

                            messagesCreated++;
                            messages.add(message);

                            System.out.println("\nMessage ready to send.");
                            System.out.println("1. Send Message");
                            System.out.println("2. Disregard Message");
                            System.out.println("3. Store Message to send later");
                            System.out.print("Choose an option: ");

                            String messageChoice = scanner.nextLine();

                            if (messageChoice.equals("1")) {
                                System.out.println(message.SentMessage());
                                System.out.println(message.printMessages());
                                messagesSent++;

                            } else if (messageChoice.equals("2")) {
                                System.out.println("Press 0 to delete the message.");
                                System.out.print("Enter 0 to confirm deletion: ");

                                String deleteChoice = scanner.nextLine();

                                if (deleteChoice.equals("0")) {
                                    messages.remove(message);
                                    System.out.println("Message disregarded.");
                                }

                            } else if (messageChoice.equals("3")) {
                                System.out.println(message.storeMessage());

                            } else {
                                System.out.println("Invalid message option.");
                            }

                            break;

                        case "2":
                            System.out.println("Coming Soon.");
                            break;

                        case "3":
                            running = false;
                            System.out.println(
                                    "Total messages sent: " + messagesSent
                            );
                            System.out.println("Thank you for using QuickChat.");
                            break;

                        default:
                            System.out.println(
                                    "Invalid choice. Please select 1, 2, or 3."
                            );
                    }
                }

            } else {
                System.out.println(
                        "Login failed. Messages cannot be sent."
                );
            }

        } else {
            System.out.println(
                    "Registration was unsuccessful. Please correct your details and try again."
            );
        }

        scanner.close();
    }
}