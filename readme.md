# A simple, minimal Maven example: hello world

To create the files in this git repo we've already run `mvn archetype:generate` from http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html
    
    mvn archetype:generate -DgroupId=com.myapp.app -DartifactId=myapp -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false

Now, to print "Hello World!", type either...

    cd myapp
    mvn compile
    java -cp target/classes com.myapp.app.App

or...

    cd myapp
    mvn package
    java -cp target/myapp-1.0-SNAPSHOT.jar com.myapp.app.App

Running `mvn clean` will get us back to only the source Java and the `pom.xml`:

    murphy:myapp pdurbin$ mvn clean --quiet
    murphy:myapp pdurbin$ ack -a -f
    pom.xml
    src/main/java/com/myapp/app/App.java
    src/test/java/com/myapp/app/AppTest.java

Running `mvn compile` produces a class file:

    murphy:myapp pdurbin$ mvn compile --quiet
    murphy:myapp pdurbin$ ack -a -f
    pom.xml
    src/main/java/com/myapp/app/App.java
    src/test/java/com/myapp/app/AppTest.java
    target/classes/com/myapp/app/App.class
    murphy:myapp pdurbin$ 
    murphy:myapp pdurbin$ java -cp target/classes com.myapp.app.App
    Hello World!

Running `mvn package` does a compile and creates the target directory, including a jar:

    murphy:myapp pdurbin$ mvn clean --quiet
    murphy:myapp pdurbin$ mvn package > /dev/null
    murphy:myapp pdurbin$ ack -a -f
    pom.xml
    src/main/java/com/myapp/app/App.java
    src/test/java/com/myapp/app/AppTest.java
    target/classes/com/myapp/app/App.class
    target/maven-archiver/pom.properties
    target/myapp-1.0-SNAPSHOT.jar
    target/surefire-reports/com.myapp.app.AppTest.txt
    target/surefire-reports/TEST-com.myapp.app.AppTest.xml
    target/test-classes/com/myapp/app/AppTest.class
    murphy:myapp pdurbin$ 
    murphy:myapp pdurbin$ java -cp target/myapp-1.0-SNAPSHOT.jar com.myapp.app.App
    Hello World!

Running `mvn clean compile exec:java` requires https://www.mojohaus.org/exec-maven-plugin/

Running `java -jar target/myapp-1.0-SNAPSHOT.jar` requires http://maven.apache.org/plugins/maven-shade-plugin/

# Runnable Jar:
JAR Plugin
The Maven’s jar plugin will create jar file and we need to define the main class that will get executed when we run the jar file.
```
<plugin>
  <artifactId>maven-jar-plugin</artifactId>
  <version>3.0.2</version>
  <configuration>
    <archive>
      <manifest>
        <addClasspath>true</addClasspath>
        <mainClass>com.myapp.App</mainClass>
      </manifest>
    </archive>
  </configuration>
</plugin>
```


