package br.techs.math;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;

/**
 * Represents a vector in the 2D Cartesian coordinate space.
 * <p>
 * All mathematical operator methods comes with two signatures. The first one
 * creates a copy of the vector, prior to the operation. The second one,
 * suffixed with "Me", applies the operation the calling vector. For example:
 * 
 * <pre>
 * Vector2D v1 = new Vector(10, 20);
 * Vector2D v2 = new Vector(20, 10);
 * Vector2D v3 = v1.sum(v2); // v3 is now the sum of v1 and v2, both vectors remain
 * // unchanged
 * v1.sumMe(v2); // v1 is now the sum of its previous value and v2.
 * </pre>
 * 
 * Most part of operations also returns the vector that suffered the operation,
 * allowing invocation chaining to be used. So, it's possible to write more
 * concise formulas such as:
 * 
 * <pre>
 * Vector2D v4 = v1.plus(v2).minusMe(5).multiplyMe(3);
 * </pre>
 * 
 * The mathematical operator names are also compatible with <a
 * href="http://groovy.codehaus.org">Groovy Script Language</a>, allowing them
 * to act as normal operators.
 * 
 * @author Vinicius Godoy de Mendonca
 */
public final class Vector2D implements Cloneable, Comparable<Vector2D> {
	private float x;
	private float y;

	/**
	 * Creates a new vector. It will be the zero vector.
	 */
	public Vector2D() {
		set(0, 0);
	}

	/**
	 * Creates a new vector, with the given x and y values.
	 * 
	 * @param x
	 *            The value in the x axis.
	 * @param y
	 *            The value in the x axis.
	 */
	public Vector2D(float x, float y) {
		set(x, y);
	}

	/**
	 * Creates a new vector, that is a copy of the given vector.
	 * 
	 * @param other
	 *            The vector to copy values.
	 */
	public Vector2D(Vector2D other) {
		set(other.x, other.y);
	}

	/**
	 * Create a new vector with the given size, and with the given angle.
	 * 
	 * @param size
	 *            The vector size.
	 * @param angle
	 *            The angle, in radians.
	 * @return The newly created vector.
	 */
	public static Vector2D createBySizeAngle(float size, double angle) {
		return new Vector2D((float) cos(angle) * size, (float) sin(angle)
				* size);
	}

	/**
	 * Sets the x and y values of this vector.
	 * 
	 * @param newX
	 *            The new x value.
	 * @param newY
	 *            The new y value.
	 * @return This vector.
	 */
	public Vector2D set(float newX, float newY) {
		x = newX;
		y = newY;
		return this;
	}

	/**
	 * Replaces the x and y values of this vector with the values of the given
	 * vector.
	 * 
	 * @param other
	 *            The vector to take values from.
	 * @return This vector.
	 */
	public Vector2D set(Vector2D other) {
		return set(other.x, other.y);
	}

	/**
	 * Changes the size of this vector in the x axis.
	 * 
	 * @param x
	 *            The new x.
	 */
	public void setX(float x) {
		this.x = x;
	}

	/**
	 * Changes the size of this vector in the x axis.
	 * 
	 * @param x
	 *            The new x.
	 */
	public void setY(float y) {
		this.y = y;
	}

	/**
	 * @return the length of this vector in the x axis.
	 */
	public float getX() {
		return x;
	}

	/**
	 * @return the length of this vector in the y axis.
	 */
	public float getY() {
		return y;
	}

	/**
	 * @return The size of this vector. To compare two vector sizes, consider
	 *         using the {@link #getSizeSqr()} function instead. Also, use the
	 *         {@link #isZero()} and {@link #isNormal()} functions to test if is
	 *         his vector is a normal or a zero vector.
	 * 
	 * @see #getSizeSqr()
	 * @see #isNormal()
	 * @see #isZero()()
	 * @see #compareTo(Vector2D)
	 * @see MathUtil#distance(Vector2D, Vector2D)
	 * @see MathUtil#distanceSqr(Vector2D, Vector2D)
	 */
	public float getSize() {
		return (float) sqrt(getSizeSqr());
	}

	/**
	 * @return The squared size of this vector. This function is faster than
	 *         {@link #getSize()}, since it avoids a square root calculation.
	 *         calculation.
	 * 
	 * @see #getSize()
	 * @see #isNormal()
	 * @see #isZero()()
	 * @see #compareTo(Vector2D)
	 * @see MathUtil#distance(Vector2D, Vector2D)
	 * @see MathUtil#distanceSqr(Vector2D, Vector2D)
	 */
	public float getSizeSqr() {
		return x * x + y * y;
	}

	/**
	 * Test if this vector is a zero vector.
	 * 
	 * @return True if this vector length is zero.
	 */
	public boolean isZero() {
		return MathUtil.equals(getSizeSqr(), 0);
	}

	/**
	 * Test if this vector is a normal vector.
	 * 
	 * @return True if this vector length is one.
	 */
	public boolean isNormal() {
		return MathUtil.equals(getSizeSqr(), 1);
	}

