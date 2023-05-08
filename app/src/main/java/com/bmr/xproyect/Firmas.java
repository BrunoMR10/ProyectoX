package com.bmr.xproyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.github.gcacace.signaturepad.views.SignaturePad;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Firmas extends AppCompatActivity {
    private SignaturePad mSignaturePad;
    String[] Datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firmas);
        mSignaturePad = (SignaturePad) findViewById(R.id.signature_pad);
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {

            } else {
                Datos = extras.getStringArray("Datos");
            }
        } else {
            Datos = (String[]) savedInstanceState.getSerializable("Datos");
        }
        mSignaturePad.setOnSignedListener(new SignaturePad.OnSignedListener() {
            @Override
            public void onStartSigning() {
                //Toast.makeText(Firmas.this, "OnStartSigning", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSigned() {
                //mSaveButton.setEnabled(true);
                //mClearButton.setEnabled(true);
            }

            @Override
            public void onClear() {
                //mSaveButton.setEnabled(false);
                //mClearButton.setEnabled(false);
            }
        });
    }

    public void Save(View view) {
        Bitmap signatureBitmap = mSignaturePad.getTransparentSignatureBitmap();
        if (addJpgSignatureToGallery(signatureBitmap)) {
            Toast.makeText(this, "Signature saved into the Gallery", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Unable to store the signature", Toast.LENGTH_SHORT).show();
        }
        mSignaturePad.clear();
        if (Datos[1].equals("Interno")){
            ToInterno();
        }

    }
    public void Borrar(View view) {
        mSignaturePad.clear();
    }

    private void ToInterno(){
        ActivityCompat.finishAffinity(this);
        Intent intent = new Intent(this,InternoRD.class);
        intent.putExtra("Datos",Datos);

        startActivity(intent);
    }

    public boolean addJpgSignatureToGallery(Bitmap signature) {
        boolean result = false;
        try {
            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            String rutacarpetaImagenes = "ProyectoX/"+"Interno/" + Datos[0] + "/" + Datos[0] + "(CF)/";
            File photo = new File(ExternalStorageDirectory+rutacarpetaImagenes, "firma"+Datos[29]+".png");
            saveBitmapToJPG(signature, photo);
            scanMediaFile(photo);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void scanMediaFile(File photo) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri contentUri = Uri.fromFile(photo);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    public boolean addSvgSignatureToGallery(String signatureSvg) {
        boolean result = false;
        try {

            String ExternalStorageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS) + File.separator;
            String rutacarpetaImagenes = "ProyectoX/"+"Interno/" + Datos[0] + "/" + Datos[0] + "(CF)/";

            File svgFile = new File(ExternalStorageDirectory+rutacarpetaImagenes, "firma"+Datos[29]+".svg");
            OutputStream stream = new FileOutputStream(svgFile);
            OutputStreamWriter writer = new OutputStreamWriter(stream);
            writer.write(signatureSvg);
            writer.close();
            stream.flush();
            stream.close();
            scanMediaFile(svgFile);
            result = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public void saveBitmapToJPG(Bitmap bitmap, File photo) throws IOException {
        Bitmap newBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.TRANSPARENT);
        canvas.drawBitmap(bitmap, 0, 0, null);
        OutputStream stream = new FileOutputStream(photo);
        newBitmap.setHasAlpha(true);
        newBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        stream.close();
    }
}