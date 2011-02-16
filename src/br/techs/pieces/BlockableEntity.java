package br.techs.pieces;

import android.graphics.Rect;
import br.techs.math.Vector2D;

public abstract class BlockableEntity extends Entity {
	private Vector2D normal;

	public BlockableEntity(Rect bounds, Vector2D normal) {
		super(bounds);
		this.normal = normal;
	}

	public void setNormal(Vector2D normal) {
		this.normal = normal;
	}

	public Vector2D getNormal() {
		return normal;
	}
}