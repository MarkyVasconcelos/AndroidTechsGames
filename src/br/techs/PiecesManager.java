package br.techs;

import java.util.ArrayList;
import java.util.List;

import br.techs.pieces.Entity;

import android.graphics.Canvas;

public class PiecesManager {
	private List<Entity> pieces;
	
	private List<Entity> tmpList;

	public PiecesManager() {
		pieces = new ArrayList<Entity>();
		tmpList = new ArrayList<Entity>();
	}

	public void addPiece(Entity ent) {
		tmpList.add(ent);
//		pieces.add(ent);
	}

	public void draw(Canvas canvas) {
		for (Entity ent : pieces)
			ent.draw(canvas);
	}

	public List<Entity> getPieces() {
		return pieces;
	}

	public void processAI() {
		pieces.addAll(tmpList);
		tmpList.clear();
		for (Entity ent : pieces)
			ent.processAI();
	}
}
