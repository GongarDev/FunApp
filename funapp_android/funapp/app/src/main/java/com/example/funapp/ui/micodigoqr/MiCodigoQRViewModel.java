package com.example.funapp.ui.micodigoqr;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MiCodigoQRViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MiCodigoQRViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment MiCodigoQR");
    }

    public LiveData<String> getText() {
        return mText;
    }
}