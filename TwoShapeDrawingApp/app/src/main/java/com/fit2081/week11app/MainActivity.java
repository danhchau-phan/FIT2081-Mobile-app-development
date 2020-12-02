package com.fit2081.week11app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GestureDetectorCompat;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity{
    CustomView view;
    int shape2Draw = Shape.CIRCLE;
    GestureDetector gD;
    ScaleGestureDetector sD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        view = findViewById(R.id.myView);

        gD = new GestureDetector(this, new W11GestureDetector());
        sD = new ScaleGestureDetector(this, new W11ScaleGestureDetector());
        view.addShape(new Circle(10,20,100));
        view.addShape(new Rectangle(10,20,100,100));
        view.clearShapes();
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gD.onTouchEvent(event);
                sD.onTouchEvent(event);
                return true;
            }
        });
    }

    class W11GestureDetector extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            if (shape2Draw == Shape.CIRCLE) {
                Circle c = new Circle(x,y,100);
                view.addShape(c);
            } else {
                Rectangle r = new Rectangle(x, y, x+100, y+100);
                view.addShape(r);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            view.clearShapes();
        }

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            switch (shape2Draw) {
                case Shape.CIRCLE:
                    shape2Draw = Shape.RECTANGLE;
                    Toast.makeText(getApplicationContext(), "Rectangle", Toast.LENGTH_SHORT).show();
                    return true;
                case Shape.RECTANGLE:
                    shape2Draw = Shape.CIRCLE;
                    Toast.makeText(getApplicationContext(), "Circle", Toast.LENGTH_SHORT).show();
                    return true;
            }
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            int x = (int) e2.getX();
            int y = (int) e2.getY();
            if (shape2Draw == Shape.CIRCLE) {
                Circle c = new Circle(x,y,100);
                view.addShape(c);
            } else {
                Rectangle r = new Rectangle(x, y, x+100, y+100);
                view.addShape(r);
            }
            return true;
        }
    }
    class W11ScaleGestureDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        int x, y;

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            if (shape2Draw == Shape.CIRCLE) {
                x = (int) detector.getFocusX();
                y = (int) detector.getFocusY();
                int r = (int) detector.getCurrentSpan();
                Circle c = new Circle(x,y,r/2);
                view.addShape(c);
            } else {
                x = (int) detector.getFocusX();
                y = (int) detector.getFocusY();
                int width = (int) detector.getCurrentSpanX();
                int height = (int) detector.getCurrentSpanY();
                Rectangle r = new Rectangle(x, y, width, height);
                view.addShape(r);
            }
        }
    }
}
