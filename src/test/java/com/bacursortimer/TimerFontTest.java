package com.bacursortimer;

import java.awt.Font;
import net.runelite.client.ui.FontManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class TimerFontTest
{
	@Nested
	class TestFont
	{
		@Test
		void runescapeUsesTheGamesBoldFace()
		{
			assertEquals(FontManager.getRunescapeBoldFont().getFamily(), TimerFont.RUNESCAPE.font(true, 16).getFamily());
		}

		@Test
		void runescapeUsesTheGamesRegularFaceWhenNotBold()
		{
			assertEquals(FontManager.getRunescapeFont().getFamily(), TimerFont.RUNESCAPE.font(false, 16).getFamily());
		}

		@Test
		void runescapeSmallIsMadeBoldOnRequest()
		{
			Font font = TimerFont.RUNESCAPE_SMALL.font(true, 16);

			assertEquals(FontManager.getRunescapeSmallFont().getFamily(), font.getFamily());
			assertTrue(font.isBold());
		}

		@Test
		void logicalFontsFollowTheBoldSetting()
		{
			assertTrue(TimerFont.MONOSPACED.font(true, 16).isBold());
			assertFalse(TimerFont.MONOSPACED.font(false, 16).isBold());
			assertEquals(Font.MONOSPACED, TimerFont.MONOSPACED.font(false, 16).getName());
		}

		@Test
		void appliesTheSize()
		{
			for (TimerFont type : TimerFont.values())
			{
				assertEquals(30, type.font(true, 30).getSize(), type.name());
			}
		}
	}
}
