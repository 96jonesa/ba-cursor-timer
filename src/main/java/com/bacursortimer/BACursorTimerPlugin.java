package com.bacursortimer;

import com.google.inject.Provides;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.gameval.VarbitID;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

@PluginDescriptor(
	name = "BA Cursor Timer",
	description = "Shows the current Barbarian Assault wave time, to the tick, next to the mouse cursor.",
	tags = {"barbarian", "assault", "minigame", "overlay", "timer", "tick", "mouse"}
)
public class BACursorTimerPlugin extends Plugin
{
	// Same wave-start and wave-end signals as the Barbarian Assault plugin's
	// "Wave N duration" chat message, so the two agree on a wave's length.
	private static final String WAVE_START_PREFIX = "---- Wave:";

	@Getter(AccessLevel.PACKAGE)
	private final WaveClock waveClock = new WaveClock();

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private BACursorTimerOverlay overlay;

	@Provides
	BACursorTimerConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(BACursorTimerConfig.class);
	}

	@Override
	protected void startUp()
	{
		overlayManager.add(overlay);
	}

	@Override
	protected void shutDown()
	{
		overlayManager.remove(overlay);
		waveClock.stop();
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (event.getType() == ChatMessageType.GAMEMESSAGE && event.getMessage().startsWith(WAVE_START_PREFIX))
		{
			// Counting from the message's own tick reads one tick ahead of the wave
			// (checked in game), so the wave's first tick is the one after it.
			// The clock reads 0.0 until then.
			waveClock.start(client.getTickCount() + 1);
		}
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		// Set while inside a wave, cleared on leaving it (completion, death or exiting)
		if (event.getVarbitId() == VarbitID.BARBASSAULT_AREAEXIT_PENDING && event.getValue() == 0)
		{
			waveClock.stop();
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGIN_SCREEN || event.getGameState() == GameState.HOPPING)
		{
			waveClock.stop();
		}
	}
}
