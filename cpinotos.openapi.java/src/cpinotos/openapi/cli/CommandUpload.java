package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(commandDescription = "Upload files to Netstorage")
public class CommandUpload {
	@Parameter(names = { "--in", "-i" }, description = "Path to local file", required = true)
	public String in;
	@Parameter(names = { "--out", "-o" }, description = "Path to netstorage, including CPCode Folder (e.g. /599907/my/file.txt)", required = true)
	public String out;
}
