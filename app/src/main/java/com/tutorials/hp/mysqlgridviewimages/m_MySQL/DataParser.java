package com.tutorials.hp.mysqlgridviewimages.m_MySQL;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.GridView;
import android.widget.Toast;

import com.tutorials.hp.mysqlgridviewimages.m_DataObject.Spacecraft;
import com.tutorials.hp.mysqlgridviewimages.m_GridView.CustomAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Oclemy on 6/5/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class DataParser extends AsyncTask<Void,Void,Integer> {


    Context c;
    String jsonData;
    GridView gv;

    ProgressDialog pd;
    ArrayList<Spacecraft> spacecrafts=new ArrayList<>();

    public DataParser(Context c, String jsonData, GridView gv) {
        this.c = c;
        this.jsonData = jsonData;
        this.gv = gv;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        pd=new ProgressDialog(c);
        pd.setTitle("Parse");
        pd.setMessage("Parsing...Please Wait");
        pd.show();

    }

    @Override
    protected Integer doInBackground(Void... params) {
        return this.parseData();
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);

        pd.dismiss();

        if(result==0)
        {
            Toast.makeText(c,"Unable To Parse",Toast.LENGTH_SHORT).show();
        }else {
            //BIND DATA TO GRIDVIEW
            CustomAdapter adapter=new CustomAdapter(c,spacecrafts);
            gv.setAdapter(adapter);
        }
    }

    private int parseData()
    {
        try
        {
            JSONArray ja=new JSONArray(jsonData);
            JSONObject jo=null;

            spacecrafts.clear();
            Spacecraft spacecraft;

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);

                int id=jo.getInt("id");
                String name=jo.getString("name");
                String imageUrl=jo.getString("imageurl");

                spacecraft=new Spacecraft();

                spacecraft.setId(id);
                spacecraft.setName(name);
                spacecraft.setImageUrl(imageUrl);

                spacecrafts.add(spacecraft);
            }


            return 1;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return 0;
    }
}






