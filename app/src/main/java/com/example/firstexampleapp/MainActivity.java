package com.example.firstexampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    public TextView textViewThreadIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        textViewThreadIndicator = (TextView) findViewById(R.id.threadIndicator);
        textViewThreadIndicator.setText("Consultar estado de cuenta");
       

        Thread thread = new Thread(){
            @Override
            public void run(){
                try{
                    sleep(3000);
                }
                catch (InterruptedException e){
                    e.printStackTrace();
                }
                finally{
                    Intent intent = new Intent(MainActivity.this, Menu.class);
                    startActivity(intent);
                }
            }
        };
        thread.start();
    }
}