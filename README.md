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
  <p>As with SAX vs XML Reader or more generally push vs pull processing models there is no the best one. One or the other is preferrable in particular circumstances. E.g. Pull like component fits into a transofrmation pipeline where one pull component has another as its source; another example is when one needs to process two sources at once, which is untrivial with push like model. On the other hand push processing fits better into Reduce part of <a href="https://en.wikipedia.org/wiki/MapReduce">MapReduce</a> pattern where you need to accumulate results from source.</p>
  <p>So, our idea was to complete classic push-like visitor pattern with an example of pull-like implementation.</p>
  <p>For the demostration we have selected Java language, and a simplest boolean expression calculator. Please note that we start from object graph, so we put expression parsing aside.</p>
