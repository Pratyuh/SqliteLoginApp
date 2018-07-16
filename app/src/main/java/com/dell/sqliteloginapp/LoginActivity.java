package com.dell.sqliteloginapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    SQLiteOpenHelper openHelper;
    SQLiteDatabase db;
    DatabaseHelper myDb;

    EditText txtmailid,txtpass,txtid;
    Button btnlgn,btnshw,btnreg,btndlt;

    Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        openHelper = new DatabaseHelper(this);
        db = openHelper.getReadableDatabase();
        myDb = new DatabaseHelper(this);

        txtmailid = (EditText)findViewById(R.id.editText6);
        txtpass = (EditText)findViewById(R.id.editText7);

        txtid = (EditText)findViewById(R.id.editText8);
        btnlgn = (Button)findViewById(R.id.button3);
        btnshw = (Button)findViewById(R.id.shwbtn);
        btnreg = (Button)findViewById(R.id.button5);
        btndlt = (Button)findViewById(R.id.button6);


        btndlt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Integer deleteRows = myDb.deleteData(txtid.getText().toString());

                if(deleteRows > 0)
                    Toast.makeText(LoginActivity.this,"Delete Success", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(LoginActivity.this,"Delete not Success", Toast.LENGTH_LONG).show();
                txtid.setText("");


            }
        });


        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        btnshw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDb.getAllData();
                if(res.getCount() == 0){
                    //show message
                    showMessage("Error" , "Nothing Found");
                    return;
                }
                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){

                    buffer.append("ID :"+res.getString(0)+"\n");
                    buffer.append("FirstName :"+res.getString(1)+"\n");
                    buffer.append("LastName :"+res.getString(2)+"\n");
                    buffer.append("Password :"+res.getString(3)+"\n");
                    buffer.append("Email :"+res.getString(4)+"\n");
                    buffer.append("Phone :"+res.getString(5)+"\n\n");

                }
                //show all data
                showMessage("Data", buffer.toString());



            }
        });





        btnlgn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mailid = txtmailid.getText().toString();
                String pass = txtpass.getText().toString();


                cursor = db.rawQuery(" select * from " + DatabaseHelper.TABLE_NAME + " WHERE " + DatabaseHelper.COL_5 + " =? AND " + DatabaseHelper.COL_4 + " =? ", new String[]{ mailid , pass });

                if(cursor!=null){
                    if(cursor.getCount()>0){

                         Intent intent = new Intent(LoginActivity.this,Welcome.class);
                        startActivity(intent);

                    }else{
                       // Toast.makeText(getApplicationContext(),"Login Error", Toast.LENGTH_LONG).show();
                        showMessage("error", "Wrong Email And Password");
                    }

                }
            }
        });

    }



    public  void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();

    }
}
