package pms.com.patientmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class PatientHomeActivity extends AppCompatActivity {
    ImageView myinfo,reportlist,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_home);
        myinfo= (ImageView) findViewById(R.id.my_bio);
        reportlist= (ImageView) findViewById(R.id.my_report);
        logout= (ImageView) findViewById(R.id.logout);

        reportlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientHomeActivity.this, ReportListActivity.class));

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);

                sharedpreferences.edit().putBoolean("logged", false).commit();
                Toast.makeText(getApplicationContext(), "Logged out!!", Toast.LENGTH_SHORT).show();

                finish();

            }
        });
        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PatientHomeActivity.this, PatientBioActivity.class));

            }
        });
    }
    }

