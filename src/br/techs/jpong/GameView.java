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
import br.techs.math.Vector2D;
import br.techs.pieces.Ball;
import br.techs.pieces.Pad;
import br.techs.pieces.Wall;

public class GameView extends View implements Runnable {
	private Paint background;
	private Handler handler;

	private PiecesManager manager;

	private Pad pad;

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

		Random rdm = new Random();

		for (int i = 0; i < 12; i++) {
			Vector2D vec = new Vector2D(10, 10);
			vec.rotate(rdm.nextInt(361));

			Ball ball = new Ball(new Vector2D(20 + rdm.nextInt(200),
					20 + rdm.nextInt(200)), vec.normalize(),
					1 + rdm.nextInt(10), manager, Color.rgb(rdm.nextInt(256),
							rdm.nextInt(256), rdm.nextInt(256)));
			manager.addPiece(ball);
		}

		manager.addPiece(new Wall(new Rect(0, 0, 10, 1024), new Vector2D(1, 0)));
		manager.addPiece(new Wall(new Rect(590, 0, 600, 1024), new Vector2D(-1,
				0)));
		manager.addPiece(new Wall(new Rect(0, 0, 600, 10), new Vector2D(0, -1)));
		manager.addPiece(new Wall(new Rect(0, 1014, 600, 1024), new Vector2D(0,
				1)));

		pad = new Pad(new Vector2D(1, 0));
		manager.addPiece(pad);
	}

	public void onDraw(Canvas canvas) {
		canvas.save();
		canvas.drawRect(0, 0, 600, 1024, background);
		manager.draw(canvas);
		canvas.restore();
	}

	@Override
	public void run() {
		while (true) {
			try {
				manager.processAI();

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
		pad.setYCenter(evt.getY());
		return true;
	}

}
