import java.util.ArrayList;

public class Helper
{

  public int[][] movements = { {2, -1},{1, 2}, {2, 1} ,{-2, 1}, {-1, 2},{-2, -1}, {1, -2}, {-1, -2}};

  public String search(ArrayList<Square> rowsArray,int columnNo, int rowNo,ArrayList<Integer> solution,int tum,long startTime,ArrayList<Integer> steps)
  {String res ="";
    ArrayList<Integer> results = new ArrayList<>(rowsArray.size()); // deepcopying the arrays, and table.
    results.addAll(solution);
    DeepCopy k = new DeepCopy();
    ArrayList<Square> alreadyTraversedMine = k.copy(rowsArray.size(),solution);

    int var = 0;


    int r=0;
    int c=0;


    while(var<8)  // trying out all the possible movements in a while loop

  {

    try
    {

       r = rowNo + movements[var][0]; // coordinates of the movements
       c = columnNo + movements[var][1];


      if (c == -1)  // this if statement is for situation when the column is -1, which meeans its the first column.
      {

        if (alreadyTraversedMine.get(r).isActive()==false)
        {
         alreadyTraversedMine.get(r).setState(true);
         results.add(alreadyTraversedMine.get(r).getNumber());



           res = search(alreadyTraversedMine,c,r,results,tum,startTime,steps);
          int step = steps.get(0);
          step++;
          steps.add(0,step);
          alreadyTraversedMine.get(r).setState(false); // if it comes back to this it means the path was a dead end, so we have to remove it.
          if(res.equals("DIE"))
          {
            return "DIE";
          }

          results.remove(results.size()-1);

        }


      }
      else
      {

                if (rowsArray.get(r).getArrayList().get(c).isActive()==false)
        {

          alreadyTraversedMine.get(r).getArrayList().get(c).setState(true);
          results.add(alreadyTraversedMine.get(r).getArrayList().get(c).getNumber());

          res =  search(alreadyTraversedMine,c,r,results,tum,startTime,steps);
          alreadyTraversedMine.get(r).getArrayList().get(c).setState(false);
          int step = steps.get(0);
          step++;
          steps.add(0,step);
          results.remove(results.size()-1);





          alreadyTraversedMine.get(r).getArrayList().get(c).setState(false);
          if(res.equals("DIE"))
          {
            return "DIE";
          }





        }

      }


      var++;

    }
    catch (IndexOutOfBoundsException e)
    {

      var++;

    }

  }
    final long endTime = System.currentTimeMillis();

    double time = ((double)(endTime - startTime))/1000;

    if(time>tum)
    {
      System.out.println("Time is up : "+time + " seconds" );
      System.out.println("Amount of steps: "+ steps.get(0));
      return "DIE";
    }

    int t =0;
    //  System.out.println("Ignore this:");
    for(int i =0; i<rowsArray.size();i++)
    {
      t =t + rowsArray.get(i).isItSolved();
    }


    //  System.out.println("Ignore this END");
    if(t== rowsArray.size()*rowsArray.size() ){




      System.out.println("Sequence: ");
      for(int i =0; i<t;i++)
      {
        System.out.print(solution.get(i)+",");

      }
      System.out.println("\n");


      System.out.println("Total execution time: " + ((double)(endTime - startTime))/1000);
      System.out.println("Amount of steps : "+ steps.get(0));


      return "DIE";

    }


return "";
}
  public ArrayList<Integer> getCoordinates(ArrayList<Square> rowsArray, int start)
  {
    int rowNo=0;
    int columnNo=-1;
    boolean done=false;
    while(true)
    {

      if(rowsArray.get(rowNo).getNumber()== start)
      {
        rowsArray.get(rowNo).setState(true);

        break;
      }
      else
      {
        for(int i =0;i<rowsArray.get(rowNo).getArrayList().size();i++)
        {
          if(rowsArray.get(rowNo).getArrayList().get(i).getNumber() == start)
          {

            rowsArray.get(rowNo).getArrayList().get(i).setState(true);

            columnNo = i; done = true;
            break;
          }
        }
        if(done==true){break;}
      }
      rowNo++;
    }
    ArrayList<Integer> res = new ArrayList<>();
    res.add(rowNo);
    res.add(columnNo);

    return res;
  }




}
