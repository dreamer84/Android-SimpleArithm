package com.example.simplearithm;

import android.os.Bundle;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener {
	private static final int MAX_LENGTH_OF_ANSWER = 5;
	
	//private static final String CALCULATOR_PACKAGE_NAME = "com.android.calculator2";
	//private static final String CALCULATOR_CLASS_NAME = "com.android.calculator2.Calculator";

	private int expressionResult = 0;
	
	private TextView textAnswerNumber;
	private TextView textFirstNumber;
	private TextView textSecondNumber;
	private TextView textOperation;
	
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        textAnswerNumber = (TextView) findViewById(R.id.text_answer_number);
        textFirstNumber = (TextView) findViewById(R.id.text_first_number);
        textSecondNumber = (TextView) findViewById(R.id.text_second_number);
        textOperation = (TextView) findViewById(R.id.text_operation);
        
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
				if (textAnswerNumber.getText().length() >= MAX_LENGTH_OF_ANSWER) break;
				textAnswerNumber.setText(textAnswerNumber.getText() + v.getTag().toString());
				testAnswer();
				break;
			case R.id.button_clear_answer_left:
			case R.id.button_clear_answer_right:
				int textAnswerNumberLength = textAnswerNumber.getText().length();
				if (textAnswerNumberLength <= 0) break;
				textAnswerNumber.setText(textAnswerNumber.getText().subSequence(0, textAnswerNumberLength-1));
				textAnswerNumberLength--;
				if (textAnswerNumberLength > 0) testAnswer();
				break;
			case R.id.button_update:
				generateExpression();
				
				//launchCalculator();
				
				break;
		}
	}
	
	public void testAnswer() {
		textAnswerNumber.setTextColor(Color.rgb(185, 0, 25));
		int userAnswer = Integer.parseInt((String) textAnswerNumber.getText());
		if (userAnswer == expressionResult) {
			//Toast.makeText(this, "RIGHT", Toast.LENGTH_SHORT).show();
			textAnswerNumber.setTextColor(Color.rgb(5, 135, 0));
		}
	}
	
	public void generateExpression() {
		int operandA = generateRandomNumber(99);
		int operandB = generateRandomNumber(99);
		int operationNumber = (int) (Math.random() * 3); // without "division"
		char operationSimbol = '?';
		int result = 0;
		
		//Log.v("SimpleArithm", operation + "");
		switch(operationNumber) {
			case 0:
				operationSimbol = '+';
				result = operandA + operandB;
				break;
			case 1:
				operationSimbol = '-';
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
			/*case 3:
				operationSimbol = '/';
				result = operandA / operandB;
				break;*/
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
	
	/*public void launchCalculator() {
		Intent intent = new Intent();
		intent.setAction(Intent.ACTION_MAIN);
		intent.addCategory(Intent.CATEGORY_LAUNCHER);
		intent.setComponent(new ComponentName(CALCULATOR_PACKAGE_NAME, CALCULATOR_CLASS_NAME));
		try {
			this.startActivity(intent);
		} catch (ActivityNotFoundException noSuchActivity) {
			// handle exception where calculator intent filter is not registered
		}
	}*/
}
