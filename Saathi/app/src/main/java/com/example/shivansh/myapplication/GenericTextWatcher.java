package com.example.shivansh.myapplication;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

public class GenericTextWatcher implements TextWatcher {

    private View view;

    GenericTextWatcher(View view) {
        this.view = view;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        switch (view.getId()) {

            case R.id.editText1:
                if (text.length() == 1)
                    ValidateOTPActivity.et2.requestFocus();
                break;
            case R.id.editText2:
                if (text.length() == 1)
                    ValidateOTPActivity.et3.requestFocus();
                break;
            case R.id.editText3:
                if (text.length() == 1)
                    ValidateOTPActivity.et4.requestFocus();
                break;
            case R.id.editText4:
                if(text.length()==1)
                    ValidateOTPActivity.et5.requestFocus();
                break;
            case R.id.editText5:
                if(text.length()==1)
                    ValidateOTPActivity.et6.requestFocus();
            case R.id.editText6:
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

}
