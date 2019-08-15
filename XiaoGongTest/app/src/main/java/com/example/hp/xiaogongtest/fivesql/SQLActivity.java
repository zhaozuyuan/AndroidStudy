package com.example.hp.xiaogongtest.fivesql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.hp.xiaogongtest.R;

import java.util.Random;

public class SQLActivity extends AppCompatActivity {
    private SQLiteDbHelper helper;
    private SQLiteDatabase db ;

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        helper = new SQLiteDbHelper(this.getApplicationContext());
        db = helper.getReadableDatabase();

        textView = (TextView)findViewById( R.id.sql_show);
        textView.setText("数据库`"+getAllData());

        findViewById(R.id.sql_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertStudents();
                textView.setText("数据库"+getAllData());
            }
        });
    }

    public void insertStudents(){

        Cursor cursor = db.query(SQLiteDbHelper.TABLE_STUDENT,new String[]{"id"},null,null,
                null,null,null);

        cursor.moveToLast();

        int last = cursor.getInt(0);

        for(int i = last;i <= last+5;i++){
            ContentValues values = studentContentValues(mockStudent(i));
            db.insert(SQLiteDbHelper.TABLE_STUDENT,null,values);
        }
    }

    public Student mockStudent(int i){
        Student student = new Student();
        student.id = i;
        student.name = "user - "+i;
        student.tel_no = String.valueOf(new Random().nextInt(200000));
        student.cls_id = new Random().nextInt(5);
        student.age = new Random().nextInt(18);

        return student;

    }

    public ContentValues studentContentValues(Student student){
        ContentValues values = new ContentValues();
        values.put("id",student.id);
        values.put("name",student.name);
        values.put("tel_no",student.tel_no);
        values.put("cls_id",student.cls_id);
        values.put("age",student.age);

        return values;
    }

    public String getAllData(){
        StringBuffer allData = new StringBuffer("\n");
        Cursor cursor = db.query(SQLiteDbHelper.TABLE_STUDENT,null,null,null,
                null,null,null,null);

        while (cursor.moveToNext()){
            String single = cursor.getInt(0)+"\t\t"
                    +cursor.getString(cursor.getColumnIndex("name"))+"\t\t"
                    +cursor.getString(cursor.getColumnIndex("tel_no"))+"\t\t"
                    +cursor.getInt(cursor.getColumnIndex("cls_id"))+"\t\t"
                    +cursor.getInt(cursor.getColumnIndex("age"));

            allData.append("\n"+single);
        }

        cursor.close();

        return allData.toString();
    }

    public void byExecutor(){
        new DbCommand<String>() {
            @Override
            public String doInBackground() {
                return null;
            }

            @Override
            public void onPostExecute(String result) {

            }
        }.execute();

    }
}
