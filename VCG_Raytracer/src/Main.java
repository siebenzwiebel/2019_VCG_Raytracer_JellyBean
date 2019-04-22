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
import ui.Window;
import scene.Scene;
import utils.algebra.Vec3;
import utils.io.Log;

// Main application class. This is the routine called by the JVM to run the program.
public class Main {

    /** ****************************************************** */
    /** ****************** CONSTANTS GO HERE *ff***************** **/

    static final int IMAGE_WIDTH = 800;
    static final int IMAGE_HEIGHT = 600;

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
        Log.warn("We don't have cameras, either");

        // setup multiple test cameras
        Vec3 camPos_1 = new Vec3(0, 0, 17);
        Vec3 viewPoint_1 = new Vec3(0,0,0);
        Vec3 upVec_1 = new Vec3(0,1,0);
        float viewAngle_1 = 35;
        float focalLength_1 = 0;

        Vec3 camPos_2 = new Vec3(0, 0, 17);
        Vec3 viewPoint_2 = new Vec3(0,0,0);
        Vec3 upVec_2 = new Vec3(0,1,0);
        float viewAngle_2 = 35;
        float focalLength_2 = 0;

        Vec3 camPos_3 = new Vec3(0, 0, 17);
        Vec3 viewPoint_3 = new Vec3(0,0,0);
        Vec3 upVec_3 = new Vec3(0,1,0);
        float viewAngle_3 = 35;
        float focalLength_3 = 0;

        renderScene.createPerspCamera(camPos_1, viewPoint_1, upVec_1, viewAngle_1, focalLength_1, IMAGE_WIDTH, IMAGE_HEIGHT);
        //renderScene.createPerspCamera(camPos, viewPoint, upVec, viewAngle, focalLength, IMAGE_WIDTH, IMAGE_HEIGHT);
        //renderScene.createPerspCamera(camPos, viewPoint, upVec, viewAngle, focalLength, IMAGE_WIDTH, IMAGE_HEIGHT);
    }

    private static void setupObjects(Scene renderScene) {
        Log.warn("Just so, so empty. No objects");
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