package com.example.android.examday;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

public class MainActivity extends AppCompatActivity {

    Display display;
    Point screenSize;
    int screenWidth;
    int screenHeight;
    GameEngine gameEngine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.display = this.getWindowManager().getDefaultDisplay();
        this.screenSize = new Point();
        this.display.getSize(screenSize);
        this.screenHeight = screenSize.y;
        this.screenWidth = screenSize.x;
        gameEngine = new GameEngine(this, screenHeight, screenWidth);
        setContentView(gameEngine);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameEngine.resumeGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
