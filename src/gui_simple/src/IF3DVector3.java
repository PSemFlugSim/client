package gui_simple.src;

/**
 * @author Dominik
 * @created 09.03.2015
 */
public class IF3DVector3
{
    public float x;
    public float y;
    public float z;

    /**
     * creates new null-vector
     */
    public IF3DVector3()
    {
        this.x = 0;
        this.y = 0;
        this.z = 0;
    }

    /**
     * creates new Vector with coordinate tuple
     *
     * @param x
     * @param y
     * @param z
     */
    public IF3DVector3(float x, float y, float z)
    {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * normalizes this vector
     *
     * @return this vector
     */
    public IF3DVector3 normalize()
    {
        float length = getLength();
        this.x /= length;
        this.y /= length;
        this.z /= length;

        return this;
    }

    /**
     * returns a new vector equaling this vector but normalized
     *
     * @return a vector pointing in the same direction as "this" but with a unit length of 1
     */
    public IF3DVector3 getNormalizedVector()
    {
        return new IF3DVector3(this.x, this.y, this.z).normalize();
    }

    /**
     * Adds another vector to this vector
     *
     * @param vector
     *            the vector to add
     * @return sum of the two vectors
     */
    public IF3DVector3 addVector(IF3DVector3 vector)
    {
        this.x += vector.x;
        this.y += vector.y;
        this.z += vector.z;

        return this;
    }

    /**
     * returns a new Vector containing the addition result
     *
     * @return new Vector = this + parameter
     */
    public IF3DVector3 getAdditionResultVector(IF3DVector3 vector)
    {
        return new IF3DVector3(this.x + vector.x, this.y + vector.y, this.z + vector.z);
    }

    /**
     * subtracts given vector from this vector
     *
     * @param vector
     *            the vector to subtract
     * @return this vector after the subtraction
     */
    public IF3DVector3 subtractVector(IF3DVector3 vector)
    {
        this.x -= vector.x;
        this.y -= vector.y;
        this.z -= vector.z;

        return this;
    }

    /**
     * returns a new Vector containing the subtraction result
     *
     * @param vector
     *            to subtract from this
     * @return new vector = this - parameter
     */
    public IF3DVector3 getSubtractionResultVector(IF3DVector3 vector)
    {
        return new IF3DVector3(this.x - vector.x, this.y - vector.y, this.z - vector.z);
    }

    /**
     * scales the vector's length
     *
     * @param scalar
     *            the factor with which to scale
     * @return scaled vector
     */
    public IF3DVector3 scaleVector(float scalar)
    {
        this.x *= scalar;
        this.y *= scalar;
        this.z *= scalar;

        return this;
    }

    /**
     * returns a new Vector containing the scaling result
     *
     * @param scalar
     *            the factor with which to scale
     * @return scaled vector
     */
    public IF3DVector3 getScaleResultVector(float scalar)
    {
        return new IF3DVector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    /**
     * inverts vectors direction
     *
     * @return inverted vector
     */
    public IF3DVector3 invertVector()
    {
        return this.scaleVector(-1.0f);
    }

    /**
     * returns new vector containing the inverted "this" vector
     *
     * @return this * (-1)
     */
    public IF3DVector3 getInvertionResultVector()
    {
        return this.getScaleResultVector(-1f);
    }

    /**
     * calculates the cross product between this vector and the given vector
     *
     * @param vector
     *            vector with which to calculate the cross product
     * @return new vector containing the cross product result
     */

    public IF3DVector3 crossProduct(IF3DVector3 vector)
    {
        return new IF3DVector3(this.y * vector.z - this.z * vector.y, this.z * vector.x - this.x
                * vector.z, this.x * vector.y - this.y * vector.x);
    }

    /**
     * calculates the dot product between this vector and the given vector
     *
     * @param vector
     *            vector with which to calculate the dot product
     * @return dot product
     */
    public float dotProduct(IF3DVector3 vector)
    {
        IF3DVector3 vector1 = this.normalize();
        IF3DVector3 vector2 = vector.normalize();

        return vector1.x * vector2.x + vector1.y * vector2.y + vector1.z * vector2.z;
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
     *
     * returns the squared vector's length. Use for performance's sake as a sqrt() calculation isn't needed for calculating the squared vector lenght
     *
     * @return
     */
    public float getLengthSquared()
    {
        return this.x * this.x + this.y * this.y + this.z * this.z;
    }

    public IF3DVector4 convertToPoint()
    {
        return new IF3DVector4(this.x, this.y, this.z, 1.0f);
    }

    public IF3DVector4 convertToDirection()
    {
        return new IF3DVector4(this.x, this.y, this.z, 0.0f);
    }
}
