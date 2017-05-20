package pjw.homework;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {
    int btnColor=0;
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

        int i;
        int j;
        int k = 0;
        int l=0;


        if (sNum <= 4) {
            LinearLayout ll = new LinearLayout(this);
            ll.setOrientation(LinearLayout.HORIZONTAL);
            for (i = 0; i < sNum; i++) {

                final Button btn = new Button(this);
                btn.setText("" + (i + 1));
                btn.setId((i + 1));
                btn.setLayoutParams(params);
                btn.setBackgroundColor(Color.GREEN);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                       if ( btnColor == 0) {
                           btn.setBackgroundColor(Color.RED);
                           btnColor = 1;
                       }else{
                           btn.setBackgroundColor(Color.GREEN);
                           btnColor=0;
                       }
                    }
                });

                ll.addView(btn);
            }
            linear.addView(ll);
        } else{

            for (i = 0; i < Math.ceil((float)sNum / 4); i++) {
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);

                for (j = k; j < k+4; j++) {
                    final Button btn = new Button(this);
                    btn.setText("" + (j + 1));
                    btn.setId((j + 1));
                    btn.setLayoutParams(params);
                    btn.setBackgroundColor(Color.GREEN);
                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if ( btnColor == 0) {
                                btn.setBackgroundColor(Color.RED);
                                btnColor = 1;
                            }else{
                                btn.setBackgroundColor(Color.GREEN);
                                btnColor=0;
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
}
