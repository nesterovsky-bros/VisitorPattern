<h2>Pull Visitor Pattern</h2>
<p><a href="https://en.wikipedia.org/wiki/Visitor_pattern">Visitor pattern</a> is often used to separate operation from object graph it operates with. Here we assume that the reader is familiar with the subject.</p>
<p>The idea is like this:</p>
<ul>
  <li>The operation over object graph is implemented as type called <code>Visitor</code>.</li>
  <li><code>Visitor</code> defines methods for each type of object in the graph, which a called during traversing of the graph.</li>
  <li>Traversing over the graph is implemented by a type called <code>Traverser</code>, or by the <code>Visitor</code> or by each object type in the graph.</li>
</ul>
<p>Implementation should collect, aggregate or perform other actions during visit of objects in the graph, so that at the end of the visit the purpose of operation will be complete.</p>
<p>Such implementation is push-like: you create operation object and call a method that gets object graph on input and returns operation result on output.</p>
<p style="direction: ltr">In the past we often dealt with big graphs (usually these are virtual graphs backended at database or at a file system).</p>
<p>Also having a strong experience in the XSLT we see that the visitor pattern in OOP is directly mapped into <code>xsl:template</code> and <code>xsl:apply-template</code> technique.</p>
<p>Another thought was that in XML processing there are two camps:</p>
<ul>
  <li>SAX (push-like) - those who process xml in callbacks, which is very similar to visitor pattern; and</li>
  <li>XML Reader (pull-like) - those who pull xml components from a source, and then iterate and process them.</li>
</ul>
<p>As with SAX vs XML Reader or, more generally, push vs pull processing models, there is no the best one. One or the other is preferable in particular circumstances. E.g. Pull like component fits into a transformation pipeline where one pull component has another as its source; another example is when one needs to process two sources at once, which is untrivial with push like model. On the other hand push processing fits better into Reduce part of <a href="https://en.wikipedia.org/wiki/MapReduce">MapReduce</a> pattern where you need to accumulate results from source.</p>
<p>So, our idea was to complete classic push-like visitor pattern with an example of pull-like implementation.</p>
<p>For the demostration we have selected Java language, and a simplest boolean expression calculator. Please note that we start from object graph, so we put expression parsing aside.</p>

<p>
  So, this is the object graph:</p>
<blockquote><pre>public interface Expression
{
  public &lt;R> R accept(Visitor&lt;R> visitor);
}

public abstract class UnaryExpression implements Expression
{
  public Expression argument;
}

public abstract class BinaryExpression implements Expression
{
  public Expression first;
  public Expression second;
}

public class AndExpression extends BinaryExpression
{
  public &lt;R> R accept(Visitor&lt;R> visitor)
  {
    return visitor.visit(this);
  }
}

public class OrExpression extends BinaryExpression
{
  public &lt;R> R accept(Visitor&lt;R> visitor)
  {
    return visitor.visit(this);
  }
}

public class NotExpression extends UnaryExpression
{
  public &lt;R> R accept(Visitor&lt;R> visitor)
  {
    return visitor.visit(this);
  }
}

public class RefExpression implements Expression
{
  public String name;

