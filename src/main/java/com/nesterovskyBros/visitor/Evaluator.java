package com.nesterovskyBros.visitor;

import java.util.ArrayDeque;

public abstract class Evaluator implements Visitor<Void, Exception>
{
  public Boolean result() throws Exception
  {
    return stack.peek(); 
  }
  
  public Void visit(AndExpression expression) throws Exception
  {
    Boolean second = stack.pop();
    Boolean first = stack.pop();

    stack.push(Boolean.TRUE.equals(first) && Boolean.TRUE.equals(second));
    
    return null;
  }

  public Void visit(NotExpression expression) throws Exception
  {
    stack.push(!Boolean.TRUE.equals(stack.pop()));
    
    return null;
  }
  
  public Void visit(OrExpression expression) throws Exception
  {
    Boolean second = stack.pop();
    Boolean first = stack.pop();

    stack.push(Boolean.TRUE.equals(first) || Boolean.TRUE.equals(second));
    
    return null;
  }

  public Void visit(RefExpression expression) throws Exception
  {
    stack.push(resolve(expression.name));
    
    return null;
  }

  protected abstract Boolean resolve(String name) throws Exception;
  protected ArrayDeque<Boolean> stack = new ArrayDeque<>();  
}
