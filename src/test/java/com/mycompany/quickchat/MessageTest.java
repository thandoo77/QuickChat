/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    assertEquals("00:0:HITONIGHT", message.createMessageHash());
}
}
