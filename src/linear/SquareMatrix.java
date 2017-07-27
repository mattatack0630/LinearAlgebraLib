package linear;

/**
 * Created by mjmcc on 7/26/2017.
 *
 * A linear.Matrix that has an equal width and height
 */
public class SquareMatrix extends Matrix
{
	public SquareMatrix(int size)
	{

		super(size, size);
	}

	public SquareMatrix(SquareMatrix src)
	{

		super(src);
	}

	public <E extends Matrix> E transpose()
	{

		return (E) transpose(this, this);
	}

	public <E extends Matrix> E setIdentity()
	{

		return (E) setIdentity(this);
	}

	public static <E extends Matrix> E setIdentity(E left)
	{
		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				left.elements[i][j] = i == j ? 1.0f : 0.0f;

		return left;
	}

	// Determinate to add later
}
