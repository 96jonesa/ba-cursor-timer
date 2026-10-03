package com.bacursortimer;

/**
 * Wave duration measured in game ticks, so the displayed time moves in exact
 * 0.6 second steps in lockstep with the server rather than drifting with wall-clock time.
 */
class WaveClock
{
	private static final int NOT_RUNNING = -1;

	private int startTick = NOT_RUNNING;

	void start(int tick)
	{
		startTick = tick;
	}

	void stop()
	{
		startTick = NOT_RUNNING;
	}

	boolean isRunning()
	{
		return startTick != NOT_RUNNING;
	}

	int elapsedTicks(int currentTick)
	{
		return Math.max(0, currentTick - startTick);
	}

	/**
	 * Formats a tick count as whole seconds and tenths ({@code 4.2}, {@code 123.6}),
	 * never rolling over into minutes. A tick is exactly six tenths of a second, so
	 * integer arithmetic gives the exact value with no rounding.
	 */
	static String format(int ticks)
	{
		int tenths = ticks * 6;
		return (tenths / 10) + "." + (tenths % 10);
	}
}
