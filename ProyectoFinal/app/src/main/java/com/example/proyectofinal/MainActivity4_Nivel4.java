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

public class MainActivity4_Nivel4 extends AppCompatActivity {

    private TextView tv_nombre, tv_score;
    private ImageView iv_uno,iv_uno0, iv_dos, iv_dos0, iv_vidas, iv_signo;
    private EditText et_respuesta;
    private MediaPlayer mp, mp_great, mp_bab;

    int score, aleatorio1,aleatorio2, aleatorio20, aleatorio10, resultado, vidas = 3;
    String nombre_jugador, string_score, string_vidas;
    String numero[] = {"cero","uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_activity4__nivel4);

        Toast.makeText(this, "Nivel 4: Sumas, Restas y multiplicaciones de alto nivel", Toast.LENGTH_SHORT).show();

        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        tv_score = (TextView) findViewById(R.id.tv_score);
        iv_vidas = (ImageView) findViewById(R.id.tv_vidas);
        iv_uno = (ImageView) findViewById(R.id.iv_num1);
        iv_uno0 = (ImageView) findViewById(R.id.iv_num10);
        iv_dos = (ImageView) findViewById(R.id.iv_num2);
        iv_dos0 = (ImageView) findViewById(R.id.iv_num20);
        et_respuesta = (EditText) findViewById(R.id.et_resultado);
        iv_signo= (ImageView) findViewById(R.id.iv_signo);

        nombre_jugador = getIntent().getStringExtra("jugador");
        tv_nombre.setText("Jugador : " + nombre_jugador );



        score = (int) getIntent().getIntExtra("score",0);
        tv_score.setText("Score : " + score );

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
            aleatorio1 = (int) (Math.random() * 4);
            aleatorio2 = (int) (Math.random() * 4);
            aleatorio10= (int) (Math.random() * 10);
            aleatorio20= (int) (Math.random() * 10);
            int signo= (int) (Math.random()*2);
            int aleatoriofinal1= aleatorio1*10+aleatorio10;
            int aleatoriofinal2= aleatorio2*10+aleatorio20;
            if(signo==1){
                iv_signo.setImageResource(R.drawable.adicion);

                resultado = aleatoriofinal1 + aleatoriofinal2;
            }else{
                iv_signo.setImageResource(R.drawable.resta);

                resultado = aleatoriofinal1 - aleatoriofinal2;
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
                    if(aleatorio10 == i){
                        iv_uno0.setImageResource(id);
                    }
                    if(aleatorio20 == i){
                        iv_dos0.setImageResource(id);
                    }
                }
            }
            else{
                numAleatorio();
            }
        }
        else{
            Intent intent = new Intent(this, MainActivity.class);
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
