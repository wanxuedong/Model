package world.share.home.ui.dashboard;

import static world.share.lib_base.RouterUrl.LOGIN_ACTIVITY;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import world.share.home.R;
import world.share.home.databinding.FragmentDashboardBinding;
import world.share.lib_base.SimpleFragment;

public class DashboardFragment extends SimpleFragment<FragmentDashboardBinding> {

    private DashboardViewModel dashboardViewModel;

    @Override
    public int attachView() {
        return R.layout.fragment_dashboard;
    }

    @Override
    public void initData() {
        super.initData();
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        final TextView textView = binding.textDashboard;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
    }

    @Override
    public void iniEvent() {
        super.iniEvent();
        binding.textDashboard.setOnClickListener(this);
    }

    @Override
    public void onClick(int id) {
        super.onClick(id);
        jump(LOGIN_ACTIVITY);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}