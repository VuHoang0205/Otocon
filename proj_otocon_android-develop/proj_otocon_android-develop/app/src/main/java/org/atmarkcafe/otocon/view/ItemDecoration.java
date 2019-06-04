package org.atmarkcafe.otocon.view;

import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ItemDecoration extends RecyclerView.ItemDecoration {
    float top, left, bottom, right;

    boolean hasFirst = false;
    float firstTop, firstLeft, firstBottom, firstRight;

    public ItemDecoration(float top, float left, float bottom, float right) {
        this.top = top;
        this.left = left;
        this.bottom = bottom;
        this.right = right;
    }

    public ItemDecoration setFirst(float top, float left, float bottom, float right) {
        this.hasFirst = true;
        this.firstTop = top;
        this.firstLeft = left;
        this.firstBottom = bottom;
        this.firstRight = right;
        return this;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        float density = view.getContext().getResources().getDisplayMetrics().density;
        if (hasFirst) {
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
            int pos = params.getViewAdapterPosition();
            if (pos == 0) {
                outRect.top = (int) (this.firstTop * density);
                outRect.left = (int) (this.firstLeft * density);
                outRect.bottom = (int) (this.firstBottom * density);
                outRect.right = (int) (this.firstRight * density);
                return;
            }
        }
        outRect.top = (int) (this.top * density);
        outRect.left = (int) (this.left * density);
        outRect.bottom = (int) (this.bottom * density);
        outRect.right = (int) (this.right * density);
    }
}