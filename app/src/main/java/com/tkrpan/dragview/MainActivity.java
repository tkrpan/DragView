package com.tkrpan.dragview;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        private static OnDragLockListener mOnDragLockListener;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);



            Button button_lock = (Button)rootView.findViewById(R.id.button_lock);
            button_lock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnDragLockListener.onDragLock(true);
                }
            });

            Button button_unLock = (Button)rootView.findViewById(R.id.button_unLock);
            button_unLock.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnDragLockListener.onDragLock(false);
                }
            });

            return rootView;
        }

        public static void setOnDragLockListener(OnDragLockListener mOnDragLockListener) {
            PlaceholderFragment.mOnDragLockListener = mOnDragLockListener;
        }
    }
}
