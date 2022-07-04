package com.cea.cares.ui.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;

import com.cea.cares.Background;
import com.cea.cares.R;
import com.cea.cares.RequestHandler;
import com.cea.cares.SharedPrefManager;
import com.cea.cares.URLs;
import com.cea.cares.User;
import com.cea.cares.databinding.FragmentActivityBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class ActivityFragment extends Fragment {

    private FragmentActivityBinding binding;
    String week;
    TextView textViewExc;
    User user;
    EditText drDate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        drDate = binding.drDate;
        textViewExc = binding.exc;

        user = SharedPrefManager.getInstance(getActivity()).getUser();

        week = String.valueOf(user.getWeek());


        getExc();


        Button button = binding.notification;
        button.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("ShortAlarm")
            @Override
            public void onClick(View view) {

                 String appDate = drDate.getText().toString().trim();

                if (TextUtils.isEmpty(appDate)) {
                    drDate.setError("Please enter the date");
                    drDate.requestFocus();
                    return;
                }
                else{
                    String regex = "^(3[01]|[12][0-9]|0[1-9])/(1[0-2]|0[1-9])/[0-9]{4}$";
                    boolean result = appDate.matches(regex);
                    if(result) {

                        Intent intent = new Intent(getActivity(), Background.class);

                        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_ONE_SHOT);
                        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                        long interval = 1000 * 20;

                        String dateandtime = appDate + " " + "08:00";
                        DateFormat formatter = new SimpleDateFormat("dd/M/yyyy hh:mm");

                        try {
                            Date date1 = formatter.parse(dateandtime);
                            Toast.makeText(getContext(), "Notification Set", Toast.LENGTH_SHORT).show();
                            alarmManager.set(AlarmManager.RTC_WAKEUP,date1.getTime(),pendingIntent);

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    } else {
                        drDate.setError("Please enter the date in dd/mm/yyyy format");
                        drDate.requestFocus();
                        return;
                    }


                }

            }
        });


        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



    public void addNotification( Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("my notification", "my notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        // Build notification
        // Actions are just fake

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,"my notification");
                builder.setContentTitle("Hello there,");
                builder.setContentText("The appointment is today");
                builder.setSmallIcon(R.drawable.cares_notification_icon);
                builder.setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        // hide the notification after its selected


        notificationManager.notify(0, builder.build());

    }

    public void getExc() {

        class GetExc extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("week",week);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_EXC, params);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);


                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        //getting the user from the response
                        String exc = obj.getString( "exc");

                        textViewExc.setText("These are the recommended exercises: \n " + exc);



                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        GetExc ge = new GetExc();
        ge.execute();

    }



}