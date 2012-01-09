package org.soen490.domain.command;

import java.io.File;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.fileupload.FileItem;
import org.soen490.domain.Message;
import org.soen490.domain.MessageFactory;
import org.soen490.domain.User;
import org.soen490.domain.helper.Helper;

public class UploadCommand extends DomainCommand {

	private static final String PATH = "C:\\Users\\Soto\\Desktop\\Audio Files";
	
	@Override
	public void execute(Helper helper) {
		try {
			List items = (List)helper.getRequestAttribute("items");
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
					double latitude = helper.getDouble("latitude");
					double longitude = helper.getDouble("longitude");
					float speed = helper.getFloat("speed");
					User user = new User(1);
					
					Message message = MessageFactory.createNew(latitude, longitude, speed, Calendar.getInstance(), user);
					String audioFileName = "audio_" + message.getMessageID() + ".amr";
					
					File uploadedFile = new File(PATH + audioFileName);
					
					item.write(uploadedFile); 
					System.out.println("Content type: " + contentType);
					System.out.println("Field: " + fieldName);
					System.out.println("File name: " + fileName);
					System.out.println("Size: " + sizeInBytes);
					System.out.println("Is in memory:" + isInMemory);
				}
			}
		} catch (Exception e) {
			// TODO exception handling
			e.printStackTrace();
		}
	}

}
