package com.karolskora.msorgranizer.java;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.karolskora.msorgranizer.models.ApplicationSettings;
import com.karolskora.msorgranizer.models.DrugSupply;
import com.karolskora.msorgranizer.models.Injection;
import com.karolskora.msorgranizer.models.Notification;
import com.karolskora.msorgranizer.models.User;

import java.io.File;


public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] {
            User.class,
            Notification.class,
            Injection.class,
            DrugSupply.class,
            ApplicationSettings.class
    };

    public static void main(String[] args) throws Exception {
        String ORMLITE_CONFIGURATION_FILE_NAME = "ormlite_config.txt";

        File configFile = new File(new File("").getAbsolutePath()
                .split("app" +File.separator + "build")[0] + File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "res" + File.separator +
                "raw" + File.separator +
                ORMLITE_CONFIGURATION_FILE_NAME);

        writeConfigFile(configFile, classes);
    }
}
