/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.quickchat;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Student
 */
public class LoginTest {

    @Test
    public void testCheckUserNameCorrect() {

        Login user = new Login(
                "Thando",
                "Makhubo",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkUserName());
    }

    @Test
    public void testCheckUserNameIncorrect() {

        Login user = new Login(
                "Thando",
                "Makhubo",
                "kyle!!!!!!!",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(user.checkUserName());
    }

    @Test
    public void testCheckPasswordCorrect() {

        Login user = new Login(
                "Thando",
                "Makhubo",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkPasswordComplexity());
    }

    @Test
    public void testCheckPasswordIncorrect() {

        Login user = new Login(
                "Thando",
                "Makhubo",
                "kyl_1",
                "password",
                "+27838968976"
        );

        assertFalse(user.checkPasswordComplexity());
    }

    @Test
    public void testCheckCellPhoneCorrect() {

        Login user = new Login(
                "Thando",
                "Makhubo",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(user.checkCellPhoneNumber());
    }

    @Test
    public void testCheckCellPhoneIncorrect() {

        Login user = new Login(
                "Thando",
                "Makhubo",
                "kyl_1",
                "Ch&&sec@ke99!",
                "08966553"
        );

        assertFalse(user.checkCellPhoneNumber());
    }

    @Test
    public void testLoginSuccessful() {

        Login user = new Login(
                "Thando",
                "Makhubo",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertTrue(
                user.loginUser("kyl_1", "Ch&&sec@ke99!")
        );
    }

    @Test
    public void testLoginFailed() {

        Login user = new Login(
                "Thando",
                "Makhubo",
                "kyl_1",
                "Ch&&sec@ke99!",
                "+27838968976"
        );

        assertFalse(
                user.loginUser("wrong_username", "wrong_password")
        );
    }
@Test
public void testRegisterUserSuccess() {

    Login user = new Login(
            "Thando",
            "Makhubo",
            "kyl_1",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertEquals(
            "Username successfully captured.\n"
            + "Password successfully captured.\n"
            + "Cell phone number successfully added.\n"
            + "Registration successful! You can now log in.",
            user.registerUser()
    );
}

@Test
public void testRegisterUserInvalidUsername() {

    Login user = new Login(
            "Thando",
            "Makhubo",
            "kyle!!!!!!!",
            "Ch&&sec@ke99!",
            "+27838968976"
    );

    assertEquals(
            "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.",
            user.registerUser()
    );
}

@Test
public void testRegisterUserInvalidPassword() {

    Login user = new Login(
            "Thando",
            "Makhubo",
            "kyl_1",
            "password",
            "+27838968976"
    );

    assertEquals(
            "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.",
            user.registerUser()
    );
}

@Test
public void testRegisterUserInvalidCellPhone() {

    Login user = new Login(
            "Thando",
            "Makhubo",
            "kyl_1",
            "Ch&&sec@ke99!",
            "08966553"
    );

    assertEquals(
            "Cell phone number incorrectly formatted or does not contain international code.",
            user.registerUser()
    );
}
}
