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
    /** ****************** CONSTANTS GO HERE ****************** **/

    static final int IMAGE_WIDTH = 800;
    static final int IMAGE_HEIGHT = 600;
    static final float bouncyness = .8f;
    static final float friction = 0.8f;
    static final float absorbtion = 0.85f;
    static Vec3 velocity = new Vec3(.9f,0,.9f);
    static Vec3 acceleration = new Vec3(0, -.0981f, 0);
    static Vec3 spherePos = new Vec3(0, 6f, -20);
    static float sphereRadius = 1.5f;



    static final String OUTPUT_TITLE = "team_jellybean";

    // TODO: Do not put static numbers inside the code. Collect them here and use them!
    // TODO: That is how you can find them and make changes much easier!

    /** ****************************************************** */
    /** ****************************************************** */


    // Initial method. This is where the show begins.
    public static void main(String[] args){
        //Log.print("Init Main");
        Window renderWindow = new Window(IMAGE_WIDTH, IMAGE_HEIGHT, OUTPUT_TITLE);

        // TODO: Make raytracing great again!

        // TODO: Tricks: Logging
        //Log.warn("This is a warning");
        //Log.error("This is an error");
        //Log.print("This is an information");

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
        for (int i = 0; i <= Globals.frames; i++){
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
        PointLight light1 = new PointLight(new Vec3(-5,8.9f,-10), new RgbColor(1,0,0), .25f);
        PointLight light2 = new PointLight(new Vec3(0,8.9f,-17), new RgbColor(0,0,1), .25f);
        PointLight light3 = new PointLight(new Vec3(5,8.9f,-23f), new RgbColor(0,1,0), .25f);

        renderScene.addLight(light1);
        renderScene.addLight(light2);
        renderScene.addLight(light3);
        /*
        int n = 100;
        for (int i = 0; i < n; i++){
            renderScene.addLight(new PointLight(new Vec3(rand(-1, 1),8.9f, rand(-19, -21)), new RgbColor(1,1,1), rand(0,1)/n));
        }
        */

    }

    private static void setupCameras(Scene renderScene) {

        Vec3 camPos = new Vec3(0, 0, 17);
        Vec3 viewPoint = new Vec3(0,0,0);
        Vec3 upVec = new Vec3(0,1,0);
        float viewAngle = 35 * Globals.RAD;

        renderScene.createPerspCamera(camPos, viewPoint, upVec, viewAngle, IMAGE_WIDTH, IMAGE_HEIGHT);

    }

    private static void setupObjects(Scene renderScene, int frame) {

    /*    parser.Parser.loadObjFile("C:\\Users\\Timo\\Desktop\\Tree.obj");

        for(int i=0; i <= Parser.i-1 ; i++){
            Vec3 e0 = new Vec3();
            Vec3 e1 = new Vec3();
            Vec3 e2 = new Vec3();
            Vec3 c = new Vec3();
            int translatex = 1;
            int translatey = -2;
            int translatez = -15;
            int scale = 1;
            e0.x = ((Parser.va.get((int)(Parser.fa.get(i).x - 1)).x)+translatex)*scale;
            e0.y = ((Parser.va.get((int)(Parser.fa.get(i).x - 1)).y)+translatey)*scale;
            e0.z = ((Parser.va.get((int)(Parser.fa.get(i).x - 1)).z)+translatez)*scale;
            e1.x = ((Parser.va.get((int)(Parser.fa.get(i).y - 1)).x)+translatex)*scale;
            e1.y = ((Parser.va.get((int)(Parser.fa.get(i).y - 1)).y)+translatey)*scale;
            e1.z = ((Parser.va.get((int)(Parser.fa.get(i).y - 1)).z)+translatez)*scale;
            e2.x = ((Parser.va.get((int)(Parser.fa.get(i).z - 1)).x)+translatex)*scale;
            e2.y = ((Parser.va.get((int)(Parser.fa.get(i).z - 1)).y)+translatey)*scale;
            e2.z = ((Parser.va.get((int)(Parser.fa.get(i).z - 1)).z)+translatez)*scale;


            Triangle triangle = new Triangle(new Vec3(0,0,0), new RgbColor((float)Math.random(),(float)Math.random(),(float)Math.random()),new Phong(0.5f,0.3f,0.4f,0.6f), e0,e1,e2);
            renderScene.addObject(triangle);
        } */




        Sphere sphere1 = new Sphere(spherePos, sphereRadius, new RgbColor(0,0,1), new Phong(0.8f,.3f, .4f, .6f));
       // Triangle triangle1 = new Triangle(new Vec3(0,0,0), new RgbColor(1,0,0),new Phong(0.3f,0.4f,0.6f), new Vec3(0-5,-0.353553f-5,-0.707107f-25),new Vec3(0.707107f-5,-0.353553f-5,0-25),new Vec3(-0.707107f-5,-0.353553f-5,0-25));
       // Triangle triangle2 = new Triangle(new Vec3(0,0,0), new RgbColor(0,1,0),new Phong(0.3f,0.4f,0.6f), new Vec3(-0.707107f-5,-0.353553f-5,0-25),new Vec3(0.707107f-5,-0.353553f-5,0-25),new Vec3(0-5,-0.353553f-5,0.707107f-25));
        //Triangle triangle3 = new Triangle(new Vec3(0,0,0), new RgbColor(0,0,1),new Phong(0.3f,0.4f,0.6f), new Vec3(0-5,-0.353553f-5,-0.707107f-25),new Vec3(-0.707107f-5,-0.353553f-5,0-25),new Vec3(0-5,-0.353553f-5,0-25));
        //Triangle triangle4 = new Triangle(new Vec3(0,0,0), new RgbColor(1,1,0),new Phong(0.3f,0.4f,0.6f), new Vec3(-0.707107f-5,-0.353553f-5,0-25),new Vec3(0-5,-0.353553f-5,0.707107f-25),new Vec3(0-5,0.353553f-5,0-25));
       // Triangle triangle5 = new Triangle(new Vec3(0,0,0), new RgbColor(1,0,1),new Phong(0.3f,0.4f,0.6f), new Vec3(0-5,-0.353553f-5,0.707107f-25),new Vec3(0.707107f-5,-0.353553f-5,0-25),new Vec3(0-5,0.353553f-5,0-25));
       // Triangle triangle6 = new Triangle(new Vec3(0,0,0), new RgbColor(1,1,1),new Phong(0.3f,0.4f,0.6f), new Vec3(0.707107f-5,-0.353553f-5,0-25),new Vec3(0-5,-0.353553f-5,-0.707107f-25),new Vec3(0-5,0.353553f-5,0-25));


        //Sphere sphere2 = new Sphere(new Vec3(5,-6,-20), 3f, new RgbColor(0,1,0), new Lambert());
        //Sphere sphere3 = new Sphere(new Vec3(4,-1.5f,-21), 1.5f, new RgbColor(1,1,1), new Lambert());


        // ADD THIS SPHERE (BOUNCY BOY = SPHERE 1) FOR ANIMATION AND SET FRAMES IN GLOBALS!!!!
        renderScene.addObject(sphere1);
      //  renderScene.addObject(triangle2);
      //  renderScene.addObject(triangle3);
      //  renderScene.addObject(triangle4);
     //   renderScene.addObject(triangle5);
      //  renderScene.addObject(triangle6);



        //renderScene.addObject(sphere2);
        //renderScene.addObject(sphere3);

        /*
        for (int i = 0; i < 3; i++){
            float k_d = rand(0,1);
            float k_s = 1 - k_d;
            renderScene.addObject(new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Phong(rand(0,1),k_d,k_s)));
        }
        */





        // dummy
        /*
        Sphere sphere_01 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_01);
        Sphere sphere_02 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_02);
        Sphere sphere_03 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_03);
        Sphere sphere_04 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_04);
        Sphere sphere_05 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_05);
        Sphere sphere_06 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_06);
        Sphere sphere_07 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_07);
        Sphere sphere_08 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_08);
        Sphere sphere_09 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_09);
        Sphere sphere_10 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_10);
        Sphere sphere_11 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_11);
        Sphere sphere_12 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_12);
        Sphere sphere_13 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_13);
        Sphere sphere_14 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_14);
        Sphere sphere_15 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_15);
        Sphere sphere_16 = new Sphere(new Vec3(rand(-10, 10),rand(-9,9),rand(-40,-20)), rand(1,3), new RgbColor(rand(0,1),rand(0,1),rand(0,1)), new Lambert());
        renderScene.addObject(sphere_16);
        */



    }

    private static float rand(int min, int max) {

        float random = (float)min + (float)Math.random() * (max - min);
        return random;
    }

    private static void setupCornellBox(Scene renderScene) {
        Plane planeBack = new Plane(new Vec3(0,0,-40), new RgbColor(1,1,1), new Lambert(0.0f), new Vec3(0,0,1));
        Plane planeLeft = new Plane(new Vec3(-12,0,0), new RgbColor(1,0,0), new Lambert(0.0f), new Vec3(1,0,0));
        Plane planeRight = new Plane(new Vec3(12,0,0), new RgbColor(0,0,1), new Lambert(0.0f), new Vec3(-1,0,0));
        Plane planeTop = new Plane(new Vec3(0,9,0), new RgbColor(1,1, 1), new Lambert(0.0f), new Vec3(0,-1,0));
        Plane planeBottom = new Plane(new Vec3(0,-9,0), new RgbColor(1,1,1), new Lambert(0.0f), new Vec3(0,1,0));


        renderScene.addObject(planeBack);
        renderScene.addObject(planeLeft);
        renderScene.addObject(planeRight);
        renderScene.addObject(planeTop);
        renderScene.addObject(planeBottom);

    }

    private static void raytraceScene(Window renderWindow, Scene renderScene, int frame){
        // TODO: You should use English only since the environment is in English and your co-workers also might only speak English

        Raytracer raytracer = new Raytracer(
                renderScene,
                renderWindow);

        raytracer.renderScene(frame);
    }
}