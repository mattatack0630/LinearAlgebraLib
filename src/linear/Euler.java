package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Euler implements Rotation
{
	public static final Vector3f XYZ = new Vector3f(0, 1, 2);
	public static final Vector3f XZY = new Vector3f(0, 2, 1);
	public static final Vector3f YXZ = new Vector3f(1, 0, 2);
	public static final Vector3f ZXY = new Vector3f(2, 0, 1);
	public static final Vector3f YZX = new Vector3f(1, 2, 0);
	public static final Vector3f ZYX = new Vector3f(2, 1, 0);

	private Vector3f eulerRad;
	private Vector3f order;

	public Euler(Vector3f eulerRad, Vector3f dir)
	{
		this.eulerRad = new Vector3f(eulerRad);
		this.order = dir;
	}

	public Euler(Vector3f eulerRad)
	{
		this(eulerRad, XYZ);
	}

	public Euler(float x, float y, float z)
	{
		this(new Vector3f(x, y, z), XYZ);
	}

	public Euler()
	{
		this(new Vector3f(), XYZ);
	}

	/**
	 * Overrides/ Implementations
	 */
	@Override
	public void fromAxisAngle(AxisAngle a)
	{
		Vector3f axis = a.getAxis();
		float angle = a.getAngleRad();

		float s = (float) Math.sin(angle);
		float c = (float) Math.cos(angle);
		float t = 1.0f - c;

		if ((axis.x() * axis.y() * t + axis.z() * s) > 0.998)
		{
			// north pole singularity detected
			eulerRad.elements[0][1] = (float) (2 * Math.atan2(axis.x() * Math.sin(angle / 2), Math.cos(angle / 2)));
			eulerRad.elements[0][2] = (float) (Math.PI / 2);
			eulerRad.elements[0][0] = 0.0f;
			return;
		}

		if ((axis.x() * axis.y() * t + axis.z() * s) < -0.998)
		{
			// south pole singularity detected
			eulerRad.elements[0][1] = (float) (-2 * Math.atan2(axis.x() * Math.sin(angle / 2), Math.cos(angle / 2)));
			eulerRad.elements[0][2] = (float) (-Math.PI / 2);
			eulerRad.elements[0][0] = 0.0f;
			return;
		}

		eulerRad.elements[0][0] = (float) Math.atan2(axis.x() * s - axis.y() * axis.z() * t,
				1 - (axis.x() * axis.x() + axis.z() * axis.z()) * t);
		eulerRad.elements[0][1] = (float) Math.atan2(axis.y() * s - axis.x() * axis.z() * t,
				1 - (axis.y() * axis.y() + axis.z() * axis.z()) * t);
		eulerRad.elements[0][2] = (float) Math.asin(axis.x() * axis.y() * t + axis.z() * s);

		eulerRad.elementsChanged = true;
	}

	@Override
	public AxisAngle toAxisAngle()
	{
		// Assuming the angles are in radians.
		double c1 = Math.cos(eulerRad.y() / 2f);
		double s1 = Math.sin(eulerRad.y() / 2f);
		double c2 = Math.cos(eulerRad.z() / 2f);
		double s2 = Math.sin(eulerRad.z() / 2f);
		double c3 = Math.cos(eulerRad.x() / 2f);
		double s3 = Math.sin(eulerRad.x() / 2f);
		double c1c2 = c1 * c2;
		double s1s2 = s1 * s2;
		float w = (float) (c1c2 * c3 - s1s2 * s3);
		float x = (float) (c1c2 * s3 + s1s2 * c3);
		float y = (float) (s1 * c2 * c3 + c1 * s2 * s3);
		float z = (float) (c1 * s2 * c3 - s1 * c2 * s3);
		float angle = (float) (2 * Math.acos(w));
		double norm = x * x + y * y + z * z;
		if (norm < 0.001)
		{ // when all euler angles are zero angle =0 so
			// we can setElements axis to anything to avoid divide by zero
			x = 1;
			y = z = 0;
		} else
		{
			norm = Math.sqrt(norm);
			x /= norm;
			y /= norm;
			z /= norm;
		}

		return new AxisAngle(new Vector3f(x, y, z), angle);
	}

	@Override
	public Matrix4f toRotationMatrix()
	{
		AxisAngle aa = toAxisAngle();
		return aa.toRotationMatrix();
	}

	@Override
	public Rotation copy()
	{

		return new Euler(eulerRad, order);
	}

	@Override
	public String toString()
	{
		String s = "linear.Euler: ";
		s += "Rad[" +
				Maths.round(eulerRad.x(), 2) + ", " +
				Maths.round(eulerRad.y(), 2) + ", " +
				Maths.round(eulerRad.z(), 2) + "]\t" + "Deg[" +
				Maths.round((float) Math.toDegrees(eulerRad.x()), 2) + ", " +
				Maths.round((float) Math.toDegrees(eulerRad.y()), 2) + ", " +
				Maths.round((float) Math.toDegrees(eulerRad.z()), 2) + "]";
		return s;
	}

	/**
	 * Getters and Setters
	 */
	public Vector3f getOrder()
	{
		return order;
	}

	public Vector3f getEulerRad()
	{
		return eulerRad;
	}

	public void setOrder(Vector3f order)
	{
		this.order = order;
	}

	public void setEulerRad(Vector3f eulerRad)
	{
		this.eulerRad = eulerRad;
	}
}
