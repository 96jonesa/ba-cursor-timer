package com.bacursortimer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

class WaveClockTest
{
	@Test
	void isNotRunningUntilStarted()
	{
		assertFalse(new WaveClock().isRunning());
	}

	@Test
	void runsFromStartUntilStop()
	{
		WaveClock clock = new WaveClock();
		clock.start(100);
		assertTrue(clock.isRunning());

		clock.stop();
		assertFalse(clock.isRunning());
	}

	@Test
	void restartingResetsTheCount()
	{
		WaveClock clock = new WaveClock();
		clock.start(100);
		clock.start(250);

		assertEquals(5, clock.elapsedTicks(255));
	}

	@Nested
	class TestElapsedTicks
	{
		@Test
		void countsTicksSinceStart()
		{
			WaveClock clock = new WaveClock();
			clock.start(1000);

			assertEquals(0, clock.elapsedTicks(1000));
			assertEquals(1, clock.elapsedTicks(1001));
			assertEquals(50, clock.elapsedTicks(1050));
		}

		@Test
		void neverGoesNegative()
		{
			WaveClock clock = new WaveClock();
			clock.start(1000);

			assertEquals(0, clock.elapsedTicks(999));
		}
	}

	@Nested
	class TestFormat
	{
		@Test
		void startsAtZero()
		{
			assertEquals("0.0", WaveClock.format(0));
		}

		@Test
		void stepsBySixTenthsPerTick()
		{
			assertEquals("0.6", WaveClock.format(1));
			assertEquals("1.2", WaveClock.format(2));
			assertEquals("1.8", WaveClock.format(3));
			assertEquals("2.4", WaveClock.format(4));
			assertEquals("3.0", WaveClock.format(5));
		}

		@Test
		void growsDigitsInsteadOfRollingOverIntoMinutes()
		{
			assertEquals("9.6", WaveClock.format(16));
			assertEquals("10.2", WaveClock.format(17));
			assertEquals("99.6", WaveClock.format(166));
			assertEquals("100.2", WaveClock.format(167));
			assertEquals("154.2", WaveClock.format(257));
		}
	}
}
