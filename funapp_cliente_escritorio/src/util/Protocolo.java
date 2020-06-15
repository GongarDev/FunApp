package util;

/**
 *
 * @author melkart
 */
public interface Protocolo {

    public static final Integer SIN_SESION = 0;
    public static final Integer INICIAR_SESION = 1;
    public static final Integer REGISTRARSE_ESTANDAR = 2;
    public static final Integer REGISTRARSE_RESPONSABLE = 3;
    public static final Integer SESION_ABIERTA_ESTANDAR = 4;
    public static final Integer SESION_ABIERTA_RESPONSABLE = 5;
    public static final Integer CERRAR_SESION = 6;
    public static final Integer SESION_FALLIDA = 7;
    public static final Integer REGISTRARSE_FALLIDO = 8;
    public static final Integer REGISTRARSE_EXISTE_USUARIO = 9;
    public static final Integer REGISTRARSE_EXISTE_SEUDONIMO = 10;
    public static final Integer INSERTAR_EVENTO = 11;
    public static final Integer INSERTAR_EXITO = 12;
    public static final Integer INSERTAR_FALLIDO = 13;
    public static final Integer VER_EVENTOS_RESPONSABLE = 14;
    public static final Integer CREAR_EDITAR_EVENTO = 15;
    public static final Integer ACTUALIZAR_EVENTO = 16;
    public static final Integer ACTUALIZAR_EXITO = 17;
    public static final Integer ACTUALIZAR_FALLIDO = 18;
    public static final Integer ACTIVAR_EVENTO = 19;
    public static final Integer SUSCRITOS_EVENTO = 20;
    public static final Integer MAPA_EVENTOS = 21;
    public static final Integer HISTORIAL_EVENTOS = 22;
    public static final Integer EXPLORAR_EVENTOS = 23;
    public static final Integer CONSULTAR_USUARIO_RESPONSABLE = 24;
    public static final Integer CONSULTAR_USUARIO_ESTANDAR = 25;
    public static final Integer ACTUALIZAR_USUARIO_RESPONSABLE = 26;
    public static final Integer ACTUALIZAR_USUARIO_ESTANDAR = 27;
    public static final Integer CONSULTAR_ENTIDAD = 28;
    public static final Integer ACTUALIZAR_ENTIDAD = 29;
    public static final Integer SUSCRIPCIONES_EVENTOS = 30;
    public static final Integer SUSCRIPCIONES_SUSCRIBIRSE = 31;
    public static final Integer SUSCRIPCIONES_DESUSCRIBIRSE = 32;
    public static final Integer INICIO_PROXIMOS = 33;
    public static final Integer INICIO_RECOMENDADOS = 34;
    public static final Integer CONSULTAR_PUBLICACIONES = 35;
    public static final Integer INSERTAR_PUBLICACION = 36;
    public static final Integer CONSULTAR_VALORACIONES = 37;
    public static final Integer INSERTAR_VALORACION = 38;
    public static final Integer COMPROBAR_CODIGO_EVENTO = 39;
    public static final Integer NO_SUSCRITO_CODIGO = 40;
    public static final Integer GANADO_PUNTOS_CODIGO = 41;
    public static final Integer MIS_EVENTOS = 42;
    public static final Integer EXISTE_ENTIDAD_USUARIO = 43;
    public static final Integer EXISTE = 44;
    public static final Integer NO_EXISTE = 45;
    public static final Integer COMPROBAR_SUSCRITO = 46;
    public static final Integer AMIGOS_LISTA = 47;
    public static final Integer AMIGOS_CODIGO = 48;
    public static final Integer SUSCRIPCIONES_USUARIO = 49;
    public static final Integer SUSCRIPCIONES_LISTA = 50;
    public static final Integer ELIMINAR_AMIGO = 51;
    public static final Integer COMPROBAR_CODIGO_SEGUIDOR = 52;
    public static final Integer AMIGOS_SIGUIENDO = 53;
    public static final Integer AMIGOS_EXISTE_SEGUIMIENTO = 54;
    public static final Integer REPORTAR_INCIDENCIA = 55;
    public static final Integer ELIMINAR_CUENTA = 56;
    public static final Integer REGISTRARSE_RESPONSABLE_ESCRITORIO = 57;
    public static final Integer SESION_ABIERTA_ADMIN = 58;
    public static final Integer ADMIN_VER_ANUNCIOS = 59;  
    public static final Integer ADMIN_INSERTAR_ANUNCIOS = 60;
    public static final Integer ADMIN_ELIMINAR_ANUNCIOS = 61;
    public static final Integer ADMIN_VER_INCIDENCIAS = 62;  
    public static final Integer ADMIN_ELIMINAR_INCIDENCIAS = 63;
    public static final Integer ADMIN_VER_EVENTOS = 64;  
    public static final Integer ADMIN_ELIMINAR_EVENTOS = 65;
    public static final Integer ADMIN_VER_USUARIOS_ESTANDAR = 66;
    public static final Integer ADMIN_ELIMINAR_USUARIO_ESTANDAR = 67;
    public static final Integer ADMIN_VER_USUARIOS_RESPONSABLE = 68;
    public static final Integer ADMIN_ELIMINAR_USUARIO_RESPONSABLE = 69;   
    public static final Integer ADMIN_VER_ENTIDADES = 70;    
    public static final Integer INICIO_CARGAR_ANUNCIOS = 71;
    public static final Integer CONSULTAR_ATRIBUTOS = 72;      
}
