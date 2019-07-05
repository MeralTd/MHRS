package com.example.mhrs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class AnasayfaActivity extends AppCompatActivity {
    private  void __init__(){
        //eklenti

        //giriş yapan kullanıcının tc si alınıyor
        TextView txtisimDeneme=findViewById(R.id.txtRandevuAl);
        Bundle gelenveri=getIntent().getExtras();
        txtisimDeneme.setText(gelenveri.getCharSequence("kullanici").toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);
        __init__();
    }
    public void ProfilGoruntule(View v)
    {
        Intent inten=new Intent(this,AnasayfaActivity.class);
        this.startActivity(inten);
    }
    public void RandevuAl(View v)
    {
        Intent inten=new Intent(this,AnasayfaActivity.class);
        this.startActivity(inten);
    }
    public void GecmisRandevuGoruntule(View v)
    {
        Intent inten=new Intent(this,AnasayfaActivity.class);
        this.startActivity(inten);
    }

}
