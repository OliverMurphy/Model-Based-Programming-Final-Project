package ca.mcgill.ecse.restoApp.view;

import java.awt.geom.*;
import javax.swing.*;
import java.awt.*;

public class LayoutVisualizer extends JPanel
{
       /**
	 * this class is used to create the 2D rectangles
	 */
	private static final long serialVersionUID = 1L;
	Rectangle2D.Double rectangle;

       public LayoutVisualizer(int width, int length)
       {
           rectangle = new Rectangle2D.Double(0, 0, width, length);
           setOpaque(false);
       }

       public Dimension getPreferredSize()
       {
            Rectangle bounds = rectangle.getBounds();
           return new Dimension(bounds.width, bounds.height);
       }

       public void paintComponent(Graphics g)
       {
           super.paintComponent(g);
           Graphics2D g2 = (Graphics2D) g;
           g2.setColor(getForeground());
           g2.fill(rectangle);

       }
}
