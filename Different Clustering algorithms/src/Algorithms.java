import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

public class Algorithms
{

  public ArrayList<Clusters> K_Means_Centroid(ArrayList<Point> points, int k,boolean  isDivisive)
  {System.out.println("K - MEANS WITH CENTROID IN CENTER OF CLUSTERS");
    Random rn = new Random();
    ArrayList<Clusters> clusters = new ArrayList<>();
    ArrayList<Point> clusterPoints = new ArrayList<>();
    for (int i = 0; i < k; i++)
    {
      int p = rn.nextInt(points.size());

      if (!(clusterPoints.contains(points.get(p))))
      {
        int noRep = 0;                                       // this randomly places the K amount of starting cluster centers, but they cannot be closer to each other than 500.
        for (int j = 0; j < clusterPoints.size(); j++)
        {
          if (Math.abs(clusterPoints.get(j).getX() - points.get(p).getX()) <= 500)
          {
            if (Math.abs(clusterPoints.get(j).getY() - points.get(p).getY()) <= 500)
            {
              noRep = 1;
            }
          }
        }
        if (noRep == 1)
        {
          i--;
        }
        else
        {
          clusterPoints.add(points.get(p));
          clusters.add(new Clusters(new Point(points.get(p).getX(),points.get(p).getY())));
        }

      }
      else
      {

        i--;
      }
    }

if(isDivisive==false)
{
//  K_Means_CentroidFirstState(points, clusters);
}
 int ooo=0;int asd=0;
    while (ooo==0)
    {
      for (int m = 0; m < points.size(); m++)
      {
        double min = 1000000;
        int cp = 0;

        for (int j = 0; j < clusters.size(); j++)  //
        {
          if (calculateDistance(clusters.get(j).getCenter(), points.get(m)) < min)  // calculate the distance of each points so they can be added to the closest cluster to them.
          {
            min = calculateDistance(clusters.get(j).getCenter(), points.get(m));
            cp = j;
          }

        }
        clusters.get(cp).addPointToCluster(points.get(m));
        clusters.get(cp).addCenterSum(points.get(m).getX(),points.get(m).getY());


      }

      int same=0;
      for(int z=0; z<clusters.size();z++)
      { int xOld= clusters.get(z).getCenter().getX();
      int yOld= clusters.get(z).getCenter().getY();
        int x = (int) Math.round(clusters.get(z).getCenterSum().get(0));  // here we calculate the centroid and update it so it becomes the new center for the cluster
        int y =(int) Math.round(clusters.get(z).getCenterSum().get(1));
        clusters.get(z).setCenter(new Point(x,y));

        if(yOld==y && xOld==x){
        same++;
      }


      }
      if(same==k )
      {
        ooo=1;


      }else
      {
        for(int jj=0; jj<clusters.size();jj++)
        {
          clusters.get(jj).setCenterX(0);
          clusters.get(jj).setCenterY(0);
          clusters.get(jj).getClusters().clear();

        }
      }




    }

    return clusters;
  }


