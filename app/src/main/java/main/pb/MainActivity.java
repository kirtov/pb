package main.pb;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class MainActivity extends Activity {

    final String NAME = "name";
    final String DATE = "date";
    ListView personLv;
    DBPerson db;
    ArrayList<Person> persons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences shp = getPreferences(MODE_PRIVATE);
        if (!shp.contains(DBPerson.PID)) {
            SharedPreferences.Editor edt = shp.edit();
            edt.putInt(DBPerson.PID, 0);
            edt.commit();
        }
        initComponents();
        getAndSetDataFromDb();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAndSetDataFromDb();
    }

    private void initComponents() {
        personLv = (ListView) findViewById(R.id.person_lv);
        personLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PersonInfoActivity.class);
                intent.putExtra(DBPerson.PID, persons.get(position).getPid());
                startActivity(intent);
            }
        });

    }

    private void getAndSetDataFromDb() {
        db = new DBPerson(this);
        persons = db.getAllPersons();
        setDataInListView(persons);
    }

    private void setDataInListView(ArrayList<Person> persons) {
        ArrayList<HashMap<String, String>> personData = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> singlePersonMap;

        for (Person prs : persons) {
            singlePersonMap = new HashMap<String, String>();
            singlePersonMap.put(NAME, prs.getFullName());
            singlePersonMap.put(DATE, prs.getDate());
            personData.add(singlePersonMap);
        }

        SimpleAdapter adapter = new SimpleAdapter(this, personData, R.layout.person_element,
                new String[] {NAME, DATE},
                new int[] {R.id.name, R.id.update});
        personLv.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.add_person) {
            Intent intent = new Intent(MainActivity.this, AddOrEditPersonActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
