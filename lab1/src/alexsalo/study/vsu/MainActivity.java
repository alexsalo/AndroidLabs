package alexsalo.study.vsu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	/** Called when the activity is first created. */
	private Button submit_btn;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		submit_btn = (Button)findViewById(R.id.ok_button);
		
		submit_btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent i = new Intent(MainActivity.this, HelloActivity.class);
				EditText et = (EditText)findViewById(R.id.name_et);
				i.putExtra("username", et.getText().toString());
				startActivity(i);
			}
		});
	}
}