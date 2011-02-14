package br.techs;

import java.util.ArrayList;
import java.util.List;

import br.techs.pieces.Entity;

import android.graphics.Canvas;

public class PiecesManager {
	private List<Entity> pieces;

	public PiecesManager() {
		pieces = new ArrayList<Entity>();
	}

	public void addPiece(Entity ent) {
		pieces.add(ent);
	}

	public void draw(Canvas canvas) {
		for (Entity ent : pieces)
			ent.draw(canvas);
	}

	public List<Entity> getPieces() {
		return pieces;
	}

	public void processAI() {
		for (Entity ent : pieces)
			ent.processAI();
	}
}
