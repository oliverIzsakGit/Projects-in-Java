import java.util.ArrayList;
import java.util.Arrays;

public class BDD
{

  private int amountOfNodes;          // amount of nodes in the BDD
  private int amountOfVariables;       // amount of variables in the boolean expression
  private Node root;                 // root of the binary decision diagram
  private static Node node1;         // static node which value equals 1
  private static Node node0;          //static node which value equals 0
  private BF funct;                 // the boolean expression given as an input


public BDD()
{
node1 = new Node();

node1.setFunction("1");
node0 = new Node();


node0.setFunction("0");

}

  public int getAmountOfVariables()
  {
    return amountOfVariables;
  }

  public Node getRoot()
  {
    return root;
  }
  void printPostorder(Node node, ArrayList<String> nds)  // for testing
  {
    if (node == null)
      return;


    printPostorder(node.getLeft(),nds);
    printPostorder(node.getRight(),nds);




    if(node.getFunction().equals("1") || node.getFunction().equals("0")){nds.add(node.getFunction());}
  }

  /**
   * This is the method that creates a BDD object
   *It initializes the root  and call the BBDCreateHelper recursive method to start creating the binary decision diagram
   * @param function
   * @return
   */
  public BDD BDD_create(BF function)
{
   amountOfVariables = function.getVariables().size();
   funct = function;
   root = new Node();
   root.setFunction(function.getOriginal());
   amountOfNodes=1;

String shadec = function.getOriginal();

   BBDCreateHelper(shadec,root,function,0);

return this;
}

  /**
   * This is a recursive method that creates the binary decision diagram
   * @param shadec string containing the boolean expression
   * @param temp Node current node
   * @param f BF containing the variables
   * @param var current depth
   */
  public void BBDCreateHelper(String shadec,Node temp,BF f,int var)
{ BF function = f;


    String shadec1 =shanonDecomposition(shadec,function.getVariables().get(var),false);
    Node newnode = new Node(temp,shadec1);
    temp.setLeftWithDepth(newnode);




    String shadec2 =shanonDecomposition(shadec,function.getVariables().get(var),true);

    Node newnode2 = new Node(temp,shadec2);
    temp.setRightWithDepth(newnode2);
    amountOfNodes+=2;
    temp.setDepth(var);
    if(var+1==function.getVariables().size()) { // this only happens at the last depth, which is the last node of the diagram, which  can have only 2 values, 1 or 0
      evaluate(newnode,newnode2);return;}
    BBDCreateHelper(shadec1,newnode,function,var+1);

    BBDCreateHelper(shadec2,newnode2,function,var+1);




}

  public int getAmountOfNodes()
  {
    return amountOfNodes;
  }

  /**
   * evaluates a node's children, it gives  them a value of either 0 or 1
   * @param left Node left child
   * @param right Node right child
   */
  public void evaluate(Node left,Node right)
  {
   if( stringToInt(left.getFunction())==0)
   {

     left.setFunction("0");


   }
   else {

     left.setFunction("1");


   }
   if(stringToInt(right.getFunction())==0)
    {

      right.setFunction("0");


    }
   else
   {

     right.setFunction("1");


   }
  }

  /**
   * this method converts a specific boolean expression where all variables have been replaced by ones and zeroes, into an int
   *and calculates it. Eg.: 1.0+1.0+0.0 , will be reduced to a 0,
   *  @param str string containing the boolean expression from a node with  last depth
   * @return int 1 or 0
   */
  public int stringToInt(String str)
  {
    ArrayList<String> vars = getAdditions(str);
    int temp =0;
    for(int i =0;i<vars.size();i++)
    {
      if(vars.get(i).length()==1){
        temp += Integer.parseInt(vars.get(i));
      }
      else if (vars.get(i).length()>1)
      { int temp2=0;
        for(int m =0; m<vars.get(i).length();m++)
        {
          if(Integer.parseInt(String.valueOf(vars.get(i).charAt(m)))==0)
          {
            temp2 =0; break;
          }
          else {temp2=1;}
        }
        if(temp2==1)
        {
          temp+=1;
        }

      }


    }

    return temp;
  }

