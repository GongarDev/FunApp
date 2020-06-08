package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Credenciales;
import model.Entidad;
import model.Evento;
import model.Incidencia;
import model.Publicacion;
import model.Usuario;
import model.UsuarioEstandar;
import model.UsuarioResponsable;
import model.Valoracion;
import util.Protocolo;

/**
 *
 * @author melkart
 */
public class HiloServidor extends Thread implements Protocolo {

    private DataOutputStream salida;
    private DataInputStream entrada;
    private Integer estadoSesion;
    private Gson gson;
    private String mensaje;
    private Usuario usuario;
    private Controlador controlador;

    public HiloServidor(Socket socket) {

        try {
            this.salida = new DataOutputStream(socket.getOutputStream());
            this.entrada = new DataInputStream(socket.getInputStream());
            this.estadoSesion = SIN_SESION;
            this.controlador = new Controlador();
            this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
            this.usuario = null;
        } catch (IOException e) {
            System.out.println("ERROR DE E/S en HiloCliente");
            e.printStackTrace();
        }
    }

    public void run() {

        try {
            String estadoJson = (String) this.entrada.readUTF();
            this.estadoSesion = gson.fromJson(estadoJson, Integer.class);

            if (this.estadoSesion == INICIAR_SESION) {

                this.mensaje = (String) this.entrada.readUTF();

                iniciarSesion();

                this.mensaje = gson.toJson(this.estadoSesion);
                this.salida.writeUTF(this.mensaje);
                this.mensaje = gson.toJson(this.usuario);
                this.salida.writeUTF(this.mensaje);

            } else if (this.estadoSesion == REGISTRARSE_RESPONSABLE
                    || this.estadoSesion == REGISTRARSE_ESTANDAR) {

                this.mensaje = (String) this.entrada.readUTF();

                altaUsuario();

                this.mensaje = gson.toJson(this.estadoSesion);
                this.salida.writeUTF(this.mensaje);

            }

            while (this.estadoSesion != SIN_SESION && this.estadoSesion != CERRAR_SESION) {

                estadoJson = (String) this.entrada.readUTF();
                this.estadoSesion = gson.fromJson(estadoJson, Integer.class);

                if (this.estadoSesion == VER_EVENTOS_RESPONSABLE) {
                    verEventosResponsable();
                } else if (this.estadoSesion == CREAR_EDITAR_EVENTO) {
                    this.mensaje = gson.toJson(this.controlador.listaTematica());
                    this.salida.writeUTF(this.mensaje);
                } else if (this.estadoSesion == INSERTAR_EVENTO) {
                    insertarEvento();
                } else if (this.estadoSesion == ACTUALIZAR_EVENTO) {
                    actualizarEvento();
                } else if (this.estadoSesion == ACTIVAR_EVENTO) {
                    activarEvento();
                } else if (this.estadoSesion == SUSCRITOS_EVENTO) {
                    suscritosEvento();
                } else if (this.estadoSesion == MAPA_EVENTOS) {
                    eventosPorCodigoPostal();
                } else if (this.estadoSesion == HISTORIAL_EVENTOS) {
                    historialEventos();
                } else if (this.estadoSesion == EXPLORAR_EVENTOS) {
                    explorarEventos();
                } else if (this.estadoSesion == CONSULTAR_USUARIO_RESPONSABLE) {
                    consultarUsuarioResponsable();
                } else if (this.estadoSesion == CONSULTAR_USUARIO_ESTANDAR) {
                    //consultarUsuarioEstandar();
                } else if (this.estadoSesion == ACTUALIZAR_USUARIO_RESPONSABLE) {
                    actualizarUsuarioResponsable();
                } else if (this.estadoSesion == ACTUALIZAR_USUARIO_ESTANDAR) {
                    actualizarUsuarioEstandar();
                } else if (this.estadoSesion == CONSULTAR_ENTIDAD) {
                    consultarEntidad();
                } else if (this.estadoSesion == ACTUALIZAR_ENTIDAD) {
                    actualizarEntidad();
                } else if (this.estadoSesion == SUSCRIPCIONES_EVENTOS) {
                    suscripcionesEventos();
                } else if (this.estadoSesion == SUSCRIPCIONES_SUSCRIBIRSE) {
                    suscribirseEvento();
                } else if (this.estadoSesion == SUSCRIPCIONES_DESUSCRIBIRSE) {
                    desuscribirseEvento();
                } else if (this.estadoSesion == INICIO_PROXIMOS) {
                    inicioProximos();
                } else if (this.estadoSesion == INICIO_RECOMENDADOS) {
                    inicioRecomendados();
                } else if (this.estadoSesion == CONSULTAR_PUBLICACIONES) {
                    consultarPublicaciones();
                } else if (this.estadoSesion == INSERTAR_PUBLICACION) {
                    insertarPublicacion();
                } else if (this.estadoSesion == CONSULTAR_VALORACIONES) {
                    consultarValoraciones();
                } else if (this.estadoSesion == INSERTAR_VALORACION) {
                    insertarValoracion();
                } else if (this.estadoSesion == COMPROBAR_CODIGO_EVENTO) {
                    comprobarCodigoQRSuscritos();
                } else if (this.estadoSesion == EXISTE_ENTIDAD_USUARIO) {
                    existeEntidadUsuario();
                } else if (this.estadoSesion == COMPROBAR_SUSCRITO) {
                    existeSuscrito();
                } else if (this.estadoSesion == AMIGOS_LISTA) {
                    listaUsuariosAmigos();
                } else if (this.estadoSesion == SUSCRIPCIONES_USUARIO) {
                    cantidadSuscripciones();
                } else if (this.estadoSesion == ELIMINAR_AMIGO) {
                    eliminarAmigo();
                } else if (this.estadoSesion == COMPROBAR_CODIGO_SEGUIDOR) {
                    comprobarCodigoQRSeguidor();
                } else if (this.estadoSesion == ELIMINAR_CUENTA) {
                    eliminarCuenta();
                } else if (this.estadoSesion == REPORTAR_INCIDENCIA) {
                    reportarIncidencia();
                }
                
            }

        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void iniciarSesion() {

        Credenciales credenciales = this.gson.fromJson(this.mensaje, Credenciales.class);
        this.usuario = (UsuarioEstandar) this.controlador.buscarUsEstandar(credenciales);
        if (this.usuario != null) {
            this.estadoSesion = SESION_ABIERTA_ESTANDAR;
        } else {
            this.usuario = (UsuarioResponsable) this.controlador.buscarUsResponsable(credenciales);
            if (this.usuario != null) {
                this.estadoSesion = SESION_ABIERTA_RESPONSABLE;
            } else {
                this.estadoSesion = SESION_FALLIDA;
            }
        }
    }

    public void altaUsuario() {

        if (this.estadoSesion == REGISTRARSE_RESPONSABLE) {
            UsuarioResponsable usuario = this.gson.fromJson(this.mensaje, UsuarioResponsable.class);
            if (this.controlador.existeUsuario(usuario.getEmail())) {
                this.estadoSesion = REGISTRARSE_EXISTE_USUARIO;
            } else if (this.controlador.existeNombreUsuario(usuario.getSeudonimo())) {
                this.estadoSesion = REGISTRARSE_EXISTE_SEUDONIMO;
            } else if (this.controlador.insertarUsResponsable(usuario)) {
                this.estadoSesion = SIN_SESION;
            } else {
                this.estadoSesion = REGISTRARSE_FALLIDO;
            }
        } else if (this.estadoSesion == REGISTRARSE_ESTANDAR) {
            UsuarioEstandar usuario = this.gson.fromJson(this.mensaje, UsuarioEstandar.class);
            if (this.controlador.existeUsuario(usuario.getEmail())) {
                this.estadoSesion = REGISTRARSE_EXISTE_USUARIO;
            } else if (this.controlador.existeNombreUsuario(usuario.getSeudonimo())) {
                this.estadoSesion = REGISTRARSE_EXISTE_SEUDONIMO;
            } else if (this.controlador.insertarUsEstandar(usuario)) {
                this.estadoSesion = SIN_SESION;
            } else {
                this.estadoSesion = REGISTRARSE_FALLIDO;
            }
        }
    }

    public void insertarEvento() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            Evento evento = this.gson.fromJson(this.mensaje, Evento.class);
            boolean insertado = this.controlador.insertarEvento(evento);
            if (insertado) {
                this.estadoSesion = INSERTAR_EXITO;
            } else {
                this.estadoSesion = INSERTAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarEvento() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            Evento evento = this.gson.fromJson(this.mensaje, Evento.class);
            boolean actualizado = this.controlador.actualizarEvento(evento);
            if (actualizado) {
                this.estadoSesion = ACTUALIZAR_EXITO;
            } else {
                this.estadoSesion = ACTUALIZAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void activarEvento() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            Evento evento = this.gson.fromJson(this.mensaje, Evento.class);
            boolean actualizado = this.controlador.activarEvento(evento);
            if (actualizado) {
                this.estadoSesion = ACTUALIZAR_EXITO;
            } else {
                this.estadoSesion = ACTUALIZAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void verEventosResponsable() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            List<Evento> listaEventos = this.controlador.listaEventosPorUsuarioResposable(id_usuario);
            this.mensaje = gson.toJson(listaEventos);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void suscritosEvento() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_evento = this.gson.fromJson(this.mensaje, Integer.class);
            int suscritos = this.controlador.suscritosEvento(id_evento);
            this.mensaje = gson.toJson(suscritos);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eventosPorCodigoPostal() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            String codigo_postal = this.gson.fromJson(this.mensaje, String.class);
            List<Evento> listaEventos = this.controlador.listaEventosPorCodigoPostal(codigo_postal);
            this.mensaje = gson.toJson(listaEventos);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void historialEventos() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            List<Evento> listaEventos = this.controlador.historialEventos(id_usuario);
            this.mensaje = gson.toJson(listaEventos);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void explorarEventos() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            String codigo_postal = this.gson.fromJson(this.mensaje, String.class);
            this.mensaje = (String) this.entrada.readUTF();
            String nombreTematica = this.gson.fromJson(this.mensaje, String.class);
            List<Evento> listaEventos = this.controlador.listaEventosExplorar(codigo_postal, nombreTematica);
            this.mensaje = gson.toJson(listaEventos);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarUsuarioResponsable() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            UsuarioResponsable usuario = this.controlador.consultarUsuarioResponsable(id_usuario);
            this.mensaje = gson.toJson(usuario);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarUsuarioResponsable() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            UsuarioResponsable usuarioResponsable = this.gson.fromJson(this.mensaje, UsuarioResponsable.class);
            boolean actualizado = this.controlador.actualizarUsuarioResponsable(usuarioResponsable);
            if (actualizado) {
                this.estadoSesion = ACTUALIZAR_EXITO;
            } else {
                this.estadoSesion = ACTUALIZAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarUsuarioEstandar() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            Usuario usuario = this.gson.fromJson(this.mensaje, Usuario.class);
            boolean actualizado = this.controlador.actualizarUsuarioEstandar(usuario);
            if (actualizado) {
                this.estadoSesion = ACTUALIZAR_EXITO;
            } else {
                this.estadoSesion = ACTUALIZAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarEntidad() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            Entidad entidad = this.controlador.consultarEntidad(id_usuario);
            this.mensaje = gson.toJson(entidad);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actualizarEntidad() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            Entidad entidad = this.gson.fromJson(this.mensaje, Entidad.class);
            boolean actualizado = this.controlador.actualizarEntidad(entidad);
            if (actualizado) {
                this.estadoSesion = ACTUALIZAR_EXITO;
            } else {
                this.estadoSesion = ACTUALIZAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void suscripcionesEventos() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            List<Evento> listaEventos = this.controlador.suscripcionesEventos(id_usuario);
            this.mensaje = gson.toJson(listaEventos);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void suscribirseEvento() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_evento = this.gson.fromJson(this.mensaje, Integer.class);
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            boolean actualizado = this.controlador.suscribirseEvento(id_evento, id_usuario);
            if (actualizado) {
                this.estadoSesion = ACTUALIZAR_EXITO;
            } else {
                this.estadoSesion = ACTUALIZAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void desuscribirseEvento() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_evento = this.gson.fromJson(this.mensaje, Integer.class);
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            boolean actualizado = this.controlador.desuscribirseEvento(id_evento, id_usuario);
            if (actualizado) {
                this.estadoSesion = ACTUALIZAR_EXITO;
            } else {
                this.estadoSesion = ACTUALIZAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inicioProximos() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            String codigo_postal = this.gson.fromJson(this.mensaje, String.class);
            List<Evento> listaEventos = this.controlador.listaEventosProximos(codigo_postal);
            this.mensaje = gson.toJson(listaEventos);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void inicioRecomendados() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            String codigo_postal = this.gson.fromJson(this.mensaje, String.class);
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            List<Evento> listaEventos = this.controlador.listaEventosRecomendados(codigo_postal, id_usuario);
            this.mensaje = gson.toJson(listaEventos);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarPublicaciones() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_evento = this.gson.fromJson(this.mensaje, Integer.class);
            List<Publicacion> listaPublicacion = this.controlador.listaPublicaciones(id_evento);
            this.mensaje = gson.toJson(listaPublicacion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarPublicacion() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            Publicacion publicacion = this.gson.fromJson(this.mensaje, Publicacion.class);
            this.mensaje = (String) this.entrada.readUTF();
            int id_evento = this.gson.fromJson(this.mensaje, Integer.class);
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            boolean insertado = this.controlador.insertarPublicacion(publicacion, id_evento, id_usuario);
            if (insertado) {
                this.estadoSesion = INSERTAR_EXITO;
            } else {
                this.estadoSesion = INSERTAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void consultarValoraciones() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_evento = this.gson.fromJson(this.mensaje, Integer.class);
            List<Valoracion> listaValoracion = this.controlador.listaValoraciones(id_evento);
            this.mensaje = gson.toJson(listaValoracion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertarValoracion() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            Valoracion valoracion = this.gson.fromJson(this.mensaje, Valoracion.class);
            boolean insertado = this.controlador.insertarValoracion(valoracion);
            if (insertado) {
                this.estadoSesion = INSERTAR_EXITO;
            } else {
                this.estadoSesion = INSERTAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void comprobarCodigoQRSuscritos() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_evento = this.gson.fromJson(this.mensaje, Integer.class);
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            boolean existe = this.controlador.existeSuscritoEvento(id_evento, id_usuario);
            if (existe) {
                this.controlador.aumentarPuntosEvento(id_evento, id_usuario);
                this.estadoSesion = GANADO_PUNTOS_CODIGO;
            } else {
                this.estadoSesion = NO_SUSCRITO_CODIGO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void existeEntidadUsuario() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            boolean existe = this.controlador.existeEntidadUsuario(id_usuario);
            if (existe) {
                this.estadoSesion = EXISTE;
            } else {
                this.estadoSesion = NO_EXISTE;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void existeSuscrito() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_evento = this.gson.fromJson(this.mensaje, Integer.class);
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            boolean existe = this.controlador.existeSuscritoEvento(id_evento, id_usuario);
            if (existe) {
                this.estadoSesion = EXISTE;
            } else {
                this.estadoSesion = NO_EXISTE;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void listaUsuariosAmigos() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            List<Usuario> listaUsuariosAmigos = this.controlador.listaUsuariosAmigos(id_usuario);
            this.mensaje = gson.toJson(listaUsuariosAmigos);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cantidadSuscripciones() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            int suscripciones = this.controlador.cantidadSuscripciones(id_usuario);
            this.mensaje = gson.toJson(suscripciones);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void eliminarAmigo() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuarioAmigo = this.gson.fromJson(this.mensaje, Integer.class);
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            boolean actualizado = this.controlador.eliminarAmigo(id_usuarioAmigo, id_usuario);
            if (actualizado) {
                this.estadoSesion = ACTUALIZAR_EXITO;
            } else {
                this.estadoSesion = ACTUALIZAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void comprobarCodigoQRSeguidor() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuarioAmigo = this.gson.fromJson(this.mensaje, Integer.class);
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            boolean existe = this.controlador.existeSeguimientoUsuario(id_usuarioAmigo, id_usuario);
            if (existe) {
                this.estadoSesion = AMIGOS_EXISTE_SEGUIMIENTO;
            } else {
                this.controlador.insertarSeguimiento(id_usuarioAmigo, id_usuario);                
                this.estadoSesion = AMIGOS_SIGUIENDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
        public void eliminarCuenta() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            int id_usuario = this.gson.fromJson(this.mensaje, Integer.class);
            boolean actualizado = this.controlador.eliminarCuenta(id_usuario);
            if (actualizado) {
                this.estadoSesion = ACTUALIZAR_EXITO;
            } else {
                this.estadoSesion = ACTUALIZAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
       public void reportarIncidencia() {

        try {
            this.mensaje = (String) this.entrada.readUTF();
            Incidencia incidencia = this.gson.fromJson(this.mensaje, Incidencia.class);
            boolean insertado = this.controlador.insertarIncidencia(incidencia);
            if (insertado) {
                this.estadoSesion = INSERTAR_EXITO;
            } else {
                this.estadoSesion = INSERTAR_FALLIDO;
            }
            this.mensaje = gson.toJson(this.estadoSesion);
            this.salida.writeUTF(this.mensaje);
        } catch (IOException ex) {
            Logger.getLogger(HiloServidor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }     
}
