package com.example.funapp.registrarse;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.funapp.registrarse.data.RegistrarseDataSource;
import com.example.funapp.registrarse.data.RegistrarseRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */

public class RegistrarseViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RegistrarseViewModel.class)) {
            return (T) new RegistrarseViewModel(RegistrarseRepository.getInstance(new RegistrarseDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}
