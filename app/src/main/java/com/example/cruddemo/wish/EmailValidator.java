package com.example.cruddemo.wish;

import android.text.Editable;
import android.text.TextWatcher;

import java.util.regex.Pattern;

public class EmailValidator implements TextWatcher {
                          //Valid email pattern
    public static final Pattern EMAIL_PATTERN = Pattern.compile(
                                  "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                                          "\\@" +
                                          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                                          "(" +   /*closed in (  ) because .com pattern can repeat many times*/
                                          "\\." +
                                          "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                                          ")+"
                          );
    private boolean mIsValid = false;
    public boolean isValid() {
        return mIsValid;
    }

    public static boolean isValidEmail(CharSequence email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    @Override
    final public void afterTextChanged(Editable editableText) {
        mIsValid = isValidEmail(editableText);
    }
    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {/*Not implemented*/}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {/*Not implemented*/}

}
