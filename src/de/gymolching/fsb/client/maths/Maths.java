package de.gymolching.fsb.client.maths;

import de.gymolching.fsb.client.api.FSBPosition;

public class Maths {
	/** Radius of the top or bottom portion of the seat */
	private static final double SEAT_RADIUS = 10;

	/**
	 * Height of the seat measured from the middle of the bottom to the middle
	 * of the top
	 */
	private static final double SEAT_HEIGHT = 10;

	/** Minimum length of one foot */
	private static final double MIN_FOOT_LENGTH = 7;

	/** MAX * startLength() = Maximum length of foot */
	private static final double MAX = 1.5;

	private static Vector3 Va = new Vector3(-Math.cos(30 * Math.PI / 180) * SEAT_RADIUS, 0,
			-Math.sin(30 * Math.PI / 180) * SEAT_RADIUS);
	private static Vector3 Vb = new Vector3(0, 0, SEAT_RADIUS);
	private static Vector3 Vc = new Vector3(Math.cos(30 * Math.PI / 180) * SEAT_RADIUS, 0,
			-Math.sin(30 * Math.PI / 180) * SEAT_RADIUS);
	private static Vector3 Vd = new Vector3(0, -SEAT_HEIGHT, -SEAT_RADIUS);
	private static Vector3 Ve = new Vector3(-Math.cos(30 * Math.PI / 180) * SEAT_RADIUS, -SEAT_HEIGHT,
			Math.sin(30 * Math.PI / 180) * SEAT_RADIUS);
	private static Vector3 Vf = new Vector3(Math.cos(30 * Math.PI / 180) * SEAT_RADIUS, -SEAT_HEIGHT,
			Math.sin(30 * Math.PI / 180) * SEAT_RADIUS);
	private static Vector3 Vab;
	private static Vector3 Vbb;
	private static Vector3 Vcb;
	private static Vector3 Vdb;
	private static Vector3 Veb;
	private static Vector3 Vfb;

	/***
	 * Calculates arm lengths
	 * 
	 * @param x
	 *            X Position of chair
	 * @param y
	 *            Y Position of chair
	 * @param z
	 *            Z Position of chair
	 * @param rotX
	 *            rotation around X axis of chair
	 * @param rotY
	 *            rotation around Y axis of chair
	 * @param rotZ
	 *            rotation around Z axis of chair
	 * @return new FSBPosition containing the calculated arm lengths
	 */
	public static FSBPosition calculatePosition(double x, double y, double z, double rotX, double rotY, double rotZ) {
		Matrix3 rotMatrix = Matrix3.getRotationMatrix(rotX * Math.PI / 180, rotY * Math.PI / 180, rotZ * Math.PI / 180);
		Vab = rotMatrix.multiply(Va);
		Vbb = rotMatrix.multiply(Vb);
		Vcb = rotMatrix.multiply(Vc);
		Vector3 m = new Vector3(-x, -y, -z);
		Vdb = m.add(Vd);
		Veb = m.add(Ve);
		Vfb = m.add(Vf);
		Vector3 newVab = Vab.invert();
		Vector3 newVbb = Vbb.invert();
		Vector3 newVcb = Vcb.invert();
		double length1 = Veb.add(newVbb).getLength() - MIN_FOOT_LENGTH;
		double length2 = Vfb.add(newVbb).getLength() - MIN_FOOT_LENGTH;

		double length3 = Vfb.add(newVcb).getLength() - MIN_FOOT_LENGTH;
		double length4 = Vdb.add(newVcb).getLength() - MIN_FOOT_LENGTH;
		double length5 = Vdb.add(newVab).getLength() - MIN_FOOT_LENGTH;
		double length6 = Veb.add(newVab).getLength() - MIN_FOOT_LENGTH;

		double multiplyer = 240 / (MAX * getStartLength());

		return new FSBPosition((int) Math.round(length1 * multiplyer), (int) Math.round((length2 * multiplyer)),
				(int) Math.round(length3 * multiplyer), (int) Math.round(length4 * multiplyer),
				(int) Math.round(length5 * multiplyer), (int) Math.round(length6 * multiplyer));
	}

	public static double getStartLength() {
		return (Vd.add(Va.invert()).getLength() - MIN_FOOT_LENGTH);

	}
}
