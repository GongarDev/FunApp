package com.example.funapp.viewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.funapp.models.Publicacion;
import com.example.funapp.models.Valoracion;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ValoracionesViewModel extends AndroidViewModel implements Protocolo {

    private static MutableLiveData<List<Valoracion>> valoracionesList;
    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public ValoracionesViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<List<Valoracion>> getValoracionesList(int id_evento) {
        valoracionesList = new MutableLiveData<>();
        cargarValoraciones(id_evento);
        return valoracionesList;
    }

    public void cargarValoraciones(int id_evento) {

        try {
            this.mensaje = this.gson.toJson(CONSULTAR_VALORACIONES);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_evento);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.valoracionesList.setValue(
                    (ArrayList) this.gson.fromJson(
                            this.mensaje, new TypeToken<ArrayList<Valoracion>>() {
                            }.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer insertarValoracion(Valoracion valoracion) {

        try {
            this.mensaje = this.gson.toJson(INSERTAR_VALORACION);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(valoracion);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }
}