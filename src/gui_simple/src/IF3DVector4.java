package gui_simple.src;

/**
 * @author Dominik
 * @created 16.03.2015
 */
public class IF3DVector4
{
	private float x;
	private float y;
	private float z;
	private float w;

	/**
	 * creates new null-vector
	 */
	public IF3DVector4()
	{
		this.x = 0;
		this.y = 0;
		this.z = 0;
		this.w = 0;
	}

	/**
	 * creates new Vector with coordinate tuple
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @param w
	 */
	public IF3DVector4(float x, float y, float z, float w)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.w = w;
	}

	/**
	 * normalizes this vector
	 * 
	 * @return this vector
	 */
	public IF3DVector4 normalize()
	{
		float length = getLength();
		this.x /= length;
		this.y /= length;
		this.z /= length;
		this.w /= length;

		return this;
	}

	/**
	 * returns a new vector equaling this vector but normalized
	 * 
	 * @return a vector pointing in the same direction as "this" but with a unit length of 1
	 */
	public IF3DVector4 getNormalized()
	{
		return new IF3DVector4(this.x, this.y, this.z, this.w).normalize();
	}

	/**
	 * Adds another vector to this vector
	 * 
	 * @param vector
	 *            the vector to add
	 * @return sum of the two vectors
	 */
	public IF3DVector4 addVector(IF3DVector4 vector)
	{
		this.x += vector.x;
		this.y += vector.y;
		this.z += vector.z;
		this.w += vector.w;

		return this;
	}

	/**
	 * returns a new Vector containing the addition result
	 * 
	 * @return new Vector = this + parameter
	 */
	public IF3DVector4 getAdditionResultVector(IF3DVector4 vector)
	{
		return new IF3DVector4(this.x + vector.x, this.y + vector.y, this.z + vector.z, this.w
				+ vector.w);
	}

	/**
	 * subtracts given vector from this vector
	 * 
	 * @param vector
	 *            the vector to subtract
	 * @return this vector after the subtraction
	 */
	public IF3DVector4 subtractVector(IF3DVector4 vector)
	{
		this.x -= vector.x;
		this.y -= vector.y;
		this.z -= vector.z;
		this.w -= vector.w;

		return this;
	}

	/**
	 * returns a new Vector containing the subtraction result
	 * 
	 * @param vector
	 *            to subtract from this
	 * @return new vector = this - parameter
	 */
	public IF3DVector4 getSubtractionResultVector(IF3DVector4 vector)
	{
		return new IF3DVector4(this.x - vector.x, this.y - vector.y, this.z - vector.z, this.w
				- vector.w);
	}

	/**
	 * scales the vector's length by a scalar
	 * 
	 * @param scalar
	 *            the factor with which to scale
	 * @return this after scaling
	 */
	public IF3DVector4 scaleVector(float scalar)
	{
		this.x *= scalar;
		this.y *= scalar;
		this.z *= scalar;
		this.w *= scalar;

		return this;
	}

	/**
	 * returns a new Vector containing the scaling result
	 * 
	 * @param scalar
	 *            the factor with which to scale
	 * @return scaled vector
	 */
	public IF3DVector4 getScaleResultVector(float scalar)
	{
		return new IF3DVector4(this.x * scalar, this.y * scalar, this.z * scalar, this.w * scalar);
	}

	/**
	 * inverts vectors direction
	 * 
	 * @return inverted vector (opposing direction)
	 */
	public IF3DVector4 invertVector()
	{
		return this.scaleVector(-1f);
	}

	/**
	 * returns new vector containing the inverted "this" vector
	 * 
	 * @return this * (-1)
	 */
	public IF3DVector4 getInvertionResultVector()
	{
		return this.getScaleResultVector(-1f);
	}

	/**
	 * returns the vector's length
	 * 
	 * @return
	 */
	public float getLength()
	{
		return (float) Math.sqrt(this.getLengthSquared());
	}

	/**
	 * returns the squared vector's length. Use for performance's sake as a sqrt() calculation isn't needed for calculating the squared vector lenght
	 * 
	 * @return
	 */
	public float getLengthSquared()
	{
		return this.x * this.x + this.y * this.y + this.z * this.z + this.w * this.w;
	}
	
	public IF3DVector3 convertTo3D()
	{
		return new IF3DVector3(this.x, this.y, this.z);
	}
}