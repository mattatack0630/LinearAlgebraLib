package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Vector3f extends Vector
{
	/**
	 * Constructors
	 */
	public Vector3f()
	{
		this(0, 0, 0);
	}

	public Vector3f(float s)
	{
		this(s, s, s);
	}

	protected Vector3f(float[] elements)
	{
		super(3);
		this.setElements(elements);
	}

	public Vector3f(float x, float y, float z)
	{
		super(3);
		this.setElements(new float[]{x, y, z});
	}

	public Vector3f(Vector3f src)
	{
		this(src.elements[0]);
	}

	/**
	 * Vec3 specific
	 */
	public float x()
	{
		return elements[0][0];
	}

	public float y()
	{
		return elements[0][1];
	}

	public float z()
	{
		return elements[0][2];
	}

	public void setX(float x)
	{
		elements[0][0] = x;
		elementsChanged = true;
	}

	public void setY(float y)
	{
		elements[0][1] = y;
		elementsChanged = true;
	}

	public void setZ(float z)
	{
		elements[0][2] = z;
		elementsChanged = true;
	}

	public void set(float x, float y, float z)
	{
		elements[0][0] = x;
		elements[0][1] = y;
		elements[0][2] = z;
		elementsChanged = true;
	}

	/**
	 * Instance Manipulation Methods
	 */
	public Vector3f cross(Vector3f other)
	{
		return Vector3f.cross(this, other, this);
	}

	/**
	 * Static Methods
	 */
	public static Vector3f cross(Vector3f left, Vector3f right, Vector3f dest)
	{
		float x = (left.elements[0][1] * right.elements[0][2]) - (left.elements[0][2] * right.elements[0][1]);
		float y = (left.elements[0][2] * right.elements[0][0]) - (left.elements[0][0] * right.elements[0][2]);
		float z = (left.elements[0][0] * right.elements[0][1]) - (left.elements[0][1] * right.elements[0][0]);

		dest.set(x, y, z);
		dest.elementsChanged = true;

		return dest;
	}
}
