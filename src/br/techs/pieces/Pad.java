package br.techs.pieces;

import br.techs.math.Vector2D;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Pad extends BlockableEntity {
	private Paint wallPaint;
	private Vector2D normal;

	public Pad(Vector2D normal) {
		super(null);
		wallPaint = new Paint();
		wallPaint.setColor(Color.GRAY);

		this.normal = normal;

		setYCenter(600);
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
		// Do nothing, walls don't think
	}

	public Vector2D getNormal() {
		return normal;
	}

}
