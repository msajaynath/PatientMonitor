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

import java.util.List;

import Entity.Patient;

public class MyPatientsListActivity extends ListActivity {

    private List<Patient> ptlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_patients_list);
        final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);
        long doctorid=sharedpreferences.getLong("userid", 0);

        ptlist=Patient.findWithQuery(Patient.class, "Select * from Patient where doctor = ?",doctorid+"");
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

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent=new Intent(MyPatientsListActivity.this,AddReportActivity.class);
        intent.putExtra("patid",ptlist.get(position).patient.getId());
        startActivity(intent);
    }
}
