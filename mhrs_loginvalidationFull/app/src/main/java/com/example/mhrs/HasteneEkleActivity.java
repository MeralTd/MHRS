package com.example.mhrs;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HasteneEkleActivity extends AppCompatActivity {

    EditText editHastaneAdi;
    hastaneler hstn;
    DatabaseReference hastane;
    Button btnHastaneEkle;
    long maxid=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hastene_ekle);

        editHastaneAdi=(EditText)findViewById(R.id.editHastaneAdi);
        btnHastaneEkle =(Button)findViewById(R.id.btnHstnEkle) ;
        hstn=new hastaneler();
        hastane=FirebaseDatabase.getInstance().getReference().child("hastaneler");
        hastane.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists())
                    maxid=(dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btnHastaneEkle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hstn.setHastaneAdı(editHastaneAdi.getText().toString().trim());
                hastane.child(String.valueOf(maxid+1)).setValue(hstn);

            }
        });



    }


}
