import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Generator extends JFrame {
  public Generator(ArrayList<Point> points,ArrayList<Clusters> clusters) {
    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        dispose();
      }
    });



    Frame f= new Frame("CanvaS");
    f.add(new Generator2(points,clusters));
    f.setLayout(null);
    f.setSize(1100, 1100);
    f.setVisible(true);
  }
}
class Generator2 extends Canvas
{

  ArrayList<Point> pts;
  ArrayList<Clusters> clstr;
  Color c[];
  public Generator2(ArrayList<Point> points,ArrayList<Clusters> clusters) {
    pts=points;
    clstr=clusters;
    c = new Color[30];
    c[0]=Color.RED;
    c[1]=Color.blue;
    c[2]= Color.CYAN;
    c[3]=Color.GREEN;
    c[4]=Color.MAGENTA;
    c[5]=Color.ORANGE;
    c[6]=Color.YELLOW;
    c[7]=Color.PINK;
    c[8]=Color.GRAY;
    c[9]=new Color(125,115,66);
    c[10]=new Color(240,163,255);
    c[11]= new Color(153,63,0);
    c[12]=new Color(76,0,92);
    c[13]= new Color(255,204,153);
    c[14]=new Color(148,255,181);
    c[15]=new Color(0,153,143);
    c[16]=new Color(116,10,255);
    c[17]=new Color(255,255,128);
    c[18]=new Color(194,0,136);
    c[19]=new Color(157,204,0);
    c[20]=new Color(2,63,165);
    c[21]=new Color(214,188,192);
    c[22]=new Color(187,119,132);
    c[23]=new Color(74,111,227);
    c[24]=new Color(190, 153, 112);
    c[25]=new Color(144,251 ,146);
    c[26]=new Color(0,118,255);
    c[27]=new Color(232,94 ,190);
    c[28]=new Color(77,14,31);
    c[29]=new Color(10,245,255);



    setBackground (Color.BLACK);
    setSize(1100, 1100);
  }
  public void paint(Graphics graphics)
  {



      for (int j = 0; j < clstr.size(); j++)
      {

        for (int h=0;h<clstr.get(j).getClusters().size();h++ )
        {
          int w = 1;
          int he = 1;
          graphics.setColor(c[j]);

          graphics.fillOval((clstr.get(j).getClusters().get(h).getX()+5000)/10+50, (-clstr.get(j).getClusters().get(h).getY()+5000)/10+50, w, he);

        }
        if (clstr.get(j).getCenter() != null)
        {
          int h2 = 7;
          int w2 = 7;
          graphics.setColor(Color.white);
          graphics.fillOval((clstr.get(j).getCenter().getX()+5000)/10+50,
              (-clstr.get(j).getCenter().getY()+5000)/10+50, w2, h2);
        }


      }
      graphics.setColor(Color.WHITE);
      graphics.drawLine(50, 550, 1050, 550);
      graphics.drawLine(550, 50, 550, 1050);
      graphics.setColor(Color.BLUE);
      graphics.drawLine(45, 0, 45, 1100);
      graphics.drawLine(1055, 0, 1055, 1100);
      graphics.drawLine(0, 45, 1100, 45);
    graphics.drawLine(0, 1055, 1100, 1055);
    }
  }




