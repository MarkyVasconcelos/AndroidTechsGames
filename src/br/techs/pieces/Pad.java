package br.techs.pieces;

import br.techs.math.Vector2D;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;

public class Pad extends BlockableEntity {
	private Paint wallPaint;
	private Vector2D normal;

	private int defaultDistanceFromAxis;

	private Axis thisAxis;

	public Pad(Vector2D normal, Axis axis, int defaultDistance) {
		super(null);
		wallPaint = new Paint();
		wallPaint.setColor(Color.GRAY);

		this.normal = normal;

		thisAxis = axis;
		this.defaultDistanceFromAxis = defaultDistance;

		setYCenter(-100);
	}

	public void notifyMotionEvent(float x, float y) {
		thisAxis.atualize(this, x, y);
	}

	public void setYCenter(float f) {
		setBounds(new Rect(80, (int) f - 100, 90, (int) f + 100));
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.save();
		canvas.drawRect(getBounds(), wallPaint);
		canvas.restore();
	}

	@Override
	public void processAI() {
		// Do nothing, position is handled by events
	}

	public Vector2D getNormal() {
		return normal;
	}

	public enum Axis {
		X_AXIS {
			@Override
			void atualize(Pad pad, float x, float y) {
				pad.setBounds(new Rect((int) x - 100,
						pad.defaultDistanceFromAxis, (int) x + 100,
						pad.defaultDistanceFromAxis + 10));
			}
		},
		Y_AXIS {
			@Override
			void atualize(Pad pad, float x, float y) {
				pad.setBounds(new Rect(pad.defaultDistanceFromAxis,
						(int) y - 100, pad.defaultDistanceFromAxis + 10,
						(int) y + 100));
			}

		};
		abstract void atualize(Pad pad, float x, float y);
	}

}
