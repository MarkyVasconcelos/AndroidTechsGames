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
import br.techs.pieces.Pad.Axis;
import br.techs.pieces.Wall;

public class GameView extends View implements Runnable {
	private int width = 600, height = 1024;
	private Paint background;
	private Handler handler;

	private PiecesManager manager;

	private Pad weastPad;
	private Pad northPad;
	private Pad eastPad;
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

		Random rdm = new Random();

		for (int i = 0; i < 12; i++) {
			Vector2D vec = new Vector2D(10, 10);
			vec.rotateMe(rdm.nextInt(361));

			Ball ball = new Ball(new Vector2D(20 + rdm.nextInt(200),
					20 + rdm.nextInt(200)), vec.normalize(),
					1 + rdm.nextInt(10), manager, Color.rgb(rdm.nextInt(256),
							rdm.nextInt(256), rdm.nextInt(256)));
			manager.addPiece(ball);
		}

		manager.addPiece(new Wall(new Rect(0, 0, 10, height),
				new Vector2D(1, 0)));
		manager.addPiece(new Wall(new Rect(width - 10, 0, width, height),
				new Vector2D(-1, 0)));
		manager.addPiece(new Wall(new Rect(0, 0, width, 10),
				new Vector2D(0, -1)));
		manager.addPiece(new Wall(new Rect(0, height - 10, width, height),
				new Vector2D(0, 1)));

		weastPad = new Pad(new Vector2D(1, 0), Axis.Y_AXIS, 80);
		manager.addPiece(weastPad);
		northPad = new Pad(new Vector2D(0, -1), Axis.X_AXIS, 80);
		manager.addPiece(northPad);
		eastPad = new Pad(new Vector2D(-1, 0), Axis.Y_AXIS, 520);
		manager.addPiece(eastPad);
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
		weastPad.notifyMotionEvent(evt);
		northPad.notifyMotionEvent(evt);
		eastPad.notifyMotionEvent(evt);
		southPad.notifyMotionEvent(evt);
		return true;
	}
}
