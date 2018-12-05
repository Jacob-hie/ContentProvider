package com.hie2j.listview;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Activity_add extends AppCompatActivity {
    private EditText edtStuno;
    private EditText edtName;
    private EditText edtAge;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtStuno = findViewById(R.id.edt_stuno);
        edtName = findViewById(R.id.edt_name);
        edtAge = findViewById(R.id.edt_age);
        btnSave = findViewById(R.id.btn_save);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String stuno = edtStuno.getText().toString();
                String name = edtName.getText().toString();
                int age = Integer.parseInt(edtAge.getText().toString());

                Intent intent = new Intent();
                Student student = new Student(stuno,name,age);
                intent.putExtra("STUDENT",student);
//                intent.putExtra("STUNO", stuno);
//                intent.putExtra("NAME", name);
//                intent.putExtra("AGE", age);

                setResult(2001, intent);
                finish();
            }
        });
    }
}
