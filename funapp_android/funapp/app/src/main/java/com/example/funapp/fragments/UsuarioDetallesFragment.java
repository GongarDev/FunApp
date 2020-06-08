package com.example.funapp.fragments;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.funapp.R;
import com.example.funapp.adapters.EventoAmigosAdapter;
import com.example.funapp.adapters.EventoSuscritoAdapter;
import com.example.funapp.models.Evento;
import com.example.funapp.models.Usuario;
import com.example.funapp.ui.suscripciones.SuscripcionesViewModel;
import com.example.funapp.util.Protocolo;
import com.example.funapp.viewModels.UsuarioDetallesViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.CONNECTIVITY_SERVICE;

public class UsuarioDetallesFragment extends Fragment implements Protocolo {

    private UsuarioDetallesViewModel usuarioDetallesViewModel;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private EventoAmigosAdapter adapter;
    private List<Evento> eventosList = new ArrayList<>();
    ImageView imgvUsuario;
    TextView tvNombreUsuario;
    TextView tvFechaIngreso;
    TextView tvDesuscribirse;
    private OnEventoSelected callback;
    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    private String mensaje;
    private Gson gson;
    private Integer estadoSesion;

    public UsuarioDetallesFragment() {
        this.gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_usuario_detalles, container, false);

        imgvUsuario = view.findViewById(R.id.imgvUsuarioSelecionado);
        tvNombreUsuario = view.findViewById(R.id.tvUsuarioSeleccionadoNombre);
        tvFechaIngreso = view.findViewById(R.id.tvUsuarioSeleccionadoFechaIngreso);
        tvDesuscribirse = view.findViewById(R.id.tvEliminarAmigo);

        recyclerView = view.findViewById(R.id.rvEventosAmigos);
        layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        recyclerView.setItemViewCacheSize(20);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void mostrarUsuario(final Usuario usuarioAmigo, Usuario usuario) {

        tvNombreUsuario.setText(usuarioAmigo.getSeudonimo());
        tvFechaIngreso.setText("Usuario desde el "+usuarioAmigo.getFecha_ingreso_LocalDate().format(
                DateTimeFormatter.ofPattern("dd-MM-yyyy")));

        switch (usuarioAmigo.getImagen()) {
            case "avatar1":
                imgvUsuario.setImageResource(R.drawable.avatar1);
                break;
            case "avatar2":
                imgvUsuario.setImageResource(R.drawable.avatar2);
                break;
            case "avatar3":
                imgvUsuario.setImageResource(R.drawable.avatar3);
                break;
            case "avatar4":
                imgvUsuario.setImageResource(R.drawable.avatar4);
                break;
            case "avatar5":
                imgvUsuario.setImageResource(R.drawable.avatar5);
                break;
            case "avatar6":
                imgvUsuario.setImageResource(R.drawable.avatar6);
                break;
            case "avatar7":
                imgvUsuario.setImageResource(R.drawable.avatar7);
                break;
            case "avatar8":
                imgvUsuario.setImageResource(R.drawable.avatar8);
                break;
            case "avatar9":
                imgvUsuario.setImageResource(R.drawable.avatar9);
                break;
            case "avatar10":
                imgvUsuario.setImageResource(R.drawable.avatar10);
                break;
            case "avatar11":
                imgvUsuario.setImageResource(R.drawable.avatar11);
                break;
            case "avatar12":
                imgvUsuario.setImageResource(R.drawable.avatar12);
                break;
            case "avatar13":
                imgvUsuario.setImageResource(R.drawable.avatar13);
                break;
            case "avatar14":
                imgvUsuario.setImageResource(R.drawable.avatar14);
                break;
            case "avatar15":
                imgvUsuario.setImageResource(R.drawable.avatar15);
                break;
            case "avatar16":
                imgvUsuario.setImageResource(R.drawable.avatar16);
                break;
            case "avatar17":
                imgvUsuario.setImageResource(R.drawable.avatar17);
                break;
            case "avatar18":
                imgvUsuario.setImageResource(R.drawable.avatar18);
                break;
            case "avatar19":
                imgvUsuario.setImageResource(R.drawable.avatar19);
                break;
            case "avatar20":
                imgvUsuario.setImageResource(R.drawable.avatar20);
                break;
            case "avatar21":
                imgvUsuario.setImageResource(R.drawable.avatar21);
                break;
            case "avatar22":
                imgvUsuario.setImageResource(R.drawable.avatar22);
                break;
            case "avatar23":
                imgvUsuario.setImageResource(R.drawable.avatar23);
                break;
            case "avatar24":
                imgvUsuario.setImageResource(R.drawable.avatar24);
                break;
            case "avatar25":
                imgvUsuario.setImageResource(R.drawable.avatar25);
                break;
            default:
                imgvUsuario.setImageResource(R.drawable.avatar0);
                break;
        }

        usuarioDetallesViewModel =
                ViewModelProviders.of(this).get(UsuarioDetallesViewModel.class);

        ConnectivityManager connectivityManager = (ConnectivityManager) this.getContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = info != null && info.isConnected();

        if (isConnected) {

            usuarioDetallesViewModel.getEventosSuscritos(usuarioAmigo.getId_usuario()).observe(this, new Observer<List<Evento>>() {
                @Override
                public void onChanged(List<Evento> eventos) {

                    if (eventos != null) {
                        eventosList = eventos;

                        adapter = new EventoAmigosAdapter(eventosList, R.layout.list_item_evento_amigos, getActivity(), new EventoAmigosAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(Evento evento, int position) {
                                callback.OnEventoSelected(evento);
                            }
                        });
                        recyclerView.setAdapter(adapter);
                    }
                }
            });
        }

        tvDesuscribirse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupConfirmar(usuarioAmigo.getId_usuario(), usuario.getId_usuario());
                if(estadoSesion==ACTUALIZAR_EXITO){
                    Snackbar.make(v, "Se ha dejado de seguir al usuario", Snackbar.LENGTH_LONG)
                            .setAction("Cerrar", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {

                                }
                            })
                            .setActionTextColor(getResources().getColor(android.R.color.holo_red_light))
                            .show();
                }else if (estadoSesion==ACTUALIZAR_EXITO){
                    Snackbar.make(v, "No se puede dejar de seguir al usuario", Snackbar.LENGTH_LONG)
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
    }

    private void popupConfirmar(int id_usuarioAmigo, int id_usuario) {

        builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.popup_confirmacion, null);

        builder.setView(view);

        dialog = builder.create();
        dialog.show();

        final TextView tvTitulo = view.findViewById(R.id.tvPopupConfirmacion);
        tvTitulo.setText("¿Estás seguro de dejar de seguir?");
        Button bConfirmar = view.findViewById(R.id.bPopupConfirmar);
        Button bCancelar = view.findViewById(R.id.bPopupCancelar);
        bConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                estadoSesion=usuarioDetallesViewModel.eliminarAmigo(id_usuarioAmigo, id_usuario);
                dialog.dismiss();
                getActivity().finish();
            }
        });

        bCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               dialog.dismiss();
            }
        });
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callback = (OnEventoSelected) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + "Debería implementar el interfaz OnEventoSelected");
        }
    }

    public interface OnEventoSelected {
        public void OnEventoSelected(Evento evento);
    }
}
