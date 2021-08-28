package com.client.lingkungan_hidup;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAdminActivity extends AppCompatActivity {
    Button btnLogin;
    EditText editTextToken;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        token = "adminlogin2022";
        editTextToken = (EditText)findViewById(R.id.textInputLoginPassword);

        btnLogin = (Button)findViewById(R.id.buttonLogin);

        btnLogin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //success
                if (editTextToken.getText().toString().equalsIgnoreCase(token) ){
                    Intent i=new Intent(getBaseContext(), ListSatwaAdminActivity.class);
                    startActivity(i);
                }
                else{
                    Toast.makeText(getBaseContext(),"Token salah!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}