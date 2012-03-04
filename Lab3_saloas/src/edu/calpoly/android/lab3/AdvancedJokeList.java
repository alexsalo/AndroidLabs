package edu.calpoly.android.lab3;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class AdvancedJokeList extends Activity {

	/**
	 * Contains the name of the Author for the jokes.
	 */
	protected String m_strAuthorName;

	/**
	 * Contains the list of Jokes the Activity will present to the user.
	 **/
	protected ArrayList<Joke> m_arrJokeList;

	/**
	 * Adapter used to bind an AdapterView to List of Jokes.
	 */
	protected JokeListAdapter m_jokeAdapter;

	/**
	 * ViewGroup used for maintaining a list of Views that each display Jokes.
	 **/
	protected LinearLayout m_vwJokeLayout;

	/**
	 * EditText used for entering text for a new Joke to be added to
	 * m_arrJokeList.
	 **/
	protected EditText m_vwJokeEditText;

	/**
	 * Button used for creating and adding a new Joke to m_arrJokeList using the
	 * text entered in m_vwJokeEditText.
	 **/
	protected Button m_vwJokeButton;

	/**
	 * Background Color values used for alternating between light and dark rows
	 * of Jokes.
	 */
	protected int m_nDarkColor;
	protected int m_nLightColor;
		
	/**
	 * Context-Menu MenuItem ID's
	 * IMPORTANT: You must use these when creating your MenuItems or the tests
	 * used to grade your submission will fail.
	 */
	protected static final int REMOVE_JOKE_MENUITEM = Menu.FIRST;
	protected static final int UPLOAD_JOKE_MENUITEM = Menu.FIRST + 1;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initLayout();
	    initAddJokeListeners();
	    
	    m_nDarkColor = getResources().getColor(R.color.dark);
	    m_nLightColor = getResources().getColor(R.color.light);
	    m_arrJokeList = new ArrayList<Joke>();	
	    
	    String[] Jokes = this.getResources().getStringArray(R.array.jokeList);
	    for (String s : Jokes){
	    	Joke j = new Joke();
	    	j.setJoke(s);
	    	addJoke(j);
	    }
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		super.onKeyDown(keyCode, event);
		switch (keyCode){
			case KeyEvent.KEYCODE_ENTER: 
				addJokeMethod();
			case KeyEvent.KEYCODE_DPAD_CENTER:
				addJokeMethod();
			case KeyEvent.KEYCODE_BACK:
				
		}
		return false;
	}

	/**
	 * Method is used to encapsulate the code that initializes and sets the
	 * Layout for this Activity.
	 */
	protected void initLayout() {
		LinearLayout rootGroupLayout = new LinearLayout(this);
		rootGroupLayout.setOrientation(LinearLayout.VERTICAL);		
		
		LinearLayout manageToolsLayout = new LinearLayout(this);
		manageToolsLayout.setOrientation(LinearLayout.HORIZONTAL);		
		m_vwJokeLayout = new LinearLayout(this);
		m_vwJokeLayout.setOrientation(LinearLayout.VERTICAL);
		
		ScrollView scrViewOfJokes = new ScrollView(this);
		scrViewOfJokes.addView(m_vwJokeLayout);
		
		m_vwJokeButton = new Button(this);
		m_vwJokeButton.setText("Add Joke");
		m_vwJokeEditText = new EditText(this);
		m_vwJokeEditText.setHint("Enter your joke here");
		LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT);
		m_vwJokeEditText.setLayoutParams(params);
		manageToolsLayout.addView(m_vwJokeButton);
		manageToolsLayout.addView(m_vwJokeEditText);
		
		rootGroupLayout.addView(manageToolsLayout);
		rootGroupLayout.addView(scrViewOfJokes);
		setContentView(rootGroupLayout);
	}

	/**
	 * Method used to hide keyboard easily
	 */
	protected void hideSoftKeyboard() {
		InputMethodManager imm = (InputMethodManager)
				getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(m_vwJokeEditText.getWindowToken(), 0);
	}
	
	/**
	 * Method is used to encapsulate the code that initializes and sets the
	 * Event Listeners which will respond to requests to "Add" a new Joke to the
	 * list.
	 */
	protected void initAddJokeListeners() {
		m_vwJokeButton.setOnClickListener(new addJokeClickListner()); 
	}

	/**
	 * Method used for encapsulating the logic necessary to properly add a new
	 * Joke to m_arrJokeList, and display it on screen.
	 * 
	 * @param joke
	 *            The Joke to add to list of Jokes.
	 */
	protected void addJoke(Joke joke) {
		m_arrJokeList.add(joke);
		
		TextView tv = new TextView(this);
		tv.setText(joke.getJoke());
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 22);
		int color;
		color = (m_arrJokeList.size() % 2 == 0) ? m_nLightColor : m_nDarkColor;
		tv.setBackgroundColor(color);
		m_vwJokeLayout.addView(tv);
	}

	/**
	 * Method used to retrieve Jokes from online server. The getJoke script
	 * takes a single optional parameter, which should be encode in "UTF-8".
	 * This parameter allows tells script to only retrieve Jokes whose author
	 * name matches the value in the parameter.
	 * 
	 * param-1) "author": The author of the joke.
	 * 
	 * URL: http://simexusa.com/aac/getJokes.php?
	 * 
	 */
	protected void getJokesFromServer() {
		// TODO
	}

	/**
	 * This method uploads a single Joke to the server. This method should test
	 * the response from the server and display success or failure to the user
	 * via a Toast Notification
	 * 
	 * The addJoke script on the server requires two parameters, both of which
	 * should be encode in "UTF-8":
	 * 
	 * param-1) "joke": The text of the joke.
	 * 
	 * param-2) "author": The author of the joke.
	 * 
	 * URL: http://simexusa.com/aac/addJoke.php?
	 * 
	 * @param joke
	 *            The Joke to be uploaded to the server.
	 * 
	 */
	protected void uploadJokeToServer(Joke joke) {
		// TODO
	}

	/**
	 * used in addJokeListner 
	 * onKeyDownEvents
	 */
	protected void addJokeMethod(){
		String s  = m_vwJokeEditText.getText().toString(); 
		if (s.length() != 0){
			Joke j = new Joke();
			j.setJoke(s);
			addJoke(j);
			m_vwJokeEditText.setText("");
			hideSoftKeyboard();
		}
	}
	
	/**
	 * for any buttons who can add joke
	 * @author Alex7
	 *
	 */
	protected class addJokeClickListner implements OnClickListener{
		public void onClick(View v) {
			addJokeMethod();
		}
	}
}