package edu.calpoly.android.lab2;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

public class SimpleJokeList extends Activity {

	/** Contains the list Jokes the Activity will present to the user **/
	protected ArrayList<Joke> m_arrJokeList;

	/**
	 * LinearLayout used for maintaining a list of Views that each display Jokes
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
	    	addJoke(s);
	    }
	    Log.d("salo", "wtf??gfg");
	    
	}
	
	/**
	 * Method used to encapsulate the code that initializes and sets the Layout
	 * for this Activity. 
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
	 * Method used to encapsulate the code that initializes and sets the Event
	 * Listeners which will respond to requests to "Add" a new Joke to the 
	 * list. 
	 */
	protected void initAddJokeListeners() {
		m_vwJokeButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				String s  = m_vwJokeEditText.getText().toString(); 
				if (s.length() != 0){
					Joke j = new Joke();
					j.setJoke(s);
					addJoke(s);
					m_vwJokeEditText.setText("");
					hideSoftKeyboard();
				}
			}
		});
	}

	/**
	 * Method used for encapsulating the logic necessary to properly initialize
	 * a new joke, add it to m_arrJokeList, and display it on screen.
	 * 
	 * @param strJoke
	 *            A string containing the text of the Joke to add.
	 */
	protected void addJoke(String strJoke) {
		Joke j = new Joke(strJoke);
		m_arrJokeList.add(j);
		
		TextView tv = new TextView(this);
		tv.setText(strJoke);
		tv.setTextSize(TypedValue.COMPLEX_UNIT_PX, 16);
		int color;
		color = (m_arrJokeList.size() % 2 == 0) ? m_nLightColor : m_nDarkColor;
		tv.setBackgroundColor(color);
		m_vwJokeLayout.addView(tv);
	}
}