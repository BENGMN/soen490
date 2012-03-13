package application.commands;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import application.ServerParameters;

import exceptions.MapperException;
import exceptions.ParameterException;
import exceptions.UnrecognizedUserException;

public class UpdateServerParametersCommand extends FrontCommand {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response)throws MapperException, ParameterException, UnrecognizedUserException, SQLException, ServletException, IOException {

		//Enumeration params = request.getParameterNames();
		
		Map params = request.getParameterMap();
		
		Set<Entry<String, Double>> set = params.entrySet();
		Iterator<Entry<String, Double>> it = set.iterator();
		
		while (it.hasNext()) {
			Entry<String, Double> e = it.next();
			if (ServerParameters.getUniqueInstance().containsKey(e.getKey()));
				ServerParameters.getUniqueInstance().put(e.getKey(), e.getValue());
		}
		
		request.getRequestDispatcher("/ServerConfigurationUtility.jsp").forward(request, response);
	}
}
