package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public interface Rotation
{
	void fromAxisAngle(AxisAngle a);

	AxisAngle toAxisAngle();

	Matrix4f toRotationMatrix();

	Rotation copy();
}
