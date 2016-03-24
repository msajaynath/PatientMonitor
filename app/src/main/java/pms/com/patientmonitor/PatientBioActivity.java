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

public class PatientBioActivity extends AppCompatActivity {

    private User u;
    private Patient pat;
    EditText name,address,phone;
    Spinner gender;
    Button update;
    ImageView changePass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_bio);
        final SharedPreferences sharedpreferences = getSharedPreferences("PMS", Context.MODE_PRIVATE);

        long patid=sharedpreferences.getLong("userid", 0);

         u= User.findById(User.class,patid);
         pat= Patient.findById(Patient.class,u.getId());

        changePass= (ImageView) findViewById(R.id.changepass);
        update= (Button) findViewById(R.id.update);
        gender= (Spinner) findViewById(R.id.gender);
        name= (EditText) findViewById(R.id.name);
        address= (EditText) findViewById(R.id.address);
        phone= (EditText) findViewById(R.id.phone);

        name.setText(u.name);
        address.setText(pat.address);
        phone.setText(pat.mob);
        if(pat.gender.contentEquals("Male"))
            gender.setSelection(0);
        else
            gender.setSelection(1);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u.name=name.getText().toString();
                pat.gender=gender.getSelectedItem().toString();
                pat.address=address.getText().toString();
                pat.mob=phone.getText().toString();
                pat.save();
                u.save();
                Toast.makeText(getApplicationContext(),"Updated!",Toast.LENGTH_SHORT).show();
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
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PatientBioActivity.this);
        alertDialog.setTitle("Change");
        final EditText oldPass = new EditText(PatientBioActivity.this);
        final EditText newPass = new EditText(PatientBioActivity.this);
        final EditText confirmPass = new EditText(PatientBioActivity.this);


        oldPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        newPass.setTransformationMethod(PasswordTransformationMethod.getInstance());
        confirmPass.setTransformationMethod(PasswordTransformationMethod.getInstance());

        oldPass.setHint("Old Password");
        newPass.setHint("New Password");
        confirmPass.setHint("Confirm Password");
        LinearLayout ll=new LinearLayout(PatientBioActivity.this);
        ll.setOrientation(LinearLayout.VERTICAL);

        ll.addView(oldPass);

        ll.addView(newPass);
        ll.addView(confirmPass);
        alertDialog.setView(ll);
        alertDialog.setPositiveButton("Yes",null);
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

                    Toast.makeText(PatientBioActivity.this, "Invalid current password!", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    if (newPass.getText().toString().length() < 6) {
                        Toast.makeText(PatientBioActivity.this, "New Password should be more than 6 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        if (!newPass.getText().toString().contentEquals(confirmPass.getText().toString())) {
                            Toast.makeText(PatientBioActivity.this, "Confirm password not same!", Toast.LENGTH_SHORT).show();
                            return;
                        } else {
                            u.password = newPass.getText().toString();
                            u.save();
                            Toast.makeText(PatientBioActivity.this, "Password updated!", Toast.LENGTH_SHORT).show();
                            alert11.cancel();

                        }

                    }
                }
                //else dialog stays open. Make sure you have an obvious way to close the dialog especially if you set cancellable to false.
            }
        });
    }


}
