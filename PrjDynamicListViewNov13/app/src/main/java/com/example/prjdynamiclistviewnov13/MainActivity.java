package com.example.prjdynamiclistviewnov13;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import model.Country;
import model.FileManagement;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,
         AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener, DialogInterface.OnClickListener {

    EditText edCName,edCCAapital;
    Button btnAdd,btnUpdate,btnSort;
    ListView lvCountries;
    ArrayList<Country>countryList;
    ArrayAdapter<Country>countryAdapter;
    AlertDialog.Builder alertDialog;
    int pos=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }

    private void initialize() {
        edCName= findViewById(R.id.edCName);
        edCCAapital= findViewById(R.id.edCCapital);
        btnAdd= findViewById(R.id.btnAdd);
        btnUpdate= findViewById(R.id.btnUpdate);
        btnSort= findViewById(R.id.btnSort);
        btnAdd.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnSort.setOnClickListener(this);
        lvCountries= findViewById(R.id.lvCountries);
        countryList= new ArrayList<Country>();
//        countryList.add(new Country("France","Paris"));
//        countryList.add(new Country("India","Delhi"));
//        countryList.add(new Country("Iran","Tehran"));
        countryList= FileManagement.readFile(this,"countries.txt");
        //countryAdapter= new ArrayAdapter<Country>(this, android.R.layout.simple_list_item_1,countryList);
        countryAdapter= new ArrayAdapter<Country>(this, R.layout.one_item,countryList);
        lvCountries.setAdapter(countryAdapter);
        lvCountries.setOnItemClickListener(this);
        lvCountries.setOnItemLongClickListener(this);
        alertDialog= new AlertDialog.Builder(this);
        alertDialog.setTitle("Deletion");
        alertDialog.setMessage("Do you want to remove(Y/N)");
        alertDialog.setPositiveButton("Yes",this);
        alertDialog.setNegativeButton("No",this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id==R.id.btnAdd){
            addCountry(view);
        }
        if(id==R.id.btnUpdate){
            updateCountry(view);
        }
        if(id==R.id.btnSort){
            sortList(view);
        }


    }

     private void addCountry(View view) {
        String name= edCName.getText().toString();
        String capital= edCCAapital.getText().toString();
        Country country= new Country(name,capital);
        if(countryExists(country)==-1&& !name.equals("")&&!capital.equals("")){
            countryList.add(country);
            countryAdapter.notifyDataSetChanged();
            Snackbar.make(view,"The country "+ country.getcName()+"has been added successfully",
                    Snackbar.LENGTH_LONG).show();
            clearWidgets();
        }else{
            Snackbar.make(view,"The country "+ country.getcName()+"has been already in the list",
                    Snackbar.LENGTH_LONG).show();
        }
    }

    private int countryExists(Country country) {
        int pos=countryList.indexOf(country);
        return pos;

    }

    private void clearWidgets() {
        edCName.setText(null);
        edCCAapital.setText(null);
        edCName.requestFocus();
    }

    private void updateCountry(View view) {
        String name= edCName.getText().toString();
        String capital= edCCAapital.getText().toString();
        Country country= new Country(name,capital);
        int pos=countryExists(country);
        if(pos!=-1){
            countryList.set(pos,country);
            countryAdapter.notifyDataSetChanged();
            Snackbar.make(view,"The country "+
                            country.getcName()+"has been updated successfully",
                    Snackbar.LENGTH_LONG).show();
        }else{
            Snackbar.make(view,"The country "+
                            country.getcName()+"does not exist",
                    Snackbar.LENGTH_LONG).show();
        }
    }
    private void sortList(View view) {

        Collections.sort(countryList);
        countryAdapter.notifyDataSetChanged();

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        pos=position;
        String name= countryList.get(position).getcName();
        String capital= countryList.get(position).getcCapital();
        edCName.setText(name);
        edCCAapital.setText(capital);

    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
//
        alertDialog.create().show();
        return false;
        //ture: only onlonglitem clicl
        //false: onitem long+onitem click is executed
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==Dialog.BUTTON_POSITIVE){
            countryList.remove(pos);
        countryAdapter.notifyDataSetChanged();
        }
        if(which==Dialog.BUTTON_NEGATIVE){

        }

    }
}