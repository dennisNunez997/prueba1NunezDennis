package com.example.prueba1_nuez_dennis_2019b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends AppCompatActivity {
    private Button ButtonSignOut;

    private FirebaseAuth Auth;
    private DatabaseReference Database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Auth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance().getReference();

        ButtonSignOut = (Button) findViewById(R.id.btnSignOut);

        ButtonSignOut.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Auth.signOut();
                startActivity(new Intent(ProfileActivity.this, MainActivity.class));
            }
        });

    }
}
