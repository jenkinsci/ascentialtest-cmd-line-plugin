![](/docs/img/title.png)
====================

-   [Plugin Information](#About)
-   [Prepare Nodes](#Prepare-Nodes)
-   [Usage](#Usage)
-   [Plot Results](#Plot-Results)
-   [More Information](#More-Information)
-   [Technical Support](#Technical-Support)

## *About*  

A Zeenyx plugin for running AscentialTest tests from Jenkins.  

The plugin provides a build step that lets you execute your AscentialTest test sets, suites, plans and tests in your Jenkins freestyle jobs and Pipelines. In addition, the plugin keeps a summary of test results that can be graphed using the Plot plug-in directly from Jenkins.  

## *Prepare Nodes*  

Prepare a test computer (node) for automated testing. Make sure the node has everything it needs to run tests successfully:  

    - AscentialTest and UA Server are installed.
    - The target applications are installed.
    - AscentialTest project files are installed.

## *Usage*  

    -Freestyle Jobs  
    -Plot Results  


### *To run your TestComplete tests as part of a Jenkins job:*  

    Add the AscentialTest build step to your Jenkins job.  
    Configure the step:  
        Select the run type: TestSet, Suite, Plan or Test.  
        Specify the AscentialTest project to be run.  
        Configure plugin options for checkout, query, target computers, reporting and email.  
    Run the build as you normally would.  


## *Plot Results*  

After the build is complete, you can view the AscentialTest Results by clicking on the Plot link.  

## More Information  

You can find complete information on using the plugin in AscentialTest documentation.

## Technical Support  

If you have any questions or need assistance with configuring the plugin, please contact the Zeenyx Support Team.  