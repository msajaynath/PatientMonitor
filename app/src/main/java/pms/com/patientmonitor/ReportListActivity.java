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

import java.text.SimpleDateFormat;
import java.util.List;

import Entity.Lab;
import Entity.Patient;

public class ReportListActivity extends ListActivity {

    private List<Lab> reportList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_list);
        final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);
        long patid=sharedpreferences.getLong("userid", 0);

        reportList= Lab.findWithQuery(Lab.class, "Select * from Lab where patient = ?", patid + "");
        String[] reportNames=new String[reportList.size()];
        for(int i=0;i<reportList.size();i++)
        {
            SimpleDateFormat formatter = new SimpleDateFormat("EEE MMM dd hh:mm:ss yyyy ");
            String formatDate = formatter.format(reportList.get(i).date);


            reportNames[i]="Report on "+formatDate;

        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, reportNames);


        // Assign adapter to List
        setListAdapter(adapter);

    }

    @Override
    protected void onListItemClick (ListView l, View v, int position, long id) {
        Intent intent=new Intent(ReportListActivity.this,ViewReportActivity.class);
        intent.putExtra("report_id", reportList.get(position).getId());
        startActivity(intent);
    }
}
