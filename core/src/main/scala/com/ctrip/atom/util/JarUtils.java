package com.ctrip.atom.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huang_xw
 */
public class JarUtils {
    /**
     * indicate OS type
     */
    private static boolean flag = false;

    static {
        String osName = System.getProperty("os.name");
        System.out.println("OS:" + osName);
        if (osName.startsWith("Windows")) {
            flag = true;
        }
    }

    /**
     * project path in windows
     */
    private static final String projectHome =
            System.getProperty("user.dir").replaceAll("\\\\", "/");
    private static final String folder = "/bin";

    private static String batName = "package.bat";

    public static List<String> buildJar(String os) {
        Process process;
        String line = "";
        String command = "cmd.exe /c ";
        if (os.toLowerCase().contains("mac")) {
            batName = "package.sh";
            command = "sh ";
        }
        String batFile = projectHome + folder + "/" + batName;
        List<String> jarFiles = new ArrayList<String>();
        //String tag = "Building jar: ";
        String assemblyTag = "Building jar: ";

        System.out.println(batFile);
        try {
            process = Runtime.getRuntime().exec(command + batFile);
            InputStream is = process.getInputStream();
            BufferedReader bReader = new BufferedReader(new InputStreamReader(is, "utf8"));
            while ((line = bReader.readLine()) != null) {
                System.out.println(line);
                int index = line.indexOf(assemblyTag);
                if (index > 0) {
                    jarFiles.add(line.substring(index + assemblyTag.length()));
                }
            }
            is.close();
            process.waitFor();
            process.destroy();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return jarFiles;
    }
}
