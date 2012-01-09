package org.soen490.domain;

import java.sql.SQLException;
import java.util.Calendar;

import org.soen490.domain.IdFactory;
import org.soen490.domain.Message;
import org.soen490.domain.User;
import org.soen490.domain.message.mappers.MessageOutputMapper;

public class MessageFactory {
	public static Message createNew(double latitude, double longitude, float speed, Calendar date, User user) throws SQLException {
		Message msg = new Message(IdFactory.getId(), latitude, longitude, speed, date, 0, user);
		MessageOutputMapper.insert(msg);  
		return msg;
	}
}
