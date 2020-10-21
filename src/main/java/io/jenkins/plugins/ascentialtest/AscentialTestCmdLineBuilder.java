package io.jenkins.plugins.ascentialtest;

import hudson.EnvVars;
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
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.kohsuke.stapler.DataBoundSetter;
import java.util.ArrayList;
import hudson.util.ArgumentListBuilder;
import hudson.model.Result;
import io.jenkins.plugins.sample.Messages;
import java.io.File;
import java.util.concurrent.TimeUnit;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Scanner;


public class AscentialTestCmdLineBuilder extends Builder implements SimpleBuildStep {

    private final String installPath, projectPath, projectName, runTypeName, runType, exportDir, reportFormat, summaryAttribute, outputLevel, checkoutURL, checkoutPath, checkoutRev, queryExpression, testArgs, targetComputers;
    private final boolean email, report1, report2, report3, report4, runOnlyFailed;
    private ArgumentListBuilder ATCommandLine, AtReportsCommandLine1, AtReportsCommandLine2, AtReportsCommandLine3, AtReportsCommandLine4;
   
    @DataBoundConstructor
    public AscentialTestCmdLineBuilder(String installPath, String projectPath, String projectName, String runTypeName, String ATCommandLine, String AtReportsCommandLine, String runType, Boolean report1 , Boolean report2 , Boolean report3 , Boolean report4, String exportDir, String reportFormat, String summaryAttribute, String outputLevel, Boolean email, String checkoutURL, String checkoutPath, String checkoutRev, Boolean runOnlyFailed, String queryExpression, String testArgs, String targetComputers ) {
        this.installPath = installPath;
        this.projectPath = projectPath;
        this.projectName = projectName;
        this.runTypeName = runTypeName;
        this.runType = runType;
        this.report1 = report1;
        this.report2 = report2;
        this.report3 = report3;
        this.report4 = report4;
        this.exportDir = exportDir;
        this.reportFormat = reportFormat;
        this.summaryAttribute = summaryAttribute;
        this.outputLevel = outputLevel;
        this.email = email;
        this.checkoutURL = checkoutURL;
        this.checkoutPath = checkoutPath;
        this.checkoutRev = checkoutRev;
        this.queryExpression = queryExpression;
        this.runOnlyFailed = runOnlyFailed;
        this.testArgs = testArgs;
        this.targetComputers = targetComputers;
        }
   
