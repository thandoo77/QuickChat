/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.quickchat;

/**
 *
 * @author Student
 */

public class Login {

    // Stores the user's registration information
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String cellPhoneNumber;

    // Creates a Login object using the user's registration details
    public Login(String firstName, String lastName, String username,
                 String password, String cellPhoneNumber) {

        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.cellPhoneNumber = cellPhoneNumber;
    }

    // Checks if the username contains an underscore
    // and is no more than 5 characters long
    public boolean checkUserName() {

        return username.contains("_") && username.length() <= 5;
    }

    // Checks if the password contains:
    // At least 8 characters
    // A capital letter
    // A number
    // A special character
    public boolean checkPasswordComplexity() {

        boolean hasCapital = false;
        boolean hasNumber = false;
        boolean hasSpecial = false;

        for (int i = 0; i < password.length(); i++) {

            char character = password.charAt(i);

            if (Character.isUpperCase(character)) {
                hasCapital = true;
            }

            if (Character.isDigit(character)) {
                hasNumber = true;
            }

            if (!Character.isLetterOrDigit(character)) {
                hasSpecial = true;
            }
        }

        return password.length() >= 8
                && hasCapital
                && hasNumber
                && hasSpecial;
    }

    // Checks if the cellphone number has the South African
    // international code +27 followed by 9 digits
    public boolean checkCellPhoneNumber() {

        String regex = "^\\+27[0-9]{9}$";

        return cellPhoneNumber.matches(regex);
    }

    // Registers the user and returns the required messages
    public String registerUser() {

        if (!checkUserName()) {
            return "Username is not correctly formatted; please ensure that your username contains an underscore and is no more than five characters in length.";
        }

        if (!checkPasswordComplexity()) {
            return "Password is not correctly formatted; please ensure that the password contains at least eight characters, a capital letter, a number, and a special character.";
        }

        if (!checkCellPhoneNumber()) {
            return "Cell phone number incorrectly formatted or does not contain international code.";
        }

        return "Username successfully captured.\n"
                + "Password successfully captured.\n"
                + "Cell phone number successfully added.\n"
                + "Registration successful.";
    }

    // Checks whether the username and password entered
    // match the registered username and password
    public boolean loginUser(String enteredUsername, String enteredPassword) {

        return enteredUsername.equals(username)
                && enteredPassword.equals(password);
    }

    // Returns the login status message
    public String returnLoginStatus(String enteredUsername, String enteredPassword) {

        if (loginUser(enteredUsername, enteredPassword)) {

            return "Welcome " + firstName + " " + lastName
                    + " it is great to see you again.";

        } else {

            return "Username or password incorrect, please try again.";
        }
    }
}
