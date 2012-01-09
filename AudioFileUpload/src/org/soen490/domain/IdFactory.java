package org.soen490.domain;

public class IdFactory {
	private static long id = 0;
	
	public synchronized static long getId() {
		return id++;
	}
}
