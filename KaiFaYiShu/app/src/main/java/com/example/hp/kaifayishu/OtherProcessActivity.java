package com.example.hp.kaifayishu;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class OtherProcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_process);

        Uri uri = Uri.parse("content://com.example.hp.kaifayishu.book.provider/book");
        Cursor cursor = getContentResolver().query(uri, null, null, null,
                null, null);
        assert cursor != null;
        cursor.moveToFirst();
        Log.d("TAG",cursor.getInt(0) + cursor.getString(1));
        cursor.close();
    }
}
