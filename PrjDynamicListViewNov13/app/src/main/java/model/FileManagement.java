package model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileManagement {
    public static ArrayList<Country>readFile(Context context, String fileName){
        ArrayList<Country>countryList= new ArrayList<Country>();
        //1- access to asset folder
        AssetManager assMan= context.getResources().getAssets();

        //2-open the file countires.txt
        try {
            InputStreamReader isr= new InputStreamReader(assMan.open(fileName));
            //3-process the data of the file
            BufferedReader br= new BufferedReader(isr);
            String oneLine= br.readLine();
            while (oneLine!=null){
                StringTokenizer st= new StringTokenizer(oneLine,",");
                String cName= st.nextToken();
                String cCapital= st.nextToken();
                countryList.add(new Country(cName,cCapital));
                oneLine=br.readLine();
            }

            //4-close the file and return the result
            br.close();
            isr.close();
        } catch (Exception e) {
           Log.d("Error",e.getMessage());
        }
        return countryList;

    }
}
