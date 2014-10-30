package com.example.markvan.getapp;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.app.Activity;
import android.widget.Button;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import android.os.AsyncTask;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class FirstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        final TextView name = (TextView) findViewById(R.id.lblName);
        Button but = (Button) findViewById(R.id.GetData);

        but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String ws = "http://www.mocky.io/v2/54511ff8584e0f38186d00c3";
                if (FirstActivity.this.isConnected()) {
                    new HttpAsyncTask().execute(ws);
                } else {
                    final String noInternet = "There's no connection";
                    name.setText(noInternet);
                }
            }
        });
    }

    //Check connection
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            return false;
        }
    }

    public class HttpAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpGet get = new HttpGet(urls[0]);
                get.setHeader("Content-type", "application/json");

                HttpResponse resp = httpclient.execute(get);
                String respString = EntityUtils.toString(resp.getEntity());

               //If line != NULL, return it.
                if (respString != null) {
                    JSONObject objJSON = new JSONObject(respString);
                    result = objJSON.getString("nombre");
                } else {
                    result = "Did not work!";
                }
            } catch (Exception e) {
            }
            return result;
        }

        //Show results
        @Override
        protected void onPostExecute(String result) {
            TextView txt = (TextView)findViewById(R.id.lblName);
            txt.setText(result);
        }
    }
}




