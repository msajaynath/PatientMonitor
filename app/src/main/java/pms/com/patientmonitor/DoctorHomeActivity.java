package pms.com.patientmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DoctorHomeActivity extends AppCompatActivity {
    ImageView myinfo,patlist,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_home);
        myinfo= (ImageView) findViewById(R.id.my_bio);
        patlist= (ImageView) findViewById(R.id.my_report);
        logout= (ImageView) findViewById(R.id.logout);

        patlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorHomeActivity.this,MyPatientsListActivity.class));

            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);

                sharedpreferences.edit().putBoolean("logged",false).commit();
                Toast.makeText(getApplicationContext(), "Logged out!!", Toast.LENGTH_SHORT).show();

                finish();

            }
        });

        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DoctorHomeActivity.this, DoctorBioActivity.class));

            }
        });

    }
}
