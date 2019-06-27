/*
 * Decompiled with CFR 0_58.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Canvas
 *  android.util.AttributeSet
 *  android.view.MotionEvent
 *  android.widget.SeekBar
 */
package com.simplistic.floatingequalizerpro.ui.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatSeekBar;

public class VerticalSeekBar extends AppCompatSeekBar {
	
	VerticalSeekBar verticalSeekBar = this;
	public VerticalSeekBar(Context context) {
		super(context);
	}

	public VerticalSeekBar(Context context, AttributeSet attributeSet) {
		super(context, attributeSet);
	}

	public VerticalSeekBar(Context context, AttributeSet attributeSet, int n) {
		super(context, attributeSet, n);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.rotate((float) -90.0);
		canvas.translate(((-this.getHeight())), (float) 0.0);

		super.onDraw(canvas);
	}

	@Override
	protected void onMeasure(int n, int n2) {

		synchronized (verticalSeekBar) {
			super.onMeasure(n2, n);
			this.setMeasuredDimension(this.getMeasuredHeight(),
					this.getMeasuredWidth());
			return;
		}
	}

	@Override
	protected void onSizeChanged(int n, int n2, int n3, int n4) {
		super.onSizeChanged(n2, n, n4, n3);
	}

	/*
	 * Enabled aggressive block sorting Enabled unnecessary exception pruning
	 */
	@Override
	public boolean onTouchEvent(MotionEvent motionEvent) {
		if (!(this.isEnabled())) {
			return false;
		}
		switch (motionEvent.getAction()) {
		default: {
			return true;
		}
		case 0:
		case 1:
		case 2: {
			this.setProgress((this.getMax() - (int) ((((this.getMax()) * motionEvent
					.getY()) / (this.getHeight())))));
			this.onSizeChanged(this.getWidth(), this.getHeight(), 0, 0);
		}
		}
		return true;
	}
}
