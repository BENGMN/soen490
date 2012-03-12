package ericsson.thinClient.view;

import java.io.IOException;

import ericsson.thinClient.R;
import ericsson.thinClient.domain.Control;
import ericsson.thinClient.domain.Message;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ThinClientActivity extends Activity  implements OnClickListener, Control.Listener {
	
    ImageButton actionButton;
    ImageButton prevButton;
    ImageButton nextButton;
    ImageButton recordButton;
    ImageButton upvoteButton;
    ImageButton downvoteButton;
    TextView statusView;
    
    private static ThinClientActivity singleton = null;
    
    public static ThinClientActivity getInstance()
    {
    	return singleton;
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singleton = this;
        setContentView(R.layout.main);
        actionButton = (ImageButton) findViewById(R.id.action);
        prevButton = (ImageButton)findViewById(R.id.prev);
        nextButton = (ImageButton)findViewById(R.id.next);
        recordButton = (ImageButton)findViewById(R.id.record);
        upvoteButton = (ImageButton)findViewById(R.id.upvote);
        downvoteButton = (ImageButton)findViewById(R.id.downvote);
        statusView = (TextView)findViewById(R.id.status);
        
        recordButton.setOnClickListener(this);
        actionButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        upvoteButton.setOnClickListener(this);
        downvoteButton.setOnClickListener(this);     
        
        statusView.setText("sldfhjasdlfjlskdgjflaskg");
        
        Control.getInstance().addListener(this);
        updatePlayButton();
        updateRecordButton();
        updateVotingButtons();
    }

	@Override
	public void onClick(View v) {
		try {
			if (v == actionButton)
				Control.getInstance().action();
			else if (v == nextButton)
				Control.getInstance().next();
			else if (v == prevButton)
				Control.getInstance().prev();
			else if (v == recordButton)
				Control.getInstance().record();
			else if (v == upvoteButton)
				Control.getInstance().upvoteSelected();
			else if (v == downvoteButton)
				Control.getInstance().downvoteSelected();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void updatePlayButton() {
		if (Control.getInstance().isPlaying())
			actionButton.setImageResource(R.drawable.pause);
		else if (Control.getInstance().isPaused())
			actionButton.setImageResource(R.drawable.play);
	}

	@Override
	public void updateRecordButton() {
		if (Control.getInstance().isRecording())
			recordButton.setImageResource(R.drawable.record);
		else
			recordButton.setImageResource(R.drawable.stoprecord);
	}

	@Override
	public void updateVotingButtons() {
		Message selected = Control.getInstance().getSelectedMessage(); 
		if (selected == null) {
			upvoteButton.setEnabled(false);
			downvoteButton.setEnabled(false);
		}
		else {
			upvoteButton.setEnabled(!selected.getRatingModified());
			downvoteButton.setEnabled(!selected.getRatingModified());
		}
	}
}