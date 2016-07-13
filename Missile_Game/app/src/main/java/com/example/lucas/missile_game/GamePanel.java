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
    float height;
    float width;

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

    long lastDown;
    long lastDuration;
    @Override
    public boolean onTouchEvent(MotionEvent event){
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            lastDown = System.currentTimeMillis();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            lastDuration = System.currentTimeMillis() - lastDown;
            entities.addEntity(new Missile(width / 2, height - 100, event.getX(), event.getY(),
                    (lastDuration > 50)?(lastDuration / 50) : 1));
        }
        System.out.println(lastDown);
        System.out.println(lastDuration);



        return true;
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);

        canvas.drawColor(0);
        entities.addParticle(500,500,20);
        entities.run(canvas);

    }


}
