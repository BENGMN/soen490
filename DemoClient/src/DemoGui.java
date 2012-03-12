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
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.http.client.ClientProtocolException;

public class DemoGui extends JFrame {
	
	private static final int WIDTH = 400;
	private static final int HEIGHT = 250;
	private static final String PLAYER_PATH = "C:\\Program Files (x86)\\QuickTime\\QuickTimePlayer.exe"; //change this depending on your computer
	private JButton uploadFileButton;
	private JFileChooser fileChooser;
	private JScrollPane audioFileChooser;
	private List<File> listOfRetrievedFiles = new LinkedList<File>(); //temp
	private Vector<String> audioFileNames; 
	private JList listOfFiles;
    private String filePath;
	private Process process;
	private FileTransfer fileTransfer;
	
	public DemoGui( ) {
		fileTransfer = new FileTransfer();
	}
	
	public void createAndShowGui() {
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setFileFilter(new AudioFilter());		
		audioFileChooser = new JScrollPane(listOfFiles);
		listOfFiles = new JList();
		audioFileNames = new Vector<String>();
		
		uploadFileButton = new JButton("Upload File");
		uploadFileButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int fileChooserValue = fileChooser.showDialog(uploadFileButton, "Select");
				
				 if (fileChooserValue == JFileChooser.APPROVE_OPTION) {
					 try {
						 //send file
						fileTransfer.uploadFile(fileChooser.getSelectedFile());
					} catch (ClientProtocolException e1) {
						e1.printStackTrace();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				 }
			}
		});
				
		JButton retrieveFilesButton = new JButton("Retrieve Files");
		retrieveFilesButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO: retrieve audio files, put them in a list and put their names a string vector							
				audioFileNames.add("First");
				audioFileNames.add("El Michels Affair - C.R.E.A.M.mp3");
				audioFileNames.add("Test.amr");
				audioFileNames.add("Test2.amr");
				listOfFiles.setListData(audioFileNames);
				audioFileChooser.validate();
				listOfFiles.setSelectedIndex(0); // ensures that a file is selected
			}
		});
		
	    listOfFiles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listOfFiles.addListSelectionListener(new ListSelectionListener(){
			@Override
			public void valueChanged(ListSelectionEvent e) {
				String selectedFile = "";
				if(!e.getValueIsAdjusting()) {
					selectedFile = (String)listOfFiles.getSelectedValue();
				}
				
				filePath = ".\\" + selectedFile;				
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
		fileButtonPanel.add(retrieveFilesButton);
		
		JPanel fileChooserPanel = new JPanel();
		fileChooserPanel.add(audioFileChooser);
			
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

