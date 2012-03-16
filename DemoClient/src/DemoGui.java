/**
 * SOEN 490
 * Capstone 2011
 * Team members: 	
 * 			Sotirios Delimanolis
 * 			Filipe Martinho
 * 			Adam Harrison
 * 			Vahe Chahinian
 * 			Ben Crudo
 * 			Anthony Boyer
 * 
 * @author Capstone 490 Team Moving Target
 *
 */

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.http.client.ClientProtocolException;

public class DemoGui extends JFrame {
	
	private static final int WIDTH = 300;
	private static final int HEIGHT = 440;
	private static final String PLAYER_PATH = "C:\\Program Files (x86)\\QuickTime\\QuickTimePlayer.exe"; //change this depending on your computer
	private JButton uploadFileButton;
	private JFileChooser fileChooser;
	private JScrollPane listScrollPane;
	private Vector<String> audioFileNames; 
	private Map<String, Message> messages;
	private JList listOfFiles;
    private String filePath;
	private Process process;
	private FileTransfer fileTransfer;
	private JTextField statusField;
	private JTextArea fileInfo;
	private JScrollPane fileInfoScrollPane;
	
	public DemoGui( ) {
		fileTransfer = new FileTransfer();
		messages = new HashMap<String, Message>();
	}
	
	public void createAndShowGui() {
		fileChooser = new JFileChooser(".\\src\\");
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new AudioFilter());
		listOfFiles = new JList();		
		listScrollPane = new JScrollPane(listOfFiles);	
		audioFileNames = new Vector<String>();
		statusField = new JTextField(23);
		statusField.setEditable(false);
		fileInfo = new JTextArea(10, 23);
		fileInfo.setEditable(false);
		fileInfoScrollPane = new JScrollPane(fileInfo);
		
		uploadFileButton = new JButton("Upload File");
		uploadFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int fileChooserValue = fileChooser.showDialog(uploadFileButton, "Select");
				
				 if (fileChooserValue == JFileChooser.APPROVE_OPTION) {
					 try {
						 //send file
						int response = fileTransfer.uploadFile(fileChooser.getSelectedFile());
						statusField.setText("Status Code: " + response);
					} catch (ClientProtocolException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				 }
			}
		});
				
		JButton retrieveFileButton = new JButton("Retrieve Files");
		retrieveFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//Retrieve audio files	
				try {
					messages = fileTransfer.downloadFiles();
				} catch (ClientProtocolException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				audioFileNames.clear();
				for(String name: messages.keySet()) {
					audioFileNames.add(name);
				}
						
				listOfFiles.setListData(audioFileNames);
				listScrollPane.validate();
				listOfFiles.setSelectedIndex(0); // ensures that a file is selected
				fileInfo.setText(messages.get((String)listOfFiles.getSelectedValue()).toString());
			}
		});
		
	    listOfFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfFiles.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting()) {
					String selectedFile = "";				
					selectedFile = (String)listOfFiles.getSelectedValue();				
					Message message = messages.get(selectedFile);
					fileInfo.setText(message.toString());

					try {
						filePath = message.getMessage().getCanonicalPath();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}				
			}
		});

		JButton playButton = new JButton("Open Audio File");
		playButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ProcessBuilder processBuilder = new ProcessBuilder(PLAYER_PATH, filePath);			 
				try {
					
					//If a file is currently playing, close the application playing it. 
					if(process != null) {
						process.destroy();
					}
					
					process = processBuilder.start();
				} catch (IOException e1) {
					e1.printStackTrace();
				}		
			}		
		});
				
		JButton stopButton = new JButton("Close Audio File");
		stopButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				process.destroy(); // closes the external application
			}		
		});
				
		JPanel fileButtonPanel = new JPanel();
		fileButtonPanel.add(uploadFileButton);
		fileButtonPanel.add(retrieveFileButton);
		
		
		JPanel fileChooserPanel = new JPanel();
		fileChooserPanel.add(statusField);
		fileChooserPanel.add(listScrollPane);
		fileChooserPanel.add(fileInfoScrollPane);
			
		JPanel mediaButtonPanel = new JPanel();
		mediaButtonPanel.add(playButton);
		mediaButtonPanel.add(stopButton);
		
		add(fileButtonPanel, BorderLayout.NORTH);
		add(fileChooserPanel, BorderLayout.CENTER);
		add(mediaButtonPanel, BorderLayout.SOUTH);
        
		setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
	}
}

