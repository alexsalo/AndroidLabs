package ru.vsu.cs.lab1_migal;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Lab1_migalActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Bundle b = this.getIntent().getExtras();
        
        if (b != null) {
        	String name = b.getString(this.getString(R.string.name));
        	
        	if (name != null) {
        		TextView txtView = (TextView)findViewById(R.id.txtViewHello);
        		txtView.setText("Hello ".concat(name));
        	}
        }
    }
}