package jukebox.android.com.socialjukebox;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Hastag extends AppCompatActivity {
    Button button1;
    EditText editText1;
    String hashTag;
    String output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hastag);
        button1 = (Button) findViewById(R.id.button1);
        editText1 = (EditText) findViewById(R.id.editText1);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hashTag = editText1.getText().toString();
                try {
//                    URL url = new URL("http://192.168.3.110:5000/hashtag");
//                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
//                    con.setRequestMethod("POST");
//                    String str =  "{\"hashtag\":\"LEMONADE\"}";
//                    byte[] outputInBytes = str.getBytes("UTF-8");
//                    OutputStream os = con.getOutputStream();
//                    os.write( outputInBytes );
//                    os.close();
//                    String readStream = readStream(con.getInputStream());
                    // Give output for the command line
                   // System.out.println(readStream);
                    String url = "http://192.168.3.110:5000/hashtag";
                    String str =  "{\"hashtag\":\""+hashTag+"\"}";
                    String urlData[]= new String [2];
                    urlData[0]=url;
                    urlData[1]=str;
                    new RetrieveData().execute(urlData);

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });}



    private static String readStream(InputStream in) {
        StringBuilder sb = new StringBuilder();
        try  {
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String nextLine = "";
            while ((nextLine = reader.readLine()) != null) {
                sb.append(nextLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }


    class RetrieveData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            output =excutePost(params[0],params[1]);

            return null;
        }
        protected void onPostExecute(Void Void) {
            // TODO: check this.exception
            // TODO: do something with the feed

        }
        public  String excutePost(String targetURL, String urlParameters)
        {
            URL url;
            HttpURLConnection connection = null;
            try {
                //Create connection
                url = new URL(targetURL);
                connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
//                connection.setRequestProperty("Content-Type",
//                        "application/x-www-form-urlencoded");
//
//                connection.setRequestProperty("Content-Length", "" +
//                        Integer.toString(urlParameters.getBytes().length));
//                connection.setRequestProperty("Content-Language", "en-US");

                connection.setUseCaches (false);
                connection.setDoInput(true);
                connection.setDoOutput(true);

                //Send request
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream ());
                wr.writeBytes (urlParameters);
                wr.flush ();
                wr.close ();

                //Get Response
                InputStream is = connection.getInputStream();
                BufferedReader rd = new BufferedReader(new InputStreamReader(is));
                String line;
                StringBuffer response = new StringBuffer();
                while((line = rd.readLine()) != null) {
                    response.append(line);
                    response.append('\r');
                }
                rd.close();
                System.out.println("output  :  "+ response.toString());
                output = response.toString();
                 if(output!=null)
                 {
                     Intent intent = new Intent(Hastag.this,SongList.class);
                     startActivity(intent);
                 }
                return response.toString();

            } catch (Exception e) {

                e.printStackTrace();
                return null;

            } finally {

                if(connection != null) {
                    connection.disconnect();
                }
            }
        }
    }
}







