package gui_simple.src;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;

/**
 * @author Dominik
 * @created 16.03.2015
 */
public class IF3DMatrix4
{
	private float[] elements;

	/**
	 * initializes new empty (all values are 0) IF3DMatrix4
	 */
	public IF3DMatrix4()
	{
		this.elements = new float[4 * 4];

		// Zero out the matrix
		for (int i = 0; i < 4 * 4; i++)
		{
			this.elements[i] = 0.0f;
		}
	}

	/**
	 * initializes new IF3DMatrix4 with a diagonal value accoring to parameter
	 * 
	 * @param diagonal
	 *            diagonal value to store in the matrix
	 */
	public IF3DMatrix4(float diagonal)
	{
		this.elements = new float[4 * 4];

		// Zero out the matrix
		for (int i = 0; i < 4 * 4; i++)
		{
			this.elements[i] = 0.0f;
		}

		// Set the diagonal to the parameter's value
		for (int i = 0; i < 4; i++)
		{
			this.elements[i + i * 4] = diagonal;
		}
	}

	/**
	 * resets this matrix to an identity matrix
	 */
	public void loadIdentity()
	{
		// Zero out the matrix
		for (int i = 0; i < 4 * 4; i++)
		{
			this.elements[i] = 0.0f;
		}

		// Set the diagonal to 1.0f
		for (int i = 0; i < 4; i++)
		{
			this.elements[i + i * 4] = 1.0f;
		}
	}

	/**
	 * multiplies this matrix and the other matrix (this * other), modifying this matrix
	 * 
	 * @param other
	 * @return
	 */
	public IF3DMatrix4 multiply(IF3DMatrix4 other)
	{
		IF3DMatrix4 mat4 = this.getMultiplicationResult(other);

		for (int i = 0; i < 4 * 4; i++)
		{
			this.elements[i] = mat4.elements[i];
		}

		return this;
	}

	/**
	 * multiplies this matrix and the other matrix (this * other)
	 * 
	 * @param other
	 * @return new Matrix containing the multiplicationResult
	 */
	public IF3DMatrix4 getMultiplicationResult(IF3DMatrix4 other)
	{
		// this matrix will store our result
		IF3DMatrix4 mat4 = new IF3DMatrix4(1.0f);

		for (int column = 0; column < 4; column++)
		{
			for (int row = 0; row < 4; row++)
			{
				float sum = 0.0f;
				for (int e = 0; e < 4; e++)
				{
					sum += this.elements[row + e * 4] * other.elements[e + column * 4];
				}

				mat4.elements[row + column * 4] = sum;
			}
		}

		return mat4;
	}

	/**
	 * converts this matrix's data to a floatbuffer
	 * 
	 * @return
	 */
	public FloatBuffer toFloatBuffer()
	{
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		for (int column = 0; column < 4; column++)
		{
			for (int row = 0; row < 4; row++)
			{
				buffer.put(this.elements[row + column * 4]);
			}
		}

		buffer.flip();

		return buffer;
	}

    /**
     * returns a matrix with 3x3
     *
     * @return new matrix
     */
    /*public Matrix3x3(){

    }*/

	/**
	 * returns exact copy of this matrix
	 * 
	 * @return new instance equalling this matrix
	 */
	public IF3DMatrix4 getCopy()
	{
		IF3DMatrix4 copy = new IF3DMatrix4();

		for (int i = 0; i < 4 * 4; i++)
		{
			copy.elements[i] = this.elements[i];
		}

		return copy;
	}

	public void print()
	{
		System.out.println("==============================");
		for (int column = 0; column < 4; column++)
		{
			for (int row = 0; row < 4; row++)
			{
				System.out.print(this.elements[row + column * 4] + ", ");
			}
			System.out.println();
		}
		System.out.println("==============================");
	}

	/**
	 * returns orthographic projection matrix
	 * 
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param near
	 * @param far
	 * @return
	 */
	public static IF3DMatrix4 getOrthographicMatrix(float left, float right, float bottom,
			float top, float near, float far)
	{
		IF3DMatrix4 ortho = new IF3DMatrix4(1.0f);

		ortho.elements[0 + 0 * 4] = +2.0f / (right - left);
		ortho.elements[1 + 1 * 4] = +2.0f / (top - bottom);
		ortho.elements[2 + 2 * 4] = -2.0f / (far - near);
		ortho.elements[0 + 3 * 4] = (right + left) / (right - left);
		ortho.elements[1 + 3 * 4] = (top + bottom) / (bottom - top);// (top - bottom); // (bottom - top)? FIXME
		ortho.elements[2 + 3 * 4] = (far + near) / (far - near);

		return ortho;
	}

	/**
	 * returns perspective matrix
	 * 
	 * @param fov
	 * @param aspectRatio
	 * @param znear
	 * @param zfar
	 * @return
	 */
	public static IF3DMatrix4 getPerspectiveMatrix(float fov, float aspectRatio, float znear,
			float zfar)
	{
		IF3DMatrix4 perspective = new IF3DMatrix4(1.0f);

		float q = 1.0f / (float) Math.tan(Math.toRadians(0.5f * fov));
		float a = q / aspectRatio;

		float b = (znear + zfar) / (znear - zfar);
		float c = (2.0f * znear * zfar) / (znear - zfar);

		perspective.elements[0 + 0 * 4] = a;
		perspective.elements[1 + 1 * 4] = q;
		perspective.elements[2 + 2 * 4] = b;
		perspective.elements[3 + 2 * 4] = -1.0f;
		perspective.elements[2 + 3 * 4] = c;

		return perspective;
	}

