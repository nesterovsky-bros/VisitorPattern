package com.nesterovskyBros.visitor;

/**
 * Binary expression.
 */
public abstract class BinaryExpression implements Expression
{
  /**
   * First argument.
   */
  public Expression first;
  
  /**
   * Second argument.
   */
  public Expression second;
}
