import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BF
{
  private ArrayList<String> function;
  private ArrayList<String> variables;
  private String original;

  public BF(String func)// The boolean function has to be given in Disjunctive Normal Form.
  {
    original = func;
    function = new ArrayList<>();
    variables = new ArrayList<>();
    createList(func);
  }


  public String getOriginal()
  {
    return original;
  }

  public void createList(String func) // This method creates an arraylist that will store all the variables which are joined together by a "." sign.
  {
    if(!func.matches("[aAbBcCdDeEfFgGhHiIjJkKlLmMnNoOpPqQrRsStTuUvVwWxXyYzZ.+]+"))
    {
      System.out.println(
          "The function can only contain the letters of the english alphabet and their lower and upper case versions and \"+\" and \".\" sign");
      return;
    }
    if (func.contains("+"))
    {
      String funct = func;

      String[] temp = funct.split("\\+");

      function.addAll(Arrays.asList(temp));

    }
    else
    {
      function.add(func);
    }

    checkVariables();
  }

  private void checkVariables()  // A = 1, a = 0  This method puts all the unique variables into an arraylist.
  {
    for (int i = 0; i < function.size(); i++)
    {
      String func = function.get(i);
      if (function.get(i).contains("."))
      {

        String[] temp = func.split("\\.");

        for (int m = 0; m < temp.length; m++)
        {

          if (!(variables.contains(temp[m].toLowerCase()) || variables.contains(temp[m].toUpperCase())))
          {
            variables.add(temp[m]);
          }
        }
      }
      else
      {
        if (!(variables.contains(func.toLowerCase()) || variables.contains(func.toUpperCase())))
        {
          variables.add(func);
        }
      }
    }
    for(int i=0;i<variables.size();i++)
    {
      variables.set(i,variables.get(i).toUpperCase());

    }
  }




  public ArrayList<String> getVariables()
  {
    return variables;
  }
}
