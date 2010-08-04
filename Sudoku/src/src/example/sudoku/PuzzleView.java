package src.example.sudoku;

import android.content.Context;
import android.graphics.*;
import android.graphics.Paint.FontMetrics;
import android.graphics.Paint.Style;
import android.util.Log;
import android.view.*;
import android.view.animation.*;

public class PuzzleView extends View{
	private static final String TAG = "Sudoku";
	private final Game game;
	public PuzzleView(Context context){
		super(context);
		this.game = (Game) context;
		setFocusable(true);
		setFocusableInTouchMode(true);
	}
	
	private float width;
	private float height;
	private int selX;
	private int selY;
	private final Rect selRect = new Rect();
	
	protected void onSizeChanged(int w, int h, int oldw, int oldh){
		width = w/9f;
		height = h/9f;
		getRect(selX, selY, selRect);
		Log.d(TAG, "onSizeChanged: width " + width + ", height " + height);
		super.onSizeChanged(w, h, oldw, oldh);
	}
	
	private void getRect(int x, int y, Rect rect){
		rect.set((int)(x*width), (int) (y*height), (int) (x*width + width), (int)(y*height + height));
	}
	
	protected void onDraw(Canvas canvas){
		Paint background = new Paint();
		background.setColor(getResources().getColor(R.color.puzzle_background));
		canvas.drawRect(0, 0, getWidth(), getHeight(), background);
		
		Paint dark = new Paint();
		dark.setColor(getResources().getColor(R.color.puzzle_dark));
		
		Paint hilite = new Paint();
		hilite.setColor(getResources().getColor(R.color.puzzle_hilite));
		
		Paint light = new Paint();
		light.setColor(getResources().getColor(R.color.puzzle_light));
		
		//Draw the minor grid lines
		for(int i = 0; i < 9; i++){
			canvas.drawLine(0, i * height, getWidth(), i * height + 1, hilite);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), light);
			canvas.drawLine(i * width + 1, 0, i * width + 1, getHeight(), hilite);
		}
		
		//draw the major grid lines
		for (int i = 0; i < 9; i++) {
			if (i % 3 != 0)
			continue;
			canvas.drawLine(0, i * height, getWidth(), i * height, dark);
			canvas.drawLine(0, i * height + 1, getWidth(), i * height + 1, hilite);
			canvas.drawLine(i * width, 0, i * width, getHeight(), dark);
			canvas.drawLine(i * width + 1, 0, i * width + 1,
			getHeight(), hilite);
		}
		
		Paint foreground = new Paint(Paint.ANTI_ALIAS_FLAG);
		foreground.setColor(getResources().getColor(R.color.puzzle_foreground));
		foreground.setStyle(Style.FILL);
		foreground.setTextSize(height * 0.75f);
		foreground.setTextScaleX(width / height);
		foreground.setTextAlign(Paint.Align.CENTER);
		
		FontMetrics fm = foreground.getFontMetrics();
		
		float x = width/2;
		float y = height/2 - (fm.ascent + fm.descent)/2;
		for(int i = 0; i < 9; i++){
			for(int j = 0; j < 9; j++){
				canvas.drawText(this.game.getTileString(i, j), i * width + x, j * height + y, foreground);
			}
		}
		
		Log.d(TAG, "selRect=" + selRect);
		Paint selected = new Paint();
		selected.setColor(getResources().getColor(R.color.puzzle_selected));
		canvas.drawRect(selRect, selected);
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event){
		Log.d(TAG, "onKeyDown: keycode=" + keyCode + ", event=" + event);
		switch(keyCode){
		case KeyEvent.KEYCODE_DPAD_UP:
			select(selX, selY -1);
			break;
		case KeyEvent.KEYCODE_DPAD_DOWN:
			select(selX, selY + 1);
			break;
		case KeyEvent.KEYCODE_DPAD_LEFT:
			select(selX -1, selY);
			break;
		case KeyEvent.KEYCODE_DPAD_RIGHT:
			select(selX + 1, selY);
			break;
		case KeyEvent.KEYCODE_0:
		case KeyEvent.KEYCODE_SPACE: selSeletctedTile(0); break;
		case KeyEvent.KEYCODE_1:	 selSeletctedTile(1); break;
		case KeyEvent.KEYCODE_2:	 selSeletctedTile(2); break;
		case KeyEvent.KEYCODE_3:	 selSeletctedTile(3); break;
		case KeyEvent.KEYCODE_4:	 selSeletctedTile(4); break;
		case KeyEvent.KEYCODE_5:	 selSeletctedTile(5); break;
		case KeyEvent.KEYCODE_6:	 selSeletctedTile(6); break;
		case KeyEvent.KEYCODE_7:	 selSeletctedTile(7); break;
		case KeyEvent.KEYCODE_8:	 selSeletctedTile(8); break;
		case KeyEvent.KEYCODE_9:	 selSeletctedTile(9); break;
		case KeyEvent.KEYCODE_ENTER:
		case KeyEvent.KEYCODE_DPAD_CENTER:
			game.showKeypadOrError(selX, selY);
			break;
		default:
			return super.onKeyDown(keyCode, event);
		}
		return true;
	}
	
	private void select(int x, int y){
		invalidate(selRect);
		selX = Math.min(Math.max(x,0), 8);
		selY = Math.min(Math.max(y,0), 8);
		getRect(selX, selY, selRect);
		invalidate(selRect);
	}
	
	public boolean onTouchEvent(MotionEvent event){
		if(event.getAction() != MotionEvent.ACTION_DOWN)
			return super.onTouchEvent(event);
		
		select((int) (event.getX()/width), (int) (event.getY()/height));
		game.showKeypadOrError(selX, selY);
		Log.d(TAG, "onTouchEvent: x " + selX + ", y" + selY);
		return true;
	}
	
	public void setSelectedTile(int tile){
		if(game.setTileIfValid(selX, selY, tile)){
			invalidate();
		}else{
			Log.d(TAG, "setSelectedTile : invalid: " + tile);
		}
	}
}
