package pjw.homework;

import android.content.Intent;
import android.support.v7.app.ActionBar;
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

        LinearLayout linear = (LinearLayout) findViewById(R.id.btnLayout);
// linearLayout params 정의
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


        Button btn[] = new Button[sNum];

        int i;
        LinearLayout ll = new LinearLayout(this);
        ll.setOrientation(LinearLayout.HORIZONTAL);

        for (i = 0; i < sNum; i++) {
            int j = i + 1;


            if (sNum > 4 ) {
                ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                btn[i] = new Button(this);
                btn[i].setText("" + j);
                btn[i].setId(j);
                ll.addView(btn[i]);
            } else {

                btn[i] = new Button(this);
                btn[i].setText("" + j);
                btn[i].setId(j);
                ll.addView(btn[i]);
            }


        }


        linear.addView(ll);
    }
}
