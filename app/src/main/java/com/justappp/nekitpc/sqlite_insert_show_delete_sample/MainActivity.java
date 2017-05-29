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
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MyLog";
    EditText edtName, edtPhone, edtUpdateDelete;

    Button btnInsertData, btnShowTable, btnDeleteTable, btnUpdateData, btnDeleteDataById;

    RadioGroup radioGroup;

    DataBase dataBase;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // создаем объект нашей базы
        dataBase = new DataBase(this, "DB", null, 1);

        // передаем возможность редактирования нашей базы
        sqLiteDatabase = dataBase.getWritableDatabase();

        edtName = (EditText) findViewById(R.id.edtName);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtUpdateDelete = (EditText) findViewById(R.id.edtUpdateDelete);

        btnInsertData = (Button) findViewById(R.id.btnInsertData);
        btnShowTable = (Button) findViewById(R.id.btnShowTable);
        btnDeleteTable = (Button) findViewById(R.id.btnDeleteTable);
        btnUpdateData = (Button) findViewById(R.id.btnUpdateData);
        btnDeleteDataById = (Button) findViewById(R.id.btnDeleteDataById);

        btnInsertData.setOnClickListener(this);
        btnShowTable.setOnClickListener(this);
        btnDeleteTable.setOnClickListener(this);
        btnUpdateData.setOnClickListener(this);
        btnDeleteDataById.setOnClickListener(this);

        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);

        radioGroupSorted();
    }

    @Override
    public void onClick(View v) {
        ContentValues cv = new ContentValues();
        switch (v.getId()) {
            case R.id.btnInsertData: // Запись данных "name" и "phone" в таблицу "Contact"
                cv.put("name", edtName.getText().toString());
                cv.put("phone", edtPhone.getText().toString());
                sqLiteDatabase.insert("Contact", null, cv);
                Log.d(TAG, "Insert data");
                break;
            case R.id.btnShowTable: // показ таблицы "Contact" в консоль
                Cursor cursor = sqLiteDatabase.query("Contact", null, null, null, null, null, null);
                // курсор в первую ячейку
                if (cursor.moveToFirst()) {
                    //курсор начинает считывать с первой ячейки и пока у нас есть ячейки
                    do {
                        Log.d("log", " id " + cursor.getInt(cursor.getColumnIndex("id"))
                                + " name " + cursor.getString(cursor.getColumnIndex("name"))
                                + " phone " + cursor.getInt(cursor.getColumnIndex("phone")));
                    } while (cursor.moveToNext());
                }
                cursor.close();
                break;
            case R.id.btnDeleteTable: // удаление всей таблицы
                sqLiteDatabase.delete("Contact", null, null); // Удаление всей таблицы
                Log.d(TAG, "Table deleted");
                break;
            case R.id.btnUpdateData: // обновление данных таблицы по id
                cv.put("name", edtName.getText().toString());
                cv.put("phone", edtPhone.getText().toString());
                sqLiteDatabase.update("Contact", cv, "id = ?", new String[]{edtUpdateDelete.getText().toString()});
                Log.d(TAG, "Table updated");
                break;
            case R.id.btnDeleteDataById: // удаление данных таблицы по id
                sqLiteDatabase.delete("Contact", "id = " + edtUpdateDelete.getText().toString(), null);
                Log.d(TAG, "Data deleted");
                break;
        }
    }

    private void radioGroupSorted() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbByName: // сортировка и вывод таблицы по "name"
                        Cursor cursor = sqLiteDatabase.query("Contact", null, null, null, null, null, "name"); // сортировка таблицы по "name"
                        // курсор в первую ячейку
                        if (cursor.moveToFirst()) {
                            //курсор начинает считывать с первой ячейки и пока у нас есть ячейки
                            do { // вывод таблицы в консоль
                                Log.d("log", " id " + cursor.getInt(cursor.getColumnIndex("id"))
                                        + " name " + cursor.getString(cursor.getColumnIndex("name"))
                                        + " phone " + cursor.getInt(cursor.getColumnIndex("phone")));
                            } while (cursor.moveToNext());
                        }
                        cursor.close();
                        break;

                    case R.id.rbByPhone: // сортировка и вывод таблицы по "phone" в консоль
                        cursor = sqLiteDatabase.query("Contact", null, null, null, null, null, "phone");
                        // курсор в первую ячейку
                        if (cursor.moveToFirst()) {
                            //курсор начинает считывать с первой ячейки и пока у нас есть ячейки
                            do {
                                Log.d("log", " id " + cursor.getInt(cursor.getColumnIndex("id"))
                                        + " name " + cursor.getString(cursor.getColumnIndex("name"))
                                        + " phone " + cursor.getInt(cursor.getColumnIndex("phone")));
                            } while (cursor.moveToNext());
                        }
                        cursor.close();
                        break;
                }
            }
        });
    }
}
