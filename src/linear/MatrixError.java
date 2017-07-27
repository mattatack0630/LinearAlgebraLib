package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class MatrixError extends Throwable
{

	public static void throwError(String errorMsg)
	{
		System.err.println(errorMsg);
		System.err.println();
		try
		{
			throw new Error();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Error Check Methods
	 **/
	protected static boolean checkSame(Matrix m0, Matrix m1)
	{
		boolean b = true;

		if (m0.height != m1.height || m0.width != m1.width)
		{
			throwError("Error : Matrices are not of the same dimensions");
			b = false;
		}

		return b;
	}

	protected static boolean checkSameRowCol(Matrix m0, Matrix m1)
	{
		boolean b = true;

		if (m0.height != m1.width)
		{
			throwError("Error : Matrices are not Row-Column compatible (m1.row != m2.column)");
			b = false;
		}

		return b;
	}

	protected static boolean checkEnoughSpace(Matrix m0, int height, int width)
	{
		boolean b = true;

		if (m0.height != height || m0.width != width)
		{
			throwError("Error : linear.Matrix is not of the correct dimensions provided\n"+
					"provided -> (w="+m0.width+", h="+m0.height+")\n" +
					"needed -> (w="+width+", h="+height+")");
			b = false;
		}

		return b;
	}
}
