package ericsson.thinClient.view;

import java.io.IOException;

import ericsson.thinClient.R;
import ericsson.thinClient.domain.Control;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ThinClientActivity extends Activity  implements OnClickListener {
    /** Called when the activity is first created. */
    Button actionButton;
    Button prevButton;
    Button nextButton;
    Button recordButton;
    Button upvoteButton;
    Button downvoteButton;
    
    private static ThinClientActivity singleton = null;
    
    public static ThinClientActivity getInstance()
    {
    	return singleton;
    }
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        singleton = this;
        setContentView(R.layout.main);
        actionButton = (Button) findViewById(R.id.action);
        prevButton = (Button)findViewById(R.id.prev);
        nextButton = (Button)findViewById(R.id.next);
        recordButton = (Button)findViewById(R.id.record);
        upvoteButton = (Button)findViewById(R.id.upvote);
        downvoteButton = (Button)findViewById(R.id.downvote);
        
        
        recordButton.setOnClickListener(this);
        actionButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        upvoteButton.setOnClickListener(this);
        downvoteButton.setOnClickListener(this);      
    }

	@Override
	public void onClick(View v) {
		try {
			if (v == actionButton) {
				Control.getInstance().action();
				if (Control.getInstance().isPlaying())
					actionButton.setText("Pause");
				else if (Control.getInstance().isPaused())
					actionButton.setText("Play");
				else
					actionButton.setText("Unknown");
			}
			else if (v == nextButton) {
				Control.getInstance().next();
			}
			else if (v == prevButton) {
				Control.getInstance().prev();
			}
			else if (v == recordButton) {
				Control.getInstance().record();
				if (Control.getInstance().isRecording())
					recordButton.setText("Stop");
				else
					recordButton.setText("Record");
			}
			else if (v == upvoteButton)
				Control.getInstance().upvoteSelected();
			else if (v == downvoteButton)
				Control.getInstance().downvoteSelected();
		}
		catch (IOException e) {
			e.printStackTrace();
		}
	}
}