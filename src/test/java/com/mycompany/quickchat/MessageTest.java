/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

/**
 *
 * @author Student
 */


public class MessageTest {


    @Test
    public void testMessageLengthValid() {
        Message message = new Message(
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals(
                "Message ready to send.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testMessageLengthInvalid() {
        String longMessage = "A".repeat(251);

        Message message = new Message(
                "+27718693002",
                longMessage,
                0
        );

        assertEquals(
                "Message exceeds 250 characters by 1; please reduce the size.",
                message.checkMessageLength()
        );
    }

    @Test
    public void testRecipientValid() {
        Message message = new Message(
                "+27718693002",
                "Hello there",
                0
        );

        assertEquals(
                "Cell phone number successfully captured.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testRecipientInvalid() {
        Message message = new Message(
                "08575975889",
                "Hello there",
                0
        );

        assertEquals(
                "Cell phone number is incorrectly formatted or does not contain an international code. Please correct the number and try again.",
                message.checkRecipientCell()
        );
    }

    @Test
    public void testMessageIDLength() {
        Message message = new Message(
                "+27718693002",
                "Hello there",
                0
        );

        assertTrue(message.checkMessageID());
    }

    @Test
    public void testMessageHashFormat() {
        Message message = new Message(
                "0012345678",
                "+27718693002",
                "Hi Mike, can you join us for dinner tonight?",
                0
        );

        assertEquals(
                "00:0:HITONIGHT",
                message.createMessageHash()
        );
    }

    // PART 3 TESTS

    @Test
    public void testMessageInitiallyNotSent() {
        Message message = new Message(
                "+27718693002",
                "Hello there",
                0
        );

        assertFalse(message.isSent());
    }

    @Test
    public void testMessageSuccessfullySent() {
        Message message = new Message(
                "+27718693002",
                "Hello there",
                0
        );

        assertEquals(
                "Message successfully sent.",
                message.SentMessage()
        );

        assertTrue(message.isSent());
    }

    @Test
    public void testMessageInitiallyNotStored() {
        Message message = new Message(
                "+27718693002",
                "Hello there",
                0
        );

        assertFalse(message.isStored());
    }

    @Test
    public void testMessageInitiallyNotDiscarded() {
        Message message = new Message(
                "+27718693002",
                "Hello there",
                0
        );

        assertFalse(message.isDiscarded());
    }

    @Test
    public void testMessageDetails() {
        Message message = new Message(
                "0012345678",
                "+27718693002",
                "Hello there",
                0
        );

        assertEquals("0012345678", message.getMessageID());
        assertEquals("+27718693002", message.getRecipient());
        assertEquals("Hello there", message.getMessageText());
        assertEquals("00:0:HELLOTHERE", message.getMessageHash());
    }

    @Test
    public void testPrintMessages() {
        Message message = new Message(
                "0012345678",
                "+27718693002",
                "Hello there",
                0
        );

        String result = message.printMessages();

        assertTrue(result.contains("Message ID: 0012345678"));
        assertTrue(result.contains("Recipient: +27718693002"));
        assertTrue(result.contains("Message: Hello there"));
    }
        @Test
    public void testMessageHashLoop() {

        Message[] messages = {
            new Message("0012345678", "+27718693002", "Hello there", 0),
            new Message("1112345678", "+27718693002", "Good morning friend", 1),
            new Message("2212345678", "+27718693002", "See you tomorrow", 2)
        };

        String[] expectedHashes = {
            "00:0:HELLOTHERE",
            "11:1:GOODFRIEND",
            "22:2:SEETOMORROW"
        };

        for (int i = 0; i < messages.length; i++) {

            assertEquals(
                expectedHashes[i],
                messages[i].createMessageHash()
            );
        }
    }
} 
