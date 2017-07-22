package com.example.gowtham.widgetsdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.R.attr.name;

public class MainActivity extends AppCompatActivity {

    EditText et_sname, et_DOB;
    TextView data_view;
    RadioGroup gender;
    CheckBox SSLC, HSSC, PG, UG;
    Spinner sp_course;
    Button submit, retrieve;
    ToggleButton tg_Button;
    ArrayList<String> courseList = new ArrayList();
    ArrayList<String> checkbox = new ArrayList();
    DatabaseReference myRef, mychild;
    String course;
    String sname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_sname = (EditText) findViewById(R.id.ed_sname);
        et_DOB = (EditText) findViewById(R.id.et_DOB);
        gender = (RadioGroup) findViewById(R.id.gender);
        SSLC = (CheckBox) findViewById(R.id.SSLC);
        HSSC = (CheckBox) findViewById(R.id.HSSC);
        UG = (CheckBox) findViewById(R.id.UG);
        PG = (CheckBox) findViewById(R.id.PG);
        submit = (Button) findViewById(R.id.submit);
        retrieve = (Button) findViewById(R.id.retrieve);
        data_view = (TextView) findViewById(R.id.data_view);
        sp_course = (Spinner) findViewById(R.id.sp_course);
        courseList.add(" ");
        courseList.add("C,C++");
        courseList.add("JAVA");
        courseList.add("ANDROID");
        courseList.add("Web-Designing");
        courseList.add("PHP");
        courseList.add("Python");
        final ArrayAdapter<String> getCourse = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, courseList);
        sp_course.setAdapter(getCourse);
        sp_course.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                course = (String) parent.getItemAtPosition(position);
                Toast.makeText(getApplicationContext(), "You have select " + course, Toast.LENGTH_LONG).show();
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 sname = et_sname.getText().toString();
                String sDOB = et_DOB.getText().toString();
                int id = gender.getCheckedRadioButtonId();
                RadioButton check = (RadioButton) findViewById(id);
                String myGender = check.getText().toString();
//                Student stu = new Student();
//                stu.setSname(sname);
//                stu.getsDOB(sDOB);
//                stu.setMyGender(myGender);


                myRef = FirebaseDatabase.getInstance().getReference(sname);
//                myRef.child("dob").setValue(sDOB);
//                myRef.child("gender").setValue(myGender);
//                myRef.child("courseDetails").setValue(course);

            }
        });

       retrieve.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               myRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(DataSnapshot dataSnapshot) {
                       for (DataSnapshot childDataSnapshot : dataSnapshot.getChildren()) {
                           Log.v("TAG",""+ childDataSnapshot.getKey()); //displays the key for the node
                           Log.v("TAG",""+ childDataSnapshot.child("sname").getValue());
                           //gives the value for given keyname
                           Toast.makeText(getApplicationContext(),"The names are "+childDataSnapshot.child("sname").getValue(),Toast.LENGTH_LONG).show();
                       }
               }

               @Override
                   public void onCancelled(DatabaseError databaseError) {

                   }
               });

           }
       });

    }

//    public void onCheckboxClicked(View view) {
//
//        boolean checked = ((CheckBox) view).isChecked();
//
//        switch (view.getId()) {
//            case R.id.SSLC:
//                checkbox.add(SSLC.getTag().toString());
//
//
//                break;
//            case R.id.HSSC:
//                checkbox.add(HSSC.getTag().toString());
//
//                break;
//
//            case R.id.UG:
//                checkbox.add(UG.getTag().toString());
//                break;
//            case R.id.PG:
//                checkbox.add(PG.getTag().toString());
//                break;
//        }
//    }
}
