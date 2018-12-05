package com.hie2j.listview;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Activity_edit extends AppCompatActivity {
    private EditText edtStuno;
    private EditText edtName;
    private EditText edtAge;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        edtStuno = findViewById(R.id.edt_stuno);
        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        btnSave = findViewById(R.id.btn_save);

        Student student = (Student) getIntent().getSerializableExtra("STUDENT");
        if (student == null){
            Toast.makeText(Activity_edit.this,"student == null",Toast.LENGTH_SHORT).show();
        }else{
            edtStuno.setText(student.getStuno());
            edtName.setText(student.getName());
            edtAge.setText(String.valueOf(student.getAge()));
        }

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stuno =edtStuno.getText().toString();
                String name = edtName.getText().toString();
                int age = Integer.parseInt(edtAge.getText().toString());

                Student student =  new Student(stuno, name, age);
                Intent intent = new Intent();
                intent.putExtra("STUDENT", student);
                setResult(3001, intent);
                finish();
            }
        });

    }
}