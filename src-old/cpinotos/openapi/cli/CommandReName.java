package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(commandDescription = "Create SymLink on Netstorage")
public class CommandReName {
	@Parameter(names = { "--in", "-i" }, description = "original Path", required = true)
	public String in;
	@Parameter(names = { "--out", "-o" }, description = "new path", required = true)
	public String out;
}
