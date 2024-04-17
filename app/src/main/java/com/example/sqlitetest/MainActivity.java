package com.example.sqlitetest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    DBHelper myDB;
    Button insertDataBtn, viewAllBtn, updateDataBtn, deleteDataBtn;
    EditText nameInput, branchInput, marksInput, idInput;
    String name, branch, id;
    int marks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myDB = new DBHelper(this);
        insertDataBtn = (Button) findViewById(R.id.button);
        nameInput = (EditText) findViewById(R.id.editTextText1);
        branchInput = (EditText) findViewById(R.id.editTextText2);
        marksInput = (EditText) findViewById(R.id.editTextText3);
        idInput = (EditText) findViewById(R.id.editTextText);
        viewAllBtn = (Button) findViewById(R.id.button2);
        updateDataBtn = (Button) findViewById(R.id.button3);
        deleteDataBtn = (Button) findViewById(R.id.button4);


        insertDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idInput.getText().toString();
                name = nameInput.getText().toString();
                branch = branchInput.getText().toString();
                marks = Integer.parseInt(marksInput.getText().toString());
                boolean status = myDB.insertData(id, name, branch, marks);
                if(!status){
                    Toast.makeText(MainActivity.this, "Data Insertion Failed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data Insertion successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        viewAllBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = myDB.getAllData();
                if(res.getCount()==0){
                    Toast.makeText(MainActivity.this, "No data in the table", Toast.LENGTH_SHORT).show();
                    showData("Error", "No Data Found");
                }
                else{
                    ArrayList<String> studentInfo= new ArrayList<String>();
                    while(res.moveToNext()){
                        studentInfo.add(res.getString(0));
                        studentInfo.add(res.getString(1));
                        studentInfo.add(res.getString(2));
                        studentInfo.add(res.getString(3));
                    }
                    Intent i = new Intent(getBaseContext(), StudentInfoDisplay.class);
                    i.putStringArrayListExtra(StudentInfoDisplay.STUDENTINFO, studentInfo);
                    startActivity(i);
                }
            }
        });

        updateDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idInput.getText().toString();
                name = nameInput.getText().toString();
                branch = branchInput.getText().toString();
                marks = Integer.parseInt(marksInput.getText().toString());
                if(myDB.getOneData(id).getCount()==0){
                    showData("Not Found", "Specified student not found");
                    return;
                }
                boolean status = myDB.updateData(id, name, branch, marks);
                if(!status){
                    Toast.makeText(MainActivity.this, "Data Updation Failed", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Data Updation successful", Toast.LENGTH_SHORT).show();
                }
            }
        });

        deleteDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = idInput.getText().toString();
                int deletedRows= myDB.deleteData(id);
                if(deletedRows==0){
                    Toast.makeText(MainActivity.this, "No data to be deleted", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, deletedRows+" row deleted successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void showData(String title, String message){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setCancelable(true);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.show();
    }
}


//Now implement listView using this (done)
//Also add a dropdown to contain all the branches that have been added to the database
//Have facility to be able to display student data from selective branches