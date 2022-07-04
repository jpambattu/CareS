package com.cea.cares;


import android.content.Context;

import android.os.AsyncTask;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class UpdateWeek  {

    String createdDate,id,week;
    int weekNo;
    User user;

    String currentDate = new SimpleDateFormat("dd/M/yyyy", Locale.getDefault()).format(new Date());

    public void updateWeek(Context context)

    {

        user = SharedPrefManager.getInstance(context).getUser();

        id = String.valueOf(user.getId());
        weekNo = user.getWeek();

        getcreatedDate(context);

    }

    void getcreatedDate(Context context){

        class GetcreatedDate extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id",id);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_DATE,params);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {
                    //converting response to json object
                    JSONObject obj = new JSONObject(s);

                    //if no error in response
                    if (!obj.getBoolean("error")) {

                        String str = obj.getString("date");


                        String[] parts = str.split(",");
                        createdDate = parts[0];
                        week = parts[1];



                        Date date1;
                        Date date2;
                        SimpleDateFormat dates = new SimpleDateFormat("dd/MM/yyyy");
                        try {
                            date1 = dates.parse(currentDate);
                            date2 = dates.parse(createdDate);
                            long difference =  Math.abs(date2.getTime() - date1.getTime());
                            long differenceDates = difference / (24 * 60 * 60 * 1000);
                            int gap = (int) (differenceDates / 7);

                            if((Integer.parseInt(week) + gap) > weekNo) {
                                weekNo = Integer.parseInt(week) + gap ;
                                updatedWeek(context);
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
        //executing the async task
        GetcreatedDate gd = new GetcreatedDate();
        gd.execute();
    }

    void updatedWeek(Context context){

        class UpdatedWeek extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("week",String.valueOf(weekNo));
                params.put("date",createdDate + "," + week);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_WEEK,params);
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
                        JSONObject userJson = obj.getJSONObject("user");

                        //creating a new user object
                        User user = new User(
                                userJson.getInt("id"),
                                userJson.getInt("target"),
                                userJson.getString("username"),
                                userJson.getString("email"),
                                userJson.getInt("week")
                        );

                        //storing the user in shared preferences
                        SharedPrefManager.getInstance(context).userLogin(user);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


        }
        //executing the async task
        UpdatedWeek uw = new UpdatedWeek();
        uw.execute();
    }

}
