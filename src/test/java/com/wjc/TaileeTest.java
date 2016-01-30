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
import java.util.List;

public class TaileeTest
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
	 * Full success text test
	 *
	 * @throws Exception
	 */
	@Test(timeout = 5000)
	public void testSuccess() throws Exception
	{
		exit.expectSystemExitWithStatus(0);

		File file = setupGoodFile();
		String[] strings = { "-file", file.getAbsolutePath(), "-finish", "finished" };
		com.wjc.Tailee.main(strings);
	}

	/**
	 * Full error text test
	 *
	 * @throws Exception
	 */
	@Test(timeout = 5000)
	public void testError() throws Exception
	{
		exit.expectSystemExitWithStatus(1);

		File file = setupErrorFile();
		String[] strings = { "-file", file.getAbsolutePath(), "-fail", "x not found" };
		com.wjc.Tailee.main(strings);
	}

	/**
	 * Only accept even number of parameters
	 *
	 * @throws Exception
	 */
	@Test
	public void testEvenNumberParameters() throws Exception
	{
		exit.expectSystemExitWithStatus(1);

		File file = setupGoodFile();
		String[] strings = { "-file", file.getAbsolutePath(), "-finish" };
		com.wjc.Tailee.main(strings);
	}

	/**
	 * Only accept even number of parameters
	 *
	 * @throws Exception
	 */
	@Test
	public void testAtLeast2Parameters() throws Exception
	{
		exit.expectSystemExitWithStatus(1);
		com.wjc.Tailee.main(new String[0]);
	}

	/**
	 * Only accept even number of parameters
	 *
	 * @throws Exception
	 */
	@Test
	public void testFileNotFound() throws Exception
	{
		exit.expectSystemExitWithStatus(1);
		String[] strings = { "-file", "/some/not/existing/thing", "-finish", "finish text" };
		com.wjc.Tailee.main(strings);
	}
}