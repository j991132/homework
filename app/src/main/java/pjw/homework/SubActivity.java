package pjw.homework;

import android.content.Intent;
import android.support.v7.app.ActionBar.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SubActivity extends AppCompatActivity {

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

        if (sNum > 4 ) {

            for (i = 0; i < sNum; i++) {
                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                final Button btn = new Button(this);

                btn.setText("" + (i+1));
                btn.setId((i+1));
                btn.setLayoutParams(params);
                ll.addView(btn);
                linear.addView(ll);

            }
        }else {
            for (i = 0; i < sNum; i++) {
                final Button btn = new Button(this);
                btn.setText("" + (i+1));
                btn.setId((i+1));
                btn.setLayoutParams(params);
                linear.addView(btn);
            }

        }


    }
}