	/**
	 * Changes the size of this vector. Does not work in zero vectors.
	 * 
	 * @param newSize
	 *            The new vector size.
	 * @return This vector, with the new size. The zero vector remains
	 *         unchanged.
	 * @see MathUtil#truncate(Vector2D, double)
	 */
	public Vector2D setSize(float newSize) {
		if (isZero()) {
			// TODO Add a log warning here
			return this;
		}

		float relation = newSize / getSize();
		x *= relation;
		y *= relation;
		return this;
	}

	/**
	 * @return The angle between this vector and the x-axis.
	 * @see MathUtil#angleBetween(Vector2D, Vector2D)
	 */
	public float getAngleX() {
		return (float) atan2(y, x);
	}

	/**
	 * Rotates this vector in the given angle.
	 * 
	 * @param angle
	 *            The angle, in bytes
	 * @return This vector.
	 */
	public Vector2D rotateMe(double angle) {
		float s = (float) sin(angle);
		float c = (float) cos(angle);

		float newX = x * c - y * s;
		float newY = x * s + y * c;

		x = newX;
		y = newY;

		return this;
	}

	/**
	 * Calculate a rotated version of this vector.
	 * 
	 * @param angle
	 *            The angle, in degrees
	 * @return A new vector, equal to this one rotated in angle degrees.
	 */
	public Vector2D rotate(double angle) {
		return clone().rotateMe(angle);
	}

	/**
	 * Returns a perpendicular vector. This function is equivalent to calling
	 * {@link #rotate(float)} with 90 degrees, but much faster and precise.
	 * 
	 * @return a perpendicular vector (rotated 90º degrees).
	 */
	public Vector2D getPerpendicular() {
		return new Vector2D(-y, x);
	}

	/**
	 * Normalizes this vector. If this vector is the zero vector, the size
	 * remains unchanged.
	 * 
	 * @return This vector normalized.
	 */
	public Vector2D normalizeMe() {
		float sizeSqr = getSizeSqr();

		if (isZero()) {
			// TODO add log message here
			return this;
		}

		// Already a normal vector
		if (isNormal()) {
			return this;
		}

		return divMe((float) sqrt(sizeSqr));
	}

	/**
	 * Creates a new vector, that is equal to this vector normalized. If this
	 * vector is the zero vector, a new copy of the zero vector will be
	 * returned.
	 * 
	 * @return A normalized copy of this vector.
	 */
	public Vector2D normalize() {
		return clone().normalizeMe();
	}

	/**
	 * Adds the given vector in this vector.
	 * <p>
	 * Roughly equivalent to the primitive type += operator.
	 * 
	 * @param other
	 *            The vector to add.
	 * @return This vector
	 * @see #plus(float)
	 */
	public Vector2D plusMe(Vector2D other) {
		x += other.x;
		y += other.y;
		return this;
	}

	/**
	 * Creates a new vector, that is the sum of this vector and the given
	 * vector.
	 * <p>
	 * Roughly equivalent to the primitive type + operator.
	 * 
	 * @param other
	 *            The vector to add.
	 * @return The added copy.
	 * @see #plusMe(float)
	 */
	public Vector2D plus(Vector2D other) {
		return clone().plusMe(other);
	}

	/**
	 * Subtracts this vector by the given vector.
	 * <p>
	 * Roughly equivalent to the primitive type -= operator.
	 * 
	 * @param other
	 *            The vector to subtract.
	 * @return this vector.
	 * @see #minus(float)
	 * @see MathUtil#distance(Vector2D, Vector2D)
	 * @see MathUtil#distanceSqr(Vector2D, Vector2D)
	 */
	public Vector2D minusMe(Vector2D other) {
		x -= other.x;
		y -= other.y;
		return this;
	}

	/**
	 * Creates a new vector, that is the subtraction of this vector by the given
	 * vector.
	 * <p>
	 * Roughly equivalent to the primitive type - operator.
	 * 
	 * @param other
	 *            The vector to subtract.
	 * @return The subtracted copy.
	 * @see #minusMe(float)
	 * @see MathUtil#distance(Vector2D, Vector2D)
	 * @see MathUtil#distanceSqr(Vector2D, Vector2D)
	 */
	public Vector2D minus(Vector2D other) {
		return clone().minusMe(other);
	}

	/**
	 * Multiplies this vector by the given scalar
	 * <p>
	 * Roughly equivalent to the primitive type *= operator.
	 * 
	 * @param scalar
	 *            The scalar to multiply.
	 * @return this vector.
	 * @see #multiply(float)
	 */
	public Vector2D multiplyMe(float scalar) {
		x *= scalar;
		y *= scalar;
		return this;
	}

	/**
	 * Creates a new vector, that is the multiplication of this vector by the
	 * given scalar
	 * <p>
	 * Roughly equivalent to the primitive type * operator.
	 * 
	 * @param scalar
	 *            The scalar to multiply.
	 * @return The multiplied copy.
	 * @see #multiplyMe(float)
	 */
	public Vector2D multiply(float scalar) {
		return clone().multiplyMe(scalar);
	}

