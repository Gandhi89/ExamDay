package com.example.android.examday;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.util.Log;

public class Nose {
    private static final String TAG = "Nose";

    int xPosition, yPosition;
    Rect hitBox;
    Bitmap noseImage;

    public Nose(Context context, int xPosition, int yPosition) {
        this.noseImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.nose01);
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        Log.d(TAG, "Nose: xPosition: " + xPosition);
        Log.d(TAG, "Nose: xPosition: " + yPosition);
        Log.d(TAG, "Nose: xPosition: " + (xPosition + this.noseImage.getWidth()));
        Log.d(TAG, "Nose: xPosition: " + (yPosition + this.noseImage.getHeight()));


        hitBox = new Rect(this.xPosition + 120, this.yPosition + 450, this.xPosition + 120 + 250, this.yPosition + 450 + 250);
        //hitBox = new Rect(this.xPosition + 430, 450 , this.xPosition+ 430 + 250, 450 + 250);
    }

    public Rect getHitbox() {
        return this.hitBox;
    }

}
