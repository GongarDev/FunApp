package com.example.funapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.funapp.R;
import com.example.funapp.adapters.EventoHistorialAdapter;
import com.example.funapp.adapters.PublicacionAdapter;
import com.example.funapp.fragments.EventoDetallesFragment;
import com.example.funapp.models.Atributo;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Publicacion;
import com.example.funapp.models.Tematica;
import com.example.funapp.models.Usuario;
import com.example.funapp.models.Valoracion;
import com.example.funapp.ui.amigos.AmigosFragment;
import com.example.funapp.ui.amigos.AmigosViewModel;
import com.example.funapp.ui.explorar.EventosTematica.EventosTematicaActivity;
import com.example.funapp.ui.explorar.ExplorarFragment;
import com.example.funapp.ui.historial.HistorialFragment;
import com.example.funapp.ui.inicio.InicioFragment;
import com.example.funapp.ui.mapa.MapaFragment;
import com.example.funapp.ui.miseventos.MisEventosFragment;
import com.example.funapp.ui.notificaciones.NotificacionesFragment;
import com.example.funapp.ui.suscripciones.SuscripcionesFragment;
import com.example.funapp.ui.suscripciones.SuscripcionesViewModel;
import com.example.funapp.util.Protocolo;
import com.example.funapp.viewModels.EstadisticasViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Protocolo, MisEventosFragment.OnItemMisEventosSelected,
        MapaFragment.OnEventoMapaSelected, HistorialFragment.OnEventoHistorialSelected,
        ExplorarFragment.OnItemExplorarSelected, SuscripcionesFragment.OnEventoSelected,
        InicioFragment.OnEventoSelected, EventoDetallesFragment.OnPublicacionSelected,
        AmigosFragment.OnAmigoSelected, NotificacionesFragment.OnEventoSelected {

    private AppBarConfiguration appBarConfiguration;
    private NavController navController;
    private DrawerLayout drawer;
    private NavigationView navView;
    private Usuario usuario;
    private Integer tipoUsuario;
    private int id_EventoSuscrito;
    private SuscripcionesViewModel suscripcionesViewModel;
    private EstadisticasViewModel estadisticasViewModel;
    private AmigosViewModel amigosViewModel;
    private List<Atributo> atributoList;
    private List<Evento> eventosList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        if (intent != null) {
            usuario = (Usuario) intent.getSerializableExtra("usuario");
            tipoUsuario = (Integer) intent.getSerializableExtra("tipoUsuario");
        }

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        navView = findViewById(R.id.main_navigation_view);
        BottomNavigationView bottomNavigationView = findViewById(R.id.main_bottom_navigation_view);
        drawer = findViewById(R.id.main_drawer_layout);

        if (tipoUsuario == SESION_ABIERTA_RESPONSABLE) {
            bottomNavigationView.getMenu().removeItem(R.id.navigation_suscripciones);
        } else {
            navView.getMenu().removeItem(R.id.navigation_mis_eventos);
        }

        appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_inicio, R.id.navigation_notificaciones, R.id.navigation_explorar, R.id.navigation_suscripciones,
                R.id.navigation_mapa, R.id.navigation_micodigoqr, R.id.navigation_amigos, R.id.navigation_historial,
                R.id.navigation_cuenta, R.id.navigation_mis_eventos, R.id.navigation_cerrarsesion).setDrawerLayout(drawer).build();

        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        actualizarPerfilEstadisticas();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void actualizarPerfilEstadisticas() {
        View hView = navView.getHeaderView(0);
        ImageView imgVPerfilEstado = (ImageView) hView.findViewById(R.id.imgVPerfilEstado);
        switch (usuario.getImagen()) {
            case "avatar1":
                imgVPerfilEstado.setImageResource(R.drawable.avatar1);
                break;
            case "avatar2":
                imgVPerfilEstado.setImageResource(R.drawable.avatar2);
                break;
            case "avatar3":
                imgVPerfilEstado.setImageResource(R.drawable.avatar3);
                break;
            case "avatar4":
                imgVPerfilEstado.setImageResource(R.drawable.avatar4);
                break;
            case "avatar5":
                imgVPerfilEstado.setImageResource(R.drawable.avatar5);
                break;
            case "avatar6":
                imgVPerfilEstado.setImageResource(R.drawable.avatar6);
                break;
            case "avatar7":
                imgVPerfilEstado.setImageResource(R.drawable.avatar7);
                break;
            case "avatar8":
                imgVPerfilEstado.setImageResource(R.drawable.avatar8);
                break;
            case "avatar9":
                imgVPerfilEstado.setImageResource(R.drawable.avatar9);
                break;
            case "avatar10":
                imgVPerfilEstado.setImageResource(R.drawable.avatar10);
                break;
            case "avatar11":
                imgVPerfilEstado.setImageResource(R.drawable.avatar11);
                break;
            case "avatar12":
                imgVPerfilEstado.setImageResource(R.drawable.avatar12);
                break;
            case "avatar13":
                imgVPerfilEstado.setImageResource(R.drawable.avatar13);
                break;
            case "avatar14":
                imgVPerfilEstado.setImageResource(R.drawable.avatar14);
                break;
            case "avatar15":
                imgVPerfilEstado.setImageResource(R.drawable.avatar15);
                break;
            case "avatar16":
                imgVPerfilEstado.setImageResource(R.drawable.avatar16);
                break;
            case "avatar17":
                imgVPerfilEstado.setImageResource(R.drawable.avatar17);
                break;
            case "avatar18":
                imgVPerfilEstado.setImageResource(R.drawable.avatar18);
                break;
            case "avatar19":
                imgVPerfilEstado.setImageResource(R.drawable.avatar19);
                break;
            case "avatar20":
                imgVPerfilEstado.setImageResource(R.drawable.avatar20);
                break;
            case "avatar21":
                imgVPerfilEstado.setImageResource(R.drawable.avatar21);
                break;
            case "avatar22":
                imgVPerfilEstado.setImageResource(R.drawable.avatar22);
                break;
            case "avatar23":
                imgVPerfilEstado.setImageResource(R.drawable.avatar23);
                break;
            case "avatar24":
                imgVPerfilEstado.setImageResource(R.drawable.avatar24);
                break;
            case "avatar25":
                imgVPerfilEstado.setImageResource(R.drawable.avatar25);
                break;
            default:
                imgVPerfilEstado.setImageResource(R.drawable.avatar0);
                break;
        }
        TextView tvPerfilEstadoNombre = (TextView) hView.findViewById(R.id.tvPerfilEstadoNombre);
        tvPerfilEstadoNombre.setText(usuario.getSeudonimo());
        if (tipoUsuario == SESION_ABIERTA_ESTANDAR) {
            cargarAtributosEstandar(hView);
        } else if (tipoUsuario == SESION_ABIERTA_RESPONSABLE) {
            cargarAtributosResponsable(hView);
        }
    }

    @Override
    public void OnEventoSelected(Evento evento) {
        Intent intent = new Intent(this, EventoDetallesActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void OnItemMisEventosSelected(Evento evento) {
        Intent intent = new Intent(this, EventoDetallesActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);
        intent.putExtra("seccion", MIS_EVENTOS);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void OnEventoMapaSelected(Evento evento) {
        Intent intent = new Intent(this, EventoDetallesActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);
        intent.putExtra("seccion", MAPA_EVENTOS);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onItemExplorarSelected(Tematica tematica) {
        Intent intent = new Intent(this, EventosTematicaActivity.class);
        intent.putExtra("tematica", tematica);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);


        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void OnEventoHistorialSelected(Evento evento) {
        Intent intent = new Intent(this, EventoDetallesActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);
        intent.putExtra("seccion", HISTORIAL_EVENTOS);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void OnPublicacionSelected(Evento evento) {
        Intent intent = new Intent(this, PublicacionesActivity.class);
        intent.putExtra("evento", evento);
        intent.putExtra("usuario", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void OnAmigoSelected(Usuario usuario) {
        Intent intent = new Intent(this, UsuarioDetallesActivity.class);
        intent.putExtra("usuario", this.usuario);
        intent.putExtra("usuarioAmigo", usuario);
        intent.putExtra("tipoUsuario", tipoUsuario);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void cargarAtributosEstandar(View hView) {
        estadisticasViewModel =
                ViewModelProviders.of(this).get(EstadisticasViewModel.class);
        estadisticasViewModel.getAtributosList(this.usuario.getId_usuario()).observe(this, new Observer<List<Atributo>>() {
            @Override
            public void onChanged(List<Atributo> atributos) {
                if (atributos != null) {
                    atributoList = atributos;
                    int expTotal = 0;
                    navView = findViewById(R.id.main_navigation_view);
                    View hView = navView.getHeaderView(0);
                    for (Atributo a : atributoList) {
                        expTotal += a.getExperiencia();
                        //Cultural
                        if (a.getId_atributo() == 1) {
                            int culturalLvl = a.getExperiencia() / 100;
                            int culturaExp = a.getExperiencia() % 100;
                            ProgressBar pbCultural = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoCultural);
                            pbCultural.setMax(100);
                            pbCultural.setProgress(culturaExp);
                            TextView tvEstadoCulturallvl = (TextView) hView.findViewById(R.id.tvEstadoCulturallvl);
                            tvEstadoCulturallvl.setText("lvl " + culturalLvl);
                        }
                        //Gastronómico
                        else if (a.getId_atributo() == 2) {
                            int gastronómicoLvl = a.getExperiencia() / 100;
                            int gastronómicoExp = a.getExperiencia() % 100;
                            ProgressBar pbGastronomico = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoGastronomico);
                            pbGastronomico.setMax(100);
                            pbGastronomico.setProgress(gastronómicoExp);
                            TextView tvEstadoGastronomicolvl = (TextView) hView.findViewById(R.id.tvEstadoGastronomicolvl);
                            tvEstadoGastronomicolvl.setText("lvl " + gastronómicoLvl);
                        }
                        //Social
                        else if (a.getId_atributo() == 3) {
                            int socialLvl = a.getExperiencia() / 100;
                            int socialExp = a.getExperiencia() % 100;
                            ProgressBar pbSocial = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoSocial);
                            pbSocial.setMax(100);
                            pbSocial.setProgress(socialExp);
                            TextView tvEstadoSociallvl = (TextView) hView.findViewById(R.id.tvEstadoSociallvl);
                            tvEstadoSociallvl.setText("lvl " + socialLvl);
                        }
                        //Deportivo
                        else if (a.getId_atributo() == 4) {
                            int deportivoLvl = a.getExperiencia() / 100;
                            int deportivoExp = a.getExperiencia() % 100;
                            ProgressBar pbDeportivo = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoDeportivo);
                            pbDeportivo.setMax(100);
                            pbDeportivo.setProgress(deportivoExp);
                            TextView tvEstadoDeportivolvl = (TextView) hView.findViewById(R.id.tvEstadoDeportivolvl);
                            tvEstadoDeportivolvl.setText("lvl " + deportivoLvl);
                        }
                        //Entretenimiento
                        else if (a.getId_atributo() == 5) {
                            int entretenimientoLvl = a.getExperiencia() / 100;
                            int entretenimientoExp = a.getExperiencia() % 100;
                            ProgressBar pbEntretenimiento = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoEntretenimiento);
                            pbEntretenimiento.setMax(100);
                            pbEntretenimiento.setProgress(entretenimientoExp);
                            TextView tvEstadoEntretenimientolvl = (TextView) hView.findViewById(R.id.tvEstadoEntretenimientolvl);
                            tvEstadoEntretenimientolvl.setText("lvl " + entretenimientoLvl);
                        }
                    }
                    int nivelUsuario = (expTotal / 100)+1;
                    int UsuarioExp = (expTotal % 100);
                    TextView tvPerfilEstadoNivel = (TextView) hView.findViewById(R.id.tvPerfilEstadoNivel);
                    tvPerfilEstadoNivel.setText("Nivel " + nivelUsuario);
                    ProgressBar pbNivelTotal = (ProgressBar) hView.findViewById(R.id.progressBarNivelTotal);
                    pbNivelTotal.setMax(100);
                    pbNivelTotal.setProgress(UsuarioExp);
                    TextView tvValoracionMedia = (TextView) hView.findViewById(R.id.tvValoracionMedia);
                    tvValoracionMedia.setVisibility(View.GONE);
                    RatingBar rbNivelTotal = (RatingBar) hView.findViewById(R.id.ratingBarValoracionMedia);
                    rbNivelTotal.setVisibility(View.GONE);
                }
            }
        });
    }

    public void cargarAtributosResponsable(View hView) {
        estadisticasViewModel =
                ViewModelProviders.of(this).get(EstadisticasViewModel.class);
        estadisticasViewModel.getEventos(usuario.getId_usuario()).observe(this, new Observer<List<Evento>>() {
            @Override
            public void onChanged(List<Evento> eventos) {
                if (eventos != null) {
                    eventosList = eventos;
                    int nivelUsuario = 0;
                    int UsuarioExp = 0;
                    TextView tvPerfilEstadoNivel = (TextView) hView.findViewById(R.id.tvPerfilEstadoNivel);
                    ProgressBar pbNivelTotal = (ProgressBar) hView.findViewById(R.id.progressBarNivelTotal);

                    if (eventos.size() != 0) {
                        nivelUsuario = (eventos.size() / 5)+1;
                        UsuarioExp = (eventos.size() % 5);
                        tvPerfilEstadoNivel.setText("Nivel " + nivelUsuario);
                        pbNivelTotal.setMax(100);
                        pbNivelTotal.setProgress(UsuarioExp);
                    } else {
                        tvPerfilEstadoNivel.setText("Nivel " + nivelUsuario);
                        pbNivelTotal.setMax(100);
                        pbNivelTotal.setProgress(UsuarioExp);
                    }

                    RatingBar rbValoracionMedia = (RatingBar) hView.findViewById(R.id.ratingBarValoracionMedia);
                    float valoracionMedia = 0.00f;
                    float valoracionMediaTotal = 0.00f;
                    int countValoraciones = 1;
                    for (Evento evento : eventosList) {
                        List<Valoracion> valoracionesList = new ArrayList<>();
                        valoracionesList = estadisticasViewModel.cargarValoraciones(evento.getId_evento());

                        if (valoracionesList != null) {
                            for (Valoracion valoracion : valoracionesList) {
                                valoracionMedia =valoracionMedia + valoracion.getPuntaje();
                                countValoraciones++;
                            }
                        }
                    }
                    rbValoracionMedia.setRating(valoracionMedia/countValoraciones);
                    rbValoracionMedia.setIsIndicator(true);
                }
            }
        });
        ProgressBar pbCultural = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoCultural);
        TextView tvEstadoCulturallvl = (TextView) hView.findViewById(R.id.tvEstadoCulturallvl);
        TextView tvEstadoCultural = (TextView) hView.findViewById(R.id.tvPerfilEstadoCultural);
        pbCultural.setVisibility(View.GONE);
        tvEstadoCulturallvl.setVisibility(View.GONE);
        tvEstadoCultural.setVisibility(View.GONE);

        ProgressBar pbGastronomico = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoGastronomico);
        TextView tvEstadoGastronomicolvl = (TextView) hView.findViewById(R.id.tvEstadoGastronomicolvl);
        TextView tvEstadoGastronomico = (TextView) hView.findViewById(R.id.tvPerfilEstadoGastronomico);
        pbGastronomico.setVisibility(View.GONE);
        tvEstadoGastronomicolvl.setVisibility(View.GONE);
        tvEstadoGastronomico.setVisibility(View.GONE);

        ProgressBar pbSocial = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoSocial);
        TextView tvEstadoSociallvl = (TextView) hView.findViewById(R.id.tvEstadoSociallvl);
        TextView tvEstadoSocial = (TextView) hView.findViewById(R.id.tvPerfilEstadoSocial);
        pbSocial.setVisibility(View.GONE);
        tvEstadoSociallvl.setVisibility(View.GONE);
        tvEstadoSocial.setVisibility(View.GONE);

        ProgressBar pbDeportivo = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoDeportivo);
        TextView tvEstadoDeportivolvl = (TextView) hView.findViewById(R.id.tvEstadoDeportivolvl);
        TextView tvEstadoDeportivo = (TextView) hView.findViewById(R.id.tvPerfilEstadoDeportivo);
        pbDeportivo.setVisibility(View.GONE);
        tvEstadoDeportivolvl.setVisibility(View.GONE);
        tvEstadoDeportivo.setVisibility(View.GONE);

        ProgressBar pbEntretenimiento = (ProgressBar) hView.findViewById(R.id.pBPerfilEstadoEntretenimiento);
        TextView tvEstadoEntretenimientolvl = (TextView) hView.findViewById(R.id.tvEstadoEntretenimientolvl);
        TextView tvEstadoEntretenimiento = (TextView) hView.findViewById(R.id.tvPerfilEstadoEntretenimiento);
        pbEntretenimiento.setVisibility(View.GONE);
        tvEstadoEntretenimientolvl.setVisibility(View.GONE);
        tvEstadoEntretenimiento.setVisibility(View.GONE);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Error al leer el código.", Toast.LENGTH_LONG).show();
            } else {
                String[] datos = result.getContents().split("/");

                if (datos[0].equals("evento")) {
                    this.id_EventoSuscrito = Integer.parseInt(datos[1]);
                    suscripcionesViewModel =
                            ViewModelProviders.of(this).get(SuscripcionesViewModel.class);
                    Integer estadoSesion = suscripcionesViewModel.lecturaQR(this.id_EventoSuscrito, this.usuario.getId_usuario());
                    if (estadoSesion == GANADO_PUNTOS_CODIGO) {
                        Snackbar.make(getCurrentFocus(), "Has ganado puntos por asistir al evento.", Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else if (estadoSesion == NO_SUSCRITO_CODIGO) {
                        Snackbar.make(getCurrentFocus(), "Debes estar suscrito al evento primero.", Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
                } else if (datos[0].equals("usuario")) {
                    this.id_EventoSuscrito = Integer.parseInt(datos[1]);
                    amigosViewModel =
                            ViewModelProviders.of(this).get(AmigosViewModel.class);
                    Integer estadoSesion = amigosViewModel.lecturaQR(this.id_EventoSuscrito, this.usuario.getId_usuario());
                    if (estadoSesion == AMIGOS_SIGUIENDO) {
                        Snackbar.make(getCurrentFocus(), "Ahora eres amigo de " + datos[2], Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    } else if (estadoSesion == AMIGOS_EXISTE_SEGUIMIENTO) {
                        Snackbar.make(getCurrentFocus(), "Ya sigues a " + datos[2], Snackbar.LENGTH_LONG)
                                .setAction("Cerrar", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                    }
                                })
                                .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                                .show();
                    }
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    public Integer getTipoUsuario() {
        return this.tipoUsuario;
    }

}
