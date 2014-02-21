
package com.example.android.apis.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;

public class ProgressArcView extends View {
    
    private static final Xfermode sModes = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    private static final int DOWNLOAD_GRAY = 0x6F000000;
    
    private Paint mPaints;
    private int mWidth;
    private float mLt;
    private RectF mRectF;
    private Bitmap mSrcB;
    private Bitmap mDstB;

    public ProgressArcView(Context context) {
        super(context);
        init();
    }

    public ProgressArcView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    // create a bitmap with a circle, used for the "dst" image
    private Bitmap makeDst(int size) {
        Bitmap bDst = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bDst);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(DOWNLOAD_GRAY);
        c.drawRect(new RectF(0, 0, size, size), p);
        return bDst;
    }

    // create a bitmap with a rect, used for the "src" image
    private Bitmap makeSrc(int size, RectF rectF, int angle) {
        Bitmap bSrc = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bSrc);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        p.setColor(0xFFFFFFFF);
        p.setStyle(Style.FILL);
        
        c.drawArc(rectF, 270, angle, true, p);
        return bSrc;
    }

    private void init() {
        mPaints = new Paint();
        mPaints.setAntiAlias(true);
        mPaints.setStyle(Paint.Style.FILL);
        mPaints.setColor(0x00000000);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        mWidth = getWidth();
        mLt = (float) Math.sqrt((mWidth * mWidth) / 2.0) - mWidth / 2.f;
        mRectF = new RectF(-mLt, -mLt, mWidth + mLt, mWidth + mLt);
        mSrcB = makeSrc(mWidth, mRectF, 0); 
        mDstB = makeDst(mWidth);
    }

    public void updateProgress(int progress) {
        if (mWidth < 0) {
            return;
        }
        mSrcB = makeSrc(mWidth, mRectF, progress * 360 / 100);
        invalidate();
    }
    
    @Override
    protected void onDraw(Canvas c) {
        c.drawColor(Color.TRANSPARENT);
        
        int sc = c.saveLayer(0, 0, mWidth, mWidth, null, 
                Canvas.MATRIX_SAVE_FLAG |
                Canvas.CLIP_SAVE_FLAG |
                Canvas.HAS_ALPHA_LAYER_SAVE_FLAG |
                Canvas.FULL_COLOR_LAYER_SAVE_FLAG |
                Canvas.CLIP_TO_LAYER_SAVE_FLAG);
        
        Paint paint = new Paint();
        c.drawBitmap(mDstB, 0, 0, paint);
        paint.setXfermode(sModes);
        c.drawBitmap(mSrcB, 0, 0, paint);
        c.restoreToCount(sc);
    }
}
