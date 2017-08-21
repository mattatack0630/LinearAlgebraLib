package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Quaternion extends Vector4f implements Rotation
{
	public Quaternion()
	{
		super(0, 0,0, 1);
		// no normalization needed
	}

	public Quaternion(float s)
	{
		super(s, s, s, s);
		normalize();
	}

	public Quaternion(Vector4f src)
	{
		this(src.x(), src.y(), src.z(), src.w());
		normalize();
	}

	public Quaternion(float x, float y, float z, float w)
	{
		super(x, y, z, w);
		normalize();
	}

	/**
	 * Overrides/ Implementations
	 */
	@Override
	public void fromAxisAngle(AxisAngle a)
	{
		Vector3f axis = a.getAxis();
		float s = (float) Math.sin(a.getAngleRad() / 2);
		elements[0][3] = (float) Math.cos(a.getAngleRad() / 2);
		elements[0][0] = axis.x() * s;
		elements[0][1] = axis.y() * s;
		elements[0][2] = axis.z() * s;
		elementsChanged = true;
	}

	@Override
	public AxisAngle toAxisAngle()
	{
		if (w() > 1)
			normalize();

		Vector3f axis = new Vector3f();
		float angle = (float) (2 * Math.acos(w()));
		float s = (float) Math.sqrt(1 - w() * w());
		if (s < 0.001)
		{
			axis.elements[0][0] = x();
			axis.elements[0][1] = y();
			axis.elements[0][2] = z();
		} else
		{
			axis.elements[0][1] = (y() / s);
			axis.elements[0][2] = (z() / s);
		}

		return new AxisAngle(axis, angle);
	}

	@Override
	public Matrix4f toRotationMatrix()
	{
		Matrix4f rotMat = new Matrix4f();

		float x = elements[0][0];
		float y = elements[0][1];
		float z = elements[0][2];
		float w = elements[0][3];

		rotMat.elements[0][0] = (1 - 2 * y * y) - (2 * z * z);
		rotMat.elements[0][1] = 2 * x * y + 2 * z * w;
		rotMat.elements[0][2] = 2 * x * z - 2 * y * w;

		rotMat.elements[1][0] = 2 * x * y - 2 * z * w;
		rotMat.elements[1][1] = (1 - 2 * x * x) - (2 * z * z);
		rotMat.elements[1][2] = 2 * y * z + 2 * x * w;

		rotMat.elements[2][0] = 2 * x * z + 2 * y * w;
		rotMat.elements[2][1] = 2 * y * z - 2 * x * w;
		rotMat.elements[2][2] = (1 - 2 * x * x) - (2 * y * y);

		rotMat.elements[3][3] = 1;

		rotMat.elementsChanged = true;

		return rotMat;
	}

	@Override
	public Rotation copy()
	{
		return new Quaternion(x(), y(), z(), w());
	}

	@Override
	public String toString()
	{
		String s = "Quaternion: ";
		s += "[(x)" + elements[0][0] + ", (y)" + elements[0][1] + ", (z)" + elements[0][2] + ", (w)" + elements[0][3] + "]";
		return s;
	}
}
