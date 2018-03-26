package spotlightwallpaper;

import com.sun.jna.Native;
import com.sun.jna.platform.win32.WinDef.UINT_PTR;
import com.sun.jna.win32.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import org.jimageanalyst.*;

public final class SpotLight {

    ArrayList<String> wallpapers;
    String dir = System.getProperty("user.home") + "\\AppData\\Local\\Packages\\Microsoft.Windows.ContentDeliveryManager_cw5n1h2txyewy\\LocalState\\Assets\\";

    int n = 0;

    public SpotLight() {
        try {
            wallpapers = path();
        } catch (Exception ex) {
            Logger.getLogger(SpotLight.class.getName()).log(Level.SEVERE, null, ex);
        }
        n=readPreference();
    }

    public void next() {
        n++;
        if (n >= wallpapers.size()||n<0) {
            n = 0;
        }
        setWallpaper();
    }

    public void savePreference(int index) {
        if(index<0)return;
        Preferences prefs = Preferences.userNodeForPackage(SpotLight.class);

        prefs.put("k", wallpapers.get(index));
    }

    public int readPreference() {
        Preferences prefs = Preferences.userNodeForPackage(SpotLight.class);
        return Collections.binarySearch(wallpapers,prefs.get("k", "default"));
    }

    void setWallpaper() {
        savePreference(n);
        new Thread(() -> {
            SPI.INSTANCE.SystemParametersInfo(
                    new UINT_PTR(SPI.SPI_SETDESKWALLPAPER),
                    new UINT_PTR(0),
                    dir + wallpapers.get(n),
                    new UINT_PTR(SPI.SPIF_UPDATEINIFILE | SPI.SPIF_SENDWININICHANGE));
        }).start();
    }

    ArrayList<String> path() throws Exception {
        File f = new File(dir);
        File[] ff = f.listFiles();
        ArrayList<String> path = new ArrayList<>();
        for (File k : ff) {
            if (k.length() / 1024 > 200) {
                byte fileContent[] = new byte[(int) k.length()];
                FileInputStream fin = new FileInputStream(k);
                fin.read(fileContent);
                InputStream inp = new ByteArrayInputStream(fileContent);
                JImageAnalyst analyst = JImageAnalystFactory.getDefaultInstance();
                ImageInfo ii = analyst.analyze(inp);

                if (ii.getHeight() == 1080 && ii.getWidth() == 1920) {
                    path.add(k.getName());
                }
            }
        }
        return path;
    }

    void random() {
        n=(int) (Math.random() * wallpapers.size());
        setWallpaper();
    }

    public interface SPI extends StdCallLibrary {

        long SPI_SETDESKWALLPAPER = 20;
        long SPIF_UPDATEINIFILE = 0x01;
        long SPIF_SENDWININICHANGE = 0x02;
        HashMap map = new HashMap<Object, Object>() {
            {
                put(OPTION_TYPE_MAPPER, W32APITypeMapper.UNICODE);
                put(OPTION_FUNCTION_MAPPER, W32APIFunctionMapper.UNICODE);
            }
        };
        SPI INSTANCE = (SPI) Native.loadLibrary("user32", SPI.class, map);

        boolean SystemParametersInfo(
                UINT_PTR uiAction,
                UINT_PTR uiParam,
                String pvParam,
                UINT_PTR fWinIni
        );
    }
}
