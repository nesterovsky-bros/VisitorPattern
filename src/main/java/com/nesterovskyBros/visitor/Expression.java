package com.nesterovskyBros.visitor;

/**
 * An expression.
 */
public interface Expression
{
  /**
   * Visits expression.
   * @param visitor an expression visitor.
   * @return result. 
   */
  public <R, E extends Throwable> R accept(Visitor<R, E> visitor) throws E;
}
