package com.example.funapp.ui.cuenta;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.funapp.R;
import com.example.funapp.activities.MainActivity;
import com.example.funapp.adapters.AmigoAdapter;
import com.example.funapp.adapters.AvatarAdapter;
import com.example.funapp.adapters.EventoSuscritoAdapter;
import com.example.funapp.login.LoginActivity;
import com.example.funapp.models.Entidad;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Incidencia;
import com.example.funapp.models.Tematica;
import com.example.funapp.models.Ubicacion;
import com.example.funapp.models.Usuario;
import com.example.funapp.models.UsuarioEstandar;
import com.example.funapp.models.UsuarioResponsable;
import com.example.funapp.ui.miseventos.crear_editar_evento.CrearEditarEventoActivity;
import com.example.funapp.util.Protocolo;
import com.google.android.material.snackbar.Snackbar;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CuentaFragment extends Fragment implements Protocolo {

    private CuentaViewModel cuentaViewModel;
    private Usuario usuario;
    private Integer tipoUsuario;
    private int estadoSesion;
    private ImageView imgvCuenta;
    private static final String CERO = "0";
    private static final String BARRA = "-";
    public final Calendar c = Calendar.getInstance();
    final int mes = c.get(Calendar.MONTH);
    final int dia = c.get(Calendar.DAY_OF_MONTH);
    final int anio = c.get(Calendar.YEAR);
    private MessageDigest md;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    EditText etCuentaFechaNacimiento;
    ImageButton ibObtenerFecha;

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private AvatarAdapter avatarAdapter;
    private List<String> avataresList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        usuario = null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cuentaViewModel =
                ViewModelProviders.of(this).get(CuentaViewModel.class);
        this.usuario = ((MainActivity) getActivity()).getUsuario();
        this.tipoUsuario = ((MainActivity) getActivity()).getTipoUsuario();

        View root = null;

        if (this.tipoUsuario == SESION_ABIERTA_RESPONSABLE) {

            root = inflater.inflate(R.layout.fragment_cuenta_responsable, container, false);

            final EditText etCuentaSeudonimo = root.findViewById(R.id.etCuentaSeudonimo);
            final EditText etCuentaContrasenia = root.findViewById(R.id.etCuentaContrasenia);
            final EditText etCuentaContraseniaConfirmar = root.findViewById(R.id.etCuentaContraseniaConfirmar);
            etCuentaFechaNacimiento = root.findViewById(R.id.etCuentaFechaNacimiento);
            final EditText etCuentaCorreo = root.findViewById(R.id.etCuentaCorreo);
            ibObtenerFecha = root.findViewById(R.id.ibMiCuentaFecha);
            final Button confirmarButton = root.findViewById(R.id.bCuentaConfirmar);

            etCuentaSeudonimo.setText(usuario.getSeudonimo());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            etCuentaFechaNacimiento.setText(usuario.getFecha_ingreso_LocalDate().format(formatter));

            //Responsable
            final EditText etCuentaNombre = root.findViewById(R.id.etCuentaNombre);
            final EditText etCuentaApellidos = root.findViewById(R.id.etCuentaApellidos);
            final EditText etCuentaDNI = root.findViewById(R.id.etCuentaDNI);
            final EditText etCuentaTelefono = root.findViewById(R.id.etCuentaTelefono);
            final Switch switchConfirmar = root.findViewById(R.id.switchCuentaConfirmar);
            imgvCuenta = root.findViewById(R.id.imgvAvatar);

            //Entidad
            final EditText etCuentaNombre2 = root.findViewById(R.id.etCuentaNombre2);
            final EditText etEntidadNIF = root.findViewById(R.id.etEntidadNIF);
            final EditText etEntidadCalle = root.findViewById(R.id.etEntidadCalle);
            final EditText etEntidadProvincia = root.findViewById(R.id.etEntidadProvincia);
            final EditText etEntidadLocalidad = root.findViewById(R.id.etEntidadLocalidad);
            final EditText etEntidadTelefono = root.findViewById(R.id.etEntidadTelefono);
            final EditText etEntidadCodigoPostal = root.findViewById(R.id.etEntidadCodigoPostal);

            final UsuarioResponsable[] usuarioResponsable = new UsuarioResponsable[1];
            cuentaViewModel.getUsuario(usuario.getId_usuario()).observe(getViewLifecycleOwner(), new Observer<UsuarioResponsable>() {
                @Override
                public void onChanged(@Nullable UsuarioResponsable usuario) {
                    usuarioResponsable[0] = usuario;
                    etCuentaNombre.setText(usuario.getNombre());
                    etCuentaApellidos.setText(usuario.getApellido());
                    etCuentaDNI.setText(usuario.getDni());
                    etCuentaTelefono.setText(usuario.getTelefono());
                    setImagenAvatar();
                }
            });

            final Entidad[] entidad = {new Entidad()};
            cuentaViewModel.getEntidad(usuario.getId_usuario()).observe(getViewLifecycleOwner(), new Observer<Entidad>() {
                @Override
                public void onChanged(@Nullable Entidad e) {
                    entidad[0] = e;
                    if (e != null) {
                        etCuentaNombre2.setText(e.getNombre());
                        etEntidadNIF.setText(e.getNif());
                        etEntidadCalle.setText(e.getCalle());
                        etEntidadProvincia.setText(e.getProvincia());
                        etEntidadLocalidad.setText(e.getLocalidad());
                        etEntidadTelefono.setText(e.getTelefono());
                        etEntidadCodigoPostal.setText(e.getCodigo_postal());
                    }
                }
            });

            ibObtenerFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obtenerFecha();
                }
            });

            confirmarButton.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {

                    if (!TextUtils.isEmpty(etCuentaSeudonimo.getText()) &&
                            !TextUtils.isEmpty(etCuentaContrasenia.getText()) &&
                            !TextUtils.isEmpty(etCuentaContraseniaConfirmar.getText()) &&
                            !TextUtils.isEmpty(etCuentaFechaNacimiento.getText()) &&
                            !TextUtils.isEmpty(etCuentaCorreo.getText()) &&
                            !TextUtils.isEmpty(etCuentaNombre.getText()) &&
                            !TextUtils.isEmpty(etCuentaApellidos.getText()) &&
                            !TextUtils.isEmpty(etCuentaDNI.getText()) &&
                            !TextUtils.isEmpty(etCuentaTelefono.getText()) &&
                            !TextUtils.isEmpty(etCuentaNombre2.getText()) &&
                            !TextUtils.isEmpty(etEntidadNIF.getText()) &&
                            !TextUtils.isEmpty(etEntidadCalle.getText()) &&
                            !TextUtils.isEmpty(etEntidadProvincia.getText()) &&
                            !TextUtils.isEmpty(etEntidadLocalidad.getText()) &&
                            !TextUtils.isEmpty(etEntidadTelefono.getText()) &&
                            !TextUtils.isEmpty(etEntidadCodigoPostal.getText())) {

                        if (!switchConfirmar.isChecked()) {
                            Snackbar.make(view, "Tienes que aceptar las condiciones de responsabilidad.", Snackbar.LENGTH_LONG)
                                    .setAction("Cerrar", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {

                                        }
                                    })
                                    .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                    .show();
                        } else {
                            String fechaEvento = etCuentaFechaNacimiento.getText().toString();
                            Date fechaNacimientoDate = null;
                            try {
                                fechaNacimientoDate = new SimpleDateFormat("dd-MM-yyyy").parse(fechaEvento);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            UsuarioResponsable usuarioRes = new UsuarioResponsable(etCuentaDNI.getText().toString(), etCuentaNombre.getText().toString(),
                                    etCuentaApellidos.getText().toString(), etCuentaTelefono.getText().toString(), usuario.getId_usuario(), etCuentaSeudonimo.getText().toString(),
                                    etCuentaCorreo.getText().toString(), fechaNacimientoDate, null, encriptacion(etCuentaContrasenia.getText().toString()), usuario.getImagen());

                            estadoSesion = cuentaViewModel.actualizarUsuarioResponsable(usuarioRes);
                            if (estadoSesion == ACTUALIZAR_EXITO) {
                                Toast.makeText(getActivity(), "El usuario se ha actualizado con éxito", Toast.LENGTH_SHORT).show();
                            } else if (estadoSesion == ACTUALIZAR_FALLIDO) {
                                Toast.makeText(getActivity(), "No se ha podido actualizar el usuario", Toast.LENGTH_SHORT).show();
                            }

                            Entidad e = new Entidad(entidad[0].getId_entidad(), etCuentaNombre2.getText().toString(), etEntidadNIF.getText().toString(),
                                    etEntidadCalle.getText().toString(), etEntidadProvincia.getText().toString(),
                                    etEntidadLocalidad.getText().toString(), etEntidadCodigoPostal.getText().toString(),
                                    etEntidadTelefono.getText().toString(), usuario.getId_usuario());

                            estadoSesion = cuentaViewModel.actualizarEntidad(e);
                            if (estadoSesion == ACTUALIZAR_EXITO) {
                                ((MainActivity) getActivity()).actualizarPerfilEstadisticas();
                                Toast.makeText(getActivity(), "La entidad se ha actualizado con éxito", Toast.LENGTH_SHORT).show();
                            } else if (estadoSesion == ACTUALIZAR_FALLIDO) {
                                Toast.makeText(getActivity(), "No se ha podido actualizar la entidad", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Snackbar.make(view, "Tienes que completar todos los datos de perfil para confirmar.", Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
                }
            });

            imgvCuenta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupAvatar();
                }
            });

            TextView tvInicidencia = root.findViewById(R.id.tvReportar2);
            tvInicidencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupReportar();
                }
            });

            TextView tvDarBaja = root.findViewById(R.id.tvDarBajaClick);
            tvDarBaja.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupDarBaja();
                }
            });

        } else if (this.tipoUsuario == SESION_ABIERTA_ESTANDAR) {

            root = inflater.inflate(R.layout.fragment_cuenta_estandar, container, false);

            Usuario u = ((MainActivity) getActivity()).getUsuario();

            imgvCuenta = root.findViewById(R.id.imgvAvatar);
            final EditText etCuentaSeudonimo = root.findViewById(R.id.etCuentaSeudonimo);
            final EditText etCuentaContrasenia = root.findViewById(R.id.etCuentaContrasenia);
            final EditText etCuentaContraseniaConfirmar = root.findViewById(R.id.etCuentaContraseniaConfirmar);
            etCuentaFechaNacimiento = root.findViewById(R.id.etCuentaFechaNacimiento);
            final EditText etCuentaCorreo = root.findViewById(R.id.etCuentaCorreo);
            ibObtenerFecha = root.findViewById(R.id.ibMiCuentaFecha);
            final Button confirmarButton = root.findViewById(R.id.bCuentaConfirmar);

            imgvCuenta.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupAvatar();
                }
            });

            etCuentaSeudonimo.setText(usuario.getSeudonimo());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            etCuentaFechaNacimiento.setText(usuario.getFecha_ingreso_LocalDate().format(formatter));
            setImagenAvatar();
            ibObtenerFecha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    obtenerFecha();
                }
            });

            confirmarButton.setOnClickListener(new View.OnClickListener() {

                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(etCuentaSeudonimo.getText()) &&
                            !TextUtils.isEmpty(etCuentaContrasenia.getText()) &&
                            !TextUtils.isEmpty(etCuentaContraseniaConfirmar.getText()) &&
                            !TextUtils.isEmpty(etCuentaFechaNacimiento.getText()) &&
                            !TextUtils.isEmpty(etCuentaCorreo.getText())) {

                        String fechaEvento = etCuentaFechaNacimiento.getText().toString();
                        Date fechaNacimientoDate = null;
                        try {
                            fechaNacimientoDate = new SimpleDateFormat("dd-MM-yyyy").parse(fechaEvento);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        u.setSeudonimo(etCuentaSeudonimo.getText().toString());
                        u.setContrasenia(encriptacion(etCuentaContrasenia.getText().toString()));
                        u.setFecha_nac(fechaNacimientoDate);
                        u.setEmail(etCuentaCorreo.getText().toString());
                        u.setImagen(usuario.getImagen());

                        estadoSesion = cuentaViewModel.actualizarUsuarioEstandar(u);

                        if (estadoSesion == ACTUALIZAR_EXITO) {
                            ((MainActivity) getActivity()).actualizarPerfilEstadisticas();
                            Toast.makeText(getActivity(), "El usuario se ha actualizado con éxito", Toast.LENGTH_SHORT).show();
                        } else if (estadoSesion == ACTUALIZAR_FALLIDO) {
                            Toast.makeText(getActivity(), "No se ha podido actualizar el usuario", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Snackbar.make(view, "Tienes que completar todos los datos de perfil para confirmar.", Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
                }
            });

            TextView tvInicidencia = root.findViewById(R.id.tvReportar2);
            tvInicidencia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupReportar();
                }
            });

            TextView tvDarBaja = root.findViewById(R.id.tvDarBajaClick);
            tvDarBaja.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupDarBaja();
                }
            });
        }

        return root;
    }

    private void popupAvatar() {


        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_avatar, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        for (int i = 1; i <= 25; i++) {
            avataresList.add("avatar" + i);
        }

        recyclerView = view.findViewById(R.id.rvAvatar);
        layoutManager = new GridLayoutManager(getActivity(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        avatarAdapter = new AvatarAdapter(avataresList, R.layout.list_item_avatar, getActivity(), new AvatarAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String avatar, int position) {
                usuario.setImagen(avatar);
                setImagenAvatar();
                dialog.dismiss();
            }
        });
        recyclerView.setAdapter(avatarAdapter);
    }

    private void popupReportar() {

        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_incidencia, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
        final EditText etIncidencia = view.findViewById(R.id.etIncidencia);
        Button bEnviar = view.findViewById(R.id.bReportar);
        Button bCancelar = view.findViewById(R.id.bCancelarReporte);

        bEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(etIncidencia.getText())) {
                    Incidencia incidencia = new Incidencia(0, etIncidencia.getText().toString(),null, usuario.getId_usuario());
                    estadoSesion = cuentaViewModel.reportarIncidencia(incidencia);
                    if (estadoSesion == INSERTAR_EXITO) {
                        Snackbar.make(view, "Se ha enviado el reporte a FunApp, se revisará lo antes posible, gracias.", Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                        dialog.dismiss();
                    }
                }
            }
        });

        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    private void popupDarBaja() {

        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_confirmacion, null);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();

        final TextView tvTitulo = view.findViewById(R.id.tvPopupConfirmacion);
        tvTitulo.setText("¿Estás seguro que quieres darte de baja en FunApp?");
        Button bConfirmar = view.findViewById(R.id.bPopupConfirmar);
        Button bCancelar = view.findViewById(R.id.bPopupCancelar);
        bConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estadoSesion = cuentaViewModel.eliminarCuenta(usuario.getId_usuario());
                if (estadoSesion == ACTUALIZAR_EXITO) {
                    Intent intentAcceder = new Intent(getContext(), LoginActivity.class);
                    startActivity(intentAcceder);
                    Snackbar.make(view, "La cuenta se ha eliminado con éxito.", Snackbar.LENGTH_LONG)
                            .setAction("Cerrar", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    dialog.dismiss();
                    getActivity().finish();
                } else if (estadoSesion == ACTUALIZAR_FALLIDO) {
                    Snackbar.make(view, "Error al dar de baja.", Snackbar.LENGTH_LONG)
                            .setAction("Cerrar", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                    dialog.dismiss();
                }

            }
        });

        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void setImagenAvatar() {
        switch (usuario.getImagen()) {
            case "avatar1":
                imgvCuenta.setImageResource(R.drawable.avatar1);
                break;
            case "avatar2":
                imgvCuenta.setImageResource(R.drawable.avatar2);
                break;
            case "avatar3":
                imgvCuenta.setImageResource(R.drawable.avatar3);
                break;
            case "avatar4":
                imgvCuenta.setImageResource(R.drawable.avatar4);
                break;
            case "avatar5":
                imgvCuenta.setImageResource(R.drawable.avatar5);
                break;
            case "avatar6":
                imgvCuenta.setImageResource(R.drawable.avatar6);
                break;
            case "avatar7":
                imgvCuenta.setImageResource(R.drawable.avatar7);
                break;
            case "avatar8":
                imgvCuenta.setImageResource(R.drawable.avatar8);
                break;
            case "avatar9":
                imgvCuenta.setImageResource(R.drawable.avatar9);
                break;
            case "avatar10":
                imgvCuenta.setImageResource(R.drawable.avatar10);
                break;
            case "avatar11":
                imgvCuenta.setImageResource(R.drawable.avatar11);
                break;
            case "avatar12":
                imgvCuenta.setImageResource(R.drawable.avatar12);
                break;
            case "avatar13":
                imgvCuenta.setImageResource(R.drawable.avatar13);
                break;
            case "avatar14":
                imgvCuenta.setImageResource(R.drawable.avatar14);
                break;
            case "avatar15":
                imgvCuenta.setImageResource(R.drawable.avatar15);
                break;
            case "avatar16":
                imgvCuenta.setImageResource(R.drawable.avatar16);
                break;
            case "avatar17":
                imgvCuenta.setImageResource(R.drawable.avatar17);
                break;
            case "avatar18":
                imgvCuenta.setImageResource(R.drawable.avatar18);
                break;
            case "avatar19":
                imgvCuenta.setImageResource(R.drawable.avatar19);
                break;
            case "avatar20":
                imgvCuenta.setImageResource(R.drawable.avatar20);
                break;
            case "avatar21":
                imgvCuenta.setImageResource(R.drawable.avatar21);
                break;
            case "avatar22":
                imgvCuenta.setImageResource(R.drawable.avatar22);
                break;
            case "avatar23":
                imgvCuenta.setImageResource(R.drawable.avatar23);
                break;
            case "avatar24":
                imgvCuenta.setImageResource(R.drawable.avatar24);
                break;
            case "avatar25":
                imgvCuenta.setImageResource(R.drawable.avatar25);
                break;
            default:
                imgvCuenta.setImageResource(R.drawable.avatar0);
                break;
        }
    }

    public void obtenerFecha() {
        DatePickerDialog recogerFecha = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                //Esta variable lo que realiza es aumentar en uno el mes ya que comienza desde 0 = enero
                final int mesActual = month + 1;
                //Formateo el día obtenido: antepone el 0 si son menores de 10
                String diaFormateado = (dayOfMonth < 10) ? CERO + String.valueOf(dayOfMonth) : String.valueOf(dayOfMonth);
                //Formateo el mes obtenido: antepone el 0 si son menores de 10
                String mesFormateado = (mesActual < 10) ? CERO + String.valueOf(mesActual) : String.valueOf(mesActual);
                //Muestro la fecha con el formato deseado
                etCuentaFechaNacimiento.setText(diaFormateado + BARRA + mesFormateado + BARRA + year);
            }
            //Estos valores deben ir en ese orden, de lo contrario no mostrara la fecha actual
            /**
             *También puede cargar los valores que quiera
             */
        }, anio, mes, dia);
        //Muestro el widget
        recogerFecha.show();
    }

    public String encriptacion(String cadena) {

        String cadenaEncriptada = "";
        try {
            this.md = MessageDigest.getInstance("SHA-512");
            this.md.update(cadena.getBytes());
            byte[] mb = md.digest();
            cadenaEncriptada = String.copyValueOf(Hex.encodeHex(mb));
            System.out.println(cadenaEncriptada);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginActivity.class.getName()).log(Level.SEVERE, null, ex);
        }
        return cadenaEncriptada;
    }
}
