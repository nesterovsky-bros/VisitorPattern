  <p>
    Pull Visitor Pattern</p>
  <p>
    <a href="https://en.wikipedia.org/wiki/Visitor_pattern">Visitor pattern</a> is often used to separate operation from object graph it operates with. 
    Here we assume that the read is familiar with the subject.</p>
  <p>
    The idea is like this:</p>
  <ul>
    <li>The operation over object graph is implemented as type called <code>Visitor</code>.</li>
    <li><code>Visitor</code> defines methods for each type of object in the graph, which a called during traversing of the graph.</li>
    <li>Traversing over the graph is implemented by the type called <code>Traverser</code>, or by the <code>Visitor</code> or by each object type in the graph.</li>
  </ul>

  <p>
    Implementation should collect, aggregate or perform other actions during visit of objects in the graph, so that at the end of the visit the purpose of operation will be complete.</p>

  <p>
    Such implementation is &quot;push-like&quot;: you create operation object and call a method that gets object graph on input and returns operation result on output.</p>
  <p>
    In the past we often dealt with big graphs (often these are virtual graphs usually backended at database or in a file system). </p>
  <p>
    Also having a strong experience in the XSLT we see that the visitor pattern in OOP is directly mapped into <code>xsl:template</code> and <code>xsl:apply-template</code> technique. </p>
  <p>
    Another thought was that in XML processing there are two camps: </p>
  <ul>
    <li>SAX (push-like) - those who process xml in callbacks, which is very similar to visitor pattern;</li>
    <li>XML Reader (pull-like) - those who pull xml elements from source, and then iteratively process the whole.</li>
  </ul>
  <p>
    So, our idea was to complete classic &quot;push-like&quot; visitor pattern with an example of &quot;pull-like&quot; implementation.</p>
  <p>
    For the demostration we have selected Java language, and a simplest boolean expression calculator. Please note that we start from object graph, so we put expression parsing aside.</p>
