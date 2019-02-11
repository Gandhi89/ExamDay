package com.example.android.examday;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

public class Finger {
    int xPosition, yPosition;
    Rect hitBox;
    Bitmap fingerImage;
    private static final int SPEED_HORIZONTAL_FINGER = 15;
    private static final int SPEED_VERTICLE_FINGER = 15;

    public Finger(Context context, int xPosition, int yPosition) {
        this.fingerImage = BitmapFactory.decodeResource(context.getResources(), R.drawable.finger01);
        this.xPosition = xPosition;
        this.yPosition = (yPosition - fingerImage.getHeight()) - (fingerImage.getHeight() / 2);

        hitBox = new Rect(this.xPosition, this.yPosition, this.xPosition + this.fingerImage.getWidth(), this.yPosition + this.fingerImage.getHeight());
    }

    public void updateFingerPositionRighToLeft() {
        this.xPosition = this.xPosition - SPEED_HORIZONTAL_FINGER;

        // update the position of the hitbox
        this.hitBox.left = this.xPosition;
        this.hitBox.right = this.xPosition + this.fingerImage.getWidth();
        this.updateHitbox();
    }

    public void updateFingerPositionLeftToRight() {
        this.xPosition = this.xPosition + SPEED_HORIZONTAL_FINGER;

        // update the position of the hitbox
        this.hitBox.left = this.xPosition;
        this.hitBox.right = this.xPosition + this.fingerImage.getWidth();
        this.updateHitbox();
    }

    public void updateFingerPositionBottomToTop() {
        this.yPosition = this.yPosition - SPEED_VERTICLE_FINGER;

        // update the position of the hitbox
        this.hitBox.left = this.xPosition;
        this.hitBox.right = this.xPosition + this.fingerImage.getWidth();
        this.updateHitbox();
    }


    public void hit() {
        this.yPosition = this.yPosition - 600;

        this.hitBox.left = this.xPosition;
        this.hitBox.right = this.xPosition + this.fingerImage.getWidth();
        this.updateHitbox();
    }

    public void updateHitbox() {
        // update the position of the hitbox
        this.hitBox.top = this.yPosition;
        this.hitBox.left = this.xPosition;
        this.hitBox.right = this.xPosition + this.fingerImage.getWidth();
        this.hitBox.bottom = this.yPosition + this.fingerImage.getHeight();
    }

    public Rect getHitbox() {
        return this.hitBox;
    }
}
