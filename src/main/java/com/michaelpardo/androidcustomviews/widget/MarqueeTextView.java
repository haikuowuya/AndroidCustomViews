package com.michaelpardo.androidcustomviews.widget;

import android.content.Context;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

public class MarqueeTextView extends TextView {
	public MarqueeTextView(Context context) {
		super(context);
	}

	public MarqueeTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean isFocused() {
		if (getEllipsize().equals(TruncateAt.MARQUEE)) {
			return true;
		}

		return super.isFocused();
	}
}
