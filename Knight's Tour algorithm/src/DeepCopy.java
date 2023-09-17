import java.util.ArrayList;

public class DeepCopy
{

  public ArrayList<Square> copy(int arraynumber, ArrayList<Integer> ints)
  {
    ArrayList<Square> RowsArray= new ArrayList<Square>(arraynumber);

    int m=1;           // counter
    int x =arraynumber;           // x size of the board. 5x5
    for (int i =0; i<x;i++) //
    {

      RowsArray.add(new Square(m,true,null));


      if(ints.contains(RowsArray.get(i).getNumber()))
      {
        RowsArray.get(i).setState(true);
      }

      for(int j=1; j<x;j++)
      { int o = m+j;

        RowsArray.get(i).getArrayList().add(new Square(o,false,RowsArray));

        if(ints.contains(RowsArray.get(i).getArrayList().get(j-1).getNumber()))
        {
          RowsArray.get(i).getArrayList().get(j-1).setState(true);
        }

      }

      m=m+x;

    }

    return RowsArray;
  }
}
