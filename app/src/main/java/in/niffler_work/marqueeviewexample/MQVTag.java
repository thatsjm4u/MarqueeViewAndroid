package in.niffler_work.marqueeviewexample;

import android.view.View;

public interface MQVTag {

    View getView(int position);
    View getPreviousView();
    View getNextView();

}
