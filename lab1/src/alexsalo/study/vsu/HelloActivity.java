package alexsalo.study.vsu;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class HelloActivity extends Activity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hello);
		
		TextView greetTv = (TextView)findViewById(R.id.greetint_tv);
		
		String un = this.getIntent().getExtras().getString("username");
		greetTv.setText("Hello " + un + "!");
	}
}
