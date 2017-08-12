package com.reveal.asia;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationType;
import com.intellij.notification.Notifications;
import com.intellij.openapi.application.ApplicationManager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 * Created by emadpres on 8/12/17.
 */
public class OddsAndEnds
{
    static public void showInfoBalloon(String title, String content)
    {
        ApplicationManager.getApplication().invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                Notification notif = new Notification("ASIA_PLUGIN_POPUP", title, content, NotificationType.INFORMATION);
                Notifications.Bus.notify(notif);
            }
        });
    }

    static public void simpleWriteToFile(String filename, String content)
    {
        BufferedWriter writer = null;
        try {
            File logFile = new File(filename);

            // This will output the full path where the file will be written to...
            //System.out.println(logFile.getCanonicalPath());

            writer = new BufferedWriter(new FileWriter(logFile));
            writer.write(content);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                // Close the writer regardless of what happens...
                writer.close();
            } catch (Exception e) {
            }
        }
    }

}
