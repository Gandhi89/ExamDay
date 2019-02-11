package com.example.android.examday;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameEngine extends SurfaceView implements Runnable {

    private static final String TAG = "GameEngine";
    int screenHeight, screenWidth;
    private static final int FPS_SPEED = 17;

    private Thread gameThread = null;
    private volatile boolean gameIsRunning;
    private SurfaceHolder holder;
    Paint paint;
    Canvas canvas;
    Finger finger;
    Nose nose;
    boolean moveLeftToRight = true;
    boolean hit = false;
    int hitCount = 0;
    int missCount = 0;
    boolean fingerIsGoingUp = false;
    Context context;
    Rect rect;


    boolean showBoundary = true;


    public GameEngine(Context context, int screenHeight, int screenWidth) {
        super(context);
        this.screenHeight = screenHeight;
        this.screenWidth = screenWidth;
        this.holder = this.getHolder();
        paint = new Paint();


        Log.d(TAG, "GameEngine: Screen Height and Width are : " + screenHeight + ", " + screenWidth);

        this.context = context;

        finger = new Finger(context, 0, screenHeight);
        nose = new Nose(context, screenHeight / 2, 00);

        rect = new Rect(nose.getHitbox().left + 310, nose.getHitbox().top, nose.getHitbox().right + 310, nose.getHitbox().bottom);
    }

    @Override
    public void run() {
        while (gameIsRunning == true) {
            updateGame();
            drawGame();
            controlFPS();
        }
    }


    private void updateGame() {


        if (fingerIsGoingUp) {

            if (finger.yPosition > 0) {
                finger.updateFingerPositionBottomToTop();
                if (finger.getHitbox().intersect(nose.getHitbox()) || finger.getHitbox().intersect(rect)) {

                    hitCount = hitCount + 1;
                    finger = new Finger(context, 0, screenHeight);
                    nose = new Nose(context, screenHeight / 2, 00);
                    hit = false;
                    fingerIsGoingUp = false;

                } else if (finger.yPosition < 100) {
                    Log.d(TAG, "updateGame: hihihihi");
                    missCount = missCount + 1;
                    finger = new Finger(context, 0, screenHeight);
                    nose = new Nose(context, screenHeight / 2, 00);
                    hit = false;
                    fingerIsGoingUp = false;
                }
            }

        } else {
            if (!hit) {
                if (moveLeftToRight) {
                    if (finger.xPosition < screenWidth) {
                        finger.updateFingerPositionLeftToRight();
                    } else {
                        moveLeftToRight = false;
                    }
                } else {
                    if (finger.xPosition > 0) {
                        finger.updateFingerPositionRighToLeft();
                    } else {
                        moveLeftToRight = true;
                    }
                }
            } else {
                if (finger.getHitbox().intersect(nose.getHitbox())) {

                    hitCount = hitCount + 1;
                    finger = new Finger(context, 0, screenHeight);
                    nose = new Nose(context, screenHeight / 2, 00);
                    hit = false;

                } else {

                    missCount = missCount + 1;

                    finger = new Finger(context, 0, screenHeight);
                    nose = new Nose(context, screenHeight / 2, 00);
                    hit = false;
                }
            }
        }
    }

    private void drawGame() {
        if (holder.getSurface().isValid()) {
            canvas = holder.lockCanvas();
            // code from here

            canvas.drawColor(Color.parseColor("#207ab2"));
            paint.setColor(Color.WHITE);

            /**
             * display two images on screen
             */
            canvas.drawBitmap(nose.noseImage, nose.xPosition, nose.yPosition, paint);
            canvas.drawBitmap(finger.fingerImage, finger.xPosition, finger.yPosition, paint);


            /**
             * draw hitBox
             */
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(12);
            paint.setColor(Color.BLACK);
            canvas.drawRect(finger.getHitbox().left, finger.getHitbox().top, finger.getHitbox().right, finger.getHitbox().bottom, paint);
            canvas.drawRect(nose.getHitbox().left, nose.getHitbox().top, nose.getHitbox().right, nose.getHitbox().bottom, paint);
            canvas.drawRect(nose.getHitbox().left + 310, nose.getHitbox().top, nose.getHitbox().right + 310, nose.getHitbox().bottom, paint);


            paint.setColor(Color.BLACK);
            paint.setTextSize(96);

            canvas.drawText("Hit Count: " + hitCount, 100, 500, paint);
            canvas.drawText("Miss Count: " + missCount, 1800, 500, paint);

            if (showBoundary) {

                canvas.drawLine((float) screenWidth / 6, 0, (float) screenWidth / 6, screenHeight, paint);
                canvas.drawLine((float) (screenWidth - screenWidth / 6), 0, (float) (screenWidth - screenWidth / 6), screenHeight, paint);

            }

            holder.unlockCanvasAndPost(canvas);


        }
    }

    private void controlFPS() {
        try {
            gameThread.sleep(FPS_SPEED);
        } catch (InterruptedException e) {
        }
    }

    public void resumeGame() {
        gameIsRunning = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int userAction = event.getActionMasked();

        if (userAction == MotionEvent.ACTION_DOWN) {
            // move player up
            fingerIsGoingUp = true;
            //finger.hit();

            hit = true;
        }

        return true;
    }
}
