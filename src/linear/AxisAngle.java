package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class AxisAngle implements Rotation
{
	private float angleRad;
	private Vector3f axis;

	public AxisAngle(Vector3f axis, float angleRad)
	{
		this.angleRad = angleRad;
		this.axis = new Vector3f(axis);
	}

	/**
	 * Overrides/ Implementations
	 */
	@Override
	public void fromAxisAngle(AxisAngle aa)
	{
		this.axis = new Vector3f(aa.axis);
		this.angleRad = aa.angleRad;
	}

	@Override
	public AxisAngle toAxisAngle()
	{

		return this;
	}

	@Override
	public Rotation copy()
	{

		return new AxisAngle(axis, angleRad);
	}

	@Override
	public String toString()
	{
		String s = "Axis Angle:\tAxis: " + Vector.round(axis, 2, null) + "\tAngle: " + angleRad;
		return s;
	}

	/**
	 * Getters and Setters
	 */
	public float getAngleRad()
	{
		return angleRad;
	}

	public Vector3f getAxis()
	{
		return axis.normalize();
	}

	public void setAxis(Vector3f axis)
	{
		this.axis = axis;
	}

	public void setAngleRad(float angleRad)
	{
		this.angleRad = angleRad;
	}
}
