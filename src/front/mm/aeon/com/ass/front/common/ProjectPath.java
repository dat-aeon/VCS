package mm.aeon.com.ass.front.common;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ProjectPath {
    public static String dirPath;

    public static String createTempDirectory(String fileName) {
        String dir = dirPath + "/temp/" + fileName + "/" + System.currentTimeMillis() + "/";
        try {
            FileUtils.forceMkdir(new File(dir));
        } catch (IOException e) {
            throw new AssertionError();
        }
        return dir;
    }
}
