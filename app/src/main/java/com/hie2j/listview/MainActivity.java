package com.hie2j.listview;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements IOnDataChangeListener {
    private EditText edtKey;
    private ImageView ivSearch;
    private ListView lvStu;
    private Button btnAdd;
    private ArrayList<Student> studentArrayList = new ArrayList<>();
    private StuAdapter adapter;
    private StudbOpenHelper studbOpenHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViews();

//        initStudentArrayList();

        adapter = new StuAdapter(MainActivity.this, studentArrayList, MainActivity.this);
        readDataFromDB();

        lvStu.setAdapter(adapter);
        lvStu.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Student student = studentArrayList.get(i);
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_edit.class);
                intent.putExtra("STUDENT", student);
//                intent.putExtra("STUNO", student.getStuno());
//                intent.putExtra("NAME", student.getName());
//                intent.putExtra("AGE", student.getAge());
                startActivityForResult(intent, 1002);
            }
        });
    }

    //初始化学生列表
    private void initStudentArrayList() {
        studentArrayList.add(new Student("17001", "lisa", 18));
        studentArrayList.add(new Student("17002", "lier", 18));
        studentArrayList.add(new Student("17003", "liwu", 18));
        studentArrayList.add(new Student("17004", "liqi", 18));
    }

    private void findViews() {
        lvStu = findViewById(R.id.lv_stu);
        btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, Activity_add.class);
                startActivityForResult(intent, 1001);
            }
        });

        edtKey = findViewById(R.id.edt_key);
        ivSearch = findViewById(R.id.iv_search);
        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String key = edtKey.getText().toString();
                Uri uri = Uri.parse("content://com.hie.student/student");
                String where = "name like '%" + key + "%' OR stuno like '%" + key + "%' " +
                        "OR age = '" + key + "'";
                studentArrayList.clear();
                Cursor cursor = getContentResolver().query(uri,null,where,null,null);
                if (cursor != null && cursor.getCount() > 0) {
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        String stuno = cursor.getString(0);
                        String name = cursor.getString(1);
                        int age = cursor.getInt(2);

                        Student student = new Student(stuno, name, age);
                        studentArrayList.add(student);
                    }
                }
                adapter.notifyDataSetChanged();
                cursor.close();
//                searchFromDB(key);
            }
        });

        edtKey.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key = edtKey.getText().toString();
                Uri uri = Uri.parse("content://com.hie.student/student");
                String where = "name like '%" + key + "%' OR stuno like '%" + key + "%' " +
                        "OR age = '" + key + "'";
                studentArrayList.clear();
                Cursor cursor = getContentResolver().query(uri,null,where,null,null);
                if (cursor != null && cursor.getCount() > 0) {
                    for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                        String stuno = cursor.getString(0);
                        String name = cursor.getString(1);
                        int age = cursor.getInt(2);

                        Student student = new Student(stuno, name, age);
                        studentArrayList.add(student);
                    }
                }
                adapter.notifyDataSetChanged();
                cursor.close();
//                searchFromDB(key);
            }
        });
    }
    //没有封装的查询
    private void searchFromDB(String key) {
        SQLiteDatabase db = studbOpenHelper.getReadableDatabase();

        String where = "name like '%" + key + "%' OR stuno like '%" + key + "%' " +
                "OR age = '" + key + "'";

        studentArrayList.clear();
        Cursor cursor = db.query("student", null, where,
                null, null, null, null, null);

        if (cursor != null && cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String stuno = cursor.getString(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);

                Student student = new Student(stuno, name, age);
                studentArrayList.add(student);
            }
        }

        adapter.notifyDataSetChanged();

        cursor.close();

        db.close();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == 2001) {
            Uri uri = Uri.parse("content://com.hie.student/student");
            Student student = (Student) data.getSerializableExtra("STUDENT");
//            String stuno = data.getStringExtra("STUNO");
//            String name = data.getStringExtra("NAME");
//            int age = data.getIntExtra("AGE", 0);
//
//            Student student =  new Student(stuno, name, age);
            ContentValues values = new ContentValues();
            values.put("stuno", student.getStuno());
            values.put("name", student.getName());
            values.put("age", student.getAge());
            getContentResolver().insert(uri, values);
            readDataFromDB();
//            addStudentToDB(student);
        } else if (resultCode == 3001) {
            Uri uri = Uri.parse("content://com.hie.student/student");
            Student student = (Student) data.getSerializableExtra("STUDENT");
//            String stuno = data.getStringExtra("STUNO");
//            String name = data.getStringExtra("NAME");
//            int age = data.getIntExtra("AGE", 0);
//
//            Student student =  new Student(stuno, name, age);
            ContentValues values = new ContentValues();
            values.put("name", student.getName());
            values.put("age", student.getAge());
            String where = "stuno = '" + student.getStuno() + "'";
            int rows = getContentResolver().update(uri,values,where,null);
            Toast.makeText(MainActivity.this,"更新了"+rows+"行",Toast.LENGTH_SHORT).show();
//            updateStudentToDB(student);
            readDataFromDB();
        }
    }
    //m没有封装的update
    private void updateStudentToDB(Student student) {
        SQLiteDatabase db = studbOpenHelper.getWritableDatabase();

        /** 第一种 组装语句
         String sql = "update student set name = '"+student.getName()+"'," +
         " age = "+student.getAge()+" where stuno = '"+student.getStuno()+"'";
         db.execSQL(sql);
         db.close();
         readDataFromDB();*/

        // 第二种方式 update
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("age", student.getAge());
        String where = "stuno = '" + student.getStuno() + "'";
        db.update("student", values, where, null);
        db.close();
        readDataFromDB();

    }
    //没有封装的add
    private void addStudentToDB(Student student) {
        SQLiteDatabase db = studbOpenHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("stuno", student.getStuno());
        values.put("name", student.getName());
        values.put("age", student.getAge());

        db.insert("student", null, values);
        db.close();
        readDataFromDB();
    }

    @Override
    public void del(Student student) {

        Uri uri = Uri.parse("content://com.hie.student/student");
        String where = "stuno = '"+student.getStuno()+"'";
        int rows = getContentResolver().delete(uri,where,null);
        Toast.makeText(MainActivity.this,"删除了"+rows+"行",Toast.LENGTH_SHORT).show();


//        SQLiteDatabase db = studbOpenHelper.getWritableDatabase();
       /* 删除也有两种 一种是execSql()
       String sql = "delete from student where stuno = '"+student.getStuno()+"'";
       db.execSQL(sql);
       db.close();
       readDataFromDB();
       */

       /* 第二种是使用delete接口
        String where = "stuno = '" + student.getStuno() + "'";
        db.delete("student", where, null);
        db.close();
        readDataFromDB();*/

         /*第三种是使用delete接口
        String where = "stuno = ?";
        String[] argArray = {student.getStuno()};
        db.delete("student", where, argArray);
        db.close();*/
        readDataFromDB();
    }

    /**
     * 从数据库读取学生列表
     * 使用ContentProvider
     */
    private void  readDataFromDB() {
        Uri uri = Uri.parse("content://com.hie.student/student");
        Cursor cursor = getContentResolver().query(uri,null,null,null,null);
        studentArrayList.clear();
        if (cursor != null && cursor.getCount() > 0) {
            for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext()) {
                String stuno = cursor.getString(0);
                String name = cursor.getString(1);
                int age = cursor.getInt(2);

                Student student = new Student(stuno, name, age);
                studentArrayList.add(student);
            }
        }
        adapter.notifyDataSetChanged();
        cursor.close();
    }
}