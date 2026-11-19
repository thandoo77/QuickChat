/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ThreadLocalRandom;

 /*
 * @author Student
 */


public class Message {


    private String messageID;
    private String sender;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int messageNumber;

    private boolean sent;
    private boolean stored;
    private boolean discarded;

    private static int totalMessagesSent = 0;

    // Constructor
    public Message(String recipient, String messageText, int messageNumber) {
        this.messageID = generateMessageID();
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.sender = "";
        this.sent = false;
        this.stored = false;
        this.discarded = false;
        this.messageHash = createMessageHash();
    }

    // Constructor with existing message ID
    public Message(String messageID, String recipient,
                   String messageText, int messageNumber) {

        this.messageID = messageID;
        this.recipient = recipient;
        this.messageText = messageText;
        this.messageNumber = messageNumber;
        this.sender = "";
        this.sent = false;
        this.stored = false;
        this.discarded = false;
        this.messageHash = createMessageHash();
    }

    // Generate random 10-digit ID
    private String generateMessageID() {
    StringBuilder id = new StringBuilder();

    for (int i = 0; i < 10; i++) {
        id.append(ThreadLocalRandom.current().nextInt(10));
    }

    return id.toString();
}
    

    // Set sender
    public void setSender(String sender) {
        this.sender = sender;
    }

    // Get sender
    public String getSender() {
        return sender;
    }

    // Check message ID
    public boolean checkMessageID() {
        return messageID != null && messageID.length() == 10;
    }

    // Check recipient cell number
    public String checkRecipientCell() {

        if (recipient != null && recipient.matches("^\\+27[0-9]{9}$")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    // Check message length
    public String checkMessageLength() {

        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        }

        int excess = messageText == null ? 0 : messageText.length() - 250;

        return "Message exceeds 250 characters by "
                + excess + "; please reduce the size.";
    }

    // Create message hash
    public String createMessageHash() {

    if (messageID == null || messageID.length() < 2
            || messageText == null || messageText.trim().isEmpty()) {
        return "";
    }

    String cleanedMessage = messageText.trim()
            .replaceAll("[^a-zA-Z0-9\\s]", "");

    String[] words = cleanedMessage.trim().split("\\s+");

    String firstWord = words[0].toUpperCase();
    String lastWord = words[words.length - 1].toUpperCase();

    return messageID.substring(0, 2)
            + ":" + messageNumber
            + ":" + firstWord + lastWord;
}
    // Send message
    public String SentMessage() {

        if (!sent && !discarded && !stored) {
            sent = true;
            totalMessagesSent++;

            return "Message successfully sent.";
        }

        return "Message cannot be sent.";
    }

    // Disregard message
    public void DisregardMessage() {
        if (!sent && !stored) {
            discarded = true;
        }
    }

    // Print message details
    public String printMessages() {

        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    // Store message in JSON
    /*
 * JSON data formatting follows the JSON specification
 * (Bray, 2017).
 */
    public String storeMessage() {

        try {

            File file = new File("messages.json");

            String jsonEntry = "{"
                    + "\"messageID\":\"" + escapeJSON(messageID) + "\","
                    + "\"messageHash\":\"" + escapeJSON(messageHash) + "\","
                    + "\"sender\":\"" + escapeJSON(sender) + "\","
                    + "\"recipient\":\"" + escapeJSON(recipient) + "\","
                    + "\"message\":\"" + escapeJSON(messageText) + "\""
                    + "}";

            String existingContent = "";

            if (file.exists()) {
                existingContent = Files.readString(file.toPath()).trim();
            }

            if (existingContent.isEmpty()
                    || existingContent.equals("[]")) {

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[\n" + jsonEntry + "\n]");
                }

            } else {

                int closingBracket = existingContent.lastIndexOf("]");

                if (closingBracket == -1) {
                    return "Error: Invalid JSON file.";
                }

                String updatedContent =
                        existingContent.substring(0, closingBracket).trim();

                if (!updatedContent.endsWith("[")) {
                    updatedContent += ",\n";
                }

                updatedContent += jsonEntry + "\n]";

                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(updatedContent);
                }
            }

            stored = true;

            return "Message successfully stored.";

        } catch (IOException e) {

            return "Error storing message: " + e.getMessage();
        }
    }

    // Escape JSON characters
    private String escapeJSON(String text) {

        if (text == null) {
            return "";
        }

        return text.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\t", "\\t");
    }

    // Total messages sent
    public static int returnTotalMessagess() {
        return totalMessagesSent;
    }

    // Alternative correctly spelled method
    public static int returnTotalMessages() {
        return totalMessagesSent;
    }

    // Getters
    public String getMessageID() {
        return messageID;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getMessageText() {
        return messageText;
    }

    public String getMessageHash() {
        return messageHash;
    }

    public int getMessageNumber() {
        return messageNumber;
    }

    public boolean isSent() {
        return sent;
    }

    public boolean isStored() {
        return stored;
    }

    public boolean isDiscarded() {
        return discarded;
    }
}
