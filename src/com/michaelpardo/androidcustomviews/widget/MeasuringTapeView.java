package com.michaelpardo.androidcustomviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

public class MeasuringTapeView extends View {
	private static final int INCH_STEP = 16;

	private Paint mPaint;
	private float mXdpi;

	private int mWidth;
	private int mHeight;

	private int mSteps;

	public MeasuringTapeView(Context context) {
		this(context, null);
	}

	public MeasuringTapeView(Context context, AttributeSet attrs) {
		super(context, attrs);

		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();

		mXdpi = displayMetrics.xdpi;

		mPaint = new Paint();
		mPaint.setTextAlign(Align.CENTER);
		mPaint.setTextSize(displayMetrics.density * 22);
		mPaint.setStrokeWidth(displayMetrics.density * 2);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mWidth = w;
		mHeight = h;

		mSteps = (int) (mWidth / mXdpi * INCH_STEP);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i <= mSteps; i++) {
			final float x = i * mXdpi / INCH_STEP;
			final float y;

			if (i % INCH_STEP == 0) {
				y = mHeight - mHeight / 3;
			}
			else if (i % (INCH_STEP / 2) == 0) {
				y = mHeight - mHeight / 4;
			}
			else {
				y = mHeight - mHeight / 8;
			}

			canvas.drawLine(x, y, x, mHeight, mPaint);

			if (i > 0 && i % INCH_STEP == 0) {
				String text = String.valueOf(i / INCH_STEP);
				canvas.drawText(text, x, mHeight / 2, mPaint);
			}
		}
	}
}