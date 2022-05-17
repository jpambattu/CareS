package com.cea.cares.ui.moreactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cea.cares.R;
import com.cea.cares.SharedPrefManager;
import com.cea.cares.User;
import com.cea.cares.databinding.FragmentMoreactionsBinding;

public class MoreActionsFragment extends Fragment {

    private FragmentMoreactionsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentMoreactionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        textView.setText("More Actions");

        final Button button = binding.buttonLogout;
        button.setOnClickListener(view -> SharedPrefManager.getInstance(getActivity()).logout());

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
