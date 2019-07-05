package com.example.mhrs;

import android.content.Intent;
import android.graphics.Color;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;


public class KayitActivity extends AppCompatActivity {
     //tasarım ekranındaki nesneleri çekmek
    private EditText editsoyad,editad,editsifre,edittc,editmail;
    private FirebaseDatabase db=FirebaseDatabase.getInstance();
    private DatabaseReference myuser=db.getReference().child("users");

    public void __init__(){
        editsoyad= findViewById(R.id.editSoyad);
        editad=findViewById(R.id.editAd);
        editsifre=findViewById(R.id.editSifre);
        edittc=findViewById(R.id.editTc);
        editmail=findViewById(R.id.editMail);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit);

        __init__();

        //şifreye geldimi tc nin degerinin 11 den az olma durumunu kontrol eder
        editsifre.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (edittc.getText().toString().length()< 11) {
                        edittc.requestFocus();
                        edittc.setError("lütfen TC Kimlik Numaranızı doğru girdiğinizden emin olun");
                    }
                }
            }
        });

        //eklendi sifre 5ten büyük girilmek zorunda (1 veya 2 uzunluklu girilmesin diye).
        editmail.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (editsifre.getText().toString().length()< 5) {
                        editsifre.requestFocus();
                        editsifre.setError("lütfen 5'ten fazla giriniz.");
                    }
                }
            }
        });
        editad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editad.setError("alfabetik karekterler giriniz");
            }
        });
        editsoyad.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus)
                    editsoyad.setError("alfabetik karekterler giriniz");
            }
        });

    }

    //kayıt tamamla butonuna basıldımı yapılcak kontroller
    public void kayitTamamla (View v){
        switch (emptyControl()){
            case 1:
                ToastMesaj("Boş Alan Bırakılamaz!");
                break;
            case 2:
                editad.requestFocus();
                editad.setError("yanlızca alfabetik karekterler girilebilir");
                break;
            case 3:
                editsoyad.requestFocus();
                editsoyad.setError("yanlızca alfabetik karekterler girilebilir");
                break;
            case 4:
                editmail.requestFocus();
                editmail.setError("Mail Adresinizi Lütfen Doğru Giriniz.");
                break;
            case 0:
                //eklendi
                users newuser=new users();
                newuser.hasta(editad.getText().toString(),editsoyad.getText().toString(),editsifre.getText().toString(),editmail.getText().toString(),edittc.getText().toString().trim());
                ayniKimlikKontrol(newuser.tc,newuser);

                break;
        }
    }
    //eklenti
    //aynı kimlik numarasına sahip kullanıcı varmı kontrolunü yapar
    public void ayniKimlikKontrol(final String tc, final users newuser) {

        myuser.child(tc).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                users u;
                u = dataSnapshot.getValue(users.class);
                if (null != u) {
                    edittc.requestFocus();
                    edittc.setError("Tc kimlik numarası daha önceceden alınmıştır,başka giriniz.");
                }else{
                    gecis(newuser);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
//eklenti
    //kayıt olan kullanıcı giriş sayfasına yönlendirilir.
    public void gecis(users u){
        myuser.child(u.tc).setValue(u);
        edittc.setError("");
        Intent intn=new Intent(this,GirisActivity.class);
        this.startActivity(intn);
    }

    //tüm hatların kontrolü ve türleri
    public int emptyControl(){
        int Kontrol = 0;
        if (edittc.getText().toString().trim().equals("")||editsoyad.getText().toString().trim().equals("")||editad.getText().toString().trim().equals("")||editsifre.getText().toString().trim().equals("")||editmail.getText().toString().trim().equals(""))
            Kontrol = 1;
        else  if(!editad.getText().toString().matches("[a-zA-Z ]+"))
            Kontrol=2;
        else  if(!editsoyad.getText().toString().matches("[a-zA-Z ]+"))
            Kontrol=3;
        else if(isValid(editmail.getText().toString().trim())==false)
            Kontrol=4;
        return Kontrol;
    }
    //doğru mail girişi kontrolü
    public final static boolean isValid(CharSequence mailtext) {
        return !TextUtils.isEmpty(mailtext) && android.util.Patterns.EMAIL_ADDRESS.matcher(mailtext).matches();
    }
    //herhangi bir hata için özelleştrilmiş toast mesajı
    public void ToastMesaj(String text){
        TextView ozelText=new TextView(getApplicationContext());
        ozelText.setText(text);
        ozelText.setTextColor(Color.RED);
        ozelText.setBackgroundColor(Color.BLACK);

        Toast uyarı=new Toast(getApplicationContext());
        uyarı.setView(ozelText);
        uyarı.setDuration(Toast.LENGTH_SHORT);
        uyarı.show();
    }



}
