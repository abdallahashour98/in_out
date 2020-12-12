package com.example.inout.aut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inout.MainActivity;
import com.example.inout.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.auth.User;


public class LOGIN_Activity extends AppCompatActivity {
EditText usernamelogin,passwordlogin;
Button login;
TextView createaccount,forgetpassword;
ImageView loginwithgoogle,loginwithfacebook;
    private FirebaseAuth pop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_l_o_g_i_n_);

        pop=FirebaseAuth.getInstance();
        usernamelogin=findViewById(R.id.edusernamelogin);
        passwordlogin=findViewById(R.id.edpasswordlogin);
        login=findViewById(R.id.btnlogin);
        createaccount=findViewById(R.id.tvxcreateaccountllogin);
        forgetpassword=findViewById(R.id.forgetpasswordlogin);
        loginwithfacebook=findViewById(R.id.loginwithfacebook);
        loginwithgoogle=findViewById(R.id.loginwithgoogle);
            findViewById(R.id.btnlogin).setOnClickListener(v -> checkif());
        findViewById(R.id.tvxcreateaccountllogin).setOnClickListener(v -> {
            startActivity(new Intent(LOGIN_Activity.this,create_accountActivity.class));
             checkif ();
        });

    }
    private void checkif() {


        String Username = usernamelogin.getText().toString().trim();
        String pass = passwordlogin.getText().toString().trim();

        if (Username.isEmpty()) {
            usernamelogin.requestFocus();
            usernamelogin.setError("please enter name");
            return;
        }
        if (pass.isEmpty())
        {
            passwordlogin.requestFocus();
            passwordlogin.setError("please enter password");
            return;
        }
        if (pass.length()<8)
        {
            passwordlogin.requestFocus();
            passwordlogin.setError("Password must be 8 digit");
            return;
        }
        loginWithFirebase(Username,pass);

    }

    private void loginWithFirebase(String username, String pass) {
        pop.signInWithEmailAndPassword(username,pass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            getSharedPreferences("login", MODE_PRIVATE)
                                    .edit()
                                    .putBoolean("isLogin", true)
                                    .apply();
                            gotologin();
                        }else {
                            Toast.makeText(LOGIN_Activity.this, "error is :"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    @Override
    protected void onStart() {
        super.onStart();

        boolean isLogin = getSharedPreferences("login", MODE_PRIVATE).getBoolean("isLogin", false);

        if (isLogin) {
            gotologin();
        }
    }
    void gotologin()
    {
        startActivity(new Intent(LOGIN_Activity.this, MainActivity.class));
            finish();
    }
}