  /**
   * This method works similiar to stringToInt method, however this one works on boolean expressions that contain variables.
   * It is for reducing functions to zero or one, depending on some of the information we have in the string, eg.: 1+a.B+c.d.e+k , will be reduced to just 1, because
   * it doesnt matter what are the other variables, this function will always be 1.
   * @param str String containing the boolean expression
   * @return String containing the value, it can be either 0,1 or the specific variable eg.: a.b+c
   */
  public String midFunctionReduction(String str )
  {
    ArrayList<String> vars = getAdditions(str);

    for(int i =0; i<vars.size();i++)
    {
      if(vars.get(i).contains("0"))
      {
        vars.set(i,"$");
      }
      else if( vars.get(i).contains("1"))
      {
        if(!vars.get(i).matches("[1]+"))
        {
          vars.set(i,vars.get(i).replace("1",""));
        }
        else {vars.set(i,"€");}

      }
      else {}
    }
    String endTemp="";
    for(int m=0; m<vars.size();m++)
    {
      if(vars.get(m).equals("$"))
      {
        endTemp+="";
      }
      else if(vars.get(m).equals("€"))
      {
        if(endTemp.equals(""))
        {
          endTemp+="1";
        }
        else {endTemp+="+1";}

      }
      else {
        if(endTemp.equals(""))
        {
          endTemp+=vars.get(m);
        }
        else {endTemp+="+"+vars.get(m);}

      }
    }
    String temp = "";
    if(endTemp.contains("1"))
    {
      endTemp="1";
    }
    else
    {

      for (int p = 0; p < endTemp.length(); p++)
      {
        if (p == (endTemp.length() - 1))
        {
          temp += endTemp.charAt(p);
        }
        else if (endTemp.charAt(p + 1) != '+' && endTemp.charAt(p) != '+')
        {
          temp += endTemp.charAt(p) + ".";
        }
        else
        {
          temp += endTemp.charAt(p);
        }
      }
      endTemp=temp;
    }
    return endTemp;
  }

  /**
   * This method takes apart the given boolean expression and sorts the variables  into arrays depending on "." or "+"
   * The ones that have "." (multiplication) between them are put together in an arrayslot
   * @param fnc string boolean expression
   * @return ArrayList<String></> containing all the parts of the boolean expression
   */
  public ArrayList<String> getAdditions(String fnc)
  {ArrayList<String> temp = new ArrayList<>();

    if (fnc.contains("+"))
    {
      String funct = fnc;

      String[] tem = funct.split("\\+");

      temp.addAll(Arrays.asList(tem));

    }
    else
    {
      temp.add(fnc);
    }
    for(int i =0;i<temp.size();i++)
    {
      if(temp.get(i).contains("."))
      {
        String func = temp.get(i);

        String[] tem = func.split("\\.");
        String str = "";
        for(int m = 0; m<tem.length;m++)
        {
          str+=tem[m];
        }
        temp.set(i,str);
      }

    }
    return temp;
  }

  /**
   * Using shadnon decomposition to take apart a boolean expression into 2 parts and reduce both sides by replacing the current selected variable
   * with zero on left side and with ones in the right side.
   * @param func string containing the boolean expression
   * @param ch string containing the current variable
   * @param zeroOrOne boolean false = 1 = left side, true = 1 = right side
   * @return a string with reduced boolean expression
   */
  public String shanonDecomposition(String func,String ch,boolean zeroOrOne)
  {
    char var = ch.charAt(0);
    char[] charay = new char[func.length()];
    for (int i = 0; i < func.length(); i++)
    {
      charay[i] = func.charAt(i);
    }
    if (zeroOrOne == true)
    {
      for (int i = 0; i < charay.length; i++)
      {
        char temp = charay[i];

        if (Character.compare(Character.toUpperCase(temp), var) == 0)
        {
          if (Character.isUpperCase(charay[i]))
          {
            charay[i] = '1';
          }
          else
          {
            charay[i] = '0';
          }
        }

      }

    }
    else if (zeroOrOne == false)
    {
      for (int i = 0; i < charay.length; i++)
      {
        char temp = charay[i];

        if (Character.compare(Character.toUpperCase(temp), var) == 0)
        {
          if (Character.isUpperCase(charay[i]))
          {
            charay[i] = '0';
          }
          else
          {
            charay[i] = '1';
          }
        }
      }


    }
    ;
    String str = new String(charay);

    return str;
  }

  /**
   * This method is responsible for reducing the Binary Decision Diagram by deleting all nodes that have identical value.
   * And by reassigning all the nodes in the last depth  to node0 and node1 , which saves memory.
   * @param bbd BBD
   * @return int representing the amount of unique nodes after the reduction, returns -1 if something went wrong.
   */
  public int BDD_reduce(BDD bbd)
{  int oldnodes = amountOfNodes;
  ArrayList<Node> crn=new ArrayList<>();
  for(int z =amountOfVariables; z>0;z--)
  {  ArrayList<String> nodes = new ArrayList<>();


    nodes.add("$");

    for(int i=0; i<amountOfVariables;i++)
    {
      postOrderTraversalReduction(bbd.getRoot(),nodes,z,crn);
      nodes.add("$");
    }
  }

  node1.setDepth(amountOfVariables);
  node0.setDepth(amountOfVariables);
  postOrderTraversalReductionOAZ(bbd.getRoot());
  amountOfNodes+=2;


if(amountOfNodes<0 || oldnodes<amountOfNodes){return -1;}
return (oldnodes-amountOfNodes);
}

