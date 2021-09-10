package com.example.firebase_practise;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    EditText name,department,duration,id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.name);
        department=findViewById(R.id.department);
        duration=findViewById(R.id.duration);
        id=findViewById(R.id.id);



    }

    public void buttonclicked(View view) {
        String variable_name=name.getText().toString().trim();
        String variable_department=department.getText().toString().trim();
        String variable_duration=duration.getText().toString().trim();
        String variable_id=id.getText().toString().trim();

        firebasedbhandler obj=new firebasedbhandler(variable_name,variable_department,variable_duration);
        FirebaseDatabase db=FirebaseDatabase.getInstance();
        DatabaseReference node=db.getReference("Student");
        node.child(variable_id).setValue(obj);

        name.setText("");
        department.setText("");
        duration.setText("");
        id.setText("");
        Toast.makeText(this, "Record Inserted: ", Toast.LENGTH_SHORT).show();

    }
}