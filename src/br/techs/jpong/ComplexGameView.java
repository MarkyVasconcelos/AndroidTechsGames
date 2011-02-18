package br.techs.jpong;

import java.util.Random;

import android.content.Context;
import android.graphics.BitmapFactory;
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
import br.techs.R;
import br.techs.hud.Scoreboard;
import br.techs.math.Vector2D;
import br.techs.pieces.Ball;
import br.techs.pieces.Pad;
import br.techs.pieces.Pad.Axis;
import br.techs.pieces.Wall;

public class ComplexGameView extends View implements Runnable {
	private int width = 600, height = 1024;
	private Paint background;
	private Handler handler;

	private PiecesManager manager;

	private Pad weastPad;
	private Pad northPad;
	private Pad eastPad;
	private Pad southPad;

	public ComplexGameView(Context context) {
		super(context);
		init();
	}

	public ComplexGameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public ComplexGameView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
		background = new Paint();
		background.setColor(Color.BLACK);

		manager = new PiecesManager();

		for (int i = 0; i < 1; i++) {
			Vector2D dir = new Vector2D(10, 8);

			Ball ball = new Ball(new Vector2D(300, 600), dir.normalize(), 8,
					manager, Color.rgb(255, 255, 255),
					BitmapFactory.decodeResource(getResources(),
							R.drawable.ball));
			manager.addPiece(ball);
		}

		manager.addPiece(new Wall(new Rect(0, 0, 10, height),
				new Vector2D(1, 0)));
		manager.addPiece(new Wall(new Rect(width - 10, 0, width, height),
				new Vector2D(-1, 0)));
		Wall north = new Wall(new Rect(0, 0, width, 10), new Vector2D(0, -1));
		manager.addPiece(north);
		Wall south = new Wall(new Rect(0, height - 10, width, height),
				new Vector2D(0, 1));
		manager.addPiece(south);

		// weastPad = new Pad(new Vector2D(1, 0), Axis.Y_AXIS, 80);
		// manager.addPiece(weastPad);
		northPad = new Pad(new Vector2D(0, -1), Axis.X_AXIS, 80);
		manager.addPiece(northPad);
		// eastPad = new Pad(new Vector2D(-1, 0), Axis.Y_AXIS, 520);
		// manager.addPiece(eastPad);
		southPad = new Pad(new Vector2D(0, 1), Axis.X_AXIS, 944);
		manager.addPiece(southPad);

		manager.addPiece(new Scoreboard(north, south));
	}

	public void onDraw(Canvas canvas) {
		canvas.save();
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
		manager.draw(canvas);
		canvas.restore();
	}

	private void createBall() {
		Random rdm = new Random();

		Vector2D vec = new Vector2D(10, 10);
		vec.rotateMe(rdm.nextInt(361));

		Ball ball = new Ball(new Vector2D(300, 600), vec.normalize(),
				3 + rdm.nextInt(7), manager, Color.rgb(50 + rdm.nextInt(206),
						50 + rdm.nextInt(206), 50 + rdm.nextInt(206)),
				BitmapFactory.decodeResource(getResources(), R.drawable.ball));

		manager.addPiece(ball);
	}

	private int loops = 1;

	@Override
	public void run() {
		while (true) {
			try {
				if (loops % 500 == 0)
					createBall();
				manager.processAI();

				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);

				Thread.sleep(10);
				loops++;
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
			if (y > 612) {
				// eastPad.notifyMotionEvent(evt.getX(i), evt.getY(i));
				southPad.notifyMotionEvent(evt.getX(i), evt.getY(i));
			} else {
				// weastPad.notifyMotionEvent(evt.getX(i), evt.getY(i));
				northPad.notifyMotionEvent(evt.getX(i), evt.getY(i));
			}
		}

		return true;
	}

}
