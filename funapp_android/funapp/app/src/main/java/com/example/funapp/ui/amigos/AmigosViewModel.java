package com.example.funapp.ui.amigos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AmigosViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AmigosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment Amigos");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
