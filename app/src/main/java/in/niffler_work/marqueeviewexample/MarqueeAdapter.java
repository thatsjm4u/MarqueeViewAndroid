package in.niffler_work.marqueeviewexample;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatImageView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class MarqueeAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Integer> items;

    public MarqueeAdapter(Context context, ArrayList items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View childView, ViewGroup viewGroup) {
        Item viewHolder;
        if (childView == null) {
            AppCompatImageView imageView = new AppCompatImageView(context);
            imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            imageView.setId(android.R.id.text1);
            childView = new LinearLayout(context);
            ((LinearLayout)childView).addView(imageView);
            viewHolder = new Item(childView);
            childView.setTag(viewHolder);
        } else
            viewHolder = (Item) childView.getTag();
            viewHolder.getImageView().setImageResource(items.get(position));
        return childView;
    }

    class Item {
        private View childView;
        private AppCompatImageView imageView;

        public Item(View childView) {
            this.childView = childView;
            this.imageView = childView.findViewById(android.R.id.text1);
        }

        public AppCompatImageView getImageView() {
            return imageView;
        }
    }
}

