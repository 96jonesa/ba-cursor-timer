package com.bacursortimer;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;

@ConfigGroup(BACursorTimerConfig.GROUP)
public interface BACursorTimerConfig extends Config
{
	String GROUP = "baCursorTimer";

	@ConfigItem(
		keyName = "textColor",
		name = "Text color",
		description = "Color of the wave timer.",
		position = 0
	)
	default Color textColor()
	{
		return Color.WHITE;
	}

	@Range(min = -200, max = 200)
	@ConfigItem(
		keyName = "offsetX",
		name = "Horizontal offset",
		description = "Pixels right of the cursor to draw the timer.",
		position = 1
	)
	default int offsetX()
	{
		return 16;
	}

	@Range(min = -200, max = 200)
	@ConfigItem(
		keyName = "offsetY",
		name = "Vertical offset",
		description = "Pixels below the cursor to draw the timer.",
		position = 2
	)
	default int offsetY()
	{
		return 0;
	}
}
