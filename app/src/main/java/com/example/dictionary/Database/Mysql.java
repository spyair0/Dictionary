package com.example.dictionary.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class Mysql extends SQLiteOpenHelper {
    private Context mContext;
    public Mysql(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext=context;
    }

    public static final String create_vocabulary="create table vocabulary ("
            +"english text primary key ,"
            +"chinese text,"
            +"sentence1 text,"
            +"sentence2 text)";

    public static final String create_Myvocabulary="create table Myvocabulary ("
            +"myenglish text primary key ,"
            +"mychinese text,"
            +"mysentence1 text,"
            +"mysentence2 text)";

    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
   db.execSQL(create_vocabulary);
        db.execSQL(create_Myvocabulary);
        Toast.makeText(mContext,"crrate succeede",Toast.LENGTH_SHORT).show();
    }
}
