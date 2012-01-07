package org.soen490.application.dispatcher.get;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import org.soen490.application.dispatcher.Dispatcher;

public class Download extends Dispatcher {

	@Override
	public void execute() throws ServletException, IOException {
		/// This messageID would be used to get the correct file eventually
		long messageID = Long.parseLong(request.getParameter("mid"));
		String fileName = "C:\\Users\\Soto\\Desktop\\new_audio1.amr";
		File returnFile = new File(fileName);
		
		ServletOutputStream out = response.getOutputStream();
		// Only in a servlet
		// ServletContext context = getServletConfig().getServletContext();
		// String mimetype = context.getMimeType("C:\\Users\\Soto\\Desktop\\new_audio1.amr");
		
		String mimetype = "audio/AMR";
		response.setContentType((mimetype != null) ? mimetype : "application/octet-stream");
		response.setContentLength((int)returnFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=" + "new_audio.amr" + "\"");
		
		FileInputStream in = new FileInputStream(returnFile);
		byte[] buffer = new byte[4096];
		
		int length;
		while((length = in.read(buffer)) > 0) {
			out.write(buffer, 0, length);
		}
		in.close();
		out.flush();
	}

}
