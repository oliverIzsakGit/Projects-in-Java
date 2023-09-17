import java.awt.*;

public class Point
{
  private int x;
  private int y;
  private boolean com=false;

  public Point(int x, int y)
  {
    this.x = x;
    this.y = y;

  }

  public int getX()
  {
    return x;
  }

  public void setX(int x)
  {
    this.x = x;
  }

  public int getY()
  {
    return y;
  }

  public boolean isCom()
  {
    return com;
  }

  public void setCom(boolean com)
  {
    this.com = com;
  }

  public void setY(int y)
  {
    this.y = y;
  }



}