    public String getinstallPath() {
        return installPath;
    }
    public String getprojectPath () {
        return projectPath;
    }
    public String getprojectName () {
        return projectName;
    }
    public String getrunType () {
        return runType;
    }
    public String getrunTypeName () {
        return runTypeName;
    }
    public Boolean getreport1 () {
        return report1;
    }
    public Boolean getreport2 () {
        return report2;
    }
    public Boolean getreport3 () {
        return report3;
    }
    public Boolean getreport4 () {
        return report4;
    }
    public String getexportDir () {
        return exportDir;
    }
    public String getreportFormat () {
        return reportFormat;
    }
    public String getsummaryAttribute () {
        return summaryAttribute;
    }
    public String getoutputLevel () {
        return outputLevel;
    }
    public Boolean getemail () {
        return email;
    }
    public String getcheckoutURL () {
        return checkoutURL;
    }
    public String getcheckoutPath () {
        return checkoutPath;
    }
    public String getcheckoutRev () {
        return checkoutRev;
    }
    public Boolean getrunOnlyFailed () {
        return runOnlyFailed;
    }
    public String getqueryExpression () {
        return queryExpression;
    }
    public String gettestArgs () {
        return testArgs;
    }
    public String gettargetComputers () {
        return targetComputers;
    }
    public ArgumentListBuilder getATCommandLine(FilePath workspace) {
        ATCommandLine  = new ArgumentListBuilder();
        ATCommandLine.add(installPath+"\\atrun.exe");

        if (checkoutURL !=null && !checkoutURL.isEmpty()){
            ATCommandLine.add("-checkout");
            ATCommandLine.add(checkoutURL);
            ATCommandLine.add(checkoutPath);
            if (checkoutRev !=null && !checkoutRev.isEmpty()){
                ATCommandLine.add("-rev");
                ATCommandLine.add(checkoutRev);
            }
        }
        else {
            ATCommandLine.add(projectPath+"\\"+projectName+".zProject");
        }        
        
        if (queryExpression !=null && !queryExpression.isEmpty()){
            ATCommandLine.add("-query");
            ATCommandLine.add(queryExpression);
        }
        else {
            if (runOnlyFailed) {
                ATCommandLine.add("-query");
                ATCommandLine.add("HasError=true");
            }
        }

        if (targetComputers !=null && !targetComputers.isEmpty()){
            ATCommandLine.add("-target");
            
            ATCommandLine.addTokenized(targetComputers);
        }
                
        ATCommandLine.add("-"+runType);
        ATCommandLine.add(runTypeName);
        
        ATCommandLine.add("-runcmd");
        ATCommandLine.add(workspace+"\\RemoveLocks.cmd");       
        return ATCommandLine;
    }
    public ArgumentListBuilder getATCommandLineForRunTypeTest(FilePath workspace) {
        ATCommandLine  = new ArgumentListBuilder();
        ATCommandLine.add("cmd.exe");
        ATCommandLine.add("/c");
        
        ATCommandLine.add("call");
        ATCommandLine.add(installPath+"\\atrun.exe");
        if (checkoutURL !=null &&!checkoutURL.isEmpty()){
            ATCommandLine.add("-checkout");
            ATCommandLine.add(checkoutURL);
            ATCommandLine.add(checkoutPath);
            if (checkoutRev !=null && !checkoutRev.isEmpty()){
                ATCommandLine.add("-rev");
                ATCommandLine.add(checkoutRev);
            }
        }
        else {
            ATCommandLine.add(projectPath+"\\"+projectName+".zProject");
        }              
        
        if (testArgs != null && !testArgs.isEmpty()){
            ATCommandLine.add(testArgs);
        }
         
        if (targetComputers !=null && !targetComputers.isEmpty() ){
            ATCommandLine.add("-target");            
            ATCommandLine.addTokenized(targetComputers);
        }
               
        ATCommandLine.add("-"+runType);
        ATCommandLine.add(runTypeName);
        
        ATCommandLine.add(">");
        ATCommandLine.add(workspace+"\\Output.txt");

        return ATCommandLine;
    }
    public ArgumentListBuilder getAtReports1CommandLine() {
        AtReportsCommandLine1  = new ArgumentListBuilder();
        AtReportsCommandLine1.add(installPath+"\\ATReports\\atreports.exe");
        AtReportsCommandLine1.add("-TestSetName");
         AtReportsCommandLine1.add(runTypeName);
       if (report1) {
        AtReportsCommandLine1.add("-Report");    
        AtReportsCommandLine1.add("TestSetOverview");    
        AtReportsCommandLine1.add("-Export");    
        AtReportsCommandLine1.add(exportDir+"\\TestSetOverview."+reportFormat);    
        AtReportsCommandLine1.add("-Format");    
        AtReportsCommandLine1.add(reportFormat);    
        } 
        if (email) {
        AtReportsCommandLine1.add("-Email");
        }
        return AtReportsCommandLine1;
    }
    public ArgumentListBuilder getAtReports2CommandLine() {
        AtReportsCommandLine2  = new ArgumentListBuilder();
        AtReportsCommandLine2.add(installPath+"\\ATReports\\atreports.exe");
        AtReportsCommandLine2.add("-TestSetName");
         AtReportsCommandLine2.add(runTypeName);
        if (report2) {
        AtReportsCommandLine2.add("-Report");    
        AtReportsCommandLine2.add("TestSetSummary");    
        AtReportsCommandLine2.add("-Export");    
        AtReportsCommandLine2.add(exportDir+"\\TestSetSummary."+reportFormat);    
        AtReportsCommandLine2.add("-Format");    
        AtReportsCommandLine2.add(reportFormat);    
        } 
        if (email) {
        AtReportsCommandLine2.add("-Email");
        }
        return AtReportsCommandLine2;
    }
    public ArgumentListBuilder getAtReports3CommandLine() {
        AtReportsCommandLine3  = new ArgumentListBuilder();
        AtReportsCommandLine3.add(installPath+"\\ATReports\\atreports.exe");
        AtReportsCommandLine3.add("-TestSetName");
         AtReportsCommandLine3.add(runTypeName);
        if (report3) {
        AtReportsCommandLine3.add("-Report");    
        AtReportsCommandLine3.add("TestSetSummaryAttr");    
        AtReportsCommandLine3.add("-SummaryAttr");    
        AtReportsCommandLine3.add(summaryAttribute);    
        AtReportsCommandLine3.add("-Export");    
        AtReportsCommandLine3.add(exportDir+"\\TestSetSummaryAttr."+reportFormat);    
        AtReportsCommandLine3.add("-Format");    
        AtReportsCommandLine3.add(reportFormat);    
        } 
        if (email) {
        AtReportsCommandLine3.add("-Email");
        }
        return AtReportsCommandLine3;
    }
    public ArgumentListBuilder getAtReports4CommandLine() {
        AtReportsCommandLine4  = new ArgumentListBuilder();
        AtReportsCommandLine4.add(installPath+"\\ATReports\\atreports.exe");
        AtReportsCommandLine4.add("-TestSetName");
         AtReportsCommandLine4.add(runTypeName);
        if (report4) {
        AtReportsCommandLine4.add("-Report");    
        AtReportsCommandLine4.add("TestSetDetailed");    
        AtReportsCommandLine4.add("-OutputLevel");    
        AtReportsCommandLine4.add(outputLevel);    
        AtReportsCommandLine4.add("-Export");    
        AtReportsCommandLine4.add(exportDir+"\\TestSetDetailed."+reportFormat);    
        AtReportsCommandLine4.add("-Format");    
        AtReportsCommandLine4.add(reportFormat);    
        } 
        if (email) {
        AtReportsCommandLine4.add("-Email");
        }
        return AtReportsCommandLine4;
    }

