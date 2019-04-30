// ************************************************************ //
//                      Hochschule Duesseldorf                  //
//                                                              //
//                     Vertiefung Computergrafik                //
// ************************************************************ //


/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    1. Documentation:    Did you comment your code shortly but clearly?
    2. Structure:        Did you clean up your code and put everything into the right bucket?
    3. Performance:      Are all loops and everything inside really necessary?
    4. Theory:           Are you going the right way?

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 <<< JellyBean >>>

     Master of Documentation: Jannik
     Master of Structure: Nam
     Master of Performance: Steven
     Master of Theory: Timo

~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

import raytracer.Raytracer;
import shape.Sphere;
import ui.Window;
import scene.Scene;
import utils.Globals;
import utils.RgbColor;
import utils.algebra.Vec3;
import utils.io.Log;

// Main application class. This is the routine called by the JVM to run the program.
public class Main {

    /** ****************************************************** */
    /** ****************** CONSTANTS GO HERE *ff***************** **/

    static final int IMAGE_WIDTH = 1920;
    static final int IMAGE_HEIGHT = 1000;

    static final String OUTPUT_TITLE = "team_raytracer";

    // TODO: Do not put static numbers inside the code. Collect them here and use them!
    // TODO: That is how you can find them and make changes much easier!

    /** ****************************************************** */
    /** ****************************************************** */


    // Initial method. This is where the show begins.
    public static void main(String[] args){
        Log.print("Init Main");
        Window renderWindow = new Window(IMAGE_WIDTH, IMAGE_HEIGHT, OUTPUT_TITLE);

        // TODO: Make raytracing great again!

        // TODO: Tricks: Logging
        Log.warn("This is a warning");
        Log.error("This is an error");
        Log.print("This is an information");

        // TODO: Tricks: Exceptions
        // This helps only temporary! You should fix the source of the error rather than catching it!
        try{
            float stupid = 1/0;
        }
        catch(ArithmeticException ex){
            Log.error("This is a caught exception, because someone wanted to divide by 0: " + ex);
        }

        try{
            Window stupid = null;
            stupid.getClass();
        }
        catch(NullPointerException ex){
            Log.error("This is a caught exception, because someone wanted use an not initialized class: " + ex);
        }

        draw(renderWindow);
    }

    private static void draw(Window renderWindow){
        Scene renderScene = new Scene();

        setupScene(renderScene);

        raytraceScene(renderWindow, renderScene);
    }

    private static void setupScene(Scene renderScene){
        setupCameras(renderScene);

        setupCornellBox(renderScene);

        setupObjects(renderScene);

        setupLights(renderScene);
    }

    private static void setupLights(Scene renderScene) {
        Log.warn("We don't have lights, yet");
    }

    private static void setupCameras(Scene renderScene) {

        Vec3 camPos = new Vec3(0, 0, 30);
        Vec3 viewPoint = new Vec3(0,40,0);
        Vec3 upVec = new Vec3(0,1,0);
        float viewAngle = 100 * Globals.RAD;

        renderScene.createPerspCamera(camPos, viewPoint, upVec, viewAngle, IMAGE_WIDTH, IMAGE_HEIGHT);

    }

    private static void setupObjects(Scene renderScene) {
        float r = 225f/255f;
        float g = 173f/255f;
        float b = 165f/255f;

        Sphere sphere1 = new Sphere(new Vec3(-4f,0,1f), 11f, new RgbColor(.95f*r, g, b));
        renderScene.addObject(sphere1);
        Sphere sphere2 = new Sphere(new Vec3(4f,0,1f), 11f, new RgbColor(.95f*r, g, b));
        renderScene.addObject(sphere2);

        Sphere sphere3 = new Sphere(new Vec3(0,9f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere3);
        Sphere sphere4 = new Sphere(new Vec3(0,12f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere4);
        Sphere sphere5 = new Sphere(new Vec3(0,15f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere5);
        Sphere sphere6 = new Sphere(new Vec3(0,18f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere6);
        Sphere sphere7 = new Sphere(new Vec3(0,21f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere7);
        Sphere sphere8 = new Sphere(new Vec3(0,24f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere8);
        Sphere sphere9 = new Sphere(new Vec3(0,27f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere9);
        Sphere sphere10 = new Sphere(new Vec3(0,30f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere10);
        Sphere sphere11= new Sphere(new Vec3(0,33f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere11);
        Sphere sphere12 = new Sphere(new Vec3(0,36f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere12);
        Sphere sphere13 = new Sphere(new Vec3(0,39f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere13);
        Sphere sphere14 = new Sphere(new Vec3(0,42f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere14);
        Sphere sphere15 = new Sphere(new Vec3(0,45f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere15);
        Sphere sphere16 = new Sphere(new Vec3(0,48f,0), 9f, new RgbColor(r,g,b));
        renderScene.addObject(sphere16);
        Sphere sphere17 = new Sphere(new Vec3(-2.6f,46f,8f), 3f, new RgbColor(1,1,1));
        renderScene.addObject(sphere17);
        Sphere sphere18 = new Sphere(new Vec3(2.6f,46f,8f), 3f, new RgbColor(1,1,1));
        renderScene.addObject(sphere18);
        Sphere sphere19 = new Sphere(new Vec3(-2.3f,38.5f,11f), 1f, new RgbColor(0,0,0));
        renderScene.addObject(sphere19);
        Sphere sphere20 = new Sphere(new Vec3(2.3f,38.5f,11f), 1f, new RgbColor(0,0,0));
        renderScene.addObject(sphere20);
        Sphere sphere21 = new Sphere(new Vec3(-1.5f,30.5f,11f), 1.5f, new RgbColor(.1f,0,0));
        renderScene.addObject(sphere21);



    }

    private static void setupCornellBox(Scene renderScene) {

    }

    private static void raytraceScene(Window renderWindow, Scene renderScene){
        // TODO: You should use English only since the environment is in English and your co-workers also might only speak English

        Raytracer raytracer = new Raytracer(
                renderScene,
                renderWindow);

        raytracer.renderScene();
    }
}