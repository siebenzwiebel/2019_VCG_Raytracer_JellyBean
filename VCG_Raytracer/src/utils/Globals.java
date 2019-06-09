package utils;

public class Globals {
    public static final int imageWidth = 1024;
    public static final int imageHeight = 768;

    // set to switch between aufgabe 3 and 4
    public static final int aufgabe = 4;

    public static final String outputTitle = "team_jellybean";
    public static float RAD = (float) Math.PI / 180;

    public static int recursionDepth = 0;
    public static final int sampling = 1;
    public static final float sampleFraction = 1f / (sampling * sampling);
    public static final int threads = 1;

    public static float ambient = 0f;
    public static float epsilon = 0.00009f;
    public static float nWater = 1.33f;
    public static float nGlass = 1.5f;
    public static float nDiamond = 2.42f;
    public static float nBleikristall = 1.93f;
    public static float nQuarz = 1.54f;
    public static float nEthanol = 1.3614f;
    public static float nAugenlinse = 1.37f;
    public static float nEis = 1.31f;


    public static int randomSpheres = 0;
    public static int randomLights = 0;
    public static boolean loadObj = false;

    public static double stopTime = 0;

    public static float zeta = .05f;
    public static final int duration = 20;
    public static final int framerate = 30;

    // ANIMATION
    public static boolean animation = false;
    public static float frames = 0; //framerate * duration;

    public static float rand(int min, int max) {
        float random = (float)min + (float)Math.random() * (max - min);
        return random;
    }
}
