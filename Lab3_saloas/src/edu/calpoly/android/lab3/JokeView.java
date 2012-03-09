package edu.calpoly.android.lab3;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class JokeView extends RelativeLayout implements Checkable {

	private Button m_vwExpandButton;
	private RadioButton m_vwLikeButton;
	private RadioButton m_vwDislikeButton;
	private RadioGroup m_vwLikeGroup;
	private TextView m_vwJokeText;
	private Joke m_joke;
	private boolean isChekedFl;

	public static final String EXPAND = " + ";
	public static final String COLLAPSE = " - ";

	/**
	 * Basic Constructor that takes only takes in an application Context.
	 * 
	 * @param context
	 *            The application Context in which this view is being added. 
	 *            
	 * @param joke
	 * 			  The Joke this view is responsible for displaying.
	 */
	public JokeView(Context context, Joke joke) {
		super(context);
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.joke_view, this, true);
		
		m_vwLikeButton = (RadioButton)findViewById(R.id.likeButton);
		m_vwDislikeButton = (RadioButton)findViewById(R.id.dislikeButton);
		m_vwLikeGroup = (RadioGroup)findViewById(R.id.ratingRadioGroup);
		m_vwJokeText = (TextView)findViewById(R.id.jokeTextView);
		m_vwExpandButton = (Button)findViewById(R.id.expandButton);
		
		setJoke(joke);
		collapseJokeView();
		this.m_vwExpandButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (m_vwExpandButton.getText().equals(EXPAND))
					expandJokeView();
				else
					collapseJokeView();
			}
		});
		this.m_vwLikeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				 if (m_vwLikeGroup == group)
                     m_joke.setRating(checkedId == R.id.likeButton ? Joke.LIKE
                                     : Joke.DISLIKE);
			}
		});
	}

	/**
	 * Mutator method for changing the Joke object this View displays. This View
	 * will be updated to display the correct contents of the new Joke.
	 * 
	 * @param joke
	 *            The Joke object which this View will display.
	 */
	public void setJoke(Joke joke) {
		m_joke = joke;
		m_vwJokeText.setText(m_joke.getJoke());
		
		switch (m_joke.getRating()){
		case (Joke.UNRATED):
			m_vwLikeGroup.clearCheck();
			break;
		case (Joke.DISLIKE):
            m_vwLikeGroup.check(R.id.dislikeButton);
			break;
		case (Joke.LIKE):
            m_vwLikeGroup.check(R.id.likeButton);
			break;
		}
	}

	/**
	 * This method encapsulates the logic necessary to update this view so that
	 * it displays itself in its "Expanded" form: 
	 * 	- Shows the complete text of the joke. 
	 *  - Brings the RadioGroup of rating Buttons back into view.
	 */
	private void expandJokeView() {
		m_vwJokeText.setEllipsize(null);
		m_vwExpandButton.setText(COLLAPSE);
		m_vwLikeGroup.setVisibility(VISIBLE);
		requestLayout();
	}

	/**
	 * This method encapsulates the logic necessary to update this view so that
	 * it displays itself in its "Collapsed" form: 
	 * 	- Shows only the first line of text of the joke. 
	 *  - If the joke is longer than one line, it appends an ellipsis to the end. 
	 *  - Removes the RadioGroup of rating Buttons from view.
	 */
	private void collapseJokeView() {
		m_vwJokeText.setEllipsize(TruncateAt.END);
		m_vwExpandButton.setText(EXPAND);
		m_vwLikeGroup.setVisibility(GONE);
		requestLayout();
	}

	public boolean isChecked() {
		return isChekedFl;
	}

	public void setChecked(boolean checked) {
		if (isChekedFl != checked)
			expandJokeView();
		else
			collapseJokeView();
	}

	public void toggle() {
		setChecked(!isChekedFl);
	}

}
