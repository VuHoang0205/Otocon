package org.atmarkcafe.otocon.function.mypage.card;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import org.atmarkcafe.otocon.BR;
import org.atmarkcafe.otocon.OtoconBindingFragment;
import org.atmarkcafe.otocon.OtoconFragment;
import org.atmarkcafe.otocon.R;
import org.atmarkcafe.otocon.api.MVPExtension;
import org.atmarkcafe.otocon.api.MVPPresenter;
import org.atmarkcafe.otocon.api.interactor.InteractorManager;
import org.atmarkcafe.otocon.databinding.*;
import org.atmarkcafe.otocon.dialog.DialogRematchMessage;
import org.atmarkcafe.otocon.dialog.PopupMessageErrorDialog;
import org.atmarkcafe.otocon.model.CreditCard;
import org.atmarkcafe.otocon.model.response.CreditCardResponse;
import org.atmarkcafe.otocon.model.response.OnResponse;
import org.atmarkcafe.otocon.utils.FragmentUtils;
import org.atmarkcafe.otocon.utils.LogUtils;
import org.atmarkcafe.otocon.view.CustomGridLayoutManager;
import org.atmarkcafe.otocon.view.ItemDecoration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SettingCardChooseFragment extends OtoconBindingFragment<FragmentCreditCardManagerBinding> implements OtoconFragment.OtoconFragmentListener, MVPExtension.View<OnResponse> {
    private SettingCardChoosePresenter presenter = new SettingCardChoosePresenter(this);

    private SettingCardChooseAdapter adapter = new SettingCardChooseAdapter(this);

    private CustomGridLayoutManager customGridLayoutManager;

    @Override
    public int layout() {
        return R.layout.fragment_credit_card_manager;
    }

    SwipeController controller;
    ItemDecoration decoration = new ItemDecoration(16, 0f, 0f, 0) {
        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

            controller.onDraw(c);
        }
    };
    public  void disableScrollOnTime(long time){
        customGridLayoutManager.setScrollEnabled(false);
        viewDataBinding.getRoot().postDelayed(new Runnable() {
            @Override
            public void run() {
                customGridLayoutManager.setScrollEnabled(true);
            }
        }, time);
    }
    @Override
    public void onCreateView(FragmentCreditCardManagerBinding viewDataBinding) {
        viewDataBinding.toolbar.setNavigationOnClickListener((view) -> getActivity().onBackPressed());
        viewDataBinding.recyclerview.setLayoutManager(customGridLayoutManager = new CustomGridLayoutManager(getActivity()));
        viewDataBinding.setFragment(this);

        adapter.setRecyclerView(viewDataBinding.recyclerview);
        controller = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {


                new DialogRematchMessage(getActivity(), getString(R.string.message_popup_delete_card), new DialogRematchMessage.DialogRematchListener() {
                    @Override
                    public void onClickButtonDialog(boolean isOke, boolean isChecked) {
                        if (isOke) {
                            adapter.notifyItemRangeChanged(position, adapter.getItemCount());
                            // call to delte
                            onDeleteCard(adapter.getItem(position));
                        }
                    }
                }).setTextButtonGreen(getString(R.string.yes))
                        .setTexButtonBlack(getString(R.string.no))
                        .show();

            }
        });

        viewDataBinding.recyclerview.addItemDecoration(decoration);

        viewDataBinding.recyclerview.setAdapter(adapter);
        viewDataBinding.bottomButtonLayout.getRoot().setVisibility(View.GONE);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(controller);
        itemTouchHelper.attachToRecyclerView(viewDataBinding.recyclerview);

        presenter.onExecute(getActivity(), SettingCardChoosePresenter.ACTION_CARD_LIST, null);
    }

    public void onSubmit() {
        // add card
        adapter.setErrorCard(null, false, false, false, false);
        presenter.onExecute(getActivity(), SettingCardChoosePresenter.ACTION_CARD_ADD, adapter.newCard);

    }

    public void onDeleteCard(CreditCard card) {
        adapter.setErrorCard(null, false, false, false, false);
        presenter.onExecute(getActivity(), SettingCardChoosePresenter.ACTION_CARD_DELETE, card);
        adapter.notifyDataSetChanged();

        if (card.isDefault()) {
            presenter.onExecute(getActivity(), SettingCardChoosePresenter.ACTION_CARD_LIST, adapter.getItem(0));
            adapter.notifyDataSetChanged();
        }

    }

    public void onSetDefaultCard(CreditCard card) {
        if (!card.isDefault()) {
            adapter.setErrorCard(null, false, false, false, false);
            adapter.notifyDataSetChanged();
            presenter.onExecute(getActivity(), SettingCardChoosePresenter.ACTION_CARD_DEFAULT, card);
        }
    }

    public void clickCreditCard(CreditCard card) {
        adapter.setErrorCard(null, false, false, false, false);
        UpdateCreditCardFragment f = UpdateCreditCardFragment.create(card, this);
        FragmentUtils.replaceChild(getStoreChildFrgementManager(), R.id.frame, f, true);
    }

    // OtoconFragment.OtoconFragmentListener
    @Override
    public void onHandlerReult(int status, Bundle extras) {
        // reload data
        presenter.onExecute(getActivity(), SettingCardChoosePresenter.ACTION_CARD_LIST, null);
    }

    // MVPExtension.View<OnResponse>
    @Override
    public void showPopup(String title, String message) {
        PopupMessageErrorDialog.show(getActivity(), title, message, null);
    }

    @Override
    public void success(OnResponse response) {
        if (response != null) {
            if (response instanceof CreditCardResponse) {

                adapter.newCard.isCheckCreate = false;
                adapter.newCard = new NewCreditCard();
                adapter.setDatas(((CreditCardResponse) response).getCreditCardList());

                if (response.isSuccess()&&((CreditCardResponse) response).getCreditCardList()!=null&&!checkDefaultCard(adapter.credits)){
                    presenter.onExecute(getActivity(), SettingCardChoosePresenter.ACTION_CARD_DEFAULT, adapter.credits.get(0));
                }
                if (decoration != null) {
                    viewDataBinding.recyclerview.removeItemDecoration(decoration);
                }

                decoration = new ItemDecoration(adapter.credits.size() == 0 ? 0 : 16f, 0f, 0, 0) {
                    @Override
                    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                        controller.onDraw(c);
                    }
                };
                viewDataBinding.recyclerview.addItemDecoration(decoration);

            } else if (response instanceof AddNewCardResponse) {
                adapter.setErrorCard(null, false, false, false, false);
                presenter.onExecute(getActivity(), SettingCardChoosePresenter.ACTION_CARD_LIST, null);
            } else {
                // reload card
                presenter.onExecute(getActivity(), SettingCardChoosePresenter.ACTION_CARD_LIST, null);
            }
        }
    }

    @Override
    public void showProgress(boolean show) {
        setEnableBack(!show);
        viewDataBinding.loading.getRoot().setVisibility(show ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showMessage(OnResponse onResponse) {
        if (onResponse != null && onResponse instanceof AddNewCardResponse) {
            AddNewCardResponse response = (AddNewCardResponse) onResponse;
            String messageError = null;
            if (response.hasError("payment_card_number") && adapter.newCard.getNumber().length()>0){
                messageError = response.getErrorMessage("payment_card_number");
            }
            if (messageError == null){
                messageError = response.getMessage();
            }
            adapter.setErrorCard(messageError,
                    response.hasError("payment_card_number"),
                    response.hasError("payment_card_year"),
                    response.hasError("payment_card_month"),
                    response.hasError("payment_card_secure"));
        }
    }

    public void onCheckedNewCard(boolean isChecked) {
        onHideKeyboard();
        viewDataBinding.txtBottom.setVisibility(isChecked ? View.GONE : View.VISIBLE);
        viewDataBinding.bottomButtonLayout.getRoot().setVisibility(isChecked ? View.VISIBLE : View.GONE);
    }

    public boolean checkDefaultCard(List<CreditCard> creditCards) {
        if (creditCards != null) {
            for (CreditCard card : creditCards) {
                if (card.isDefault()) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }
}


class SettingCardChooseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    RecyclerView recyclerView;

    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    String errorMessage;
    boolean payment_card_number;
    boolean payment_card_year;
    boolean payment_card_month;
    boolean payment_card_secure;

    public void setErrorCard(String message, boolean payment_card_number, boolean payment_card_year, boolean payment_card_month, boolean payment_card_secure) {
        this.errorMessage = message;
        this.payment_card_number = payment_card_number;
        this.payment_card_year = payment_card_year;
        this.payment_card_month = payment_card_month;
        this.payment_card_secure = payment_card_secure;

        notifyDataSetChanged();
    }

    SettingCardChooseFragment fragment;
    public NewCreditCard newCard = new NewCreditCard();

    public SettingCardChooseAdapter(SettingCardChooseFragment fragment) {
        this.fragment = fragment;
    }

    // 1 is footer
    @Override
    public int getItemViewType(int position) {
        if (isPositionFooter(position)) {
            return 1;
        }
        return 0;
    }

    private boolean isPositionFooter(int position) {
        return position >= credits.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == 0) {
            LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ViewDataBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_setting_credit_card, viewGroup, false);
            return new ViewHolder(binding);
        } else {
            return CreditCardViewHolder.create(viewGroup);
        }
    }

    public void initView(){
        if (credits.size() == 0) newCard.isCheckCreate = true;
        else newCard.isCheckCreate = false;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof CreditCardViewHolder) {

            ((CreditCardViewHolder) viewHolder).bind(newCard, fragment, credits.size(), errorMessage, payment_card_number, payment_card_year, payment_card_month, payment_card_secure);
//            ((CreditCardViewHolder) viewHolder).setHeight(recyclerView.getHeight());
        } else if (viewHolder instanceof ViewHolder) {
            ((ViewHolder) viewHolder).bind(credits.get(i), fragment);
        }
    }

    @Override
    public int getItemCount() {
        return credits.size() + 1;
    }

    List<CreditCard> credits = new ArrayList<>();

    public void setDatas(List<CreditCard> credits) {
        if (credits == null) {
            credits = new ArrayList<>();
        }
        this.credits = credits;
        initView();
        notifyDataSetChanged();
    }

    public CreditCard getItem(int position) {
        return credits.get(position);
    }
}

