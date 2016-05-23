package com.ikeres.app.professorearthquake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by Iker on 23/05/2016.
 */
public class Menu extends AppCompatActivity  {

    Toolbar toolbar;
    Button bt1;
    Button bt2;
    Button bt3;
    Intent i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        Toolbar toolbar =(Toolbar)findViewById(R.id.toolbar);
        bt1 =(Button)findViewById(R.id.hoy);
        bt1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                i = new Intent(Menu.this, MainActivity.class);
                i.putExtra("filtro","hora");
                startActivity(i);
            }
        });
        bt2 =(Button)findViewById(R.id.semana);
        bt2.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                i = new Intent(Menu.this, MainActivity.class);
                i.putExtra("filtro", "dia");
                startActivity(i);
            }
        });
       bt3 =(Button)findViewById(R.id.mes);
        bt3.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                i = new Intent(Menu.this, MainActivity.class);
                i.putExtra("filtro", "semana");
                startActivity(i);
            }
        });

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Proffesor Hearthquake");
        getSupportActionBar().setIcon(R.drawable.icono);

    }



    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i;
        switch (item.getItemId()){
            case R.id.menu_1:
                 i = new Intent(Menu.this, MainActivity.class);
                i.putExtra("filtro", "hora");
                startActivity(i);
                break;
            case R.id.menu_2:
                  i = new Intent(Menu.this, MainActivity.class);
                i.putExtra("filtro", "dia");
                startActivity(i);
                break;
            case R.id.menu_3:
                 i = new Intent(Menu.this, MainActivity.class);
                i.putExtra("filtro", "semana");
                startActivity(i);
                break;

        }


        return super.onOptionsItemSelected(item);
    }



        }




