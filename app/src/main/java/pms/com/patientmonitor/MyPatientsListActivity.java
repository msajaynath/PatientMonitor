package pms.com.patientmonitor;

import android.app.Activity;
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
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entity.Patient;
import Entity.Patients;

public class MyPatientsListActivity extends ListActivity {

    private List<Patients> ptlist;
    private TextView headText;
    private Connection connection;
    private Statement statement;
    private String doctorid;
    private Intent in;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients_list);
        headText= (TextView) findViewById(R.id.headText);
        final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);
         doctorid=sharedpreferences.getString("userid", "");

         in =getIntent();

        new DownloadWebPageTask().execute();
        }


//        else
//        {
//            String searchText=in.getStringExtra("search");
//            ptlist=Patient.findWithQuery(Patient.class, "Select * from Patient where doctor = ?", doctorid + "");
//
//                List<String> patNames = new ArrayList<String>();
//                 temp=ptlist;
//              //  String[] patNames=new String[ptlist.size()];
//                for(int i=0;i<ptlist.size();i++)
//                {
//                    String texts=ptlist.get(i).patient.name.toString().toLowerCase();
//
//                    if(texts.contains(searchText.toLowerCase()))
//                    patNames.add(ptlist.get(i).patient.name.toString());
//                    else
//                    {
//                        temp.remove(i);
//                    }
//
//                }
//                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                        android.R.layout.simple_list_item_1, patNames);
//
//
//                // Assign adapter to List
//                if(patNames.size()==0)
//                {
//                    headText.setText("No patients found!!!");
//                }
//            else {
//
//
//                    setListAdapter(adapter);
//                }
//
//        }








    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent=new Intent(MyPatientsListActivity.this,AddReportActivity.class);
        intent.putExtra("patid",ptlist.get(position).userid);
        intent.putExtra("docid",doctorid+"");
        intent.putExtra("patname",ptlist.get(position).name);
        startActivity(intent);
    }
    ArrayAdapter<String> adapter;

    private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            //if(!in.hasExtra("search"))
            {
                ptlist=new ArrayList<Patients>();
                try {
                    //Your code goes here

                    Class.forName("com.mysql.jdbc.Driver").newInstance();

                    Log.d("", "here1");

                    connection = DriverManager.getConnection(getString(R.string.JDBCUrl), getString(R.string.JDBCUsername), getString(R.string.JDBCPassword));
                    // Log.d("","here2");

                    statement = connection.createStatement();
                    // Log.d("","here2");


                    ResultSet rs = null;
                    if(!in.hasExtra("search"))

                    rs = statement.executeQuery("SELECT * FROM Patient where doctor='" + doctorid + "'");
                    else
                        rs = statement.executeQuery("SELECT * FROM Patient where doctor='" + doctorid + "' and name like('%"+ in.getStringExtra("search")+"%')");

                    Log.d("Debug", "here1");

                    while (rs.next()) {
                        ptlist.add(new Patients(rs.getString("patientid"), rs.getString("name"), rs.getString("doctor"), "", "", "", "", ""));
                    }


                    // ptlist = Patient.findWithQuery(Patient.class, "Select * from Patient where doctor = ?", doctorid + "");
                    String[] patNames = new String[ptlist.size()];
                    for (int i = 0; i < ptlist.size(); i++) {
                        patNames[i] = ptlist.get(i).name.toString();

                    }
                   adapter = new ArrayAdapter<String>(MyPatientsListActivity.this, android.R.layout.simple_list_item_1, patNames);


                    // Assign adapter to List
                    runOnUiThread (new Thread(new Runnable() {
                        public void run() {

                            setListAdapter(adapter);
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
