package org.soen490.upload;

import java.io.File;
import java.io.IOException;

import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class UploadServlet extends HttpServlet {

	private static final String PATH = "C:\\Users\\Soto\\Desktop\\";
	private static final String TEMP_DIRECTORY = "C:\\Users\\Soto\\Desktop\\Temp\\";
	private static final long REQUEST_MAX_SIZE = 50 * 1024;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

				List items = upload.parseRequest(request);
				Iterator it = items.iterator();
				
				while (it.hasNext()) {
					FileItem item = (FileItem) it.next();
					
					if(item.isFormField()) {
						
					} else {
						String contentType = item.getContentType();
						String fileName = item.getName();
						String fieldName = item.getFieldName();
						boolean isInMemory = item.isInMemory();
						long sizeInBytes = item.getSize();
						
						File uploadedFile = new File(PATH + "new_audio1.amr");
						
						item.write(uploadedFile); 
						System.out.println("Content type: " + contentType);
						System.out.println("Field: " + fieldName);
						System.out.println("File name: " + fileName);
						System.out.println("Size: " + sizeInBytes);
						System.out.println("Is in memory:" + isInMemory);
					}
				}
	
			} catch (Exception ex) {
				throw new ServletException(ex);
			}
		} else {
			throw new ServletException();
		}
		
	}
}
