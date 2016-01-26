package com.example.geoquiz;

public class TrueFalse {
	private int mQuestion;
	private boolean mAnswer;
	
	public TrueFalse(int quest, boolean answ) {
		mQuestion = quest;
		mAnswer = answ;
	}

	public int getQuestion() {
		return mQuestion;
	}

	public boolean isAnswer() {
		return mAnswer;
	}

	public void setQuestion(int question) {
		mQuestion = question;
	}

	public void setAnswer(boolean answer) {
		mAnswer = answer;
	}

}
