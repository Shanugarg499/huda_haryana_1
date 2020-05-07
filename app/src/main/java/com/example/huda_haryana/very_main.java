package com.example.huda_haryana;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class very_main extends AppCompatActivity {

    ProgressBar progressBar;
    private SignInButton signInButton;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;
    private EditText Email, Password;
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar2);
        loginButton = findViewById(R.id.fb_login);
        mAuth = FirebaseAuth.getInstance();
        callbackManager = CallbackManager.Factory.create();
        loginButton.setPermissions(Arrays.asList("email", "public_profile"));

//        checkloginstatus();

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });


    findViewById(R.id.login_txt).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), Signupcode.class));
            finishAffinity();
        }
    });
    findViewById(R.id.login_b).setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            login();
        }
    });

    }
    private void login() {
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
                            progressBar.setVisibility(View.GONE);
                            Intent intent = new Intent(very_main.this, MainActivity.class);
                            startActivity(intent);
                            finishAffinity();
                        }
                        else{
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(very_main.this, "Some temporary error occurred!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(very_main.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }
                });

    }


//    private void FirebaseGoogleAuth(GoogleSignInAccount acct){
//        if(acct != null){
//            AuthCredential authCredential = GoogleAuthProvider.getCredential(acct.getId(), null);
//            mAuth.signInWithCredential(authCredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                @Override
//                public void onComplete(@NonNull Task<AuthResult> task) {
//                    if(task.isSuccessful()){
//                        Toast.makeText(very_main.this, "Successful", Toast.LENGTH_SHORT).show();
//                        FirebaseUser user = mAuth.getCurrentUser();
//                        updateUI(user);
//                    }
//                    else{
//                        Toast.makeText(very_main.this, "Failed", Toast.LENGTH_SHORT).show();
//                        updateUI(null);
//                    }
//                }
//            });
//        }
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            if(currentAccessToken==null){
                Toast.makeText(getApplicationContext(), "User logged out", Toast.LENGTH_SHORT).show();
            }
            else{
                loaduserProfile(currentAccessToken);
            }
        }
    };

    private void loaduserProfile(AccessToken newAccessToken){

        GraphRequest request = GraphRequest.newMeRequest(newAccessToken, new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try {
                    String first_name = object.getString("first_name");
                    String last_name = object.getString("last_name");
                    String email = object.getString("email");
                    String id = object.getString("id");

                     Toast.makeText(getApplicationContext(), first_name + " " +last_name + " logged in successfully", Toast.LENGTH_SHORT).show();
                     startActivity(new Intent(getApplicationContext(), MainActivity.class));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name, last_name, email, id");
        request.setParameters(parameters);
        request.executeAsync();

    }

//    private void checkloginstatus(){
//        if(AccessToken.getCurrentAccessToken() != null){
//            Toast.makeText(getApplicationContext(), " You already logged in ", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(getApplicationContext(), MainActivity.class));
//        }
//    }

}
