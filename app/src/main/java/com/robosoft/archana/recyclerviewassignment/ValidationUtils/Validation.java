package com.robosoft.archana.recyclerviewassignment.ValidationUtils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * Created by archana on 17/2/16.
 */
public class Validation {

    public static void checkValid(final EditText editText) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editText.getText().toString().isEmpty()) {
                    editText.setError("EditFiled should not be empty");
                }

            }
        });
    }
}
