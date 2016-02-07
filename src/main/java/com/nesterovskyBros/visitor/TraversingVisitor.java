package com.nesterovskyBros.visitor;

/**
 * Classical traversing visitor.
 */
public class TraversingVisitor<R, E extends Throwable> implements Visitor<R, E>
{
  public TraversingVisitor(Visitor<R, E> visitor)
  {
    this.visitor = visitor;
  }
  
  public TraversingVisitor(Visitor<R, E> visitor, boolean traverseFirst)
  {
    this.visitor = visitor;
    this.traverseFirst = traverseFirst;    
  }
  
  public void traverse(BinaryExpression expression)
    throws E
  {
    expression.first.accept(this);
    expression.second.accept(this);
  }
  
  public void traverse(UnaryExpression expression)
    throws E
  {
    expression.argument.accept(this);
  }
    
  public void traverse(AndExpression expression)
    throws E
  {
    traverse((BinaryExpression)expression);
  }
  
  public void traverse(NotExpression expression)
    throws E
  {
    traverse((UnaryExpression)expression);
  }
  
  public void traverse(OrExpression expression)
    throws E
  {
    traverse((BinaryExpression)expression);
  }

  public void traverse(RefExpression expression)
    throws E
  {
  }

  @Override
  public R visit(AndExpression expression)
    throws E
  {
    if (traverseFirst)
    {
      traverse(expression);
    }
    
    R result = visitor == null ? null : visitor.visit(expression);

    if (!traverseFirst)
    {
      traverse(expression);
    }
    
    return result;
  }
  
  @Override
  public R visit(NotExpression expression)
    throws E
  {
    if (traverseFirst)
    {
      traverse(expression);
    }
    
    R result = visitor == null ? null : visitor.visit(expression);

    if (!traverseFirst)
    {
      traverse(expression);
    }
    
    return result;
  }

  @Override
  public R visit(OrExpression expression)
    throws E
  {
    if (traverseFirst)
    {
      traverse(expression);
    }
    
    R result = visitor == null ? null : visitor.visit(expression);

    if (!traverseFirst)
    {
      traverse(expression);
    }
    
    return result;
  }
  
  @Override
  public R visit(RefExpression expression)
    throws E
  {
    if (traverseFirst)
    {
      traverse(expression);
    }
    
    R result = visitor == null ? null : visitor.visit(expression);

    if (!traverseFirst)
    {
      traverse(expression);
    }
    
    return result;
  }

  protected Visitor<R, E> visitor;
  protected boolean traverseFirst;
}
