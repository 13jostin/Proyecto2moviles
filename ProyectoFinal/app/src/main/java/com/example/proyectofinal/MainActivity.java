package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre;
    private ImageView iv_personaje;
    private TextView tv_bestScore;
    private MediaPlayer mp;

    int aleatorio = (int) (Math.random() * 10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre = (EditText) findViewById(R.id.et_nombre);
        iv_personaje = (ImageView) findViewById(R.id.iv_personaje);
        tv_bestScore = (TextView) findViewById(R.id.tv_BestScore);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        int id;
        if(aleatorio == 0 || aleatorio == 10){
            id = getResources().getIdentifier("mango","drawable",getPackageName());
            iv_personaje.setImageResource(id);
        }else  if(aleatorio == 1 || aleatorio == 9){
            id = getResources().getIdentifier("fresa","drawable",getPackageName());
            iv_personaje.setImageResource(id);
        }else  if(aleatorio == 2 || aleatorio == 8){
            id = getResources().getIdentifier("manzana","drawable",getPackageName());
            iv_personaje.setImageResource(id);
        }else  if(aleatorio == 3 || aleatorio == 7){
            id = getResources().getIdentifier("sandia","drawable",getPackageName());
            iv_personaje.setImageResource(id);
        }else  if(aleatorio == 4 || aleatorio == 5 || aleatorio == 6){
            id = getResources().getIdentifier("naranja","drawable",getPackageName());
            iv_personaje.setImageResource(id);
        }

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"db",null,1);
        SQLiteDatabase BD = admin.getWritableDatabase();

        Cursor consulta = BD.rawQuery("select * from puntaje where score = (select max(score) from puntaje)",null);
        if(consulta.moveToFirst()){
            String temp_nombre = consulta.getString(0);
            String temp_score = consulta.getString(1);
            tv_bestScore.setText("Record: "+temp_score+" de "+temp_nombre);

        }else{

        }
        BD.close();

        mp = MediaPlayer.create(this, R.raw.alphabet_song);
        mp.start();;
        mp.setLooping(true);

    }

    public void jugar(View vista){
        String nombre = et_nombre.getText().toString();
        if(!nombre.isEmpty()){
            mp.stop();
            mp.release();
            Intent intent = new Intent(this,Main2Activity_Nivel1.class);
            intent.putExtra("jugador",nombre);
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Primero debes tu nombre",Toast.LENGTH_SHORT).show();
            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_nombre,InputMethodManager.SHOW_IMPLICIT);
        }

    }

    @Override
    public void onBackPressed(){
        //
    }

}