import java.util.ArrayList;

import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

public class Coordinates
{
  public double x;
  public double y;
  public String letter;



  public double getX()
  {
    return x;
  }

  public void setX(double x)
  {
    this.x = x;
  }

  public double getY()
  {
    return y;
  }

  public void setY(double y)
  {
    this.y = y;
  }



  public Coordinates(int xx,int yy,String let)
  {x=xx;
  y = yy;
  letter=let;

  }

  public String getLetter()
  {
    return letter;
  }

  public double calculateDistance(Coordinates other)
  {
    double temp1 = abs(x-other.getX());
    double temp2 = abs(y-other.getY());
    double temp3 = sqrt((temp1)*(temp1) + (temp2)*(temp2));
    double roundOff = (Math.round(temp3*10000.0)/10000.0);
  //  System.out.println("temp1 : "+ temp1 + "  temp2 : "+ temp2 + " temp3 : "+ temp3 + " round: "+ roundOff);
    return roundOff;
  }


}
