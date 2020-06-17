package com.leadgrow.estate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Signupcode extends AppCompatActivity {

    EditText newemail, newpassword;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private int RC_SIGN_IN = 1;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupcode);

        newemail = findViewById(R.id.emailhere);
        newpassword = findViewById(R.id.passwordhere);


        mAuth = FirebaseAuth.getInstance();



        findViewById(R.id.registerhere).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = newemail.getText().toString().trim();
                String password = newemail.getText().toString().trim();

                if(username.isEmpty()){ newemail.setError("Email is required"); newemail.requestFocus(); return;}

                if(password.isEmpty())
                { newpassword.setError("Password is required"); newpassword.requestFocus(); return; }

                if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){ newemail.setError("Please enter a valid email");newemail.requestFocus();return; }

                if(password.length()<6){ newpassword.setError("Password must has minimum length 6 !");newpassword.requestFocus();return; }

                mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Signupcode.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Signupcode.this, MainActivity.class));
                            finishAffinity();
                        }
                        else{Toast.makeText(getApplicationContext(), "This email is already registered ", Toast.LENGTH_SHORT).show();}
                    }
                });

            }
        });

        findViewById(R.id.gotologin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), very_main.class));
                finishAffinity();
            }
        });


    }



//    if(requestCode == RC_SIGN_IN){
//        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//        handleSignInResult(task);
//    }


}