class ViewHolder extends RecyclerView.ViewHolder {
    ViewDataBinding binding;

    public ViewHolder(@NonNull ViewDataBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(CreditCard card, SettingCardChooseFragment fragment) {
        binding.setVariable(BR.card, card);
        binding.setVariable(BR.fragment, fragment);
    }
}

class SettingCardChoosePresenter extends MVPPresenter<CreditCard, OnResponse> {
    public static final int ACTION_CARD_LIST = 0;

    public static final int ACTION_CARD_ADD = 1;

    public static final int ACTION_CARD_DELETE = 2;

    public static final int ACTION_CARD_DEFAULT = 3;

    public SettingCardChoosePresenter(MVPExtension.View<OnResponse> view) {
        super(view);
    }

    @Override
    public void onExecute(Context context, int action, CreditCard card) {
        // action 0 --> get card list
        // action 1 --> add card
        // action 2 --> delete card
        // action 3 --> update card default
        if (action == ACTION_CARD_LIST) {
            execute(InteractorManager.getApiInterface(context).getCreditCardList(), new ExecuteListener<CreditCardResponse>() {
                @Override
                public void onNext(CreditCardResponse response) {
                    if (response != null && response.isSuccess()) {
                        view.success(response);
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, response);
                        view.showPopup(messages[0], messages[1]);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        } else if (action == ACTION_CARD_ADD) {
            Map<String, List<String>> errors = card.getErrors(true);

            LogUtils.d("card : " + errors, null);
            if (errors != null) {
                AddNewCardResponse response = new AddNewCardResponse();
                response.setCode(0);
                response.setErrors(errors);
                response.setMessage(card.getErrorString(context, true));
                view.showMessage(response);
                return;
            }
            card.setSecure(card.getSecurityCode());
            execute(InteractorManager.getApiInterface(context).addCreditCard(card), new ExecuteListener<AddNewCardResponse>() {
                @Override
                public void onNext(AddNewCardResponse response) {
                    if (response != null && response.isSuccess()) {
                        view.success(response);
                    } else if (response == null){
                        String[] messages = OnResponse.getMessage(context, null, response);
                        view.showPopup(messages[0], messages[1]);
                    } else {
                        view.showMessage(response);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        } else if (action == ACTION_CARD_DELETE) {
            execute(InteractorManager.getApiInterface(context).deleteCreditCard(card.getId() + ""), new ExecuteListener<OnResponse>() {
                @Override
                public void onNext(OnResponse response) {
                    if (response != null && response.isSuccess()) {
                        view.success(response);
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, response);
                        view.showPopup(messages[0], messages[1]);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        } else if (action == ACTION_CARD_DEFAULT) {
            execute(InteractorManager.getApiInterface(context).updateDefaultCreditCard(card.getId() + ""), new ExecuteListener<OnResponse>() {
                @Override
                public void onNext(OnResponse response) {
                    if (response != null && response.isSuccess()) {
                        view.success(response);
                    } else {
                        String[] messages = OnResponse.getMessage(context, null, response);
                        view.showPopup(messages[0], messages[1]);
                    }
                }

                @Override
                public void onError(Throwable e) {
                    String[] messages = OnResponse.getMessage(context, e, null);
                    view.showPopup(messages[0], messages[1]);
                }
            });
        }
    }
}
