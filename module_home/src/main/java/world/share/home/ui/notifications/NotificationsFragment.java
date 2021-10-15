package world.share.home.ui.notifications;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import world.share.home.R;
import world.share.home.databinding.FragmentNotificationsBinding;
import world.share.lib_base.SimpleFragment;

public class NotificationsFragment extends SimpleFragment<FragmentNotificationsBinding> {

    private NotificationsViewModel notificationsViewModel;

    @Override
    public int attachView() {
        return R.layout.fragment_notifications;
    }

    @Override
    public void initData() {
        super.initData();
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        final TextView textView = binding.textNotifications;
        notificationsViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}