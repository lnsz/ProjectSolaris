package com.example.lucas.missile_game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by lucas on 7/11/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;
    private Background background;
    private int x, vx;
    private int y ,vy;
    public GamePanel(Context context){
        super(context);

        // add the callback to the surfaceholder to intercelt events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        boolean retry = true;
        while(retry){
            try{
                thread.setRunning(false);
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){
        x=y=0;
        vx=vy=1;
        background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.space));

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){

        return super.onTouchEvent(event);
    }

    public void update(){

        background.update();
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        x+=vx;
        y+=vy;
        background.draw(canvas);
        canvas.drawColor(Color.WHITE);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x,y,100, paint);

    }
}
