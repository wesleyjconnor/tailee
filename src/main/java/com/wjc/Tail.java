package com.wjc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import static com.wjc.Log.log;

/**
 *
 */
public class Tail
{
	public void tail(final File toTail, final List<String> finishText, final List<String> failText)
	{
		try
		{
			BufferedReader bufferedReader = new BufferedReader(new FileReader(toTail));

			while (true)
			{
				String line = bufferedReader.readLine();
				if (line == null)
				{
					//wait until there is more of the file for us to read
					Thread.sleep(10);
				}
				else
				{
					finishText.stream().filter(line::contains).forEach(s -> {
						System.out.println(line);
						log("");
						log("Finishing on success text " + s);
						System.exit(0);
					});

					failText.stream().filter(line::contains).forEach(s -> {
						System.out.println(line);
						log("");
						log("Failing on failure text : " + s);
						System.exit(1);
					});

					System.out.println(line);
				}
			}
		}
		catch (InterruptedException | IOException e)
		{
			e.printStackTrace();
		}
	}
}