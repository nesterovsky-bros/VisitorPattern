package com.nesterovskyBros.visitor;

/**
 * Not expression.
 */
public class NotExpression extends UnaryExpression
{
  @Override
  public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E
  {
    return visitor.visit(this);
  }
}
