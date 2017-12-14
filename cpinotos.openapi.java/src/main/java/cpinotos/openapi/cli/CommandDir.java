package cpinotos.openapi.cli;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;


@Parameters(commandDescription = "List Netstorage folder content. Output will be provided in JSON format")
public class CommandDir {
	@Parameter(names = { "--out", "-o" }, description = "Path to folder on Netstorage, including the CPCode Folder (e.g. /599907/my/folder)", required = true)
	public String out;
	@Parameter(names = { "--recursive", "-r" }, description = "Force recursive listing of all subfolders", required = false)
	public boolean recursive = false;
}
