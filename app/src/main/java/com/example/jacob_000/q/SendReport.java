package com.example.jacob_000.q;

import android.os.Debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by jacob_000 on 10/5/2017.
 */

public class SendReport extends Thread
{

    public static BufferedReader reader;
    private int queue;

    public SendReport(int Queue)
    {
        queue = Queue;
    }


    @Override
    public void run()
    {
            //Debug.waitForDebugger();

            // Send data
            try
            {
                // Defined URL  where to send data
                URL url = new URL("http://www.igal-testing-sites.com/api/report?location.latitude=" + MapsActivity.CurrentLatitude + "&location.longitude=" + MapsActivity.CurrentLongitude + "&Queue=" + queue);

                // Send POST data request
                URLConnection conn = url.openConnection();
                conn.setDoInput(true);
                //OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
                //wr.write("?location.latitude=123&location.longitude=456&Queue=7890");
                //wr.flush();

                // Get the server response
                reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder sb = new StringBuilder();
                String line = null;

                // Read Server Response
                while ((line = reader.readLine()) != null)
                {
                    // Append server response in string
                    sb.append(line + "\n");
                }

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }
            finally {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }



    }

}
