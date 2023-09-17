import java.util.ArrayList;

import static java.lang.Math.sqrt;
import static java.lang.StrictMath.abs;

public class Sequence
{

  public double getLength(ArrayList<Coordinates> seqq)
  { double total =0;
   for(int i=0;i<seqq.size();i++)
   {
     if(i==seqq.size()-1)
     {
       total+= seqq.get(i).calculateDistance(seqq.get(0));
     break;
     }
     total += seqq.get(i).calculateDistance(seqq.get(i+1));


   }
   return total;
  }

}
