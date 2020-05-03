package com.example.funapp.ui.suscripciones;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuscripcionesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SuscripcionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment Suscripciones");
    }

    public LiveData<String> getText() {
        return mText;
    }
}