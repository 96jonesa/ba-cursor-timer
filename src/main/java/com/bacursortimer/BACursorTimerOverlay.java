package com.bacursortimer;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;
import net.runelite.client.util.ColorUtil;

class BACursorTimerOverlay extends Overlay
{
	private static final int BACKGROUND_PADDING = 2;

	private final Client client;
	private final BACursorTimerPlugin plugin;
	private final BACursorTimerConfig config;

	private Font font;
	private TimerFont fontType;
	private boolean fontBold;
	private int fontSize;

	@Inject
	private BACursorTimerOverlay(Client client, BACursorTimerPlugin plugin, BACursorTimerConfig config)
	{
		super(plugin);
		setPosition(OverlayPosition.DYNAMIC);
		// Drawn in the client's final pass, after interfaces, the right-click menu
		// and tooltips, and last among the cursor-following overlays there
		setLayer(OverlayLayer.ALWAYS_ON_TOP);
		setPriority(PRIORITY_HIGHEST);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		WaveClock clock = plugin.getWaveClock();
		if (!clock.isRunning())
		{
			return null;
		}

		// (-1, -1) while the cursor is outside the game canvas
		Point mouse = client.getMouseCanvasPosition();
		if (mouse.getX() < 0 || mouse.getY() < 0)
		{
			return null;
		}

		String text = WaveClock.format(clock.elapsedTicks(client.getTickCount()));

		graphics.setFont(font());
		FontMetrics metrics = graphics.getFontMetrics();
		// Center the text vertically on the cursor, then apply the configured offset
		int x = mouse.getX() + config.offsetX();
		int y = mouse.getY() + config.offsetY() + (metrics.getAscent() - metrics.getDescent()) / 2;

		if (config.showBackground())
		{
			int alpha = Math.round(config.backgroundOpacity() * 255 / 100f);
			graphics.setColor(ColorUtil.colorWithAlpha(config.backgroundColor(), alpha));
			graphics.fill(backgroundBounds(x, y, metrics, text));
		}

		OverlayUtil.renderTextLocation(graphics, new Point(x, y), text, config.textColor());
		return null;
	}

	/**
	 * The rectangle behind text drawn with its baseline at ({@code x}, {@code baseline}),
	 * covering the text and its one-pixel drop shadow plus padding on every side.
	 */
	static Rectangle backgroundBounds(int x, int baseline, FontMetrics metrics, String text)
	{
		return new Rectangle(
			x - BACKGROUND_PADDING,
			baseline - metrics.getAscent() - BACKGROUND_PADDING,
			metrics.stringWidth(text) + 1 + 2 * BACKGROUND_PADDING,
			metrics.getAscent() + metrics.getDescent() + 1 + 2 * BACKGROUND_PADDING);
	}

	private Font font()
	{
		TimerFont type = config.font();
		boolean bold = config.bold();
		int size = config.fontSize();
		if (font == null || type != fontType || bold != fontBold || size != fontSize)
		{
			font = type.font(bold, size);
			fontType = type;
			fontBold = bold;
			fontSize = size;
		}
		return font;
	}
}
