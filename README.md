# tailee
Simple command line tool to tail a log file until something interesting happens

Is very useful for automated deployment scenarios, for instance deploying an application from Jenkins

### Usage
Start your application and ensure the output is being output to a file
```bash
myApplication >> logfile
java -jar tailee.jar -file logfile \
    -finish "application is now running..." \
    -fail "file not found" \
    -fail "java.lang.NullPointerException"
```

###Notes
Accepts unlimited number of finish and fail text
tailee will close after 60 minutes ( this is hard coded but am willing to pull it out to a parameter on request )

####Why java?
Wanted to do this in bash, and oh boy did I try but it was too complicated (and verbose)
Deployment of java was the easiest solution at the time, could easily be ported to grooovy
