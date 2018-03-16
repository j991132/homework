package pjw.homework;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Arrays;

public class SubActivity extends AppCompatActivity {
    int i;
    int j;
    int sNum;
    ArrayList color;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);
//인텐트 넘어온 값 저장
        Intent intent = getIntent();
        String subText = intent.getStringExtra("subName");
        int sNum = intent.getIntExtra("btnNum", 0);
//타이틀에 넘어온 id값 표시하기
        TextView title = (TextView) findViewById(R.id.subjectname);
        title.setText(subText);



        final LinearLayout linear = (LinearLayout) findViewById(R.id.btnLayout);
// linearLayout params 정의
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);



        int k = 0;
        int l=0;


        if (sNum <= 4) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            for (i = 0; i < sNum; i++) {

                final ToggleButton btn = new ToggleButton(this);
                btn.setText("" + (i + 1)); //첫 텍스트 보이기
                btn.setTextOn("" + (i + 1)); //토클온 텍스트
                btn.setTextOff("" + (i + 1)); //토클오프 텍스트
                btn.setId((i + 1));

                btn.setLayoutParams(params);
                btn.setBackgroundColor(Color.GREEN);
//토클키 설정하기
                btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                         color = new ArrayList();

                        if (isChecked == true){
                            //빨간색 표시
                            btn.setBackgroundColor(Color.RED);
                            color.add(buttonView.getId(),"RED");
                        } else {
                           //녹색 표시
                            btn.setBackgroundColor(Color.GREEN);
                            color.add(buttonView.getId(),"GREEN");
                        }

                    }
                });
                ll.addView(btn);
            }
            linear.addView(ll);
        } else{

            for (i = 0; i < Math.ceil((float)sNum / 4); i++) { //소수이용 올림수 처리로 4개 이상시 레이어 추가
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                for (j = k; j < k+4; j++) {
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
                            if (isChecked == true){
                                //빨간색 표시
                                btn.setBackgroundColor(Color.RED);
                            } else {
                                //녹색 표시
                                btn.setBackgroundColor(Color.GREEN);
                            }

                        }
                    });
                    if(l<sNum) {
                        ll.addView(btn);
                        l++;
                    }
                }
                linear.addView(ll);

                k = k + 4;

            }


        }
    }
 //상태저장 매서드
    protected void saveCurrentState()
    {
        SharedPreferences pref = getSharedPreferences( "SaveState", MODE_PRIVATE );
        SharedPreferences.Editor edit = pref.edit();

        JSONArray array = new JSONArray();
        for (i=0; i < color.size(); i++)
        {
            array.put(color.get(i));
        }
        edit.putString("ToggleColor", array.toString());
        edit.commit();
    }
//상태복구 매서드
    protected void restoreFromSavedState() {
        SharedPreferences pref = getSharedPreferences("SaveState", MODE_PRIVATE);

        String json = pref.getString("ToggleColor", null);
        if (json != null) {
            try {
                JSONArray array = new JSONArray(json);
                color.clear();

                for (i = 0; i < array.length(); i++) {
                    String url = array.optString(i);
                    color.add(url);
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
        }
    }
    else {

        }
    }
}
