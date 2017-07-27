package linear;

/**
 * Created by mjmcc on 12/15/2016.
 */
public class MatrixGenerator
{
	// Make directional later
	public static TransformMatrix genTransformMatrix(Vector3f translation, Rotation rotation, Vector3f scale, TransformMatrix dest)
	{
		dest = dest == null ? new TransformMatrix() : dest;
		dest.setIdentity();

		// Translate
		dest.translate(translation);
		// Rotate
		dest.rotate(rotation);
		// Scale
		dest.scale(scale);

		return dest;
	}

	public static Matrix4f genPerspectiveMatrix(float near, float far, float fov, float aspectRatio, Matrix4f dest)
	{
		dest = dest == null ? new Matrix4f() : dest;
		dest.setIdentity();

		float y_scale = (float) ((1f / Math.tan(fov / 2f)));
		float x_scale = y_scale / aspectRatio;
		float frustum_length = far - near;

		dest.elements[0][0] = x_scale;
		dest.elements[1][1] = y_scale;
		dest.elements[2][2] = -((far + near) / frustum_length);
		dest.elements[2][3] = -1;
		dest.elements[3][2] = -((2 * near * far) / frustum_length);
		dest.elements[3][3] = 0;

		return dest;
	}

	public static Matrix4f genOrthoMatrix(float width, float height, float length, Matrix4f dest)
	{
		dest = dest == null ? new Matrix4f() : dest;
		dest.setZero();

		dest.setIdentity();
		dest.setElement(0, 0, 2.0f / width);
		dest.setElement(1, 1, 2.0f / height);
		dest.setElement(2, 2, -2.0f / length);

		return dest;
	}

	public static Matrix4f genViewMatrix(Vector3f pos, Vector3f rotation, TransformMatrix dest)
	{
		dest = dest == null ? new TransformMatrix() : dest;
		dest.setIdentity();

		TransformMatrix.rotate(dest, new AxisAngle(new Vector3f(1,0,0), rotation.x()), dest);
		TransformMatrix.rotate(dest, new AxisAngle(new Vector3f(0,1,0), rotation.y()), dest);

		Vector3f negativeCameraPos = new Vector3f(pos).negate();
		TransformMatrix.translate(dest, negativeCameraPos, dest);

		return dest;
	}

	public static Matrix3f genNBTMatrix(Vector3f normal, Vector3f tangent, Vector3f bitangent, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		dest.setZero();

		dest.setRow(0, normal.getElements()[0]);
		dest.setRow(1, tangent.getElements()[0]);
		dest.setRow(2, bitangent.getElements()[0]);

		return dest;
	}

	public static Matrix3f genNBTConversion(Matrix3f nbt0, Matrix3f nbt1, Matrix3f dest)
	{
		dest = dest == null ? new Matrix3f() : dest;
		dest.setZero();

		Matrix3f.mult(nbt0, Matrix3f.invert(nbt1, null), dest);

		return dest;
	}

	public static Matrix4f genRotationMatrix(Rotation rotation, Matrix4f dest)
	{
		dest = dest == null ? new Matrix4f() : dest;
		dest.setIdentity();

		return Matrix4f.rotate(dest, rotation, dest);
	}
}
