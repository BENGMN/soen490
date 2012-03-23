package Client;
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

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class AudioFilter extends FileFilter{
	
	public static final String TYPE_AMR = "amr";

	@Override
	public boolean accept(File file) {
		if(file.isDirectory()) {
			return true;
		}
		
		String fileName = file.getName();
		String extension = " ";
		int index = fileName.lastIndexOf(".");
		
		if(index > 0 && index < fileName.length() - 1) {
			extension = fileName.substring(index + 1);
			
			if(extension.equals(TYPE_AMR)) {
				return true;
			}
		}
		
		return false;
	}

	@Override
	public String getDescription() {
		return "AMR files (*.amr)";
	}

}
