package com.example.funapp.ui.historial;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funapp.models.Evento;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HistorialViewModel extends AndroidViewModel implements Protocolo {

    private MutableLiveData<List<Evento>> eventosList;

    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public HistorialViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<List<Evento>> getEventos(int id_usuario) {
        if (eventosList==null){
            eventosList= new MutableLiveData<>();
            cargarEventos(id_usuario);
        }
        return eventosList;
    }

    public void cargarEventos(int id_usuario){

        try {
            this.mensaje = this.gson.toJson(HISTORIAL_EVENTOS);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.eventosList.setValue(
                    (ArrayList) this.gson.fromJson(
                            this.mensaje, new TypeToken<ArrayList<Evento>>(){}.getType()));
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}