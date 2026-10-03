package com.bacursortimer;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class BACursorTimerPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(BACursorTimerPlugin.class);
		RuneLite.main(args);
	}
}
