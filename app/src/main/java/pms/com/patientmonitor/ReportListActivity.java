package pms.com.patientmonitor;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import Entity.Lab;
import Entity.Patient;
import Entity.Patients;

public class ReportListActivity extends ListActivity {

    private List<Lab> reportList;
    private Connection connection;
    private Statement statement;
    ArrayAdapter<String> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);
        String patid=sharedpreferences.getString("userid", "0");
//
        new DownloadWebPageTask().execute();
    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent=new Intent(ReportListActivity.this,ViewReportActivity.class);
        intent.putExtra("reportid", reportList.get(position).labid+"");
        startActivity(intent);
    }

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            //if(!in.hasExtra("search"))
            {
                final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);
                String patid=sharedpreferences.getString("userid", "");

                reportList=new ArrayList<Lab>();
                try {
                    //Your code goes here

                    Class.forName("com.mysql.jdbc.Driver").newInstance();

                    Log.d("", "here1");

                    connection = DriverManager.getConnection(getString(R.string.JDBCUrl), getString(R.string.JDBCUsername), getString(R.string.JDBCPassword));
                    // Log.d("","here2");

                    statement = connection.createStatement();
                    // Log.d("","here2");


                    ResultSet rs = null;

                        rs = statement.executeQuery("SELECT * FROM Lab where patientid='" + patid + "'");
                    Log.d("Debug", "here1");

                    while (rs.next()) {
                        reportList.add(new Lab(rs.getInt("labid"), rs.getString("patientid"),"", "", 0, 0, 0.f,Long.parseLong("0"),rs.getString("ddate")));
                        Log.d("Debug", rs.getInt("labid")+"");

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

                            String[] reportNames=new String[reportList.size()];
                            for(int i=0;i<reportList.size();i++)
                            {
                                Log.d("Debug", reportList.get(i).date+"");

                                SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy ");
                                String formatDate = formatter.format(Long.parseLong(reportList.get(i).date));


                                reportNames[i]="Report on "+formatDate;

                            }
                            Log.d("Debug", "here3");
                            adapter = new ArrayAdapter<String>(ReportListActivity.this,
                                    android.R.layout.simple_list_item_1, reportNames);


                            // Assign adapter to List
                            setListAdapter(adapter);                        }
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
