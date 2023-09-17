import java.util.ArrayList;

public class Node

{


  private Node left;
  private Node right;
  private Node parent;
  private int depth;
  private String function;




  public Node(Node parent,String fu)
  {




    this.parent = parent;


    function = fu;
  }
  public Node()
  {
    this.left=null;
    this.right = null;
    if(parent==null){
      this.depth=0;
      }

    parent = null;
    function = null;
  }

  public Node getLeft()
  {
    return left;
  }



  public boolean isLeft(){
    if(this.getParent().getLeft().equals(this)){
      return true;
    }
    return  false;
  }
  public boolean isRight(){
    if(this.getParent().getRight().equals(this)){
      return true;
    }
    return  false;
  }
  public boolean isRoot(){
    if(this.getParent()==null){
      return true;
    }
    return  false;
  }

  public String getFunction()
  {
    return function;
  }

  public void setFunction(String function)
  {
    this.function = function;
  }

  public void setLeftWithDepth(Node left)
  {

    this.left = left;
    this.left.depth=this.depth+1;

  }
  public void setLeft(Node left)
  {

    this.left = left;


  }
  public void setRight(Node right)
  {
    this.right = right;

  }
  public Node getRight()
  {
    return right;
  }

  public void setRightWithDepth(Node right)
  {
    this.right = right;
    this.right.depth=this.depth+1;
  }

  public Node getParent()
  {
    return parent;
  }

  public void setParent(Node parent)
  {
    this.parent = parent;
  }


  public int getDepth()
  {
    return depth;
  }

  public void setDepth(int depth)
  {
    this.depth = depth;
  }


}
