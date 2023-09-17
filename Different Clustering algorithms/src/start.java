import java.util.ArrayList;
import java.util.Random;

import java.util.Scanner;

public class start
{
  public static int nStarterPoints = 20;
  public static int generatedPoints= 1000;
  public static void main(String[] args)
  {  Scanner sc = new Scanner(System.in);
    Random rnd = new Random();
    ArrayList<Point> points = new ArrayList<>();
    ArrayList<Clusters> clts = new ArrayList<>();
    System.out.println("Enter the number of points generated");
    int choice3=sc.nextInt();
    generatedPoints = generatedPoints+choice3;

    for (int i = 0; i <nStarterPoints; i++)
    { int x=0;
      int y=0;
      int check= 0;
      while(check==0)
      { int noRep=0;
        x= random().get(0);
        y = random().get(1);
        for (int j = 0; j < points.size(); j++)
        { if(points.get(j).getX()== x && points.get(j).getY()==y)
        {
          break;
        }
          if (Math.abs(points.get(j).getX()-x) <= 400)
          {
            if (Math.abs(points.get(j).getY()-y) <= 400)
            {
              noRep = 1;
            }
          }
        }
        if(noRep==0)
        {
          check=1;
        }
      }
     points.add(new Point(x,y));






    }



    for (int k = 0; k <generatedPoints; k++)
    {
      int xx=0;
      int yy=0;
      int check2= 0;
      while(check2==0)
      { int noRep2=0;
        int rndnmb1 = rnd.nextInt(100)+1;
        int rndnmb2 = rnd.nextInt(100)+1;
        int nop = rnd.nextInt(2);
        int nop2 = rnd.nextInt(2);
        if(nop==1)
        {
          rndnmb1=-(rndnmb1);
        }
        if(nop2==1)
        {
          rndnmb2=-(rndnmb2);
        }
        int rand = rnd.nextInt(points.size());

        xx = points.get(rand).getX()+rndnmb1;
        yy = points.get(rand).getY()+ rndnmb2;
        for (int j = 0; j < points.size(); j++)
        {
if(xx<-5000 || xx>5000 || yy<-5000 || yy>5000)
{
  noRep2=1;
}
          if (points.get(j).getX() == xx && points.get(j).getY() == yy)
          {

              noRep2 = 1;
          }
        }
        if(noRep2==0)
        {
          check2=1;
        }
      }

      points.add(new Point(xx,yy));

    }
for(int i =0; i<points.size();i++)
{
 if( points.get(i).getY()>5000|| points.get(i).getY()<-5000 ||  points.get(i).getX()>5000 ||  points.get(i).getX() <-5000 )
 {
   System.out.println("ERROR");
   System.exit(1);
 }
}


    System.out.println("Press the corresponding number :  \n1 - K-means centroid\n2 - K-means medoid\n3 - Agglomerative\n4 - Divisive\n5 - all of them");
    int choice = sc.nextInt();
    int choice2=0;

    if(choice==1 ||choice==2 ||choice==5)
    {
      System.out.println("Enter the value of the K variable");
      choice2= sc.nextInt();
    }

    ArrayList<ArrayList<Double>>info= new ArrayList<>();
   info.add(new ArrayList<>());
    info.add(new ArrayList<>());
    info.add(new ArrayList<>());
    info.add(new ArrayList<>());


    Algorithms a = new Algorithms();
    final long startTime = System.currentTimeMillis();
    if(choice==1 || choice==5)
    {
      clts= a.K_Means_Centroid(points,choice2,false);
      double overall=0;
      System.out.println("---------------------- FINAL SOLUTION ----------------------------------");
      for(int i=0;i<clts.size();i++)
      { double avg =0;
        System.out.println("Coordinates: " +clts.get(i).getCenter().getX() + ","+ clts.get(i).getCenter().getY()+ " - "+a.colors(i));
        for(int j =0; j<clts.get(i).getClusters().size();j++)
        {
          avg+= a.calculateDistance(clts.get(i).getClusters().get(j),clts.get(i).getCenter());


        }
        System.out.println("Average distance of points from the center: "+ avg/clts.get(i).getClusters().size());
        if( avg/clts.get(i).getClusters().size()>500)
        {

          info.get(0).add(0.0);

        }
        System.out.println("Amount of points in cluster: "+ clts.get(i).getClusters().size());
        overall+=avg/clts.get(i).getClusters().size();

      }
      System.out.println("Overall Cluster average: "+overall/clts.size());
      info.get(0).add(overall/clts.size());
      final long endTime = System.currentTimeMillis();

      double time = ((double) (endTime - startTime)) / 1000;
      System.out.println("The execution time was: "+ time + " seconds for " +generatedPoints + " points");
      info.get(0).add(time);
      Generator f = new Generator(points,clts);
    }
    if(choice==2 || choice==5)
    {
      clts= a.K_Means_Medoid(points,choice2);
      double overall=0;
      System.out.println("---------------------- FINAL SOLUTION ----------------------------------");
      for(int i=0;i<clts.size();i++)
      { double avg =0;
        System.out.println("Coordinates: " +clts.get(i).getCenter().getX() + ","+ clts.get(i).getCenter().getY()+ " - "+a.colors(i));
        for(int j =0; j<clts.get(i).getClusters().size();j++)
        {
          avg+= a.calculateDistance(clts.get(i).getClusters().get(j),clts.get(i).getCenter());

        }
        System.out.println("Average distance of points from the center: "+ avg/clts.get(i).getClusters().size());
        if( avg/clts.get(i).getClusters().size()>500)
        {

          info.get(1).add(0.0);

        }
        System.out.println("Amount of points in cluster: "+ clts.get(i).getClusters().size());
        overall+=avg/clts.get(i).getClusters().size();
      }  System.out.println("Overall Cluster average: "+overall/clts.size());
      info.get(1).add(overall/clts.size());
      final long endTime = System.currentTimeMillis();

      double time = ((double) (endTime - startTime)) / 1000;
      System.out.println("The execution time was: "+ time + " seconds for " +generatedPoints + " points");
      info.get(1).add(time);
      Generator f = new Generator(points,clts);
    }
    if(choice==3 || choice==5)
    {
      clts= a.Agglomerative(points);

      double overall=0;
      System.out.println("---------------------- FINAL SOLUTION ----------------------------------");
      for(int i=0;i<clts.size();i++)
      { double avg =0;
        System.out.println("Coordinates: " +clts.get(i).getCenter().getX() + ","+ clts.get(i).getCenter().getY()+ " - "+a.colors(i));
        for(int j =0; j<clts.get(i).getClusters().size();j++)
        {
          avg+= a.calculateDistance(clts.get(i).getClusters().get(j),clts.get(i).getCenter());

        }
        System.out.println("Average distance of points from the center: "+ avg/clts.get(i).getClusters().size());
        if( avg/clts.get(i).getClusters().size()>500)
        {

          info.get(2).add(0.0);

        }
        System.out.println("Amount of points in cluster: "+ clts.get(i).getClusters().size());
        overall+=avg/clts.get(i).getClusters().size();
      }   info.get(2).add((double) clts.size());
      System.out.println("Overall Cluster average: "+overall/clts.size());
      info.get(2).add(overall/clts.size());

      final long endTime = System.currentTimeMillis();

      double time = ((double) (endTime - startTime)) / 1000;
      System.out.println("The execution time was: "+ time + " seconds for " +generatedPoints + " points");
      info.get(2).add(time);
      Generator f = new Generator(points,clts);


    }
     if(choice==4 || choice==5)
    {
      clts= a.Divisive(points);
      double overall=0;
      System.out.println("---------------------- FINAL SOLUTION ----------------------------------");
      for(int i=0;i<clts.size();i++)
      { double avg =0;
        System.out.println("Coordinates: " +clts.get(i).getCenter().getX() + ","+ clts.get(i).getCenter().getY()+ " - "+a.colors(i));
        for(int j =0; j<clts.get(i).getClusters().size();j++)
        {
          avg+= a.calculateDistance(clts.get(i).getClusters().get(j),clts.get(i).getCenter());

        }
        System.out.println("Average distance of points from the center: "+ avg/clts.get(i).getClusters().size());
        if( avg/clts.get(i).getClusters().size()>500)
        {

          System.out.println("THIS NEVER HAPPENS");
          info.get(3).add(0.0);

        }
        System.out.println("Amount of points in cluster: "+ clts.get(i).getClusters().size());
        overall+=avg/clts.get(i).getClusters().size();
      }
      info.get(3).add((double) clts.size());
      System.out.println("Overall Cluster average: "+overall/clts.size());
      info.get(3).add(overall/clts.size());

      final long endTime = System.currentTimeMillis();

      double time = ((double) (endTime - startTime)) / 1000;
      System.out.println("The execution time was: "+ time + " seconds for " +generatedPoints + " points");
      info.get(3).add(time);
      Generator f = new Generator(points,clts);
    }
     if(choice==6)
     {
       for(int kk=1;kk<21;kk++)
       {
         clts= a.K_Means_Centroid(points,kk,false);
         double overall=0;
         System.out.println("---------------------- FINAL SOLUTION ----------------------------------");
         for(int i=0;i<clts.size();i++)
         { double avg =0;

           for(int j =0; j<clts.get(i).getClusters().size();j++)
           {
             avg+= a.calculateDistance(clts.get(i).getClusters().get(j),clts.get(i).getCenter());


           }
           System.out.println("Average distance of points from the center: "+ avg/clts.get(i).getClusters().size());
           if( avg/clts.get(i).getClusters().size()>500)
           {

             info.get(0).add(0.0);

           }
           System.out.println("Amount of points in cluster: "+ clts.get(i).getClusters().size());
           overall+=avg/clts.get(i).getClusters().size();

         }
         System.out.println("Overall Cluster average: "+overall/clts.size());
         info.get(0).add(overall/clts.size());
         final long endTime = System.currentTimeMillis();

         double time = ((double) (endTime - startTime)) / 1000;
         System.out.println("The execution time was: "+ time + " seconds for " +generatedPoints + " points");
         info.get(0).add(time);
         Generator f = new Generator(points,clts);

         clts= a.K_Means_Medoid(points,kk);
         double overall2=0;
         System.out.println("---------------------- FINAL SOLUTION ----------------------------------");
         for(int i=0;i<clts.size();i++)
         { double avg =0;
           System.out.println("Coordinates: " +clts.get(i).getCenter().getX() + ","+ clts.get(i).getCenter().getY()+ " - "+a.colors(i));
           for(int j =0; j<clts.get(i).getClusters().size();j++)
           {
             avg+= a.calculateDistance(clts.get(i).getClusters().get(j),clts.get(i).getCenter());

           }
           System.out.println("Average distance of points from the center: "+ avg/clts.get(i).getClusters().size());
           if( avg/clts.get(i).getClusters().size()>500)
           {

             info.get(1).add(0.0);

           }
           System.out.println("Amount of points in cluster: "+ clts.get(i).getClusters().size());
           overall2+=avg/clts.get(i).getClusters().size();
         }  System.out.println("Overall Cluster average: "+overall2/clts.size());
         info.get(1).add(overall2/clts.size());
         final long endTime1 = System.currentTimeMillis();

         double time1 = ((double) (endTime1 - startTime)) / 1000;
         System.out.println("The execution time was: "+ time1 + " seconds for " +generatedPoints + " points");
         info.get(1).add(time1);
         Generator f1 = new Generator(points,clts);

         int on =info.get(0).size()-3;
         int tw = info.get(1).size()-3;
         if(on<0){on=0;}
         if(tw<0){tw=0;}
         System.out.println("Evaluation:\nNumber of points:"+generatedPoints+"\nK for K-means algorithms: "+kk+"\nK-means Centroid:\n   -Amount of clusters where avg distance was over 500: "+ on+"\n   -Overall cluster average: "+info.get(0).get(info.get(0).size()-2));
         System.out.println("K-means Medoid:\n   -Amount of clusters where avg distance was over 500: "+ tw+"\n   -Overall cluster average: "+info.get(1).get(info.get(1).size()-2));
       info.get(1).clear();
       info.get(0).clear();
       }
     }
if(choice==5){
  int on =info.get(0).size()-3;
  int tw = info.get(1).size()-3;
  int th  =info.get(2).size()-3;
  int fo= info.get(3).size()-3;

  System.out.println("Evaluation:\nNumber of points:"+generatedPoints+"\nK for K-means algorithms: "+choice2+"\nK-means Centroid:\n   -Amount of clusters where avg distance was over 500: "+ on+"\n   -Overall cluster average: "+info.get(0).get(info.get(0).size()-2)+"\n   -Execution time: "+info.get(0).get(info.get(0).size()-1));
  System.out.println("K-means Medoid:\n   -Amount of clusters where avg distance was over 500: "+ tw+"\n   -Overall cluster average: "+info.get(1).get(info.get(1).size()-2)+"\n   -Execution time: "+info.get(1).get(info.get(1).size()-1) );
  System.out.println("Agglomerative:\n   -Amount of clusters where avg distance was over 500: "+ th+"\n   -Overall cluster average: "+info.get(2).get(info.get(2).size()-2) +"\n   -Execution time: "+info.get(2).get(info.get(2).size()-1)+ "\n   -Amount of clusters: "+ info.get(2).get(info.get(2).size()-3));
  System.out.println("Divisive:\n   -Amount of clusters where avg distance was over 500: "+ fo+"\n   -Overall cluster average: "+info.get(3).get(info.get(3).size()-2) +"\n   -Execution time: "+info.get(3).get(info.get(3).size()-1)+ "\n   -Amount of clusters: "+ info.get(3).get(info.get(3).size()-3));
}



  }
  public static ArrayList<Integer> random()
  {
    Random rnd = new Random();

    int x = rnd.nextInt(10000)-5000;
    int y= rnd.nextInt(10000)-5000;

    ArrayList<Integer> rs= new ArrayList<>();
    rs.add(x);
    rs.add(y);
    return rs;

  }


}
