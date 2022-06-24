package com.cea.cares.ui.profile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import java.util.Objects;

public class UpdateProfile extends AppCompatActivity {

    EditText orderGravida,age,height,weight,midarmcir;
    RadioGroup workload,fever,anemia, iron2, bleed, asthma, convolusion2, bad_obs_history, injection,falif, iron, workload2, convolusion, bleed1, asthma2, inject2;
    String temp ;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_profile);

        orderGravida = findViewById(R.id.orderGravida);
        age = findViewById(R.id.age);
        height = findViewById(R.id.height);
        weight = findViewById(R.id.weight);
        midarmcir = findViewById(R.id.midarmcir);

        workload = findViewById(R.id.radioWorkload);
        anemia = findViewById(R.id.radioAnemia);
        asthma = findViewById(R.id.radioAsthma);
        bad_obs_history = findViewById(R.id.radioBadh);
        injection = findViewById(R.id.radioInjection);
        falif = findViewById(R.id.radioFalif);
        iron = findViewById(R.id.radioIron);
        workload2 = findViewById(R.id.radioSecWrk);
        convolusion = findViewById(R.id.radioConv);
        bleed1 = findViewById(R.id.radioBleed);
        asthma2 = findViewById(R.id.radioSecAst);
        inject2 = findViewById(R.id.radioSecInj);
        iron2 = findViewById(R.id.radioSecIrn);
        convolusion2 = findViewById(R.id.radioSecCnv);
        bleed = findViewById(R.id.radioSecBld);
        fever = findViewById(R.id.radioFvr);

        Button submit = findViewById(R.id.submit);

        submit.setOnClickListener(view -> profileUpdate());




    }

    private void profileUpdate(){
        User user = SharedPrefManager.getInstance(getApplicationContext()).getUser();
        String id = String.valueOf(user.getId());


        int wl = workload.getCheckedRadioButtonId();
        RadioButton radioButtonwl =  findViewById(wl);
        if(wl==-1){

        }
        else{
            if(Objects.equals(radioButtonwl.getText(), "yes"))
                 temp = "1.0";
            else
                temp = "0.0";
        }
        final String wkload = temp ;


        int anm = anemia.getCheckedRadioButtonId();
        RadioButton radioButtonanm =  findViewById(anm);
        if(anm==-1){

        }
        else{
            if(Objects.equals(radioButtonanm.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String anmia = temp ;

        int ast = asthma.getCheckedRadioButtonId();
        RadioButton radioButtonast =  findViewById(ast);
        if(ast==-1){

        }
        else{
            if(Objects.equals(radioButtonast.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String astma = temp ;

        int bahs = bad_obs_history.getCheckedRadioButtonId();
        RadioButton radioButtonbahs =  findViewById(bahs);
        if(bahs==-1){

        }
        else{
            if(Objects.equals(radioButtonbahs.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String bad_histry = temp ;

        int injct = injection.getCheckedRadioButtonId();
        RadioButton radioButtoninjct =  findViewById(injct);
        if(injct==-1){

        }
        else{
            if(Objects.equals(radioButtoninjct.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String inj = temp ;

        int flf = falif.getCheckedRadioButtonId();
        RadioButton radioButtonflf =  findViewById(flf);
        if(flf==-1){

        }
        else{
            if(Objects.equals(radioButtonflf.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String flif = temp ;

        int irn = iron.getCheckedRadioButtonId();
        RadioButton radioButtonirn =  findViewById(irn);
        if(irn==-1){

        }
        else{
            if(Objects.equals(radioButtonirn.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String fe = temp ;

        int wl2 = workload2.getCheckedRadioButtonId();
        RadioButton radioButtonwl2 =  findViewById(wl2);
        if(wl2==-1){

        }
        else{
            if(Objects.equals(radioButtonwl2.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String wkload2 = temp ;

        int clnv = convolusion.getCheckedRadioButtonId();
        RadioButton radioButtonclnv =  findViewById(clnv);
        if(clnv==-1){

        }
        else{
            if(Objects.equals(radioButtonclnv.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String cnv = temp ;

        int bled = bleed1.getCheckedRadioButtonId();
        RadioButton radioButtonbled =  findViewById(bled);
        if(bled==-1){

        }
        else{
            if(Objects.equals(radioButtonbled.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String bld = temp ;

        int ast2 = asthma2.getCheckedRadioButtonId();
        RadioButton radioButtonast2 =  findViewById(ast2);
        if(ast==-1){

        }
        else{
            if(Objects.equals(radioButtonast2.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";
        }
        final String astma2 = temp ;

        int injct2 = inject2.getCheckedRadioButtonId();
        RadioButton radioButtoninjct2 =  findViewById(injct2);
        if(injct2==-1){

        }
        else{
            if(Objects.equals(radioButtoninjct2.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String inj2 = temp ;


        int irn2 = iron2.getCheckedRadioButtonId();
        RadioButton radioButtonirn2 =  findViewById(irn2);
        if(irn2==-1){

        }
        else{
            if(Objects.equals(radioButtonirn2.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String fe2 = temp ;

        int clnv2 = convolusion2.getCheckedRadioButtonId();
        RadioButton radioButtonclnv2 =  findViewById(clnv2);
        if(clnv2==-1){

        }
        else{
            if(Objects.equals(radioButtonclnv2.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String cnv2 = temp ;

        int bled2 = bleed.getCheckedRadioButtonId();
        RadioButton radioButtonbled2 =  findViewById(bled2);
        if(bled2==-1){

        }
        else{
            if(Objects.equals(radioButtonbled2.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String bld2 = temp ;

        int fvrr = fever.getCheckedRadioButtonId();
        RadioButton radioButtonfvrr =  findViewById(fvrr);
        if(fvrr==-1){

        }
        else{
            if(Objects.equals(radioButtonfvrr.getText(), "yes"))
                temp = "1.0";
            else
                temp = "0.0";

        }
        final String fvr = temp ;


        final String OrderGravida = orderGravida.getText().toString().trim();
        final String mage = age.getText().toString().trim();
        final String hght = height.getText().toString().trim();
        final String wght = weight.getText().toString().trim();
        final String mid_arm_cir = midarmcir.getText().toString().trim();



        float h = Float.parseFloat(hght);
        int w = Integer.parseInt(wght);
        final String bmi = String.valueOf(w/(h*h));

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