	public static IF3DMatrix4 getLookAtMatrix(IF3DVector3 eye, IF3DVector3 center, IF3DVector3 up)
	{
		IF3DMatrix4 result = new IF3DMatrix4(1.0f);

		IF3DVector3 forward = center.getSubtractionResultVector(eye);
		forward.normalize();
		
		IF3DVector3 side = forward.crossProduct(up);
		side.normalize();
		
		up = side.crossProduct(forward);
		
		result.elements[0 + 0 * 4] = side.x;
		result.elements[0 + 1 * 4] = side.y;
		result.elements[0 + 2 * 4] = side.z;
		
		result.elements[1 + 0 * 4] = up.x;
		result.elements[1 + 1 * 4] = up.y;
		result.elements[1 + 2 * 4] = up.z;
				
		result.elements[2 + 0 * 4] = -forward.x;
		result.elements[2 + 1 * 4] = -forward.y;
		result.elements[2 + 2 * 4] = -forward.z;
		
		// TODO: verify implementation
		// IF3DVector3 f = center.getSubtractionResultVector(eye).normalize();
		// IF3DVector3 u = up.getNormalizedVector();
		// IF3DVector3 s = f.crossProduct(u).normalize();
		// u = s.crossProduct(f);
		//
		// result.elements[0 + 0 * 4] = s.x;
		// result.elements[0 + 1 * 4] = s.y;
		// result.elements[0 + 2 * 4] = s.z;
		//
		// result.elements[1 + 0 * 4] = u.x;
		// result.elements[1 + 1 * 4] = u.y;
		// result.elements[1 + 2 * 4] = u.z;
		//
		// result.elements[2 + 0 * 4] = -f.x;
		// result.elements[2 + 1 * 4] = -f.y;
		// result.elements[2 + 2 * 4] = -f.z;

		return result.multiply(IF3DMatrix4.getTranslationMatrix(-eye.x, -eye.y, -eye.z));
	}

	/**
	 * returns new translation matrix
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static IF3DMatrix4 getTranslationMatrix(float x, float y, float z)
	{
		IF3DMatrix4 result = new IF3DMatrix4(1.0f);

		result.elements[0 + 3 * 4] = x;
		result.elements[1 + 3 * 4] = y;
		result.elements[2 + 3 * 4] = z;

		return result;
	}

	/**
	 * returns new Translation matrix
	 * 
	 * @param translation
	 *            a 3D vector describing the translation
	 * @return
	 */
	public static IF3DMatrix4 getTranslationMatrix(IF3DVector3 translation)
	{
		return getTranslationMatrix(translation.x, translation.y, translation.z);
	}

	/**
	 * returns new scale matrix
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static IF3DMatrix4 getScaleMatrix(float x, float y, float z)
	{
		IF3DMatrix4 result = new IF3DMatrix4(1.0f);

		result.elements[0 + 0 * 4] = x;
		result.elements[1 + 1 * 4] = y;
		result.elements[2 + 2 * 4] = z;

		return result;
	}

	/**
	 * returns new scale matrix
	 * 
	 * @param scale
	 *            3D vector describing the amount of scaling on each axis
	 * @return
	 */
	public static IF3DMatrix4 getScaleMatrix(IF3DVector3 scale)
	{
		return getScaleMatrix(scale.x, scale.y, scale.z);
	}

	/**
	 * returns the rotation matrix
	 * 
	 * @param angle
	 *            amount of rotation
	 * @param x
	 *            rotation amount around x
	 * @param y
	 *            rotation amount around y
	 * @param z
	 *            rotation amount around z
	 * @return
	 */
	public static IF3DMatrix4 getRotationMatrix(float angle, float x, float y, float z)
	{
		float radiens = (float) Math.toRadians(angle);
		IF3DMatrix4 matRotX = new IF3DMatrix4(1.0f);
		IF3DMatrix4 matRotY = new IF3DMatrix4(1.0f);
		IF3DMatrix4 matRotZ = new IF3DMatrix4(1.0f);

		float cosX = (float) Math.cos(radiens * x);
		float sinX = (float) Math.sin(radiens * x);

		float cosY = (float) Math.cos(radiens * y);
		float sinY = (float) Math.sin(radiens * y);

		float cosZ = (float) Math.cos(radiens * z);
		float sinZ = (float) Math.sin(radiens * z);

		matRotX.elements[1 + 1 * 4] = +cosX;
		matRotX.elements[1 + 2 * 4] = -sinX;
		matRotX.elements[2 + 1 * 4] = +sinX;
		matRotX.elements[2 + 2 * 4] = +cosX;

		matRotY.elements[0 + 0 * 4] = +cosY;
		matRotY.elements[0 + 2 * 4] = +sinY;
		matRotY.elements[2 + 0 * 4] = -sinY;
		matRotY.elements[2 + 2 * 4] = +cosY;

		matRotZ.elements[0 + 0 * 4] = +cosZ;
		matRotZ.elements[1 + 0 * 4] = +sinZ;
		matRotZ.elements[0 + 1 * 4] = -sinZ;
		matRotZ.elements[1 + 1 * 4] = +cosZ;

		return matRotX.multiply(matRotY.multiply(matRotZ));
	}

	/**
	 * returns the rotation matrix
	 * 
	 * @param angle
	 *            amount of rotation
	 * @param axis
	 *            3D vector describing the axis rotation
	 * @return
	 */
	public static IF3DMatrix4 getRotationMatrix(float angle, IF3DVector3 axis)
	{
		return getRotationMatrix(angle, axis.x, axis.y, axis.z);
	}
}
