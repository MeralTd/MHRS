package com.example.mhrs;

import android.support.annotation.NonNull;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseHelper {

    DatabaseReference db;
    Boolean saved=null;

    public FirebaseHelper(DatabaseReference db) {
        this.db = db;
    }

    //SAVE
    public  Boolean save(hastaneler hstnn)
    {
        if(hstnn==null)
        {
            saved=false;
        }else
        {
            try
            {
                db.child("hastaneler").push().setValue(hstnn);
                saved=true;
            }catch (DatabaseException e)
            {
                e.printStackTrace();
                saved=false;
            }
        }


        return saved;
    }

    //READ
    public ArrayList<String> retrieve()
    {
        final ArrayList<String> hstler=new ArrayList<>();

        db.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,hstler);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                fetchData(dataSnapshot,hstler);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return hstler;
    }

    private void fetchData(DataSnapshot snapshot,ArrayList<String> hstler)
    {
        hstler.clear();
        for (DataSnapshot ds:snapshot.getChildren())
        {
            String name=ds.getValue(hastaneler.class).getHastaneAdı();
            hstler.add(name);
        }

    }
}

