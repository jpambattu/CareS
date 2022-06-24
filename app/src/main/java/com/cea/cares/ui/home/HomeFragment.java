package com.cea.cares.ui.home;


import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.cea.cares.RequestHandler;
import com.cea.cares.SharedPrefManager;
import com.cea.cares.URLs;
import com.cea.cares.User;
import com.cea.cares.databinding.FragmentHomeBinding;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private  String id,week;
    TextView textViewpredict,textViewFood;
    User user;
    int target;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {


        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        TextView textViewUsername = binding.textViewUsername;
        textViewpredict = binding.textViewpredict;
        textViewFood = binding.food;

        user = SharedPrefManager.getInstance(getActivity()).getUser();

        String text = "Hi " + user.getUsername();
        textViewUsername.setText(text);

        id = String.valueOf(user.getId());

        week = String.valueOf(user.getWeek());

        target = user.getTarget();
        if(target == 1)
            textViewpredict.setText("you and your baby is just fine" + "\nthis is your " + week + " week");
        else if (target == 0)
            textViewpredict.setText("Our baby is underweight!!!"+ "\nthis is your " + week + " week");

        getFood();

        Button button = binding.buttonpredict;
        button.setOnClickListener(view -> predict());



        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void predict(){

        class Predict extends AsyncTask<Void, Void, String> {
            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id",id);


                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_PREDICT+id,params);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                Toast.makeText(getActivity(),"Predicted Successfully",Toast.LENGTH_SHORT).show();

                //HomeViewModel homeViewModel = new HomeViewModel();
                //homeViewModel.refreshUser(requireContext());


                refreshUser();


            }


        }
        //executing the async task
        Predict pr = new Predict();
        pr.execute();
    }

    public void refreshUser() {

        class RefreshUser extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id",id);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_REFRESH, params);
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
                        SharedPrefManager.getInstance(getActivity()).userLogin(user);

                        User usercpy = SharedPrefManager.getInstance(getActivity()).getUser();

                        int res = usercpy.getTarget();
                        int week = usercpy.getWeek();
                        if(res == 1)
                            textViewpredict.setText("you and your baby is just fine" + "\nthis is your " + week + " week");
                        else if (res == 0)
                            textViewpredict.setText("Our baby is underweight!!!"+ "\nthis is your " + week + " week");

//                        target = String.valueOf(res);
//                        textViewpredict.setText(target);

                    } else {
                        Toast.makeText(getActivity(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        RefreshUser ru = new RefreshUser();
        ru.execute();

    }

    public void getFood() {

        class GetFood extends AsyncTask<Void, Void, String> {

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("week",week);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_FOOD, params);
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
                        String food = obj.getString( "food");

                        textViewFood.setText("These are the recommended food items\n" + food);



                    } else {
                        Toast.makeText(getActivity(), "Some error occurred", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

        GetFood gf = new GetFood();
        gf.execute();

    }





}