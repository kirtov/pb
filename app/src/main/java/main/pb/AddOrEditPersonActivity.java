package main.pb;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;

public class AddOrEditPersonActivity extends Activity {

    EditText snameEdt, nameEdt, pnameEdt, pnumEdt, emailEdt, commentEdt;
    Spinner grpSpinner;
    public static final String EDITMODE = "edit_mode";
    boolean editMode;
    Person personToEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_activity);
        editMode = getIntent().getBooleanExtra(EDITMODE, false);
        initComponents();

        int pid = getIntent().getIntExtra(DBPerson.PID, 0);
        if (editMode) {
            DBPerson db = new DBPerson(this);
            personToEdit = db.getPersonByPid(pid);
            insertInfo(personToEdit);
            setTitle(getResources().getString(R.string.edit_name));
        } else {
            setTitle(getResources().getString(R.string.add_name));
        }
    }

    private void insertInfo(Person person) {
        snameEdt.setText(person.getSName());
        nameEdt.setText(person.getName());
        pnameEdt.setText(person.getPName());
        pnumEdt.setText(person.getPhoneNumber());
        emailEdt.setText(person.getEmail());
        commentEdt.setText(person.getComment());
        String[] groups = getResources().getStringArray(R.array.group);
        grpSpinner.setSelection(Arrays.asList(groups).indexOf(person.getGroup()));
    }

    private void initComponents() {
        snameEdt = (EditText) findViewById(R.id.sname_edt);
        nameEdt = (EditText) findViewById(R.id.name_edt);
        pnameEdt = (EditText) findViewById(R.id.pname_edt);
        pnumEdt = (EditText) findViewById(R.id.num_edt);
        emailEdt = (EditText) findViewById(R.id.email_edt);
        commentEdt = (EditText) findViewById(R.id.comment_edt);
        grpSpinner = (Spinner) findViewById(R.id.grp_spinner);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_person) {
            String sname = snameEdt.getText().toString();
            String name = nameEdt.getText().toString();
            String pname = pnameEdt.getText().toString();
            String pnum = pnumEdt.getText().toString();
            String email = emailEdt.getText().toString();
            String comment = commentEdt.getText().toString();
            String group = grpSpinner.getSelectedItem().toString();
            int pid;
            if (!editMode) {
                SharedPreferences shp = getPreferences(MODE_PRIVATE);
                pid = shp.getInt(DBPerson.PID, 0) + 1;
                SharedPreferences.Editor edt = shp.edit();
                edt.putInt(DBPerson.PID, pid);
                edt.commit();
            } else {
                pid = getIntent().getIntExtra(DBPerson.PID, 0);
            }

            Calendar c = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("dd:MM:yy, HH:mm");
            String strDate = sdf.format(c.getTime());
            Person person = new Person(pid, name, sname, pname, group, pnum, email, comment, strDate);
            DBPerson db = new DBPerson(this);

            if (editMode) {
                db.update(person);
                Toast.makeText(AddOrEditPersonActivity.this, getResources().getString(R.string.updated),Toast.LENGTH_LONG).show();
            } else {
                db.insert(person);
                Toast.makeText(AddOrEditPersonActivity.this, getResources().getString(R.string.saved),Toast.LENGTH_LONG).show();
            }
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
