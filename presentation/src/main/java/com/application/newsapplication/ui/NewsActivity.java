package com.application.newsapplication.ui;

import android.app.AlertDialog;
import android.view.MenuItem;

import com.application.newsapplication.R;
import com.application.newsapplication.ui.base.BaseActivity;
import com.application.newsapplication.ui.newslist.HomeScreenFragment;

public class NewsActivity extends BaseActivity {

    @Override
    protected void initViews() {
        getNavigator().launchWelcomeScreen();
    }

    @Override
    protected boolean shouldDisableDrawer() {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                handleHomeButtonClick();
                break;
            case R.id.menu_exit:
                handleExitButtonClick();
                break;
        }
        return true;
    }

    private void handleExitButtonClick() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(getString(R.string.dlg_msg_app_exit))
                .setNegativeButton(getString(R.string.dlg_negative_btn), (dialogInterface, i) -> dialogInterface.cancel())
                .setPositiveButton(getString(R.string.dlg_positive_btn), (dialogInterface, i) -> finish()).show();
    }

    @Override
    public void onBackPressed() {
        if (getTopFragment() instanceof HomeScreenFragment) {
            handleExitButtonClick();
        } else {
            super.onBackPressed();
        }
    }
}
