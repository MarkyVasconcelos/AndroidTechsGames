package br.techs.jpong;

import br.techs.R;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class JPongActivity extends Activity {
	private static final int REFRESH = 1;
	
	private Handler guiRefresher;
	private GameView gameView;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jpong);
		
		
		gameView = (GameView) findViewById(R.id.gameView);
		
		guiRefresher = new Handler(){
			public void handleMessage(Message msg){
				if(msg.what == REFRESH){
					gameView.invalidate();
				}
				super.handleMessage(msg);
			}
		};
		
		gameView.setCallbackHandler(guiRefresher);
		
		
		Thread t = new Thread(gameView);
		t.setDaemon(true);
		t.start();
		//t.run();
	}
}