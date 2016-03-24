package Entity;

import com.orm.SugarRecord;

/**
 * Created by msajaynath on 19/03/16.
 */public class User extends SugarRecord {
    public String username,name,email,password;
    public String department,job;



    // You must provide an empty constructor
    public User() {}

    public User(String username,String name,String email,String password,String department,String job)
    {
        this.username=username;
        this.name=name;
        this.email=email;
        this.password=password;
        this.department=department;
        this.job=job;


    }


}
