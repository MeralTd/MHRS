package com.example.mhrs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void Giris(View v)
    {
        Intent intn = new Intent(this,GirisActivity.class);
        this.startActivity(intn);
    }
    public void Kayit(View v)
    {
        Intent intn = new Intent(this,KayitActivity.class);
        this.startActivity(intn);

    }
}
