package com.example.windows10.att;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserLogin extends AppCompatActivity implements View.OnClickListener{

    private Button buttonSignIn;
    private EditText editTextEmail1;
    private EditText editTextPassword1;
    private TextView textViewSignIn;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        firebaseAuth= FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()!= null){
            //profile activity
            finish();
            startActivity(new Intent(getApplicationContext(),profileActivity.class));
        }

        editTextEmail1 = (EditText) findViewById(R.id.editTextEmail1);
        editTextPassword1=(EditText) findViewById(R.id.editTextPassword1);
        buttonSignIn=(Button) findViewById(R.id.buttonSignIn);
        textViewSignIn=(TextView) findViewById(R.id.textViewSignIn);

        progressDialog = new ProgressDialog(this);

        buttonSignIn.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);
    }

    private void userLogin(){
        String email1=editTextEmail1.getText().toString().trim();
        String password1=editTextPassword1.getText().toString().trim();

        //checking if email and passwords are empty
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Please enter email", Toast.LENGTH_LONG).show();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Please enter password", Toast.LENGTH_LONG).show();
            return;
        }

        //if the email and password are not empty
        //displaying a progress dialog

        progressDialog.setMessage("Registering Please Wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email1,password1)
                .addOnCompleteListener(this, MediaPlayer.OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                     progressDialog.dismiss();
                     if(task.isSuccessful()){
                            //start the profile activity
                         finish();
                         startActivity(new Intent(getApplicationContext(),profileActivity.class));
                     }

                 }
        });
    }

    @Override
    public void onClick(View view) {
        if (view == buttonSignIn) {
            userLogin();
        }

        if (view == textViewSignIn) {
            finish();
            startActivity(new Intent(this, com.example.home.attendance.MainActivity.class));
        }
    }
}
