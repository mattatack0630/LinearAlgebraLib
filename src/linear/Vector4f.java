package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Vector4f extends Vector
{
	/**
	 * Constructors
	 */
	public Vector4f(float x, float y, float z, float w)
	{
		super(4);
		this.setElements(new float[]{x, y, z, w});
	}

	public Vector4f()
	{
		this(0, 0, 0, 0);
	}

	public Vector4f(float s)
	{
		this(s, s, s, s);
	}

	public Vector4f(Vector4f src)
	{
		this(src.x(), src.y(), src.z(), src.w());
	}

	/**
	 * Vec4 Specific Methods
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

	public float w()
	{
		return elements[0][3];
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

	public void setW(float w)
	{
		elements[0][3] = w;
		elementsChanged = true;
	}

	public void set(float x, float y, float z, float w)
	{
		elements[0][0] = x;
		elements[0][1] = y;
		elements[0][2] = z;
		elements[0][3] = w;
		elementsChanged = true;
	}

	public void set(Vector4f v)
	{
		elements[0][0] = v.elements[0][0];
		elements[0][1] = v.elements[0][1];
		elements[0][2] = v.elements[0][2];
		elements[0][3] = v.elements[0][3];
		elementsChanged = true;
	}
}