# Folder tree before package:
```
# Java Project CI/CD with GitHub Actions

This repository contains a Java application with a complete CI/CD pipeline using GitHub Actions. The pipeline automates building, testing, packaging, and deploying the application using Docker and Helm.

## Project Structure

Before running the build:
```
```
.
├── myapp-chart
│   ├── Chart.yaml
│   ├── charts
│   ├── templates
│   │   ├── NOTES.txt
│   │   ├── _helpers.tpl
│   │   ├── deployment.yaml
│   │   ├── hpa.yaml
│   │   ├── ingress.yaml
│   │   ├── service.yaml
│   │   ├── serviceaccount.yaml
│   │   └── tests
│   │       └── test-connection.yaml
│   └── values.yaml
├── pom.xml
├── pom.xml.versionsBackup
└── src
    ├── main
    │   └── java
    │       └── com
    │           └── myapp
    │               └── App.java
    └── test
        └── java
            └── com
                └── myapp
                    └── AppTest.java
```
```

After running the build (`mvn clean package`):
```
```
.
├── myapp-chart
│   ├── Chart.yaml
│   ├── charts
│   ├── templates
│   │   ├── NOTES.txt
│   │   ├── _helpers.tpl
│   │   ├── deployment.yaml
│   │   ├── hpa.yaml
│   │   ├── ingress.yaml
│   │   ├── service.yaml
│   │   ├── serviceaccount.yaml
│   │   └── tests
│   │       └── test-connection.yaml
│   └── values.yaml
├── pom.xml
├── pom.xml.versionsBackup
├── src
│   ├── main
│   │   └── java
│   │       └── com
│   │           └── myapp
│   │               └── App.java
│   └── test
│       └── java
│           └── com
│               └── myapp
│                   └── AppTest.java
└── target
    ├── classes
    │   └── com
    │       └── myapp
    │           └── App.class
    ├── generated-sources
    │   └── annotations
    ├── generated-test-sources
    │   └── test-annotations
    ├── maven-archiver
    │   └── pom.properties
    ├── maven-status
    │   └── maven-compiler-plugin
    │       ├── compile
    │       │   └── default-compile
    │       │       ├── createdFiles.lst
    │       │       └── inputFiles.lst
    │       └── testCompile
    │           └── default-testCompile
    │               ├── createdFiles.lst
    │               └── inputFiles.lst
    ├── myapp-1.0.0.jar
    ├── surefire-reports
    │   ├── TEST-com.myapp.AppTest.xml
    │   └── com.myapp.AppTest.txt
    └── test-classes
        └── com
            └── myapp
           └── AppTest.class
```
```

## CI/CD Pipeline Overview

The GitHub Actions workflow performs the following steps:

1. **Build:**
   - Uses `mvn clean package` for fresh, clean compilation of the Java application.
   - Generates a JAR artifact.
2. **Store Build Artifact:**
   - Uploads the built JAR file as an artifact.
3. **Test:**
   - Uses `mvn test` to compile and test the Java application.
4. **Docker Image Creation & Push:**
   - Builds a Docker image from the `Dockerfile`.
   - Tags the image with the Git commit SHA and `latest`.
   - Pushes the image to Docker Hub.
5. **Run the Docker Image.**
6. **Notify if failure occurs.**

## Prerequisites

Before running the pipeline, ensure you have:

- A GitHub repository with Actions enabled.
- A Docker Hub account with push permissions.

## Running the Pipeline

The pipeline triggers automatically on each push to the `main` branch. It can also be triggered manually from the GitHub Actions UI.

## Deployment Instructions

1. Ensure your Kubernetes context is set correctly:

   ```sh
   kubectl config use-context your-cluster
   ```

2. Install the Helm chart:

   ```sh
   helm install my-app helm/
   ```

3. Verify the deployment:

   ```sh
   kubectl get pods
   ```

## Troubleshooting

- Check workflow logs in GitHub Actions if the pipeline fails.
- Ensure Docker Hub credentials are set as GitHub secrets (`DOCKER_USERNAME`, `DOCKER_PASSWORD`).
- Validate Helm values before deploying.

## Contributions

Contributions are welcome! Open an issue or submit a pull request to suggest improvements.

## License

This project is licensed under the MIT License.



# Java Project CI/CD with GitHub Actions

This repository contains a Java application with a complete CI/CD pipeline using GitHub Actions. The pipeline automates building, testing, packaging, and deploying the application using Docker and Helm.

## Project Structure

- `src/` - Java source code
- `pom.xml` - Maven build configuration
- `.github/workflows/ci-cd.yml` - GitHub Actions workflow for CI/CD
- `Dockerfile` - Docker image definition
- `helm/` - Helm chart for Kubernetes deployment

## CI/CD Pipeline Overview

The GitHub Actions workflow performs the following steps:

1. **Build:**
   - Uses `mvn clean package` for fresh, clean compile of the Java application.
   - Generates a JAR artifact.
2.  **Store Build Artifact:**
   - Uploads the built JAR file as an artifact.
3. **Test:**
   - Uses `mvn test` to compile and test the Java application.
4. **Docker Image Creation & Push:**
   - Builds a Docker image from the `Dockerfile`.
   - Tags the image with the Git commit SHA and `latest`.
   - Pushes the image to Docker Hub.
5. **Run the Docker Image.**
6. **Notify if failure accoure.**


     
## Prerequisites

Before running the pipeline, ensure you have:

- A GitHub repository with Actions enabled.
- A Docker Hub account with push permissions.

## Running the Pipeline

The pipeline triggers automatically on each push to the `main` branch. It can also be triggered manually from the GitHub Actions UI.

## Deployment Instructions

1. Ensure your Kubernetes context is set correctly:

   ```sh
   kubectl config use-context your-cluster
   ```

2. Install the Helm chart:

   ```sh
   helm install my-app helm/
   ```

3. Verify the deployment:

   ```sh
   kubectl get pods
   ```

## Troubleshooting

- Check workflow logs in GitHub Actions if the pipeline fails.
- Ensure Docker Hub credentials are set as GitHub secrets (`DOCKER_USERNAME`, `DOCKER_PASSWORD`).
- Validate Helm values before deploying.

## Contributions

Contributions are welcome! Open an issue or submit a pull request to suggest improvements.

## License

This project is licensed under the MIT License.


