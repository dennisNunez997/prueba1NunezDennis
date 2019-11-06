package com.example.prueba1_nuez_dennis_2019b;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private EditText EditTextName, EditTextEmail, EditTextPassword;
    private Button btnRegistro;
    private Button btnSendToLogin;

    private String name="";
    private String email="";
    private String password="";

    FirebaseAuth Auth;
    DatabaseReference Database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Auth = FirebaseAuth.getInstance();
        Database = FirebaseDatabase.getInstance().getReference();

        EditTextName = (EditText) findViewById(R.id.editTextName);
        EditTextEmail = (EditText) findViewById(R.id.editTextMail);
        EditTextPassword = (EditText) findViewById(R.id.editTextPassword);

        btnRegistro = (Button) findViewById(R.id.btnRegistro);
        btnSendToLogin = (Button) findViewById(R.id.btnSentToLogin);

        btnRegistro.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                name = EditTextName.getText().toString();
                email = EditTextEmail.getText().toString();
                password = EditTextPassword.getText().toString();

                if(!name.isEmpty() && !email.isEmpty() && !password.isEmpty())
                {
                    if(password.length()>=6)
                    {
                        registrarUsuario();
                    }
                    else
                    {
                        Toast.makeText(MainActivity.this, "el password debe tener al menos 6 caracteres",Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "debe completar los campos",Toast.LENGTH_SHORT).show();

                }


            }
        });

        btnSendToLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view)
            {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }

        });

    }




    private void registrarUsuario()
    {
        Auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", name);
                    map.put("email", email);
                    map.put("pass", password);



                    String id = Auth.getCurrentUser().getUid();
                    Database.child("Users").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task2) {
                            if(task2.isSuccessful())
                            {
                                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
                                finish();
                            }
                            else
                            {
                                Toast.makeText(MainActivity.this, "No se pudo crear los datos correctamente",Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No se pudo registrar este usuario",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        if(Auth.getCurrentUser() != null)
        {
            startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            finish();
        }
    }








}
