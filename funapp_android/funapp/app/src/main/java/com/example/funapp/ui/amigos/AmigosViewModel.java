package com.example.funapp.ui.amigos;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.funapp.models.Usuario;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AmigosViewModel extends AndroidViewModel implements Protocolo {

    private MutableLiveData<List<Usuario>> usuariosList;

    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public AmigosViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<List<Usuario>> getAmigos(int id_usuario) {
            usuariosList = new MutableLiveData<>();
            cargarAmigos(id_usuario);
        return usuariosList;
    }

    public void cargarAmigos(int id_usuario){

        try {
            if(SocketHandler.getSalida()!=null) {
                this.mensaje = this.gson.toJson(AMIGOS_LISTA);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                this.mensaje = this.gson.toJson(id_usuario);
                SocketHandler.getSalida().writeUTF(this.mensaje);
                this.mensaje = (String) SocketHandler.getEntrada().readUTF();
                this.usuariosList.setValue(
                        (ArrayList) this.gson.fromJson(
                                this.mensaje, new TypeToken<ArrayList<Usuario>>() {
                                }.getType()));
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer lecturaQR(int id_usuarioAmigo, int id_usuario){
        try {
            this.mensaje = this.gson.toJson(COMPROBAR_CODIGO_SEGUIDOR);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuarioAmigo);
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
