package Entity;

import com.orm.SugarRecord;

import java.util.Date;

/**
 * Created by msajaynath on 19/03/16.
 */public class Lab extends SugarRecord {
    public User patient;

    public String diagnosis;

    public int heartrate,pulse,temprature;
    public long spo2;
    public long date;


    // You must provide an empty constructor
    public Lab() {}

    public Lab(User patient, String diagnosis, int heartrate,int pulse,int temprature,long spo2,long date)
    {
        this.patient=patient;
        this.diagnosis=diagnosis;
        this.heartrate=heartrate;
        this.pulse=pulse;
        this.temprature=temprature;
        this.spo2=spo2;
        this.date=date;

    }


}
