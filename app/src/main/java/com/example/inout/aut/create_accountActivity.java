package com.example.inout.aut;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.menu.ShowableListMenu;

import android.app.VoiceInteractor;
import android.content.Intent;
import android.location.Address;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.inout.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class create_accountActivity extends AppCompatActivity {
    EditText username,password,email,phone,address;
    Spinner gander,day,month,year;
    ImageView back;
    Button next;
    TextView terms;


    private FirebaseAuth boody;
    private FirebaseFirestore db ;
    // ProgressBar progressBaar;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        username=findViewById(R.id.edusernamerig);
        password=findViewById(R.id.edpasswordrig);
        email=findViewById(R.id.edemailrig);
        phone=findViewById(R.id.edphonerig);
        address=findViewById(R.id.edaddresrig);
        back=findViewById(R.id.imgbackrig);
        terms=findViewById(R.id.tvxterms);
        gander=findViewById(R.id.spngenderrig);
        day=findViewById(R.id.spndayrig);
        month=findViewById(R.id.spnmonthrig);
        year=findViewById(R.id.spnyearrig);
        next=findViewById(R.id.btnrig);
        findViewById(R.id.btnrig).setOnClickListener(v -> checkif());
        boody = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    private void checkif() {
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String Email = email.getText().toString().trim();
        String Phone = phone.getText().toString().trim();
        String addres = address.getText().toString().trim();
        String Gender = gander.getSelectedItem().toString();
        String Day = day.getSelectedItem().toString();
        String Month = month.getSelectedItem().toString();
        String Year = year.getSelectedItem().toString();
        String Next = next.getText().toString();



        if (user.isEmpty()) {
            username.requestFocus();
            username.setError("please enter name");
            return;
        }
        if (pass.isEmpty())
        {
            password.requestFocus();
            password.setError("please enter password");
            return;
        }
        if (pass.length()<8)
        {
            password.requestFocus();
            password.setError("Password must be 8 digit");
            return;
        }
        if (Email.isEmpty())
        {
            email.requestFocus();
            email.setError("please enter Email");
            return;
        }

        if (Phone.isEmpty())
        {
            phone.requestFocus();
            phone.setError("please enter phone");
            return;
        }
        if (Phone.length()<11)
        {
            phone.requestFocus();
            phone.setError("please enter 11 digit ");
            return;
        }
        if (addres.isEmpty())
        {
            address.requestFocus();
            address.setError("please enter address");
            return;
        }
            signupfirebase(Email,pass);

    }

    private void signupfirebase(String email, String pass) {
        boody.createUserWithEmailAndPassword(email,pass)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        saveuserdate();
                    }else {
                       // progressBaar.setVisibility(View.VISIBLE);

                        Toast.makeText(create_accountActivity.this, "Exception : "+task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                });
    }

    private void saveuserdate() {


        String uID = boody.getCurrentUser().getUid();

        Map<String, Object> user = new HashMap<>();
        user.put("id", uID);
        user.put("username", username.getText().toString().trim());
        user.put("password", password.getText().toString().trim());
        user.put("email", email.getText().toString().trim());
        user.put("phone", phone.getText().toString().trim());
        user.put("address", address.getText().toString().trim());
        user.put("gander", gander.getSelectedItem());
        user.put("day", day.getSelectedItem());
        user.put("month", month.getSelectedItem());
        user.put("year", year.getSelectedItem());


        db.collection("users")
        .document(uID)
        .set(user)
        .addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    //   progressBaar.setVisibility(View.VISIBLE);

                    Toast.makeText(create_accountActivity.this, "account created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(create_accountActivity.this,LOGIN_Activity.class));

                }else
                {
                    //progressBaar.setVisibility(View.VISIBLE);

                    Toast.makeText(create_accountActivity.this, "Error + \\n"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                }
            }
        });

    }




}