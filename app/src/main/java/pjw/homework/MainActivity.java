package pjw.homework;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
//변수선언
    Button addBtn;
    LinearLayout layout;
    Context context;
    ScrollView sv;
    int count = 0;
    String id;
    int num=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = (Button)findViewById(R.id.addBtn);
        //sv = (ScrollView)findViewById(R.id.scrollView);
        layout = (LinearLayout)findViewById(R.id.layout);
        context = this;
//추가버튼 눌렀을때
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                count++;

//다이얼로그 생성
                final Dialog ok = new Dialog(context);
                        ok.setTitle("과제명과 학생 수 입력");
                        ok.setContentView(R.layout.info);

                Button dia = (Button) ok.findViewById(R.id.btn_ok);
//다이얼로그의 확인버튼 눌렀을 때
                dia.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText editId = (EditText)ok.findViewById(R.id.edit_id);
//과제명에 입력값이 없을때 구분하기(입력된 값의 길이로 비교)
                        if (editId.getText().toString().length() == 0) {
                            id=null;
                        }else {
                            id = editId.getText().toString();
                        }
                        EditText editNum = (EditText)ok.findViewById(R.id.edit_num);
                        if (editNum.getText().toString().length() == 0) {
                            num = 0;
                        }else {
                            num = Integer.parseInt(editNum.getText().toString());
                        }
                        if (id !=null && num !=0){

                            Toast.makeText(MainActivity.this, "과제명"+id+"학생수"+num,
                                    Toast.LENGTH_SHORT).show();

                            Button btn = new Button(context);
                            //btn.setText("버튼" + String.valueOf(count)); 원래 번호증가 버튼
                            btn.setText("" + id);
                            btn.setId(count);
                            btn.setOnClickListener(new View.OnClickListener() {
                                                       @Override
                                                       public void onClick(View v) {
                                                           Toast.makeText(MainActivity.this, (v.getId()) + "액티비티호출",
                                                                   Toast.LENGTH_SHORT).show();
                                                       }
                                                   }

                            );
                            layout.addView(btn);
                            ok.dismiss();
                        }else {
                            Toast.makeText(MainActivity.this, "입력된 과제명 또는 학생 수가 없습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                ok.show();


                }
            });
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState); // 반드시 호출해 주세요.
        // 상태임시저장하기
        // 추가로 자료를 저장하는 코드는 여기에 작성 하세요.
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // 추가로 자료를 복원하는 코드는 여기에 작성하세요.
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // 추가로 자료를 복원하는 코드는 여기에 작성하세요.
    }

}
