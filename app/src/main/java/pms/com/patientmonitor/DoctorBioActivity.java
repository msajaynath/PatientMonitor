package pms.com.patientmonitor;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import Entity.Patient;
import Entity.User;

public class DoctorBioActivity extends AppCompatActivity {

    private User u;
    EditText name,department;
    
    Button update;
    ImageView changePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_bio);

        final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);

        long patid=sharedpreferences.getLong("userid", 0);

        u= User.findById(User.class,patid);

        changePass= (ImageView) findViewById(R.id.changepass);
        update= (Button) findViewById(R.id.update);
        name= (EditText) findViewById(R.id.name);
        department= (EditText) findViewById(R.id.department);

        name.setText(u.name);
        department.setText(u.department);
       

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.name=name.getText().toString();
               
                u.department=department.getText().toString();
                u.save();
                Toast.makeText(getApplicationContext(), "Updated!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        changePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckAndSavePassword(u);
            }
        });



    }
    private void CheckAndSavePassword(final User u) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DoctorBioActivity.this);
        alertDialog.setTitle("Change");
        final EditText oldPass = new EditText(DoctorBioActivity.this);
        final EditText newPass = new EditText(DoctorBioActivity.this);
        final EditText confirmPass = new EditText(DoctorBioActivity.this);


        oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        oldPass.setHint("Old Password");
        newPass.setHint("New Password");
        confirmPass.setHint("Confirm Password");
        LinearLayout ll=new LinearLayout(DoctorBioActivity.this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(oldPass);

        ll.addView(newPass);
        ll.addView(confirmPass);
        alertDialog.setView(ll);
        alertDialog.setPositiveButton("Yes", null);
        alertDialog.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                });
        alertDialog.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });

        final AlertDialog alert11 = alertDialog.create();
        alert11.show();

        alert11.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean wantToCloseDialog = false;
                //Do stuff, possibly set wantToCloseDialog to true then...
                if (!oldPass.getText().toString().contentEquals(u.password)) {

                    Toast.makeText(DoctorBioActivity.this, "Invalid current password!", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    if (newPass.getText().toString().length() < 6) {
                        Toast.makeText(DoctorBioActivity.this, "New Password should be more than 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (!newPass.getText().toString().contentEquals(confirmPass.getText().toString())) {
                            Toast.makeText(DoctorBioActivity.this, "Confirm password not same!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            u.password = newPass.getText().toString();
                            u.save();
                            Toast.makeText(DoctorBioActivity.this, "Password updated!", Toast.LENGTH_SHORT).show();
                            alert11.cancel();

                        }

                    }
                }
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }


}
