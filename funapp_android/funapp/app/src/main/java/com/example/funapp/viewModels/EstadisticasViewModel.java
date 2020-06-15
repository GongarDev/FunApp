package com.example.funapp.viewModels;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.funapp.models.Atributo;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Valoracion;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EstadisticasViewModel extends AndroidViewModel implements Protocolo {

    private static MutableLiveData<List<Atributo>> atributosList;
    private MutableLiveData<List<Evento>> eventosList;

    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public EstadisticasViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<List<Atributo>> getAtributosList(int id_usuario) {
        atributosList = new MutableLiveData<>();
        cargarAtributos(id_usuario);
        return atributosList;
    }

    public void cargarAtributos(int id_usuario) {

        try {
            this.mensaje = this.gson.toJson(CONSULTAR_ATRIBUTOS);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.atributosList.setValue(
                    (ArrayList) this.gson.fromJson(
                            this.mensaje, new TypeToken<ArrayList<Atributo>>() {
                            }.getType()));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            if(SocketHandler.getSalida()!=null) {
                this.mensaje = this.gson.toJson(HISTORIAL_EVENTOS);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                this.mensaje = this.gson.toJson(id_usuario);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                this.mensaje = (String) SocketHandler.getEntrada().readUTF();
                this.eventosList.setValue(
                        (ArrayList) this.gson.fromJson(
                                this.mensaje, new TypeToken<ArrayList<Evento>>() {
                                }.getType()));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Valoracion> cargarValoraciones(int id_evento) {

        try {
            this.mensaje = this.gson.toJson(CONSULTAR_VALORACIONES);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_evento);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (ArrayList) this.gson.fromJson(
                        this.mensaje, new TypeToken<ArrayList<Valoracion>>() {
                        }.getType());
    }
}
