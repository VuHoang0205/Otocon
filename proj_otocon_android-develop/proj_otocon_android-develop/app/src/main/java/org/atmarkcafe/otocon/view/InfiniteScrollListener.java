package org.atmarkcafe.otocon.view;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public abstract class InfiniteScrollListener extends RecyclerView.OnScrollListener {
    private boolean loading = true; // True if we are still waiting for the last set of data to load.
    private int firstVisibleItem = 0;
    private int totalItemCount = 0;
    private int currentPage = 0;
    private int limitNumberToCallLoadMore = 1;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    private Runnable loadMore = () -> {
        onLoadMore(currentPage);
    };

    public void setLinearLayoutManager(LinearLayoutManager linearLayoutManager) {
        this.linearLayoutManager = linearLayoutManager;
    }

    public void setGridLayoutManager(GridLayoutManager gridLayoutManager) {
        this.gridLayoutManager = gridLayoutManager;
    }

    public void setLoading(boolean loading) {
        this.loading = loading;
    }

    @Override
    public void onScrolled(final RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (linearLayoutManager != null) {
            totalItemCount = linearLayoutManager.getItemCount();
            firstVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            if (loading
                    && linearLayoutManager.getChildCount() > 0
                    && firstVisibleItem >= totalItemCount - limitNumberToCallLoadMore
                    && totalItemCount >= linearLayoutManager.getChildCount()) {

                loading = false;
                //currentPage++;
                recyclerView.post(loadMore);
            }
        }
        if (gridLayoutManager != null) {
            totalItemCount = gridLayoutManager.getItemCount();
            firstVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
            if (loading
                    && gridLayoutManager.getChildCount() > 0
                    && firstVisibleItem >= totalItemCount - limitNumberToCallLoadMore
                    && totalItemCount >= gridLayoutManager.getChildCount()) {

                loading = false;
                //currentPage++;
                recyclerView.post(loadMore);
            }
        }
    }

    public abstract void onLoadMore(int current_page);
}
