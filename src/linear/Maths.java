package linear;

import java.util.Random;

public class Maths
{

	private static Random random = new Random();

	public static float getSign(float num)
	{
		if (num >= 0)
			return 1;
		else
			return -1;
	}

	public static float round(float d, int i)
	{
		float tens = (float) Math.pow(10, i);
		return Math.round(d * tens) / tens;
	}

	public static float clamp(float n, float min, float max)
	{
		if (n < min)
			n = min;
		else if (n > max)
			n = max;
		return n;
	}

	public static int clamp(int n, int min, int max)
	{
		if (n < min)
			n = min;
		else if (n > max)
			n = max;
		return n;
	}

	public static float random(float f0, float f1)
	{

		return map(random.nextFloat(), 0, 1, f0, f1);
	}

	public static int randomi(int i0, int i1)
	{

		return (int) map(random.nextFloat(), 0, 1, i0, i1);
	}

	public static float map(float s, float a0, float a1, float b0, float b1)
	{
		return (s - a0) / (a1 - a0) * (b1 - b0) + b0;
	}

	public static float squared(float v)
	{

		return v * v;
	}

	public static float max(float... numbers)
	{
		float largest = numbers[0];

		for (float n : numbers)
			if (n > largest)
				largest = n;

		return largest;
	}

	public static int max(int... numbers)
	{
		int largest = numbers[0];

		for (int n : numbers)
			if (n > largest)
				largest = n;

		return largest;
	}

	public static float min(float... numbers)
	{
		float largest = numbers[0];

		for (float n : numbers)
			if (n < largest)
				largest = n;

		return largest;
	}

	public static int min(int... numbers)
	{
		int largest = numbers[0];

		for (int n : numbers)
			if (n < largest)
				largest = n;

		return largest;
	}

	public static float abs(float v)
	{

		return v < 0 ? v * -1 : v;
	}
}
