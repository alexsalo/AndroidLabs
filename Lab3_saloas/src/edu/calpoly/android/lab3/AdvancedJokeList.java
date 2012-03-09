package edu.calpoly.android.lab3;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

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
	protected ListView m_vwJokeLayout;

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
		registerForContextMenu(m_vwJokeLayout);
	    initAddJokeListeners();
	    
	    m_nDarkColor = getResources().getColor(R.color.dark);
	    m_nLightColor = getResources().getColor(R.color.light);
	    m_arrJokeList = new ArrayList<Joke>();	
	    m_jokeAdapter = new JokeListAdapter(getApplicationContext(), m_arrJokeList);
	    
	    m_vwJokeLayout.setAdapter(m_jokeAdapter);
	    m_vwJokeLayout.setOnItemLongClickListener(m_jokeAdapter);
	    
	    String[] Jokes = this.getResources().getStringArray(R.array.jokeList);
	    for (String s : Jokes){
	    	Joke j = new Joke();
	    	j.setJoke(s);
	    	addJoke(j);
	    }
	}

	/**
	 * Method is used to encapsulate the code that initializes and sets the
	 * Layout for this Activity.
	 */
	protected void initLayout() {		
		setContentView(R.layout.advanced);
		m_vwJokeButton = (Button)findViewById(R.id.addJokeButton);
		m_vwJokeEditText = (EditText)findViewById(R.id.newJokeEditText);
		m_vwJokeLayout = (ListView)findViewById(R.id.jokeListViewGroup);
		
		m_vwJokeLayout.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
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
		m_vwJokeEditText.setOnKeyListener(new onJokeKeyListner());
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
		m_jokeAdapter.notifyDataSetChanged();
	}

	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo){
		super.onCreateContextMenu(menu, v, menuInfo);
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.joke_listview_context_menu, menu);
		//MenuItem item = menu.add(0, REMOVE_JOKE_MENUITEM, 0, R.string.remove_menuitem);
		
		/*item.setOnMenuItemClickListener(new OnMenuItemClickListener() {
			public boolean onMenuItemClick(MenuItem item) {
				m_arrJokeList.remove(m_jokeAdapter.getSelectedPosition());
				m_jokeAdapter.notifyDataSetChanged();
				return true;
			}
		});*/
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.remove_contextmenu:
	        	m_arrJokeList.remove(m_jokeAdapter.getSelectedPosition());
				m_jokeAdapter.notifyDataSetChanged();
	            return true;
	        case R.id.upload_contextmenu:
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		MenuInflater inflater = this.getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	protected void filterCopyArrayByRatingMatches(int rating){
		ArrayList<Joke> filteredJokes = new ArrayList<Joke>();
		for (Joke j : m_arrJokeList){
			if (j.getRating() == rating)
				filteredJokes.add(j);
		}
		m_jokeAdapter = new JokeListAdapter(getApplicationContext(), filteredJokes);
		m_vwJokeLayout.setAdapter(m_jokeAdapter);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()){
		case R.id.like_menuitem:
			filterCopyArrayByRatingMatches(Joke.LIKE);
			return true;
		case R.id.dislike_menuitem:
			filterCopyArrayByRatingMatches(Joke.DISLIKE);
			return true;
		case R.id.unrated_menuitem:
			filterCopyArrayByRatingMatches(Joke.UNRATED);
			return true;
		case R.id.show_all_menuitem:
			m_jokeAdapter = new JokeListAdapter(getApplicationContext(), m_arrJokeList);
			m_vwJokeLayout.setAdapter(m_jokeAdapter);
			return true;
		case R.id.download_menuitem:
			getJokesFromServer();
			return true;
		case R.id.upload_menuitem:
			uploadJokeToServer(new Joke("fdgd", "Cal Poly"));
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
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
		try {
			java.net.URL URL = new java.net.URL("http://simexusa.com/aac/getAllJokes.php");
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		String responseString = "";
		try {
			String mURL = "http://simexusa.com/aac/addOneJoke.php?";
			mURL += "joke=";
			mURL += java.net.URLEncoder.encode(joke.getJoke());
			mURL += "&author=";
			mURL += java.net.URLEncoder.encode(joke.getAuthor());
			InputStream response = new java.net.URL(mURL).openStream();
			responseString = response.toString();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String result;
		if (responseString.equals("1 record added"))
			result = "Succes";
		else 
			result = "Failure";
		Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();

	}
	
	/**
	 * for any buttons who can add joke
	 * @author Alex7
	 *
	 */
	protected class addJokeClickListner implements OnClickListener{
		public void onClick(View v) {
			if (m_vwJokeButton == (Button) v)
				addJoke(new Joke(m_vwJokeEditText.getText().toString(),"S"));	
		}
	}
	
	protected class onJokeKeyListner implements OnKeyListener{
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			if (m_vwJokeEditText == (EditText) v
				 && (keyCode == KeyEvent.KEYCODE_DPAD_CENTER || keyCode == KeyEvent.KEYCODE_ENTER) 
				 && event.getAction() == KeyEvent.ACTION_DOWN ){
				addJoke(new Joke(m_vwJokeEditText.getText().toString(),"S"));	
				return true;
			}
			return false;
		}
	}
}