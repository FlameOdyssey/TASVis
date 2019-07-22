/**
 * Construct a frame and save it
 * Qflame
 */
import java.awt.image.*;
import java.io.*; 
import javax.imageio.ImageIO;

public class Frame
{
    BufferedImage procon;
    public Frame(BufferedImage i)
    {
        procon = i;
    }
    void stack(BufferedImage b, int offX, int offY)
    {
        for(int x=0;x<b.getWidth();x++)
        {
            for(int y=0;y<b.getHeight();y++)
            {
                if(b.getRGB(x, y) != 0)
                {
                    procon.setRGB(x + offX, y - offY, b.getRGB(x,y));
                }
            }
        }
    }
    void dump(int id)
    {
        try
        {
            File out = new File("frames/" + id + ".png");
            ImageIO.write(procon, "png", out);
        }
        catch(Exception f)
        {
            System.out.println("Error saving frame.");
        }
    }
}
