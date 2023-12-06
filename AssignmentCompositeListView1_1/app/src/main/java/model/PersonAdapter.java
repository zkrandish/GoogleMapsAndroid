package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.assignmentcompositelistview1_1.R;

import java.util.ArrayList;

public class PersonAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Person> personList;
    private LayoutInflater inflater;

    public PersonAdapter(Context context, ArrayList<Person> personList) {
        this.context = context;
        this.personList = personList;
        this.inflater= LayoutInflater.from(context);
    }

    //Person onePerson;

    @Override
    public int getCount() {
        return personList.size();
    }

    @Override
    public Object getItem(int position) {
        return personList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        //View view = convertView;
        Person person = (Person) getItem(position);
        if (person instanceof Student) {
            if (view == null || !view.getTag().equals("student")) {
                view = inflater.inflate(R.layout.list_item_student, viewGroup, false);
                view.setTag("student");
            }


            TextView tvFullName = view.findViewById(R.id.tvFullName);
            TextView tvAge = view.findViewById(R.id.tvAge);
            ImageView imPhoto = view.findViewById(R.id.imPhoto);

            Student student = (Student) person;
            tvFullName.setText(student.getName());
            tvAge.setText(student.getAge());

            // Set the photo for the student
            String photoName = student.getPhoto();
            int photoResId = context.getResources().getIdentifier(photoName, "drawable", context.getPackageName());
            imPhoto.setImageResource(photoResId);

            ImageView imMore = view.findViewById(R.id.imMore);
            imMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Toast.makeText(context, "" + student.getAge(), Toast.LENGTH_LONG).show();
                }
            });

        } else if (person instanceof Teacher) {
            if (view == null || !view.getTag().equals("teacher")) {
                view = inflater.inflate(R.layout.list_item_teacher, viewGroup, false);
                view.setTag("teacher");

            }

            ImageView imPhoto = view.findViewById(R.id.imPhoto);
            TextView tvFullName = view.findViewById(R.id.tvFullName);
            TextView tvSalary = view.findViewById(R.id.tvsalary);
            TextView tvCommission = view.findViewById(R.id.tvCommision);

            Teacher teacher = (Teacher) person;
            tvFullName.setText(teacher.getName());
            tvSalary.setText(teacher.getSalary());
            tvCommission.setText(teacher.getCommission());
            // Set the photo for the teacher
            String photoName = teacher.getPhoto();
            int photoResId = context.getResources().getIdentifier(photoName, "drawable", context.getPackageName());
            imPhoto.setImageResource(photoResId);


            ImageView imMore = view.findViewById(R.id.imMore);
            imMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String salary = ((Teacher) person).getSalary();
                    String commission = ((Teacher) person).getCommission();
                    Toast.makeText(context, "Salary: " + salary + ", Commission: " + commission, Toast.LENGTH_SHORT).show();
                }
            });

        }
        return  view;

    }
}
