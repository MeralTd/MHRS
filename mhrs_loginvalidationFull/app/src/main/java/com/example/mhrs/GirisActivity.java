package com.example.mhrs;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GirisActivity extends AppCompatActivity {

    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    private DatabaseReference myuser=db.getReference().child("users");
    EditText editsifre,edittc;

    public void __init__(){
        editsifre=findViewById(R.id.editSifre);
        edittc=findViewById(R.id.editTc);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris);
        __init__();

        editsifre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (edittc.getText().toString().trim().length()< 11) {
                        edittc.requestFocus();
                        edittc.setError("lütfen TC Kimlik Numaranızı doğru girdiğinizden emin olun");
                    }
                }
            }
        });
    }

    public void Kayit(View v)
    {
        Intent intn = new Intent(this,KayitActivity.class);
        this.startActivity(intn);
    }

    public void Giris (View v){
        switch (emptyControl()){
            case 1:
                ToastMesaj("Boş Alan Bırakılamaz!");
                break;
            case 2:
                ToastMesaj("VeriTabanına Kayıt HerHangi Bir Sebepten Dolayı Sağlanamadı.");
                break;
            case 0:
                sifreKimlikKontrol(edittc.getText().toString(),editsifre.getText().toString());
                break;
        }

    }
    //eklenti
    //giriş yapan kişinin tc ve şifresi kontrol ediliyor ve yetkiye göre sayfalara yönlendiriliyor.
    //NOT USERS ADINDA SINIF AÇTIM.
    public void sifreKimlikKontrol(final String tc, final String sifre) {
        myuser.child(tc).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users u;
                u = dataSnapshot.getValue(users.class);
                if (null != u) {
                    if (sifre.equals(u.sifre)) {
                        switch (u.yetki) {
                            //hasta sayfasına gidiyo
                            case "1":
                                Intent Hastaintn = new Intent(GirisActivity.this, AnasayfaActivity.class);
                                Hastaintn.putExtra("kullanici",tc);
                                GirisActivity.this.startActivity(Hastaintn);
                                break;
                            //doktor sayfasına gitcek
                            case "2":
                                Intent doktorintn = new Intent(GirisActivity.this, GirisActivity.class);
                                doktorintn.putExtra("kullanici",tc);
                                GirisActivity.this.startActivity(doktorintn);
                                break;
                            //admin sayfasına gitcek
                            case "3":
                                Intent adminintn = new Intent(GirisActivity.this, AdminAnaSayfaActivity.class);
                                adminintn.putExtra("kullanici",tc);
                                GirisActivity.this.startActivity(adminintn);
                                break;
                        }
                    }
                    else{
                        ToastMesaj("Bilgilerinizi Doğru Girdiğinizden Emin Olun.");
                    }
                }
                else
                    ToastMesaj("Böyle Bir Kullanıcı Bulunmamaktadır.");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }


    public void ToastMesaj(String text) {
        TextView ozelText = new TextView(getApplicationContext());
        ozelText.setText(text);
        ozelText.setTextColor(Color.RED);
        ozelText.setBackgroundColor(Color.BLACK);

        Toast uyarı = new Toast(getApplicationContext());
        uyarı.setView(ozelText);
        uyarı.setDuration(Toast.LENGTH_SHORT);
        uyarı.show();
    }

    public int emptyControl() {
        int Kontrol = 0;
        if (edittc.getText().toString().trim().equals("")||editsifre.getText().toString().trim().equals(""))
            Kontrol = 1;
        else if (edittc.getText().toString().trim().equals("")&&editsifre.getText().toString().trim().equals(""))
            Kontrol =1;
        return Kontrol;
    }
}