    @DataBoundSetter

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        
        EnvVars envVars;
        envVars = run.getEnvironment(listener);
        envVars.put("WORKSPACE", workspace.toString());
        ///*toString*/envVars.put("RUN",run.toString());
        String buildURL = run.getUrl();
        
        if (runType.equals("testset")){
            CreateLockFile (workspace, buildURL);
               
            ArgumentListBuilder CmdArgs = new ArgumentListBuilder();
            CmdArgs.add("cmd.exe");
            CmdArgs.add("/c");
            CmdArgs.add("echo");
            CmdArgs.add("Fail File");
            CmdArgs.add(">");
            CmdArgs.add(workspace+"\\fail.lock");
            launcher.launch().cmds(CmdArgs).stdout(listener).join();

            CmdArgs = new ArgumentListBuilder();
            CmdArgs.add("cmd.exe");
            CmdArgs.add("/c");
            CmdArgs.add("echo");
            CmdArgs.add("Test File");
            CmdArgs.add(">");
            CmdArgs.add(workspace+"\\test.lock");
            launcher.launch().cmds(CmdArgs).stdout(listener).join();
        }
        if (!runType.equals("test")){
            launcher.launch().envs(envVars).cmds(getATCommandLine(workspace)).stdout(listener).join();
        }
        else {
            launcher.launch().envs(envVars).cmds(getATCommandLineForRunTypeTest(workspace)).stdout(listener).join();
        }
        if (runType.equals("testset")){
            File TestLockFile = new File(workspace+"\\test.lock");
            while (true) {
                boolean bExists = TestLockFile.exists();
                if (!bExists){
                break;
                }
                TimeUnit.SECONDS.sleep(10);
            }
        }
               
        if (runType.equals("testset")){
            if (report1) {    
            launcher.launch().cmds(getAtReports1CommandLine()).stdout(listener).join();
            }
            
            if (report2) {    
            launcher.launch().cmds(getAtReports2CommandLine()).stdout(listener).join();
            }

            if (report3) {    
            launcher.launch().cmds(getAtReports3CommandLine()).stdout(listener).join();
            }

            if (report4) {    
            launcher.launch().cmds(getAtReports4CommandLine()).stdout(listener).join();
            }
        }

        if (runType.equals("testset")){
            File FailLockFile = new File(workspace+"\\fail.lock");
            boolean bFailFileExists = FailLockFile.exists();       
            if (bFailFileExists){
                run.setResult(Result.FAILURE);               
            } 
        }

