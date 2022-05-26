package com.cea.cares.ui.profile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cea.cares.MainActivity;
import com.cea.cares.R;
import com.cea.cares.RequestHandler;
import com.cea.cares.SharedPrefManager;
import com.cea.cares.URLs;
import com.cea.cares.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class UpdateProfile extends AppCompatActivity {

    EditText orderGravida,age,height,weight,midarmcir,workload,fever,anemia, iron2, bleed, asthma, convolusion2, bad_obs_history, injection,falif, iron, workload2, convolusion, bleed1, asthma2, inject2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        orderGravida = findViewById(R.id.orderGravida);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        midarmcir = findViewById(R.id.midarmcir);
        workload = findViewById(R.id.workload);
        anemia = findViewById(R.id.anemia);
        asthma = findViewById(R.id.asthma);
        bad_obs_history = findViewById(R.id.bad_obsteric_history);
        injection = findViewById(R.id.injection);
        falif = findViewById(R.id.falif);
        iron = findViewById(R.id.iron);
        workload2 = findViewById(R.id.second_workload);
        convolusion = findViewById(R.id.convolusion);
        bleed1 = findViewById(R.id.bleed);
        asthma2 = findViewById(R.id.asthma2);
        inject2 = findViewById(R.id.injection2);
        iron2 = findViewById(R.id.iron2);
        convolusion2 = findViewById(R.id.convolusion2);
        bleed = findViewById(R.id.bleed2);
        fever = findViewById(R.id.fever);

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(view -> profileUpdate());




    }

    private void profileUpdate(){
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String id = String.valueOf(user.getId());

        final String OrderGravida = orderGravida.getText().toString().trim();
        final String mage = age.getText().toString().trim();
        final String hght = height.getText().toString().trim();
        final String wght = weight.getText().toString().trim();
        final String mid_arm_cir = midarmcir.getText().toString().trim();
        final String wkload = workload.getText().toString().trim();
        final String anmia = anemia.getText().toString().trim();
        final String astma = asthma.getText().toString().trim();
        final String bad_histry = bad_obs_history.getText().toString().trim();
        final String inj = injection.getText().toString().trim();
        final String flif = falif.getText().toString().trim();
        final String fe = iron.getText().toString().trim();
        final String wkload2 = workload2.getText().toString().trim();
        final String cnv = convolusion.getText().toString().trim();
        final String bld = bleed1.getText().toString().trim();
        final String astma2 = asthma2.getText().toString().trim();
        final String inj2 = inject2.getText().toString().trim();
        final String fe2 = iron2.getText().toString().trim();
        final String cnv2 = convolusion2.getText().toString().trim();
        final String bld2 = bleed.getText().toString().trim();
        final String fvr = fever.getText().toString().trim();

        float h = Float.parseFloat(hght);
        int w = Integer.parseInt(wght);
        final String bmi = String.valueOf(w/h*h);

        //validations to be added

        class ProfileUpdate extends AsyncTask<Void, Void, String> {

            private ProgressBar progressBar;

            @Override
            protected String doInBackground(Void... voids) {
                //creating request handler object
                RequestHandler requestHandler = new RequestHandler();

                //creating request parameters
                HashMap<String, String> params = new HashMap<>();
                params.put("id",id);
                params.put("parity", OrderGravida);
                params.put("mother_age", mage);
                params.put("mother_height", hght);
                params.put("mid_arm_cir", mid_arm_cir);
                params.put("workload", wkload);
                params.put("anemia", anmia);
                params.put("asthma", astma);
                params.put("bad_obs_history", bad_histry);
                params.put("injection", inj);
                params.put("falif", flif);
                params.put("iron", fe);
                params.put("workload2", wkload2);
                params.put("convolusion", cnv);
                params.put("bleed", bld);
                params.put("asthma2", astma2);
                params.put("inject2", inj2);
                params.put("iron2", fe2);
                params.put("convolusion2", cnv2);
                params.put("bleed2", bld2);
                params.put("fever", fvr);
                params.put("BMI", bmi);

                //returing the response
                return requestHandler.sendPostRequest(URLs.URL_UPDATE, params);
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //displaying the progress bar while user registers on the server
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
                progressBar.setVisibility(View.VISIBLE);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //hiding the progressbar after completion
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getApplicationContext(),"Updated Successfully",Toast.LENGTH_SHORT).show();
            }
        }

        //executing the async task
        ProfileUpdate pu = new ProfileUpdate();
        pu.execute();

        startActivity(new Intent(this, MainActivity.class));
    }


}
