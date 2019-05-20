package ui;

import utils.Globals;
import utils.RgbColor;
import utils.algebra.Vec2;
import utils.io.DataExporter;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class Window {

    private int mWidth;
    private int mHeight;

    private BufferedImage mBufferedImage;
    private BufferedImage mEdgeBufferedImage;

    private JFrame mFrame;
    private String mOutputTitle;

    /**
     Create render window with the given dimensions
     **/
    public Window(int width, int height, String outputTitle){
        mWidth = width;
        mHeight = height;
        mOutputTitle = outputTitle;

        // we are using only one frame
        mBufferedImage = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);
        mEdgeBufferedImage = new BufferedImage(mWidth, mHeight, BufferedImage.TYPE_INT_RGB);

        createFrame();
    }

    public BufferedImage getBufferedImage(){
        return mBufferedImage;
    }
    public BufferedImage getEdgeBufferedImage(){
        return mEdgeBufferedImage;
    }

    /**
     Setup render frame with given parameters
     **/
    private void createFrame(){
        JFrame frame = new JFrame();

        frame.getContentPane().add(new JLabel(new ImageIcon(mBufferedImage)));
        frame.setSize(mBufferedImage.getHeight() + frame.getSize().height, mBufferedImage.getWidth() + frame.getSize().width);
        frame.pack();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);

        mFrame = frame;
    }

    /**
     Draw debug information
     **/
    private void setOutputLabel(BufferedImage renderImage, String text, int recursions, int frame){
        Graphics graphic = renderImage.getGraphics();
        graphic.setColor(Color.black);
        graphic.fill3DRect(0,mHeight - 30,mWidth,mHeight,true);
        graphic.setColor(Color.green);
        graphic.drawString("Elapsed rendering time: " + text + " sec, Recursions: " + recursions + ", Frame: " + frame + "/" + Globals.frames, 10, mHeight - 10);

        mFrame.repaint();
    }

    /**
     Draw pixel to our render frame
     **/
    public void setPixel(BufferedImage bufferedImage, RgbColor color, Vec2 screenPosition){
        bufferedImage.setRGB((int)screenPosition.x, (int)screenPosition.y, color.getRGB());
        mFrame.repaint();
    }

    /**
     Export the rendering to an PNG image with rendering information
     **/
    public void exportRenderingToFile(BufferedImage renderImage, String text, int recursions, int frame){
        this.setOutputLabel(renderImage, text, recursions, frame);
        //long timeInMillis = System.currentTimeMillis();
        //SimpleDateFormat sdf = new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
        //Date resultdate = new Date(timeInMillis);

        DataExporter.exportImageToPng(renderImage, mOutputTitle + "_" + frame + ".png");
    }


}
