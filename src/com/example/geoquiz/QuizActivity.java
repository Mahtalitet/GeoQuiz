package com.example.geoquiz;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class QuizActivity extends ActionBarActivity {

	private Button mTrueButton;
	private Button mFalseButton;
	private Button mNextButton;
	private Button mBackButton;
	private TextView mQuestionTextView;
	private TrueFalse[] mTrueFalseList = {
		new TrueFalse(R.string.question_africa, false),
		new TrueFalse(R.string.question_americas, true),
		new TrueFalse(R.string.question_asia, true),
		new TrueFalse(R.string.question_mideast, false),
		new TrueFalse(R.string.question_oceans, true)			
	};
	private int mCurrentIndex = 0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_quiz);
		
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
		
		mNextButton = (Button) findViewById(R.id.next_button);
		mNextButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				mCurrentIndex = (mCurrentIndex + 1) % mTrueFalseList.length;
				nextQuestion(mCurrentIndex);
			}
		});
		
		mBackButton = (Button) findViewById(R.id.back_button);
		mBackButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				if (mCurrentIndex != 0) {
					mCurrentIndex--;
					nextQuestion(mCurrentIndex);

					
				} else {
					mCurrentIndex = Math.abs(mCurrentIndex - (mTrueFalseList.length-1));
					nextQuestion(mCurrentIndex);
				}
			}
		});
		
		nextQuestion(mCurrentIndex);

	}

	
	private void nextQuestion(int currentIndex) {
		int question = mTrueFalseList[currentIndex].getQuestion();
		mQuestionTextView.setText(question);
	}
	
	private void checkAnswer(boolean userPressedTrue) {
		boolean answer = mTrueFalseList[mCurrentIndex].isAnswer();
		int messageId = 0;
		
		if (answer == userPressedTrue) {
			messageId = R.string.correct_toast;
			
		} else {
			messageId = R.string.incorrect_toast;
		}
		Toast.makeText(this, messageId, Toast.LENGTH_SHORT).show();
	}
	
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
}
