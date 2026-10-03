package com.bacursortimer;

import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Point;
import net.runelite.client.ui.FontManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayUtil;

class BACursorTimerOverlay extends Overlay
{
	private final Client client;
	private final BACursorTimerPlugin plugin;
	private final BACursorTimerConfig config;

	@Inject
	private BACursorTimerOverlay(Client client, BACursorTimerPlugin plugin, BACursorTimerConfig config)
	{
		super(plugin);
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(PRIORITY_HIGH);
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

		graphics.setFont(FontManager.getRunescapeBoldFont());
		FontMetrics metrics = graphics.getFontMetrics();
		// Center the text vertically on the cursor, then apply the configured offset
		int x = mouse.getX() + config.offsetX();
		int y = mouse.getY() + config.offsetY() + (metrics.getAscent() - metrics.getDescent()) / 2;

		OverlayUtil.renderTextLocation(graphics, new Point(x, y), text, config.textColor());
		return null;
	}
}
