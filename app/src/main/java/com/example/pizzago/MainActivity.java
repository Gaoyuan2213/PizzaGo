package com.example.pizzago;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.security.MessageDigest;

public class MainActivity extends AppCompatActivity {
    EditText password;
    EditText email;
    Button register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        try {
            PackageInfo p = getPackageManager()
                    .getPackageInfo(getPackageName(),
                            PackageManager.GET_SIGNING_CERTIFICATES);
            Signature sig = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                sig = p.signingInfo.getApkContentsSigners()[0];
            }
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] bytes = md.digest(sig.toByteArray());

            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) sb.append(String.format("%02X:", b));
            String runtimeSha1 = sb.substring(0, sb.length() - 1);
            Log.d("SHA-RUNTIME", runtimeSha1);
        } catch (Exception ignore) { }

        password = findViewById(R.id.Password);
        email = findViewById(R.id.EmailRegister);
        register = findViewById(R.id.Register);
        setRegisterBtn();
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;

        });
    }
    private void setRegisterBtn(){
        register.setOnClickListener(view->processRigester());
    }
    private void processRigester(){
        Intent intent = new Intent(this,RegisterActivity.class);
        startActivity(intent);
    }

}