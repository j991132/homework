package pjw.homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SubActivity extends AppCompatActivity {
    int i;
    int j;
    int sNum ;
    String key;

        HashMap<Integer, Integer> color, value;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
//인텐트 넘어온 값 저장
        Intent intent = getIntent();
        String subText = intent.getStringExtra("subName");
        sNum = intent.getIntExtra("btnNum", 0);

//타이틀에 넘어온 id값 표시하기
        TextView title = (TextView) findViewById(R.id.subjectname);
        title.setText(subText);
        color = new HashMap<Integer, Integer>();


        final LinearLayout linear = (LinearLayout) findViewById(R.id.btnLayout);

// linearLayout params 정의
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


        int k = 0;
        int l = 0;


        if (sNum <= 4) {

            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            for (i = 0; i < sNum; i++) {

                final ToggleButton btn = new ToggleButton(this);
                btn.setText("" + (i + 1)); //첫 텍스트 보이기
                btn.setTextOn("" + (i + 1)); //토클온 텍스트
                btn.setTextOff("" + (i + 1)); //토클오프 텍스트
                btn.setId((i + 1));
                Log.d("TAG", "버튼번호"+btn.getId());
                btn.setLayoutParams(params);
                color.put(btn.getId(), 2);

                btn.setBackgroundColor(Color.GREEN);
//토클키 설정하기
                btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                        if (isChecked == true) {
                            //빨간색 표시

                            color.put(buttonView.getId(), 1);

                        } else {
                            //녹색 표시

                            color.put(buttonView.getId(), 2);

                        }
                        if (color.get(buttonView.getId()) == 1) {
                            //빨간색 표시

                            btn.setBackgroundColor(Color.RED);

                        } else {
                            //녹색 표시

                            btn.setBackgroundColor(Color.GREEN);

                        }

                    }
                });
                ll.addView(btn);
            }
            linear.addView(ll);
        } else {

            for (i = 0; i < Math.ceil((float) sNum / 4); i++) { //소수이용 올림수 처리로 4개 이상시 레이어 추가
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                for (j = k; j < k + 4; j++) {
                    final ToggleButton btn = new ToggleButton(this);
                    btn.setText("" + (j + 1));  //첫 텍스트 보이기
                    btn.setTextOn("" + (j + 1));  //토글온  텍스트
                    btn.setTextOff("" + (j + 1)); //토클오프 텍스트
                    btn.setId((j + 1));
                    btn.setLayoutParams(params);
                    btn.setBackgroundColor(Color.GREEN);
                    btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked == true) {
                                //빨간색 표시
                                btn.setBackgroundColor(Color.RED);
                            } else {
                                //녹색 표시
                                btn.setBackgroundColor(Color.GREEN);
                            }

                        }
                    });
                    if (l < sNum) {
                        ll.addView(btn);
                        l++;
                    }
                }
                linear.addView(ll);

                k = k + 4;

            }


        }


        getHashMap(key);

        Log.d("TAG", "복구시 해시맵");
        System.out.println(value);
        if( value == null) {System.out.println("value is null");}
        if (value !=null) {
            if (value.isEmpty() == false) {
                System.out.println(value);
                addbtn();
            }
        }
    }

    @Override
    protected void onPause()
    {
        super.onPause();


        saveHashMap(key, color);
        Log.d("TAG", "저장시 해시맵");
        System.out.println(color);
    }

 //상태저장 매서드
    protected void saveHashMap(String key, Map color )
    {
        SharedPreferences pref = getSharedPreferences( "SaveState", MODE_PRIVATE );
        SharedPreferences.Editor editor = pref.edit();
        //editor.putInt( "studentnum", sNum);
        Log.d("TAG", "저장시 sNum"+sNum);
/*        for   ( int s : color.keySet ())   {
            editor . putInt(String.valueOf(s), color. get ( s ));
        } editor . commit ();
*/
        Gson gson = new Gson();
        String json = gson.toJson (color);
        Log.d("TAG", "저장 json 이후"+json);
        editor.putString (key, json);

        editor.apply();



    }

//상태복구 매서드

    public HashMap<Integer,Integer> getHashMap(String key) {
       SharedPreferences pref = getSharedPreferences( "SaveState", MODE_PRIVATE );
        //sNum = pref.getInt( "studentnum", sNum );

/*
        HashMap<Integer, Integer> map =    (HashMap <Integer, Integer>) pref . getAll ();
        for   ( Integer s : map . keySet ())   {
            Integer value = map . get ( s );
*/
       Gson gson = new Gson();
        String json = pref.getString(key,(new JSONObject()).toString());
        Log.d("TAG", "복구 json 이후"+json);
        java.lang.reflect.Type type = new TypeToken<HashMap<Integer,Integer>>(){}.getType();
        value = new HashMap<Integer, Integer>();
        value =  gson.fromJson(json, type);
        return value;



        }



// 복구시 버튼 재 배치
    protected void addbtn() {
        Log.d("TAG", "addbtn 이후 value");
        System.out.println(value);
            final LinearLayout linear = (LinearLayout) findViewById(R.id.btnLayout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            Log.d("TAG", "aaaaaaaaaaaaaaaaaaaaaaaa");
            if (sNum <= 4) {
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                Log.d("TAG", "bbbbbbbbbbbbbbbbbbbbbbbbbbb" + sNum);
                for (i = 0; i < sNum; i++) {
                    Log.d("TAG", "cccccccccccccccccccccccc");
                    final ToggleButton btn = new ToggleButton(this);
                    Log.d("TAG", "dddddddddddddddddddd");
                    btn.setText("2-" + (i + 1)); //첫 텍스트 보이기
                    Log.d("TAG", "deeeeeeeeeeeeeeeeeee");
                    btn.setTextOn("2-" + (i + 1)); //토클온 텍스트
                    btn.setTextOff("2-" + (i + 1)); //토클오프 텍스트

                    btn.setId((i + 1));

                    btn.setLayoutParams(params);
                    if (value.get(btn.getId()) == 1) {
                        //빨간색 표시

                        btn.setBackgroundColor(Color.RED);

                    } else {
                        //녹색 표시

                        btn.setBackgroundColor(Color.GREEN);

                    }
//토클키 설정하기
                    btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                            if (color.get(buttonView.getId()) == 1) {
                                //빨간색 표시

                                btn.setBackgroundColor(Color.RED);

                            } else {
                                //녹색 표시

                                btn.setBackgroundColor(Color.GREEN);

                            }

                        }
                    });
                    ll.addView(btn);
                }
                linear.addView(ll);
            }
        }


}
