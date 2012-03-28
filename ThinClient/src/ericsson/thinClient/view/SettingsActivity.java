package ericsson.thinClient.view;

import ericsson.thinClient.R;
import ericsson.thinClient.technical.AndroidHttp;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class SettingsActivity extends Activity implements OnClickListener {
	Button saveSettingsButton;
    Button cancelSettingsButton;
    EditText hostnameField;
    EditText portField;
    
    static SettingsActivity singleton = null;
	
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        singleton = this;
        
        setContentView(R.layout.settings);
        
        saveSettingsButton = (Button)findViewById(R.id.settingsSave);
        cancelSettingsButton = (Button)findViewById(R.id.settingsCancel);
    	hostnameField = (EditText)findViewById(R.id.hostname);
        portField = (EditText)findViewById(R.id.port);
        saveSettingsButton.setOnClickListener(this);
        cancelSettingsButton.setOnClickListener(this);
        hostnameField.setText(AndroidHttp.getInstance().entrypointHostname);
    	portField.setText(new Integer(AndroidHttp.getInstance().entrypointPort).toString());
    }
    
	public void onClick(View v) {
		if (v == saveSettingsButton) {
			AndroidHttp.getInstance().entrypointHostname = hostnameField.getText().toString();
	    	AndroidHttp.getInstance().entrypointPort = Integer.parseInt(portField.getText().toString());
	    	finish();
		}
		else if (v == cancelSettingsButton) {
			setContentView(R.layout.main);
			finish();
		}
	}
}
