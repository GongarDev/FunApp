package com.example.funapp.ui.miseventos.crear_editar_evento;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

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

public class CrearEditarEventoViewModel extends AndroidViewModel implements Protocolo {

    private static MutableLiveData<List<Tematica>> tematicasList;
    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public CrearEditarEventoViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<List<Tematica>> getTematicasList() {

        if (tematicasList==null){
            tematicasList= new MutableLiveData<>();
            cargarTematicas();
            Log.v("TEMATICAS",tematicasList.toString());
        }
        return tematicasList;
    }


    public void cargarTematicas() {

        try {
            this.mensaje = this.gson.toJson(CREAR_EDITAR_EVENTO);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.tematicasList.setValue(
                    (ArrayList) this.gson.fromJson(
                            this.mensaje, new TypeToken<ArrayList<Tematica>>(){}.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Integer insertarEvento(Evento evento) {

        try {
            this.mensaje = this.gson.toJson(INSERTAR_EVENTO);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(evento);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }

    public Integer actualizarEvento(Evento evento){
        try {
            this.mensaje = this.gson.toJson(ACTUALIZAR_EVENTO);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(evento);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }

}
