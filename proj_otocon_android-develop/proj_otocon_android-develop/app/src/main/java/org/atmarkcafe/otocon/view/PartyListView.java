package org.atmarkcafe.otocon.view;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.model.response.ConditionsearchData;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

/**
 * List Party support two style
 *
 * @author acv-truongvv
 */
public class PartyListView extends LinearLayout {

    public enum Type {
        none,//
        age,//
        gender,//
        special//
    }

    @BindView(R.id.title_type_text)
    TextView title_type_text;

    @BindView(R.id.title_type)
    TextView title_type;

    // Adapter of list party
//    private HomeAdapter homeAdapter;
    private GridDividerDecoration gridDividerDecoration;

    // Action for Onclick item
//    private BaseRecylerAdapter.OnItemClickListener onItemClickListener;

    @BindView(R.id.rb_list)
    RadioButton rbList;

    @BindView(R.id.listview)
    RecyclerView listview;


    public PartyListView(Context context) {
        super (context);

        init (R.layout.view_partylist);
    }

    public PartyListView(Context context, AttributeSet attrs) {
        super (context, attrs);

        init (R.layout.view_partylist);
    }



    public void init(int res) {
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        li.inflate(res, this);

        //gridDividerDecoration = new GridDividerDecoration ((int) getContext ().getResources ().getDimension (R.dimen.dimen_15dp), 2);

        ButterKnife.bind (this);
//        homeAdapter = new HomeAdapter (getContext (), R.layout.item_toppage_recyclerview_adapter);
        switchMode (rbList.isChecked () ? 1 : 2);

        title_type.setVisibility (GONE);
        updateNumberCount ();
    }

    @OnCheckedChanged(R.id.rb_list)
    public void onChangeLinearRecyclerView() {
        switchMode (rbList.isChecked () ? 1 : 2);
    }

    public void addData(List<ConditionsearchData> parties) {
//        homeAdapter.clear ();
//        if (parties != null) {
//            homeAdapter.setData (parties);
//            homeAdapter.notifyDataSetChanged ();
//        }

        updateNumberCount ();

    }

    public void setupTitleType(Type type, OnClickListener listener) {

        title_type.setVisibility (View.VISIBLE);

        switch (type) {

            case none:
                break;
            case age:
                break;
            case gender:
                break;
            case special:
                title_type.setVisibility (View.VISIBLE);
                break;
        }

        title_type.setOnClickListener (listener);
    }

    public void setTextTitleType(String text) {
        title_type.setText (text);
    }

    private void updateNumberCount() {

    }


    @OnClick(R.id.title_type)
    void onClickTitleType() {

    }

    private void switchMode(int mode) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager (getContext ());

        switch (mode) {
            case 1:
                listview.removeItemDecoration (gridDividerDecoration);
                linearLayoutManager.setOrientation (LinearLayoutManager.VERTICAL);
               // homeAdapter.setLayout (R.layout.item_toppage_recyclerview_adapter);
                break;
            case 2:
                listview.addItemDecoration (gridDividerDecoration);
                linearLayoutManager = new GridLayoutManager (getContext (), 2);
              //  homeAdapter.setLayout (R.layout.item_toppage_recyclerview_grid_adapter);

                break;
        }

        listview.setLayoutManager (linearLayoutManager);

      //  homeAdapter.setOnItemClickListener (this.onItemClickListener);

       // listview.setAdapter (homeAdapter);
    }

