package org.soen490.application.filters;




import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class UploadFilter
 */
@WebFilter("/capstone/upload")
public class UploadFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest)request).getPathInfo();
		
		System.out.println("path: " + path);
		
		String command = null;
		
		Pattern pattern = Pattern.compile("^/(upload)$");
		Matcher matcher = pattern.matcher(path);
		
		if (matcher.find()) {
			if("upload".equals(matcher.group(1)))
				command = "Upload";
		} 
		
		String queryString = ((HttpServletRequest)request).getQueryString();
		
		pattern = Pattern.compile("latitude=(\\d+)&longitude=(\\d+)&speed=(\\d+)$");
		matcher = pattern.matcher(queryString);
		
		if(matcher.find()) {
			String latitude = null;
			String longitude = null;
			String speed = null;
			
			latitude = matcher.group(1);
			longitude = matcher.group(2);
			speed = matcher.group(3);
			
			request.setAttribute("latitude", latitude);
			request.setAttribute("longitude", longitude);
			request.setAttribute("speed", speed);
		}
		
		request.setAttribute("command", command);
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
		
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
       
   

}
