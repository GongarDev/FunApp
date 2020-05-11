package com.example.funapp.ui.cuenta;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CuentaViewModel  extends ViewModel {

    private MutableLiveData<String> mText;

    public CuentaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment Mi Cuenta");
    }

    public LiveData<String> getText() {
        return mText;
    }
}