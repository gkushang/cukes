cukesRepo
=========

<br/>


### Important

The prefered IDE is IntelliJ IDEA 12. Dependency and project setup sections are written for IDEA.

<br/>


### Running the project

1. Click Run and then Edit Configurations…

2. Set the configuration name (e.g. CukesRepo) and tick the *Single instance only* checkbox

3. Add a *Make* configuration to the *Before launch* section

4. Under the Parameters tab

    4.1. Set the Working directory path to your project's root directory

    4.2. Populate the *Command line* text box with **jetty:run**

5. Under the Runner tab
    
    5.1. Set the following VM Options:
    
        -Djetty.port=[port]
        -Denv.props.path=[tokens file path]
        
    5.2. Set the JRE to v1.7

6. Save and run the configuration

<br/>

