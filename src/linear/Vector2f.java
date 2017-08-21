package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Vector2f extends Vector
{
	/**
	 * Constructors
	 */
	public Vector2f()
	{

		this(0, 0);
	}

	public Vector2f(float s)
	{

		this(s, s);
	}

	public Vector2f(float x, float y)
	{
		super(2);
		this.setElements(new float[]{x, y});
	}

	public Vector2f(Vector2f src)
	{

		super(src.elements);
	}

	/**
	 * Vec2 Specific Methods
	 */
	public float x()
	{
		return elements[0][0];
	}

	public float y()
	{
		return elements[0][1];
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

	public void set(float x, float y)
	{
		elements[0][0] = x;
		elements[0][1] = y;
		elementsChanged = true;
	}
}
