package application;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UnknownCommand extends FrontCommand
{
	public void execute(HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Unknown Command");
		}
		catch (IOException e)
		{
			
		}
	}
}
