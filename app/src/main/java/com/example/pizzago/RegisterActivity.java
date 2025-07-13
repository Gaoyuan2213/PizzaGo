package com.example.pizzago;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import Core.User;
import Credential.UseraccountCredential;
import Firebase.Firebasemanager;
import Firebase.RegisterManager;

public class RegisterActivity extends AppCompatActivity {
    EditText password1;
    EditText password2;
    Button register;
    EditText Email;
    ImageButton button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        password1 = findViewById(R.id.Password1);
        password2 = findViewById(R.id.Password2);
        register = findViewById(R.id.CREATE);
        button = findViewById(R.id.imageButton);
        Email = findViewById(R.id.EmailRegister);
        setRegisterListener();
        setHintlistener();

    }

    private void setRegisterListener(){
        register.setOnClickListener(v->processListener());
    }
    private void processListener(){
        boolean isValidPassword = UseraccountCredential.isValidPassword(password1.getText().toString().trim());
        String p1 = password1.getText().toString().trim();
        String p2= password2.getText().toString().trim();
        String email = Email.getText().toString().trim();
        assert p1 != null;
        assert p2 != null;
        if(UseraccountCredential.checkifPasswordSame(p1,p2)&&isValidPassword){
            User user = new User();
            user.setEmail(email);
            user.setPassword(p1);
            Firebasemanager manager = Firebasemanager.getInstance();
            manager.RegistUser(user, new RegisterManager.SignupCallback() {
                @Override
                public void onSuccess() {
                    runOnUiThread(()->{
                        Toast.makeText(RegisterActivity.this,
                                "Registration successful!", Toast.LENGTH_SHORT).show();
                        finish();
                    });
                }

                @Override
                public void onFailure(@NonNull Exception e) {
                     runOnUiThread(()->{
                         Toast.makeText(RegisterActivity.this,
                                 "Sign-up failed: " + e.getMessage(),
                                 Toast.LENGTH_LONG).show();
                     });
                }
            });

        }
        else{
            Toast.makeText(this,"password is not valid, please reenter the password",Toast.LENGTH_SHORT).show();
        }
    }
    private void setHintlistener(){
        button.setOnClickListener(v->processHint());
    }
    private void processHint(){
        Toast.makeText(this,"Must be 7+ letters and contain one special character (! # @)",
                Toast.LENGTH_LONG).show();
    }

}