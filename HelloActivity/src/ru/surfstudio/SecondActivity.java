
package ru.surfstudio;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class SecondActivity extends Activity {

    public static final String EXTRA_VALUE = "EXTRA_VALUE";
    public static final String EXTRA_VALUE2 = "EXTRA_VALUE2";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        TextView textView = (TextView)findViewById(R.id.second_text);

        int value = getIntent().getExtras().getInt(EXTRA_VALUE);

        textView.setText(String.valueOf(value));
    }
    
    @Override
    protected void onDestroy() {
        Intent intent = new Intent();
        intent.putExtra(EXTRA_VALUE2, 0);
//        setResult(EXTRA_VALUE2, intent);
        super.onDestroy();
    }
}
