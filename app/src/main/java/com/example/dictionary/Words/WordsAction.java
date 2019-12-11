package com.example.dictionary.Words;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.example.dictionary.Database.Mysql;
import com.example.dictionary.MainActivity;

public class WordsAction {
    SQLiteDatabase db;
    private Context m;
 public  WordsAction(Context context){
     Mysql mysql = new Mysql(context,"vocabulary",null,1);
     db= mysql.getWritableDatabase();
     m=context;
 }
    public void addWords(Words words) {
        ContentValues values=new ContentValues();
        values.put("english",words.getEnglish());
        values.put("chinese",words.getChinese());
        values.put("sentence1",words.getS1());
        values.put("sentence2",words.getS2());
        db.insert("vocabulary",null,values);
        values.clear();
    }
    public void addMyords(Words words) {
        ContentValues values=new ContentValues();
        values.put("myenglish",words.getEnglish());
        values.put("mychinese",words.getChinese());
        values.put("mysentence1",words.getS1());
        values.put("mysentence2",words.getS2());
        db.insert("Myvocabulary",null,values);
        values.clear();
    }
    public void changeMyords(Words words) {
        ContentValues values=new ContentValues();
        values.put("myenglish",words.getEnglish());
        values.put("mychinese",words.getChinese());
        values.put("mysentence1",words.getS1());
        values.put("mysentence2",words.getS2());
        db.update("Myvocabulary",values,"myenglish=?",new String[]{words.getEnglish()});
    }
    public void changewords(Words words) {
        ContentValues values=new ContentValues();
        values.put("english",words.getEnglish());
        values.put("chinese",words.getChinese());
        values.put("sentence1",words.getS1());
        values.put("sentence2",words.getS2());
        db.update("vocabulary",values,"english=?",new String[]{words.getEnglish()});
    }
    public void deletewords(String english){
//        db.execSQL("delete from Myvocabulary where english=?", new String[] {english});
        db.delete("Myvocabulary","myenglish=?",new String[]{english});
    }
 public Words findWordsFromMySql(String key){
     Words words = new Words();
     Cursor cursor =  db.query("vocabulary",null, "english= ?",new String[]{ key},null,null,null);
     if(cursor.getCount()>0){
     while(cursor.moveToNext()){
         words.setChinese(cursor.getString(cursor.getColumnIndex("chinese")));
         words.setS1(cursor.getString(cursor.getColumnIndex("sentence1")));
         words.setS2(cursor.getString(cursor.getColumnIndex("sentence2")));
        }
     }
    else{
         Toast.makeText(m,"未找到搜索结果",Toast.LENGTH_SHORT).show();
     }
    cursor.close();
     return words;
 }
    public Words[] findMywords(){
        Words words = new Words();
        Words[] wordsarray={};
        Cursor cursor =  db.query("Myvocabulary",null, null,null,null,null,null);
        if(cursor.getCount()>0){
            wordsarray=new Words[cursor.getCount()];
            while(cursor.moveToNext()){
                words.setEnglish(cursor.getString(cursor.getColumnIndex("myenglish")));
                words.setChinese(cursor.getString(cursor.getColumnIndex("mychinese")));
                words.setS1(cursor.getString(cursor.getColumnIndex("mysentence1")));
                words.setS2(cursor.getString(cursor.getColumnIndex("mysentence2")));
                wordsarray[cursor.getPosition()]=words;
                words=new Words();
            }
        }
        cursor.close();
        return wordsarray;
    }
}
