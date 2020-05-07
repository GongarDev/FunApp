package com.example.funapp.registrarse.data;

import com.example.funapp.registrarse.data.model.RegistradoInUser;

public class RegistrarseRepository {

    private static volatile RegistrarseRepository instance;

    private RegistrarseDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private RegistradoInUser user = null;

    // private constructor : singleton access
    private RegistrarseRepository(RegistrarseDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static RegistrarseRepository getInstance(RegistrarseDataSource dataSource) {
        if (instance == null) {
            instance = new RegistrarseRepository(dataSource);
        }
        return instance;
    }

    public boolean isRegistrado() {
        return user != null;
    }

    public void logout() {
        user = null;
        dataSource.logout();
    }

    private void setRegistradoInUser(RegistradoInUser user) {
        this.user = user;
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<RegistradoInUser> registrarse(String seudonimo, String contrasenia, String confirmarContrasenia,
                                                String correo, String fechaNacimiento, boolean responsable) {
        // handle registro
        Result<RegistradoInUser> result = dataSource.registrarse(seudonimo, contrasenia,
                confirmarContrasenia, correo, fechaNacimiento, responsable);
        if (result instanceof Result.Success) {
            setRegistradoInUser(((Result.Success<RegistradoInUser>) result).getData());
        }
        return result;
    }

}
