package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Matrix4f extends SquareMatrix
{
	/**
	 * Constructors
	 */
	public Matrix4f()
	{
		super(4);
	}

	public Matrix4f(Matrix4f src)
	{

		super(src);
	}

	/**
	 * Faster, 4x4 specific method
	 */
	public Matrix4f invertMatrix4f()
	{
		return Matrix4f.invert(this, this);
	}

	/**
	 * Faster, 4x4 specific method
	 */
	public float determinantMatrix4f()
	{
		return Matrix4f.determinant(this);
	}

	/**
	 * Static Methods
	 **/
	public static Matrix4f invert(Matrix4f src, Matrix4f dest)
	{
		float determinant = determinant(src);

		if (determinant != 0.0F)
		{
			float determinant_inv = 1.0F / determinant;
			float t00 = determinant3x3(src.elements[1][1], src.elements[1][2], src.elements[1][3], src.elements[2][1], src.elements[2][2], src.elements[2][3], src.elements[3][1], src.elements[3][2], src.elements[3][3]);
			float t01 = -determinant3x3(src.elements[1][0], src.elements[1][2], src.elements[1][3], src.elements[2][0], src.elements[2][2], src.elements[2][3], src.elements[3][0], src.elements[3][2], src.elements[3][3]);
			float t02 = determinant3x3(src.elements[1][0], src.elements[1][1], src.elements[1][3], src.elements[2][0], src.elements[2][1], src.elements[2][3], src.elements[3][0], src.elements[3][1], src.elements[3][3]);
			float t03 = -determinant3x3(src.elements[1][0], src.elements[1][1], src.elements[1][2], src.elements[2][0], src.elements[2][1], src.elements[2][2], src.elements[3][0], src.elements[3][1], src.elements[3][2]);
			float t10 = -determinant3x3(src.elements[0][1], src.elements[0][2], src.elements[0][3], src.elements[2][1], src.elements[2][2], src.elements[2][3], src.elements[3][1], src.elements[3][2], src.elements[3][3]);
			float t11 = determinant3x3(src.elements[0][0], src.elements[0][2], src.elements[0][3], src.elements[2][0], src.elements[2][2], src.elements[2][3], src.elements[3][0], src.elements[3][2], src.elements[3][3]);
			float t12 = -determinant3x3(src.elements[0][0], src.elements[0][1], src.elements[0][3], src.elements[2][0], src.elements[2][1], src.elements[2][3], src.elements[3][0], src.elements[3][1], src.elements[3][3]);
			float t13 = determinant3x3(src.elements[0][0], src.elements[0][1], src.elements[0][2], src.elements[2][0], src.elements[2][1], src.elements[2][2], src.elements[3][0], src.elements[3][1], src.elements[3][2]);
			float t20 = determinant3x3(src.elements[0][1], src.elements[0][2], src.elements[0][3], src.elements[1][1], src.elements[1][2], src.elements[1][3], src.elements[3][1], src.elements[3][2], src.elements[3][3]);
			float t21 = -determinant3x3(src.elements[0][0], src.elements[0][2], src.elements[0][3], src.elements[1][0], src.elements[1][2], src.elements[1][3], src.elements[3][0], src.elements[3][2], src.elements[3][3]);
			float t22 = determinant3x3(src.elements[0][0], src.elements[0][1], src.elements[0][3], src.elements[1][0], src.elements[1][1], src.elements[1][3], src.elements[3][0], src.elements[3][1], src.elements[3][3]);
			float t23 = -determinant3x3(src.elements[0][0], src.elements[0][1], src.elements[0][2], src.elements[1][0], src.elements[1][1], src.elements[1][2], src.elements[3][0], src.elements[3][1], src.elements[3][2]);
			float t30 = -determinant3x3(src.elements[0][1], src.elements[0][2], src.elements[0][3], src.elements[1][1], src.elements[1][2], src.elements[1][3], src.elements[2][1], src.elements[2][2], src.elements[2][3]);
			float t31 = determinant3x3(src.elements[0][0], src.elements[0][2], src.elements[0][3], src.elements[1][0], src.elements[1][2], src.elements[1][3], src.elements[2][0], src.elements[2][2], src.elements[2][3]);
			float t32 = -determinant3x3(src.elements[0][0], src.elements[0][1], src.elements[0][3], src.elements[1][0], src.elements[1][1], src.elements[1][3], src.elements[2][0], src.elements[2][1], src.elements[2][3]);
			float t33 = determinant3x3(src.elements[0][0], src.elements[0][1], src.elements[0][2], src.elements[1][0], src.elements[1][1], src.elements[1][2], src.elements[2][0], src.elements[2][1], src.elements[2][2]);
			dest.elements[0][0] = t00 * determinant_inv;
			dest.elements[1][1] = t11 * determinant_inv;
			dest.elements[2][2] = t22 * determinant_inv;
			dest.elements[3][3] = t33 * determinant_inv;
			dest.elements[0][1] = t10 * determinant_inv;
			dest.elements[1][0] = t01 * determinant_inv;
			dest.elements[2][0] = t02 * determinant_inv;
			dest.elements[0][2] = t20 * determinant_inv;
			dest.elements[1][2] = t21 * determinant_inv;
			dest.elements[2][1] = t12 * determinant_inv;
			dest.elements[0][3] = t30 * determinant_inv;
			dest.elements[3][0] = t03 * determinant_inv;
			dest.elements[1][3] = t31 * determinant_inv;
			dest.elements[3][1] = t13 * determinant_inv;
			dest.elements[3][2] = t23 * determinant_inv;
			dest.elements[2][3] = t32 * determinant_inv;
		}

		dest.elementsChanged = true;
		return dest;
	}

	public static float determinant(Matrix4f src)
	{
		float f = src.elements[0][0] * (src.elements[1][1] * src.elements[2][2] * src.elements[3][3] + src.elements[1][2] * src.elements[2][3] * src.elements[3][1] + src.elements[1][3] * src.elements[2][1] * src.elements[3][2] - src.elements[1][3] * src.elements[2][2] * src.elements[3][1] - src.elements[1][1] * src.elements[2][3] * src.elements[3][2] - src.elements[1][2] * src.elements[2][1] * src.elements[3][3]);
		f -= src.elements[0][1] * (src.elements[1][0] * src.elements[2][2] * src.elements[3][3] + src.elements[1][2] * src.elements[2][3] * src.elements[3][0] + src.elements[1][3] * src.elements[2][0] * src.elements[3][2] - src.elements[1][3] * src.elements[2][2] * src.elements[3][0] - src.elements[1][0] * src.elements[2][3] * src.elements[3][2] - src.elements[1][2] * src.elements[2][0] * src.elements[3][3]);
		f += src.elements[0][2] * (src.elements[1][0] * src.elements[2][1] * src.elements[3][3] + src.elements[1][1] * src.elements[2][3] * src.elements[3][0] + src.elements[1][3] * src.elements[2][0] * src.elements[3][1] - src.elements[1][3] * src.elements[2][1] * src.elements[3][0] - src.elements[1][0] * src.elements[2][3] * src.elements[3][1] - src.elements[1][1] * src.elements[2][0] * src.elements[3][3]);
		f -= src.elements[0][3] * (src.elements[1][0] * src.elements[2][1] * src.elements[3][2] + src.elements[1][1] * src.elements[2][2] * src.elements[3][0] + src.elements[1][2] * src.elements[2][0] * src.elements[3][1] - src.elements[1][2] * src.elements[2][1] * src.elements[3][0] - src.elements[1][0] * src.elements[2][2] * src.elements[3][1] - src.elements[1][1] * src.elements[2][0] * src.elements[3][2]);
		return f;
	}

	private static float determinant3x3(float t00, float t01, float t02, float t10, float t11, float t12, float t20, float t21, float t22)
	{
		return t00 * (t11 * t22 - t12 * t21) + t01 * (t12 * t20 - t10 * t22) + t02 * (t10 * t21 - t11 * t20);
	}

	public static Matrix4f scale(Matrix4f src, Vector3f scale, Matrix4f dest)
	{
		Matrix4f m = new Matrix4f(src);

		for (int i = 0; i < dest.width - 1; i++)
			for (int j = 0; j < dest.height; j++)
				m.elements[i][j] = src.elements[i][j] * scale.elements[0][i];

		dest.load(m.elements, m.width, m.height);
		dest.elementsChanged = true;

		return dest;
	}

	public static Matrix4f translate(Matrix4f src, Vector3f tran, Matrix4f dest)
	{
		Matrix4f m = new Matrix4f(src);
		Vector4f vec4f = new Vector4f(tran.x(), tran.y(), tran.z(), 1.0f);

		for (int i = 0; i < m.height; i++)
			m.elements[3][i] = Vector.dot(new Vector(src.getRow(i)), vec4f);

		dest.load(m.elements, m.width, m.height);
		dest.elementsChanged = true;

		return dest;
	}

	public static Matrix4f rotate(Matrix4f src, AxisAngle axisAngle, Matrix4f dest)
	{
		Vector3f axis = axisAngle.getAxis();
		float angle = axisAngle.getAngleRad();

		Matrix4f m = new Matrix4f(src);

		float c = (float) Math.cos((double) angle);
		float s = (float) Math.sin((double) angle);
		float oneminusc = 1.0F - c;
		float xy = axis.x() * axis.y();
		float yz = axis.y() * axis.z();
		float xz = axis.x() * axis.z();
		float xs = axis.x() * s;
		float ys = axis.y() * s;
		float zs = axis.z() * s;
		float f00 = axis.x() * axis.x() * oneminusc + c;
		float f01 = xy * oneminusc + zs;
		float f02 = xz * oneminusc - ys;
		float f10 = xy * oneminusc - zs;
		float f11 = axis.y() * axis.y() * oneminusc + c;
		float f12 = yz * oneminusc + xs;
		float f20 = xz * oneminusc + ys;
		float f21 = yz * oneminusc - xs;
		float f22 = axis.z() * axis.z() * oneminusc + c;
		float t00 = src.elements[0][0] * f00 + src.elements[1][0] * f01 + src.elements[2][0] * f02;
		float t01 = src.elements[0][1] * f00 + src.elements[1][1] * f01 + src.elements[2][1] * f02;
		float t02 = src.elements[0][2] * f00 + src.elements[1][2] * f01 + src.elements[2][2] * f02;
		float t03 = src.elements[0][3] * f00 + src.elements[1][3] * f01 + src.elements[2][3] * f02;
		float t10 = src.elements[0][0] * f10 + src.elements[1][0] * f11 + src.elements[2][0] * f12;
		float t11 = src.elements[0][1] * f10 + src.elements[1][1] * f11 + src.elements[2][1] * f12;
		float t12 = src.elements[0][2] * f10 + src.elements[1][2] * f11 + src.elements[2][2] * f12;
		float t13 = src.elements[0][3] * f10 + src.elements[1][3] * f11 + src.elements[2][3] * f12;
		m.elements[2][0] = src.elements[0][0] * f20 + src.elements[1][0] * f21 + src.elements[2][0] * f22;
		m.elements[2][1] = src.elements[0][1] * f20 + src.elements[1][1] * f21 + src.elements[2][1] * f22;
		m.elements[2][2] = src.elements[0][2] * f20 + src.elements[1][2] * f21 + src.elements[2][2] * f22;
		m.elements[2][3] = src.elements[0][3] * f20 + src.elements[1][3] * f21 + src.elements[2][3] * f22;
		m.elements[0][0] = t00;
		m.elements[0][1] = t01;
		m.elements[0][2] = t02;
		m.elements[0][3] = t03;
		m.elements[1][0] = t10;
		m.elements[1][1] = t11;
		m.elements[1][2] = t12;
		m.elements[1][3] = t13;

		dest.load(m.elements, m.width, m.height);
		dest.elementsChanged = true;

		return dest;
	}

	public static Matrix4f rotate(Matrix4f left, Rotation rotation, Matrix4f dest)
	{
		dest.elementsChanged = true;
		Matrix4f rotMat = rotation.toRotationMatrix();
		return multDot(left, rotMat, dest);
	}

	/**
	 * Static Methods
	 */
	public static Vector[] decompose(Matrix4f src)
	{
		Matrix4f nMat = new Matrix4f();
		nMat.load(src.elements, 4, 4);

		// Position Component
		Vector3f ePos = new Vector3f(nMat.getColumn(3));
		nMat.translate(nMat, new Vector3f(-ePos.x(), -ePos.y(), -ePos.z()), nMat);

		// Scale Component
		Vector3f eScale = new Vector3f(new Vector3f(nMat.getColumn(0)).length(),
				new Vector3f(nMat.getColumn(1)).length(),
				new Vector3f(nMat.getColumn(2)).length());

		Matrix4f.scale(nMat, new Vector3f(1f / eScale.x(), 1f / eScale.y(), 1f / eScale.z()), nMat);

		// Rotation Component
		Quaternion eRot = new Quaternion();
		eRot.elements[0][3] = (float) Math.sqrt(Math.max(0, 1 + nMat.elements[0][0] + nMat.elements[1][1] + nMat.elements[2][2])) / 2;
		eRot.elements[0][0] = (float) Math.sqrt(Math.max(0, 1 + nMat.elements[0][0] - nMat.elements[1][1] - nMat.elements[2][2])) / 2;
		eRot.elements[0][1] = (float) Math.sqrt(Math.max(0, 1 - nMat.elements[0][0] + nMat.elements[1][1] - nMat.elements[2][2])) / 2;
		eRot.elements[0][2] = (float) Math.sqrt(Math.max(0, 1 - nMat.elements[0][0] - nMat.elements[1][1] + nMat.elements[2][2])) / 2;
		eRot.elements[0][0] *= Maths.getSign(nMat.elements[1][2] - nMat.elements[2][1]);
		eRot.elements[0][1] *= Maths.getSign(nMat.elements[2][0] - nMat.elements[0][2]);
		eRot.elements[0][2] *= Maths.getSign(nMat.elements[0][1] - nMat.elements[1][0]);
		eRot.normalize();

		return new Vector[]{ePos, eRot, eScale};
	}
}
