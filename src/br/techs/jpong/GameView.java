package br.techs.jpong;

import java.util.Random;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import br.techs.PiecesManager;
import br.techs.hud.Scoreboard;
import br.techs.math.Vector2D;
import br.techs.pieces.Ball;
import br.techs.pieces.Pad;
import br.techs.pieces.Pad.Axis;
import br.techs.pieces.Wall;

public class GameView extends View implements Runnable {
	private int width = 600, height = 1024;
	private Paint background;
	private Handler handler;

	private PiecesManager manager;

	private Pad northPad;
	private Pad southPad;

	public GameView(Context context) {
		super(context);
		init();
	}

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public GameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		background = new Paint();
		background.setColor(Color.BLACK);

		manager = new PiecesManager();

		manager.addPiece(new Ball(new Vector2D(300, 600), new Vector2D(1, 1),
				8, manager, Color.rgb(255, 255, 255),null));

		manager.addPiece(new Wall(new Rect(0, 0, 10, height),
				new Vector2D(1, 0)));
		manager.addPiece(new Wall(new Rect(width - 10, 0, width, height),
				new Vector2D(-1, 0)));
		Wall north = new Wall(new Rect(0, 0, width, 10), new Vector2D(0, -1));
		manager.addPiece(north);
		Wall south = new Wall(new Rect(0, height - 10, width, height),
				new Vector2D(0, 1));
		manager.addPiece(south);

		northPad = new Pad(new Vector2D(0, -1), Axis.X_AXIS, 80);
		manager.addPiece(northPad);
		southPad = new Pad(new Vector2D(0, 1), Axis.X_AXIS, 944);
		manager.addPiece(southPad);
	}

	public void onDraw(Canvas canvas) {
		canvas.save();
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
		manager.draw(canvas);
		canvas.restore();
	}

	@Override
	public void run() {
		while (true) {
			try {
				manager.processAI();
				
				//invalidate();
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);

				Thread.sleep(10);
			} catch (Exception e) {
			}
		}
	}

	public void setCallbackHandler(Handler guiRefresher) {
		this.handler = guiRefresher;
	}

	public boolean onTouchEvent(MotionEvent evt) {
		for (int i = 0; i < evt.getPointerCount(); i++) {
			float y = evt.getY(i);
			if (y > 612)
				southPad.notifyMotionEvent(evt.getX(i), evt.getY(i));
			else
				northPad.notifyMotionEvent(evt.getX(i), evt.getY(i));
		}

		return true;
	}

}
