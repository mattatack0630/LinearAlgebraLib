package linear;

/**
 * Created by mjmcc on 12/14/2016.
 */
public class Matrix
{
	protected static final float SAME_TOLERANCE = 0.00001f;
	protected float[][] elements;
	protected int width, height;

	/**
	 * Constructors
	 */
	protected Matrix(float[][] elements, int w, int h)
	{
		this.elements = elements;
		this.width = w;
		this.height = h;
	}

	public Matrix(int w, int h)
	{
		this.elements = new float[w][h];
		this.width = w;
		this.height = h;
		setZero();
	}

	protected Matrix(Matrix src)
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
		for (int i = 0; i < col.length; i++)
			col[i] = elements[c][i];
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
		// up -> down
		if (data.length == height)
			for (int i = 0; i < height; i++)
				elements[c][i] = data[i];
		else MatrixError.throwError("");
	}

	public void setRow(int r, float[] data)
	{
		// Left -> right
		if (data.length == width)
			for (int i = 0; i < width; i++)
				elements[i][r] = data[i];
		else MatrixError.throwError("");
	}

	public void setElement(int x, int y, float v)
	{
		if (width >= x && height >= y)
			elements[x][y] = v;
		else MatrixError.throwError("");
	}

	protected void load(float[][] f, int w, int h)
	{
		if (width == w && height == h && f.length == w && f[0].length == h)
			for (int i = 0; i < w; i++)
				for (int j = 0; j < h; j++)
					elements[i][j] = f[i][j];
		else MatrixError.throwError("");
	}

	/**
	 * linear.Matrix Instance Methods
	 */
	public <E extends Matrix> E negate()
	{

		return (E) Matrix.negate(this, this);
	}

	public <E extends Matrix> E setZero()
	{

		return (E) Matrix.setZero(this);
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

	public <E extends Matrix> E mult(Matrix other)
	{

		return (E) Matrix.mult(this, other, this);
	}

	public <E extends Matrix> E multElements(E other)
	{

		return (E) Matrix.multElements(this, other, this);
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
		String s = getClass().getSimpleName() + ":\n";

		for (int i = 0; i < height; i++)
		{
			s += "[";
			for (int j = 0; j < width; j++)
			{
				s += elements[j][i] + (j == width - 1 ? "]\n" : ", ");
			}
		}

		return s;
	}

	@Override
	public boolean equals(Object o)
	{
		boolean b = false;

		if (o instanceof Matrix)
		{
			b = true;
			Matrix m = (Matrix) o;

			if (this.width == m.width && this.height == m.height)
			{
				for (int i = 0; i < width; i++)
					for (int j = 0; j < height; j++)
						b = (Math.abs(elements[i][j] - m.elements[i][j]) < SAME_TOLERANCE) && b;
			}
		}

		return b;
	}

	/**
	 * Static Methods
	 **/
	public static <E extends Matrix> E mult(Matrix left, Matrix right, Matrix dest)
	{
		try
		{
			Matrix m = new Matrix(right.width, left.height);

			MatrixError.checkSameRowCol(right, left);
			MatrixError.checkEnoughSpace(dest, m.height, m.width);
			// check cols == rows and that the dest has right dimensions

			for (int i = 0; i < m.width; i++)
			{
				for (int j = 0; j < m.height; j++)
				{
					float[] r = left.getRow(j);
					float[] c = right.getColumn(i);

					for (int k = 0; k < r.length; k++)
						m.elements[i][j] += (r[k] * c[k]);
				}
			}

			if (dest != null)
				dest.load(m.elements, m.width, m.height);

			return (E) dest;
		} catch (ClassCastException | NullPointerException e)
		{
			return null;
		}
	}

	public static <E extends Matrix> E transpose(Matrix left, Matrix dest)
	{
		Matrix m = new Matrix(left.height, left.width);

		MatrixError.checkEnoughSpace(dest, m.height, m.width);

		for (int i = 0; i < m.width; i++)
			for (int j = 0; j < m.height; j++)
				m.elements[i][j] = left.elements[j][i];

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return (E) dest;
	}

	public static <E extends Matrix> E multElements(E left, E right, E dest)
	{
		MatrixError.checkSame(left, right);
		MatrixError.checkSame(left, dest);

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				dest.elements[i][j] = left.elements[i][j] * right.elements[i][j];

		return dest;
	}

	public static <E extends Matrix> E scale(E left, float s, E dest)
	{
		Matrix m = new Matrix(left.width, left.height);

		MatrixError.checkEnoughSpace(dest, left.height, left.width);

		for (int i = 0; i < m.width; i++)
			for (int j = 0; j < m.height; j++)
				m.elements[i][j] = left.elements[i][j] * s;

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return dest;
	}

	public static <E extends Matrix> E round(E left, int n, E dest)
	{
		MatrixError.checkSame(left, dest);

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				dest.elements[i][j] = Maths.round(left.elements[i][j], n);

		return dest;
	}

	public static <E extends Matrix> E add(E left, E right, E dest)
	{
		Matrix m = new Matrix(left.width, left.height);

		MatrixError.checkEnoughSpace(dest, m.height, m.width);
		// check left size == right size and that the dest has right dimensions

		for (int i = 0; i < m.width; i++)
			for (int j = 0; j < m.height; j++)
				m.elements[i][j] = left.elements[i][j] + right.elements[i][j];

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return dest;

	}

	public static <E extends Matrix> E sub(E left, E right, E dest)
	{

		Matrix m = new Matrix(left.width, left.height);

		MatrixError.checkEnoughSpace(dest, m.height, m.width);
		// check left size == right size and that the dest has right dimensions

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				m.elements[i][j] = left.elements[i][j] - right.elements[i][j];

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return dest;

	}

	public static <E extends Matrix> E absolute(E left, E dest)
	{
		MatrixError.checkSame(left, dest);

		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				dest.elements[i][j] = Math.abs(left.elements[i][j]);

		return dest;
	}

	public static <E extends Matrix> E negate(E left, E dest)
	{
		Matrix m = new Matrix(left.width, left.height);

		MatrixError.checkEnoughSpace(dest, left.height, left.width);

		for (int i = 0; i < m.width; i++)
			for (int j = 0; j < m.height; j++)
				m.elements[i][j] = -1.0f * left.elements[i][j];

		if (dest != null)
			dest.load(m.elements, m.width, m.height);

		return dest;
	}

	public static <E extends Matrix> E setZero(E left)
	{
		for (int i = 0; i < left.width; i++)
			for (int j = 0; j < left.height; j++)
				left.elements[i][j] = 0.0f;
		return left;
	}
}
