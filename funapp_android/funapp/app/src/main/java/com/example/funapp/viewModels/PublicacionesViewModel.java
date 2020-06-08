package com.example.funapp.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.funapp.models.Publicacion;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PublicacionesViewModel extends AndroidViewModel implements Protocolo {

    private static MutableLiveData<List<Publicacion>> publicacionesList;
    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public PublicacionesViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<List<Publicacion>> getPublicacionesList(int id_evento) {
        publicacionesList = new MutableLiveData<>();
        cargarPublicaciones(id_evento);
        return publicacionesList;
    }

    public void cargarPublicaciones(int id_evento) {

        try {
            this.mensaje = this.gson.toJson(CONSULTAR_PUBLICACIONES);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_evento);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.publicacionesList.setValue(
                    (ArrayList) this.gson.fromJson(
                            this.mensaje, new TypeToken<ArrayList<Publicacion>>() {
                            }.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer insertarPublicacion(Publicacion publicacion, int id_evento, int id_usuario) {

        try {
            this.mensaje = this.gson.toJson(INSERTAR_PUBLICACION);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(publicacion);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_evento);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }
}

