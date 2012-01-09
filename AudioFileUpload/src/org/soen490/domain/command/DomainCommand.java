package org.soen490.domain.command;

import org.soen490.domain.helper.Helper;

public abstract class DomainCommand {	
	public abstract void execute(Helper helper);
}
