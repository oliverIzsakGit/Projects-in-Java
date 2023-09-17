import java.util.ArrayList;

public class main
{
  public static void main(String[] args)
  {// testing


    BF bf = new BF("A.B+C.E+D");
    System.out.println("Boolean function: "+ bf.getOriginal());
    BDD m = new BDD();
    BDD  diagram = m.BDD_create(bf);
    System.out.println("Amount of unique variables: "+m.getAmountOfVariables());
    System.out.println("Amount of nodes created: "+m.getAmountOfNodes());
    int before = m.getAmountOfNodes();

    System.out.println("Reduction:");
    int reduction = m.BDD_reduce(m);
    System.out.println("Amount of unique variables: "+m.getAmountOfVariables());
    System.out.println("Amount of nodes created: "+m.getAmountOfNodes());
    int after= m.getAmountOfNodes();
    System.out.println("Use:");
    int errors=0;
    if (m.BDD_use(m,"00000")==1)
    {
      System.out.println("ERROR, the solution should be:"+ 0);errors++;
    }if (m.BDD_use(m,"00001")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }
    if (m.BDD_use(m,"00010")==1)
    {
      System.out.println("ERROR, the solution should be:"+ 0);errors++;
    }
    if (m.BDD_use(m,"00011")==0)
    {
      System.out.println("ERROR, the solution should be:"+1 );errors++;
    }
    if (m.BDD_use(m,"00100")==1)
    {
      System.out.println("ERROR, the solution should be:"+ 0);errors++;
    }
    if (m.BDD_use(m,"00101")==0)
    {
      System.out.println("ERROR, the solution should be:"+ 1);errors++;
    }if (m.BDD_use(m,"00110")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"00111")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"01000")==1)
  {
    System.out.println("ERROR, the solution should be:"+ 0);errors++;
  }if (m.BDD_use(m,"01001")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"01010")==1)
  {
    System.out.println("ERROR, the solution should be:"+ 0);errors++;
  }if (m.BDD_use(m,"01011")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"01100")==1)
  {
    System.out.println("ERROR, the solution should be:"+ 0);errors++;
  }if (m.BDD_use(m,"01101")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"01110")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"01111")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"10000")==1)
  {
    System.out.println("ERROR, the solution should be:"+ 0);errors++;
  }if (m.BDD_use(m,"10001")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"10010")==1)
  {
    System.out.println("ERROR, the solution should be:"+ 0);errors++;
  }if (m.BDD_use(m,"10011")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"10100")==1)
  {
    System.out.println("ERROR, the solution should be:"+ 0);errors++;
  }if (m.BDD_use(m,"10101")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"10110")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"10111")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"11000")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"11001")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"11010")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"11011")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"11100")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"11101")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"11110")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }if (m.BDD_use(m,"11111")==0)
  {
    System.out.println("ERROR, the solution should be:"+ 1);errors++;
  }

    System.out.println("Amount of errors: "+ errors);
    double perc = (double)Math.round((100-((double)after/(double)before)*100) * 1000d) / 1000d;

    System.out.println("Node reduction percentage: "+ perc + "%"  );


    // random test with random boolean functions, specific variable amount, and specific bdd creation
    Test test = new Test();

    System.out.println("Reduction successful\n"+"Node reduction Percentage: "+(double)Math.round(test.randomTest(13,13,10) * 1000d) / 1000d+ "%\n");




    //random test with time

    //System.out.println("Total time spent : "+test.randomTestWithTime(13,13,10));


  }
}
