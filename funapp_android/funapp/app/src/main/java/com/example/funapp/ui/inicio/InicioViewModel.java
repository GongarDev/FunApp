package com.example.funapp.ui.inicio;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funapp.models.Evento;
import com.example.funapp.ui.miseventos.MisEventosFragment;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InicioViewModel extends AndroidViewModel implements Protocolo {

    private MutableLiveData<List<Evento>> eventosProximosList;
    private MutableLiveData<List<Evento>> eventosRecomendadosList;

    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public InicioViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<List<Evento>> getEventosProximos(String codigo_postal) {
        if (eventosProximosList==null){
            eventosProximosList= new MutableLiveData<>();
            cargarEventosProximos(codigo_postal);
        }
        return eventosProximosList;
    }

    public LiveData<List<Evento>> getEventosRecomendados(String codigo_postal, int id_usuario) {
        if (eventosRecomendadosList==null){
            eventosRecomendadosList= new MutableLiveData<>();
            cargarEventosRecomendados(codigo_postal, id_usuario);
        }
        return eventosRecomendadosList;
    }

    public void cargarEventosProximos(String codigo_postal){

        try {
            if(SocketHandler.getSalida()!=null) {
                this.mensaje = this.gson.toJson(INICIO_PROXIMOS);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                this.mensaje = this.gson.toJson(codigo_postal);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                this.mensaje = (String) SocketHandler.getEntrada().readUTF();
                this.eventosProximosList.setValue(
                        (ArrayList) this.gson.fromJson(
                                this.mensaje, new TypeToken<ArrayList<Evento>>() {
                                }.getType()));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarEventosRecomendados(String codigo_postal, int id_usuario){

        try {
            this.mensaje = this.gson.toJson(INICIO_RECOMENDADOS);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(codigo_postal);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            List<Evento> listaEventos = (ArrayList) this.gson.fromJson(
                    this.mensaje, new TypeToken<ArrayList<Evento>>(){}.getType());
            List<Evento> listaRecomendados = new ArrayList();
            if(listaEventos.size()!=0) {
                for (int i = 0; i < listaEventos.size(); i++) {
                    listaRecomendados.add(listaEventos.get((int) (Math.random() * (listaEventos.size() - 1) + 0)));
                }
            }
            this.eventosRecomendadosList.setValue(listaRecomendados);
        }catch (IOException e) {
            e.printStackTrace();
        }
    }
}