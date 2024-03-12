//package GameTest;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.util.List;
import java.util.ArrayList;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Vector;
public class DraggedShips {
    Point imageUpperLeft, prevPoint;
    ImageIcon imageHorazontal, imageVertical;
    int length;
    boolean isHorizontal;

    public DraggedShips(String path, Point start, int len)
    {
        try{
        File file = new File(path);
        BufferedImage image = ImageIO.read(file);
        imageVertical = new ImageIcon(image);
        imageUpperLeft = start;
        prevPoint = imageUpperLeft;

        //File file2 = new File(horizontalPath);
        //BufferedImage image2 = ImageIO.read(file2);
        //imageHorazontal = new ImageIcon(image2);
        isHorizontal = false;
        this.length = len;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

    }

    int getlength()
    {
        return length;
    }

    void changeOrientation()
    {
        isHorizontal = !isHorizontal;
    }

    Rectangle getBounds()
    {
        return new Rectangle((int)imageUpperLeft.getX(), (int)imageUpperLeft.getY(), getImage().getIconWidth(),getImage().getIconHeight());
    }
    ImageIcon getImage()
    {
        if(isHorizontal == true)
        {
            return imageHorazontal;
        }
        else
            return imageVertical;
    }
}

class MyPanel extends JPanel{
    Vector<DraggedShips> images;
    DraggedShips ship;

    MyPanel()
    {
        images.add(new DraggedShips("C:\\Users\\jorge\\Desktop\\COSC330\\new\\new2\\BattleShip\\canvas.png",new Point(100,100),4)); //battleship
        images.add(new DraggedShips("C:\\Users\\jorge\\Desktop\\COSC330\\new\\new2\\BattleShip\\canvas1.png", new Point(100,150),5));//carrier
        images.add(new DraggedShips("C:\\Users\\jorge\\Desktop\\COSC330\\new\\new2\\BattleShip\\canvas2.png", new Point(100,200), 3));//cruiser
        images.add(new DraggedShips("C:\\Users\\jorge\\Desktop\\COSC330\\new\\new2\\BattleShip\\canvas4.png", new Point(100,250),2));//destroyer
        images.add(new DraggedShips("C:\\Users\\jorge\\Desktop\\COSC330\\new\\new2\\BattleShip\\canvas5.png", new Point(100,300), 3));//submarine
        ClickListener clickListener = new ClickListener();
        this.addMouseListener(clickListener);
        
        DragListener dragListener = new DragListener();
        this.addMouseMotionListener(dragListener);


    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        for(DraggedShips ships:images)
        {
            ships.getImage().paintIcon(this, g, (int) ships.imageUpperLeft.getX(), (int) ships.imageUpperLeft.getY());
        }
    }

    private class ClickListener extends MouseAdapter
    {
        public void mousePressed(MouseEvent e)
        {
            for(DraggedShips ships : images)
            {
                if(ships.getBounds().contains(e.getPoint()))
                {
                    ship = ships;
                    ship.prevPoint = e.getPoint();
                    return;
                }
            }
        }
    }

    private class DragListener extends MouseMotionAdapter
    {
        public void mouseDragged(MouseEvent e)
        {
            if(ship!=null)
            {
                Point currentPoint = e.getPoint();
                int dx = (int) (currentPoint.getX() - ship.prevPoint.getX());
                int dy = (int) (currentPoint.getY() - ship.prevPoint.getY());
                ship.imageUpperLeft.translate(dx, dy);
                ship.prevPoint = currentPoint;
                repaint();
            }
        }
    }

    public List<Integer> getShipCoordinates()
    {
        List<Integer> coordinates = new ArrayList<>();
        for(int i = 0; i < images.size(); i++)
        {
            DraggedShips ship = images.get(i);
            Point location = ship.imageUpperLeft;
            coordinates.add((int)location.getY());
            coordinates.add((int)location.getX());
        }
        return coordinates;
    }
}