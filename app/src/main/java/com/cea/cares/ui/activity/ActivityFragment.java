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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class ActivityFragment extends Fragment {

    private FragmentActivityBinding binding;
    String week;
    TextView textViewExc;
    User user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentActivityBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        EditText drDate = binding.drDate;
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
                 String currentDate = new SimpleDateFormat("dd/M/yyyy", Locale.getDefault()).format(new Date());

                if(Objects.equals(appDate, ""))
                    Toast.makeText(getActivity(),"Please enter the date", Toast.LENGTH_LONG).show();
                else{
                    Date date1;
                    Date date2;
                    SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        date1 = dates.parse(currentDate);
                        date2 = dates.parse(appDate);
                        long difference =  Math.abs(date1.getTime() - date2.getTime());
                        long differenceDates = (difference / (24 * 60 * 60 * 1000)) - 1;


                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    Intent intent = new Intent(getActivity(), Background.class);

                    PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

                    long interval = 1000 * 10;

                    alarmManager.setExact(AlarmManager.RTC_WAKEUP,interval,pendingIntent);
                    //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, 20000, interval, pendingIntent);
                    Toast.makeText(getContext(), "Notification Set", Toast.LENGTH_SHORT).show();
                    //addNotification();
                }

                //addNotification();
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

//        Intent intent = new Intent(context, Background.class);
//
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//        alarmManager.cancel(pendingIntent);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("my notification", "my notification", NotificationManager.IMPORTANCE_HIGH);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }


        // Build notification
        // Actions are just fake
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context,"my notification");
                builder.setContentTitle("New notification");
                builder.setContentText("Subject");
                builder.setSmallIcon(R.drawable.ic_home_black_24dp);
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
                        Toast.makeText(getActivity(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                        //getting the user from the response
                        String exc = obj.getString( "exc");

                        textViewExc.setText("These are the recommended exercises \n " + exc);



                    } else {
                        Toast.makeText(getActivity(), "Some error occurred", Toast.LENGTH_SHORT).show();
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