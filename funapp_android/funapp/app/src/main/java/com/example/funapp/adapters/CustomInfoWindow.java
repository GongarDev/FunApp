package com.example.funapp.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.funapp.R;
import com.example.funapp.models.Evento;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import java.time.format.DateTimeFormatter;

//Esta clase sirve para construir el layout de información que aparece en el marcador de googleMaps.

public class CustomInfoWindow implements GoogleMap.InfoWindowAdapter {

    private View view;
    private Context context;

    public CustomInfoWindow(Context context) {
        this.context = context;
        view= LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);

    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View getInfoContents(Marker marker) {

        TextView title= view.findViewById(R.id.window_info_title);
        title.setText(marker.getTitle());

        TextView description= view.findViewById(R.id.window_info_desc);
        Evento e = (Evento)marker.getTag();
        description.setText(e.getFecha_evento_LocalDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
        + " a las " + e.getHora_inicio());

        ImageView imageView = view.findViewById(R.id.icon_info);
        switch (marker.getSnippet()) {
            case "Cultura local":
                imageView.setImageResource(R.drawable.cultura);
                break;
            case "Espectáculos":
                imageView.setImageResource(R.drawable.espectaculos);
                break;
            case "Gastronomía":
                imageView.setImageResource(R.drawable.gastronomia);
                break;
            case "Entretenimiento":
                imageView.setImageResource(R.drawable.entretenimiento);
                break;
            case "Deporte":
                imageView.setImageResource(R.drawable.deporte);
                break;
            case "Tecnología":
                imageView.setImageResource(R.drawable.tecnologia);
                break;
            case "Benéficos":
                imageView.setImageResource(R.drawable.beneficos);
                break;
            case "Ponencias":
                imageView.setImageResource(R.drawable.ponencias);
                break;
        }

        return view;
    }
}

