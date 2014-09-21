package com.tkrpan.dragview;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

/**
 * Created by tomislav on 9/17/14.
 */
public class DragLayout extends RelativeLayout implements OnDragLockListener {

    private static final String TAG = " DragLayout ";
    private boolean captureView;

    private ViewDragHelper mViewDragHelper;
    private View mDragView;

    private int mTop, mLeft;

    public DragLayout(Context context) {
        this(context, null);
    }

    public DragLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DragLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mViewDragHelper = ViewDragHelper.create(this, 1f, new DragHelperCallback());
        MainActivity.PlaceholderFragment.setOnDragLockListener(this);
    }

    @Override
    protected void onFinishInflate() {
        mDragView = findViewById(R.id.dragLayout);

        mDragView.setX(300);
        mDragView.setY(100);
    }

    @Override
    public void onDragLock(boolean lock) {
        captureView = !lock;
    }

    private class DragHelperCallback extends ViewDragHelper.Callback {

        @Override
        public boolean tryCaptureView(View view, int i) {
            return captureView;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            //metoda koja se poziva tokom promijene pozicije View-a

            mTop = top;
            mLeft = left;

            Log.i("TAG", TAG  + " top : " + mTop);
            Log.e("TAG", TAG  + " left : " + mLeft);
            invalidate();
        }

        @Override
        public void onViewCaptured(View capturedChild, int activePointerId) {
            super.onViewCaptured(capturedChild, activePointerId);
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            //metoda koja se poziva nakon sto se View ispusti
        }

        @Override
        public int clampViewPositionVertical (View child, int top, int dy) {

            final int topBound = getPaddingTop();
            final int bottomBound = getHeight() - mDragView.getHeight() - mDragView.getPaddingBottom();

            return Math.min(Math.max(top, topBound), bottomBound);
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

                final int leftBound = getPaddingLeft();
                final int rightBound = getWidth() - mDragView.getWidth();

                return Math.min(Math.max(left, leftBound), rightBound);
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        //metoda koja se poziva nakon sto se DragLayout dotakne
        Log.d("TAG", TAG  + " onInterceptTouchEvent()");
        final int action = MotionEventCompat.getActionMasked(motionEvent);

        if(action == MotionEvent.ACTION_CANCEL){
            mViewDragHelper.cancel();
            return false;
        }
        return mViewDragHelper.shouldInterceptTouchEvent(motionEvent);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //metoda koja se poziva tokom pomicanja prsta po DragLayout-u
        Log.d("TAG", TAG  + " onTouchEvent()");
        mViewDragHelper.processTouchEvent(event);
        return true;
    }
}
