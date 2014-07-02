package com.mackthehobbit.mbplugin.terrain;

/**
 * Fun fact: written for my Ludum Dare #29 entry
 * (at the last minute)
 */
public class Noise {
	
	private double persistence;
	private int octaves;
	
	public Noise(double persistence, int octaves) {
		this.persistence = persistence;
		this.octaves = octaves;
	}
	
	public static double noise(int x, int y) {
		int n = x + y * 57;
		n = (n << 13) ^ n;
		return (1.0 - ((n * (n * n * 15731 + 789221) + 1376312589) & 0x7fffffff) / 1073741824.0);
	}
	
	public static double smoothNoise(int x, int y) {
		double corners = (noise(x - 1, y - 1) + noise(x + 1, y - 1)
				+ noise(x - 1, y + 1) + noise(x + 1, y + 1)) / 16.0;
		double sides = (noise(x - 1, y) + noise(x + 1, y) + noise(x, y - 1) + noise(
				x, y + 1)) / 8.0;
		double center = noise(x, y) / 4.0;
		return corners + sides + center;
	}
	
	public static double interpolate(double a, double b, double x) {
		double ft = x * 3.1415927;
		double f = (1 - Math.cos(ft)) * .5;
		return a * (1 - f) + b * f;
	}
	
	public static double interpolatedNoise(double x, double y) {
		int intx = (int) x;
		double fracx = x - intx;
		int inty = (int) y;
		double fracy = y - inty;
		
		double v1 = smoothNoise(intx, inty);
		double v2 = smoothNoise(intx + 1, inty);
		double v3 = smoothNoise(intx, inty + 1);
		double v4 = smoothNoise(intx + 1, inty + 1);
		
		double i1 = interpolate(v1, v2, fracx);
		double i2 = interpolate(v3, v4, fracx);
		
		return interpolate(i1, i2, fracy);
	}
	
	public double perlinNoise(double x, double y) {
		double total = 0;
		for (int i = 0; i < this.octaves; i++) {
			int frequency = (int) Math.pow(2, i);
			int amplitude = (int) Math.pow(this.persistence, i);
			total += interpolatedNoise(x * frequency, y * frequency)
					* amplitude;
		}
		return total;
	}
}
