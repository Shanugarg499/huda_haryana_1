package com.example.huda_haryana;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {


    private EditText Password, Email;
    ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();


        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        progressBar = findViewById(R.id.login_progress);

        findViewById(R.id.login_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });

    }

    private void Login() {
        progressBar.setVisibility(View.VISIBLE);
        String email = Email.getText().toString().trim();
        String password = Password.getText().toString().trim();

        if(email.isEmpty()){
            progressBar.setVisibility(View.GONE);
            Email.setError("Email required");
            Email.requestFocus();
            return;
        }
        if(password.isEmpty()){
            progressBar.setVisibility(View.GONE);
            Password.setError("Password required");
            Password.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            progressBar.setVisibility(View.GONE);
            Email.setError("Valid Email required");
            Email.requestFocus();
            return;
        }
        if(password.length()<6){
            progressBar.setVisibility(View.GONE);
            Password.setError("Minimum length of password should be 6");
            Password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(login.this, "Some temporary error occurred!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(login.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }


}
