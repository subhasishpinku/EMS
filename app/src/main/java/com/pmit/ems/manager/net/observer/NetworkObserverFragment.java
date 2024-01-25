package com.pmit.ems.manager.net.observer;

import androidx.fragment.app.Fragment;

import com.pmit.ems.ui.Backpressedlistener;

import java.util.ArrayDeque;
import java.util.Queue;

import retrofit2.Call;

public class NetworkObserverFragment extends Fragment implements Backpressedlistener {
    private Queue<Call<?>> mCalls;
    public static Backpressedlistener backpressedlistener;
    public void addNetworkRequest(Call<?> call) {
        if (mCalls == null) {
            mCalls = new ArrayDeque<>();
        }

        mCalls.add(call);
    }

    public void cancelRequests() {
        if (mCalls != null) {
            while (mCalls.size() > 0) {
                Call<?> poll = mCalls.poll();
                if (poll != null)
                    poll.cancel();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        cancelRequests();

    }
    @Override
    public void onPause() {
        backpressedlistener=null;
        super.onPause();
    }


    // Overriding onResume() method
    @Override
    public void onResume() {
        super.onResume();
        backpressedlistener=this;
    }

    @Override
    public void onBackPressed() {
        if(NetworkObserverFragment.backpressedlistener!=null){
            NetworkObserverFragment.backpressedlistener.onBackPressed();

        }
    }
}
