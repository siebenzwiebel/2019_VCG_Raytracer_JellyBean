package utils;

import utils.algebra.Vec3;

public class Globals {
    public static final int imageWidth = 800;
    public static final int imageHeight = 600;

    // set to switch between aufgabe 3 and 4
    public static final int aufgabe = 0;

    public static final String outputTitle = "team_jellybean";
    public static final float RAD = (float) Math.PI / 180;

    public static final int recursionDepth = 1;
    public static final int lightRecursionDepth = 2; // SET TO ZERO TO TURN GLOBAL ILLUMINATION OFF  // lightRays = lightRecursionRays^(lightRecursionDepth)
    public static final int lightRecursionRays = 32;
    public static final int sampling = 2;
    public static final int lightSamples = 4;
    public static final float sampleFraction = 1f / (sampling * sampling);
    public static final int threads = Runtime.getRuntime().availableProcessors();
    public static final Vec3 ambientLight = new Vec3(1f,1f,1f);

    public static final float albedo = 1f;
    public static final float reciPi = (float) (albedo / Math.PI);

    public static final float ambient = 1.2f;
    public static final float epsilon = 0.00009f;

    public static final float water = 1.33f;
    public static final float glass = 1.5f;
    public static final float diamond = 2.42f;
    public static final float bleikristall = 1.93f;
    public static final float quarz = 1.54f;
    public static final float ethanol = 1.3614f;
    public static final float augenlinse = 1.37f;
    public static final float eis = 1.31f;


    public static final int randomSpheres = 0;
    public static final int randomLights = 0;
    public static final boolean loadObj = false;

    public static double stopTime = 0;

    public static float zeta = .05f;
    public static final int duration = 20;
    public static final int framerate = 30;


    // ANIMATION
    public static final boolean animation = false;
    public static final float frames = 0; //framerate * duration;

    public static float rand(int min, int max) {
        return (float)min + (float)Math.random() * (max - min);
    }
}
