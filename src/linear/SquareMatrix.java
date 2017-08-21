package linear;

/**
 * Created by mjmcc on 7/26/2017.
 * <p>
 * A linear.Matrix that has an equal width and height
 */
public class SquareMatrix extends Matrix
{
//	private float preCalculatedDeterminate; // Not implemented yet

	public SquareMatrix(int size)
	{
		super(size, size);
	}

	public SquareMatrix(SquareMatrix src)
	{
		super(src);
	}

	public <E extends SquareMatrix> E invertMatrix()
	{
		return (E) SquareMatrix.invert(this, this);
	}

	public <E extends SquareMatrix> E setIdentity()
	{
		return (E) SquareMatrix.setIdentity(this);
	}

	public <E extends SquareMatrix> E transpose()
	{
		return (E) Matrix.transpose(this, this);
	}

	public float determinantMatrix()
	{
		return SquareMatrix.determinant(this);
	}
/*

	@Override
	public void PreCalcUsefulData()
	{
		if(elementsChanged)
		{
			// Determinate
			preCalculatedDeterminate = SquareMatrix.determinant(this);
		}

		elementsChanged = false;
	}

*/
	////////////////////////////////////////////////////////////////////////////////////////////

	public static <E extends SquareMatrix> E setIdentity(E left)
	{
		left.elementsChanged = true;

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				left.elements[i][j] = i == j ? 1.0f : 0.0f;

		return left;
	}

	/**
	 * ! Slow, but complete method !
	 * Use specific inversion for common matrix orders like 2x2, 3x3, or 4x4
	 * */
	public static <E extends SquareMatrix> E invert(E left, E dest)
	{
		MatrixError.checkSame(left, dest);

		// Temp dest, in case src == dest
		boolean inputIsDest = dest == left;
		float[][] destEls = inputIsDest ? new float[dest.width][dest.height] : dest.elements;
		float det = 1.0f / determinant(left);
		int order = left.width;

		SquareMatrix minor = new SquareMatrix(order - 1);

		for (int j = 0; j < order; j++)
		{
			for (int i = 0; i < order; i++)
			{
				// get the co-factor (matrix) of A(j,i)
				getMinor(left, minor, j, i);
				destEls[i][j] = det * determinant(minor);
				destEls[i][j] *= ((i + j) % 2 == 1) ? -1 : 1;
			}
		}

		dest.elementsChanged = true;
		dest.elements = destEls;

		return dest;
	}

	/**
	 * ! Slow, but complete method !
	 * Use specific inversion for common matrix orders like 2x2, 3x3, or 4x4
	 * */
	public static float determinant(SquareMatrix left)
	{
		//if(!left.elementsChanged)
		//	return left.preCalculatedDeterminate;

		int order = left.width;
		float det = 0;

		if (order == 1)
			return left.elements[0][0];

		SquareMatrix coMat = new SquareMatrix(order - 1);

		for (int i = 0; i < order; i++)
		{
			SquareMatrix.getMinor(left, coMat, 0, i);
			det += (i % 2 == 1 ? -1.0 : 1.0) * left.elements[0][i] * determinant(coMat);
		}

		return det;
	}

	/**
	 * Get a minor / sub-matrix
	 * @param row the row to discard
	 * @param col the column to discard
	 * */
	private static void getMinor(SquareMatrix left, SquareMatrix dest, int row, int col)
	{
		int colCount = 0;
		int rowCount = 0;
		int order = left.width;

		for (int i = 0; i < order; i++)
		{
			if (i != row)
			{
				colCount = 0;
				for (int j = 0; j < order; j++)
				{
					// when j is not the element
					if (j != col)
					{
						dest.elements[rowCount][colCount] = left.elements[i][j];
						colCount++;
					}
				}
				rowCount++;
			}
		}

		dest.elementsChanged = true;
	}
}