  public ArrayList<Clusters> K_Means_Medoid(ArrayList<Point> points, int k)
  {
    System.out.println("K - MEANS WITH MEDOID IN CENTER OF CLUSTERS");
    Random rn = new Random();
    ArrayList<Clusters> clusters = new ArrayList<>();
    ArrayList<Point> clusterPoints = new ArrayList<>();
    for (int i = 0; i < k; i++)
    {
      int p = rn.nextInt(points.size());

      if (!(clusterPoints.contains(points.get(p))))
      {

        int noRep = 0;

        for (int j = 0; j < clusterPoints.size(); j++)   // this randomly places the K amount of starting cluster centers, but they cannot be closer to each other than 500.
        {


          if (Math.abs(clusterPoints.get(j).getX() - points.get(p).getX()) <= 500 && Math.abs(clusterPoints.get(j).getY() - points.get(p).getY()) <= 500)
          {

              noRep = 1;


          }
        }
        if (noRep == 1)
        {
          i--;
        }
        else
        {
          clusterPoints.add(points.get(p));
          clusters.add(new Clusters(new Point(points.get(p).getX(),points.get(p).getY())));

        }

      }
      else
      {

        i--;
      }
    }



   // K_Means_CentroidFirstState(points,clusters);
    int ooo=0;
    ArrayList<Clusters> bestone= new ArrayList<>();
    ArrayList<ArrayList<Integer>> candidates= new ArrayList<>();
    ArrayList<Clusters> stuckInLoop=null;

    for(int i=0;i<clusters.size()*2;i++)
    {
      candidates.add(new ArrayList<>());
      candidates.get(i).add(0);
    }

    int loop=0;
    while (ooo==0)
    {
      for (int m = 0; m < points.size(); m++)
      {
        double min = 1000000;
        int cp = 0;

        for (int j = 0; j < clusters.size(); j++)                   // calculate the distance of each points so they can be added to the closest cluster to them.
        {
          if (calculateDistance(clusters.get(j).getCenter(), points.get(m)) < min)
          {
            min = calculateDistance(clusters.get(j).getCenter(), points.get(m));
            cp = j;
          }

        }
        clusters.get(cp).addPointToCluster(points.get(m));
        clusters.get(cp).addCenterSum(points.get(m).getX(),points.get(m).getY());


      }
      if(loop==0)
      {bestone=copy(clusters);}

      for(int z=0; z<clusters.size();z++)
      {
        int x = (int) Math.round(clusters.get(z).getCenterSum().get(0));
        int y =(int) Math.round(clusters.get(z).getCenterSum().get(1));
        int best=10000;
        int newCenter=-1;
        Collections.shuffle(clusters.get(z).getClusters());  // shuffle the array
        for(int g=0;g<clusters.get(z).getClusters().size();g++)
        {
          int bx= Math.abs(Math.abs(clusters.get(z).getClusters().get(g).getX())-Math.abs(x));
          int by=Math.abs(Math.abs(clusters.get(z).getClusters().get(g).getY())-Math.abs(y));

          if((bx+by)<best)
          {

            best= bx+by;
            newCenter=g;


          }if(bx+by ==best)
        {
         if(!candidates.get(z).contains(g)) { // if there is a candidate medoid then adds it to the candidate arraylist
           candidates.get(z).add(g);

         }
        }

        }


          clusters.get(z).setCenter(clusters.get(z).getClusters().get(newCenter));




      }

      if(calculateMedoid(stuckInLoop)==calculateMedoid(clusters))
    {
      for(int i =0; i<candidates.size()/2;i++)  // this is responsible for changing the best medoid to candidate best medoid. This only happens when there are other options for medoids.
      {

        try
        {
          clusters.get(i).setCenter(clusters.get(i).getClusters().get(
              candidates.get(i).get(candidates.get(i + clusters.size()).get(0))));
          int wtf = candidates.get(i + clusters.size()).get(0);
          candidates.get(i + clusters.size()).set(0, wtf + 1);
        }
        catch(IndexOutOfBoundsException e)
        {

        }
      }
    }
      stuckInLoop=copy(clusters);


      if(calculateMedoid(bestone)>calculateMedoid(clusters)) // compares the currently found medoid with the best one found so far. if the current one is better, than replaces the best one
      {

        bestone.clear();

       bestone= copy(clusters);

      }
      if(loop>1000)
      {
        ooo=1;

        clusters.clear();
        clusters.addAll(bestone);


      }else
      {
        for(int jj=0; jj<clusters.size();jj++)  // reset everthing.
        {
          clusters.get(jj).setCenterX(0);
          clusters.get(jj).setCenterY(0);
          clusters.get(jj).getClusters().clear();

        }
      }

loop++;


    }
    System.out.println("Loop: "+ loop );
    return clusters;
  }
  public ArrayList<Integer> getClosest(ArrayList<Clusters> clusters) { // gets the 2 closest points and returns them in an arraylist.

    double minD=66666;
    ArrayList<Integer> min=new ArrayList<>(2);
    min.add(0);min.add(0);
    for(int i=0;i<clusters.size();i++) {

      for(int j=0;j<clusters.size();j++) {
        if(i==j)continue;
        double dist=calculateDistance(clusters.get(i).getCenter(),clusters.get(j).getCenter());
        if(minD>dist){
          minD=dist;
          min.set(0,i);
          min.set(1,j);
        }

      }

    }
    return min;
  }

