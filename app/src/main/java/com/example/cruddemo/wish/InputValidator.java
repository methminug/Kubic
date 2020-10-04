package com.example.cruddemo.wish;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.regex.Pattern;

/*
*   Checks if inputs sent to the add new wish class are valid
*/

public class InputValidator implements TextWatcher {

    public static final Pattern ITEMNAME_INPUT_PATTERN = Pattern.compile("[A-Za-z | A-Za-z0-9]{1,20}");

    private boolean pIsValid = false;

    // STATIC because no need to create an instance of this class since it only validates
    public static boolean isValidName(CharSequence userInput) {
        return userInput != null && ITEMNAME_INPUT_PATTERN.matcher(userInput).matches();
    }

    @Override
    final public void afterTextChanged(Editable editableText) {
        pIsValid = isValidName(editableText);
    }

    public boolean isValid() {
        return pIsValid;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


}
