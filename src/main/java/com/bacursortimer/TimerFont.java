package com.bacursortimer;

import java.awt.Font;
import lombok.RequiredArgsConstructor;
import net.runelite.client.ui.FontManager;

@RequiredArgsConstructor
public enum TimerFont
{
	RUNESCAPE("RuneScape")
	{
		@Override
		Font base(boolean bold)
		{
			// The game ships a separate bold face, so use it rather than a synthetic bold
			return bold ? FontManager.getRunescapeBoldFont() : FontManager.getRunescapeFont();
		}
	},
	RUNESCAPE_SMALL("RuneScape Small")
	{
		@Override
		Font base(boolean bold)
		{
			return FontManager.getRunescapeSmallFont().deriveFont(style(bold));
		}
	},
	SANS_SERIF("Sans-serif")
	{
		@Override
		Font base(boolean bold)
		{
			return new Font(Font.SANS_SERIF, style(bold), BASE_SIZE);
		}
	},
	SERIF("Serif")
	{
		@Override
		Font base(boolean bold)
		{
			return new Font(Font.SERIF, style(bold), BASE_SIZE);
		}
	},
	MONOSPACED("Monospaced")
	{
		@Override
		Font base(boolean bold)
		{
			return new Font(Font.MONOSPACED, style(bold), BASE_SIZE);
		}
	};

	private static final int BASE_SIZE = 16;

	private final String name;

	/**
	 * The font to draw the timer in, before the configured size is applied.
	 */
	abstract Font base(boolean bold);

	Font font(boolean bold, int size)
	{
		return base(bold).deriveFont((float) size);
	}

	private static int style(boolean bold)
	{
		return bold ? Font.BOLD : Font.PLAIN;
	}

	@Override
	public String toString()
	{
		return name;
	}
}
