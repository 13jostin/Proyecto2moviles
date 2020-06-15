package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2_Nivel2 extends AppCompatActivity {

    private TextView tv_nombre, tv_score;
    private ImageView iv_uno, iv_dos, iv_vidas;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bab;

    int score, aleatorio1, aleatorio2, resultado, vidas = 3;
    String nombre_jugador, string_score, string_vidas;
    String numero[] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity2__nivel2);

        Toast.makeText(this, "Nivel 2: Restas basicas", Toast.LENGTH_SHORT).show();

        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        tv_score = (TextView) findViewById(R.id.tv_score);
        iv_vidas = (ImageView) findViewById(R.id.tv_vidas);
        iv_uno = (ImageView) findViewById(R.id.iv_num1);
        iv_dos = (ImageView) findViewById(R.id.iv_num2);
        et_respuesta = (EditText) findViewById(R.id.et_resultado);

        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador : " + nombre_jugador );

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
        if (score < 10) {
            aleatorio1 = (int) (Math.random() * 10);
            aleatorio2 = (int) (Math.random() * 10);
            resultado = aleatorio1 - aleatorio2;
            if(resultado <= 10 && resultado >= 0){
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
            Intent intent = new Intent(this, MainActivity3_Nivel3.class);
            string_score = String.valueOf(score);
            string_vidas = String.valueOf(vidas);
            intent.putExtra("jugardor", nombre_jugador);
            intent.putExtra("score", string_score);
            intent.putExtra("vidas", string_vidas);
            startActivity(intent);
            finish();
            mp.stop();
            mp.release();
        }
    }

    public void evaluar(View vista){

        String respuesta = et_respuesta.getText().toString();

        if(!respuesta.isEmpty()){
            int respuestaEntera = Integer.parseInt(respuesta);

            if(resultado == respuestaEntera){
                mp_great.start();
                score++;
                tv_score.setText("score : " + score);
            }
            else{
                mp_bab.start();
                vidas--;
                switch (vidas){
                    case 3:
                        iv_vidas.setImageResource(R.drawable.tresvidas);
                        break;
                    case 2:
                        Toast.makeText(this, "Quedan 2 manzanas", Toast.LENGTH_SHORT).show();
                        iv_vidas.setImageResource(R.drawable.dosvidas);
                        break;
                    case 1:
                        Toast.makeText(this, "Queda una manzana", Toast.LENGTH_SHORT).show();
                        iv_vidas.setImageResource(R.drawable.unavida);
                        break;
                    case 0:
                        Toast.makeText(this, "No quedan mazanas", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(this, MainActivity.class);
                        startActivity(intent);
                        mp.stop();
                        mp.release();
                        finish();
                        break;
                }
            }

            et_respuesta.setText("");
            numAleatorio();
        }
        else{
            Toast.makeText(this, "Debes dar tu respuesta", Toast.LENGTH_SHORT).show();
        }
    }
}