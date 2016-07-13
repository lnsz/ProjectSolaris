package com.example.lucas.missile_game;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.os.Handler;
import android.util.Log;

/**
 * Created by lucas on 7/11/2016.
 */
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{
    private MainThread thread;
    private Background background;
    public static float height, width;
    Paint paint;
    long lastDown, lastDuration;
    boolean isPressed = false;

    public GamePanel(Context context){
        super(context);

        // add the callback to the surfaceholder to intercelt events
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        setFocusable(true);

        paint = new Paint();
        paint.setARGB(255,255,255,255);
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

    EntitySystem entities;
    @Override
    public void surfaceCreated(SurfaceHolder holder){
       // background = new Background(BitmapFactory.decodeResource(getResources(), R.drawable.space));
        entities = new EntitySystem();

        width = getResources().getDisplayMetrics().widthPixels;
        height = getResources().getDisplayMetrics().heightPixels;

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            lastDown = System.currentTimeMillis();
            isPressed = true;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            lastDuration = System.currentTimeMillis() - lastDown;
            entities.addEntity(new Missile(width / 2, height- 100, event.getX(), event.getY(),
                    (lastDuration > 500)?(lastDuration / 50) : 10, entities), true); // Min str is 10
            isPressed = false;
        }
        return true;
    }

    public void strMeter(Canvas canvas){
        if (System.currentTimeMillis() - lastDown > 3000){ // Max str is 30, after that it resets
            lastDown = System.currentTimeMillis();
        }
        double timeRatio = (System.currentTimeMillis() - lastDown) / 3000.0;
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(width / 2, height- 100, (float)(500 * timeRatio), this.paint);

    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawColor(0);
        if (isPressed){
            strMeter(canvas);
        }
        entities.run(canvas);

    }


}
