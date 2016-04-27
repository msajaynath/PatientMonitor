package Entity;

import com.orm.SugarRecord;

/**
 * Created by msajaynath on 19/03/16.
 */public class Patient extends  SugarRecord {
    public User doctor;
    public User nurse;
    public User patient;
    public String address,gender,mob,landline;



    // You must provide an empty constructor
    public Patient() {}

    public Patient(User patient,User doctor, User nurse,String address,String gender,String mob,String landline)
    {
        this.patient=patient;
        this.doctor=doctor;
        this.nurse=nurse;
        this.doctor=doctor;
        this.address=address;
        this.gender=gender;
        this.mob=mob;
        this.landline=landline;
    }


}
