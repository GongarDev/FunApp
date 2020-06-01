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
}
