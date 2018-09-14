package com.application.newsapplication.ui.widget;


import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.application.newsapplication.R;
import com.application.newsapplication.ui.base.AppToolbar;

public class CustomToolBar extends Toolbar {

    private Context mContext;

    public CustomToolBar(Context context) {
        super(context);
        init(context);
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CustomToolBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        setTextAlignment(TEXT_ALIGNMENT_TEXT_START);
    }

    public void addSettings(AppToolbar settings) {
        String title = settings.getTitle() == null ? "" : settings.getTitle();
        setTitleMarginStart(0);
        setHomeButton(settings);
        setOverflowIcon(ContextCompat.getDrawable(getContext(), R.drawable.ic_more));
        setToolbarBackground(settings);
        applyFontToTitle(title);
        applyLogo(settings.getLogo());
    }

    private void applyLogo(int logo) {
        if (logo > -1) {
            setLogo(logo);
        }
    }

    private void setToolbarBackground(AppToolbar toolBarSetting) {
        int backgroundId = toolBarSetting.getBackground();
        if (backgroundId != -1) {
            this.setBackgroundResource(backgroundId);
        } else {
            this.setBackgroundColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
        }
    }

    private void setHomeButton(AppToolbar settings) {
        int drawableId;
        if (settings.isBackButtonEnabled()) {
            drawableId = R.drawable.ic_back;
        } else if (settings.isCloseButtonEnabled()) {
            drawableId = R.drawable.ic_cancel;
        } else if (settings.isNavigationButtonEnabled()) {
            drawableId = R.drawable.ic_menu;
        } else {
            drawableId = -1;
            setTitleMarginStart(mContext.getResources().getDimensionPixelSize(R.dimen.app_start_margin));
        }

        if (drawableId > -1) {
            setNavigationIcon(ContextCompat.getDrawable(getContext(), drawableId));
        }else{
            setNavigationIcon(null);
        }
    }

    private void applyFontToTitle(String title) {

        if (TextUtils.isEmpty(title)) {
            setTitle("");
            return;
        }

        SpannableString itemTitle = new SpannableString(title);
        setTitle(itemTitle);
        setTitleTextAppearance(getContext(), R.style.Toolbar_TitleText);
        setTitleTextColor(ContextCompat.getColor(getContext(), R.color.toolbar_txt_color));
        setTitle(title);
    }

}
