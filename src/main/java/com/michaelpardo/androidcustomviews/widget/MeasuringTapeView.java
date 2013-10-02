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
	private static final int SEGMENTS_PER_INCH = 16;

	private int mWidth;
	private int mHeight;

	private float mXdpi;
	private int mTotalSegments;

	private Paint mPaint;
	private Paint mMarkerPaint;

	private float mTouchX;
	private boolean mIsTouching;

	public MeasuringTapeView(Context context) {
		this(context, null);
	}

	public MeasuringTapeView(Context context, AttributeSet attrs) {
		super(context, attrs);

		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		// This will not work on many devices. xdpi should be compared to densityDpi
		// to ensure that the difference falls within an acceptable threshold. If
		// that threshold is crossed, then the rendering should be deemed inaccurate.
		// Here we use densityDpi instead of xdpi to render the view properly on
		// Genymotion devices.
		mXdpi = displayMetrics.densityDpi;

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

		mTotalSegments = (int) (mWidth / mXdpi * SEGMENTS_PER_INCH);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		for (int i = 0; i <= mTotalSegments; i++) {
			final float x = i * mXdpi / SEGMENTS_PER_INCH;
			final float y;

			if (i % SEGMENTS_PER_INCH == 0) {
				y = mHeight - mHeight / 3;
			}
			else if (i % (SEGMENTS_PER_INCH / 2) == 0) {
				y = mHeight - mHeight / 4;
			}
			else if (i % (SEGMENTS_PER_INCH / 4) == 0) {
				y = mHeight - mHeight / 6;
			}
			else {
				y = mHeight - mHeight / 8;
			}

			canvas.drawLine(x, y, x, mHeight, mPaint);

			if (i > 0 && i % SEGMENTS_PER_INCH == 0) {
				String text = String.valueOf(i / SEGMENTS_PER_INCH);
				canvas.drawText(text, x, mHeight / 2, mPaint);
			}
		}

		if (mIsTouching) {
			String text = String.format(" %.2f%n", mTouchX / mXdpi);

			canvas.drawLine(mTouchX, 0, mTouchX, mHeight, mMarkerPaint);
			canvas.drawText(text, mTouchX, -mMarkerPaint.ascent(), mMarkerPaint);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean invalidate = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			mIsTouching = true;
		}
		else if (event.getAction() == MotionEvent.ACTION_UP) {
			mIsTouching = false;
			invalidate = true;
		}

		mTouchX = Math.max(0, event.getX());

		if (mIsTouching || invalidate) {
			invalidate();
		}

		return true;
	}
}