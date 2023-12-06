package model;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.prjcompositlistview_nov27.R;

import java.security.PrivateKey;
import java.util.ArrayList;

public class PlayerAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Player>playerList;
    Player onePlayer;

    public PlayerAdapter(Context context, ArrayList<Player> playerList) {
        this.context = context;
        this.playerList = playerList;
    }

    @Override
    public int getCount() {
        return playerList.size();
    }

    @Override
    public Object getItem(int i) {
        return playerList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        //1-required declaration
        View oneItem = null;
        ImageView imphoto,imMore;
        TextView tvFullName,tvTeamName;

        //2- convert the layout tone-item.cml to java
        //layout inflation
        LayoutInflater inflater= LayoutInflater.from(context);
        oneItem=inflater.inflate(R.layout.one_item,viewGroup,false);
        //3- reference the widgets of one item.xml
        tvFullName=oneItem.findViewById(R.id.tvFullName);
        tvTeamName= oneItem.findViewById(R.id.tvTeamName);
        imphoto= oneItem.findViewById(R.id.imPhoto);
        imMore= oneItem.findViewById(R.id.imMore);
        imMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onePlayer=(Player)getItem(i);
                Toast.makeText(context, ""+onePlayer.getYearOfBirth(), Toast.LENGTH_SHORT).show();
            }
        });
        //4-populate the widgets of one item.xml
        onePlayer=(Player) getItem(i);
        tvFullName.setText(onePlayer.getFullName());
        tvTeamName.setText(onePlayer.getTeamName());
        String photoName= onePlayer.getPhoto();
        int imPhotoRes= context.getResources().getIdentifier("drawable/"+photoName,null, context.getPackageName());
        imphoto.setImageResource(imPhotoRes);

        //5- return result

        return oneItem;
    }
}
