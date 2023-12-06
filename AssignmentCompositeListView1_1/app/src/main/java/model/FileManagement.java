package model;

import android.content.Context;
import android.content.res.AssetManager;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileManagement {
    public static ArrayList<Person>readFile(Context context, String fileName){
        ArrayList<Person>personList= new ArrayList<Person>();
        AssetManager assetManager= context.getResources().getAssets();

        try{
            InputStreamReader isr= new InputStreamReader(assetManager.open(fileName));
            BufferedReader br= new BufferedReader(isr);
            String oneLine= br.readLine();
            while(oneLine!=null){
                String[]tokens= oneLine.split(",");
                if(tokens.length>=5){
                    String type= tokens[0];
                    String id= tokens[1];
                    String name= tokens[2];
                    String fourthToken= tokens[3];
                    String fifthToken= tokens[4];
                    String picture;
                    switch (type){
                        case "s": case "S":
                            // Student has id, name, age, college, and picture
                            if (tokens.length == 6) {
                                String age = fourthToken;
                                String college = fifthToken;
                                picture= tokens[5].trim();
                                personList.add(new Student(id, name, picture, age, college));
                            }
                            break;
                        case "T": case "t":
                            if(tokens.length==7){
                                String salary=fourthToken;
                                String commission= fifthToken;
                                String company= tokens[5].trim();
                                picture=tokens[6].trim();
                                personList.add(new Teacher(id,name,picture,salary,commission,company));
                            }
                            break;
                    }
                }
                oneLine= br.readLine();
            }
            br.close();
            isr.close();

        }catch (IOException e) {
            Toast.makeText(context,e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return personList;
    }
}
