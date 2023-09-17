import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class main
{
  public static void main(String[] args)
  {

   Helper help =new Helper();
   ArrayList<Integer> starts = new ArrayList<>();
    Scanner sc=new Scanner(System.in);
    int write=0;
    System.out.println("Input the size of the board. (eg.:5,6) :");

    int k= sc.nextInt();
    System.out.println("Input the search time in seconds for an individual solution(eg.:5,10,20,30,90 ) :");
    int tum = sc.nextInt();
    System.out.println("If you want to test for randomized 5 starting points press 1\nIf you want to test for all starting points press 2\nIf you want to test a specific starting point press 3");
  int choice= sc.nextInt();
  if(choice==3)
  {
    System.out.println("Choose a starting point (1-25 or 1-36): ");
    int start = sc.nextInt();
    starts.add(start);
  }else if ( choice ==1)
  {int random=0;
    Random rnd = new Random();
    if(k==5)
    {
      starts.add(21);
      System.out.println(starts.get(0));
    }
    else if(k==6)
    {
      starts.add(31); System.out.println(starts.get(0));
    }
    for(int i=0;i<4;i++)
    {
       random = rnd.nextInt(k*k)+1;
       if(starts.contains(random))
       {
         i--;
       }
       else
       {
         System.out.print(random+",");

         starts.add(random);
       }

    }

  }else if(choice==2)
  {
    for(int i=1;i<k*k+1;i++)
    {
      starts.add(i);
    }
  }

    for(int v=1; v< (k*k)+1;v++)
    {
      if(starts.contains(v))
      {
        ArrayList<Square> RowsArray = new ArrayList<>(k);
      if(write==0)
      {
        System.out.println("This is how the table looks:");
      }
        int m = 1;           // counter
        int x = k;           // x size of the board. 5x5
        for (int i = 0; i < x; i++) //
        {
          RowsArray.add(new Square(m, true, null));
          if(write==0)
          {
            System.out.print(RowsArray.get(i).getNumber());
          }
          for (int j = 1; j < x; j++)
          {
            int o = m + j;

            RowsArray.get(i).getArrayList().add(new Square(o, false, RowsArray));
            if( write==0)
            {
              if ((i == 0 || i == 1))
              {   if(i==1 && k==6 && (o==12 || o==11))
              {
                System.out.print("  " + RowsArray.get(i).getArrayList().get(j - 1).getNumber());
              } else
                System.out.print("   " + RowsArray.get(i).getArrayList().get(j - 1).getNumber());
              }

              else System.out.print("  " + RowsArray.get(i).getArrayList().get(j - 1).getNumber());
              }
            }
            if(write==0){
              System.out.println();
            }

          m = m + x;
        }
        System.out.println("For the number: " + v);

        int oko=0;
           for(int i =0;i<starts.size();i++)
           {
             if(starts.contains(v))
             {
               oko= starts.get(i);
             }
           }
           if(choice==2)
           {
             ArrayList<Integer> result = help.getCoordinates(RowsArray, oko);
           }
        ArrayList<Integer> result = help.getCoordinates(RowsArray, v);
        int rowNo = result.get(0);
        int columnNo = result.get(1);
        ArrayList<Integer> asd = new ArrayList<>(RowsArray.size() * RowsArray.size());
        if (columnNo == -1)
        {
          asd.add(RowsArray.get(rowNo).getNumber());
        }
        else
        {
          asd.add(RowsArray.get(rowNo).getArrayList().get(columnNo).getNumber());
        }
        final long startTime = System.currentTimeMillis();
       ArrayList<Integer> steps = new ArrayList<>();
       steps.add(0);
        help.search(RowsArray, columnNo, rowNo, asd, tum, startTime,steps);
        write++;
      }
    }




    System.out.println("END");

  }




}
