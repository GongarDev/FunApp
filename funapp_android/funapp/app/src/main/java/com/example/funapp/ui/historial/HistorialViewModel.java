package com.example.funapp.ui.historial;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HistorialViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HistorialViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment Historial");
    }

    public LiveData<String> getText() {
        return mText;
    }
}