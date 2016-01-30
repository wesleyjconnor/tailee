package com.wjc;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.wjc.Log.*;

/**
 * This is used in scripts to 'tail' a file until the required text is found returns SUCCESS
 * or it finds 'fail' text is found returns FAILURE
 * It will stop after 60 minutes (should make this optional)
 */
public class Tailee
{
	private static final String fileParam = "-file";
	private static final String failParam = "-fail";
	private static final String finishParam = "-finish";

	public static void main(String[] args)
	{
		if (args.length < 2)
		{
			err("Not enough args");
			failed();
		}

		if (args.length % 2 != 0)
		{
			log("Only accept even number of params");
			failed();
		}

		List<String> finishText = new ArrayList<>();
		List<String> failText = new ArrayList<>();

		File toTail = null;

		for (int i = 0; i < args.length; i = i + 2)
		{

			String param = args[i];
			String value = args[i + 1];

			switch (param)
			{
			case fileParam:
				toTail = new File(value);
				break;
			case finishParam:
				finishText.add(value);
				break;
			case failParam:
				failText.add(value);
				break;
			default:
				err("BAD PARAMS");
				err("param was " + param);
				err("value was " + value);
				failed();
			}
		}

		log("Processed flags :");
		for (String s : finishText)
		{
			log("Finishing on : " + s);
		}
		for (String s : failText)
		{
			log("Halting on : " + s);
		}

		new Thread(new com.wjc.killMe()).start();

		checkFile(toTail);
		new com.wjc.Tail().tail(toTail, finishText, failText);

	}

	private static void checkFile(final File file)
	{
		if (file != null && !file.exists())
		{
			err("File : " + file.getAbsolutePath() + " was not found ");
			failed();
		}
	}

}