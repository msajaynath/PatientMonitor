package pms.com.patientmonitor;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import Entity.Lab;
import Entity.Patient;
import Entity.User;

public class ViewReportActivity extends AppCompatActivity {

    TextView heart,temp,pulse;
    TextView headerText;
    TextView theart,ttemp,tpulse;
    TextView spo2;
    EditText diagnosis;
    private Lab labreport;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
        Intent in=getIntent();
        labreport=Lab.findById(Lab.class,in.getLongExtra("report_id",0));
        Patient pat=Patient.findById(Patient.class,labreport.patient.getId());
        headerText= (TextView) findViewById(R.id.headertext);
        headerText.setText("Report by Dr.  "+pat.doctor.name);
        heart= (TextView) findViewById(R.id.seekheart);
        theart= (TextView) findViewById(R.id.hrrate);
        temp= (TextView) findViewById(R.id.seektemp);
        ttemp= (TextView) findViewById(R.id.ttemp);
        pulse= (TextView) findViewById(R.id.seekpulse);
        tpulse= (TextView) findViewById(R.id.tpulse);
        diagnosis= (EditText) findViewById(R.id.diagnosis);
        spo2= (TextView) findViewById(R.id.spo2);
        heart.setText(labreport.heartrate+"");
        temp.setText(labreport.temprature+"");
        pulse.setText(labreport.pulse+"");
        spo2.setText(labreport.spo2+"");
        diagnosis.setText(labreport.diagnosis+"");
    }
}
