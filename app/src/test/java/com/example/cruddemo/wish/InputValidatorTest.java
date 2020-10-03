package com.example.cruddemo.wish;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
Unit tests for the input validator class
 */
public class InputValidatorTest {

    @Test
    public void name_isCorrect() {
        assertTrue(InputValidator.isValidName("MazeRun Books 1 to 4"));
    }

    @Test
    public void name_isWrong_characters() {
        assertFalse(InputValidator.isValidName("Books 1 - 4"));
    }

    @Test
    public void name_isWrong_length() {
        assertFalse(InputValidator.isValidName("Books 1 to 4 from maze runner series"));
    }
}