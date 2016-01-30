package com.wjc;

import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TailTest
{
	@Rule
	public TemporaryFolder testFolder = new TemporaryFolder();

	@Rule
	public final ExpectedSystemExit exit = ExpectedSystemExit.none();

	public File setupGoodFile() throws Exception
	{
		return setupFile(Arrays.asList("some data", "finished", "some data after"));
	}

	public File setupErrorFile() throws Exception
	{
		return setupFile(Arrays.asList("some data", "x not found", "finished"));
	}

	public File setupFile(List<String> lines) throws Exception
	{
		File file = testFolder.newFile();
		Path path = Paths.get(file.toURI());
		Files.write(path, lines, Charset.forName("UTF-8"));
		return file;
	}

	/**
	 * Simple success text example
	 *
	 * @throws Exception
	 */
	@Test(timeout = 5000)
	public void testSuccess() throws Exception
	{
		exit.expectSystemExitWithStatus(0);

		List<String> successText = Collections.singletonList("finished");
		List<String> errorText = Collections.singletonList("x not found");

		com.wjc.Tail tail = new com.wjc.Tail();
		tail.tail(setupGoodFile(), successText, errorText);
	}

	/**
	 * Simple fail text example
	 *
	 * @throws Exception
	 */
	@Test(timeout = 5000)
	public void testTailError() throws Exception
	{
		exit.expectSystemExitWithStatus(1);

		List<String> successText = Collections.singletonList("finished");
		List<String> errorText = Collections.singletonList("x not found");

		com.wjc.Tail tail = new com.wjc.Tail();
		tail.tail(setupErrorFile(), successText, errorText);
	}
}