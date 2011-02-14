package br.techs.jpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;
import br.techs.PiecesManager;
import br.techs.math.Vector2D;
import br.techs.pieces.Ball;
import br.techs.pieces.Wall;

public class GameView extends View implements Runnable {
	private Paint background;
	private Handler handler;

	private PiecesManager manager;

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

		Ball ball = new Ball(new Vector2D(60, 60), new Vector2D(1, 10).normalize(), 2);
		ball.setPiecesManager(manager);
		manager.addPiece(ball);
		manager.addPiece(new Wall(new Rect(0, 0, 10, 1024), new Vector2D(1, 0)));
		manager.addPiece(new Wall(new Rect(590, 0, 600, 1024), new Vector2D(-1,
				0)));
		manager.addPiece(new Wall(new Rect(0, 0, 600, 10), new Vector2D(0,-1)));
		manager.addPiece(new Wall(new Rect(0, 1014, 600, 1024), new Vector2D(0,
				1)));
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

}
