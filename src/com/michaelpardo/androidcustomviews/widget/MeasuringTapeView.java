package com.michaelpardo.androidcustomviews.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

public class MeasuringTapeView extends View {
	private static final int INCH_SEGMENTS = 16;

	private Paint mPaint;
	private Paint mMarkerPaint;

	private float mXdpi;

	private int mWidth;
	private int mHeight;

	private int mTotalSegments;

	private boolean mIsTouching;
	private int mMarkerX;

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

		mMarkerPaint = new Paint();
		mMarkerPaint.setColor(0xFFCC0000);
		mMarkerPaint.setTextSize(displayMetrics.density * 14);
		mMarkerPaint.setStrokeWidth(displayMetrics.density * 2);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);

		mWidth = w;
		mHeight = h;

		mTotalSegments = (int) (mWidth / mXdpi * INCH_SEGMENTS);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i <= mTotalSegments; i++) {
			final float x = i * mXdpi / INCH_SEGMENTS;
			final float y;

			if (i % INCH_SEGMENTS == 0) {
				y = mHeight - mHeight / 3;
			}
			else if (i % (INCH_SEGMENTS / 2) == 0) {
				y = mHeight - mHeight / 4;
			}
			else {
				y = mHeight - mHeight / 8;
			}

			canvas.drawLine(x, y, x, mHeight, mPaint);

			if (i > 0 && i % INCH_SEGMENTS == 0) {
				String text = String.valueOf(i / INCH_SEGMENTS);
				canvas.drawText(text, x, mHeight / 2, mPaint);
			}
		}

		if (mIsTouching) {
			String text = String.format(" %.2f%n", Math.max(0, mMarkerX / mXdpi));

			canvas.drawLine(mMarkerX, 0, mMarkerX, mHeight, mMarkerPaint);
			canvas.drawText(text, mMarkerX, -mMarkerPaint.ascent(), mMarkerPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mIsTouching = true;
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			mIsTouching = false;
		}

		mMarkerX = Math.max(0, (int) event.getX());

		invalidate();

		return true;
	}
}