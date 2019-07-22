/**
 * Create a display of controller inputs from a given TAS file
 * Qflame
 */
import java.io.*; 
import java.util.Scanner;
import java.util.ArrayList;
import java.awt.image.*;
import javax.imageio.ImageIO;

class TASVis
{
    static int frame = 0;
    static boolean debug = false;
    static String[] buttons = {"KEY_A", "KEY_B", "KEY_X", "KEY_Y", "KEY_L", "KEY_R", "KEY_ZL", "KEY_ZR", "KEY_PLUS", "KEY_MINUS", "KEY_DLEFT", "KEY_DUP", "KEY_DRIGHT", "KEY_DDOWN"};
    static BufferedImage[] pressedIndicator = new BufferedImage[14];
    public static void main(String[] args)
    {
        try
        {
            System.out.println("Loading resources...");
            for(int a=0;a<14;a++)
            {
                Frame q = new Frame(ImageIO.read(new File("resources/KEY_A.png")));
                if(debug)System.out.println("resources/" + buttons[a] + ".png");
                pressedIndicator[a] = ImageIO.read(new File("resources/" + buttons[a] + ".png"));
            }
            System.out.println("Done\nReading TAS file");
            Scanner sc = new Scanner(new File("script.txt")); 
            while (sc.hasNextLine())
            {
                parseInstruction(sc.nextLine().split(" "));
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
            System.out.println("Error reading TAS input file or required resource file");
        }
    }
    static void parseInstruction(String[] inputs)
    {
        Frame blank = null;
        try
        {
            blank = new Frame(ImageIO.read(new File("resources/blank.png")));
        }
        catch(Exception er)
        {
            System.out.println(er + "\nFailed to load resource blank.png");
        }
        //In case of any frames with no input, add a blank procon with no buttons
        int nextFrame = Integer.parseInt(inputs[0]);
        while(frame < nextFrame)
        {
            System.out.println("Adding blank for frame " + frame);

            blank.dump(frame);
            frame++;
        }
        try
        {
            //Load the basic controller
            System.out.println("Processing inputs for frame " + frame);
            Frame builtFrame = new Frame(ImageIO.read(new File("resources/proconbase.png")));
            
            BufferedImage lStick = ImageIO.read(new File("resources/leftstick.png"));
            BufferedImage rStick = ImageIO.read(new File("resources/rightstick.png"));
                    
            //Parse inputs
            String[] pressed = inputs[1].split(";");
            String[] lStickPos = inputs[2].split(";");
            String[] rStickPos = inputs[3].split(";");
            
            //Place the joysticks
            int lmagX = Integer.parseInt(lStickPos[0]) / 1000;
            int lmagY = Integer.parseInt(lStickPos[1]) / 1000;
            int rmagX = Integer.parseInt(rStickPos[0]) / 1000;
            int rmagY = Integer.parseInt(rStickPos[1]) / 1000;
            builtFrame.stack(lStick, lmagX, lmagY);
            builtFrame.stack(rStick, rmagX, rmagY);
                    
            //Press any buttons
            for(int b=0;b<pressed.length;b++)
            {
                if(debug)System.out.println(pressed[b]);
                for(int c=0;c<14;c++)
                {
                    if(pressed[b].equals(buttons[c]))
                    {
                        builtFrame.stack(pressedIndicator[c], 0, 0);
                    }
                }
            }
            
            //Save the frame
            builtFrame.dump(frame);
            frame++;
        }
        catch(Exception g)
        {
            System.out.println(g);
            System.out.println("Error loading resources.");
        }
    }
}
