package de.kevoundfreun.labmaps;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AltaReclamoActivity extends AppCompatActivity{
    LatLng ubicacion;
    EditText et_reclamo;
    EditText et_email;
    EditText et_phone;
    ImageView iv_photo;
    Button reclamar;
    Button cancelar;
    FloatingActionButton fab_photo;
    TextView tv_path;

    static final int REQUEST_IMAGE_CAPTURE = 1;
    String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_reclamo);


        Bundle extras = getIntent().getExtras();
        ubicacion = (LatLng) extras.get("coordenadas");

        et_reclamo = (EditText) findViewById(R.id.et_reclamo);
        et_email = (EditText) findViewById(R.id.et_email);
        et_phone = (EditText) findViewById(R.id.et_telefono);
        fab_photo = (FloatingActionButton) findViewById(R.id.fab_add_ph);
        tv_path = (TextView) findViewById(R.id.tv_path);
        iv_photo = (ImageView) findViewById(R.id.iv_photo);

        reclamar = (Button) findViewById(R.id.button_accept);
        reclamar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Reclamo reclamo = new Reclamo((Double)ubicacion.latitude,
                        (Double)ubicacion.longitude,
                        et_reclamo.getText().toString(),
                        et_phone.getText().toString(),
                        et_email.getText().toString() );

                Intent returnIntent = new Intent();
                returnIntent.putExtra("result",reclamo);
                setResult(AltaReclamoActivity.RESULT_OK,returnIntent);
                finish();
            }
        });

        cancelar = (Button) findViewById(R.id.button_cancel);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AltaReclamoActivity.this,"CANCELAR", Toast.LENGTH_SHORT).show();
                finish();
            }
        });


        fab_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    // crash con null pointer exception
                    /*File photoFile = null;
                    photoFile = createImageFile();*/
                    // Continue only if the File was successfully created
                    /*if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(AltaReclamoActivity.this,
                                "com.example.android.fileprovider",
                                photoFile);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);*/
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                    //}
                }

            }
        });

    }
// Crash con null pointer exception
/*    private File createImageFile(){
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = null;
        try {
            image = File.createTempFile(
                    imageFileName,  *//* prefix *//*
                    ".jpg",         *//* suffix *//*
                    storageDir      *//* directory *//*
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        tv_path.setText(mCurrentPhotoPath);
        return image;
    }*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == 1) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                //TODO: fix null pointer exception aca
                // revisar con explicaciones en https://developer.android.com/training/camera/photobasics.html#TaskManifest
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                iv_photo.setImageBitmap(imageBitmap);
            }
        }
    }


}
