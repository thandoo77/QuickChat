/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
/**
 *
 * @author Student
 */


public class Message {

    private String messageID;
    private String recipient;
    private String messageText;
    private String messageHash;
    private int messageNumber;

    private boolean sent;
    private boolean stored;
    private boolean discarded;

    private static int totalMessagesSent = 0;

   public Message(String recipient, String messageText, int messageNumber) {
    this(generateMessageID(), recipient, messageText, messageNumber);
}

public Message(String messageID, String recipient, String messageText, int messageNumber) {
    this.messageID = messageID;
    this.recipient = recipient;
    this.messageText = messageText;
    this.messageNumber = messageNumber;
    this.messageHash = createMessageHash();

    this.sent = false;
    this.stored = false;
    this.discarded = false;
}

    private static String generateMessageID() {
        Random random = new Random();
        long id = 1_000_000_000L
                + (long) (random.nextDouble() * 9_000_000_000L);

        return String.valueOf(id);
    }

    public Boolean checkMessageID() {
        return messageID != null && messageID.length() <= 10;
    }

    public String checkRecipientCell() {
        if (recipient != null
                && recipient.matches("\\+27[0-9]{9}")) {
            return "Cell phone number successfully captured.";
        }

        return "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.";
    }

    public String checkMessageLength() {
        if (messageText != null && messageText.length() <= 250) {
            return "Message ready to send.";
        }

        int length = messageText == null ? 0 : messageText.length();

        return "Message exceeds 250 characters by "
                + (length - 250)
                + "; please reduce the size.";
    }

    
public String createMessageHash() {
    String firstTwoDigits = messageID.substring(0, 2);
    String firstWord = "";
    String lastWord = "";

    String[] words = messageText.trim().split("\\s+");

    if (words.length > 0) {
        firstWord = words[0];
        lastWord = words[words.length - 1];
   
    // Remove punctuation from the first and last words.
    firstWord = firstWord.replaceAll("[^a-zA-Z0-9]", "");
    lastWord = lastWord.replaceAll("[^a-zA-Z0-9]", "");

    return firstTwoDigits + ":"
            + messageNumber + ":"
            + (firstWord + lastWord).toUpperCase();
}


        return firstTwoDigits + ":"
                + messageNumber + ":"
                + (firstWord + lastWord).toUpperCase();
    }

    public String SentMessage() {
    sent = true;
    totalMessagesSent++;

    return "Message successfully sent.";
}

    public String printMessages() {
        return "Message ID: " + messageID
                + "\nMessage Hash: " + messageHash
                + "\nRecipient: " + recipient
                + "\nMessage: " + messageText;
    }

    public String storeMessage() {
    try {
        java.io.File file = new java.io.File("messages.json");
        java.util.List<String> entries = new java.util.ArrayList<>();

        if (file.exists() && file.length() > 0) {
            String content = java.nio.file.Files.readString(file.toPath()).trim();

            // Read existing messages from the JSON array.
            if (content.startsWith("[") && content.endsWith("]")) {
                content = content.substring(1, content.length() - 1).trim();

                if (!content.isEmpty()) {
                    entries.add(content);
                }
            }
        }

        String safeRecipient = recipient.replace("\\", "\\\\")
                .replace("\"", "\\\"");

        String safeMessage = messageText.replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r");

        String jsonMessage = "{"
                + "\"messageID\":\"" + messageID + "\","
                + "\"messageHash\":\"" + messageHash + "\","
                + "\"recipient\":\"" + safeRecipient + "\","
                + "\"message\":\"" + safeMessage + "\""
                + "}";

        entries.add(jsonMessage);

        try (FileWriter writer = new FileWriter(file, false)) {
            writer.write("[\n");

            for (int i = 0; i < entries.size(); i++) {
                writer.write(entries.get(i));

                if (i < entries.size() - 1) {
                    writer.write(",");
                }

                writer.write("\n");
            }

            writer.write("]");
        }

        stored = true;
        return "Message successfully stored.";

    } catch (IOException e) {
        return "Unable to store message: " + e.getMessage();
    }
}

    public int returnTotalMessagess() {
        return totalMessagesSent;
    }

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