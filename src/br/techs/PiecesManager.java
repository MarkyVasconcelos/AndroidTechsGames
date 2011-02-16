package br.techs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import android.graphics.Canvas;
import br.techs.pieces.Entity;

public class PiecesManager {
	private List<Entity> pieces;
	
	private Collection<Entity> tmpList;

	public PiecesManager() {
		pieces = new ArrayList<Entity>();
		tmpList = Collections.synchronizedCollection(new ArrayList<Entity>());
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
