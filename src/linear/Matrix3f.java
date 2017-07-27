package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Matrix3f extends SquareMatrix
{
	/**
	 * Constructors
	 */
	public Matrix3f(Matrix3f src)
	{

		super(src);
	}

	public Matrix3f()
	{

		super(3);
	}

	/**
	 * Mat3 Specific Methods
	 */
	public static Matrix3f invert(Matrix3f src, Matrix3f dest)
	{
		float determinant = src.determinant();

		if (determinant != 0.0F)
		{
			if (dest == null)
			{
				dest = new Matrix3f();
			}

			float determinant_inv = 1.0F / determinant;
			float t00 = src.elements[1][1] * src.elements[2][2] - src.elements[1][2] * src.elements[2][1];
			float t01 = -src.elements[1][0] * src.elements[2][2] + src.elements[1][2] * src.elements[2][0];
			float t02 = src.elements[1][0] * src.elements[2][1] - src.elements[1][1] * src.elements[2][0];
			float t10 = -src.elements[0][1] * src.elements[2][2] + src.elements[0][2] * src.elements[2][1];
			float t11 = src.elements[0][0] * src.elements[2][2] - src.elements[0][2] * src.elements[2][0];
			float t12 = -src.elements[0][0] * src.elements[2][1] + src.elements[0][1] * src.elements[2][0];
			float t20 = src.elements[0][1] * src.elements[1][2] - src.elements[0][2] * src.elements[1][1];
			float t21 = -src.elements[0][0] * src.elements[1][2] + src.elements[0][2] * src.elements[1][0];
			float t22 = src.elements[0][0] * src.elements[1][1] - src.elements[0][1] * src.elements[1][0];
			dest.elements[0][0] = t00 * determinant_inv;
			dest.elements[1][1] = t11 * determinant_inv;
			dest.elements[2][2] = t22 * determinant_inv;
			dest.elements[0][1] = t10 * determinant_inv;
			dest.elements[1][0] = t01 * determinant_inv;
			dest.elements[2][0] = t02 * determinant_inv;
			dest.elements[0][2] = t20 * determinant_inv;
			dest.elements[1][2] = t21 * determinant_inv;
			dest.elements[2][1] = t12 * determinant_inv;
			return dest;
		} else
		{
			return null;
		}
	}

	public float determinant()
	{
		float f = elements[0][0] * (elements[1][1] * elements[2][2] - elements[1][2] * elements[2][1]) + elements[0][1] * (elements[1][2] * elements[2][0] - elements[1][0] * elements[2][2]) + elements[0][2] * (elements[1][0] * elements[2][1] - elements[1][1] * elements[2][0]);
		return f;
	}
}
