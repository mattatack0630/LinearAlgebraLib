package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Vector extends Matrix
{

	public Vector(int i)
	{

		super(1, i);
	}

	public Vector(float[] elements)
	{
		super(1, elements.length);
		this.setElements(elements);
	}

	public Vector(float[][] elements)
	{

		super(elements, 1, elements[0].length);
	}

	/**
	 * Getters and Setters
	 */
	public void set(Vector src)
	{
		if (src.height == height)
			for (int i = 0; i < src.height; i++)
				elements[0][i] = src.elements[0][i];
	}

	protected void setElement(int i, float v)
	{
		if (i < height)
		{
			elements[0][i] = v;
		}
	}

	protected void setElements(float[] elements)
	{
		for (int i = 0; i < height; i++)
			this.elements[0][i] = elements[i];
	}

	protected int elementsSize()
	{

		return height;
	}

	/**
	 * Instance Manipulation Methods
	 */
	public float dot(Vector other)
	{

		return Vector.dot(this, other);
	}

	public <E extends Vector> E normalize()
	{
		try
		{
			float l = length();
			if (l != 0 && l != 1)
				scale(1f / l);
			// avoid div by 0 and already normal
			return (E) this;
		} catch (ClassCastException | NullPointerException e)
		{
			return null;
		}
	}

	public <E extends Vector> E setMagnitude(float magnitude)
	{
		try
		{
			/** important stuff **/
			normalize();
			scale(magnitude);
			return (E) this;
			/********************/
		} catch (ClassCastException | NullPointerException e)
		{
			return null;
		}
	}

	public float angle(Vector other)
	{

		return Vector.angle(this, other);
	}

	public float lengthSquared()
	{
		float l = 0;
		for (int i = 0; i < elementsSize(); i++)
			l += (elements[0][i] * elements[0][i]);
		return l;
	}

	public float length()
	{

		return (float) Math.sqrt(lengthSquared());
	}

	/**
	 * Static Methods
	 **/
	public static float dot(Vector left, Vector right)
	{
		MatrixError.checkSame(left, right);

		float d = 0;

		for (int i = 0; i < left.height; i++)
			d += (left.elements[0][i] * right.elements[0][i]);

		return d;
	}

	public static float angle(Vector left, Vector right)
	{
		MatrixError.checkSame(left, right);

		float dls = Vector.dot(left, right) / (left.length() * right.length());

		dls = Maths.clamp(dls, -1f, 1f);

		return (float) Math.acos(dls);
	}
}
