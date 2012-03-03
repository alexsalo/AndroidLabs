package ru.vsu.cs.lab1_migal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class NameGetter extends Activity implements OnClickListener {

	private EditText mEdTxt;
	private Button mBtnOK;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    this.setContentView(R.layout.name_getter);
	    
	    mEdTxt = (EditText) findViewById(R.id.editTxt);
	    mBtnOK = (Button) findViewById(R.id.btnOK);
	    
	    mBtnOK.setOnClickListener(this);
	}
	
	public void onClick(View v) {
		Intent i = new Intent(this, Lab1_migalActivity.class);
		String name = this.getString(R.string.name);
		String value = mEdTxt.getText().toString();
		i.putExtra(name, value);
		
		startActivity(i);
	}
}
