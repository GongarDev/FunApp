package com.example.funapp.ui.explorar;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funapp.models.Evento;
import com.example.funapp.models.Tematica;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExplorarViewModel extends AndroidViewModel implements Protocolo {

    private MutableLiveData<List<Tematica>> tematicasList;

    public ExplorarViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Tematica>> getTematicas() {
        if (tematicasList == null) {
            tematicasList = new MutableLiveData<>();
            cargarTematicas();
        }
        return tematicasList;
    }

    public void cargarTematicas() {

        List<Tematica> tematicas = new ArrayList<>();
        tematicas.add(new Tematica("Cultura local", "cultura"));
        tematicas.add(new Tematica("Deporte", "deporte"));
        tematicas.add(new Tematica("Espectáculos", "espectaculos"));
        tematicas.add(new Tematica("Gastronomía", "gastronomia"));
        tematicas.add(new Tematica("Entretenimiento", "entretenimiento"));
        tematicas.add(new Tematica("Tecnología", "tecnologia"));
        tematicas.add(new Tematica("Benéficos", "beneficos"));
        tematicas.add(new Tematica("Ponencias", "ponencias"));
        this.tematicasList.setValue(tematicas);
    }
}