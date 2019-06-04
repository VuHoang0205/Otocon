package org.atmarkcafe.otocon.function.party.register.card.items;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.support.annotation.NonNull;

import com.xwray.groupie.databinding.BindableItem;

import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.databinding.ItemCreditCardBinding;
import org.atmarkcafe.otocon.model.CreditCard;

public class CreditCardItem extends BindableItem<ItemCreditCardBinding> {

    public final ObservableBoolean selected = new ObservableBoolean(false);
    public final ObservableField<String> errorMessage = new ObservableField<>("");
    public final ObservableField<String> securityCode = new ObservableField<>("");
    private ObservableInt selectedIndex;

    public CreditCard creditCard;

    public CreditCardItem(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public CreditCardItem setSelectedIndex(ObservableInt selectedIndex) {
        this.selectedIndex = selectedIndex;
        return this;
    }

    @Override
    public int getLayout() {
        return R.layout.item_credit_card;
    }

    @Override
    public void bind(@NonNull ItemCreditCardBinding viewBinding, int position) {
        viewBinding.setItem(this);
        selected.set(position == selectedIndex.get());
    }
}
