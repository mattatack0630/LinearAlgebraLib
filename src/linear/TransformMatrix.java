package linear;

/**
 * Created by mjmcc on 7/26/2017.
 *
 *  A linear.Matrix4f extension that tracks (scale, position, rotation) and generates
 *  a Transformation linear.Matrix.
 */
public class TransformMatrix extends Matrix4f
{
	private Vector3f scale;
	private AxisAngle rotation;
	private Vector3f translation;

	public TransformMatrix(Vector3f scale, Rotation rotation, Vector3f translation)
	{
		this.setIdentity();
		this.scale = scale;
		this.rotation = rotation.toAxisAngle();
		this.translation = translation;
		this.updateMatrix();
	}

	public TransformMatrix()
	{

		this(new Vector3f(1), new AxisAngle(new Vector3f(0, 1, 0), 0), new Vector3f());
	}

	/**
	 * Sync the scale, rotation, and translation values to generate
	 * a transformation matrix. Use for non-dynamic flag.
	 * */
	public TransformMatrix updateMatrix()
	{
		setIdentity();
		translate(this, this.translation, this);
		rotate(this, this.rotation, this);
		scale(this, this.scale, this);
		return this;
	}

	/**
	 * Instance Manipulation Methods
	 * */
	public TransformMatrix scale(float scale)
	{
		this.scale.set(scale, scale, scale);
		return this;
	}

	public TransformMatrix scale(Vector3f scale)
	{
		this.scale.set(scale);
		return this;
	}

	public TransformMatrix rotate(Rotation rotation)
	{
		this.rotation = rotation.toAxisAngle();
		return this;
	}

	public TransformMatrix translate(Vector3f translation)
	{
		this.translation.set(translation);
		return this;
	}

	/**
	 * Static Methods
	 * */
	public static Vector[] decompose(TransformMatrix src)
	{
		TransformMatrix nMat = new TransformMatrix();
		nMat.load(src.elements, 4, 4);

		Vector3f ePos = new Vector3f(nMat.getColumn(3));
		nMat.translate(new Vector3f(-ePos.x(), -ePos.y(), -ePos.z()));

		Vector3f eScale = new Vector3f(new Vector3f(nMat.getColumn(0)).length(),
				new Vector3f(nMat.getColumn(1)).length(),
				new Vector3f(nMat.getColumn(2)).length());

		nMat.scale(new Vector3f(1f / eScale.x(), 1f / eScale.y(), 1f / eScale.z()));

		// Just a place holder -- **Vec4 != linear.Quaternion**
		Vector4f eRot = new Vector4f();
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
