package world.share.home.ui.home;

import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import world.share.home.R;
import world.share.home.databinding.FragmentHomeBinding;
import world.share.lib_base.SimpleFragment;

public class HomeFragment extends SimpleFragment<FragmentHomeBinding> {

    private HomeViewModel homeViewModel;

    @Override
    public int attachView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initData() {
        super.initData();
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
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