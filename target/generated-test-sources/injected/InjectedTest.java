import java.util.*;
/**
 * Entry point to auto-generated tests (generated by maven-hpi-plugin).
 * If this fails to compile, you are probably using Hudson &lt; 1.327. If so, disable
 * this code generation by configuring maven-hpi-plugin to &lt;disabledTestInjection&gt;true&lt;/disabledTestInjection&gt;.
 */
public class InjectedTest extends junit.framework.TestCase {
  public static junit.framework.Test suite() throws Exception {
    System.out.println("Running tests for "+"io.jenkins.plugins:knoxautopol:1.0.0");
    Map<String, Object> parameters = new HashMap<String, Object>();
    parameters.put("basedir","C:\\Users\\vsk90\\OneDrive\\Desktop\\AccuKnox\\poc\\jenkins-plugin\\knoxautopol-plugin");
    parameters.put("artifactId","knoxautopol");
    parameters.put("packaging","hpi");
    parameters.put("outputDirectory","C:\\Users\\vsk90\\OneDrive\\Desktop\\AccuKnox\\poc\\jenkins-plugin\\knoxautopol-plugin\\target\\classes");
    parameters.put("testOutputDirectory","C:\\Users\\vsk90\\OneDrive\\Desktop\\AccuKnox\\poc\\jenkins-plugin\\knoxautopol-plugin\\target\\test-classes");
    parameters.put("requirePI","true");
    return org.jvnet.hudson.test.PluginAutomaticTestBuilder.build(parameters);
  }
}
