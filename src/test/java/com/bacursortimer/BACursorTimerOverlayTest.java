package com.bacursortimer;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class BACursorTimerOverlayTest
{
	private static FontMetrics metrics()
	{
		Graphics2D graphics = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB).createGraphics();
		return graphics.getFontMetrics(TimerFont.RUNESCAPE.font(true, 16));
	}

	@Nested
	class TestBackgroundBounds
	{
		@Test
		void coversTheTextAndItsShadowWithPadding()
		{
			FontMetrics metrics = metrics();
			Rectangle bounds = BACursorTimerOverlay.backgroundBounds(100, 50, metrics, "12.6");

			assertEquals(98, bounds.x);
			assertEquals(50 - metrics.getAscent() - 2, bounds.y);
			assertEquals(metrics.stringWidth("12.6") + 5, bounds.width);
			assertEquals(metrics.getAscent() + metrics.getDescent() + 5, bounds.height);
		}

		@Test
		void growsWithTheText()
		{
			FontMetrics metrics = metrics();

			assertTrue(BACursorTimerOverlay.backgroundBounds(0, 0, metrics, "100.2").width
				> BACursorTimerOverlay.backgroundBounds(0, 0, metrics, "9.6").width);
		}
	}
}
