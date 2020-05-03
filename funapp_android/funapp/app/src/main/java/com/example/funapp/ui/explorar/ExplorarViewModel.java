package com.example.funapp.ui.explorar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ExplorarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ExplorarViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment Explorar");
    }

    public LiveData<String> getText() {
        return mText;
    }
}