        if (runType.equals("test")){
              ArrayList<String> OutputList = new ArrayList<String>();
              try {
                Scanner TestOutputFile = new Scanner(new File(workspace+"\\Output.txt"), "UTF-8");
                
                while (TestOutputFile.hasNextLine()){       
                    OutputList.add(TestOutputFile.nextLine());             
                }
                TestOutputFile.close();          
               }
                catch(Exception e){
                   System.out.println("Exception occurred");
                   e.printStackTrace();
                }
                                             
            ArgumentListBuilder CmdArgs = new ArgumentListBuilder();
            CmdArgs.add("cmd.exe");
            CmdArgs.add("/c");
            CmdArgs.add("echo");
            CmdArgs.addTokenized(OutputList.get(0));
            CmdArgs.add("&&");
            CmdArgs.add("echo");
            CmdArgs.addTokenized(OutputList.get(1));
            CmdArgs.add("&&");
            CmdArgs.add("echo");
            CmdArgs.addTokenized(OutputList.get(2));
            CmdArgs.add("&&");
            CmdArgs.add("echo");
            CmdArgs.addTokenized(OutputList.get(3));
            CmdArgs.add("&&");
            CmdArgs.add("echo");
            CmdArgs.addTokenized(OutputList.get(4));

            launcher.launch().cmds(CmdArgs).stdout(listener).join();
            
            if (!OutputList.get(3).equals("NumErrors: 0")){
                run.setResult(Result.FAILURE);
            }
        }
    }

    private void CreateLockFile(FilePath workspace, String BUILD_URL) {
        try{
    	   File file = new File(workspace+"\\RemoveLocks.cmd");
    	   if(file.delete()){
    	      System.out.println(file.getName() + " is deleted!");
           }else{
    	      System.out.println("Delete failed: File didn't delete");
    	    }
        }
        catch(Exception e){
           System.out.println("Exception occurred");
    	   e.printStackTrace();
        }
           
        try {
        File LockFile = new File(workspace+"\\RemoveLocks.cmd");
        if (LockFile.createNewFile()) {
            System.out.println("File created: " + LockFile.getName());
        
            Writer w = new OutputStreamWriter(new FileOutputStream(LockFile), "UTF-8");
            BufferedWriter writer = new BufferedWriter(w);

            writer.append("if %NumFailed% EQU 0 del \""+workspace+"\\fail.lock\"");
            writer.append("\n");
            writer.append("if exist \""+workspace+"\\test.lock\" del \""+workspace+"\\test.lock\"");
            writer.append("\n");
            writer.append(">> \""+workspace+"\\ATPassed.properties\" echo YVALUE=%NumPassed%");
            writer.append("\n");
            writer.append(">> \""+workspace+"\\ATPassed.properties\" echo URL="+BUILD_URL+"Passed");
            writer.append("\n");
            writer.append(">> \""+workspace+"\\ATFailed.properties\" echo YVALUE=%NumFailed%");
            writer.append("\n");
            writer.append(">> \""+workspace+"\\ATFailed.properties\" echo URL="+BUILD_URL+"Failed");
            writer.append("\n");
            writer.append(">> \""+workspace+"\\ATBlocked.properties\" echo YVALUE=%NumBlocked%");
            writer.append("\n");
            writer.append(">> \""+workspace+"\\ATBlocked.properties\" echo URL="+BUILD_URL+"Blocked");
            writer.append("\n");
            writer.append(">> \""+workspace+"\\ATNotRun.properties\" echo YVALUE=%NumNotRun%");
            writer.append("\n");
            writer.append(">> \""+workspace+"\\ATNotRun.properties\" echo URL="+BUILD_URL+"NumNotRun");           
            writer.close();
        } 
        else {
        System.out.println("File already exists.");
        }
      } 
      catch (IOException e) {
        System.out.println("An error occurred.");
        e.printStackTrace();
      }
    }

    @Symbol("ascentialtest")
    @Extension
    public static final class DescriptorImpl extends BuildStepDescriptor<Builder> {


        public FormValidation doCheckInstallPath(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.HelloWorldBuilder_DescriptorImpl_errors_missingName());          
            return FormValidation.ok();
        }
        public FormValidation doCheckProjectPath(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.HelloWorldBuilder_DescriptorImpl_errors_missingName());          
            return FormValidation.ok();
        }
        public FormValidation doCheckProjectName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.HelloWorldBuilder_DescriptorImpl_errors_missingName());          
            return FormValidation.ok();
        }
        public FormValidation doCheckRunTypeName(@QueryParameter String value)
                throws IOException, ServletException {
            if (value.length() == 0)
                return FormValidation.error(Messages.HelloWorldBuilder_DescriptorImpl_errors_missingName());          
            return FormValidation.ok();
        }
        /*public ListBoxModel doFillRunTypeItems() {
            ListBoxModel items = new ListBoxModel();
            items.add("testset", "testset");
            items.add("suite", "suite");
            items.add("plan", "plan");
            items.add("test", "test");
            return items;
        }*/             
        
        @Override
        public boolean isApplicable(Class<? extends AbstractProject> aClass) {
            return true;
        }

        @Override
        public String getDisplayName() {
            return Messages.HelloWorldBuilder_DescriptorImpl_DisplayName();
        }
                
    }
}
