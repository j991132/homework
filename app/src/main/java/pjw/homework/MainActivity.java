package pjw.homework;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity
{
	//전역변수선언
	Button addBtn;
	Button resetBtn;
	Button[] btn = new Button[20];
	LinearLayout layout;
	Context context;
	ScrollView sv;
	int count = 0;
	String id = null;
	String[] subject = new String[20];  //배열변수
	int num = 0;
	int[] studentnumber = new int[20];
	String Id;
	int[] activitynumber = new int[20];
	int i = 0;
	String[] name = new String[20];
	int[] studentnum = new int[20];
	int[] actnum = new int[20];


	@Override
	protected void onCreate( final Bundle savedInstanceState )
	{
		super.onCreate( savedInstanceState );
		setContentView( R.layout.activity_main );


		addBtn = ( Button ) findViewById( R.id.addBtn );
		resetBtn = ( Button ) findViewById( R.id.reset );
		//sv = (ScrollView)findViewById(R.id.scrollView);
		layout = ( LinearLayout ) findViewById( R.id.layout );
		context = this;
//초기화버튼 눌렀을때
		resetBtn.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View view )
			{
				SharedPreferences pref = getSharedPreferences( "SaveState", MODE_PRIVATE );
				pref.edit().clear().commit();
				count = 0;
				restoreFromSavedState();
				layout.removeAllViews();
			}
		} );
