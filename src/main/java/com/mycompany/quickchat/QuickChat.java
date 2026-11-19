/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.quickchat;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Student
 */

public class QuickChat {

    // FIVE DYNAMIC COLLECTIONS
    private static ArrayList<Message> sentMessages = new ArrayList<>();
    private static ArrayList<Message> disregardedMessages = new ArrayList<>();
    private static ArrayList<StoredMessage> storedMessages = new ArrayList<>();
    private static ArrayList<String> messageHashes = new ArrayList<>();
    private static ArrayList<String> messageIDs = new ArrayList<>();

    private static final String JSON_FILE = "messages.json";

    // STORED MESSAGE CLASS
    static class StoredMessage {

        String messageID;
        String messageHash;
        String sender;
        String recipient;
        String messageText;
        boolean legacyFormat;

        StoredMessage(String messageID, String messageHash,
                String sender, String recipient,
                String messageText, boolean legacyFormat) {

            this.messageID = messageID;
            this.messageHash = messageHash;
            this.sender = sender;
            this.recipient = recipient;
            this.messageText = messageText;
            this.legacyFormat = legacyFormat;
        }
    }

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

            if (user.loginUser(loginUsername, loginPassword)) {

                System.out.println("\nWelcome to QuickChat, "
                        + firstName + "!");

                loadStoredMessages();

                // PART 2: MESSAGE LIMIT

                int numberOfMessages;

                while (true) {

                    System.out.print(
                            "\nHow many messages would you like to send? "
                    );

                    try {

                        numberOfMessages = Integer.parseInt(
                                scanner.nextLine()
                        );

                        if (numberOfMessages >= 0) {
                            break;
                        }

                        System.out.println(
                                "Please enter zero or a positive number."
                        );

                    } catch (NumberFormatException e) {

                        System.out.println(
                                "Please enter a valid whole number."
                        );
                    }
                }

                int messagesCreated = 0;
                int messagesSent = 0;

                boolean running = true;

                // MAIN MENU

                while (running) {

                    System.out.println("\n===== QUICKCHAT MENU =====");
                    System.out.println("1. Send Messages");
                    System.out.println("2. Show recently sent messages");
                    System.out.println("3. Quit");
                    System.out.println("4. Display and manage stored messages");

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

    // FOR LOOP TO PROCESS THE SELECTED NUMBER OF MESSAGES

    for (int i = messagesCreated; i < numberOfMessages; i++) {

        Message message;

        // RE-PROMPT UNTIL VALID MESSAGE DETAILS ARE ENTERED

        while (true) {

            System.out.print(
                    "Enter recipient cellphone number: "
            );

            String recipient = scanner.nextLine();

            System.out.print("Enter your message: ");

            String messageText = scanner.nextLine();

            if (messageText.length() > 250) {

                System.out.println(
                        "Message too long. Please enter a message of less than 250 characters."
                );

                continue;
            }

            message = new Message(
                    recipient,
                    messageText,
                    i
            );

            message.setSender(firstName + " " + lastName);

            if (!message.checkRecipientCell().equals(
                    "Cell phone number successfully captured.")) {

                System.out.println(
                        message.checkRecipientCell()
                );

                continue;
            }

            break;
        }

        System.out.println("\nMessage ready to send.");

        String messageChoice;

        // RE-PROMPT UNTIL A VALID MESSAGE OPTION IS SELECTED

        while (true) {

            System.out.println("1. Send Message");
            System.out.println("2. Disregard Message");
            System.out.println("3. Store Message to send later");

            System.out.print("Choose an option: ");

            messageChoice = scanner.nextLine();

            if (messageChoice.equals("1")
                    || messageChoice.equals("2")
                    || messageChoice.equals("3")) {

                break;

            } else {

                System.out.println(
                        "Invalid option. Please choose 1, 2 or 3."
                );
            }
        }

        if (messageChoice.equals("1")) {

            System.out.println(message.SentMessage());

            System.out.println(message.printMessages());

            sentMessages.add(message);

            messageHashes.add(message.getMessageHash());

            messageIDs.add(message.getMessageID());

            messagesSent++;

        } else if (messageChoice.equals("2")) {

            System.out.println(
                    "Press 0 to delete the message."
            );

            System.out.print(
                    "Enter 0 to confirm deletion: "
            );

            String deleteChoice = scanner.nextLine();

            if (deleteChoice.equals("0")) {

                message.DisregardMessage();

                disregardedMessages.add(message);

                System.out.println("Message disregarded.");

            } else {

                System.out.println(
                        "Message was not disregarded."
                );
            }

        } else if (messageChoice.equals("3")) {

            System.out.println(message.storeMessage());

            loadStoredMessages();
        }

        messagesCreated++;
    }

    break;

                        case "2":

                            displaySentMessages();

                            break;

                        case "3":

                            running = false;

                            System.out.println(
                                    "Total messages sent: " + messagesSent
                            );

                            System.out.println(
                                    "Thank you for using QuickChat."
                            );

                            break;

                        case "4":

                            storedMessagesMenu(scanner);

                            break;

                        default:

                            System.out.println(
                                    "Invalid choice. Please select 1, 2, 3 or 4."
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

    // LOAD JSON MESSAGES

    private static void loadStoredMessages() {

        storedMessages.clear();
        messageHashes.clear();
        messageIDs.clear();

        File file = new File(JSON_FILE);

        if (!file.exists() || file.length() == 0) {
            return;
        }

        try {

            String content = Files.readString(file.toPath());

            Pattern objectPattern = Pattern.compile("\\{([^{}]*)\\}");
            Matcher objectMatcher = objectPattern.matcher(content);

            int legacyCounter = 1;

            while (objectMatcher.find()) {

                String object = objectMatcher.group();

                Map<String, String> fields = new LinkedHashMap<>();

                Pattern fieldPattern = Pattern.compile(
                        "\"([^\"]+)\"\\s*:\\s*\"((?:\\\\.|[^\"\\\\])*)\""
                );

                Matcher fieldMatcher = fieldPattern.matcher(object);

                while (fieldMatcher.find()) {

                    fields.put(
                            fieldMatcher.group(1),
                            unescapeJson(fieldMatcher.group(2))
                    );
                }

                String recipient = fields.get("recipient");
                String messageText = fields.get("message");

                boolean legacy = fields.containsKey("text");

                if (legacy) {
                    messageText = fields.get("text");
                }

                if (recipient == null || messageText == null) {
                    continue;
                }

                String id = fields.get("messageID");
                String hash = fields.get("messageHash");
                String sender = fields.get("sender");

                if (id == null) {
                    id = String.format("%010d", legacyCounter);
                }

                if (hash == null) {

                    Message temporaryMessage = new Message(
                            id,
                            recipient,
                            messageText,
                            legacyCounter - 1
                    );

                    hash = temporaryMessage.getMessageHash();
                }

                StoredMessage storedMessage = new StoredMessage(
                        id,
                        hash,
                        sender,
                        recipient,
                        messageText,
                        legacy
                );

                storedMessages.add(storedMessage);
                messageIDs.add(id);
                messageHashes.add(hash);

                legacyCounter++;
            }

        } catch (IOException e) {

            System.out.println(
                    "Error loading messages: " + e.getMessage()
            );
        }
    }

    // JSON UNESCAPING

    private static String unescapeJson(String value) {

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < value.length(); i++) {

            char current = value.charAt(i);

            if (current == '\\' && i + 1 < value.length()) {

                char next = value.charAt(++i);

                switch (next) {

                    case 'n':
                        result.append('\n');
                        break;

                    case 'r':
                        result.append('\r');
                        break;

                    case 't':
                        result.append('\t');
                        break;

                    case '"':
                        result.append('"');
                        break;

                    case '\\':
                        result.append('\\');
                        break;

                    case '/':
                        result.append('/');
                        break;

                    default:
                        result.append(next);
                        break;
                }

            } else {

                result.append(current);
            }
        }

        return result.toString();
    }

    // JSON ESCAPING

    private static String escapeJson(String value) {

        if (value == null) {
            return "";
        }

        return value.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // SAVE JSON FILE

    private static void saveStoredMessages() {

        try (FileWriter writer = new FileWriter(JSON_FILE, false)) {

            writer.write("[\n");

            for (int i = 0; i < storedMessages.size(); i++) {

                StoredMessage message = storedMessages.get(i);

                writer.write("{");

                if (message.legacyFormat) {

                    writer.write(
                            "\"sender\":\""
                            + escapeJson(message.sender) + "\","
                    );

                    writer.write(
                            "\"recipient\":\""
                            + escapeJson(message.recipient) + "\","
                    );

                    writer.write(
                            "\"text\":\""
                            + escapeJson(message.messageText) + "\""
                    );

                } else {

                    writer.write(
                            "\"messageID\":\""
                            + escapeJson(message.messageID) + "\","
                    );

                    writer.write(
                            "\"messageHash\":\""
                            + escapeJson(message.messageHash) + "\","
                    );

                    writer.write(
                            "\"sender\":\""
                            + escapeJson(message.sender) + "\","
                    );

                    writer.write(
                            "\"recipient\":\""
                            + escapeJson(message.recipient) + "\","
                    );

                    writer.write(
                            "\"message\":\""
                            + escapeJson(message.messageText) + "\""
                    );
                }

                writer.write("}");

                if (i < storedMessages.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]");

            loadStoredMessages();

        } catch (IOException e) {

            System.out.println(
                    "Unable to update JSON file: " + e.getMessage()
            );
        }
    }

    // OPERATION 1: DISPLAY SENT MESSAGES

    private static void displaySentMessages() {

        if (sentMessages.isEmpty()) {

            System.out.println("No sent messages available.");
            return;
        }

        System.out.println("\n===== SENT MESSAGES =====");

        for (Message message : sentMessages) {

            System.out.println("Recipient: " + message.getRecipient());
            System.out.println("Message: " + message.getMessageText());
            System.out.println("Message ID: " + message.getMessageID());

            System.out.println("--------------------------");
        }
    }

    // DISPLAY STORED MESSAGE SENDERS AND RECIPIENTS

    private static void displayStoredSendersAndRecipients() {

        if (storedMessages.isEmpty()) {

            System.out.println("No stored messages available.");
            return;
        }

        System.out.println(
                "\n===== STORED MESSAGE SENDERS AND RECIPIENTS ====="
        );

        for (StoredMessage message : storedMessages) {

            System.out.println(
                    "Sender: "
                    + (message.sender == null ? "Unknown" : message.sender)
            );

            System.out.println("Recipient: " + message.recipient);

            System.out.println("--------------------------------");
        }
    }

    // OPERATION 2: LONGEST STORED MESSAGE

    private static void displayLongestMessage() {

        if (storedMessages.isEmpty()) {

            System.out.println("No stored messages available.");
            return;
        }

        StoredMessage longest = storedMessages.get(0);

        for (StoredMessage message : storedMessages) {

            if (message.messageText.length()
                    > longest.messageText.length()) {

                longest = message;
            }
        }

        System.out.println("\n===== LONGEST STORED MESSAGE =====");

        System.out.println("Message: " + longest.messageText);
        System.out.println("Length: " + longest.messageText.length());
        System.out.println("Recipient: " + longest.recipient);
    }

    // OPERATION 3: SEARCH BY MESSAGE ID

    private static void searchByMessageID(Scanner scanner) {

        System.out.print("Enter message ID: ");

        String searchID = scanner.nextLine();

        for (StoredMessage message : storedMessages) {

            if (message.messageID.equals(searchID)) {

                System.out.println("\nMessage found!");

                System.out.println("Recipient: " + message.recipient);
                System.out.println("Message: " + message.messageText);

                return;
            }
        }

        System.out.println("Message ID not found.");
    }

    // OPERATION 4: SEARCH BY RECIPIENT

    private static void searchByRecipient(Scanner scanner) {

        System.out.print("Enter recipient cellphone number: ");

        String searchRecipient = scanner.nextLine();

        boolean found = false;

        for (StoredMessage message : storedMessages) {

            if (message.recipient.equalsIgnoreCase(searchRecipient)) {

                System.out.println("\nMessage found!");

                System.out.println("Message ID: " + message.messageID);
                System.out.println("Message: " + message.messageText);

                found = true;
            }
        }

        if (!found) {

            System.out.println(
                    "No messages found for this recipient."
            );
        }
    }

    // OPERATION 5: DELETE BY MESSAGE HASH

    private static void deleteByMessageHash(Scanner scanner) {

        System.out.print("Enter message hash: ");

        String searchHash = scanner.nextLine();

        for (int i = 0; i < storedMessages.size(); i++) {

            StoredMessage message = storedMessages.get(i);

            if (message.messageHash.equals(searchHash)) {

                String deletedMessage = message.messageText;

                storedMessages.remove(i);

                saveStoredMessages();

                System.out.println(
                        "Message: \"" + deletedMessage
                        + "\" successfully deleted."
                );

                return;
            }
        }

        System.out.println("Message hash not found.");
    }

    // OPERATION 6: COMPLETE MESSAGE REPORT

    private static void displayMessageReport() {

        if (storedMessages.isEmpty()) {

            System.out.println("No stored messages available.");
            return;
        }

        System.out.println("\n===== STORED MESSAGE REPORT =====");

        for (StoredMessage message : storedMessages) {

            System.out.println("Message ID: " + message.messageID);

            System.out.println("Message Hash: " + message.messageHash);

            System.out.println(
                    "Sender: "
                    + (message.sender == null ? "Unknown" : message.sender)
            );

            System.out.println("Recipient: " + message.recipient);

            System.out.println("Message: " + message.messageText);

            System.out.println("--------------------------------");
        }

        System.out.println(
                "Total stored messages: " + storedMessages.size()
        );
    }

    // STORED MESSAGE MENU

    private static void storedMessagesMenu(Scanner scanner) {

        boolean menuRunning = true;

        while (menuRunning) {

            System.out.println("\n===== STORED MESSAGE MENU =====");

            System.out.println(
                    "1. Display sender and recipient of stored messages"
            );

            System.out.println("2. Display the longest stored message");

            System.out.println("3. Search for a message using its ID");

            System.out.println("4. Search for messages using recipient");

            System.out.println("5. Delete a message using its hash");

            System.out.println("6. Display a complete message report");

            System.out.println("7. Return to main menu");

            System.out.print("Enter your choice: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1":

                    displayStoredSendersAndRecipients();

                    break;

                case "2":

                    displayLongestMessage();

                    break;

                case "3":

                    searchByMessageID(scanner);

                    break;

                case "4":

                    searchByRecipient(scanner);

                    break;

                case "5":

                    deleteByMessageHash(scanner);

                    break;

                case "6":

                    displayMessageReport();

                    break;

                case "7":

                    menuRunning = false;

                    break;

                default:

                    System.out.println(
                            "Invalid option. Please try again."
                    );

                    break;
            }
        }
    }
}