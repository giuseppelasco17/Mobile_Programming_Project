package com.gelsolasco.runnerapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**La MenuActivity permette la visualizzazione del menu nel quale è implementato solo l'handler del
 * click relativo al bottone che permette la visualizzazione della lista degli atleti
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Intent i = getIntent();
        String name = i.getStringExtra("name");
        TextView name_tv = findViewById(R.id.name_tv);
        name_tv.setText(name);
        Button list_btn = findViewById(R.id.list_btn);
        //Implemento il clickListner del bottone e passo alla visualizzazione della lista
        list_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Passo alla ListActivity
                Intent intent = new Intent(MenuActivity.this, ListActivity.class);
                startActivity(intent);
            }
        });

    }
}
