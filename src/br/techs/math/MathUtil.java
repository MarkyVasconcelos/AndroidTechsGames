package br.techs.math;

import static java.lang.Math.acos;
import java.util.Random;

/**
 * A simple utility class with a bunch of useful mathematical methods.
 * 
 * @author Vinicius G. Mendonca
 */
public class MathUtil {
	/**
	 * Represents the default maximum tolerated delta for functions that does
	 * float comparison, such as {@link Vector2D#isNormal()} or
	 * {@link MathUtil#equals(float, float)}
	 */
	public static final float FLOAT_DELTA = 0.00001f;

	/**
	 * Represents the default maximum tolerated delta for functions that does
	 * double comparison, such as {@link MathUtil#equals(double, double)}
	 */
	public static final float DOUBLE_DELTA = 0.00000000000001f;

	/**
	 * Force this vector to have the given maximum size.
	 * 
	 * @param vector
	 *            The vector to truncate
	 * @param size
	 *            The maximum vector size.
	 * @return The truncated vector
	 */
	public static Vector2D truncate(Vector2D vector, float size) {
		return (vector.getSizeSqr() > size * size) ? vector.setSize(size)
				: vector;
	}

	/**
	 * Return the distance between two vectors representing points.
	 * 
	 * @param p1
	 *            Vector 1
	 * @param p2
	 *            Vector 2
	 * @return The distance.
	 */
	public static float distance(Vector2D p1, Vector2D p2) {
		return p1.minus(p2).getSize();
	}

	/**
	 * Return the squared distance between two vectors representing points.
	 * 
	 * @param p1
	 *            Vector 1
	 * @param p2
	 *            Vector 2
	 * @return The squared distance.
	 */
	public static float distanceSqr(Vector2D p1, Vector2D p2) {
		return p1.minus(p2).getSizeSqr();
	}

	/**
	 * Calculate the angle between the two given vectors.
	 * 
	 * @param v1
	 *            Vector 1
	 * @param v2
	 *            Vector 2
	 * @return The angle between them.
	 */
	public static float angleBetween(Vector2D v1, Vector2D v2) {
		// Normalize the vectors, if needed.
		if (!v1.isNormal()) {
			v1 = v1.normalize();
		}

		if (!v2.isNormal()) {
			v2 = v2.normalize();
		}

		float angPi = (float) acos(v1.dot(v2));

		// side test
		return (v1.getY() * v2.getX() > v1.getY() * v2.getY()) ? -angPi : angPi;
	}

	/**
	 * Reflect the given ray vector, as if it collided with a surface, with the
	 * given normal.
	 * 
	 * @param ray
	 *            The ray vector
	 * @param normal
	 *            The surface normal
	 * @return The reflected vector.
	 */
	public static Vector2D reflect(Vector2D ray, Vector2D normal) {
		return ray.minus(normal.multiply(2 * ray.dot(normal)));
	}

	/**
	 * Generates a new random number between 0.0 (inclusive) and 1.0
	 * (inclusive).
	 * 
	 * @param random
	 *            The random number generator to use.
	 * @return A random number between 0.0 (inclusive) and 1.0 (inclusive).
	 */
	public static float nextFloatInclusive(Random random) {
		float r = random.nextFloat();
		return r == random.nextFloat() ? 1 : r;
	}

	/**
	 * Compare if two floats are equals within the given delta tolerance.
	 * 
	 * @param f1
	 *            A float
	 * @param f2
	 *            Another float
	 * @param delta
	 *            The delta tolerance.
	 * @return True if they are equal, false if not.
	 */
	public static boolean equals(float f1, float f2, float delta) {
		return Math.abs(f1 - f2) <= delta;
	}

	/**
	 * Compare if two floats are equals with the default delta tolerance.
	 * 
	 * @param f1
	 *            A float
	 * @param f2
	 *            Another float
	 * @return True if they are equal, false if not.
	 * @see MathUtil#FLOAT_DELTA
	 */
	public static boolean equals(float f1, float f2) {
		return equals(f1, f2, FLOAT_DELTA);
	}

	/**
	 * Compare if two floats are equals within the given delta tolerance.
	 * 
	 * @param f1
	 *            A float
	 * @param f2
	 *            Another float
	 * @param delta
	 *            The delta tolerance.
	 * @return True if they are equal, false if not.
	 */
	public static boolean equals(double f1, double f2, double delta) {
		return Math.abs(f1 - f2) <= delta;
	}

	/**
	 * Compare if two floats are equals with the default delta tolerance.
	 * 
	 * @param f1
	 *            A float
	 * @param f2
	 *            Another float
	 * @return True if they are equal, false if not.
	 * @see MathUtil#FLOAT_DELTA
	 */
	public static boolean equals(double f1, double f2) {
		return equals(f1, f2, DOUBLE_DELTA);
	}
}