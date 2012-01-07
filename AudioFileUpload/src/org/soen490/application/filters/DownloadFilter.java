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
 * Servlet Filter implementation class DownloadFilter
 */
@WebFilter("/capstone/download")
public class DownloadFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,	FilterChain chain) throws IOException, ServletException {
		String path = ((HttpServletRequest)request).getPathInfo();
		
		System.out.println("path: " + path);
		
		String command = null;
		
		Pattern pattern = Pattern.compile("^/(download)$");
		Matcher matcher = pattern.matcher(path);
		
		if (matcher.find()) {	
			if("download".equals(matcher.group(1)))
				command = "Download";
		} 
		
		String queryString = ((HttpServletRequest)request).getQueryString();
		
		pattern = Pattern.compile("mid=(\\d+)");
		matcher = pattern.matcher(queryString);
		
		if (matcher.find()) {
			String mid = null;
			mid = matcher.group(1);
			
			request.setAttribute("mid", mid);
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
