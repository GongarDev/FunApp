package com.example.funapp.ui.explorar.EventosTematica;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.funapp.models.Evento;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EventosTematicaViewModel extends AndroidViewModel implements Protocolo {

    private MutableLiveData<List<Evento>> eventosList;

    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public EventosTematicaViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<List<Evento>> getEventos(String codigo_postal, String nombreTematica) {
        if (eventosList == null) {
            eventosList = new MutableLiveData<>();
            cargarEventos(codigo_postal, nombreTematica);
        }
        return eventosList;
    }

    public void cargarEventos(String codigo_postal, String nombreTematica) {

        try {
            this.mensaje = this.gson.toJson(EXPLORAR_EVENTOS);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(codigo_postal);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(nombreTematica);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.eventosList.setValue(
                    (ArrayList) this.gson.fromJson(
                            this.mensaje, new TypeToken<ArrayList<Evento>>() {
                            }.getType()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}