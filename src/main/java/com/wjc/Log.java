package com.wjc;

public class Log
{
	public static void failed()
	{
		err("normal usage is 'java -jar " + Tailee.class.getSimpleName()
				+ ".jar -file file.log -finish \"string to search for\"' (optionally) -fail \"string to fail on \"");
		System.exit(1);
	}

	/**
	 * We dont need no log4j
	 */
	public static void log(String message)
	{
		System.out.println(Tailee.class.getSimpleName() + " : " + message);
	}

	public static void err(String message)
	{
		System.err.println(Tailee.class.getSimpleName() + " : " + message);
	}
}