package com.bacursortimer;

import java.awt.Color;
import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;
import net.runelite.client.config.Units;

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

	@ConfigItem(
		keyName = "font",
		name = "Font",
		description = "Font of the wave timer.",
		position = 3
	)
	default TimerFont font()
	{
		return TimerFont.RUNESCAPE;
	}

	@ConfigItem(
		keyName = "bold",
		name = "Bold",
		description = "Draw the wave timer in bold.",
		position = 4
	)
	default boolean bold()
	{
		return true;
	}

	@Range(min = 8, max = 64)
	@ConfigItem(
		keyName = "fontSize",
		name = "Font size",
		description = "Size of the wave timer text. 16 is the game's normal size.",
		position = 5
	)
	default int fontSize()
	{
		return 16;
	}

	@ConfigItem(
		keyName = "showBackground",
		name = "Background",
		description = "Draw a rectangle behind the wave timer.",
		position = 6
	)
	default boolean showBackground()
	{
		return false;
	}

	@ConfigItem(
		keyName = "backgroundColor",
		name = "Background color",
		description = "Color of the rectangle behind the wave timer.",
		position = 7
	)
	default Color backgroundColor()
	{
		return Color.BLACK;
	}

	@Range(max = 100)
	@Units(Units.PERCENT)
	@ConfigItem(
		keyName = "backgroundOpacity",
		name = "Background opacity",
		description = "Opacity of the rectangle behind the wave timer, from 0 (invisible) to 100 (solid).",
		position = 8
	)
	default int backgroundOpacity()
	{
		return 50;
	}
}
