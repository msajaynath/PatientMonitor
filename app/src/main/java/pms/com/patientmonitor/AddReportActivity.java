package pms.com.patientmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

import Entity.Lab;
import Entity.Patient;
import Entity.User;

public class AddReportActivity extends AppCompatActivity {
    SeekBar heart,temp,pulse;
    TextView headerText;
    TextView theart,ttemp,tpulse;
    Button submit;
    EditText spo2,diagnosis;
    private User patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_report);
        Intent in=getIntent();
        patient=User.findById(User.class,in.getLongExtra("patid",0));
        headerText= (TextView) findViewById(R.id.headertext);
        headerText.setText("Report for "+patient.name);
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
                SaveReportWithValidation(patient);

            }
        });
    }
    private void SaveReportWithValidation(User p)
    {
        Calendar c = Calendar.getInstance();

        if(diagnosis.getText().toString().length()==0)
        {
            Toast.makeText(getApplicationContext(),"Please leave comments!",Toast.LENGTH_SHORT).show();
            return;
        }
        long spo2val=0;

        if(spo2.getText().toString().length()>0)
            spo2val=Long.getLong(spo2.getText().toString(),0);

        Lab labReport=new Lab(p,diagnosis.getText().toString(),heart.getProgress(),pulse.getProgress(),temp.getProgress(),spo2val,System.currentTimeMillis());
        labReport.save();
        Toast.makeText(getApplicationContext(),"Report saved!",Toast.LENGTH_SHORT).show();
        finish();

    }

}
