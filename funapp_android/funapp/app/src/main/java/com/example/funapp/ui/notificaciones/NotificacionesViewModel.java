package com.example.funapp.ui.notificaciones;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificacionesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public NotificacionesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment Notificaciones");
    }

    public LiveData<String> getText() {
        return mText;
    }
}