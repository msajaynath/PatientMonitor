package pms.com.patientmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

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
    Intent in;
    private Connection connection;
    private Statement statement;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_report);
         in=getIntent();
        headerText= (TextView) findViewById(R.id.headertext);
        heart= (TextView) findViewById(R.id.seekheart);
        theart= (TextView) findViewById(R.id.hrrate);
        temp= (TextView) findViewById(R.id.seektemp);
        ttemp= (TextView) findViewById(R.id.ttemp);
        pulse= (TextView) findViewById(R.id.seekpulse);
        tpulse= (TextView) findViewById(R.id.tpulse);
        diagnosis= (EditText) findViewById(R.id.diagnosis);
        spo2= (TextView) findViewById(R.id.spo2);
       new  DownloadWebPageTask().execute();

    }


    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            //if(!in.hasExtra("search"))
            {
                final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);
                String patid=sharedpreferences.getString("userid", "");

                ///reportList=new ArrayList<Lab>();
                try {
                    //Your code goes here

                    Class.forName("com.mysql.jdbc.Driver").newInstance();

                    Log.d("", "here1");

                    connection = DriverManager.getConnection(getString(R.string.JDBCUrl), getString(R.string.JDBCUsername), getString(R.string.JDBCPassword));
                    // Log.d("","here2");

                    statement = connection.createStatement();
                    // Log.d("","here2");


                    ResultSet rs = null;

                    rs = statement.executeQuery("SELECT * FROM Lab where labid=" + in.getStringExtra("reportid") + "");
                    Log.d("Debug", "here1");

                    while (rs.next()) {
                        Log.d("Debug", rs.getInt("labid")+"");

                        labreport=new Lab(rs.getInt("labid"), rs.getString("patientid"),"",rs.getString("diagnosis")
                                ,rs.getInt("heartrate"),rs.getInt("pulse"),rs.getFloat("temperature"),rs.getLong("spo2"),rs.getString("ddate"));
                       // reportList.add(new Lab(rs.getInt("labid"), rs.getString("patientid"),"", "", 0, 0, 0.f,Long.parseLong("0"),rs.getString("ddate")));
                    }

                    Log.d("Debug", "here2");

                    // ptlist = Patient.findWithQuery(Patient.class, "Select * from Patient where doctor = ?", doctorid + "");
                    // String[] patNames = new String[ptlist.size()];
//                    for (int i = 0; i < ptlist.size(); i++) {
//                        patNames[i] = ptlist.get(i).name.toString();
//
//                    }




                    // Assign adapter to List
                    runOnUiThread (new Thread(new Runnable() {
                        public void run() {
                           // headerText.setText("Report by Dr.  "+pat.doctor.name);

        heart.setText(labreport.heartrate+"");
        temp.setText(labreport.temperature+"");
        pulse.setText(labreport.pulse+"");
        spo2.setText(labreport.spo2+"");
        diagnosis.setText(labreport.diagnosis+"");
                        }
                    }));
                    connection.close();

                } catch (Exception e) {
                    Log.d("dev",e.toString());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }

}