  /**
   * This recursive method is responsible for rearranging the last depth nodes to node1 and node2.
   * @param node current Node
   */
  public void postOrderTraversalReductionOAZ(Node node)
  {

    if (node == null)
      return;

    postOrderTraversalReductionOAZ(node.getLeft());

    postOrderTraversalReductionOAZ(node.getRight());

    if(node.getDepth()==amountOfVariables)
    {

      if(node.getFunction().equals("0"))
      {
        if(node.isLeft())
        {
          node0.setParent(node.getParent());
          node.getParent().setLeft(node0);

          amountOfNodes--;
        }
        else {node0.setParent(node.getParent());node.getParent().setRight(node0); amountOfNodes--;}

      }
      else if(node.getFunction().equals("1"))
      {
        if(node.isLeft())
        {node1.setParent(node.getParent());
          node.getParent().setLeft(node1); amountOfNodes--;
        }
        else {node1.setParent(node.getParent());node.getParent().setRight(node1); amountOfNodes--;}
      }
    }
  }

  /**
   * This is the method which reduces the nodes in the BDD.
   * It goes through the all nodes from bottom to top and looks at boolean expression , looks for identical boolean expression and replaces the duplicates with the first one found
   * @param node current node
   * @param nodes containing the combination of a node
   * @param n  depth of the node
   * @param crn arraylist containing the node which combination is being looked for
   */
  public void postOrderTraversalReduction(Node node,ArrayList<String> nodes,int n,ArrayList<Node> crn)
  { if (node == null)
      return;
    postOrderTraversalReduction(node.getLeft(), nodes, n, crn);
    postOrderTraversalReduction(node.getRight(), nodes, n, crn);
    if (node.getDepth() == amountOfVariables && n == node.getDepth())
    {
      String combination = node.getFunction();
      if (node.isLeft())
      {
        combination = combination + node.getParent().getRight().getFunction();
        if (nodes.get(nodes.size() - 1).equals("|"))
        { if (nodes.get(nodes.size() - 2).equals(combination))
          { if (node.getParent().isLeft())
            { node.getParent().getParent().setLeft(crn.get(0));
            amountOfNodes -= 1; }
            else
            { node.getParent().getParent().setRight(crn.get(0));
              amountOfNodes -= 1;
            }
          }
        }
        else if (!(nodes.contains(combination)))
        { nodes.add(combination);
          crn.add(0,node.getParent());
          nodes.add("|");
        }
      }
    }
    else if (node.getDepth() == n && node.getDepth()!=amountOfVariables-1) // this is for depths above the last and the second to last. Because in the above if statement I get rid of all the duplicate nodes in both the last depth nodes and their parent nodes
    { String combination = node.getFunction();
    String temp = midFunctionReduction(combination);
    combination = temp;
    if (nodes.get(nodes.size() - 1).equals("|"))
      { if (nodes.get(nodes.size() - 2).equals(combination))
        { if (node.isLeft())
          { node.getParent().setLeft(crn.get(0));
            amountOfNodes -= 1; }
          else
          { node.getParent().setRight(crn.get(0));
            amountOfNodes -= 1;
          }
        }
      }
      else if (!(nodes.contains(combination)))
      { nodes.add(combination);
        crn.add(0,node);
        nodes.add("|"); }
    }
  }


  /**
   * This method is responsible for going through the BDD and inserting the given combination of one and zeroes  into the boolean expression to calculate the result.
   * The method accepts a binary decision diagram which has a boolean expression in it, and accepts a value which consists of one and zeroes,
   * and each one and zero representing a variable in the boolean expression, it always assigns the 0 or 1 to the variable that comes first in the expression.
   * eg.: if your boolean function is A+C+B.E.D+A , then typing 01001, means A=0;  C=1; B=0;E= 0, D=1;
   * @param bdd binary decision diagram
   * @param value combination of 1s and 0s
   * @return int representing the result of the expression
   */
  public int BDD_use(BDD bdd,String value)
  { Node node =bdd.getRoot();
    if(amountOfVariables==value.length())
    { for (int i = 0; i < amountOfVariables; i++)
      { if (value.charAt(i) == '0')
        {
          node = node.getLeft();
        }
        else if (value.charAt(i) == '1')
        {
          node = node.getRight();
        }
        else
        {
          System.out.println("error, use only numbers 1 or 0");
          return -1;
        }
      }
    }
    else {
      System.out.println("Error, wrong input for string");return -1;}

    if(node.getFunction().equals("1")) {return 1;}
    else
       return 0;
  }

}
