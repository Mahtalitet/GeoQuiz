package com.example.geoquiz;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class QuizActivity extends ActionBarActivity {
	
	public static final String TAG = "QuizActivity";
	public static final String KEY_ANSWER = "questionNumber";
	public static final String KEY_CHEATING_STATUS = "cheatingStatus";


	private Button mTrueButton;
	private Button mFalseButton;
	private ImageButton mNextButton;
	private ImageButton mBackButton;
	private Button mCheatButton;
	private TextView mQuestionTextView;
	private TrueFalse[] mTrueFalseList = {
		new TrueFalse(R.string.question_russia, false),
		new TrueFalse(R.string.question_africa, false),
		new TrueFalse(R.string.question_americas, true),
		new TrueFalse(R.string.question_asia, true),
		new TrueFalse(R.string.question_mideast, false),
		new TrueFalse(R.string.question_oceans, true)
	};
	private int mCurrentIndex = 0;
	private boolean[] mCheatingStatus = new boolean[mTrueFalseList.length];

	
	//ѕереопределение методов жизненного цикла активити
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d(TAG, "onCreate(Bundle) called");
		setContentView(R.layout.activity_quiz);
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			ActionBar actionBar = getSupportActionBar();
			actionBar.setSubtitle("xaxaxa");
			
		}

		mTrueButton = (Button) findViewById(R.id.true_button);
		mTrueButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(true);
			}
		});
		
		mFalseButton = (Button) findViewById(R.id.false_button);
		mFalseButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				checkAnswer(false);
			}
		});
		
		mQuestionTextView = (TextView) findViewById(R.id.question_text_view);
		mQuestionTextView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mTrueFalseList.length;
				nextQuestion(mCurrentIndex);
			}
		});
		
		mBackButton = (ImageButton) findViewById(R.id.back_button);
		mBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (mCurrentIndex != 0) {
					mCurrentIndex--;
				} else {
					mCurrentIndex = Math.abs(mCurrentIndex - (mTrueFalseList.length-1));
				}
				nextQuestion(mCurrentIndex);
			}
		});
		
		mNextButton = (ImageButton) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mTrueFalseList.length;
				nextQuestion(mCurrentIndex);
			}
		});
		
		mCheatButton = (Button) findViewById(R.id.cheat_button);
		mCheatButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(QuizActivity.this, CheatActivity.class);
				boolean trueAnswerIs = mTrueFalseList[mCurrentIndex].isAnswer();
				i.putExtra(CheatActivity.EXTRA_TRUE_ANSWER_IS, trueAnswerIs);
				startActivityForResult(i, 0);
				
			}
		});
		
		if (savedInstanceState != null) {
			mCurrentIndex = savedInstanceState.getInt(KEY_ANSWER, 0);
			mCheatingStatus = savedInstanceState.getBooleanArray(KEY_CHEATING_STATUS);
		}
		nextQuestion(mCurrentIndex);

	}
	
	//ћетод дл€ сохранени€ значений переменных между реализаци€ми активити (например, при повороте устройства)
	
	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		Log.i(TAG, "called onSaveInstanceState(Bundle)");
		savedInstanceState.putInt(KEY_ANSWER, mCurrentIndex);
		savedInstanceState.putBooleanArray(KEY_CHEATING_STATUS, mCheatingStatus);
	}
	
	//ћетод дл€ получени€ значени€ через интент, возвращаемый дочерней активностью
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent inputIntent) {
		if (inputIntent == null) {
			return;
		}
		
		boolean isCheater = inputIntent.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN, false);
		if (isCheater) {
			mCheatingStatus[mCurrentIndex] = isCheater;
		}
	}
	
	
	@Override
	public void onStart () {
		super.onStart();
		Log.d(TAG, "onStart() called");
	}
	
	@Override
	public void onResume() {
		super.onResume();
		Log.d(TAG, "onResume() called");
	}
	
	@Override
	public void onPause() {
		super.onPause();
		Log.d(TAG, "onPause() called");
	}
	
	@Override
	public void onStop() {
		super.onStop();
		Log.d(TAG, "onStop() called");
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d(TAG, "onDestroy() called");
	}
	
	//ћетоды дл€ меню
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.quiz, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	// —амописные методы
	private void nextQuestion(int currentIndex) {
		int question = mTrueFalseList[currentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		boolean answer = mTrueFalseList[mCurrentIndex].isAnswer();
		int messageId = 0;
		
		if (mCheatingStatus[mCurrentIndex]) {
			messageId = R.string.judgment_toast;
		
		} else {
			if (answer == userPressedTrue) {
				messageId = R.string.correct_toast;
				
			} else {
				messageId = R.string.incorrect_toast;
			}
		}
		Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
	}
	
}
