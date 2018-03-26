/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package spotlightwallpaper;

/**
 *
 * @author hp
 */
public class SpotLightWallpaper {

    private static final String OS = System.getProperty("os.name").toLowerCase();

    public static boolean isWindows() {
        return (OS.contains("win"));
    }

    public static void main(String[] args) {
        String key=(args.length>1)?args[0]:"";
        if (isWindows()) {
            SpotLight s=new SpotLight();
            if ("-r".equals(key)){s.random();}
            else{s.next();}
        }
    }

}
