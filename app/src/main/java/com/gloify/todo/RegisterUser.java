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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class RegisterUser extends Activity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView display2;
    private EditText r_email, r_name, r_age, r_password;
    private Button register_btn;
    private ProgressBar r_progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);

        mAuth = FirebaseAuth.getInstance();

        display2 = findViewById(R.id.Disp2_gloify);
        display2.setOnClickListener(this);

        register_btn = findViewById(R.id.register_btn);
        register_btn.setOnClickListener(this);

        r_name = findViewById(R.id.register_name);
        r_age = findViewById(R.id.register_age);
        r_email = findViewById(R.id.register_email);
        r_password = findViewById(R.id.register_passwd);
        r_progressBar = findViewById(R.id.progressBar_r);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.Disp2_gloify:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.register_btn:
                register_btn();
                break;

        }

    }

    private void register_btn() {

        String name = r_name.getText().toString().trim();
        String age = r_age.getText().toString().trim();
        String email = r_email.getText().toString().trim();
        String passwd = r_password.getText().toString().trim();

        if (name.isEmpty()) {
            r_name.setError("Name is required");
            r_name.requestFocus();
            return;
        }

        if (age.isEmpty()) {
            r_age.setError("Age is required");
            r_age.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            r_email.setError("Email is required");
            r_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            r_email.setError("Please provide Valid email!");
            r_email.requestFocus();
            return;

        }

        if (passwd.isEmpty()) {
            r_password.setError("Password is required");
            r_password.requestFocus();
            return;
        }

        if (passwd.length() < 6) {
            r_password.setError("Minimum password character should be 6 character");
            r_password.requestFocus();
            return;
        }

        r_progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, passwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete( Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    User user = new User(name, age, email);

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete( Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        Toast.makeText(RegisterUser.this, "Registered successfully", Toast.LENGTH_LONG).show();
                                        r_progressBar.setVisibility(View.GONE);
                                    } else {
                                        Toast.makeText(RegisterUser.this, "Failed to Register! Try Again!", Toast.LENGTH_LONG).show();
                                        r_progressBar.setVisibility(View.GONE);
                                    }

                                }
                            });
                } else {
                    Toast.makeText(RegisterUser.this, "Failed to Register! Try Again!", Toast.LENGTH_LONG).show();
                    r_progressBar.setVisibility(View.GONE);
                }
            }
        });

    }


}
