package io.jenkins.plugins.AccuKnoxCLI;

import hudson.Launcher;
import hudson.Extension;
import hudson.FilePath;
import hudson.util.FormValidation;
import hudson.model.AbstractProject;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;

import javax.servlet.ServletException;
import java.io.IOException;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;
import java.io.*;
import java.net.*;
import java.util.Scanner;

public class KnoxAutoPol extends Builder implements SimpleBuildStep {

    private final String keyword;
    private final String tag;
    private boolean useAutoApply;
    private boolean doNotPersist;
    private static String OS = System.getProperty("os.name").toLowerCase();
    private static String query;

    @DataBoundConstructor
    public KnoxAutoPol(String keyword, String tag) {
        this.keyword = keyword;
        this.tag = tag;
    }

    public String getKeyword() {
        return keyword;
    }
    public String getTag() {
        return tag;
    }

    public boolean isAutoApply() {
        return useAutoApply;
    }
    public boolean isDoNotPersist() {
        return doNotPersist;
    }

    @DataBoundSetter
    public void setUseAutoApply(boolean useAutoApply) {
        this.useAutoApply = useAutoApply;
    }
    @DataBoundSetter
    public void setDoNotPersist( boolean doNotPersist) {
        this.doNotPersist = doNotPersist;
    }

    public static void downloader(String url,String file_name) {

        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
        FileOutputStream fileOutputStream = new FileOutputStream(file_name)) {
            byte dataBuffer[] = new byte[1024];
            int bytesRead;
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                fileOutputStream.write(dataBuffer, 0, bytesRead);
            }
        } catch (IOException e) {
        // handle exception
        }      
  }

  public static String execCmd(String cmd) {
    String result = null;
    try (InputStream inputStream = Runtime.getRuntime().exec(cmd).getInputStream();
            Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
        result = s.hasNext() ? s.next() : null;
    } catch (IOException e) {
        e.printStackTrace();
    }
    return result;
}

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        if (OS.contains("win")) {
            downloader("https://github.com/vishnusomank/policy-cli/raw/main/knox_autopol.exe","knox_autopol.exe");
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
            System.out.println("keyword : "+keyword+"\nTag : "+tag+"\nPersist : "+doNotPersist+"\nAuto Apply : "+useAutoApply); 
            if ( (keyword == null) && (tag != null) ) {
                query = System.getProperty("user.dir") + "\\knox_autopol.exe --tags="+tag+" --persist="+doNotPersist+" --auto-apply="+useAutoApply;
            } else if ( (keyword != null) && (tag == null) ){
                query = System.getProperty("user.dir") + "\\knox_autopol.exe --keyword="+keyword+" --persist="+doNotPersist+" --auto-apply="+useAutoApply;
            } else if ( (keyword != null) && (tag != null) ){ 
                query = System.getProperty("user.dir") + "\\knox_autopol.exe --keyword="+keyword+" --tags="+tag+" --persist="+doNotPersist+" --auto-apply="+useAutoApply;
            } else {
                query = System.getProperty("user.dir") + "\\knox_autopol.exe --help";
            }
            listener.getLogger().println(execCmd(query));

            
        } else {
            downloader("https://github.com/vishnusomank/policy-cli/raw/main/knox_autopol","knox_autopol"); 
            System.out.println("Working Directory = " + System.getProperty("user.dir")); 
            File file = new File(System.getProperty("user.dir")+"/knox_autopol");
            file.setExecutable(true, false);
		    file.setReadable(true, false);
		    file.setWritable(true, false);
            System.out.println("keyword : "+keyword+"\nTag : "+tag+"\nPersist : "+doNotPersist+"\nAuto Apply : "+useAutoApply); 
            if ( (keyword == null) && (tag != null) ) {
                query = System.getProperty("user.dir") + "/knox_autopol --tags="+tag+" --persist="+doNotPersist+" --auto-apply="+useAutoApply;
            } else if ( (keyword != null) && (tag == null) ){
                query = System.getProperty("user.dir") + "/knox_autopol --keyword="+keyword+" --persist="+doNotPersist+" --auto-apply="+useAutoApply;
            } else if ( (keyword != null) && (tag != null) ){ 
                query = System.getProperty("user.dir") + "/knox_autopol --keyword="+keyword+" --tags="+tag+" --persist="+doNotPersist+" --auto-apply="+useAutoApply;
            } else {
                query = System.getProperty("user.dir") + "/knox_autopol --help";
            }
            listener.getLogger().println(execCmd(query));
       
        }
    }

    @Symbol("KnoxAutoPol")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return "AccuKnox CLI";
        }

    }

}
