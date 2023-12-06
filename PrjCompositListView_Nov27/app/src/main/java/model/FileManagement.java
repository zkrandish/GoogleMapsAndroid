package model;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class FileManagement {
    public  static ArrayList<Player>readFile(Context context, String fileName){
        ArrayList<Player>playerList= new ArrayList<Player>();

        //1- access to asset manager
        AssetManager assMan= context.getResources().getAssets();

        try {
            //2-open the file
            InputStreamReader isr= new InputStreamReader(assMan.open(fileName));
            //3- read the content of the file
            BufferedReader br = new BufferedReader(isr);
            String oneLine= br.readLine();
            while (oneLine!=null) {
                StringTokenizer st = new StringTokenizer(oneLine, ",");
                String fName = st.nextToken();
                String tName = st.nextToken();
                try {
                    int yearOfBirth = Integer.valueOf(st.nextToken());
                    String photo = st.nextToken();
                    playerList.add(new Player(fName, tName, yearOfBirth, photo));
                } catch (Exception e) {
                    Log.d("ERROR", e.getMessage());

                }
                oneLine = br.readLine();
            }
            //4 closeing buffer files
            br.close();
            isr.close();

        } catch (IOException e) {
            Toast.makeText(context,e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return playerList;
    }
}
