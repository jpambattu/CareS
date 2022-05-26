package com.cea.cares.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cea.cares.SharedPrefManager;
import com.cea.cares.User;
import com.cea.cares.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    User user;
    String username,email;

    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textNotifications;
        textView.setText("This is profile Fragment");

        user = SharedPrefManager.getInstance(getActivity()).getUser();
        username = user.getUsername();
        email = user.getEmail();

        TextView usernameemail = binding.UsernameEmail;
        usernameemail.setText(username + "\n" + email);

        Button button = binding.update;
        button.setOnClickListener(this::profileupdate);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void profileupdate(View view){
        Intent intent = new Intent(getActivity(), UpdateProfile.class);
        startActivity(intent);
    }
}