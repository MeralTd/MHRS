package com.example.mhrs;

import java.io.Serializable;

public class users implements Serializable {
    //eklenti
    //Sisteme Kaydederken Bu formatta kaydediyor
    public String ad;
    public String soyad;
    public String sifre;
    public String tc;
    public String mail;
    public String yetki;

    public users(){
    }
    public void hasta (String ad,String soyad,String sifre,String mail,String tc) {
        this.ad=ad;
        this.mail=mail;
        this.sifre=sifre;
        this.soyad=soyad;
        this.tc=tc;
        this.yetki="1";
    }
    public void doktor(String ad,String soyad,String sifre,String mail,String tc){
        this.ad=ad;
        this.mail=mail;
        this.sifre=sifre;
        this.soyad=soyad;
        this.tc=tc;
        this.yetki="2";
    }
    public void admin(String ad,String soyad,String sifre,String mail,String tc){
        this.ad=ad;
        this.mail=mail;
        this.sifre=sifre;
        this.soyad=soyad;
        this.tc=tc;
        this.yetki="3";
    }

}
