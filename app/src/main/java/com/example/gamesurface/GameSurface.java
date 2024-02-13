package com.example.gamesurface;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    Bitmap image;
    SurfaceHolder holder;
    float curX, curY; //текущие координаты картинки
    Paint paint;
    float touchX, touchY; //координаты точки касания
    float dx, dy; // смещения по осям
    SurfaceThread thread;
    float canvasWidth, canvasHeight;
    boolean isFirstFrame = true;
    Sprite sprite;
    public GameSurface(Context context) {
        super(context);
        image = BitmapFactory.decodeResource(getResources(), R.drawable.sprites);
        holder = getHolder();
        holder.addCallback(this); //"активировали" интерфейс
        curX = curY = 200;
        paint = new Paint();
        sprite = new Sprite(image, curX, curY);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        if (isFirstFrame) {
            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();
            isFirstFrame = !isFirstFrame;
        }
//        canvas.drawBitmap(image, curX, curY, paint);
//        curX += dx;
//        curY += dy;
//        controlRoute();
        sprite.draw(canvas);
    }

    void controlRoute(){
        if (curX < 10 || curX > canvasWidth - image.getWidth() - 10)
            dx = -dx;
        if (curY < 10 || curY > canvasHeight - image.getHeight()- 10)
            dy = -dy;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            touchX = event.getX();
            touchY = event.getY();
            //calculation();
            sprite.setTx(touchX);
            sprite.setTy(touchY);
        }
        return true;
    }

    void calculation(){
        float x = touchX - curX;
        float y = touchY - curY;
        float speed = 20;
        dx = x / (float) Math.sqrt(x * x + y * y) * speed;
        dy = y / (float) Math.sqrt(x * x + y * y) * speed;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        thread = new SurfaceThread(holder, this);
        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        thread.setRunning(false);
        boolean retry = true;
        while (retry) {
            try {
                thread.join();
                retry = false;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
