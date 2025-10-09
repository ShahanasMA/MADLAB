package com.example.p12;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editTextID,editTextName,editTextEmail;
    Button btnAdd, btnView, btnUpdate,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editTextID= findViewById(R.id.editTextId);
        editTextName= findViewById(R.id.editTextName);
        editTextEmail= findViewById(R.id.editTextEmail);

        btnAdd = findViewById(R.id.btnAdd);
        btnView = findViewById(R.id.btnView);
        btnUpdate= findViewById(R.id.btnUpdate);
        btnDelete= findViewById(R.id.btnDelete);

        addData();
        viewAll();
        updateData();
        deleteData();
    }
    public void addData(){
        btnAdd.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(
                                editTextName.getText().toString(),
                                editTextEmail.getText().toString()
                        );
                        if (isInserted){
                            Toast.makeText(MainActivity.this,"Data Inserted Successfully!",Toast.LENGTH_SHORT).show();

                        } else {
                            Toast.makeText(MainActivity.this,"Data insertion failed",Toast.LENGTH_SHORT).show();

                        }

                    }
                }
        );
    }

    public void viewAll(){
        btnView.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Cursor res = myDb.getALLData();

                        if(res.getCount()==0){
                            showMessage("Error","Nothin found");
                        }

                        StringBuffer buffer = new StringBuffer();
                        while(res.moveToNext()){
                            buffer.append("ID"+res.getString(0)+"\n");
                            buffer.append("Name"+res.getString(1)+"\n");
                            buffer.append("email:"+res.getString(2)+"\n\n");
                        }

                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }

    public void updateData(){
        btnUpdate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isUpdated = myDb.updateData(
                                editTextID.getText().toString(),
                                editTextName.getText().toString(),
                                editTextEmail.getText().toString()
                        );

                        if(isUpdated){
                            Toast.makeText(MainActivity.this, "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Data update failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

    public void deleteData(){
        btnDelete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer deletedRows = myDb.deleteData(editTextID.getText().toString());
                        if(deletedRows>0){
                            Toast.makeText(MainActivity.this, "Data Deleted Successfully", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(MainActivity.this, "Data Deletion failed", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
    }

    public void showMessage(String title,String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
