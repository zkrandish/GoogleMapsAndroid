package com.example.assignmentcompositelistview1_1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import model.FileManagement;
import model.Person;
import model.PersonAdapter;
import model.Student;
import model.Teacher;

public class MainActivity extends AppCompatActivity  {

    ListView lvPeople;
    ArrayList<Person> personList;
    PersonAdapter personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initialize();
    }

    private void initialize() {
        lvPeople= findViewById(R.id.lvPerson);
        //lvPeople.setOnItemClickListener(this);
        personList= FileManagement.readFile(this,"person.txt");
        personAdapter= new PersonAdapter(this,personList);
        lvPeople.setAdapter(personAdapter);
        lvPeople.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Person person = personList.get(position);
                if(person instanceof Student){
                    String college = ((Student) person).getCollege();
                    Toast.makeText(MainActivity.this,"college" + college, Toast.LENGTH_LONG).show();
                } else if (person instanceof Teacher) {
                    String company = ((Teacher)person).getCompany();
                    Toast.makeText(MainActivity.this,"comapany "+ company,Toast.LENGTH_LONG).show();
                }
            }
        });
    }


}