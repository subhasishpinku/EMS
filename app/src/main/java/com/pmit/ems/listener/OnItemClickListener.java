package com.pmit.ems.listener;


import android.view.View;

public interface OnItemClickListener {
    void onLongClick(View view, int position, int id);

    void onClick(View view, int position, int id);
}
