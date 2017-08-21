package linear;

/**
 * Created by mjmcc on 7/26/2017.
 * <p>
 * A linear.Matrix4f extension that tracks (scale, position, rotation) and generates
 * a Transformation linear.Matrix.
 */
public class TransformMatrix extends Matrix4f
{
	private Vector3f scale;
	private Matrix4f rotation;
	private Vector3f translation;
	private boolean accumulateTransforms;

	public TransformMatrix(boolean accTransforms, Vector3f scale, Rotation rotation, Vector3f translation)
	{
		this.setIdentity();
		this.scale = scale;
		this.rotation = rotation.toRotationMatrix();
		this.translation = translation;
		this.accumulateTransforms = accTransforms;
		this.applyTransforms();
	}

	public TransformMatrix(boolean accumulateTransforms, Matrix4f src)
	{
		Vector[] decomp = Matrix4f.decompose(src);

		this.setIdentity();
		this.scale = (Vector3f) decomp[0];
		this.translation = (Vector3f) decomp[2];
		this.rotation = ((Quaternion) decomp[1]).toRotationMatrix();
		this.accumulateTransforms = accumulateTransforms;
		this.applyTransforms();
	}

	public TransformMatrix(boolean accTransforms)
	{
		this(accTransforms, new Vector3f(1), new Quaternion(), new Vector3f());
	}

	public TransformMatrix(TransformMatrix src)
	{
		this.setIdentity();
		this.scale = new Vector3f(src.scale);
		this.rotation = new Matrix4f(src.rotation);
		this.translation = new Vector3f(src.translation);
		this.accumulateTransforms = src.accumulateTransforms;
		this.applyTransforms();
	}

	/**
	 * Apply the scale, rotation, and translation values to generate
	 * a transformation matrix.
	 */
	public TransformMatrix applyTransforms()
	{
		setIdentity();

		translate(this, this.translation, this);
		multDot(this, this.rotation, this);
		scale(this, this.scale, this);

		return this;
	}

	/**
	 * Instance Manipulation Methods
	 */
	public TransformMatrix scale(float scale)
	{
		if(accumulateTransforms)
			this.scale.scale(scale);
		else
			this.scale.set(scale, scale, scale);

		return this;
	}

	public TransformMatrix scale(Vector3f scale)
	{
		if(accumulateTransforms)
			this.scale.multElements(scale);
		else
			this.scale.set(scale);

		return this;
	}

	public TransformMatrix rotate(Rotation rotation)
	{
		if(accumulateTransforms)
			this.rotation.multDot(rotation.toRotationMatrix());
		else
			this.rotation = rotation.toRotationMatrix();

		return this;
	}

	public TransformMatrix translate(Vector3f translation)
	{
		if(accumulateTransforms)
			this.translation.add(translation);
		else
			this.translation.set(translation);

		return this;
	}

	/**
	 * Getters
	 * */
	public Vector3f getScale()
	{
		return scale;
	}

	public Matrix4f getRotation()
	{
		return rotation;
	}

	public Vector3f getTranslation()
	{
		return translation;
	}
}
