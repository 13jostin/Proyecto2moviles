package com.example.proyectofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
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

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
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
            Intent intent = null;

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
            SQLiteDatabase BD = admin.getWritableDatabase();

            Cursor file = BD.rawQuery("select * from puntaje where nombre = " +  "'" + nombre + "'", null);

            if(file.moveToFirst()){
                String sco = file.getString(1);
                String lif = file.getString(2);
                if(sco != null && sco != "0" && lif != null && lif != "0") {
                    if ((Integer.parseInt(sco) < 10)) {
                        intent = new Intent(this, Main2Activity_Nivel1.class);
                    } else if ((Integer.parseInt(sco) >= 10) && (Integer.parseInt(sco) < 20)) {
                        intent = new Intent(this, MainActivity2_Nivel2.class);
                    }
                    else if ((Integer.parseInt(sco) >= 20) && (Integer.parseInt(sco) < 30)) {
                        intent = new Intent(this, MainActivity3_Nivel3.class);
                    }
                    else if ((Integer.parseInt(sco) >= 30) && (Integer.parseInt(sco) < 40)) {
                        intent = new Intent(this, MainActivity4_Nivel4.class);
                    }
                    intent.putExtra("jugador", nombre);

                    intent.putExtra("score", file.getString(1));
                    intent.putExtra("vidas", file.getString(2));
                }
                else{
                    intent.putExtra("jugador", nombre);

                    intent.putExtra("score", "0");
                    intent.putExtra("vidas", "3");
                }
            }
            else{
                intent = new Intent(this,Main2Activity_Nivel1.class);
                intent.putExtra("jugador",nombre);

                ContentValues registro = new ContentValues();
                String sc = "0", lifes = "3";
                registro.put("nombre", nombre);
                registro.put("score", sc);
                registro.put("vidas", lifes);
                BD.insert("puntaje",null, registro);

                intent.putExtra("score",sc);
                intent.putExtra("vidas",lifes);
            }

            BD.close();
            startActivity(intent);
            finish();
        }else{
            Toast.makeText(this,"Primero debes tu nombre",Toast.LENGTH_SHORT).show();
            et_nombre.requestFocus();
            InputMethodManager imm = (InputMethodManager) getSystemService(this.INPUT_METHOD_SERVICE);
            imm.showSoftInput(et_nombre,InputMethodManager.SHOW_IMPLICIT);
        }

    }

    public void nuevoJuego(View vista){
        String nombre = et_nombre.getText().toString();
        if(!nombre.isEmpty()){
            mp.stop();
            mp.release();
            Intent intent = new Intent(this,Main2Activity_Nivel1.class);
            intent.putExtra("jugador",nombre);

            AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this,"administracion",null,1);
            SQLiteDatabase BD = admin.getWritableDatabase();

            Cursor file = BD.rawQuery("select * from puntaje where nombre = " +  "'" + nombre + "'", null);

            if(file.moveToFirst()){
                ContentValues registro = new ContentValues();
                String sc = "0", lifes = "3";
                registro.put("nombre", nombre);
                registro.put("score", sc);
                registro.put("vidas", lifes);
                int cantidad =  BD.update("puntaje", registro, "nombre=" + "'" + nombre + "'",null);

                intent.putExtra("score",sc);
                intent.putExtra("vidas",lifes);
            }
            else{

                intent.putExtra("jugador",nombre);

                ContentValues registro = new ContentValues();
                String sc = "0", lifes = "3";
                registro.put("nombre", nombre);
                registro.put("score", sc);
                registro.put("vidas", lifes);
                BD.insert("puntaje",null, registro);

                intent.putExtra("score",sc);
                intent.putExtra("vidas",lifes);
            }

            BD.close();
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