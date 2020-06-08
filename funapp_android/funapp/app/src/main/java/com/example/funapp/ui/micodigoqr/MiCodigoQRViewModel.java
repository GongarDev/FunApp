package com.example.funapp.ui.micodigoqr;

import android.graphics.Bitmap;
import android.widget.ImageView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.funapp.R;
import com.example.funapp.models.Usuario;
import com.google.zxing.BarcodeFormat;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MiCodigoQRViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private MutableLiveData<Bitmap> imgCodigoQR;

    public MiCodigoQRViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Este es tu código QR de usuario. Úsalo para hacer amigos u otras actividades.");
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<Bitmap> getCodigoQR(Usuario usuario) {
        if (imgCodigoQR==null){
            imgCodigoQR= new MutableLiveData<>();
            try {
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                imgCodigoQR.setValue(barcodeEncoder.encodeBitmap("usuario/"+
                        usuario.getId_usuario()+"/"+usuario.getSeudonimo()+"/"+usuario.getFecha_ingreso().toString(),
                        BarcodeFormat.QR_CODE, 400, 400));
            } catch(Exception e) {
            }
        }
        return imgCodigoQR;
    }
}