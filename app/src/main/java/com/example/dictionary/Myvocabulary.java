package com.example.dictionary;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dictionary.Words.Words;
import com.example.dictionary.Words.WordsAction;

public class Myvocabulary extends AppCompatActivity
{
    String[] newData={};
public void init(String[] data){
    ArrayAdapter<String> adapter=new ArrayAdapter<String>(Myvocabulary.this,android.R.layout.simple_list_item_1,data);
    ListView listView=(ListView)findViewById(R.id.listview);
    listView.setAdapter(adapter);
}
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mywords);
        final WordsAction wordsAction=new WordsAction(Myvocabulary.this);
        final Words[] wordsarry=wordsAction.findMywords();
        final String[] data=new String[wordsarry.length];
        newData=new String[wordsarry.length];
        for(int i=0;i<wordsarry.length;i++){
            data[i]=wordsarry[i].getEnglish();
            newData[i]=wordsarry[i].getEnglish();
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Myvocabulary.this,android.R.layout.simple_list_item_1,data);
        ListView listView=(ListView)findViewById(R.id.listview);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, final int position, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(Myvocabulary.this);
                LayoutInflater inflater=getLayoutInflater();
                final View view2=inflater.inflate(R.layout.deletewords,null);
                builder.setView(view2).setTitle("删除单词")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                            wordsAction.deletewords(data[position]);
                                Toast.makeText(Myvocabulary.this, "删除成功"+data[position], Toast.LENGTH_SHORT).show();
                                new Thread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Message msg = new Message();
                                        msg.what=updateUI;
                                        handler.sendMessage(msg);
                                    }
                                }).start();
                            }

                });
                builder.show();
            }
        });
    }
    final public int updateUI=1;
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case updateUI:
                    init(newData);
                    break;
                default:
                    break;
            }
        }
    };
}
