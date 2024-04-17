package com.example.sqlitetest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.ListView;
import java.util.ArrayList;

public class StudentInfoDisplay extends AppCompatActivity {

    Spinner spinner;
    DBHelper myDB;

    public static final String STUDENTINFO = "STUDENTINFO";
    ArrayList<String> studentNames = new ArrayList<String>();
    ArrayList<String> studentBranch = new ArrayList<String>();
    ArrayList<String> studentMarks = new ArrayList<String>();
    ArrayList<String> recvdData = new ArrayList<String>();

    ArrayList<String> updatedStudentNames = new ArrayList<String>();
    ArrayList<String> updatedStudentBranch = new ArrayList<String>();
    ArrayList<String> updatedStudentMarks = new ArrayList<String>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_info_display);
        listView = (ListView) findViewById(R.id.listView);
        spinner = (Spinner) findViewById(R.id.spinner);

        Intent i = getIntent();
        recvdData = i.getStringArrayListExtra(STUDENTINFO);

        for (int j = 0; j < recvdData.size(); j++) {
            if(j%4==0){
                continue;
            } else if (j%4==1) {
                studentNames.add(recvdData.get(j));
            }else if (j%4==2) {
                studentBranch.add(recvdData.get(j));
            }else if (j%4==3) {
                studentMarks.add(recvdData.get(j));
            }
        }

        myDB = new DBHelper(this);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(StudentInfoDisplay.this, android.R.layout.simple_spinner_item, studentBranch);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        CustomBaseAdapter adapter = new CustomBaseAdapter(getApplicationContext(), studentNames, studentBranch, studentMarks);
        listView.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String branch = parent.getItemAtPosition(position).toString();
                Toast.makeText(StudentInfoDisplay.this, branch, Toast.LENGTH_SHORT).show();
                Cursor res = myDB.getBranchData(branch);

                updatedStudentNames.clear();
                updatedStudentBranch.clear();
                updatedStudentMarks.clear();
                while(res.moveToNext()) {
                    updatedStudentNames.add(res.getString(1));
                    updatedStudentBranch.add(res.getString(2));
                    updatedStudentMarks.add(res.getString(3));
                }
                adapter.filteredStudents(updatedStudentNames, updatedStudentBranch, updatedStudentMarks);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}