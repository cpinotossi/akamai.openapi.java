package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(commandDescription = "Create SymLink on Netstorage")
public class CommandReName {
	@Parameter(names = { "--in", "-i" }, description = "Original Path, Path to netstorage, including CPCode Folder (e.g. /599907/my/old.txt)", required = true)
	public String in;
	@Parameter(names = { "--out", "-o" }, description = "New Path, Path to netstorage, including CPCode Folder (e.g. /599907/my/new.txt)", required = true)
	public String out;
}
