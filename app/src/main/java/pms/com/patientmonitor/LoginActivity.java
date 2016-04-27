package pms.com.patientmonitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import Entity.Patient;
import Entity.User;

public class LoginActivity extends AppCompatActivity {

    Button login;
    EditText username,password;
    private Connection connection;
    private Statement statement;
    List<User> logUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //seedfunction();
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
                 logUser = new ArrayList<User>();

                Thread thread = new Thread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        try {
                            //Your code goes here

                            Class.forName("com.mysql.jdbc.Driver").newInstance();

                            Log.d("", "here1");

                            connection = DriverManager.getConnection(getString(R.string.JDBCUrl), getString(R.string.JDBCUsername), getString(R.string.JDBCPassword));
                            // Log.d("","here2");

                            statement = connection.createStatement();
                            // Log.d("","here2");


                            ResultSet rs = null;
                            try {
                                rs = statement.executeQuery("SELECT * FROM Users where username='" + username.getText().toString() + "' and password='" + password.getText().toString() + "'");
                                Log.d("Debug","here1");

                                while (rs.next()) {
                                    logUser.add(new User(rs.getString("username"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("department"), rs.getString("job")));
                                    Log.d("Debug",  rs.getString("name"));

                                    // String id = rs.getString("username");
                                    // String firstName = rs.getString("name");
                                    // String lastName = rs.getString("last_name");
                                    // System.out.println("ID: " + id + ", First Name: " + firstName
                                    //);
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                                Log.d("Debug",e.toString());
                            }

                            //query = query +"'" +variable+"'";
                            // ResultSet result = statement.executeQuery(query);
                            connection.close();

                        }
                        catch (Exception e)
                        {
                            Log.d("Debug",e.toString());

                        }
                        if (logUser.size() == 0) {
                            Toast.makeText(getApplicationContext(), "Invalid credentials!!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        //Toast.makeText(getApplicationContext(), "Welcome " + logUser.get(0).name, Toast.LENGTH_SHORT).show();
                        sharedpreferences.edit().putBoolean("logged", true).commit();
                        sharedpreferences.edit().putString("userid", logUser.get(0).username).commit();
                        if (logUser.get(0).job.contains("Doctor")) {
                            sharedpreferences.edit().putString("type", "d").commit();

                            startActivity(new Intent(LoginActivity.this, DoctorHomeActivity.class));
                        }
                        if (logUser.get(0).job.contains("Patient")) {
                            sharedpreferences.edit().putString("type", "p").commit();

                            startActivity(new Intent(LoginActivity.this, PatientHomeActivity.class));
                        }
                        finish();
                    }



                });

                thread.start();

                // = User.find(User.class, "username = ? and password = ?", username.getText().toString(), password.getText().toString());

            }
        });



    }

//    private void seedfunction() {
//
//        if(User.listAll(User.class).size()==0)
//        {
//            new User("ntest","Test Nurse","nurse@aims.com","pass","Ortho","Nurse").save(); //id will be 1
//            new User("dtest","Test Doctor","dotor@aims.com","pass","Ortho","Doctor").save();// id will be 2
//
//            //add a few patients
//
//            new User("ptest1","Mathew Philip","test@gmail.com","pass","","Patient").save();
//            new User("ptest2","James Rody","test@gmail.com","pass","","Patient").save();
//            new User("ptest3","Manu  Mathew","test@gmail.com","pass","","Patient").save();
//            new User("ptest4","Merry George","test@gmail.com","pass","","Patient").save();
//
//            //link to doctor
//            new Patient(User.findById(User.class,3),User.findById(User.class,2),User.findById(User.class,1),"test address","Male","+9043000230","").save();
//            new Patient(User.findById(User.class,4),User.findById(User.class,2),User.findById(User.class,1),"test address","Male","+9043000230","").save();
//            new Patient(User.findById(User.class,5),User.findById(User.class,2),User.findById(User.class,1),"test address","Male","+9043000230","").save();
//            new Patient(User.findById(User.class,6),User.findById(User.class,2),User.findById(User.class,1),"test address","Female","+9043000230","").save();
//
//        }
//    }
}
