package com.nesterovskyBros.visitor;

import static org.junit.Assert.*;

import java.util.function.Function;

import org.junit.Test;

public class EvaluatorTest
{
  @Test
  public void testResolve()
    throws Exception
  {
    Expression expression = createExpression();
    Boolean result = evaluate(expression, EvaluatorTest::resolve);
    
    assertTrue(result);

    result = evaluateClassic(expression, EvaluatorTest::resolve);
    
    assertTrue(result);
}

  public static Boolean evaluate(
    Expression expression, 
    Function<String, Boolean> resolver)
    throws Exception
  {
    Evaluator evaluator = createEvaluator(resolver);
    Traverser traverser = new Traverser();
    Iterable<Expression> iterable = 
      traverser.traverseFirstStream(expression)::iterator;
    
    for(Expression e: iterable)
    {
      e.accept(evaluator);
    }
    
    return evaluator.result();
  }
  
  public static Boolean evaluateClassic(
    Expression expression, 
    Function<String, Boolean> resolver)
    throws Exception
  {
    Evaluator evaluator = createEvaluator(resolver);
    TraversingVisitor<Void, Exception> traverser = 
      new TraversingVisitor<>(evaluator, true); 
    
    expression.accept(traverser);
    
    return evaluator.result();
  }

  public static Evaluator createEvaluator(Function<String, Boolean> resolver)
  {
    return new Evaluator()
    {
      @Override
      protected Boolean resolve(String name)
        throws Exception
      {
        return resolver.apply(name);
      }
    };
  }
  
  public static Expression createExpression()
  {
    AndExpression and = new AndExpression();
    RefExpression a = new RefExpression();
    RefExpression b = new RefExpression();
    
    a.name = "a";
    b.name = "b";
    and.first = a;
    and.second = b;
    
    return and;
  }

  private static Boolean resolve(String name)
  {
    switch(name)
    {
      case "a":
      case "b":
      case "c":
      {
        return true;
      }
    }
    
    return false;
  }
}
