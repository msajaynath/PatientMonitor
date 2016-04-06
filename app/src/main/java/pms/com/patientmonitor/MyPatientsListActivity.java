package pms.com.patientmonitor;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Entity.Patient;

public class MyPatientsListActivity extends ListActivity {

    private List<Patient> ptlist;
    private TextView headText;
    private List<Patient> temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients_list);
        headText= (TextView) findViewById(R.id.headText);
        final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);
        long doctorid=sharedpreferences.getLong("userid", 0);

        Intent in =getIntent();
        if(!in.hasExtra("search"))
        {
            ptlist=Patient.findWithQuery(Patient.class, "Select * from Patient where doctor = ?", doctorid + "");                 temp=ptlist;
            temp=ptlist;
            String[] patNames=new String[ptlist.size()];
            for(int i=0;i<ptlist.size();i++)
            {
                patNames[i]=ptlist.get(i).patient.name.toString();

            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, patNames);


            // Assign adapter to List
            setListAdapter(adapter);
        }

        else
        {
            String searchText=in.getStringExtra("search");
            ptlist=Patient.findWithQuery(Patient.class, "Select * from Patient where doctor = ?", doctorid + "");

                List<String> patNames = new ArrayList<String>();
                 temp=ptlist;
              //  String[] patNames=new String[ptlist.size()];
                for(int i=0;i<ptlist.size();i++)
                {
                    String texts=ptlist.get(i).patient.name.toString().toLowerCase();

                    if(texts.contains(searchText.toLowerCase()))
                    patNames.add(ptlist.get(i).patient.name.toString());
                    else
                    {
                        temp.remove(i);
                    }

                }
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, patNames);


                // Assign adapter to List
                if(patNames.size()==0)
                {
                    headText.setText("No patients found!!!");
                }
            else {


                    setListAdapter(adapter);
                }

        }






    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent=new Intent(MyPatientsListActivity.this,AddReportActivity.class);
        intent.putExtra("patid",temp.get(position).patient.getId());
        startActivity(intent);
    }
}
