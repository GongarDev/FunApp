package com.example.funapp.ui.notificaciones;

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

public class NotificacionesViewModel extends AndroidViewModel implements Protocolo {

    private MutableLiveData<List<Evento>> eventosList;

    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public NotificacionesViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<List<Evento>> getEventosList(int id_usuario, Integer tipoUsuario) {
        if (eventosList == null) {
            eventosList = new MutableLiveData<>();
            if (tipoUsuario == SESION_ABIERTA_ESTANDAR) {
                cargarEventosEstandar(id_usuario);
            } else if (tipoUsuario == SESION_ABIERTA_ESTANDAR) {
                cargarEventosResponsable(id_usuario);
            }
        }
        return eventosList;
    }

    public void cargarEventosEstandar(int id_usuario) {

        try {
            this.mensaje = this.gson.toJson(SUSCRIPCIONES_EVENTOS);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
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

    public void cargarEventosResponsable(int id_usuario) {

        try {
            if (SocketHandler.getSalida() != null) {
                this.mensaje = this.gson.toJson(VER_EVENTOS_RESPONSABLE);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                this.mensaje = this.gson.toJson(id_usuario);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                this.mensaje = (String) SocketHandler.getEntrada().readUTF();
                this.eventosList.setValue(
                        (ArrayList) this.gson.fromJson(
                                this.mensaje, new TypeToken<ArrayList<Evento>>() {
                                }.getType()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




