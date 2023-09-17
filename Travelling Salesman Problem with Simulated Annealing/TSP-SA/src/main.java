import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class main
{
  public static void main(String[] args)
  {
    double average = 0;
    double averageTime = 0;
    double mi = 1001000;
    ArrayList newcs = null;
    ArrayList newcs2=null;
    Scanner sc=new Scanner(System.in);
    System.out.println("Chose an option for testing:");
    System.out.println("1. A specific test with 30 coordinates already determined and the same every time.");
    System.out.println("2. A specific test with 20 coordinates already determined and the same every time.");
    System.out.println("3. A randomized permutation of cities with randomized coordinates, here you can add the number of cities.");
    int choice= sc.nextInt();
    System.out.println("Type in the number of repetitions: ");
    int repetition= sc.nextInt();
    System.out.println("Type in the number for one of the following cooling factor (the larger the number the faster the algorithm runs.):\n0,01\n0,001\n0,0001\n0,00001\n0,000001");
    double cf = sc.nextDouble();
    int cno=0;
    if(choice==3)
    {
      System.out.println("Type in the number of cities:");
      cno=sc.nextInt();
    }

    for (int ho = 0; ho < repetition; ho++)
    {ArrayList<Coordinates> cs = new ArrayList<>();
      ArrayList<Coordinates> cs2 = new ArrayList<>();

      if(choice==1)
    {
      Coordinates a = new Coordinates(3, 2, "a");
      Coordinates b = new Coordinates(4, 7, "b");

      Coordinates c = new Coordinates(6, 3, "c");
      Coordinates d = new Coordinates(8, 5, "d");
      Coordinates e = new Coordinates(12, 3, "e");
      Coordinates f = new Coordinates(17, 13, "f");
      Coordinates g = new Coordinates(22, 4, "g");
      Coordinates h = new Coordinates(6, 19, "h");
      Coordinates i = new Coordinates(9, 7, "i");
      Coordinates j = new Coordinates(31, 21, "j");
      Coordinates k = new Coordinates(19, 9, "k");
      Coordinates ll = new Coordinates(24, 14, "l");
      Coordinates m = new Coordinates(18, 27, "m");
      Coordinates n = new Coordinates(47, 33, "n");
      Coordinates o = new Coordinates(40, 50, "o");
      Coordinates p = new Coordinates(49, 26, "p");
      Coordinates q = new Coordinates(21, 43, "q");
      Coordinates r = new Coordinates(16, 4, "r");
      Coordinates s = new Coordinates(17, 11, "s");
      Coordinates t = new Coordinates(35, 28, "t");
      Coordinates u = new Coordinates(11, 33, "u");
      Coordinates v = new Coordinates(28, 39, "v");
      Coordinates w = new Coordinates(7, 12, "w");
      Coordinates x = new Coordinates(16, 15, "x");
      Coordinates y = new Coordinates(23, 34, "y");
      Coordinates z = new Coordinates(51, 27, "z");
      Coordinates á = new Coordinates(43, 21, "á");
      Coordinates é = new Coordinates(27, 37, "é");
      Coordinates ó = new Coordinates(61, 69, "ó");
      Coordinates ú = new Coordinates(29, 59, "ú");
     // Coordinates ö = new Coordinates(55, 65, "ö");


      cs.add(a);
      cs.add(b);
      cs.add(c);
      cs.add(d);
      cs.add(e);
      cs.add(f);
      cs.add(g);
      cs.add(h);
      cs.add(i);
      cs.add(j);
      cs.add(k);
      cs.add(ll);
      cs.add(m);
      cs.add(n);
      cs.add(o);
      cs.add(p);
      cs.add(q);
      cs.add(r);
      cs.add(s);
      cs.add(t);
      cs.add(u);
      cs.add(v);
      cs.add(w);
      cs.add(x);
      cs.add(y);
      cs.add(z);
      cs.add(á);
      cs.add(é);
      cs.add(ó);
      cs.add(ú);
     // cs.add(ö);



      cs2.add(a);
      cs2.add(b);
      cs2.add(c);
      cs2.add(d);
      cs2.add(e);
      cs2.add(f);
      cs2.add(g);
      cs2.add(h);
      cs2.add(i);
      cs2.add(j);
      cs2.add(k);
      cs2.add(ll);

      cs2.add(m);
      cs2.add(n);
      cs2.add(o);
      cs2.add(p);
      cs2.add(q);
      cs2.add(r);
      cs2.add(s);
      cs2.add(t);
      cs2.add(u);
      cs2.add(v);
      cs2.add(w);
      cs2.add(x);
      cs2.add(y);
      cs2.add(z);
      cs2.add(á);
      cs2.add(é);
      cs2.add(ó);
      //cs2.add(ö);
    }
    else if(choice==2)
    {
      Coordinates a = new Coordinates(3, 2, "a");
      Coordinates b = new Coordinates(4, 7, "b");

      Coordinates c = new Coordinates(6, 3, "c");
      Coordinates d = new Coordinates(8, 5, "d");
      Coordinates e = new Coordinates(12, 3, "e");
      Coordinates f = new Coordinates(17, 13, "f");
      Coordinates g = new Coordinates(22, 4, "g");
      Coordinates h = new Coordinates(6, 19, "h");
      Coordinates i = new Coordinates(9, 7, "i");
      Coordinates j = new Coordinates(31, 21, "j");
      Coordinates k = new Coordinates(19, 9, "k");
      Coordinates ll = new Coordinates(24, 14, "l");
      Coordinates m = new Coordinates(18, 27, "m");
      Coordinates n = new Coordinates(47, 33, "n");
      Coordinates o = new Coordinates(40, 50, "o");
      Coordinates p = new Coordinates(49, 26, "p");
      Coordinates q = new Coordinates(21, 43, "q");
      Coordinates r = new Coordinates(16, 4, "r");
      Coordinates s = new Coordinates(17, 11, "s");
      Coordinates t = new Coordinates(35, 28, "t");


      cs.add(a);
      cs.add(b);
      cs.add(c);
      cs.add(d);
      cs.add(e);
      cs.add(f);
      cs.add(g);
      cs.add(h);
      cs.add(i);
      cs.add(j);
      cs.add(k);
      cs.add(ll);
      cs.add(m);
      cs.add(n);
      cs.add(o);
      cs.add(p);
      cs.add(q);
      cs.add(r);
      cs.add(s);
      cs.add(t);

      cs2.add(a);
      cs2.add(b);
      cs2.add(c);
      cs2.add(d);
      cs2.add(e);
      cs2.add(f);
      cs2.add(g);
      cs2.add(h);
      cs2.add(i);
      cs2.add(j);
      cs2.add(k);
      cs2.add(ll);

      cs2.add(m);
      cs2.add(n);
      cs2.add(o);
      cs2.add(p);
      cs2.add(q);
      cs2.add(r);
      cs2.add(s);
      cs2.add(t);
    }
    else if(choice==3 && ho==0)
    { Random rand = new Random();

      for(int rp=0; rp<cno;rp++)
      { int ko =0;
        int xr= rand.nextInt(200)+1;
        int yr = rand.nextInt(200)+1;
        for(int jk=0;jk<cs.size();jk++)
        {
          if(cs.get(jk).getY()==yr && cs.get(jk).getX()==xr)
          {
            ko++;
          }
        }
        if(ko>0){
          rp--;
        }else{
          cs.add(new Coordinates(xr,yr,""+rp+"-"));
          cs2.add(new Coordinates(xr,yr,""+rp+"-"));
        }



      }
    }


    if(choice==3 && ho==0)
      { newcs = new ArrayList();
         newcs2 = new ArrayList();
        newcs.addAll(cs);
        newcs2.addAll(cs2);
      }else if(choice==3 && ho!=0){
      cs.addAll(newcs);
      cs2.addAll(newcs2);
    }





    Sequence ns = new Sequence();
    Random random = new Random();

    double temperature = 100;

    double minDistance = ns.getLength(cs);
    double probability = 0;

    double coolingFactor =cf;
    double loopLength = (temperature / coolingFactor);


    double min = ns.getLength(cs);
    ArrayList<Coordinates> reset = new ArrayList<>();
    double curr = 0;
    System.out.println("Starting permutation: ");
    String str = "";
    final long startTime = System.currentTimeMillis();
    for (int o1 = 0; o1 < cs.size(); o1++)
    {
      str = str + (cs.get(o1).getLetter())+ "["+(int)cs.get(o1).getX()+","+(int)cs.get(o1).getY()+"],";
    }
      System.out.println("\n");
      for (int o1 = 0; o1 < cs.size(); o1++)
      {
        str = str + ((int)cs.get(o1).getX()+","+(int)cs.get(o1).getY()+"\n");
      }

      System.out.println(str + "\n" + "Sum :" + ns.getLength(cs) + " km");

    for (long l = 0; l < loopLength * cs.size(); l++)
    {
      double rn = random.nextDouble();

      Coordinates temp = cs.get((int)(l % cs.size()));
      if (l % cs.size() == cs.size() - 1)
      {
        cs.set((int)(l % cs.size()), cs.get(0));
        cs.set(0, temp);
      }
      else
      {
        cs.set((int)(l % cs.size()), cs.get((int) (l % cs.size() + 1)));
        cs.set((int)(l % cs.size()) + 1, temp);
      }

      probability = Math.exp(-(ns.getLength(cs) - minDistance) / temperature);

      if (temperature <= 0)
      {
        break;
      }
      if (minDistance > ns.getLength(cs))
      {
        minDistance = ns.getLength(cs);
        //cs2=cs;
        // other method not sure which one is better so far
        cs2.clear();
        cs2.addAll(cs);
        //System.out.println("Got lucky ! Found new sequence that costs less: "+ ns.getLength(cs));
        if (ns.getLength(cs) < min)
        {
          min = ns.getLength(cs);
          str = "";
          reset.clear();
          for (int o1 = 0; o1 < cs.size(); o1++)
          {
            str = str + (cs.get(o1).getLetter());
            reset.add(cs.get(o1));
          }
        }
        temperature -= coolingFactor;

      }
      else if (minDistance <= ns.getLength(cs) && rn < probability)
      {

        minDistance = ns.getLength(cs);

        cs2.clear();
        cs2.addAll(cs);
        //System.out.println("Got unlucky ! Found new sequence that costs more: "+ ns.getLength(cs) +" chance: "+ rn + " temp: "+ temperature);

        temperature -= coolingFactor;

      }
      else
      {

        cs.clear();
        cs.addAll(cs2);

      }
      if (l == loopLength * cs.size() * 0.5 || l
          == loopLength * cs.size() * 0.75 || l == loopLength * cs.size() * 0.95)
      {
        curr = minDistance;
        minDistance = min;
        cs.clear();
        cs.addAll(reset);
      }

    }
    final long endTime = System.currentTimeMillis();

    double time = ((double) (endTime - startTime)) / 1000;
    System.out.println(
        "The permutation with the minimum length: " + str + "\nLength: " + min + " km");

    System.out.println("Solution:");
    str = "";
    System.out.println("Execution time: " + time + " seconds");
    for (int o1 = 0; o1 < cs.size(); o1++)
    {
      str = str + (cs.get(o1).getLetter());
    }

    System.out.println("Permutation: " + str + "\nLength: " + minDistance + " km");
    average += minDistance;
    averageTime += time;
    if (min < mi)
    {
      mi = min;
    }
    System.out.println("Did it help? " + curr);
  }
    System.out.println("The average length for "+repetition+" execution: "+ average/repetition + " km"+ "\nAverage time for "+repetition+" executions: "+ averageTime/repetition + " seconds");
    System.out.println("Minimal solution found: "+ mi + " km");
  }
}
