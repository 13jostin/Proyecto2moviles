package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;

public class MainActivity4_Nivel4 extends AppCompatActivity {

    private TextView tv_nombre, tv_score;
    private ImageView iv_uno, iv_dos, iv_vidas, iv_signo;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bab;

    int score, aleatorio1, aleatorio2, resultado, vidas = 3;
    String nombre_jugador, string_score, string_vidas;
    String numero[] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve", "diez", "once", "doce", "trece", "catorce", "quince", "dieciseis", "diecisiete", "dieciocho", "diecinueve", "veinte"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4__nivel4);

        Toast.makeText(this, "Nivel 3: Sumas y Restas nivel medio", Toast.LENGTH_SHORT).show();

        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        tv_score = (TextView) findViewById(R.id.tv_score);
        iv_vidas = (ImageView) findViewById(R.id.tv_vidas);
        iv_uno = (ImageView) findViewById(R.id.iv_num1);
        iv_dos = (ImageView) findViewById(R.id.iv_num2);

        et_respuesta = (EditText) findViewById(R.id.et_resultado);
        iv_signo= (ImageView) findViewById(R.id.iv_signo);

        nombre_jugador = getIntent().getStringExtra("jugador");
        string_score = getIntent().getStringExtra("score");
        score = Integer.parseInt(getIntent().getStringExtra("score"));
        vidas = Integer.parseInt(getIntent().getStringExtra("vidas"));

        tv_score.setText("score : " + string_score);
        tv_nombre.setText("Jugador : " + nombre_jugador );

        setVidas(vidas, nombre_jugador);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mp = MediaPlayer.create(this, R.raw.goats);
        mp.start();
        mp.setLooping(true);

        mp_great = MediaPlayer.create(this, R.raw.wonderful);
        mp_bab = MediaPlayer.create(this, R.raw.bad);

        numAleatorio();
    }

    public void numAleatorio() {
        if (score >= 30) {
            aleatorio1 = (int) (Math.random() * 20);
            aleatorio2 = (int) (Math.random() * 20);
            int signo= (int) (Math.random()*3);

            switch(signo){
                case 0:{
                    iv_signo.setImageResource(R.drawable.adicion);
                    resultado = aleatorio1 + aleatorio2;
                    break;
                }
                case 1: {
                    iv_signo.setImageResource(R.drawable.resta);
                    resultado = aleatorio1 - aleatorio2;
                    break;
                }
                case 2:{
                    iv_signo.setImageResource(R.drawable.multiplicacion);
                    resultado = aleatorio1 * aleatorio2;
                }

            }

            if(resultado <= 40 && resultado >= 0){
                for(int i = 0; i < numero.length; i++){
                    int id = getResources().getIdentifier(numero[i], "drawable", getPackageName());
                    if(aleatorio1 == i){
                        iv_uno.setImageResource(id);
                    }
                    if(aleatorio2 == i){
                        iv_dos.setImageResource(id);
                    }
                }
            }
            else{
                numAleatorio();
            }
        }
        else{
            Intent intent = new Intent(this, MainActivity4_Nivel4.class);
            string_score = String.valueOf(score);
            string_vidas = String.valueOf(vidas);
            intent.putExtra("jugador", nombre_jugador);
            intent.putExtra("score", string_score);
            intent.putExtra("vidas", string_vidas);
            startActivity(intent);
            finish();
            mp.stop();
            mp.release();
        }
    }

    private void setVidas(int vidas, String name){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        switch (vidas){
            case 3:
                iv_vidas.setImageResource(R.drawable.tresvidas);
                break;
            case 2:
                iv_vidas.setImageResource(R.drawable.dosvidas);
                break;
            case 1:
                iv_vidas.setImageResource(R.drawable.unavida);
                break;
            case 0:
                Toast.makeText(this, "No quedan mazanas", Toast.LENGTH_SHORT).show();
                int cantidad = BD.delete("puntaje", "nombre=" + "'" + name + "'", null);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                mp.stop();
                mp.release();
                finish();
                break;
        }
    }

    private int modificar(String name, int score, int vidas){

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        ContentValues registro = new ContentValues();
        registro.put("nombre", name);
        registro.put("score", score);
        registro.put("vidas", vidas);

        int cantidad =  BD.update("puntaje", registro, "nombre=" + "'" + name + "'",null);
        BD.close();

        return cantidad;

    }

    public void evaluar(View vista){

        String respuesta = et_respuesta.getText().toString();

        if(!respuesta.isEmpty()) {
            int respuestaEntera = Integer.parseInt(respuesta);

            if (resultado == respuestaEntera) {
                mp_great.start();
                score++;
                tv_score.setText("score : " + score);
            } else {
                mp_bab.start();
                vidas--;
                setVidas(vidas, nombre_jugador);
            }

            et_respuesta.setText("");
            numAleatorio();

            if (vidas > 0) {
                int state = modificar(nombre_jugador, score, vidas);
            }
        }
        else{
            Toast.makeText(this, "Debes dar tu respuesta", Toast.LENGTH_SHORT).show();
        }
    }


}