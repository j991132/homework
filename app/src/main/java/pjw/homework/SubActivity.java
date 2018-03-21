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
    int i, j, k, l;
    int sNum, aNum ;
    String key, subText;
    HashMap<Integer, Integer> color, value;

    LinearLayout linear, ll;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
//인텐트 넘어온 값 저장
        Intent intent = getIntent();
//클래스 아래에 전역변수 선언한 후 따로 변수타입을 또 한 번 지정하면 지역변수로 바뀌어서 인식한다. 그냥 변수명만 써서 이용하자
        subText = intent.getStringExtra("subName");
        sNum = intent.getIntExtra("btnNum", 0);
        aNum = intent.getIntExtra("actNum", 0);

//타이틀에 넘어온 id값 표시하기
        TextView title = (TextView) findViewById(R.id.subjectname);
        title.setText(subText);
//해시맵을 이렇게 위에 선언해야 지 for 아래에 선언하면 전에 있던 것이 덮어씌어 진다
        color = new HashMap<Integer, Integer>();


        linear = (LinearLayout) findViewById(R.id.btnLayout);

// linearLayout params 정의
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);


        k = 0;
        l = 0;


        if (sNum <= 4) {

            ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            for (i = 0; i < sNum; i++) {

                final ToggleButton btn = new ToggleButton(this);
                btn.setText("" + (i + 1)); //첫 텍스트 보이기
                btn.setTextOn("" + (i + 1)); //토클온 텍스트
                btn.setTextOff("" + (i + 1)); //토클오프 텍스트
                btn.setId((i + 1));

                btn.setLayoutParams(params);
                color.put(btn.getId(), 2);                  //초기 색깔 저장값은 2번으로 초록색

                btn.setBackgroundColor(Color.GREEN);
//토클키 설정하기
                btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {



                        if (isChecked == true) {
                            //빨간색 표시

                            color.put(btn.getId(), 1);

                        } else {
                            //녹색 표시

                            color.put(btn.getId(), 2);

                        }
                        if (color.get(btn.getId()) == 1) {
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
                    color.put(btn.getId(), 2);
                    btn.setBackgroundColor(Color.GREEN);
                    btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            if (isChecked == true) {
                                //빨간색 표시

                                color.put(btn.getId(), 1);

                            } else {
                                //녹색 표시

                                color.put(btn.getId(), 2);

                            }
                            if (color.get(btn.getId()) == 1) {
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
        //System.out.println(value);   값이 잘 복구됬나 찍어봤다
//복구된 해시맵이 널이 아니지만 복구매서드에 의해 해시맵은 생성됬다면 값이 비어있기 때문에
        if( value == null) {System.out.println("value is null");}
        if (value !=null) {
            if (value.isEmpty() == false) {

                addbtn();
            }
        }
    }
// 화면 꺼짐시 해시맵 저장
    @Override
    protected void onPause()
    {
        super.onPause();


        saveHashMap(key, color);

    }

 //상태저장 매서드 - 해시맵을 json을 이용해서 형태변환후 통째로 저장하는 것인데 jsom을 미리 Gson 으로 라이브러리 짜 놓은 것을 이용해서 코딩을 간단히 줄인다.(라이브러리 추가작업 필요)
    protected void saveHashMap(String key, Map color )
    {
        SharedPreferences pref = getSharedPreferences( "SaveState"+subText, MODE_PRIVATE );
        SharedPreferences.Editor editor = pref.edit();

        Log.d("TAG", "저장시 sNum"+sNum);

        Gson gson = new Gson();
        String json = gson.toJson (color);
        Log.d("TAG", "저장 json 이후"+json);
        editor.putString (key, json);

        editor.apply();



    }

//상태복구 매서드 - 복구할 때오 Gson 을 이용하여 복구한다 복구할땐 복구용 해시맵을 또 선언해준다

    public HashMap<Integer,Integer> getHashMap(String key) {

//저장할 때 프리퍼런스 name을 액티비티 별로 가변적으로 주어 저장해야 해시맵이 중복되지 않는다
        
       SharedPreferences pref = getSharedPreferences( "SaveState"+subText, MODE_PRIVATE );
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
        linear.removeAllViews();
        Log.d("TAG", "addbtn 이후 value");
        System.out.println(value);
            linear = (LinearLayout) findViewById(R.id.btnLayout);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            k=0;
            l=0;
            if (sNum <= 4) {
                ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                for (i = 0; i < sNum; i++) {

                    final ToggleButton btn = new ToggleButton(this);

                    btn.setText("" + (i + 1)); //첫 텍스트 보이기

                    btn.setTextOn("" + (i + 1)); //토클온 텍스트
                    btn.setTextOff("" + (i + 1)); //토클오프 텍스트

                    btn.setId((i + 1));

                    btn.setLayoutParams(params);
//복구 해시맵에 저장된 상태를 이용하여 토글 색을 저장하고 바꿔준다.
                    if (value.get(btn.getId()) == 1) {
                        //빨간색 표시

                        btn.setBackgroundColor(Color.RED);
                        color.put(btn.getId(), 1);
                    } else {
                        //녹색 표시

                        btn.setBackgroundColor(Color.GREEN);
                        color.put(btn.getId(), 2);
                    }
//토클키 설정하기
                    btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                            if (color.get(btn.getId()) == 1) {
                                //빨간색 표시

                                btn.setBackgroundColor(Color.GREEN);
                                color.put(btn.getId(), 2);
                            } else {
                                //녹색 표시

                                btn.setBackgroundColor(Color.RED);
                                color.put(btn.getId(), 1);
                            }

                        }
                    });
                    ll.addView(btn);
                }
                linear.addView(ll);
            }else {
                Log.d("TAG", "addbtn 4이상일때  value");
                System.out.println(value);
                for (i = 0; i < Math.ceil((float) sNum / 4); i++) { //소수이용 올림수 처리로 4개 이상시 레이어 추가
                    ll = new LinearLayout(this);
                    ll.setOrientation(LinearLayout.HORIZONTAL);

                    for (j = k; j < k + 4; j++) {
                        final ToggleButton btn = new ToggleButton(this);
                        btn.setText("" + (j + 1));  //첫 텍스트 보이기
                        btn.setTextOn("" + (j + 1));  //토글온  텍스트
                        btn.setTextOff("" + (j + 1)); //토클오프 텍스트
                        btn.setId((j + 1));
                        btn.setLayoutParams(params);
                        Log.d("TAG", "버튼 텍스트값"+btn.getId());
                        if (value.get(btn.getId()) == 1) {
                            //빨간색 표시

                            btn.setBackgroundColor(Color.RED);
                            color.put(btn.getId(), 1);
                        } else {
                            //녹색 표시

                            btn.setBackgroundColor(Color.GREEN);
                            color.put(btn.getId(), 2);
                        }
                        btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                            @Override
                            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                if (color.get(btn.getId()) == 1) {
                                    //빨간색 표시

                                    btn.setBackgroundColor(Color.GREEN);
                                    color.put(btn.getId(), 2);
                                } else {
                                    //녹색 표시

                                    btn.setBackgroundColor(Color.RED);
                                    color.put(btn.getId(), 1);
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
        }




}
