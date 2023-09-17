import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Test
{
  ArrayList<String> abc;


  public Test()
  {
    abc = new ArrayList<>();
    String[] k = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
        "M", "N", "O", "P", "Q", "R", "S", "T", "V"};
    abc.addAll(Arrays.asList(k));
  }

  public double randomTest(int minVars, int maxVars, int amountofBDD)
  {
    int max = maxVars - minVars;
    max++;
    Random rndm = new Random();

    BDD bbd = new BDD();
    BF bf;
    double allnodes = 0;
    double currentNode = 0;

    for (int i = 0; i < amountofBDD; i++)
    {
      int m = rndm.nextInt(max) + minVars;

      String o = createRandomBooleanFunction(m);
      bf = new BF(o);

      ArrayList<String> allSolutions = new ArrayList<>();
      bbd = bbd.BDD_create(bf);

      currentNode = bbd.getAmountOfNodes();
      bbd.printPostorder(bbd.getRoot(), allSolutions);
      BDD bk = bbd.BDD_create(bf);

          bbd.BDD_reduce(bbd);
      int l = bbd.getAmountOfNodes();
      double perc = (double)Math.round((100.00000000000 - ((l / currentNode)*100))* 1000d) / 1000d;
      System.out.println("Random boolean expression created: " + bbd.getRoot().getFunction() + "\nAmount of nodes: "+currentNode+" Amount of nodes after reduction: "+l+"\nreduction percentage: "+ perc+"%");
      currentNode = 100.00000000000 - ((l / currentNode)*100);

      allnodes += currentNode;

      for (int y = 0; y < Math.pow(2, bbd.getAmountOfVariables()); y++)
      {

        String binaryInString = Integer.toBinaryString(y);
        if (binaryInString.length() != bbd.getAmountOfVariables())
        {
          int plus = bbd.getAmountOfVariables() - binaryInString.length();
          String temp = "";
          for (int ho = 0; ho < plus; ho++)
          {
            temp += "0";
          }
          temp += binaryInString;
          binaryInString = temp;
        }

        int oneorzero = Integer.parseInt(allSolutions.get(y));
        int banswer = bbd.BDD_use(bbd, binaryInString);
        int bkanswe = bk.BDD_use(bk, binaryInString);
        if (oneorzero != banswer)
        {
          System.out.println(
              "Mistake at: " + binaryInString + " ooz: " + oneorzero + " bansw: "
                  + banswer + " bkanswe: " + bkanswe + " the function:\n" + bbd.getRoot().getFunction());
        }

      }
    }
    allnodes = allnodes / amountofBDD;
    return allnodes;
  }

  public String createRandomBooleanFunction(int variables)
  {
    Random rndm = new Random();

    ArrayList<String> vars = new ArrayList<>();

    for (int i = 0; i < variables; i++)
    {
      vars.add(abc.get(i));
    }
    int m = rndm.nextInt(variables);
    for (int i = 0; i < m; i++)
    {
      int z = rndm.nextInt(variables);
      vars.set(i, vars.get(i).toLowerCase());

    }
    ArrayList<String> am = new ArrayList<>();
    am.add("+");
    am.add(".");
    String function = "";
    ArrayList<String> reduce = new ArrayList<>();
    reduce.addAll(vars);

    while (true)
    {
      int rand = rndm.nextInt(variables);
      int rand2 = rndm.nextInt(5);
      if (function.length() == 0)
      {
        function += vars.get(rand);
        reduce.remove(vars.get(rand));
      }

      if (function.endsWith("+"))
      {
        if (rand2 == 0)
        {
          function += vars.get(rand);
        }

        else
        {
          function += vars.get(rand);
          reduce.remove(vars.get(rand));
        }
      }
      else if (function.endsWith("."))
      {
        if (rand2 == 0)
        {
          function += vars.get(rand);
        }
        else
        {
          function += vars.get(rand);
          reduce.remove(vars.get(rand));
        }
      }
      else
      {
        if (rand2 == 0 || rand2 == 1 || rand2 == 2)
        {
          function += ".";
        }
        else
        {
          function += "+";
        }

      }
      if (reduce.size() == 0)
      {
        break;
      }

    }

    return function;

  }

  public double randomTestWithTime(int minVars, int maxVars, int amountofBDD)
  {
    int max = maxVars - minVars;
    max++;
    Random rndm = new Random();

    BDD bbd = new BDD();
    BF bf;
    double allnodes = 0;
    double currentNode = 0;
double supertime =0;
double supertime2=0;
    for (int i = 0; i < amountofBDD; i++)
    {
      int m = rndm.nextInt(max) + minVars;

      String o = createRandomBooleanFunction(m);
      bf = new BF(o);
      long startTime = System.nanoTime();
      bbd = bbd.BDD_create(bf);
      long endTime = System.nanoTime();
      double duration = (endTime - startTime)/ 1000000000.0000;

      //System.out.println("Create time: "+ duration);

      long startTime2 = System.nanoTime();
      int l = bbd.BDD_reduce(bbd);
      long endTime2 = System.nanoTime();
      double duration2 = (endTime2 - startTime2)/ 1000000000.0000;
      //System.out.println("Reduce time: "+ duration2);
      double d = duration+duration2;
      //System.out.println("Time together: "+ d);



      supertime+= duration;
      supertime2+=duration2;
    }
    System.out.println("All create time :"+ supertime);
    System.out.println("Average create time: "+supertime/amountofBDD);
    System.out.println("All reduce time: "+supertime2);
    System.out.println("Average reduce time: "+supertime2/amountofBDD);
    System.out.println("Average total time: "+(supertime+supertime2)/amountofBDD);
    return supertime+supertime2;
  }
}
