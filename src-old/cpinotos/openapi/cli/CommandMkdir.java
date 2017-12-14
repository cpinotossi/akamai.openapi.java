package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;



@Parameters(commandDescription = "Create folder at Netstorage")
public class CommandMkdir {
	@Parameter(names = { "--out", "-o" }, description = "Path to netstorage, including CPCode Folder (e.g. /599907/my/folder)", required = true)
	public String out;
}

