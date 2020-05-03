package com.example.funapp.ui.inicio;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class InicioViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public InicioViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment Inicio");
    }

    public LiveData<String> getText() {
        return mText;
    }
}