  public &lt;R> R accept(Visitor&lt;R> visitor)
  {
    return visitor.visit(this);
  }
} </pre></blockquote>
<p>and a <code>Visitor</code> interface:</p>
<blockquote><pre>public interface Visitor&lt;R>
{
  R visit(AndExpression expression);
  R visit(NotExpression expression);
  R visit(OrExpression expression);
  R visit(RefExpression expression);
}</pre></blockquote>
<p>Now, let&#39;s define an <code>Evaluator</code> - a visitor to calculate expression value:</p>
<blockquote><pre>public abstract class Evaluator implements Visitor&lt;Void>
{
  public boolean result()
  {
    return stack.pop(); 
  }
  
  public Void visit(AndExpression expression)
  {
    boolean second = stack.pop();
    boolean first = stack.pop();

    stack.push(first && second);
    
    return null;
  }

  public Void visit(NotExpression expression)
  {
    stack.push(!stack.pop());
    
    return null;
  }
  
  public Void visit(OrExpression expression)
  {
    boolean second = stack.pop();
    boolean first = stack.pop();

    stack.push(first || second);
    
    return null;
  }

  public Void visit(RefExpression expression)
  {
    stack.push(resolve(expression.name));
    
    return null;
  }

  public abstract boolean resolve(String name);
  public ArrayDeque&lt;Boolean> stack = new ArrayDeque<>();  
}
</pre></blockquote>
<p>These are the common parts between push and pull implementations.</p>
<p>At this point we should deal with traversing logic, which in case of push-like implementation recursively calls visitors in the graph; and in case of pull-like implementation builds an iterator over graph.</p>
<p>So, this is push-like <code>TraversingVisitor</code>:</p>
<blockquote><pre>public class TraversingVisitor&lt;R> implements Visitor&lt;R>
{
  public TraversingVisitor(Visitor&lt;R> visitor)
  {
    this.visitor = visitor;
  }
  
  public TraversingVisitor(Visitor&lt;R> visitor, boolean traverseFirst)
  {
    this.visitor = visitor;
    this.traverseFirst = traverseFirst;    
  }
  
  public void traverse(BinaryExpression expression)
  {
    expression.first.accept(this);
    expression.second.accept(this);
  }
  
  public void traverse(UnaryExpression expression)
  {
    expression.argument.accept(this);
  }
    
  public void traverse(AndExpression expression)
  {
    traverse((BinaryExpression)expression);
  }
  
  public void traverse(NotExpression expression)
  {
    traverse((UnaryExpression)expression);
  }
  
  public void traverse(OrExpression expression)
  {
    traverse((BinaryExpression)expression);
  }

  public void traverse(RefExpression expression)
  {
  }

  public R visit(AndExpression expression)
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
  
  public R visit(NotExpression expression)
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

  public R visit(OrExpression expression)
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
  
  public R visit(RefExpression expression)
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

  protected Visitor&lt;R> visitor;
  protected boolean traverseFirst;
}</pre></blockquote>
<p>And this is pull-like Traverser that provides <a href="https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html">Stream</a> over the graph.</p>
<blockquote><pre>public class Traverser implements Visitor&lt;Stream&lt;Expression>>
{
  public Stream&lt;Expression> stream(Expression expression)
  {
    return Stream.concat(
      Stream.of(expression), 
      expression.accept(this).flatMap(this::stream));
  }
  
  public Stream&lt;Expression> traverseFirstStream(Expression expression)
  {
    return Stream.concat(
      expression.accept(this).flatMap(this::traverseFirstStream),
      Stream.of(expression));
  }
  
  public Stream&lt;Expression> visit(BinaryExpression expression) 
  {
    return Stream.of(expression.first, expression.second);
  }
  
  public Stream&lt;Expression> visit(UnaryExpression expression)
  {
    return Stream.of(expression.argument);
  }

  public Stream&lt;Expression> visit(AndExpression expression)
  {
    return visit((BinaryExpression)expression);
  }

  public Stream&lt;Expression> visit(NotExpression expression)
  {
    return visit((UnaryExpression)expression);
  }
  
  public Stream&lt;Expression> visit(OrExpression expression)
  {
    return visit((BinaryExpression)expression);
  }

  public Stream&lt;Expression> visit(RefExpression expression)
  {
    return Stream.empty();
  }
}</pre></blockquote>
<p>Now we have all components needed to evaluate the expression:</p>
<blockquote><pre>  public static boolean evaluatePull(Expression expression, Evaluator evaluator)
  {
    Traverser traverser = new Traverser();
    Iterable&lt;Expression> iterable = traverser.traverseFirstStream(expression)::iterator;
    
    for(Expression e: iterable)
    {
      e.accept(evaluator);
    }
    
    return evaluator.result();
  }
  
  public static boolean evaluatePush(Expression expression, Evaluator evaluator)
  {
    TraversingVisitor&lt;Void> traverser = new TraversingVisitor<>(evaluator, true); 
    
    expression.accept(traverser);
    
    return evaluator.result();
  }</pre></blockquote>
