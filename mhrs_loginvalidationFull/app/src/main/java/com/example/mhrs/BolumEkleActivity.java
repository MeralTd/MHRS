package com.example.mhrs;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.R.layout.simple_spinner_dropdown_item;

public class BolumEkleActivity extends AppCompatActivity {

    Spinner spHastaneAdi;
    EditText editBolumAdi;
    ArrayAdapter<String> adapterHastane;
    DatabaseReference  myBolum;
    ArrayList<String> hastannn=new ArrayList<String>();
    Button btnBolumEkle;
    long maxid=0;
    Bolumler blm;
    hastaneler hstn;
    TextView txthstn;
    String kullaniciId;

    hastaneler stn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bolum_ekle);

        spHastaneAdi=(Spinner)findViewById(R.id.spHastaneAdi);
        editBolumAdi=(EditText)findViewById(R.id.editBolumAdi);
        btnBolumEkle=(Button)findViewById(R.id.btnBlmEkle);
        blm=new Bolumler();
        myBolum= FirebaseDatabase.getInstance().getReference().child("hastaneler");

        myBolum.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds:dataSnapshot.getChildren()){
                    hastaneler u=ds.getValue(hastaneler.class);
                    Toast.makeText(getApplicationContext(),u.getHastaneAdı(),Toast.LENGTH_LONG).show();
                    hastannn.add(u.getHastaneAdı());
                }
                SpinnerDoldur();
                //spinerikla();

                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });

        btnBolumEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blm.setBolumAdı(editBolumAdi.getText().toString().trim());
                myBolum.child("bolumler").child(String.valueOf(maxid+1)).setValue(blm);

            }
        });

    }
    public void SpinnerDoldur(){
        Toast.makeText(getApplicationContext(), hastannn.get(0),Toast.LENGTH_LONG).show();
        adapterHastane= new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_dropdown_item,hastannn);
        adapterHastane.setDropDownViewResource(simple_spinner_dropdown_item);
        spHastaneAdi.setAdapter(adapterHastane);
    }

   /* public void spinerikla(){
        spHastaneAdi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection =spHastaneAdi.getItemAtPosition(position).toString();
                txthstn.setText(selection);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }*/


}