//    class HomeAdapter extends BaseRecylerAdapter {
//
//        public HomeAdapter(Context context, int layout) {
//            super (context, layout);
//        }
//
//        @Override
//        public BaseReceylerViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
//            return new BaseReceylerViewHolder (parent.getContext (), layout) {
//                @BindView(R.id.bnLinear)
//                Button bnLinear;
//
//                @BindView(R.id.bnCity)
//                Button bnCity;
//
//                @BindView(R.id.ivImageItem)
//                RoundedImageView ivImageItem;
//
//                @BindView(R.id.tvDateParty)
//                TextView tvDateParty;
//
//                @BindView(R.id.tvContent)
//                TextView tvContent;
//
//                @BindView(R.id.tvShortContent)
//                TextView tvShortContent;
//
//                @BindView(R.id.cbLike)
//                CheckBox cbLike;
//
//                @BindView(R.id.tvAgeMan)
//                TextView tvAgeOfMan;
//
//                @BindView(R.id.tvAgeOfWomen)
//                TextView tvAgeOfWomen;
//
//                @BindView(R.id.tvPriceOfMan)
//                TextView tvPriceOfMan;
//
//                @BindView(R.id.tvPriceOfWomen)
//                TextView tvPriceOfWomen;
//
//                @Override
//                protected void initViews() {
//                    super.initViews ();
//                    bnLinear = itemView.findViewById (R.id.bnLinear);
//                    bnCity = itemView.findViewById (R.id.bnCity);
//                    ivImageItem = itemView.findViewById (R.id.ivImageItem);
//                    tvDateParty = itemView.findViewById (R.id.tvDateParty);
//                    tvContent = itemView.findViewById (R.id.tvContent);
//                    tvShortContent = itemView.findViewById (R.id.tvShortContent);
//                    tvAgeOfMan = itemView.findViewById (R.id.tvAgeMan);
//                    tvAgeOfWomen = itemView.findViewById (R.id.tvAgeOfWomen);
//                    tvPriceOfMan = itemView.findViewById (R.id.tvPriceOfMan);
//                    tvPriceOfWomen = itemView.findViewById (R.id.tvPriceOfWomen);
//
//                    bnLinear.setOnClickListener ((view) -> {
//                        if (getOnItemClickListener () != null) {
//                            getOnItemClickListener ().onItemClickListener (position, data, 1);
//                        }
//                    });
//
//                    bnCity.setOnClickListener ((view) -> {
//                        if (getOnItemClickListener () != null) {
//                            getOnItemClickListener ().onItemClickListener (position, data, 2);
//                        }
//                    });
//                }
//
//                public void onClick(View view) {
//                    super.onClick (view);
//
//                    if (getOnItemClickListener () != null) {
//                        getOnItemClickListener ().onItemClickListener (position, data, 0);
//                    }
//                }
//
//                @Override
//                public void setData(int position, Object o) {
//                    super.setData (position, o);
//                    ConditionsearchData party = (ConditionsearchData) o;
//
//                    if (party != null) {
//                        tvDateParty.setText (party.getEventDate () == null ? "" : party.datePartyEventSplit (party.getEventDate (), parent.getContext ().getString (R.string.party_detail_event_date)));
//                        tvContent.setText (party.getName () == null ? "" : party.getName ());
//                        tvShortContent.setText (party.getSubName () == null ? "" : party.getSubName ());
//
//                        tvAgeOfMan.setText (String.format (parent.getContext ().getString (R.string.party_detail_age),
//                                party.getAgeFromM () == null ? "" : party.getAgeFromM (),
//                                party.getAgeToM ()
//                        ));
//
//                        tvAgeOfWomen.setText (String.format (party.getAgeFromF () == null ? "" : parent.getContext ().getString (R.string.party_detail_age),
//                                party.getAgeFromF (),
//                                party.getAgeToF ()
//                        ));
//
//                        tvPriceOfMan.setText (String.format (party.getSpecialPriceM () == null ? "" : parent.getContext ().getString (R.string.party_detail_circle),
//                                party.getSpecialPriceM ()
//                        ));
//
//                        tvPriceOfWomen.setText (String.format (party.getSpecialPriceF () == null ? "" : parent.getContext ().getString (R.string.party_detail_circle),
//                                party.getSpecialPriceF ()
//                        ));
//
//                        //Set image default
//                        ivImageItem.setImageResource (R.drawable.imageholder);
//
//                        if (party.getPicture () == null) {
//                            ivImageItem.setImageResource (R.drawable.imageholder);
//                        } else {
//
//                            //Load image
//                            ImageLoader.getInstance ().displayImage (party.getPicture () == null ? "" : BuildConfig.SERVER_URL + party.getPicture (), ivImageItem);
//                        }
//                    }
//                }
//            };
//        }
//    }
}
