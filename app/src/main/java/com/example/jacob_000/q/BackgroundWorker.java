package com.example.jacob_000.q;

import android.app.Activity;
import android.os.Debug;
import android.support.annotation.MainThread;
import android.support.v4.app.FragmentActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * Created by jacob_000 on 10/4/2017.
 */

public class BackgroundWorker extends Thread
{
    public static BufferedReader reader;
    private Activity mainActivity;
    private Reports reports;
    private String LastReportCode;

    public BackgroundWorker(Activity activity)
    {
        mainActivity = activity;
    }

    @Override
    public void run()
    {

        //Debug.waitForDebugger();


            try {

                    // Defined URL  where to send data
                    URL url = new URL("http://www.igal-testing-sites.com/api/GetReports?lastReportHash=" + LastReportCode);

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
                    while ((line = reader.readLine()) != null) {
                        // Append server response in string
                        sb.append(line + "\n");
                    }

                    Gson gson = new Gson();
                    Type type = new TypeToken<Reports>(){}.getType();
                    reports = gson.fromJson(sb.toString(), type);

                    Runnable runnable = new Runnable() {
                        @Override
                        public void run()
                        {
                            MapsActivity.UpdateReports(reports.Reports);

                            synchronized(this)
                            {
                                this.notify();
                            }
                        }
                    };

                    if (reports != null) {
                        this.LastReportCode = reports.LastReportHash;

                        if (reports.Reports.size() > 0) {
                            synchronized (runnable) {
                                mainActivity.runOnUiThread(runnable);
                                runnable.wait(); // unlocks myRunable while waiting
                            }
                        }

                    }
                Thread.sleep(5000);
                run();

            }
            catch (Exception ex)
            {
                ex.printStackTrace();
            }

    }


}
