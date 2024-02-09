package com.example.gamesurface;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class SurfaceThread extends Thread{
    SurfaceHolder holder;
    GameSurface gameSurface;
    boolean isRunning = false;
    long currentTime, nextTime, ellapsedTime;

    public SurfaceThread(SurfaceHolder holder, GameSurface gameSurface){
        this.holder = holder;
        this.gameSurface = gameSurface;
        currentTime = System.currentTimeMillis();
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    @Override
    public void run() {
        while (isRunning){
            nextTime = System.currentTimeMillis();
            ellapsedTime = nextTime - currentTime;
            if(ellapsedTime > 30){
                Canvas canvas = null;
                canvas = holder.lockCanvas();
                synchronized (holder) {
                    gameSurface.draw(canvas);
                }
                holder.unlockCanvasAndPost(canvas);
                currentTime = nextTime;
            }
        }
    }
}
