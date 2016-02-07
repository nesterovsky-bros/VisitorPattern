package com.nesterovskyBros.visitor;

import java.util.stream.Stream;

/**
 * Streaming traverser.
 */
public class Traverser implements Visitor<Stream<Expression>, RuntimeException>
{
  public Stream<Expression> stream(Expression expression)
  {
    return Stream.concat(
      Stream.of(expression), 
      expression.accept(this).flatMap(this::stream));
  }
  
  public Stream<Expression> traverseFirstStream(Expression expression)
  {
    return Stream.concat(
      expression.accept(this).flatMap(this::traverseFirstStream),
      Stream.of(expression));
  }
  
  public Stream<Expression> visit(BinaryExpression expression) 
  {
    return Stream.of(expression.first, expression.second);
  }
  
  public Stream<Expression> visit(UnaryExpression expression)
  {
    return Stream.of(expression.argument);
  }

  public Stream<Expression> visit(AndExpression expression)
  {
    return visit((BinaryExpression)expression);
  }

  public Stream<Expression> visit(NotExpression expression)
  {
    return visit((UnaryExpression)expression);
  }
  
  public Stream<Expression> visit(OrExpression expression)
  {
    return visit((BinaryExpression)expression);
  }

  public Stream<Expression> visit(RefExpression expression)
  {
    return Stream.empty();
  }
}
