package br.techs.pieces;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import br.techs.math.Vector2D;

public class Pad extends BlockableEntity {
	private Paint wallPaint;

	private int defaultDistanceFromAxis;

	private Axis thisAxis;

	public Pad(Vector2D normal, Axis axis, int defaultDistance) {
		super(new Rect(), normal);
		wallPaint = new Paint();
		wallPaint.setColor(Color.GRAY);

		thisAxis = axis;
		this.defaultDistanceFromAxis = defaultDistance;

	}

	public void notifyMotionEvent(float x, float y) {
		thisAxis.atualize(this, x, y);
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

	public enum Axis {
		X_AXIS {
			@Override
			void atualize(Pad pad, float x, float y) {
				pad.setBounds(new Rect((int) x - 150,
						pad.defaultDistanceFromAxis, (int) x + 150,
						pad.defaultDistanceFromAxis + 10));
			}
		},
		Y_AXIS {
			@Override
			void atualize(Pad pad, float x, float y) {
				pad.setBounds(new Rect(pad.defaultDistanceFromAxis,
						(int) y - 150, pad.defaultDistanceFromAxis + 10,
						(int) y + 150));
			}

		};
		abstract void atualize(Pad pad, float x, float y);
	}

}
