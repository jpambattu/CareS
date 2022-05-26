package com.cea.cares.ui.moreactions;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cea.cares.R;
import com.cea.cares.RequestHandler;
import com.cea.cares.SharedPrefManager;
import com.cea.cares.URLs;
import com.cea.cares.User;
import com.cea.cares.databinding.FragmentMoreactionsBinding;

import java.util.HashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MoreActionsFragment extends Fragment {

    private FragmentMoreactionsBinding binding;
    private ScheduledExecutorService scheduleTaskExecutor;
    int week;
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentMoreactionsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textDashboard;
        textView.setText("More Actions");

        final Button button = binding.buttonLogout;
        button.setOnClickListener(view -> SharedPrefManager.getInstance(getActivity()).logout());



        scheduleTaskExecutor = Executors.newScheduledThreadPool(15);

        //Schedule a task to run every 5 seconds (or however long you want)
        scheduleTaskExecutor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                // Do stuff here!

                user = SharedPrefManager.getInstance(getActivity()).getUser();

                week = user.getWeek() + 1;

                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("week",String.valueOf(week));

                //returing the response
                requestHandler.sendPostRequest(URLs.URL_WEEK, params);





            }
        }, 0, 15, TimeUnit.SECONDS); // or .MINUTES, .HOURS etc.

        return root;
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}
