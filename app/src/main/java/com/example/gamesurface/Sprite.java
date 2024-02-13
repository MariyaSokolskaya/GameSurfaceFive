package com.example.gamesurface;

import android.app.PendingIntent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;

public class Sprite {
    Bitmap sprite;
    float x, y; //координаты точки на экране
    float dx, dy;
    float tx, ty;
    float height, width; //высота и ширина одного кадра
    Paint paint;
    public final int SPRITE_ROWS = 4;
    public final int SPRITE_COLUMNS = 3;
    int currentFrame; //текущий кадр из строки
    int direction = 1; // направление - номер строки с кадрами
    private float canvasWidth;
    private float canvasHeight;
    boolean isFirst = true;

    public Sprite(Bitmap sprite, float x, float y){
        this.sprite = sprite; //общая картинка со всеми кадрами
        this.x = x;
        this.y = y;
        paint = new Paint();
        width = sprite.getWidth() / SPRITE_COLUMNS;
        height = sprite.getHeight() / SPRITE_ROWS;
    }
    void controlRoute(){
        if (x < 10 || x > canvasWidth - width - 10)
            dx = -dx;
        if (y < 10 || y > canvasHeight - height- 10)
            dy = -dy;
    }
    void draw(Canvas canvas){
        if (isFirst) {
            canvasWidth = canvas.getWidth();
            canvasHeight = canvas.getHeight();
            isFirst = !isFirst;
        }
        int frameX = (int)(currentFrame * width);
        int frameY = (int)(direction * height);
        Rect src = new Rect(frameX, frameY, frameX + (int)width, frameY + (int)height);
        Rect dst = new Rect((int)x, (int)y, (int)(x + width + 20), (int)(y + height + 20));
        canvas.drawBitmap(sprite, src, dst, paint);
        currentFrame = ++currentFrame % 3;
        x += dx;
        y += dy;
        controlRoute();

    }
    void calculate(){
        float x1 = tx - x;
        float y1 = ty - y;
        float speed = 20;
        dx = x1 / (float) Math.sqrt(x1 * x1 + y1 * y1) * speed;
        dy = y1 / (float) Math.sqrt(x1 * x1 + y1 * y1) * speed;
    }

    public void setTx(float tx) {
        this.tx = tx;
        calculate();
    }

    public void setTy(float ty) {
        this.ty = ty;
        calculate();
    }
}
