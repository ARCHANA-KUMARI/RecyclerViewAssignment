package com.robosoft.archana.recyclerviewassignment.ValidationUtils;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.io.Serializable;

/**
 * Created by archana on 17/2/16.
 */
public class UrlValidation implements Serializable {

   public static void validUrl(final EditText editurl){
       editurl.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {

           }
           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {
               String url = editurl.getText().toString();
               if (isEmailValid(url)) {
               } else if (editurl.getText().toString().isEmpty()) {
                   editurl.setError("EditField should not empty");
               } else {
                   editurl.setError("Invalid Entry");
               }
           }
       });
   }

    public static boolean isEmailValid(String mEmail) {

        String emailPattern = "[http://]+/[a-z0-9]+\\.+[a-z]+";
        if (mEmail.matches(emailPattern))
            return true;
        else
            return false;
    }

}
