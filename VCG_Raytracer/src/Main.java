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

import material.Phong;
import parser.Parser;
import light.PointLight;
import material.Lambert;
import raytracer.Raytracer;
import shape.Plane;
import shape.Sphere;
import shape.Triangle;
import ui.Window;
import scene.Scene;
import utils.Globals;
import utils.RgbColor;
import utils.algebra.Vec3;
import utils.io.Log;
import java.io.File;


import java.io.File;


// Main application class. This is the routine called by the JVM to run the program.
public class Main {

    /** ****************************************************** */
    /** ****************** ANIMATION STUFF! ****************** */
    /** ****************** TODO: AUSLAGERN! ****************** */

    static final float bouncyness = .8f;
    static final float friction = 0.8f;
    static final float absorbtion = 0.85f;
    static Vec3 velocity = new Vec3(.9f,0,.9f);
    static Vec3 acceleration = new Vec3(0, -.0981f, 0);
    static Vec3 spherePos = new Vec3(4, -3f, -20);
    static float sphereRadius = 4.5f;


    /** ****************************************************** */
    /** ****************************************************** */


    // Initial method. This is where the show begins.
    public static void main(String[] args){
        //Log.print("Init Main");
        Window renderWindow = new Window(Globals.imageWidth, Globals.imageHeight, Globals.outputTitle);

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

        // loop for animation rendering, for nice results set Globals.frames to 30 * animationDuration
        float frames = 0;
        if (Globals.animation == true) {
            frames = Globals.frames;
        }
        for (int i = 0; i <= frames; i++){
            draw(renderWindow, i);
        }

    }

    private static void draw(Window renderWindow, int frame){
        Scene renderScene = new Scene();

        setupScene(renderScene, frame);

        raytraceScene(renderWindow, renderScene, frame);
    }

    private static void setupScene(Scene renderScene, int frame){
        //Log.print("frame: " + frame);
        setupCameras(renderScene);

        setupCornellBox(renderScene);

        setupObjects(renderScene, frame);

        setupLights(renderScene);
    }

    private static void setupLights(Scene renderScene) {

        /** ****************************************************** */
        /** ****************** CUSTOM LIGHTS! ******************** */
        /** * RENDER THEM ONLY, WHEN THERE ARE NO RANDOM LIGHTS ** */
        /** ****************************************************** */

        if (Globals.randomLights == 0) {
            PointLight red = new PointLight(new Vec3(-5, 8.9f, -10), new RgbColor(1, 0, 0), .5f);
            PointLight blue = new PointLight(new Vec3(0, 8.9f, -17), new RgbColor(0, 0, 1), .5f);
            PointLight green = new PointLight(new Vec3(5, 8.9f, -10), new RgbColor(0, 1, 0), .5f);
            PointLight white = new PointLight(new Vec3(0, 8.5f, -15), new RgbColor(1f, 1f, 1f), 1f);

            //renderScene.addLight(red);
            //renderScene.addLight(blue);
            //renderScene.addLight(green);
            renderScene.addLight(white);
        }

        /** ****************************************************** */
        /** ****************************************************** */


        /** ****************************************************** */
        /** ****************** RANDOM LIGHTS! ******************** */
        /** ********* set amount in Globals.randomLights ********* */
        /** ****************************************************** */

        // add random lights (set Globals.randomLights to amount of desired lights
        for (int i = 0; i < Globals.randomLights; i++){
            renderScene.addLight(new PointLight(new Vec3(rand(-1, 1),8.9f, rand(-19, -21)), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), rand(0,2)/Globals.randomLights));
        }

