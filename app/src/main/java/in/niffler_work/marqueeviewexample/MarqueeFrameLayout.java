package in.niffler_work.marqueeviewexample;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Timer;
import java.util.TimerTask;

public class MarqueeFrameLayout extends FrameLayout implements View.OnTouchListener {

    private int height, width;
    private MarqueeAdapter adapter;
    private ArrayList<ItemToBeDrawn> itemToBeDrawns = new ArrayList<>();
    private ItemToBeDrawn startNode;


    static boolean touchDown = false;
    float previousScrollX = 0, currentScrollX = 0;
    Timer scrollTimer;
    ItemToBeDrawn replicaF, replicaB;
    int itemCount = 0;
    private float scrollX;
    private float firstX;
    VelocityTracker vTracker;
    static int FRAME_RATE = (int) (1000.0/60.0);
    static float DECELERATION_RATE = (float) (1.0/1.1);

    public MarqueeFrameLayout(@NonNull Context context) {
        super(context);
        this.setClipChildren(true);
        this.setOnTouchListener(this);
        scrollTimer = new Timer();
        scrollTimer.schedule(new ScrollTimerTask(),FRAME_RATE, FRAME_RATE);
        itemToBeDrawns = new ArrayList<ItemToBeDrawn>();
    }

    public MarqueeFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.setClipChildren(true);
        this.setOnTouchListener(this);
        scrollTimer = new Timer();
        scrollTimer.schedule(new ScrollTimerTask(),FRAME_RATE, FRAME_RATE);
        itemToBeDrawns = new ArrayList<ItemToBeDrawn>();
    }

    public MarqueeFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setClipChildren(true);
        this.setOnTouchListener(this);
        scrollTimer = new Timer();
        scrollTimer.schedule(new ScrollTimerTask(),FRAME_RATE, FRAME_RATE);
        itemToBeDrawns = new ArrayList<ItemToBeDrawn>();
    }

    public MarqueeFrameLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.setClipChildren(true);
        this.setOnTouchListener(this);
        scrollTimer = new Timer();
        scrollTimer.schedule(new ScrollTimerTask(),FRAME_RATE, FRAME_RATE);
        itemToBeDrawns = new ArrayList<ItemToBeDrawn>();
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
//        removeAllViews();
//        removeAllViewsInLayout();
    }

    public MarqueeAdapter getAdapter() {
        return adapter;
    }

    public void setAdapter(MarqueeAdapter adapter) {
        this.adapter = adapter;
        initItemToBeDrawn();
        ReDrawFrameLayout();
        invalidate();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        evaluateChildDisplaySequence();
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//    }

    private void evaluateChildDisplaySequence(float scrollX) {
        FrameLayout.LayoutParams params, prevParams;
        ItemToBeDrawn itemToBeDrawn, prevItemToBeDrawn;
        for (int i=0; i<itemToBeDrawns.size(); i++) {
            itemToBeDrawn = itemToBeDrawns.get(i);
            params = ((FrameLayout.LayoutParams)itemToBeDrawn.getView().getLayoutParams());
//            if (width < params.leftMargin + itemToBeDrawn.getView().getWidth()) {
//                break;
//            }

            if (i == 0) {
                params.leftMargin += (int) scrollX;
            } else
            {
                prevItemToBeDrawn = itemToBeDrawns.get(i-1);
                prevParams = (LayoutParams) prevItemToBeDrawn.getView().getLayoutParams();
                params.leftMargin += (int) (prevItemToBeDrawn.getView().getWidth() + scrollX);
            }
            itemToBeDrawn.getView().setLayoutParams(params);
        }
    }

    private void initItemToBeDrawn() {
        if (itemToBeDrawns.isEmpty()) {

//            itemToBeDrawns.add(new ItemToBeDrawn(0, 0, getContext(), new WeakReference<MarqueeAdapter>(adapter)));
//            itemToBeDrawns.add(new ItemToBeDrawn(2, 2, getContext(), new WeakReference<MarqueeAdapter>(adapter)));
//            itemToBeDrawns.add(new ItemToBeDrawn(2, 2, getContext(), new WeakReference<MarqueeAdapter>(adapter)));
//            itemToBeDrawns.add(new ItemToBeDrawn(3, 3, getContext(), new WeakReference<MarqueeAdapter>(adapter)));
        }
    }

    private void ReDrawFrameLayout() {
        for (ItemToBeDrawn itemToBeDrawn : itemToBeDrawns)
                addView(itemToBeDrawn.getView());
    }

    private void reArrangeChildView() {

    }

    @Override
    public boolean onTouch(View arg0, MotionEvent arg1) {

        if (arg1.getAction() == MotionEvent.ACTION_DOWN) {
            vTracker = VelocityTracker.obtain();
            touchDown = true;
            firstX = (float) (arg1.getX() * -1);
        } else if (arg1.getAction() == MotionEvent.ACTION_MOVE) {
            scrollX = firstX - (float) (arg1.getX() * -1);
            firstX = (float) (arg1.getX() * -1);
            vTracker.addMovement(arg1);
        } else if (arg1.getAction() == MotionEvent.ACTION_UP) {
            touchDown = false;
            vTracker.computeCurrentVelocity(1000);
            scrollX = (float) (vTracker.getXVelocity() * 0.1);
//            if(scrollX > itemSize.width){
//                scrollX = itemSize.width;
//            }
//            if(scrollX < -itemSize.width){
//                scrollX = -itemSize.width;
//            }
        }
        return true;
    }

    public class ScrollTimerTask extends TimerTask {
        public synchronized void arrangeViews() {

            if (scrollX != 0) {
                synchronized (itemToBeDrawns) {

                    ((Activity)getContext()).runOnUiThread(new Runnable() {
                        public void run() {
                            Collections.sort(itemToBeDrawns, new Comparator<ItemToBeDrawn>() {
                                        @Override
                                        public int compare(ItemToBeDrawn lhs, ItemToBeDrawn rhs) {
                                            FrameLayout.LayoutParams lhsParams = (FrameLayout.LayoutParams) lhs.getView().getLayoutParams();
                                            FrameLayout.LayoutParams rhsParams = (FrameLayout.LayoutParams) rhs.getView().getLayoutParams();
                                            return ((Integer) lhsParams.leftMargin).compareTo((Integer) rhsParams.leftMargin);
                                        }
                                    });

                            evaluateChildDisplaySequence(scrollX);

//                            for (int i = 0; i < itemToBeDrawns.size(); i++) {
//                                //TODO:  arrangement of left margin so that view can appear in sequence
//                                ItemToBeDrawn vw = itemToBeDrawns.get(i);
//                                FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) vw.getView().getLayoutParams();
//                                if (i == 0) {
//                                    params.leftMargin += scrollX;
//                                } else {
//                                    View v = itemToBeDrawns.get(0).getView();
//                                    FrameLayout.LayoutParams fparams = (FrameLayout.LayoutParams) v.getLayoutParams();
//                                    params.leftMargin = (int) (fparams.leftMargin + (v.getWidth()));
//                                }
//
////                                if (vw != replicaF && vw != replicaB) {
////                                    if (scrollX > 0) {
////                                        if (params.leftMargin > ((itemCount - 1) * vw.getView().getWidth())
////                                                && params.leftMargin <= (itemCount * vw.getView().getWidth())) {
////                                            FrameLayout.LayoutParams rparams = null;
////                                            if (replicaF == null) {
////                                                replicaF = vw;
////                                                rparams = new FrameLayout.LayoutParams(
////                                                        (int) vw.getView().getWidth(), (int) vw.getView().getHeight());
////
////                                                rparams.leftMargin = (int) - vw.getView().getWidth();
////                                                replicaF.getView().setLayoutParams(rparams);
////                                            }
//////                                            if (PSInfiniteScrollView.this.findViewById(replicaF.getId()) == null) {
//////                                                PSInfiniteScrollView.this.addView(replicaF);
//////                                                itemToBeDrawns.add(0, replicaF);
//////                                                Log.d("Carousel", "Added to 0 index" + i);
//////                                            }
////                                        }
////                                        if (params.leftMargin >= (itemCount * vw.getView().getWidth())) {
////                                            FrameLayout.LayoutParams fparams = (FrameLayout.LayoutParams)replicaF.getView().getLayoutParams();
////                                            Log.d("Carousel", "First margin " + fparams.leftMargin + " Last Margin " + params.leftMargin);
////                                            if (replicaF != null) {
//////                                                if (PSInfiniteScrollView.this.findViewById(replicaF.getId()) != null) {
//////                                                    PSInfiniteScrollView.this.removeView(replicaF);
//////                                                    itemToBeDrawns.remove(replicaF);
//////                                                    itemToBeDrawns.remove(vw);
//////                                                    itemToBeDrawns.add(0, vw);
//////                                                    i = 0;
//////                                                    params.leftMargin = 0;
//////                                                    Log.d("Carousel",
//////                                                            "Removed from 0 index"
//////                                                                    + i);
//////
//////                                                }
////                                            }
////                                            replicaF = null;
////                                        }
////                                    } else {
////                                        if (params.leftMargin < 0
////                                                && params.leftMargin >= - vw.getView().getWidth()) {
////                                            FrameLayout.LayoutParams rparams = null;
////                                            if (replicaB == null) {
////                                                replicaB = vw;
////                                                rparams = new FrameLayout.LayoutParams((int) vw.getView().getWidth(), (int) vw.getView().getHeight());
////
////                                                rparams.leftMargin = (int) ((itemCount - 1) * vw.getView().getWidth());
////                                                replicaB.getView().setLayoutParams(rparams);
////                                            }
//////                                            if (PSInfiniteScrollView.this.findViewById(replicaB.getId()) == null) {
//////                                                PSInfiniteScrollView.this.addView(replicaB);
//////                                                itemToBeDrawns.add(replicaB);
//////                                                Log.d("Carousel", "Added  index" + i);
//////                                            }
////                                        }
////                                        if (params.leftMargin < - vw.getView().getWidth()) {
////                                            if (replicaB != null) {
//////                                                if (PSInfiniteScrollView.this.findViewById(replicaB.getId()) != null) {
//////                                                    PSInfiniteScrollView.this.removeView(replicaB);
//////                                                    itemToBeDrawns.remove(replicaB);
//////                                                    itemToBeDrawns.remove(vw);
//////                                                    itemToBeDrawns.add(vw);
//////                                                    i = 0;
//////                                                    params.leftMargin = (int) (itemCount * vw.getView().getWidth());
//////                                                    Log.d("Carousel", "Removed  index" + i);
//////                                                }
////                                            }
////                                            replicaB = null;
////                                        }
////                                    }
////                                }
////                                else
////                                    {
////
////                                }
//                                vw.getView().setLayoutParams(params);
//                            }

//                            if(replicaF != null){
//                                FrameLayout.LayoutParams fparams = (FrameLayout.LayoutParams)replicaF.getView().getLayoutParams();
//                                if(fparams.leftMargin < - replicaF.getView().getWidth()|| fparams.leftMargin > 0){
//                                    if (PSInfiniteScrollView.this.findViewById(replicaF.getId()) != null) {
//                                        replicaF.getLayoutParams();
//                                        PSInfiniteScrollView.this.removeView(replicaF);
//                                        itemToBeDrawns.remove(replicaF);
//                                        Log.d("Carousel","Removed  Front " + fparams.leftMargin);
//                                    }
//                                    replicaF = null;
//                                }
//                            }

                            if(touchDown) {
                                scrollX = 0;
                            }
                            else
                                {
                                scrollX = (float) (scrollX * DECELERATION_RATE);
                                if(Math.abs(scrollX) < 0.01)
                                    scrollX = 0;
                                Log.d("Carousel", "Velocity " + vTracker.getXVelocity() + " Scroll " + scrollX);
                            }
                            Collections.sort(itemToBeDrawns, new Comparator<ItemToBeDrawn>() {
                                        @Override
                                        public int compare(ItemToBeDrawn lhs, ItemToBeDrawn rhs) {
                                            FrameLayout.LayoutParams lhsParams = (FrameLayout.LayoutParams) lhs.getView().getLayoutParams();
                                            FrameLayout.LayoutParams rhsParams = (FrameLayout.LayoutParams) rhs.getView().getLayoutParams();
                                            return ((Integer) lhsParams.leftMargin).compareTo((Integer) rhsParams.leftMargin);
                                        }
                                    });
                        }
                    });

                }
            }

        }

        @Override
        public void run() {

            arrangeViews();

        }

    }

//    public void addItem(PSCloneableView vw){
//        FrameLayout.LayoutParams rparams = new FrameLayout.LayoutParams(
//                (int) itemSize.width, (int) itemSize.height);
//        rparams.leftMargin = (int) (itemToBeDrawns.size() * itemSize.width);
//        vw.setLayoutParams(rparams);
//        super.addView(vw, itemToBeDrawns.size());
//        itemToBeDrawns.add(vw);
//        itemCount = itemToBeDrawns.size();
//
//    }
//
//    public void removeItem(PSCloneableView vw){
//        super.removeView(vw);
//        itemToBeDrawns.remove(vw);
//        itemCount = itemToBeDrawns.size();
//    }


    class ItemToBeDrawn {
        private int index;
        private int marqueeItemPosition;
        private Context context;
        private View mainView;
        private ItemToBeDrawn prevItemToBeDrawn, nextItemToBeDrawn;
        private WeakReference<MarqueeAdapter> adapterWeakReference;

        public ItemToBeDrawn(int index, int marqueeItemPosition, Context context, WeakReference<MarqueeAdapter> adapterWeakReference) {
            this.index = index;
            this.marqueeItemPosition = marqueeItemPosition;
            this.context = context;
            this.adapterWeakReference = adapterWeakReference;
        }

        public View getView() {
            if (mainView == null)
            {
                View childView = new LinearLayout(context);
                childView.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,  FrameLayout.LayoutParams.WRAP_CONTENT));
                ((LinearLayout)childView).setOrientation(LinearLayout.HORIZONTAL);
//        ((LinearLayout.LayoutParams)((LinearLayout)childView).getLayoutParams()).setMargins(0, 0, 0, 0);
                ((LinearLayout)childView).addView(adapterWeakReference.get().getView(marqueeItemPosition, null, null));
                childView.setTag(this);
                mainView = childView;
            }
            return mainView;
        }

        private View getView(int index, int marqueeItemPosition) {
            ItemToBeDrawn itemToBeDrawn = new ItemToBeDrawn(index, marqueeItemPosition, context, adapterWeakReference);
            View childView = new LinearLayout(context);
            ((LinearLayout)childView).addView(adapterWeakReference.get().getView(marqueeItemPosition, null, null));
            childView.setTag(itemToBeDrawn);
            return childView;
        }

        public ItemToBeDrawn getPrevItemToBeDrawn() {
            return prevItemToBeDrawn;
        }

        public void setPrevItemToBeDrawn(ItemToBeDrawn prevItemToBeDrawn) {
            this.prevItemToBeDrawn = prevItemToBeDrawn;
        }

        public ItemToBeDrawn getNextItemToBeDrawn() {
            return nextItemToBeDrawn;
        }

        public void setNextItemToBeDrawn(ItemToBeDrawn nextItemToBeDrawn) {
            this.nextItemToBeDrawn = nextItemToBeDrawn;
        }

        public void addNextNode () {
            nextItemToBeDrawn = new ItemToBeDrawn(0, 0, getContext(), new WeakReference<MarqueeAdapter>(adapter));
        }

//        public View getPreviousView() {
//            int index = this.index-1;
//            if (marqueeItemPosition > 0)
//                return getView(index, marqueeItemPosition -1);
//            else
//                return getView(index, adapterWeakReference.get().getCount()-1);
//        }
//
//        public View getNextView() {
//            int index = this.index+1;
//            if (marqueeItemPosition == adapterWeakReference.get().getCount())
//                return getView(index, 0);
//            else
//                return getView(index, marqueeItemPosition +1);
//        }
    }


}