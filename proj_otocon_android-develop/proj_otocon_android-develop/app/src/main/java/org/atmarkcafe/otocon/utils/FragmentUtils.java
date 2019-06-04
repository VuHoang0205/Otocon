package org.atmarkcafe.otocon.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.R;

public class FragmentUtils {


    public static void replace(FragmentActivity activity, Fragment f, boolean isBack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        addAnimation(transaction, R.anim.slide_left_in, R.anim.slide_right_out);

        transaction.replace(R.id.container_body, f, f.getClass().getName());

        if (isBack) {
            transaction.addToBackStack(f.getClass().getName());
        }

        transaction.commitAllowingStateLoss();
    }
    public static void add(FragmentActivity activity, Fragment f, boolean isBack) {
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();

        addAnimation(transaction, R.anim.slide_left_in, R.anim.slide_right_out);

        transaction.add(R.id.container_body, f, f.getClass().getName());

        if (isBack) {
            transaction.addToBackStack(f.getClass().getName());
        }

        transaction.commitAllowingStateLoss();
    }

    public static void replaceChild(FragmentManager childFragment, int id, OtoconBindingFragment f, boolean isBack) {
        FragmentTransaction transaction = childFragment.beginTransaction();
        addAnimation(transaction, R.anim.slide_left_in, R.anim.slide_right_out);
        transaction.replace(id, f, f.getClass().getName());
        f.setStoreChildFrgementManager(childFragment);

        if (isBack) {
            transaction.addToBackStack(f.getClass().getName());
        }

        transaction.commitAllowingStateLoss();
    }

    public static void backFragment(FragmentActivity activity, Class aClass){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        fragmentManager.popBackStackImmediate(aClass.getName(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public static void backToTop(FragmentActivity activity){
        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        if (fragmentManager.getBackStackEntryCount()>0) {
            String name = fragmentManager.getBackStackEntryAt(0).getName();
            fragmentManager.popBackStackImmediate(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }

    private static void addAnimation(FragmentTransaction transaction, int enter, int exit) {
        transaction.setCustomAnimations(R.anim.slide_left_in, 0, 0, R.anim.slide_right_out);
    }

    public static Fragment getFragment(FragmentActivity activity) {
        return activity.getSupportFragmentManager().findFragmentById(R.id.container_body);
    }
}
