package com.example.NotesApp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Signup extends AppCompatActivity {
 private EditText msignupemail,msignuppassword;
 private RelativeLayout msignup;
 private TextView mgotologin;

 private FirebaseAuth firebaseAuth;


 @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        getSupportActionBar().hide();

        msignupemail=findViewById(R.id.sigupnemail);
        msignuppassword=findViewById(R.id.signuppassword);
        msignup = findViewById(R.id.signup);
        mgotologin =findViewById(R.id.gotologin);
        firebaseAuth = FirebaseAuth.getInstance();



        mgotologin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent =new Intent(Signup.this,MainActivity.class);
                startActivity(intent);
            }
        });


        msignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = msignupemail.getText().toString().trim();
                String password = msignuppassword.getText().toString().trim();
                if(mail.isEmpty()||password.isEmpty()){
                    Toast.makeText(getApplicationContext(),"All fields are required",Toast.LENGTH_SHORT).show();
                }
                else if(password.length()<7){
                    Toast.makeText(getApplicationContext(),"Password should contain atleast 7 digits",Toast.LENGTH_SHORT).show();

                }
                else{
                    //registered the user to forebase
                    firebaseAuth.createUserWithEmailAndPassword(mail,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Registeration Successful",Toast.LENGTH_SHORT).show();
                                sendEmailverfication();

                            }
                            else{
                                Toast.makeText(Signup.this, "Failed to Register", Toast.LENGTH_SHORT).show();
                            }





                        }
                    });



                }
            }
        });
    }

    //send email verification

    private void sendEmailverfication(){

        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        if(firebaseUser!=null){
            firebaseUser.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    Toast.makeText(getApplicationContext()," Verificatoin Email is sent,Verify and Log In Again",Toast.LENGTH_SHORT).show();
                    firebaseAuth.signOut();
                    finish();
                    startActivity(new Intent(Signup.this,MainActivity.class));
                }
            });

        }

        else{
            Toast.makeText(Signup.this, "failed to send verification email", Toast.LENGTH_SHORT).show();
        }


    }

}