package de.gymolching.fsb.client.maths;

public class Matrix3 {
	public double[] elements;

	/**
	 * Creates zeroed-out matrix
	 */
	public Matrix3() {
		// Allocate 3 by 3 matrix
		this.elements = new double[3 * 3];

		// Zero out each matrix's element
		for (int i = 0; i < this.elements.length; i++) {
			this.elements[i] = 0.0;
		}
	}

	/**
	 * Creates Matrix with specific diagonal value
	 * 
	 * @param diagonal
	 */
	public Matrix3(double diagonal) {
		this();

		this.elements[0] = diagonal;
		this.elements[4] = diagonal;
		this.elements[8] = diagonal;
	}

	/**
	 * Creates "vector matrix"
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public Matrix3(double x, double y, double z) {
		this();

		this.elements[0] = x;
		this.elements[3] = y;
		this.elements[6] = z;
	}

	/**
	 * Returns the multiplication result of this matrix and the parameter (this
	 * * parameter)
	 * 
	 * @param matrix
	 * @return
	 */
	public Matrix3 multiply(Matrix3 matrix) {
		Matrix3 result = new Matrix3();
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				float sum = 0.0f;
				for (int e = 0; e < 3; e++) {
					sum += this.elements[e + column * 3]
							* matrix.elements[row + e * 3];
				}

				result.elements[row + column * 3] = sum;
			}
		}

		return result;
	}

	/**
	 * Returns the multiplication result of this matrix and the parameter vector
	 * (this * vector)
	 * 
	 * @param vector
	 * @return
	 */
	public Vector3 multiply(Vector3 vector) {
		Matrix3 vectorMatrix = new Matrix3();
		vectorMatrix.elements[0] = vector.x;
		vectorMatrix.elements[3] = vector.y;
		vectorMatrix.elements[6] = vector.z;

		Matrix3 resultMatrix = this.multiply(vectorMatrix);

		Vector3 result = new Vector3(resultMatrix.elements[0],
				resultMatrix.elements[3], resultMatrix.elements[6]);
		return result;
	}

	public void printToScreen() {
		System.out.println("[Matrix3] " + this + "\n{");
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				System.out.print(this.elements[3 * row + column] + ", ");
			}
			System.out.println("");
		}
		System.out.println("}");
	}

	/**
	 * Creates new Rotation matrix
	 * 
	 * @param rotX
	 *            rotation angle around x axis in radians
	 * @param rotY
	 *            "
	 * @param rotZ
	 *            "
	 * 

	 */
	public static Matrix3 getRotationMatrix(double rotX, double rotY,
			double rotZ) {
		Matrix3 matRotX = new Matrix3(1.0);
		Matrix3 matRotY = new Matrix3(1.0);
		Matrix3 matRotZ = new Matrix3(1.0);

		double cosX = Math.cos(rotX);
		double sinX = Math.sin(rotX);

		double cosY = Math.cos(rotY);
		double sinY = Math.sin(rotY);

		double cosZ = Math.cos(rotZ);
		double sinZ = Math.sin(rotZ);

		matRotX.elements[1 + 1 * 3] = +cosX;
		matRotX.elements[1 + 2 * 3] = -sinX;
		matRotX.elements[2 + 1 * 3] = +sinX;
		matRotX.elements[2 + 2 * 3] = +cosX;

		matRotY.elements[0 + 0 * 3] = +cosY;
		matRotY.elements[0 + 2 * 3] = +sinY;
		matRotY.elements[2 + 0 * 3] = -sinY;
		matRotY.elements[2 + 2 * 3] = +cosY;

		matRotZ.elements[0 + 0 * 3] = +cosZ;
		matRotZ.elements[1 + 0 * 3] = +sinZ;
		matRotZ.elements[0 + 1 * 3] = -sinZ;
		matRotZ.elements[1 + 1 * 3] = +cosZ;

		return matRotX.multiply(matRotY.multiply(matRotZ));
	}
}
