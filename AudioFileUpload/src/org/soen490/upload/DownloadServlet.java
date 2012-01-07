package org.soen490.upload;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DownloadServlet extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		// This messageID would be used to get the correct file eventually
		long messageID = Long.parseLong(request.getParameter("messageID"));
		String fileName = "C:\\Users\\Soto\\Desktop\\new_audio1.amr";
		File returnFile = new File(fileName);
		
		
		ServletOutputStream out = response.getOutputStream();
		ServletContext context = getServletConfig().getServletContext();
		String mimetype = context.getMimeType("C:\\Users\\Soto\\Desktop\\new_audio1.amr");
		
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
