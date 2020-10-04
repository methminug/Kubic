package com.example.cruddemo.wish;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
Unit tests for the email validator class
 */
public class EmailValidatorTest {

    @Test
    public void email_isCorrect() {
        assertTrue(EmailValidator.isValidEmail("AbC124@gmail.com"));
    }

    @Test
    public void email_isWrong_characters() {
        assertFalse(EmailValidator.isValidEmail("AbC124#@gmail.com"));
    }

    @Test
    public void email_isWrong_missingname() {
        assertFalse(InputValidator.isValidName("@gmail.com"));
    }

    @Test
    public void email_isWrong_repeatedChar() {
        assertFalse(InputValidator.isValidName("ABC123@gmail..com"));
    }
    @Test
    public void email_isWrong_null() {
        assertFalse(InputValidator.isValidName(null));
    }
}