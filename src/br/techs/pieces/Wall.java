package br.techs.pieces;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import br.techs.math.Vector2D;

public class Wall extends BlockableEntity {
	private Paint wallPaint;
	private HitListener listener;

	public Wall(Rect bounds, Vector2D normal) {
		super(bounds, normal);
		wallPaint = new Paint();
		wallPaint.setColor(Color.GRAY);
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

	public void addHitListener(HitListener listener) {
		this.listener = listener;
	}

	public interface HitListener {
		public void notifyHited();
	}

	public void notifyHit() {
		if (listener != null)
			listener.notifyHited();
	}

}
