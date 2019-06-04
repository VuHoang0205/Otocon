package org.atmarkcafe.otocon.extesion;

import android.graphics.Color;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.function.home.items.ItemParty;
import org.atmarkcafe.otocon.model.Party;
import org.atmarkcafe.otocon.view.InfiniteScrollListener;

import java.util.ArrayList;
import java.util.List;

public class PartyListAdapter extends GroupAdapter {

    public interface PartyListLoadListener extends ItemParty.ItemPartyListener {
        public void onLoadMore(int currentPage);
    }

    private boolean isLoading = false;
    private PartyListLoadListener listener;

    public PartyListAdapter(PartyListLoadListener loadMoreListener) {
        super();
        this.listener = loadMoreListener;
        columnSection = new Section();
        add(columnSection);
    }

    private Section columnSection;
    private RecyclerView recyclerView;

    private int totalParty = 0;

    private ItemParty.ShowType showType = ItemParty.ShowType.list;


    public void setShowType(ItemParty.ShowType showType) {
        this.showType = showType;

        switchLayout();
    }

    public ItemParty.ShowType getShowType() {
        return showType;
    }

    public int getTotalParty() {
        return totalParty;
    }

    @Override
    public void clear() {
        super.clear();
        datas.clear();
        setCurrentPage(0);

        columnSection = new Section();
        add(columnSection);

        notifyDataSetChanged();
    }

    public void setRecyclerView(RecyclerView mRecyclerView) {
        this.recyclerView = mRecyclerView;
        recyclerView.setAdapter(this);
        recyclerView.addOnScrollListener(onScrollListener);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
                super.getItemOffsets(outRect, view, parent, state);

                if (getShowType() == ItemParty.ShowType.grid) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) view.getLayoutParams();
                    final int position = params.getViewAdapterPosition();
                    float density = recyclerView.getContext().getResources().getDisplayMetrics().density;
                    if (position % 2 == 0) {
                        outRect.top = (int) (density * 0.0);
                        outRect.left = (int) (density * 10.0);
                        outRect.bottom = (int) (density * 15.0);
                        outRect.right = (int) (density * 4);
                    } else {
                        outRect.top = (int) (density * 0.0);
                        outRect.left = (int) (density * 4.0);
                        outRect.bottom = (int) (density * 15.0);
                        outRect.right = (int) (density * 10.0);
                    }
                } else {
                    outRect.top = 0;
                    outRect.left = 0;
                    outRect.bottom = 0;
                    outRect.right = 0;
                }
            }
        });
        switchLayout();
    }

    public void switchLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), 2);

        recyclerView.setLayoutManager(getShowType() == ItemParty.ShowType.list ? linearLayoutManager : gridLayoutManager);
        notifyDataSetChanged();
    }

    public int size() {
        return columnSection.getGroupCount();
    }

    private List<Party> datas = new ArrayList<>();

    public boolean remove(Party party){
        if (datas.contains(party)){
            datas.remove(party);
            columnSection.remove(new ItemParty(this, party, null));
            this.totalParty--;
            notifyDataSetChanged();
            return true;
        }
        return false;
    }

    public void updateData(int total, List<Party> parties) {
        this.totalParty = total;

        if (parties != null && parties.size() > 0) {
            for (Party party : parties) {
                if (!datas.contains(party)) {
                    datas.add(party);
                } else {
                    int index = datas.indexOf(party);
                    datas.remove(party);
                    datas.add(index, party);
                }
            }

            List<ItemParty> shows = new ArrayList<>();
            for (Party party : datas) {
                ItemParty itemParty = new ItemParty(this, party, listener);
                itemParty.setTypeShow(getShowType());
                shows.add(itemParty);
            }

            setCurrentPage(onScrollListener.currentPage + 1);
            columnSection.update(shows);
        }

        notifyDataSetChanged();
    }


    public void setCurrentPage(int page) {
        onScrollListener.currentPage = page;
    }

    public int getCurrentPage(){
        return  onScrollListener.currentPage;
    }

    // load more
    private OnScrollListener onScrollListener = new OnScrollListener();

    public void setLoading(boolean loading){
        this.isLoading = loading;
    }

    class OnScrollListener extends RecyclerView.OnScrollListener {
        int currentPage = 0;

        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (isLoading) return;

            int totalItemCount = columnSection.getItemCount();
            int lastVisibleItem = 0;

            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                lastVisibleItem = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

            if (layoutManager instanceof LinearLayoutManager) {
                lastVisibleItem = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            }

            if (totalParty > totalItemCount && lastVisibleItem == size() - 1) {
                // load more
                if (listener != null) {
                    isLoading = true;
                    listener.onLoadMore(currentPage);
                }
            }
        }
    }
}
