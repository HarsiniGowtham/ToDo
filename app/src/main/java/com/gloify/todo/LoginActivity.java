package com.gloify.todo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends Activity implements View.OnClickListener {

    private TextView register;
    private EditText l_email, l_passwd;
    private Button logIn_btn;
    private ProgressBar l_progressbar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        register = findViewById(R.id.register);
        register.setOnClickListener(this);

        logIn_btn = findViewById(R.id.login_btn);
        logIn_btn.setOnClickListener(this);

        l_email = findViewById(R.id.login_email);
        l_passwd = findViewById(R.id.login_passwd);
        l_progressbar = findViewById(R.id.progressBar_l);

        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;

            case R.id.login_btn:
                userLogin();
                break;
        }

    }

    private void userLogin() {
        String email = l_email.getText().toString().trim();
        String passwd = l_passwd.getText().toString().trim();

        if (email.isEmpty()){
            l_email.setError("Email is required");
            l_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            l_email.setError("Please provide Valid email!");
            l_email.requestFocus();
            return;

        }

        if (passwd.isEmpty()){
            l_passwd.setError("Password is required");
            l_passwd.requestFocus();
            return;
        }

        if (passwd.length()<6){
            l_passwd.setError("Minimum password character should be 6 character");
            l_passwd.requestFocus();
            return;
        }

        l_progressbar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                    l_progressbar.setVisibility(View.GONE);
                }else{
                    Toast.makeText(LoginActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                    l_progressbar.setVisibility(View.GONE);
                }
            }
        });
    }
}
