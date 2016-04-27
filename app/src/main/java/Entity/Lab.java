package Entity;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by msajaynath on 19/03/16.
 */public class Lab {
    public  int labid;
    public String patient,doctor;

    public String diagnosis;

    public int heartrate,pulse;
    public float temperature;
    public long spo2;
    public String date;


    // You must provide an empty constructor
    public Lab() {}

    public Lab(int labid,String patient,String doctor, String diagnosis, int heartrate,int pulse,float temperature,long spo2,String date)
    {
        this.labid=labid;
        this.patient=patient;
        this.doctor=doctor;
        this.diagnosis=diagnosis;
        this.heartrate=heartrate;
        this.pulse=pulse;
        this.temperature=temperature;
        this.spo2=spo2;
        this.date=date;

    }


}
