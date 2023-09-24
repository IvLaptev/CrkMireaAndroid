package com.ivlaptev.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import androidx.annotation.NonNull;

public class TestSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    class DrawThread extends Thread {
        private final int[] colors = new int[]{ Color.GREEN, Color.YELLOW, Color.RED };
        private int currentColor = -1;

        public void run() {
            while (true) {
                Canvas c = getHolder().lockCanvas();

                currentColor = (currentColor + 1) % colors.length;
                System.out.println(currentColor);
                c.drawColor(colors[currentColor]);

                getHolder().unlockCanvasAndPost(c);

                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                }
            }
        }
    }

    DrawThread drawThread;

    public TestSurfaceView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder surfaceHolder) {
        drawThread = new DrawThread();
        drawThread.start();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder surfaceHolder) {
        drawThread.stop();
    }
}
