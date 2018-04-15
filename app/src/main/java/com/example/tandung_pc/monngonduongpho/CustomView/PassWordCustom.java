package com.example.tandung_pc.monngonduongpho.CustomView;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

import com.example.tandung_pc.monngonduongpho.R;


/**
 * Created by letandung on 09/09/2017.
 */

@SuppressLint("AppCompatCustomView")
public class PassWordCustom extends EditText {
    Drawable eye, notEye;
    Boolean visible = false;
    Boolean useStrike = false;
    Boolean useValidate = false;
    Drawable drawable1;
    int ALPHA = (int) (255 * .55f);

    public PassWordCustom(Context context) {
        super(context);
        KhoiTao(null);
    }

    public PassWordCustom(Context context, AttributeSet attrs) {
        super(context, attrs);
        KhoiTao(attrs);
    }

    public PassWordCustom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        KhoiTao(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public PassWordCustom(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        KhoiTao(attrs);
    }

    private void KhoiTao(AttributeSet attributeSet) {
        if (attributeSet != null) {
            TypedArray typedArray = getContext().getTheme().obtainStyledAttributes(attributeSet, R.styleable.PassWordCustom, 0, 0);
            this.useStrike = typedArray.getBoolean(R.styleable.PassWordCustom_useSkike, false);
            this.useValidate = typedArray.getBoolean(R.styleable.PassWordCustom_useValidate, false);
        }
        eye = ContextCompat.getDrawable(getContext(), R.drawable.icondisplaypassword).mutate();
        notEye = ContextCompat.getDrawable(getContext(), R.drawable.iconobscurepassword).mutate();

        Caidat();
    }

    private void Caidat() {
        setInputType(InputType.TYPE_CLASS_TEXT | (visible ? InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD : InputType.TYPE_TEXT_VARIATION_PASSWORD));
        Drawable[] drawable = getCompoundDrawables();
        drawable1 = useStrike && !visible ? notEye : eye;
        drawable1.setAlpha(ALPHA);
        setCompoundDrawablesWithIntrinsicBounds(drawable[0], drawable[1], drawable1, drawable[3]);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN && event.getX() >= (getRight() - drawable1.getBounds().width())) {
            visible = !visible;
            Caidat();
            invalidate();
        }
        return super.onTouchEvent(event);
    }
}