//추가버튼 눌렀을때
		addBtn.setOnClickListener( new View.OnClickListener()
		{
			@Override
			public void onClick( View v )
			{

				count++;

//다이얼로그 생성
				final Dialog ok = new Dialog( context );
				ok.setTitle( "과제명과 학생 수 입력" );
				ok.setContentView( R.layout.info );

				Button dia = ( Button ) ok.findViewById( R.id.btn_ok );
//다이얼로그의 확인버튼 눌렀을 때
				dia.setOnClickListener( new View.OnClickListener()
				{
					@Override
					public void onClick( View v )
					{
						EditText editId = ( EditText ) ok.findViewById( R.id.edit_id );
//과제명에 입력값이 없을때 구분하기(입력된 값의 길이로 비교)
						if( editId.getText().toString().length() == 0 )
						{
							id = null;
						}
						else
						{
							id = editId.getText().toString();
						}
						EditText editNum = ( EditText ) ok.findViewById( R.id.edit_num );
						if( editNum.getText().toString().length() == 0 )
						{
							num = 0;
						}
						else
						{
							num = Integer.parseInt( editNum.getText().toString() );
						}
						if( id != null && num != 0 )
						{

							Toast.makeText( MainActivity.this, "과제명:  " + id + "  학생수:  " + num,
									Toast.LENGTH_SHORT ).show();

							subject[count - 1] = id;
							studentnumber[count - 1] = num;
							activitynumber[count - 1] = count;
							btn[count - 1] = new Button( context );
							//btn.setText("버튼" + String.valueOf(count)); 원래 번호증가 버튼
							btn[count - 1].setText( subject[count - 1] );
							btn[count - 1].setId( count );

							btn[count - 1].setOnClickListener( new View.OnClickListener()
															   {
																   @Override
																   public void onClick( View v )
																   {
																	   Toast.makeText( MainActivity.this, ( v.getId() ) + "액티비티호출",
																			   Toast.LENGTH_SHORT ).show();

																	   Intent intent = new Intent(MainActivity.this, SubActivity.class);
																	   intent.putExtra("subName", subject[v.getId()-1]);
																	   intent.putExtra("btnNum", studentnumber[v.getId()-1]);
																	   startActivity(intent);
																   }
															   }

							);
							btn[count-1].setOnLongClickListener( new View.OnLongClickListener()
							{
								@Override
								public boolean onLongClick( View view )
								{
									//다이얼로그생성
									final Dialog delete = new Dialog( context );
									delete.setTitle( "삭제할까요?" );
									delete.setContentView( R.layout.delete );
									Button delBtn = ( Button ) delete.findViewById( R.id.delete );
									Button cancelBtn = ( Button ) delete.findViewById( R.id.cancel );
									// 다이얼로그 삭제버튼 누를 때
									delBtn.setTag( view );    //델버튼에 롱터치view 버튼 태그로 바꿔서 연결
									delBtn.setOnClickListener( new View.OnClickListener()
									{
										@Override
										public void onClick( View view )
										{
											View currBtn = ( View ) view.getTag(); //변수에 태그로 바꾼거 연결
											int index = Arrays.asList( btn ).indexOf( currBtn );  //배열을 리스트로 만들어서 해당버튼의 번호 찾기
											SharedPreferences pref = getSharedPreferences( "SaveState", MODE_PRIVATE );
											pref.edit()
													.remove( "actnum[" + index + "]" )
													.remove( "name[" + index + "]" )
													.remove( "studentnum[" + index + "]" )
													.commit();


											count--;
//											restoreFromSavedState();
											layout.removeView( ( View ) view.getTag() );

											saveCurrentState();

											delete.dismiss();
										}
									} );
									//다이얼로그 취소버튼 누를 때
									cancelBtn.setOnClickListener( new View.OnClickListener()
									{
										@Override
										public void onClick( View view )
										{
											delete.dismiss();
										}
									} );
									delete.show();
									return true;
								}
							} );
							layout.addView( btn[count - 1] );
							saveCurrentState();
							ok.dismiss();
						}
						else
						{
							Toast.makeText( MainActivity.this, "입력된 과제명 또는 학생 수가 없습니다.",
									Toast.LENGTH_SHORT ).show();
						}


					}
				} );



				ok.show();


			}
		} );

		//저장상태 복구
		restoreFromSavedState();
		addbtn();
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
	protected void onPause()
	{
		super.onPause();

		// 추가로 자료를 복원하는 코드는 여기에 작성하세요.
		saveCurrentState();
	}

	@Override
	protected void onRestart()
	{
		super.onRestart();

		// 추가로 자료를 복원하는 코드는 여기에 작성하세요.


	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();

		// 추가로 자료를 복원하는 코드는 여기에 작성하세요.

	}

	@Override
	protected void onResume()
	{
		super.onResume();

		// 추가로 자료를 복원하는 코드는 여기에 작성하세요.

	}

	protected void saveCurrentState()
	{
		SharedPreferences pref = getSharedPreferences( "SaveState", MODE_PRIVATE );
		SharedPreferences.Editor edit = pref.edit();
		for( i = 0; i < count; i++ )
		{
			edit.putInt( "actnum[" + i + "]", activitynumber[i] );
			edit.putString( "name[" + i + "]", subject[i] );
			edit.putInt( "studentnum[" + i + "]", studentnumber[i] );
		}
		edit.putInt( "actcount", count );
		edit.commit();
	}

	protected void restoreFromSavedState()
	{
		SharedPreferences pref = getSharedPreferences( "SaveState", MODE_PRIVATE );
		count = pref.getInt( "actcount", count );
		for( i = 0; i < count; i++ )
		{
			activitynumber[i] = pref.getInt( "actnum", activitynumber[i] );
			subject[i] = pref.getString( "name[" + i + "]", subject[i] );
			studentnumber[i] = pref.getInt( "studentnum", studentnumber[i] );

		}
	}

	protected void addbtn()
	{
		if( count != 0 )
		{

			for( i = 0; i < count; i++ )
			{
				btn[i] = new Button( context );
				//btn.setText("버튼" + String.valueOf(count)); 원래 번호증가 버튼
				btn[i].setText( "" + subject[i] );
				btn[i].setId( i + 1 );
				btn[i].setOnClickListener( new View.OnClickListener()
				{
					@Override
					public void onClick( View v )
					{
						Toast.makeText( MainActivity.this, ( v.getId() ) + "액티비티호출",
								Toast.LENGTH_SHORT ).show();
					}
				} );
				//버튼 길게 눌렀을 때
				btn[i].setOnLongClickListener( new View.OnLongClickListener()
				{
					@Override
					public boolean onLongClick( View view )
					{
						//다이얼로그생성
						final Dialog delete = new Dialog( context );
						delete.setTitle( "삭제할까요?" );
						delete.setContentView( R.layout.delete );
						Button delBtn = ( Button ) delete.findViewById( R.id.delete );
						Button cancelBtn = ( Button ) delete.findViewById( R.id.cancel );
						// 다이얼로그 삭제버튼 누를 때
						delBtn.setTag( view );
						delBtn.setOnClickListener( new View.OnClickListener()
						{
							@Override
							public void onClick( View view )
							{
								View currBtn = ( View ) view.getTag();
								int index = Arrays.asList( btn ).indexOf( currBtn );
								SharedPreferences pref = getSharedPreferences( "SaveState", MODE_PRIVATE );
								pref.edit()
										.remove( "actnum[" + index + "]" )
										.remove( "name[" + index + "]" )
										.remove( "studentnum[" + index + "]" )
										.commit();
								count--;
//								restoreFromSavedState();
								layout.removeView( ( View ) view.getTag() );

								saveCurrentState();

								delete.dismiss();
							}
						} );
						//다이얼로그 취소버튼 누를 때
						cancelBtn.setOnClickListener( new View.OnClickListener()
						{
							@Override
							public void onClick( View view )
							{
								delete.dismiss();
							}
						} );
						delete.show();
						return true;
					}
				} );
				layout.addView( btn[i] );
			}
		}

	}

	// Back의 상태값을 저장하기 위한 변수
	boolean m_close_flag = false;


	// 일정 시간 후 상태값을 초기화하기 위한 핸들러
	Handler m_close_handler = new Handler()
	{
		public void handleMessage( Message msg )
		{
			m_close_flag = false;
		}
	};


	// ... 관련 없는 코드 생략 ...


	// Back 키가 터치되면 호출되는 메소드
	public void onBackPressed()
	{

		// m_close_flag 가 false 이면 첫번째로 키가 눌린 것이다.
		if( m_close_flag == false )
		{ // Back 키가 첫번째로 눌린 경우


			// 안내 메세지를 토스트로 출력한다.
			Toast.makeText( this, "취소키를 빨리 한번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG ).show();


			// 상태값 변경
			m_close_flag = true;


			// 핸들러를 이용하여 3초 후에 0번 메세지를 전송하도록 설정한다.
			m_close_handler.sendEmptyMessageDelayed( 0, 3000 );


		}
		else
		{ // Back 키가 3초 내에 연달아서 두번 눌린 경우


			// 액티비티를 종료하는 상위 클래스의 onBackPressed 메소드를 호출한다.
			super.onBackPressed();
		}
	}


	protected void onStop()
	{
		super.onStop();

		// 핸드러에 등록된 0번 메세지를 모두 지운다.

		m_close_handler.removeMessages( 0 );
	}
}

