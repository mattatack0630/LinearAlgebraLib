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
	public Matrix4f toRotationMatrix()
	{
		Matrix4f rotMat = new Matrix4f();

		// Assumes normalized axis
		float x = axis.elements[0][0];
		float y = axis.elements[0][1];
		float z = axis.elements[0][2];
		float s = (float) Math.sin(angleRad);
		float c = (float) Math.cos(angleRad);
		float t = 1.0f - c;

		rotMat.elements[0][0] = t * x * x + c;
		rotMat.elements[0][1] = t * x * y + z * s;
		rotMat.elements[0][2] = t * x * z - y * s;

		rotMat.elements[1][0] = t * x * y - z * s;
		rotMat.elements[1][1] = t * y * y + c;
		rotMat.elements[1][2] = t * y * z + x * s;

		rotMat.elements[2][0] = t * x * z + y * s;
		rotMat.elements[2][1] = t * y * z - x * s;
		rotMat.elements[2][2] = t * z * z + c;

		rotMat.elements[3][3] = 1;

		rotMat.elementsChanged = true;

		return rotMat;
	}

	@Override
	public Rotation copy()
	{

		return new AxisAngle(axis, angleRad);
	}

	@Override
	public String toString()
	{
		String s = "Axis Angle:\nAxis:\n" + Vector.round(axis, 2, new Vector3f()) + "Angle: " + angleRad;
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