  public ArrayList<Clusters> clusters2 = new ArrayList<>();
  public ArrayList<Clusters> Agglomerative(ArrayList<Point> points)
  {
    ArrayList<Clusters> result = new ArrayList<>();

      for(int i=0; i<points.size();i++)           // With this loop I make all individual points into individual clusters.
                                                  // and add them to the clusters2 global variable
      {Point p = new Point(points.get(i).getX(),points.get(i).getY());
      Clusters nc = new Clusters(p);
      nc.getClusters().add(p);
        clusters2.add(nc);
      }
    ArrayList<Integer> shortest= new ArrayList<>();//save the index of the 2 closest points
    int x;
    int y;
    while(true) {
      if(clusters2.size()==1 ) {//if there is one left then add it to the results and end.
        result.add(clusters2.get(0));
        break;
      }
      shortest=getClosest(clusters2);//gets the two points  that are the closest to each other
      x=(clusters2.get(shortest.get(0)).getCenter().getX()+clusters2.get(shortest.get(1)).getCenter().getX())/2; // calculate the  new center for the merged clusters.
      y=(clusters2.get(shortest.get(0)).getCenter().getY()+clusters2.get(shortest.get(1)).getCenter().getY())/2;
      Point p =new Point(x, y);
      Clusters c = new Clusters(p);  // create a cluster with the new updated center and add the other points to its Arraylist of points.
      c.getClusters().addAll(0,clusters2.get(shortest.get(0)).getClusters());// add together the points for the merged clusters
      c.getClusters().addAll(c.getClusters().size(),clusters2.get(shortest.get(1)).getClusters());
       double distS =0;
       int count=0;
      for(int i=0;i<c.getClusters().size();i++) { // here I just calculate the avg distance.
        double distance = calculateDistance(c.getCenter(),new Point(c.getClusters().get(i).getX(),c.getClusters().get(i).getY()));
        distS+=distance;
        count++;
      }
      double avg =distS / count;
      if(avg>500) { // if the combined clusteres are above 500

        if(clusters2.size()==2) { //if only 2 clusters remain and they are above 500 then we just let them stay like that, since its not worth merging if they end up being bigger than 500

            clusters2.remove(0);
            clusters2.remove(0);

          break;
        }
        else if(clusters2.get(shortest.get(0)).getClusters().size()>clusters2.get(shortest.get(1)).getClusters().size()) { // if there are more than 2, then  we only add the one with the lowest amount of pints
          Clusters cls = clusters2.get((int)shortest.get(0));
          clusters2.remove((int)shortest.get(0));
          result.add(cls);
        }
        else {
          result.add(clusters2.remove((int)shortest.get(1)));
        }
      }
      else{   // if avg distance is below 500 then we just remove them from global list,and add the merged cluster back to it.
        if (shortest.get(0)<shortest.get(1)) {
          clusters2.remove((int)shortest.get(0));
          clusters2.remove(((int)shortest.get(1))-1);
        }
        else {
          clusters2.remove((int)shortest.get(1));
          clusters2.remove(((int)shortest.get(0))-1);
        }
        clusters2.add(c);
      }
    }
    return result;

  }
  public ArrayList<Clusters> clusterss = new ArrayList<>();

  public int div(Clusters c,ArrayList<Point> points){


    if (points.size()== 0)  // if size is 0 ,then top
    {return 0;}


   double overall=0;
    double avg =0;

    for(int j =0; j<c.getClusters().size();j++)  // calculates the avg distance from the middle for all points in that cluster
    {
      overall+= calculateDistance(c.getClusters().get(j),c.getCenter());
    }

    avg=overall/c.getClusters().size();

    if(avg<= 500) { //if avg distance form middle is ssmaller or equal to 500 then it is added to the final result.

      clusterss.add(c);
      return 0;

    }
    else {
      ArrayList<Clusters> newC = K_Means_Centroid(points,2,true);  // if not, then it calls the k means centroid method with k=2, to divide the cluster into 2 parts.
      div(newC.get(0),newC.get(0).getClusters());                              // then on the 2 clusters the div method is called again, and this goes on recursively until every cluster is below 500 avg distnace.
      div(newC.get(1),newC.get(1).getClusters());
    }

    return 0;


  }
  public ArrayList<Clusters> Divisive(ArrayList<Point>points)
  {
    System.out.println("DIVISIVE CLUSTERING");

    ArrayList<Clusters> clusters = new ArrayList<>();
    Clusters cl= new Clusters(new Point(1,1));  // adds together all the points into one big cluster
    for (int m = 0; m < points.size(); m++)
    {

      cl.addPointToCluster(points.get(m));
      cl.addCenterSum(points.get(m).getX(),points.get(m).getY());


    }
    cl.setCenter(new Point((int) Math.round(cl.getCenterSum().get(0)),(int) Math.round(cl.getCenterSum().get(1))));  // calculates and assigns the new center.
    div(cl, points);

    return clusterss;





  }

