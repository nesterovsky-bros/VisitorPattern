package com.nesterovskyBros.visitor;

public interface Visitor<R, E extends Throwable>
{
  R visit(AndExpression expression) throws E;
  R visit(NotExpression expression) throws E;
  R visit(OrExpression expression) throws E;
  R visit(RefExpression expression) throws E;
}
