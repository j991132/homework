package pjw.homework;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //전역변수선언
    Button addBtn;
    Button[] btn = new Button[20];
    LinearLayout layout;
    Context context;
    ScrollView sv;
    int count = 0;
    String id = null;
    String[] subject = new String[20];
    int num = 0;
    int[] studentnumber = new int[20];
    String Id;
    int[] activitynumber = new int[20];
    int i=0;
    String[] name= new String[20];
    int[] studentnum = new int[20];
    int[] actnum = new int[20];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        addBtn = (Button) findViewById(R.id.addBtn);
        //sv = (ScrollView)findViewById(R.id.scrollView);
        layout = (LinearLayout) findViewById(R.id.layout);
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
                        EditText editId = (EditText) ok.findViewById(R.id.edit_id);
//과제명에 입력값이 없을때 구분하기(입력된 값의 길이로 비교)
                        if (editId.getText().toString().length() == 0) {
                            id = null;
                        } else {
                            id = editId.getText().toString();
                        }
                        EditText editNum = (EditText) ok.findViewById(R.id.edit_num);
                        if (editNum.getText().toString().length() == 0) {
                            num = 0;
                        } else {
                            num = Integer.parseInt(editNum.getText().toString());
                        }
                        if (id != null && num != 0) {

                            Toast.makeText(MainActivity.this, "과제명" + id + "학생수" + num,
                                    Toast.LENGTH_SHORT).show();

                            subject[count-1] = id;
                            studentnumber[count-1]=num;
                            activitynumber[count-1]=count;
                            btn[count-1] = new Button(context);
                            //btn.setText("버튼" + String.valueOf(count)); 원래 번호증가 버튼
                            btn[count-1].setText("" + subject[count - 1]);
                            btn[count-1].setId(count);
                            btn[count-1].setOnClickListener(new View.OnClickListener() {
                                                                @Override
                                                                public void onClick(View v) {
                                                                    Toast.makeText(MainActivity.this, (v.getId()) + "액티비티호출",
                                                                            Toast.LENGTH_SHORT).show();
                                                                }
                                                            }

                            );
                            layout.addView(btn[count-1]);
                            ok.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "입력된 과제명 또는 학생 수가 없습니다.",
                                    Toast.LENGTH_SHORT).show();
                        }


                    }
                });
                ok.show();


            }
        });
    }

   // @Override
   // protected void onSaveInstanceState(Bundle outState) {
   //     super.onSaveInstanceState(outState); // 반드시 호출해 주세요.
        // 상태임시저장하기
        // 추가로 자료를 저장하는 코드는 여기에 작성 하세요.

   // }

  //  @Override
  //  protected void onRestoreInstanceState(Bundle savedInstanceState) {
   //     super.onRestoreInstanceState(savedInstanceState);

        // 추가로 자료를 복원하는 코드는 여기에 작성하세요.
  //  }

    @Override
    protected void onPause() {
        super.onPause();

        // 추가로 자료를 복원하는 코드는 여기에 작성하세요.
        saveCurrentState();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // 추가로 자료를 복원하는 코드는 여기에 작성하세요.


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // 추가로 자료를 복원하는 코드는 여기에 작성하세요.

    }

    @Override
    protected void onResume() {
        super.onResume();

        // 추가로 자료를 복원하는 코드는 여기에 작성하세요.
      restoreFromSavedState();
      addbtn();
    }

    protected void saveCurrentState() {
        SharedPreferences pref = getSharedPreferences("SaveState", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        for(i=0;i<count;i++) {
            edit.putInt("actnum["+i+"]", activitynumber[i]);
            edit.putString("name["+i+"]", subject[i]);
            edit.putInt("studentnum["+i+"]", studentnumber[i]);
             }
        edit.putInt("actcount", count);
        edit.commit();
    }

    protected void restoreFromSavedState() {
        SharedPreferences pref = getSharedPreferences("SaveState", MODE_PRIVATE);
        count = pref.getInt("actcount", count);
        for(i=0;i<count;i++) {
            activitynumber[i] = pref.getInt("actnum", activitynumber[i]);
            subject[i] = pref.getString("name["+i+"]", subject[i]);
            studentnumber[i] = pref.getInt("studentnum", studentnumber[i]);

        }
    }

    protected void addbtn() {
        if (count != 0) {

          for (i = 0; i < count; i++) {
          btn[i] = new Button(context);
        //btn.setText("버튼" + String.valueOf(count)); 원래 번호증가 버튼
          btn[i].setText("" + subject[i]);
          btn[i].setId(i + 1);
          btn[i].setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          Toast.makeText(MainActivity.this, (v.getId()) + "액티비티호출",
                                                  Toast.LENGTH_SHORT).show();
                                      }
                                  }

        );
          layout.addView(btn[i]);
    }
}

        }
    }

