package ericsson.thinClient.view;

import ericsson.thinClient.R;
import ericsson.thinClient.R.id;
import ericsson.thinClient.R.layout;
import ericsson.thinClient.domain.ControlLogic;
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
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        actionButton = (Button) findViewById(R.id.action);
        prevButton = (Button)findViewById(R.id.prev);
        nextButton = (Button)findViewById(R.id.next);
        recordButton = (Button)findViewById(R.id.record);
        
        recordButton.setOnClickListener(this);
        actionButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		if (v == actionButton) {
			ControlLogic.getInstance().action();
			if (ControlLogic.getInstance().isPlaying())
				actionButton.setText("Pause");
			else if (ControlLogic.getInstance().isPaused())
				actionButton.setText("Play");
			else
				actionButton.setText("Unknown");
		}
		else if (v == nextButton) {
			ControlLogic.getInstance().next();
		}
		else if (v == prevButton) {
			ControlLogic.getInstance().prev();
		}
		else if (v == recordButton) {
			ControlLogic.getInstance().record();
			if (ControlLogic.getInstance().isRecording())
				recordButton.setText("Stop");
			else
				recordButton.setText("Record");
		}
	}
}