package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Matrix
{
	protected static final float SAME_TOLERANCE = 1E-5f;
	protected boolean elementsChanged;
	protected float[][] elements;
	protected int width, height;

	/**
	 * Constructors
	 */
	public Matrix(float[][] elements, int w, int h)
	{
		this.elementsChanged = false;
		this.elements = elements;
		this.height = h;
		this.width = w;
	}

	public Matrix(int w, int h)
	{
		this.elementsChanged = false;
		this.elements = new float[w][h];
		this.width = w;
		this.height = h;
	}

	public Matrix(Matrix src)
	{
		this(src.width, src.height);
		load(src.elements, width, height);
	}

	/**
	 * linear.Matrix Getters and Setters
	 */
	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public float[] getRow(int r)
	{
		float[] row = new float[width];

		for (int i = 0; i < row.length; i++)
			row[i] = elements[i][r];

		return row;
	}

	public float[] getColumn(int c)
	{
		float[] col = new float[height];

		System.arraycopy(elements[c], 0, col, 0, col.length);

		return col;
	}

	protected float[][] getElements()
	{
		return elements;
	}

	public float getElement(int x, int y)
	{
		return elements[x][y];
	}

	public void setCol(int c, float[] data)
	{
		elementsChanged = true;
		// up -> down
		if (data.length == height)
			System.arraycopy(data, 0, elements[c], 0, data.length);
		else
			MatrixError.throwError("");
	}

	public void setRow(int r, float[] data)
	{
		elementsChanged = true;
		// Left -> right
		if (data.length == width)
			for (int i = 0; i < width; i++)
				elements[i][r] = data[i];
		else
			MatrixError.throwError("");
	}

	public void setElement(int x, int y, float v)
	{
		elementsChanged = true;

		if (width >= x && height >= y)
			elements[x][y] = v;
		else
			MatrixError.throwError("");
	}

	protected void load(float[][] f, int w, int h)
	{
		elementsChanged = true;

		if (width == w && height == h && f.length == w && f[0].length == h)
			for (int i = 0; i < f.length; i++)
				System.arraycopy(f[i], 0, elements[i], 0, f[i].length);
		else
			MatrixError.throwError("Tried to load invalid array size");
	}

/*
	public boolean areElementsChanged()
	{
		return elementsChanged;
	}

	public void PreCalcUsefulData()
	{
		if (elementsChanged)
		{
			// Nothing yet
		}

		elementsChanged = false;
	}
*/

	/**
	 * linear.Matrix Instance Methods
	 */
	public <E extends Matrix> E negate()
	{
		return (E) Matrix.negate(this, this);
	}

	public <E extends Matrix> E absolute()
	{
		return (E) Matrix.absolute(this, this);
	}

	public <E extends Matrix> E add(Matrix other)
	{
		return (E) Matrix.add(this, other, this);
	}

	public <E extends Matrix> E sub(Matrix other)
	{
		return (E) Matrix.sub(this, other, this);
	}

	public <E extends Matrix> E scale(float scale)
	{
		return (E) Matrix.scale(this, scale, this);
	}

	public <E extends Matrix> E divElements(E other)
	{
		return (E) Matrix.divElements(this, other, this);
	}

	public <E extends Matrix> E multElements(E other)
	{
		return (E) Matrix.multElements(this, other, this);
	}

	public <E extends Matrix> E multDot(Matrix other)
	{
		return (E) Matrix.multDot(this, other, this);
	}

	public <E extends Matrix> E setRandom(float min, float max)
	{
		return (E) Matrix.setRandom(this, min, max);
	}

	public <E extends Matrix> E setZero()
	{
		return (E) Matrix.setZero(this);
	}

	/**
	 * Overridden Object Methods
	 */
	@Override
	public int hashCode()
	{
		int hash = 0;

		for (int i = 0; i < width; i++)
			for (int j = 0; j < height; j++)
				hash = hash ^ new Float(elements[i][j]).hashCode();

		return hash;
	}

	@Override
	public String toString()
	{
		// Bad toString() I know, but it looks good in the console... :D
		String title = getClass().getSimpleName().trim();
		int colSize = 10 + (title.length() % 2);

		if (colSize < title.length())
			colSize = title.length();

		String result = new String();
		result += ("\u250C" + StringUtils.center(title, width * colSize) + "\u2510");
		result += "\n";

		for (int i = 0; i < height; i++)
		{
			result += "\u2502";

			for (int j = 0; j < width; j++)
			{
				String num = String.format("%.3f", elements[j][i]);
				num = StringUtils.center(num, colSize);
				result += num;
			}

			result += "\u2502";
			result += "\n";
		}

		result += ("\u2514" + StringUtils.center("", width * colSize) + "\u2518");
		result += "\n";

		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		boolean b = false;

		if (o instanceof Matrix)
		{
			Matrix m = (Matrix) o;

			if (this.width == m.width && this.height == m.height)
			{
				b = true;

				for (int i = 0; i < width; i++)
				{
					for (int j = 0; j < height; j++)
					{
						b = (Math.abs(elements[i][j] - m.elements[i][j]) < SAME_TOLERANCE) && b;

						// If even one element is not equal, we can return false
						if (b == false) return b;
					}
				}
			}
		}

		return b;
	}

	/**
	 * Static Methods
	 **/
	public static <E extends Matrix> E setZero(E left)
	{
		left.elementsChanged = true;

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				left.elements[i][j] = 0.0f;

		return left;
	}

	public static <E extends Matrix> E setRandom(E left, float min, float max)
	{
		left.elementsChanged = true;

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				left.elements[i][j] = Maths.random(min, max);

		return left;
	}

	public static <E extends Matrix> E negate(E left, E dest)
	{
		MatrixError.checkSame(dest, left);
		dest.elementsChanged = true;

		for (int i = 0; i < dest.width; i++)
			for (int j = 0; j < dest.height; j++)
				dest.elements[i][j] = -1.0f * left.elements[i][j];

		return dest;
	}

	public static <E extends Matrix> E absolute(E left, E dest)
	{
		MatrixError.checkSame(left, dest);
		dest.elementsChanged = true;

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				dest.elements[i][j] = Math.abs(left.elements[i][j]);

		return dest;
	}

	public static <E extends Matrix> E sub(E left, E right, E dest)
	{
		// check left size == right size and that the dest has right dimensions
		dest.elementsChanged = true;

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				dest.elements[i][j] = left.elements[i][j] - right.elements[i][j];

		return dest;

	}

	public static <E extends Matrix> E add(E left, E right, E dest)
	{
		MatrixError.checkSame(left, right);
		MatrixError.checkSame(left, dest);
		dest.elementsChanged = true;

		for (int i = 0; i < dest.width; i++)
			for (int j = 0; j < dest.height; j++)
				dest.elements[i][j] = left.elements[i][j] + right.elements[i][j];

		return dest;
	}

	public static <E extends Matrix> E round(E left, int n, E dest)
	{
		MatrixError.checkSame(left, dest);
		dest.elementsChanged = true;

		for (int i = 0; i < left.width; i++)
		{
			for (int j = 0; j < left.height; j++)
			{
				// Rounding method
				float tens = (float) Math.pow(10, n);
				dest.elements[i][j] = Math.round(dest.elements[i][j] * tens) / tens;
			}
		}

		return dest;
	}

	public static <E extends Matrix> E scale(E left, float s, E dest)
	{
		MatrixError.checkSame(left, dest);
		dest.elementsChanged = true;

		for (int i = 0; i < dest.width; i++)
			for (int j = 0; j < dest.height; j++)
				dest.elements[i][j] = left.elements[i][j] * s;

		return dest;
	}

	public static <E extends Matrix> E transpose(Matrix left, Matrix dest)
	{
		MatrixError.checkEnoughSpace(dest, left.width, left.height);
		dest.elementsChanged = true;

		boolean inputIsDest = dest == left;

		float[][] destEls = inputIsDest ? new float[dest.width][dest.height] : dest.elements;

		for (int i = 0; i < dest.width; i++)
			for (int j = 0; j < dest.height; j++)
				destEls[i][j] = left.elements[j][i];

		if (inputIsDest)
			dest.elements = destEls;

		return (E) dest;
	}

	public static <E extends Matrix> E divElements(E left, E right, E dest)
	{
		MatrixError.checkSame(left, right);
		MatrixError.checkSame(left, dest);
		dest.elementsChanged = true;

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				dest.elements[i][j] = left.elements[i][j] / right.elements[i][j];

		return dest;
	}

	public static <E extends Matrix> E multElements(E left, E right, E dest)
	{
		MatrixError.checkSame(left, right);
		MatrixError.checkSame(left, dest);
		dest.elementsChanged = true;

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				dest.elements[i][j] = left.elements[i][j] * right.elements[i][j];

		return dest;
	}

	public static <E extends Matrix> E multDot(Matrix left, Matrix right, Matrix dest)
	{
		MatrixError.checkSameRowCol(right, left);
		MatrixError.checkEnoughSpace(dest, left.height, right.width);
		dest.elementsChanged = true;

		boolean inputIsDest = dest == left || dest == right;

		float[][] destEls = inputIsDest ? new float[right.width][left.height] : dest.elements;

		for (int i = 0; i < dest.width; i++)
		{
			for (int j = 0; j < dest.height; j++)
			{
				destEls[i][j] = 0;

				for (int k = 0; k < left.width; k++)
				{
					destEls[i][j] += (left.elements[k][j] * right.elements[i][k]); // left.Row_J[k] * right.Col_I[k]
				}
			}
		}

		if (inputIsDest)
			dest.elements = destEls;

		return (E) dest;
	}
}
