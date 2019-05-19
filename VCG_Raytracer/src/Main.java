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

import light.PointLight;
import material.LambertMaterial;
import raytracer.Raytracer;
import shape.Plane;
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
        PointLight light1 = new PointLight(new Vec3(0,9,-20), new RgbColor(1,1,1), 1f);
        PointLight light2 = new PointLight(new Vec3(0,3,0), new RgbColor(0,1,1), .5f);
        PointLight light3 = new PointLight(new Vec3(3,3,0), new RgbColor(1,0,1), .5f);
        renderScene.addLight(light1);
        //renderScene.addLight(light2);
        //renderScene.addLight(light3);
    }

    private static void setupCameras(Scene renderScene) {

        Vec3 camPos = new Vec3(0, 0, 17);
        Vec3 viewPoint = new Vec3(0,0,0);
        Vec3 upVec = new Vec3(0,1,0);
        float viewAngle = 35 * Globals.RAD;

        renderScene.createPerspCamera(camPos, viewPoint, upVec, viewAngle, IMAGE_WIDTH, IMAGE_HEIGHT);

    }

    private static void setupObjects(Scene renderScene) {

        Sphere sphere1 = new Sphere(new Vec3(-5,-6,-30), 3f, new RgbColor(1,0,0), new LambertMaterial());
        Sphere sphere2 = new Sphere(new Vec3(5,-6,-20), 3f, new RgbColor(0,1,0), new LambertMaterial());

        renderScene.addObject(sphere1);
        renderScene.addObject(sphere2);


    }

    private static void setupCornellBox(Scene renderScene) {
        Plane planeBack = new Plane(new Vec3(0,0,-40), new RgbColor(1,1,1), new LambertMaterial(), new Vec3(0,0,1));
        Plane planeLeft = new Plane(new Vec3(-12,0,0), new RgbColor(1,0,0), new LambertMaterial(), new Vec3(1,0,0));
        Plane planeRight = new Plane(new Vec3(12,0,0), new RgbColor(0,0,1), new LambertMaterial(), new Vec3(-1,0,0));
        Plane planeTop = new Plane(new Vec3(0,9,0), new RgbColor(1,1, 1), new LambertMaterial(), new Vec3(0,-1,0));
        Plane planeBottom = new Plane(new Vec3(0,-9,0), new RgbColor(1,1,1), new LambertMaterial(), new Vec3(0,1,0));


        renderScene.addObject(planeBack);
        renderScene.addObject(planeLeft);
        renderScene.addObject(planeRight);
        renderScene.addObject(planeTop);
        renderScene.addObject(planeBottom);

    }

    private static void raytraceScene(Window renderWindow, Scene renderScene){
        // TODO: You should use English only since the environment is in English and your co-workers also might only speak English

        Raytracer raytracer = new Raytracer(
                renderScene,
                renderWindow);

        raytracer.renderScene();
    }
}