        /** ****************************************************** */
        /** ****************************************************** */


    }

    private static void setupCameras(Scene renderScene) {

        Vec3 camPos = new Vec3(0, 0, 17);
        Vec3 viewPoint = new Vec3(0,0,0);
        Vec3 upVec = new Vec3(0,1,0);
        float viewAngle = 35 * Globals.RAD;

        renderScene.createPerspCamera(camPos, viewPoint, upVec, viewAngle, Globals.imageWidth, Globals.imageHeight);

    }

    private static void setupObjects(Scene renderScene, int frame) {

        /** ****************************************************** */
        /** ****************** CUSTOM OBJECTS! ******************* */
        /** * RENDER THEM ONLY, WHEN THERE ARE NO RANDOM LIGHTS ** */
        /** ****************************************************** */
        if (Globals.randomSpheres == 0){
            renderScene.addObject(new Sphere(new Vec3(-2, -6, -33), 3f, new RgbColor(1, 0, 0), new Phong(1f, 0f, .3f, .2f, .2f)));
            renderScene.addObject(new Sphere(new Vec3(3, -5, -20), 4f, new RgbColor(0, 0, 1), new Phong(0f, 1f, .3f, .2f, .2f)));
        }
        /** ****************************************************** */
        /** ****************************************************** */


        /** ****************************************************** */
        /** ****************** PARSE .OBJ! *********************** */
        /** *** RENDER ONLY, WHEN THERE ARE NO RANDOM SPHERES **** */
        /** ****************************************************** */

        if (Globals.loadObj == true && Globals.randomSpheres == 0) {

            parser.Parser.loadObjFile("teapot_lowpoly.obj");

            for (int i = 0; i <= Parser.i - 1; i++) {

                Vec3 e0 = new Vec3();
                Vec3 e1 = new Vec3();
                Vec3 e2 = new Vec3();
                Vec3 c = new Vec3();

                // manually translate model
                int translatex = 0;
                int translatey = 0;
                int translatez = -5;

                // scale model
                float scale = .2f;


                e0.x = ((Parser.va.get((int) (Parser.fa.get(i).x - 1)).x) + translatex) * scale;
                e0.y = ((Parser.va.get((int) (Parser.fa.get(i).x - 1)).y) + translatey) * scale;
                e0.z = ((Parser.va.get((int) (Parser.fa.get(i).x - 1)).z) + translatez) * scale;
                e1.x = ((Parser.va.get((int) (Parser.fa.get(i).y - 1)).x) + translatex) * scale;
                e1.y = ((Parser.va.get((int) (Parser.fa.get(i).y - 1)).y) + translatey) * scale;
                e1.z = ((Parser.va.get((int) (Parser.fa.get(i).y - 1)).z) + translatez) * scale;
                e2.x = ((Parser.va.get((int) (Parser.fa.get(i).z - 1)).x) + translatex) * scale;
                e2.y = ((Parser.va.get((int) (Parser.fa.get(i).z - 1)).y) + translatey) * scale;
                e2.z = ((Parser.va.get((int) (Parser.fa.get(i).z - 1)).z) + translatez) * scale;
                // TODO: Rotation of Model


                Triangle triangle = new Triangle(new Vec3(0, 0, 0), new RgbColor(.7f, .7f, .7f), new Lambert(0f, 0f, 0.5f, 0.3f), e0, e1, e2);
                renderScene.addObject(triangle);
            }
        }

        /** ****************************************************** */
        /** ****************************************************** */


        /** ****************************************************** */
        /** ****************** ANIMATION STUFF! ****************** */
        /** ****************** TODO: AUSLAGERN! ****************** */

        Vec3 boundingMin = new Vec3(-12, -9, -40);
        Vec3 boundingMax = new Vec3(12, 9, -10);

        velocity = velocity.add(acceleration);
        spherePos = spherePos.add(velocity);

        if (spherePos.x <= boundingMin.x + sphereRadius){
            velocity.x *= -1 * absorbtion;
            spherePos.x = boundingMin.x + sphereRadius ;
        }
        if (spherePos.x >= boundingMax.x - sphereRadius){
            velocity.x *= -1 * absorbtion;
            spherePos.x = boundingMax.x - sphereRadius ;
        }
        if (spherePos.y <= boundingMin.y + sphereRadius){
            velocity.y *= -1 * bouncyness;
            spherePos.y = boundingMin.y + sphereRadius ;
            velocity.x *= friction;
            velocity.z *= friction;
        }
        if (spherePos.y >= boundingMax.y - sphereRadius){
            velocity.y *= -1 * bouncyness;
            spherePos.y = boundingMax.y - sphereRadius ;
        }
        if (spherePos.z <= boundingMin.z + sphereRadius){
            velocity.z *= -1 * absorbtion;
            spherePos.z = boundingMin.z + sphereRadius ;
        }
        if (spherePos.z >= boundingMax.z - sphereRadius){
            velocity.z *= -1 * absorbtion;
            spherePos.z = boundingMax.z - sphereRadius ;
        }

        // ADD THIS SPHERE (BOUNCY BOY = SPHERE 1) FOR ANIMATION AND SET FRAMES IN GLOBALS!!!!
        if (Globals.animation == true) {
            renderScene.addObject(new Sphere(spherePos, sphereRadius, new RgbColor(1, 1, 1), new Phong(.6f,0f, .3f, .7f, .8f)));
        }

        /** ****************************************************** */
        /** ****************************************************** */


        /** ****************************************************** */
        /** ****************** RANDOM SPHERES! ******************* */
        /** ********* set amount in Globals.randomSpheres ******** */
        /** ****************************************************** */

        // ADD COMPLETELY RANDOM SPHERES (amount in Globals.randomSpheres)
        for (int i = 1; i <= Globals.randomSpheres; i++){

            float x = rand(-10,10);
            float y = rand(-9,9);
            float z = rand(-40,-20);
            float radius = rand(1,4);
            float r = rand(0,1);
            float g = rand(0,1);
            float b = rand(0,1);
            float reflectivity = rand(0,1);
            float refractivity = rand(0,1);
            float k_a = rand(0,1)*.5f;
            float k_d = rand(0,1);
            float k_s = rand(0,1);
            if ((k_d + k_s) > 1){
                while ((k_d + k_s) > 1){
                    k_d *= 0.9;
                    k_s *= 0.9;
                }
            }

            renderScene.addObject(new Sphere(new Vec3(x,y,z), radius, new RgbColor(r,g,b), new Phong(reflectivity, refractivity, k_a, k_d, k_s)));
            Log.print("i: " + i + " // pos: " + x + " " + y + " " + z +" // radius: " + radius + " // color: " + r + " " + g + " " + b + " // reflecivity: " + reflectivity + " // k_a: " + k_a + " // k_d: " + k_d + " // k_s: " + k_s);
        }

        /** ****************************************************** */
        /** ****************************************************** */

    }

    private static float rand(int min, int max) {
        float random = (float)min + (float)Math.random() * (max - min);
        return random;
    }

    private static void setupCornellBox(Scene renderScene) {
        Plane planeBack = new Plane(new Vec3(0,0,-40), new RgbColor(1,1,1), new Lambert(0f, 0f, .5f, .3f), new Vec3(0,0,1));
        Plane planeLeft = new Plane(new Vec3(-12,0,0), new RgbColor(1,0,0), new Lambert(0f, 0f, .5f, .3f), new Vec3(1,0,0));
        Plane planeRight = new Plane(new Vec3(12,0,0), new RgbColor(0,0,1), new Lambert(0f, 0f, .5f, .3f), new Vec3(-1,0,0));
        Plane planeTop = new Plane(new Vec3(0,9,0), new RgbColor(1,1, 1), new Lambert(0f, 0f, .5f, .3f), new Vec3(0,-1,0));
        Plane planeBottom = new Plane(new Vec3(0,-9,0), new RgbColor(1,1,1), new Lambert(0f, 0f, .5f, .3f), new Vec3(0,1,0));
        Plane planeFront = new Plane(new Vec3(0,0,18), new RgbColor(0,0,0), new Lambert(0f, 0f, .5f, .3f), new Vec3(0,1,0));

        renderScene.addObject(planeBack);
        renderScene.addObject(planeLeft);
        renderScene.addObject(planeRight);
        renderScene.addObject(planeTop);
        renderScene.addObject(planeBottom);
        renderScene.addObject(planeFront);

    }

    private static void raytraceScene(Window renderWindow, Scene renderScene, int frame){
        // TODO: You should use English only since the environment is in English and your co-workers also might only speak English

        Raytracer raytracer = new Raytracer(
                renderScene,
                renderWindow);

        raytracer.renderScene(frame);
    }
}