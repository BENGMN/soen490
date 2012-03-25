package ericsson.thinClient.view;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

import ericsson.thinClient.R;
import ericsson.thinClient.domain.Control;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ThinClientActivity extends Activity  implements OnClickListener {
	
    ImageButton actionButton;
    ImageButton prevButton;
    ImageButton nextButton;
    ImageButton recordButton;
    ImageButton upvoteButton;
    ImageButton downvoteButton;
    ProgressBar progressBar;
    ArrayList<View> stateButtons;
    Stack< ArrayList<Pair<View,Boolean>>> buttonState;
    TextView statusView;
    
    private static ThinClientActivity singleton = null;
    
    public static ThinClientActivity getInstance()
    {
    	return singleton;
    }
    
    private class Pair<A,B> {
    	public A first;
    	public B second;
    	public Pair(A first, B second) { this.first = first; this.second = second; }
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singleton = this;
        buttonState = new Stack<ArrayList<Pair<View, Boolean>>>();
        stateButtons = new ArrayList<View>();
        
        setContentView(R.layout.main);
        actionButton = (ImageButton) findViewById(R.id.action);
        prevButton = (ImageButton)findViewById(R.id.prev);
        nextButton = (ImageButton)findViewById(R.id.next);
        recordButton = (ImageButton)findViewById(R.id.record);
        upvoteButton = (ImageButton)findViewById(R.id.upvote);
        downvoteButton = (ImageButton)findViewById(R.id.downvote);
        statusView = (TextView)findViewById(R.id.status);
        
        // All of these will be included in the state stack,
        // and global enable/disable.
        stateButtons.add(actionButton);
        stateButtons.add(prevButton);
        stateButtons.add(nextButton);
        stateButtons.add(recordButton);
        stateButtons.add(upvoteButton);
        stateButtons.add(downvoteButton);
        
        recordButton.setOnClickListener(this);
        actionButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        upvoteButton.setOnClickListener(this);
        downvoteButton.setOnClickListener(this);
        
        statusView.setText("No Action Underway");
        refreshButtons();
    }
    
    private void pushButtonState() {
    	ArrayList<Pair<View, Boolean>> array = new ArrayList<Pair<View, Boolean>>();
    	for (View view : stateButtons)
    		array.add(new Pair<View, Boolean>(view, view.isEnabled()));
    	buttonState.push(array);
    }
    
    private void popButtonState() {
    	ArrayList<Pair<View, Boolean>> array = buttonState.pop();
    	for (Pair<View, Boolean> pair : array)
    		pair.first.setEnabled(pair.second);
    }
    
    private void disableButtons() {
    	for (View view : stateButtons)
    		view.setEnabled(false);
    }
    
    /*private void enableButtons() {
    	for (View view : stateButtons)
    		view.setEnabled(true);
    }*/
    
    private void refreshButtons()
    {
    	if (Control.getInstance().isPlaying()) {
			actionButton.setImageResource(R.drawable.pause);
			recordButton.setEnabled(Control.getInstance().canStopPlaying());
		}
		else if (Control.getInstance().isPaused()) {
			actionButton.setImageResource(R.drawable.play);
			recordButton.setEnabled(Control.getInstance().canStartPlaying());
		}
		if (Control.getInstance().isRecording()) {
			recordButton.setImageResource(R.drawable.stoprecord);
			recordButton.setEnabled(Control.getInstance().canStopRecording());
		}
		else {
			recordButton.setImageResource(R.drawable.record);
			recordButton.setEnabled(Control.getInstance().canStartRecording());
		}
		upvoteButton.setEnabled(Control.getInstance().canUpvote());
		downvoteButton.setEnabled(Control.getInstance().canDownvote());
    }
	
	private class InteractionThread extends AsyncTask<View, Void, View> {	
		
		protected View doInBackground(View... params) {
			try {
				View v = params[0];
				if (v == actionButton) {
					Control.getInstance().action();
					return v;
				}
				else if (v == nextButton) {
					Control.getInstance().next();
					return v;
				}
				else if (v == prevButton) {
					Control.getInstance().prev();
					return v;
				}
				else if (v == recordButton) {
					Control.getInstance().record();
					return v;
				}
				else if (v == upvoteButton) {
					Control.getInstance().upvoteSelected();
					return v;
				}
				else if (v == downvoteButton) {
					Control.getInstance().downvoteSelected();
					return v;
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		protected void onPreExecute()
		{
			pushButtonState();
			disableButtons();
		}
		
		protected void onPostExecute(View result)
		{
			popButtonState();
			refreshButtons();
		}
	}
	
	@Override
	public void onClick(View v) {
		new InteractionThread().execute(v);
	}
}