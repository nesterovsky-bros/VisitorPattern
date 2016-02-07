package com.nesterovskyBros.visitor;

/**
 * Or expression.
 */
public class OrExpression extends BinaryExpression
{
  @Override
  public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E
  {
    return visitor.visit(this);
  }
}
