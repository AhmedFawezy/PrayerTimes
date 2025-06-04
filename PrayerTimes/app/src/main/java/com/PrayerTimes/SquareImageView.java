package com.PrayerTimes;


//التحكم في حجم الصورة داخل تطبيقك
import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View.MeasureSpec;
import android.widget.ImageView;

public class SquareImageView extends ImageView {
    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, MeasureSpec.makeMeasureSpec((MeasureSpec.getSize(i) * 30) / 16, MeasureSpec.EXACTLY));
    }

    public SquareImageView(Context context) {
        super(context);
    }

    public SquareImageView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public SquareImageView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @TargetApi(21)
    public SquareImageView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
    }
}
