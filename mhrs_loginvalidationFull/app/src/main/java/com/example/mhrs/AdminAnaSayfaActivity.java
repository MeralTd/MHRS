package com.example.mhrs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class AdminAnaSayfaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_ana_sayfa);
    }

    public void HastaneEkle(View v)
    {
        Intent intn = new Intent(this,HasteneEkleActivity.class);
        this.startActivity(intn);
    }

    public void BolumEkle(View v)
    {
        Intent intn = new Intent(this,BolumEkleActivity.class);
        this.startActivity(intn);
    }
}