  public double calculateDistance(Point p1,Point p2)  // calculated eucledian distance
  {

    double temp1 = abs(p1.getX()-p2.getX());
    double temp2 = abs(p1.getY()-p2.getY());
    double temp3 = sqrt((temp1)*(temp1) + (temp2)*(temp2));

    return (Math.round(temp3*10000.0)/10000.0);
  }




  public ArrayList<Clusters> K_Means_CentroidFirstState(ArrayList<Point> p, ArrayList<Clusters>c) // this is just reposonsible for typing out and showing the info for the first run for K-means algorithms. When they havent been updated with new centroid/medoid yet.
  {
ArrayList<Point> points = new ArrayList<>();
ArrayList<Clusters> clusters= new ArrayList<>();

for(int i=0; i<p.size();i++)
{
  points.add(new Point(p.get(i).getX(),p.get(i).getY()));
}
for(int i=0; i<c.size();i++)
{
  clusters.add(new Clusters(c.get(i).getCenter()));
  for(int j =0; j<c.get(i).getClusters().size();j++)
  {
  clusters.get(i).getClusters().add( new Point(c.get(i).getClusters().get(j).getX(),c.get(i).getClusters().get(j).getY()));
  }
  }





      for (int m = 0; m < points.size(); m++)
      {
        double min = 1000000;
        int cp = 0;

        for (int j = 0; j < clusters.size(); j++)
        {
          if (calculateDistance(clusters.get(j).getCenter(), points.get(m)) < min)
          {
            min = calculateDistance(clusters.get(j).getCenter(), points.get(m));
            cp = j;
          }

        }
        clusters.get(cp).addPointToCluster(points.get(m));
        clusters.get(cp).addCenterSum(points.get(m).getX(),points.get(m).getY());


      }
double overall=0;
    for(int i=0;i<clusters.size();i++)
    { double avg =0;
      System.out.println("Coordinates: " +clusters.get(i).getCenter().getX() + ","+ clusters.get(i).getCenter().getY()+ " - "+colors(i));
      for(int j =0; j<clusters.get(i).getClusters().size();j++)
      {
        avg+= calculateDistance(clusters.get(i).getClusters().get(j),clusters.get(i).getCenter());
      }
      System.out.println("Average distance of points from the center: "+ avg/clusters.get(i).getClusters().size());
      System.out.println("Amount of points in cluster: "+ clusters.get(i).getClusters().size());
      overall+=avg/clusters.get(i).getClusters().size();
    }
    System.out.println("Overall average distance: "+ overall/clusters.size());


        new Thread(() -> {

          Generator gg = new Generator(points,clusters);
          gg.setIgnoreRepaint(true);
        }).start();






    return clusters;
  }
  public double calculateMedoid(ArrayList<Clusters>cls)  // this calculates the cluster average distance
  { double overall=0;
  if(cls!=null)
  {
    for (int i = 0; i < cls.size(); i++)
    {
      double avg = 0;

      for (int j = 0; j < cls.get(i).getClusters().size(); j++)
      {
        avg += calculateDistance(cls.get(i).getClusters().get(j), cls.get(i).getCenter());
      }

      overall += avg / cls.get(i).getClusters().size();
    }

  //  System.out.println("Overall: " + overall / cls.size());
  }else {return -1;}
    return overall/cls.size();
  }

  public String colors(int m)
  { ArrayList<String> c = new ArrayList<>();
    c.add("Red");
    c.add("Blue");
    c.add("Cyan");
    c.add("Green");
    c.add("Magenta");
    c.add("Orange");
    c.add("Yellow");
    c.add("Pink");
    c.add("Gray");
   c.add("Brown");
   c.add("Ligh Pink");
   c.add("Redwood");
   c.add("Dark Purple");
   c.add("Beige");
   c.add("Light green");
   c.add("Dark green");
   c.add("Light Purple");
   c.add("Light Yellow");
   c.add("Dark Pink");
   c.add("Weird green");
   c.add("Dark Blue");

   return c.get(m);
  }
  public ArrayList<Clusters> copy(ArrayList<Clusters> cls)  // deep copy for an arraylist of clusters.
  {


    ArrayList<Clusters> clusters= new ArrayList<>();

    for(int i=0; i<cls.size();i++)
    {

      clusters.add(new Clusters(new Point(cls.get(i).getCenter().getX(),cls.get(i).getCenter().getY())));

      for(int j=0; j<cls.get(i).getClusters().size();j++)
      {

        clusters.get(i).addPointToCluster( new Point(cls.get(i).getClusters().get(j).getX(),cls.get(i).getClusters().get(j).getY()));
      }


    }

return clusters;
  }
}
