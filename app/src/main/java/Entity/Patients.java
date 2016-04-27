package Entity;

import com.orm.SugarRecord;

/**
 * Created by msajaynath on 19/03/16.
 */public class Patients  {

    public String userid,name,address,gender,mob,landline,doctor,nurse;



    // You must provide an empty constructor
    public Patients() {}

    public Patients(String userid,String name, String address, String gender, String mob, String landline, String doctor, String nurse)
    {
        this.userid=userid;
        this.name=name;
        this.doctor=doctor;
        this.nurse=nurse;
        this.doctor=doctor;
        this.address=address;
        this.gender=gender;
        this.mob=mob;
        this.landline=landline;
    }


}
