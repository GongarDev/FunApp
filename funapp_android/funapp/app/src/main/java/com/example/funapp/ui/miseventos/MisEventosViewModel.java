package com.example.funapp.ui.miseventos;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funapp.models.Tematica;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MisEventosViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private static MutableLiveData<List<Tematica>> tematicasList;
    private String mensaje;
    private Gson gson;

    public MisEventosViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Esto es el fragment MisEventos");
        this.gson = new Gson();
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<List<Tematica>> getTematicasList() {

        if (tematicasList==null){
            tematicasList= new MutableLiveData<>();
            cargarTematicas();
        }
        return tematicasList;
    }

    public void cargarTematicas() {

        try {
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.tematicasList.setValue(
                    (ArrayList) this.gson.fromJson(
                            this.mensaje, new TypeToken<ArrayList<Tematica>>(){}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}