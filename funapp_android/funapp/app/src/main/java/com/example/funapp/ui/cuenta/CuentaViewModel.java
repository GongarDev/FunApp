package com.example.funapp.ui.cuenta;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funapp.models.Entidad;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Incidencia;
import com.example.funapp.models.Tematica;
import com.example.funapp.models.Usuario;
import com.example.funapp.models.UsuarioEstandar;
import com.example.funapp.models.UsuarioResponsable;
import com.example.funapp.util.Protocolo;
import com.example.funapp.util.SocketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CuentaViewModel extends AndroidViewModel implements Protocolo {

    private static MutableLiveData<UsuarioResponsable> usuario;
    private static MutableLiveData<Entidad> entidad;
    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public CuentaViewModel(@NonNull Application application) {
        super(application);
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    public LiveData<UsuarioResponsable> getUsuario(int id_usuario) {

        if (usuario==null){
            usuario= new MutableLiveData<>();
            cargarUsuarioResponsable(id_usuario);
        }
        return usuario;
    }

    public LiveData<Entidad> getEntidad(int id_usuario) {

        if (entidad==null){
            entidad= new MutableLiveData<>();
            cargarEntidad(id_usuario);
        }
        return entidad;
    }

    public void cargarUsuarioResponsable(int id_usuario) {
        try {
            this.mensaje = this.gson.toJson(CONSULTAR_USUARIO_RESPONSABLE);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.usuario.setValue((UsuarioResponsable)this.gson.fromJson(this.mensaje, UsuarioResponsable.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarEntidad(int id_usuario) {
        try {
            this.mensaje = this.gson.toJson(CONSULTAR_ENTIDAD);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(id_usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.entidad.setValue((Entidad) this.gson.fromJson(this.mensaje, Entidad.class));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Integer actualizarUsuarioResponsable(UsuarioResponsable usuario){
        try {
            this.mensaje = this.gson.toJson(ACTUALIZAR_USUARIO_RESPONSABLE);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }

    public Integer actualizarEntidad(Entidad entidad){
        try {
            this.mensaje = this.gson.toJson(ACTUALIZAR_ENTIDAD);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(entidad);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }

    public Integer actualizarUsuarioEstandar(Usuario usuario){
        try {
            this.mensaje = this.gson.toJson(ACTUALIZAR_USUARIO_ESTANDAR);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(usuario);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }

    public Integer reportarIncidencia(Incidencia incidencia){
        try {
            this.mensaje = this.gson.toJson(REPORTAR_INCIDENCIA);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = this.gson.toJson(incidencia);
            SocketHandler.getSalida().writeUTF(this.mensaje);
            this.mensaje = (String) SocketHandler.getEntrada().readUTF();
            this.estadoSesion = (Integer) this.gson.fromJson(this.mensaje, Integer.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this.estadoSesion;
    }

    public Integer eliminarCuenta(int id_usuario){
        try {
            this.mensaje = this.gson.toJson(ELIMINAR_CUENTA);
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
