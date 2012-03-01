
package ru.surfstudio;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;

public class HelloActivityActivity extends Activity {

    private static final String TAG = HelloActivityActivity.class.getSimpleName();

    private final int REQUEST_CODE_SECOND_ACTIVITY = 0;

    private TextView textView;

    private ProgressBar progressBar;
    
    private MyAsyncQDWDTask task;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        textView = (TextView)findViewById(R.id.text_field);
        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        Button button = (Button)findViewById(R.id.fisrt_button);
        button.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                task = new MyAsyncQDWDTask();
                task.execute();
                // Intent intent = new Intent(HelloActivityActivity.this,
                // SecondActivity.class);
                // intent.putExtra(SecondActivity.EXTRA_VALUE, 7);
                // startActivityForResult(intent, REQUEST_CODE_SECOND_ACTIVITY);
            }
        });
    }
    
    @Override
    protected void onDestroy() {
        super.onDestroy();
        task.detach();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_CODE_SECOND_ACTIVITY:
                if (resultCode == Activity.RESULT_CANCELED) {
                    Toast.makeText(this, data.getExtras().getInt(SecondActivity.EXTRA_VALUE2), Toast.LENGTH_LONG);
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    protected void setContentVisibility(boolean visible) {
        textView.setVisibility(visible ? View.VISIBLE : View.GONE);
        progressBar.setVisibility(!visible ? View.VISIBLE : View.GONE);
    }

    public void showProgress(int progress) {
        progressBar.setProgress(progress);
    }

    public void onResult(JSONObject jo) {
        textView.setText(String.valueOf(jo));
    }

    private class MyAsyncQDWDTask extends AsyncTask<Void, Integer, Message> {

        private HelloActivityActivity activity;

        @Override
        protected void onProgressUpdate(Integer... values) {
            if (activity != null) {
                activity.showProgress(values[0]);
            }
        }

        @Override
        protected void onPreExecute() {
            setContentVisibility(false);
        }

        @Override
        protected Message doInBackground(Void... params) {
            Message message = new Message();
            AQuery aq = new AQuery(HelloActivityActivity.this);
            String url = "http://www.google.com/uds/GnewsSearch?q=Obama&v=1.0";
            AjaxCallback<JSONObject> cb = new AjaxCallback<JSONObject>();
            cb.url(url).type(JSONObject.class);
            aq.sync(cb);
            JSONObject jo = cb.getResult();
            message.arg1 = ErrorCode.ERROR_CODE_OK;
            message.obj = jo;
            publishProgress(3);
            return message;
        }

        @Override
        protected void onPostExecute(Message result) {
            switch (result.arg1) {
                case ErrorCode.ERROR_CODE_OK:
                    setContentVisibility(true);
                    if (activity != null) {
                        activity.onResult((JSONObject)result.obj);
                    }
                    break;
                case ErrorCode.ERROR_TIMEOUT:
                    Toast.makeText(HelloActivityActivity.this, "TimeOut", Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
        }

        public void detach() {
            activity = null;
        }
    }
}
