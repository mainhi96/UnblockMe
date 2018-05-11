package com.example.sony.unblockme.state;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.sony.unblockme.ChangePass;
import com.example.sony.unblockme.R;
import com.example.sony.unblockme.main.MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainMenuState extends AppCompatActivity {
    private Button btnPlay;
    private Button btnPass;
    private TextView tvName;
    private TextView tvLevel;
    public static String name;
    public String level;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_state);

        btnPass= (Button) findViewById(R.id.btnPass);
        btnPlay= (Button) findViewById(R.id.btnPlay);
        tvName= (TextView) findViewById(R.id.tvName);
        tvLevel= (TextView) findViewById(R.id.tvLevel);

        Intent intent = getIntent();
        MainMenuState.name = intent.getStringExtra("username");
         level= intent.getStringExtra("level");
        tvName.setText("User: "+name);
        tvLevel.setText("Level: " +level);


     //   new GetInfo().execute("http://192.168.1.108/unblockme/api/account?username="+name);



        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainMenuState.this, MainActivity.class);
                int x= Integer.parseInt(level);
                intent.putExtra("level", x);
                startActivity(intent);
                finish();

            }
        });

        btnPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(MainMenuState.this, ChangePass.class);
                startActivity(intent);
            }
        });
    }
    public class GetInfo extends AsyncTask<String, String, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... strings) {
            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(strings[0]);
                connection = (HttpURLConnection) url.openConnection();
                //       connection.setRequestMethod("GET");

                connection.connect();

                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();

                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line);
                }

                String finalJson = buffer.toString();
                if (finalJson.length() < 10) return null;
                ArrayList<String> data = new ArrayList<>();

               /* JSONObject parentObject = new JSONObject(finalJson);
                JSONArray parentArray = parentObject.getJSONArray("parentObject");
                JSONObject finalObject = parentArray.getJSONObject(0);

                String description = finalObject.getString("description");*/


                // String titre = finalObject.getString("title");
                try {

                    // JSONArray jsonArray = new JSONArray(finalJson);
                    //
                    //3. Duyệt từng đối tượng trong Array và lấy giá trị ra
                    //  for(int i = 0; i < jsonArray.length(); i++) {
                    //    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    // String name = jsonObject.optString("user").toString();
                    JSONObject jsonObject = new JSONObject(finalJson);
                    String username = jsonObject.optString("username").toString();
                    data.add(username);
                    String job = jsonObject.optString("level").toString();
                    data.add(job);

                    //}
                } catch (JSONException e) {
                    e.printStackTrace();
                }


                return data;


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();

            } finally {
                if (connection != null) {
                    connection.disconnect();
                }

                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


            return null;

        }

        @Override
        protected void onPostExecute(ArrayList<String> strings) {
            super.onPostExecute(strings);
            if (strings != null) {
                tvName.setText(strings.get(0));
                tvLevel.setText(strings.get(1));
            }
        }
    }
}
