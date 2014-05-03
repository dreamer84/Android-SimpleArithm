package com.example.simplearithm;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener {
	public static final String TAG = "com.example.simplearithm.MainActivity";
	public static final int MAX_LENGTH_OF_ANSWER = 5;

	private Integer expressionResult;

	private List<Integer> attemptResultsList;

	private TextView textAnswerNumber;
	private TextView textFirstNumber;
	private TextView textSecondNumber;
	private TextView textOperation;

	private TextView textProgress;

	private Button buttonPicker0;
	private Button buttonPicker1;
	private Button buttonPicker2;
	private Button buttonPicker3;
	private Button buttonPicker4;
	private Button buttonPicker5;
	private Button buttonPicker6;
	private Button buttonPicker7;
	private Button buttonPicker8;
	private Button buttonPicker9;
	private Button buttonReset;
	private Button buttonClearAnswerLeft;
	private Button buttonClearAnswerRight;

	DisplayMetrics displayMetrics;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		displayMetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

		expressionResult = null;

		attemptResultsList = new ArrayList<Integer>();
		loadProgress();

		textFirstNumber = (TextView) findViewById(R.id.text_first_number);
		textSecondNumber = (TextView) findViewById(R.id.text_second_number);
		textOperation = (TextView) findViewById(R.id.text_operation);
		textAnswerNumber = (TextView) findViewById(R.id.text_answer_number);

		textProgress = (TextView) findViewById(R.id.text_progress);

		buttonPicker0 = (Button) findViewById(R.id.button_picker_0);
		buttonPicker0.setOnClickListener(this);
		buttonPicker1 = (Button) findViewById(R.id.button_picker_1);
		buttonPicker1.setOnClickListener(this);
		buttonPicker2 = (Button) findViewById(R.id.button_picker_2);
		buttonPicker2.setOnClickListener(this);
		buttonPicker3 = (Button) findViewById(R.id.button_picker_3);
		buttonPicker3.setOnClickListener(this);
		buttonPicker4 = (Button) findViewById(R.id.button_picker_4);
		buttonPicker4.setOnClickListener(this);
		buttonPicker5 = (Button) findViewById(R.id.button_picker_5);
		buttonPicker5.setOnClickListener(this);
		buttonPicker6 = (Button) findViewById(R.id.button_picker_6);
		buttonPicker6.setOnClickListener(this);
		buttonPicker7 = (Button) findViewById(R.id.button_picker_7);
		buttonPicker7.setOnClickListener(this);
		buttonPicker8 = (Button) findViewById(R.id.button_picker_8);
		buttonPicker8.setOnClickListener(this);
		buttonPicker9 = (Button) findViewById(R.id.button_picker_9);
		buttonPicker9.setOnClickListener(this);

		buttonReset = (Button) findViewById(R.id.button_update);
		buttonReset.setOnClickListener(this);

		buttonClearAnswerLeft = (Button) findViewById(R.id.button_clear_answer_left);
		buttonClearAnswerLeft.setOnClickListener(this);
		buttonClearAnswerRight = (Button) findViewById(R.id.button_clear_answer_right);
		buttonClearAnswerRight.setOnClickListener(this);

		generateExpression();

		updateProgress();
	}

	@Override
	protected void onDestroy() {
		saveProgress();
		super.onDestroy();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	        case R.id.action_settings:
	            Intent intent = new Intent(this, SettingsActivity.class);
	            startActivity(intent);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		if (outState == null) {
			return;
		}

		outState.putString("textFirstNumber", textFirstNumber.getText()
				.toString());
		outState.putString("textSecondNumber", textSecondNumber.getText()
				.toString());
		outState.putString("textOperation", textOperation.getText().toString());
		outState.putString("expressionResult", expressionResult.toString());

		// Saves the attempts
		outState.putString("attemptResultsList",
				attemptsToString(attemptResultsList));

		Log.d(TAG, "onSaveInstanceState(): " + attemptResultsList.toString());
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);

		if (savedInstanceState == null) {
			return;
		}

		textFirstNumber
				.setText(savedInstanceState.getString("textFirstNumber"));
		textSecondNumber.setText(savedInstanceState
				.getString("textSecondNumber"));
		textOperation.setText(savedInstanceState.getString("textOperation"));
		expressionResult = Integer.valueOf(savedInstanceState
				.getString("expressionResult"));

		// Restores the attempts
		String attString = savedInstanceState.getString("attemptResultsList");
		attemptResultsList = attemptsToList(attString);

		updateProgress();

		Log.d(TAG, "onSaveInstanceState(): " + attemptResultsList.toString());
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.button_picker_0:
		case R.id.button_picker_1:
		case R.id.button_picker_2:
		case R.id.button_picker_3:
		case R.id.button_picker_4:
		case R.id.button_picker_5:
		case R.id.button_picker_6:
		case R.id.button_picker_7:
		case R.id.button_picker_8:
		case R.id.button_picker_9:
			if (textAnswerNumber.getText().length() >= MAX_LENGTH_OF_ANSWER) {
				break;
			}
			textAnswerNumber.setText(textAnswerNumber.getText()
					+ v.getTag().toString());
			testAnswer();
			break;
		case R.id.button_clear_answer_left:
		case R.id.button_clear_answer_right:
			int textAnswerNumberLength = textAnswerNumber.getText().length();
			if (textAnswerNumberLength <= 0) {
				break;
			}
			textAnswerNumber.setText(textAnswerNumber.getText().subSequence(0,
					textAnswerNumberLength - 1));
			textAnswerNumberLength--;
			if (textAnswerNumberLength > 0)
				testAnswer();
			break;
		case R.id.button_update:
			generateExpression();
			setButtonsEnabled(true);
			break;
		}
	}

	/**
	 * Tests the user answer
	 */
	public void testAnswer() {
		Log.d(TAG, "expressionResult: " + expressionResult);

		// Sets text color to default
		textAnswerNumber.setTextColor(Color.rgb(185, 0, 25));

		// Gets the user answer
		String userAnswerAsString = (String) textAnswerNumber.getText();
		int userAnswer = Integer.parseInt(userAnswerAsString);

		// Checks the answer
		// 1. The answers are equals => It's a RIGHT answer!
		if ((expressionResult != null) && (userAnswer == expressionResult)) {
			textAnswerNumber.setTextColor(Color.rgb(5, 135, 0));
			alert("You're right!");
			setButtonsEnabled(false);
			attemptResultsList.add(1);
			// 2. They're not equal, but they have same number of digits => It's
			// a WRONG answer!
		} else if (userAnswerAsString.length() == expressionResult.toString()
				.length()) {
			alert("The right answer is " + expressionResult);
			setButtonsEnabled(false);
			attemptResultsList.add(0);
		}

		updateProgress();

		Log.d(TAG, "attempts: " + attemptResultsList);
	}

	public void generateExpression() {
		int operandA = generateRandomNumber(99);
		int operandB = generateRandomNumber(99);
		int operationNumber = (int) (Math.random() * 3); // without "division"
		char operationSimbol = '?';
		int result = 0;

		switch (operationNumber) {
		case 0:
			operationSimbol = '+';
			result = operandA + operandB;
			break;
		case 1:
			operationSimbol = '−';
			if (operandB > operandA) {
				int savedOperand = operandA;
				operandA = operandB;
				operandB = savedOperand;
			}
			result = operandA - operandB;
			break;
		case 2:
			operationSimbol = '*';
			operandB = generateRandomNumber(9);
			result = operandA * operandB;
			break;
		/*
		 * case 3: operationSimbol = '/'; result = operandA / operandB; break;
		 */
		}

		textFirstNumber.setText(operandA + "");
		textSecondNumber.setText(operandB + "");
		textOperation.setText(operationSimbol + "");
		textAnswerNumber.setText("");
		expressionResult = result;
	}

	public int generateRandomNumber(int maxValue) {
		return (int) ((Math.random() * maxValue) + 1);
	}

	/**
	 * Fills the progress bar with the last results.
	 */
	public void updateProgress() {
		textProgress.setText(attemptsToString(attemptResultsList));
	}

	/**
	 * Sets the answer-input buttons enabled or disabled
	 * 
	 * @param enabled
	 *            true or false
	 */
	public void setButtonsEnabled(boolean enabled) {
		buttonPicker0.setEnabled(enabled);
		buttonPicker1.setEnabled(enabled);
		buttonPicker2.setEnabled(enabled);
		buttonPicker3.setEnabled(enabled);
		buttonPicker4.setEnabled(enabled);
		buttonPicker5.setEnabled(enabled);
		buttonPicker6.setEnabled(enabled);
		buttonPicker7.setEnabled(enabled);
		buttonPicker8.setEnabled(enabled);
		buttonPicker9.setEnabled(enabled);
		buttonClearAnswerLeft.setEnabled(enabled);
		buttonClearAnswerRight.setEnabled(enabled);
	}

	public void saveProgress() {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedPref.edit();
		editor.putString(getString(R.string.saved_attempts),
				attemptsToString(attemptResultsList));
		editor.commit();

		Log.d(TAG, "saveProgress(): " + attemptResultsList.toString());
	}

	public void loadProgress() {
		SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
		String attStr = sharedPref.getString(
				getString(R.string.saved_attempts), "");

		attemptResultsList = attemptsToList(attStr);

		Log.d(TAG, "loadProgress(): " + attemptResultsList.toString());
	}

	/**
	 * Displays a text message using a Toast object at top-center position with
	 * relative y-offset.
	 * 
	 * @param message
	 */
	public void alert(CharSequence message) {
		int displayHeightPixels = displayMetrics.heightPixels;

		Toast toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
		toast.setGravity(Gravity.TOP | Gravity.CENTER, 0,
				(int) (displayHeightPixels * 0.12));
		toast.show();
	}

	public String attemptsToString(List<Integer> attempts) {
		if (attempts == null) {
			return "";
		}

		StringBuilder attString = new StringBuilder();
		for (Integer a : attempts) {
			attString.append(a);
		}
		return attString.toString();
	}

	public List<Integer> attemptsToList(String attempts) {
		List<Integer> attList = new ArrayList<Integer>();
		for (int i = 0; i < attempts.length(); i++) {
			attList.add((attempts.charAt(i) == '1') ? 1 : 0);
		}

		return attList;
	}
}
