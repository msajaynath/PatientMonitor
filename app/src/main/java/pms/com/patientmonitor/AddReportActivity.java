package pms.com.patientmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

import Entity.Lab;
import Entity.Patient;
import Entity.Patients;
import Entity.User;

public class AddReportActivity extends AppCompatActivity {
    SeekBar heart,temp,pulse;
    TextView headerText;
    TextView theart,ttemp,tpulse;
    Button submit;
    EditText spo2,diagnosis;
    Intent in;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        in=getIntent();
        Log.d("Begin", in.getStringExtra("docid"));

        // patient=User.findById(User.class,in.getLongExtra("patid",0));
        headerText= (TextView) findViewById(R.id.headertext);
        headerText.setText("Report for "+in.getStringExtra("patname"));
        heart= (SeekBar) findViewById(R.id.seekheart);
        theart= (TextView) findViewById(R.id.hrrate);
        temp= (SeekBar) findViewById(R.id.seektemp);
        ttemp= (TextView) findViewById(R.id.ttemp);
        pulse= (SeekBar) findViewById(R.id.seekpulse);
        tpulse= (TextView) findViewById(R.id.tpulse);
        submit= (Button) findViewById(R.id.submit);
        diagnosis= (EditText) findViewById(R.id.diagnosis);
        spo2= (EditText) findViewById(R.id.spo2);

        heart.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                theart.setText(progress+" /min");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        pulse.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tpulse.setText(progress+" bpm");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        temp.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ttemp.setText(progress+"  celsius");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveReportWithValidation();

            }
        });
    }
    private Connection connection;
    private Statement statement;
    private void SaveReportWithValidation()
    {
        Calendar c = Calendar.getInstance();

        if(diagnosis.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Please leave comments!",Toast.LENGTH_SHORT).show();
            return;
        }


////
        Thread thread = new Thread(new Runnable()
        {
            @Override
            public void run()
            {
        try {
             String spo2val="0";

           if(spo2.getText().toString().length()>0)
                spo2val=spo2.getText().toString();
            //Your code goes here

            Class.forName("com.mysql.jdbc.Driver").newInstance();

            Log.d("", "here1");

            connection = DriverManager.getConnection(getString(R.string.JDBCUrl), getString(R.string.JDBCUsername), getString(R.string.JDBCPassword));
            // Log.d("","here2");

            statement = connection.createStatement();
            // Log.d("","here2");


            ResultSet rs = null;

            statement.executeUpdate("INSERT INTO Lab(patientid,doctorid,diagnosis,heartrate,pulse,temperature,spo2,ddate)"
                    + " VALUES ('" + in.getStringExtra("patid") + "','" + in.getStringExtra("docid") + "','" + diagnosis.getText().toString() + "'," + heart.getProgress() + "," + pulse.getProgress() + "," + temp.getProgress() + "," + spo2val + "," + System.currentTimeMillis() + ")");



            Log.d("", "here2");

            // Assign adapter to List
            connection.close();

            runOnUiThread(new Runnable() {
                public void run() {
                    Toast.makeText(getApplicationContext(), "Report saved!", Toast.LENGTH_SHORT).show();
                    finish();

                }
            });

            Toast.makeText(getApplicationContext(),"Report saved!",Toast.LENGTH_SHORT).show();



        } catch (Exception e) {
            Log.d("dev",e.toString());
        }
            }
        });
        ///
        thread.start();;


    }

}
