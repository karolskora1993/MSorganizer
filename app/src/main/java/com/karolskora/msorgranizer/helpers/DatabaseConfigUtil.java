package com.karolskora.msorgranizer.helpers;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.karolskora.msorgranizer.models.User;

import java.io.File;


public class DatabaseConfigUtil extends OrmLiteConfigUtil {

    private static final Class<?>[] classes = new Class[] {
            User.class,
    };

    public static void main(String[] args) throws Exception {
        String ORMLITE_CONFIGURATION_FILE_NAME = "ormlite_config.txt";

/**
 * Full configuration path includes the project root path, and the location
 * of the ormlite_config.txt file appended to it.
 */
        File configFile = new File(new File("").getAbsolutePath()
                .split("app" +File.separator + "build")[0] + File.separator +
                "app" + File.separator +
                "src" + File.separator +
                "main" + File.separator +
                "res" + File.separator +
                "raw" + File.separator +
                ORMLITE_CONFIGURATION_FILE_NAME);

/**
 * Pass configFile as argument in configuration file writer method.
 */
        writeConfigFile(configFile, classes);
    }
}
