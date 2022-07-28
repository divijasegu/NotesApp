package com.example.NotesApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText mloginemail,mloginpassword;
    private RelativeLayout mlogin,mgotosignup;
private TextView mgotoforgotpassword;

ProgressBar mprogressbarofmainactivity;


private FirebaseAuth firebaseAuth;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        mloginemail= findViewById(R.id.loginemail);

        mprogressbarofmainactivity =findViewById(R.id.progressbarofmainactivity);

        mloginpassword =findViewById(R.id.loginpassword);
        mlogin =findViewById(R.id.login);
        mgotoforgotpassword =findViewById(R.id.gotoforgotpassword);
        mgotosignup =findViewById(R.id.gotosignup);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
if(firebaseUser!=null){
    finish();
    startActivity(new Intent(MainActivity.this,notesactivity.class));
}


        mgotosignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,Signup.class));
            }
        });

          mgotoforgotpassword.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        startActivity(new Intent(MainActivity.this,forgotpassword.class));

    }
});

mlogin.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String mail = mloginemail.getText().toString().trim();
        String password = mloginpassword.getText().toString().trim();



        if(mail.isEmpty()||password.isEmpty()){
            Toast.makeText(getApplicationContext(), "All fields are empty", Toast.LENGTH_SHORT).show();
        }
        else{
            //login the user
            mprogressbarofmainactivity.setVisibility(View.VISIBLE);

            firebaseAuth.signInWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {


                    if(task.isSuccessful()){
                        checkmailverification();



                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Account not exist", Toast.LENGTH_SHORT).show();
                        mprogressbarofmainactivity.setVisibility(View.INVISIBLE);

                    }



                }
            });
        }
    }
});
    }

    private void checkmailverification(){
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser.isEmailVerified()==true){
            Toast.makeText(getApplicationContext(), "Logged in", Toast.LENGTH_SHORT).show();
            finish();
            startActivity(new Intent(MainActivity.this,notesactivity.class));


        }
        else{

            mprogressbarofmainactivity.setVisibility(View.INVISIBLE);
            Toast.makeText(getApplicationContext(), "Verify your mail", Toast.LENGTH_SHORT).show();
            firebaseAuth.signOut();
        }



    }




}