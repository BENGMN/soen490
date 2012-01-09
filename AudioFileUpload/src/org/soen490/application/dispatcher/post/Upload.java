package org.soen490.application.dispatcher.post;

import java.io.File;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.soen490.application.dispatcher.Dispatcher;
import org.soen490.domain.command.UploadCommand;

public class Upload extends Dispatcher {


	private static final String TEMP_DIRECTORY = "C:\\Users\\Soto\\Desktop\\Temp\\";
	private static final long REQUEST_MAX_SIZE = 50 * 1024;
	
	@Override
	public void execute() throws ServletException, IOException {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		
		if (isMultipart) {
			try {
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				
				// Set size threshold for storing upload
				fileItemFactory.setSizeThreshold(1 * 1024 * 50); // 50 KB
				
				// Set temporary directory to store uploaded files above threshold size
				fileItemFactory.setRepository(new File(TEMP_DIRECTORY));
				
				ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
				
				//HashMap<String, String> params = new HashMap<String, String>();
				
				upload.setSizeMax(REQUEST_MAX_SIZE);
				
				request.setAttribute("items", upload.parseRequest(request));

				UploadCommand uc = new UploadCommand();
				uc.execute(helper);
	
			} catch (Exception ex) {
				throw new ServletException(ex);
			}
		} else {
			throw new ServletException();
		}
	}

}
