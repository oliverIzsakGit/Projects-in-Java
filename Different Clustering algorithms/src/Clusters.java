import java.util.ArrayList;

public class Clusters
{
  private ArrayList<Point> clusterPoints = new ArrayList<>();
  private Point center;

  private double centerX=0;
  private double centerY=0;
  //private Color color;

  public double getCenterX()
  {
    return centerX;
  }

  public void setCenterX(double centerX)
  {
    this.centerX = centerX;
  }

  public double getCenterY()
  {
    return centerY;
  }

  public void setCenterY(double centerY)
  {
    this.centerY = centerY;
  }

  public ArrayList<Double> getCenterSum( )
  {
    ArrayList<Double> results = new ArrayList<>();
    results.add(centerX/clusterPoints.size());
    results.add(centerY/clusterPoints.size());
   return results;
  }
  public void addCenterSum(double x,double y)
  {
    this.centerX += x;
    this.centerY+= y;
  }
  public Clusters(Point middle)
  {
    center = middle;



  }

  public ArrayList<Point> getClusters()
  {
    return clusterPoints;
  }
   public void addPointToCluster(Point t)
   {
     clusterPoints.add(t);
   }
  public void setClusters(ArrayList<Point> clusters)
  {
  clusterPoints = clusters;
  }

  public Point getCenter()
  {
    return center;
  }

  public void setCenter(Point center)
  {
    this.center = center;
  }




}
