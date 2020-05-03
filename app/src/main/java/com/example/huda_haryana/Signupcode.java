package com.example.huda_haryana;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
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
        signInButton = findViewById(R.id.google_signin);

        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signin();
            }
        });


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
                    }
                });

            }
        });

        findViewById(R.id.gotologin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Signupcode.this, very_main.class));
                finishAffinity();
            }
        });

    }

    private void signin(){
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_SIGN_IN){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask){
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            updateUI(acc);

        }
        catch (ApiException e){
            Toast.makeText(this, "Sign In Failed", Toast.LENGTH_SHORT).show();
            updateUI(null);
        }
    }
    public void  updateUI(GoogleSignInAccount account){
        if(account != null){
            Toast.makeText(this,"U Signed In successfully as "+account.getDisplayName(),Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MainActivity.class));
        }else {
            Toast.makeText(this,"U Didnt signed in",Toast.LENGTH_LONG).show();
            startActivity(new Intent(this,MainActivity.class));
        }
    }



}