	/**
	 * Divides this vector by the given scalar
	 * <p>
	 * Roughly equivalent to the primitive type /= operator.
	 * 
	 * @param scalar
	 *            The scalar to divide.
	 * @return this vector.
	 * @throws DivideByZeroException
	 *             If the scalar is zero
	 * @see #div(float)
	 */
	public Vector2D divMe(float scalar) {
		if (scalar == 0) {
			throw new ArithmeticException();
		}

		x /= scalar;
		y /= scalar;
		return this;
	}

	/**
	 * Creates a new vector, that is the division of this vector by the given
	 * scalar
	 * <p>
	 * Roughly equivalent to the primitive type / operator.
	 * 
	 * @param scalar
	 *            The scalar to divide.
	 * @return The divided copy.
	 * 
	 * @throws DivideByZeroException
	 *             If the scalar is zero.
	 * @see #divMe(float)
	 */
	public Vector2D div(float scalar) {
		return clone().divMe(scalar);
	}

	/**
	 * Negates this vector.
	 * 
	 * @return This vector, after negation.
	 * @see #negative()
	 */
	public Vector2D negativeMe() {
		x = -x;
		y = -y;
		return this;
	}

	/**
	 * @return A negated copy of this vector
	 * @see #negate()
	 */
	public Vector2D negative() {
		return clone().negativeMe();
	}

	/**
	 * Two vectors will be equal if they have equal sizes of x and y. This
	 * method makes strict float comparison. For a more relaxed comparison, see
	 * {@link #similar(Vector2D)} method.
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == null || obj.getClass() != getClass()) {
			return false;
		}

		if (obj == this) {
			return true;
		}

		Vector2D other = (Vector2D) obj;
		return x == other.x && y == other.y;
	}

	/**
	 * Similar to equals, but uses {@link MathUtil#equals(Object)} to compare x
	 * and y.
	 * 
	 * @param other
	 *            Othe vector.
	 * @return True if the two are similar, false if not.
	 */
	public boolean similar(Vector2D other) {
		return MathUtil.equals(x, other.x) && MathUtil.equals(y, other.y);
	}

	/**
	 * Calculate the dot product between this vector and the given vector.
	 * 
	 * @param other
	 *            Other vector.
	 * @return The dot product.
	 * 
	 * @see MathUtil#angleBetween(Vector2D, Vector2D)
	 */
	public float dot(Vector2D other) {
		float dp = x * other.x + y * other.y;

		// Remove float imprecision
		if (dp >= 1.0) {
			dp = 1.0f;
		} else if (dp <= -1.0) {
			dp = -1.0f;
		}
		return dp;
	}

	/**
	 * A string representation of this vector.
	 * <p>
	 * E.g. x: 1.000 y: 2.291
	 */
	@Override
	public String toString() {
		return String.format("x: %.3f y: %.3f", x, y);
	}

	/**
	 * Creates and return a copy of this vector.
	 */
	@Override
	public Vector2D clone() {
		return new Vector2D(this);
	}

	/**
	 * Returns the value at the given index.
	 * 
	 * @param index
	 *            0 for x, 1 for y. If 1 is supplied, it will return 1 value for
	 *            z coordinate, assuming that user wants an homogeneous system
	 *            coordinate.
	 * 
	 * @return The value at the given index.
	 */
	public float getAt(int index) {
		if (index == 0) {
			return x;
		}
		if (index == 1) {
			return y;
		}
		if (index == 2) {
			// TODO Add an info log here.
			return 1;
		}
		throw new ArrayIndexOutOfBoundsException();
	}

	/**
	 * Puts the value at the given index.
	 * 
	 * @param index
	 *            0 for x, 1 for y. Coordinates in index 2 will be discarded.
	 * @param value
	 *            The value to put.
	 * @return The value.
	 */
	public float putAt(int index, float value) {
		if (index == 0) {
			x = value;
		} else if (index == 1) {
			y = value;
		} else if (index != 2) {
			throw new ArrayIndexOutOfBoundsException();
		}

		return value;
	}

	/**
	 * Convert this vector to a primitive float array.
	 * 
	 * @return A primitive float array filled with x and y values.
	 */
	public float[] toArray() {
		return new float[] { x, y };
	}

	/**
	 * Convert this vector to a primitive float array in an homogeneous
	 * coordinate system.
	 * 
	 * @return A primitive float array filled with x and y values, and 1 for z
	 *         value.
	 */
	public float[] to3DArray() {
		return new float[] { x, y, 1 };
	}

	/**
	 * Compare two vectors. Vectors are compared based on their size.
	 * 
	 * @see Comparable#compareTo(Object)
	 * @return > 0 if this vector size is bigger than the given vector size <br>
	 *         < 0 if this vector size is smaller than the given vector size <br>
	 *         0 if both sizes are equal.
	 */
	@Override
	public int compareTo(Vector2D other) {
		if (getSizeSqr() < other.getSizeSqr()) {
			return -1;
		}
		if (getSizeSqr() > other.getSizeSqr()) {
			return 1;
		}
		return 0;
	}
}
