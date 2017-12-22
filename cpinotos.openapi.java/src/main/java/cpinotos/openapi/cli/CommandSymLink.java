package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(commandDescription = "Create SymLink on Netstorage")
public class CommandSymLink {
	@Parameter(names = { "--in", "-i" }, description = "Path, the desired path location (including applicable sub-directories) ending in a link name that will serve as the symlink (e.g., /599907/my/mylink). Include an extension for the link name, if applicable", required = true)
	public String in;
	@Parameter(names = { "--out", "-o" }, description = "Target, Used to define the target of the symlink. Path on netstorage, including CPCode Folder (e.g. /599907/my/file.txt).", required = true)
	public String out;
}
