package br.techs.pieces;

import android.graphics.Canvas;
import android.graphics.Rect;

public abstract class Entity {
	private Rect bounds;

	public Entity(Rect bounds) {
		this.bounds = bounds;
	}

	public Rect getBounds() {
		return bounds;
	}

	public void setBounds(Rect rect) {
		this.bounds = rect;
	}

	public abstract void draw(Canvas canvas);
	
	public abstract void processAI();
}
