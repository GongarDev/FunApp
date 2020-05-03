package com.example.funapp.ui.mapa;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MapaViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MapaViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment Mapa");
    }

    public LiveData<String> getText() {
        return mText;
    }
}