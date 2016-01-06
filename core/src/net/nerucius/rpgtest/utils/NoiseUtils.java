package net.nerucius.rpgtest.utils;

import com.badlogic.gdx.math.MathUtils;

public class NoiseUtils {

	private static float base = 0.6f;
	private static float r;

	// Tune these to adjust flicker
	private static float min_cutoff = 0.25f;
	private static float max_cutoff = 1f;

	private static final float[] freq = {0.25f, 3.5f, 2.2f, 1.15f, 14.2f, 30f, 68.01f};
	private static final float[] ampl = {0.3f, 0.5f, 0.25f, 0.25f, 0.125f, 0.125f, 0.0625f};

	public static float flicker(float t) {
		return flicker(t, 0f);
	}

	public static float flicker(float t, float offset) {
		t = t + offset;
		r = base;
		for (int i = 0; i < freq.length; i++)
			r += ampl[i] * MathUtils.sinDeg(t * freq[i]);

		// Cutoff frequencies
		r = r > max_cutoff ? 1f : r;
		r = r < min_cutoff ? 0f : r;
		return r;
	}

}