package com.example.recaudio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private String out_file = null;
    private ImageView bt_rec, bt_export;
    private MediaRecorder RecFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt_rec = (ImageView) findViewById(R.id.bt_record);
        bt_export = (ImageView) findViewById(R.id.bt_export);

        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.RECORD_AUDIO}, 1000);
        }
    }


    public void Recording(View Record){
        if(out_file == null){
            out_file = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Med_Rec.mp3";
            RecFile = new MediaRecorder();
            //Seleccion del MIC_Aud
            RecFile.setAudioSource(MediaRecorder.AudioSource.MIC);
            //Formato de salida del audio
            RecFile.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            //Codificsción del audio
            RecFile.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //nombramos el archivo grabado
            RecFile.setOutputFile(out_file);

            try {
                RecFile.prepare();
                RecFile.start();
            }catch (IOException e){
                // poner la captura mas adelante :.D
            }

            bt_rec.setImageResource(R.drawable.record);
            Toast.makeText(getApplicationContext(), "Recording...", Toast.LENGTH_SHORT).show();
        }else if(RecFile != null){
            //Detener
            RecFile.stop();
            //grabación finalizada
            RecFile.release();
            RecFile = null;
            bt_rec.setImageResource(R.drawable.record);
            Toast.makeText(this,"Record Ready!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void Play(View play){
        MediaPlayer mediaplayer = new MediaPlayer();
        try {
            mediaplayer.setDataSource(out_file);
            mediaplayer.prepare();
        }catch (IOException e){
            //Añadir captura mas adelante.
        }
        mediaplayer.start();
        Toast.makeText(getApplicationContext(), "Playing File..", Toast.LENGTH_SHORT).show();
    }


    public void Export(View exporting){
        //Establecer clase para conexion de basesde datos y realizar el método
    }
}