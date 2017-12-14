package cpinotos.openapi.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import cpinotos.openapi.test.step.OpenCLITestDeleteFile;
import cpinotos.openapi.test.step.OpenCLITestDeleteFolder;
import cpinotos.openapi.test.step.OpenCLITestDir;
import cpinotos.openapi.test.step.OpenCLITestDirRecursive;
import cpinotos.openapi.test.step.OpenCLITestMkdir;
import cpinotos.openapi.test.step.OpenCLITestSymLink;
import cpinotos.openapi.test.step.OpenCLITestUpload;

@RunWith(Suite.class)
@SuiteClasses({
	OpenCLITestMkdir.class, 
	OpenCLITestUpload.class, 
	OpenCLITestSymLink.class,
	OpenCLITestDir.class, 
	OpenCLITestDirRecursive.class, 
	OpenCLITestDeleteFile.class, 
	OpenCLITestDeleteFolder.class})

public class TestSuiteOpenCLI {
}
