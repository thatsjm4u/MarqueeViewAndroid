package in.niffler_work.marqueeviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    final String TAG = "MainActivity";

    private ArrayList<Integer> imageItems = new ArrayList<>();
    private MarqueeFrameLayout marqueeView;
    private MarqueeAdapter marqueeAdapter;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        marqueeView = findViewById(R.id.marqueeView);
        listView = findViewById(R.id.listView);

        imageItems.add(android.R.drawable.ic_delete);
        imageItems.add(R.drawable.p1);
        imageItems.add(R.drawable.p2);
//        imageItems.add(android.R.drawable.ic_secure);
//        imageItems.add(android.R.drawable.ic_input_add);
//        imageItems.add(android.R.drawable.ic_input_get);
        marqueeAdapter = new MarqueeAdapter(getBaseContext(), imageItems);

        listView.setAdapter(marqueeAdapter);
        marqueeView.setAdapter(marqueeAdapter);

//        frameLayout = findViewById(R.id.frameLayout);
//        LoadInitialData();
////        frameLayout.setMeasureAllChildren(true);
//        for (AppCompatImageView img : imageItems) {
//            frameLayout.addView(img);
//        }
//
//        frameLayout.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
//            @Override
//            public boolean onPreDraw() {
//                Log.d(TAG, "Height : " + frameLayout.getHeight() + " Width : " + frameLayout.getWidth());
//                configureCirculerView(frameLayout.getWidth());
//                layoutWidth = frameLayout.getWidth();
//                return true;
//            }
//        });

//        frameLayout.getViewTreeObserver().addOnScrollChangedListener(new ViewTreeObserver.OnScrollChangedListener() {
//            @Override
//            public void onScrollChanged() {
//                configureCirculerView(frameLayout.getWidth());
//            }
//        });
    }

//    private void configureCirculerView(int width) {
//        if (layoutWidth != width) {
//            if (layoutWidth < width) {
//                for (AppCompatImageView imageView : imageItems) {
//                    Log.d(TAG, " IMAGEVIEW Height : " + imageView.getHeight() + " Width : " + imageView.getWidth() + " Parent Width : " + width);
//                }
//            }
//        }
//    }
//
//    private void LoadInitialData() {
//
//        AppCompatImageView imageView = new AppCompatImageView(getBaseContext());
//        imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
////        imageView.setBackgroundColor(Color.BLUE);
//        imageView.setImageResource(android.R.drawable.ic_btn_speak_now);
//        imageItems.add(imageView);
//        imageView = new AppCompatImageView(getBaseContext());
//        imageView.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
////        imageView.setBackgroundColor(Color.RED);
//        imageView.setImageResource(android.R.drawable.ic_delete);
//        imageItems.add(imageView);
//    }


}
