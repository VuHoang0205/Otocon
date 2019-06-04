package org.atmarkcafe.otocon.function.mypage;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;
import com.xwray.groupie.Section;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.AcitivityMypageBinding;
import org.atmarkcafe.otocon.databinding.FragmentMypageRootBinding;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.function.mypage.CalendarCollaborationFragment;
import org.atmarkcafe.otocon.function.mypage.NotificationSettingFragment;
import org.atmarkcafe.otocon.function.mypage.coupon.CouponListFragment;
import org.atmarkcafe.otocon.function.mypage.item.MyPageActionItem;
import org.atmarkcafe.otocon.function.mypage.profile.ProfileSettingFragment;
import org.atmarkcafe.otocon.function.party.LikePartyListFragment;
import org.atmarkcafe.otocon.function.party.RegisteredPartyListFragment;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.KeyExtensionUtils;

public class MyPageRootFragment extends OtoconBindingFragment<FragmentMypageRootBinding> {

    public enum Rediect{
        none, coupon;
    }

    public static MyPageRootFragment create(Rediect rediect){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KeyExtensionUtils.KEY_REDIRECT, rediect);

        MyPageRootFragment fragment = new MyPageRootFragment();

        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public int layout() {
        return R.layout.fragment_mypage_root;
    }

    @Override
    public void onCreateView(FragmentMypageRootBinding mBinding) {
       setStoreChildFrgementManager(getChildFragmentManager());

       switch ((Rediect)getArguments().getSerializable(KeyExtensionUtils.KEY_REDIRECT)){

           case none:
              //FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, new MyPageMenuFragment(), false);
               break;
           case coupon:
               FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, new CouponListFragment(), false);
               break;
       }

    }

}
