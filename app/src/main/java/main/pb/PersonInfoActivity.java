package main.pb;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class PersonInfoActivity extends Activity {

    TextView fname, numb, email, comment, group;
    int pid;
    Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_activity);
        setTitle(getResources().getString(R.string.info_name));
        pid = getIntent().getIntExtra(DBPerson.PID, 0);
        initComponents();
        setInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setInfo();
    }

    private void initComponents() {
        fname = (TextView) findViewById(R.id.fullname_info);
        numb = (TextView) findViewById(R.id.pnum_info);
        email = (TextView) findViewById(R.id.email_info);
        comment = (TextView) findViewById(R.id.comment_info);
        group = (TextView) findViewById(R.id.group_info);
    }

    private void setInfo() {
        DBPerson db = new DBPerson(this);
        person = db.getPersonByPid(pid);
        if (person != null) {
            fname.setText(person.getFullName());
            numb.setText(person.getPhoneNumber());
            email.setText(person.getEmail());
            comment.setText(person.getComment());
            group.setText(person.getGroup());
            Linkify.addLinks(numb, Linkify.PHONE_NUMBERS);
            Linkify.addLinks(email, Linkify.EMAIL_ADDRESSES);
        } else {
            Toast.makeText(PersonInfoActivity.this, getResources().getString(R.string.person_not_found), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.edit_person) {
            Intent intent = new Intent(PersonInfoActivity.this, AddOrEditPersonActivity.class);
            intent.putExtra(DBPerson.PID, person.getPid());
            intent.putExtra(AddOrEditPersonActivity.EDITMODE, true);
            startActivity(intent);
            return true;
        } else if (id == R.id.delete_person) {
            final DBPerson db = new DBPerson(this);
            AlertDialog.Builder builder = new AlertDialog.Builder(PersonInfoActivity.this);
            builder.setTitle(getResources().getString(R.string.delete_q));
            builder.setNegativeButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    db.delete(person);
                    Toast.makeText(PersonInfoActivity.this, getResources().getString(R.string.deleted), Toast.LENGTH_LONG).show();
                    PersonInfoActivity.this.finish();
                }
            });
            builder.setNeutralButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            builder.show();
            return false;
        }
        return super.onOptionsItemSelected(item);
    }
}
