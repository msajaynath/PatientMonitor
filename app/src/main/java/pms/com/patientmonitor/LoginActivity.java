package pms.com.patientmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import Entity.Patient;
import Entity.User;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        seedfunction();
        login= (Button) findViewById(R.id.login);

        username= (EditText) findViewById(R.id.username);
        password= (EditText) findViewById(R.id.password);
        final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);
        if(sharedpreferences.getBoolean("logged",false))
        {
            if(sharedpreferences.getString("type","").contentEquals("p")) {
                startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class));
            }
            else if(sharedpreferences.getString("type","").contentEquals("d"))
            {
                startActivity(new Intent(LoginActivity.this, DoctorHomeActivity.class));

            }
                finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (username.getText().toString().length() == 0 || password.getText().toString().length() == 0) {

                    Toast.makeText(getApplicationContext(), "Username/Password required!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                List<User> log = User.find(User.class, "username = ? and password = ?", username.getText().toString(), password.getText().toString());
                if (log.size() == 0) {
                    Toast.makeText(getApplicationContext(), "Invalid credentials!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Welcome " + log.get(0).name, Toast.LENGTH_SHORT).show();
                sharedpreferences.edit().putBoolean("logged", true).commit();
                sharedpreferences.edit().putLong("userid", log.get(0).getId()).commit();
                if(log.get(0).job.contains("Doctor"))
                {
                    sharedpreferences.edit().putString("type", "d").commit();

                    startActivity(new Intent(LoginActivity.this, DoctorHomeActivity.class));
                }
                if(log.get(0).job.contains("Patient"))
                {
                    sharedpreferences.edit().putString("type", "p").commit();

                    startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class));
                }
                finish();
            }
        });



    }

    private void seedfunction() {

        if(User.listAll(User.class).size()==0)
        {
            new User("ntest","Test Nurse","nurse@aims.com","pass","Ortho","Nurse").save(); //id will be 1
            new User("dtest","Test Doctor","dotor@aims.com","pass","Ortho","Doctor").save();// id will be 2

            //add a few patients

            new User("ptest1","Mathew Philip","test@gmail.com","pass","","Patient").save();
            new User("ptest2","James Rody","test@gmail.com","pass","","Patient").save();
            new User("ptest3","Manu  Mathew","test@gmail.com","pass","","Patient").save();
            new User("ptest4","Merry George","test@gmail.com","pass","","Patient").save();

            //link to doctor
            new Patient(User.findById(User.class,3),User.findById(User.class,2),User.findById(User.class,1),"test address","Male","+9043000230","").save();
            new Patient(User.findById(User.class,4),User.findById(User.class,2),User.findById(User.class,1),"test address","Male","+9043000230","").save();
            new Patient(User.findById(User.class,5),User.findById(User.class,2),User.findById(User.class,1),"test address","Male","+9043000230","").save();
            new Patient(User.findById(User.class,6),User.findById(User.class,2),User.findById(User.class,1),"test address","Female","+9043000230","").save();

        }
    }
}
