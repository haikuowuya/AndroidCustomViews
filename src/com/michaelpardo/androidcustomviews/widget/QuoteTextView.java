package com.michaelpardo.androidcustomviews.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.michaelpardo.androidcustomviews.R;

public class QuoteTextView extends RelativeLayout {
	private TextView mBodyTextView;

	public QuoteTextView(Context context) {
		this(context, null);
	}

	public QuoteTextView(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuoteTextView);
		String text = a.getString(R.styleable.QuoteTextView_android_text);

		a.recycle();

		inflate(context, R.layout.widget_quote_textview, this);

		mBodyTextView = (TextView) findViewById(R.id.body_text_view);
		mBodyTextView.setText(text);
	}

	public void setText(String text) {
		mBodyTextView.setText(text);
	}
}