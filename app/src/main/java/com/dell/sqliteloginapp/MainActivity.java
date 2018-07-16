package com.dell.sqliteloginapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   SQLiteOpenHelper openHelper;
   SQLiteDatabase db;
    DatabaseHelper myDb;


    EditText txtfname,txtlname,txtpass, txtmail,txtphone;
    Button btnreg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openHelper = new DatabaseHelper(this);
        myDb = new DatabaseHelper(this);


        txtfname = (EditText)findViewById(R.id.editText);
        txtlname = (EditText)findViewById(R.id.editText2);
        txtpass = (EditText)findViewById(R.id.editText3);
        txtmail = (EditText)findViewById(R.id.editText10);
        txtphone = (EditText)findViewById(R.id.editText5);
        btnreg = (Button)findViewById(R.id.button);



        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            if(validate()){
                boolean isinserted = myDb.insertData(txtfname.getText().toString(),txtlname.getText().toString(),
                        txtpass.getText().toString(),txtmail.getText().toString(),txtphone.getText().toString());

                if(isinserted == true){

                    Toast.makeText(MainActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                    startActivity(intent);

                }else{
                    Toast.makeText(MainActivity.this,"Registration Failed",Toast.LENGTH_SHORT).show();
                }
                }
            }
        });

    }

    private Boolean validate(){
        Boolean result = false;

        String fname = txtfname.getText().toString();
        String lname = txtlname.getText().toString();
        String pass = txtpass.getText().toString();
        String mail = txtmail.getText().toString();
        String phone = txtphone.getText().toString();

        if(fname.isEmpty() || lname.isEmpty() || pass.isEmpty() || mail.isEmpty() || phone.isEmpty()){
            Toast.makeText(MainActivity.this,"Please Enter All Details",Toast.LENGTH_SHORT).show();
        }else {
            result = true;
        }
        return result;
    }




}
