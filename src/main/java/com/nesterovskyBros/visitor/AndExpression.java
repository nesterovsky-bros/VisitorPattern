package com.nesterovskyBros.visitor;

/**
 * And expression.
 */
public class AndExpression extends BinaryExpression
{
  @Override
  public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E
  {
    return visitor.visit(this);
  }
}
