package com.nesterovskyBros.visitor;

/**
 * Condition reference expression.
 */
public class RefExpression implements Expression
{
  /**
   * A reference name.
   */
  public String name;

  @Override
  public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E
  {
    return visitor.visit(this);
  }
}
