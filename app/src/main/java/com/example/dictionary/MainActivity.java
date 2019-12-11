package com.example.dictionary;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dictionary.Words.Words;
import com.example.dictionary.Words.WordsAction;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    ImageView re,collect;
    TextView tv1,tv2,tv3,tv4;
    EditText et,ete,etc,ets1,ets2,etee,etcc,ets11,ets22;
   WordsAction wordsAction;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add:
                        AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                        LayoutInflater inflater=getLayoutInflater();
                        final View view=inflater.inflate(R.layout.addwords,null);
                        builder.setView(view).setTitle("添加单词")
                                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                    @Override

                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        ete = (EditText) view.findViewById(R.id.ete);
                                        etc = (EditText) view.findViewById(R.id.etc);
                                        ets1 = (EditText) view.findViewById(R.id.ets1);
                                        ets2 = (EditText) view.findViewById(R.id.ets2);

                                        if (ete.getText().toString().isEmpty()|| etc.getText().toString().isEmpty()
                                                || ets1.getText().toString().isEmpty()|| ets2.getText().toString().isEmpty()) {
                                            Toast.makeText(MainActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Words words = new Words();
                                            words.setEnglish(ete.getText().toString());
                                            words.setChinese(etc.getText().toString());
                                            words.setS1(ets1.getText().toString());
                                            words.setS2(ets2.getText().toString());
                                            wordsAction.addWords(words);
                                            Toast.makeText(MainActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                        builder.show();
                break;
            case R.id.danci:
                Intent intent1=new Intent(MainActivity.this,Myvocabulary.class);
                startActivity(intent1);
                break;
            case R.id.change:
                AlertDialog.Builder builder2=new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater2=getLayoutInflater();
                final View view2=inflater2.inflate(R.layout.wordschange,null);
                etee = (EditText) view2.findViewById(R.id.etee);
                etcc = (EditText) view2.findViewById(R.id.etcc);
                ets11 = (EditText) view2.findViewById(R.id.ets11);
                ets22 = (EditText) view2.findViewById(R.id.ets22);
                Words words2 = wordsAction.findWordsFromMySql(et.getText().toString());
                etee.setText(et.getText().toString());
                etcc.setText(words2.getChinese());
                ets11.setText(words2.getS1());
                ets22.setText(words2.getS2());
                builder2.setView(view2).setTitle("修改单词")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (etee.getText().toString().isEmpty()|| etcc.getText().toString().isEmpty()
                                        || ets11.getText().toString().isEmpty()|| ets22.getText().toString().isEmpty()) {
                                    Toast.makeText(MainActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
                                } else {
                                    Words words = new Words();
                                    words.setEnglish(etee.getText().toString());
                                    words.setChinese(etcc.getText().toString());
                                    words.setS1(ets11.getText().toString());
                                    words.setS2(ets22.getText().toString());
                                    wordsAction.changewords(words);
                                    wordsAction.changeMyords(words);
                                    Toast.makeText(MainActivity.this, "修改成功", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                builder2.show();
                break;
            default:
                break;
        }
        return true;
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wordsAction = new WordsAction(MainActivity.this);
       re=(ImageView) findViewById(R.id.re);
        collect=(ImageView) findViewById(R.id.collect);
        tv1=(TextView)findViewById(R.id.tv1);
         tv2=(TextView)findViewById(R.id.tv2);
         tv3=(TextView)findViewById(R.id.tv3);
         tv4=(TextView)findViewById(R.id.tv4);
         et=(EditText)findViewById(R.id.et);
       re.setOnClickListener(this);
        collect.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.re:
                tv1.setText(et.getText().toString());
                Words words = wordsAction.findWordsFromMySql(et.getText().toString());
                tv2.setText(words.getChinese());
                tv3.setText(words.getS1());
                tv4.setText(words.getS2());
                break;
            case R.id.collect:
                if (tv1.getText().toString().isEmpty()||tv2.getText().toString().isEmpty()
                ||tv3.getText().toString().isEmpty()||tv4.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                } else {
                    Words mywords = new Words();
                    mywords.setEnglish(tv1.getText().toString());
                    mywords.setChinese(tv2.getText().toString());
                    mywords.setS1(tv3.getText().toString());
                    mywords.setS2(tv4.getText().toString());
                    wordsAction.addMyords(mywords);
                    Toast.makeText(MainActivity.this, "成功添加到个人单词本", Toast.LENGTH_SHORT).show();
                    break;

                }
        }
    }
}
