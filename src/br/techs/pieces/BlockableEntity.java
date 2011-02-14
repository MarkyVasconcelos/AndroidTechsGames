package br.techs.pieces;

import android.graphics.Rect;
import br.techs.math.Vector2D;

public abstract class BlockableEntity extends Entity {
	public BlockableEntity(Rect bounds) {
		super(bounds);
	}

	public abstract Vector2D getNormal();

}
