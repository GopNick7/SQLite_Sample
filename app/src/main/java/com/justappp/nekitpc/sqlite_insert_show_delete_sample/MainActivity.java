package com.justappp.nekitpc.sqlite_insert_show_delete_sample;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText edtName, edtPhone;

    Button btnInsert, btnShow, btnDelete;

    DataBase dataBase;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);

        btnInsert = (Button) findViewById(R.id.btnInsert);
        btnShow = (Button) findViewById(R.id.btnShow);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnInsert.setOnClickListener(this);
        btnShow.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        dataBase = new DataBase(this);

        // передаем возможность редактирования нашей базы
        sqLiteDatabase = dataBase.getWritableDatabase();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            // Запись данных "name" и "phone" в таблицу "Contact"
            case R.id.btnInsert:
                ContentValues cv = new ContentValues();
                cv.put("name", edtName.getText().toString());
                cv.put("phone", edtPhone.getText().toString());
                sqLiteDatabase.insert("Contact", null, cv);

                Toast.makeText(this, R.string.dataInsert, Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnShow:
                Cursor cursor = sqLiteDatabase.query("Contact", null, null, null, null, null, null);

                // курсор в первую ячейку
                if (cursor.moveToFirst()) {
                    //курсор начинает считывать с первой ячейки и пока у нас есть ячейки
                    do {
                        Log.d("log",
                                "id " + cursor.getInt(cursor.getColumnIndex("id")) +
                                        " name " + cursor.getString(cursor.getColumnIndex("name")) +
                                        " phone " + cursor.getInt(cursor.getColumnIndex("phone")));
                    } while (cursor.moveToNext());
                } else {
                    Toast.makeText(this, R.string.tableNotFound, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnDelete:
                sqLiteDatabase.delete("Contact", null, null); // Удаление всей таблицы

                Toast.makeText(this, R.string.tableDeleted, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
