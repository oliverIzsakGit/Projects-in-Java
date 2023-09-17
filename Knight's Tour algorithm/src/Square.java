import java.util.ArrayList;

public class Square
{

  private int number;
  private boolean state;
  private boolean row;
  private ArrayList<Square> sqrows;


  @Override public String toString()
  {
    return number+"\n";
  }

  public Square(int number,boolean row,ArrayList<Square> sqrs)
  {
    this.number = number;
    this.state = false;
    this.row = row;
    if(row)
    {
     sqrows = new ArrayList<>();
    }


  }
public ArrayList<Square> getArrayList()
{
  return sqrows;
}

  public int getNumber()
  {
    return number;
  }

  public void setNumber(int number)
  {
    this.number = number;
  }

  public boolean isActive()
  {
    return state;
  }

  public void setState(boolean state)
  {
    this.state = state;
  }

  public int isItSolved()
  {int k =0;

  if(isActive())
  {
    k++;

  }
    for(int i =0; i<sqrows.size();i++)
    {
      if(sqrows.get(i).isActive())
      {
        k++;


      }

    }
   // System.out.println("Current t is "+ k);
    return k;
  }


}
