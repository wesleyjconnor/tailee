package com.wjc;

import java.time.ZonedDateTime;

/**
 * Make sure this doesnt run longer than 1 hour
 */
public class killMe implements Runnable
{
	@Override
	public void run()
	{
		ZonedDateTime now = ZonedDateTime.now();
		ZonedDateTime oneHoursTime = ZonedDateTime.now().plusHours(1);

		while (now.isBefore(oneHoursTime))
		{
			now = ZonedDateTime.now();
			try
			{
				Thread.sleep(1000);
			}
			catch (InterruptedException ignored)
			{
			}
		}

		System.out.println(Tailee.class.getSimpleName() + " timeout expired, we have to die now");
		System.exit(1);
	}
}