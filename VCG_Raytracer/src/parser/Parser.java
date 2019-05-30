package parser;

import material.Phong;
import shape.Triangle;
import utils.RgbColor;
import utils.algebra.Vec3;
import utils.algebra.Vec2;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;



public class Parser {

    public static int i = 0;
    public static List<Vec3> va = new ArrayList<Vec3>();
    public static List<Vec3> fa = new ArrayList<Vec3>();

    public static void loadObjFile(String filePath){
        Vec3 e0;
        Vec3 e1;
        Vec3 e2;

        FileReader fr = null;
        try{
            fr = new FileReader(new File(filePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedReader reader = new BufferedReader(fr);
        String line = null;


        try{
            while ((line = reader.readLine()) != null){
                String[] split = line.split(" ");
                if(line.startsWith("v ")){
                    Vec3 v = new Vec3();
                    v.x = Float.parseFloat(split[1]);
                    v.y = Float.parseFloat(split[2]);
                    v.z = Float.parseFloat(split[3]);
                    va.add(v);
                }
                else if(line.startsWith("f ")){
                    Vec3 v = new Vec3();
                    v.x = Float.parseFloat(split[1].split("/")[0]);
                    v.y = Float.parseFloat(split[2].split("/")[0]);
                    v.z = Float.parseFloat(split[3].split("/")[0]);
                    fa.add(v);
                    i++;
                }
            } reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        }
}

