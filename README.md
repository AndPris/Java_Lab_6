# Lab 6
## Made by Andrii Prysiazhnyi IP-22, variant 18

# How to run project
You need [Maven](https://maven.apache.org/guides/getting-started/maven-in-five-minutes.html) to be [installed](https://maven.apache.org/download.cgi) on your computer.
1. Clone repository:<br>
    `git clone https://github.com/AndPris/Java_Lab_6`<br>
    `cd Java_Lab_6`
2. Build project (it will also run unit tests):<br>
    `mvn package`
3. Run project:<br>
    `mvn clean compile exec:java`
4. Run tests:<br>
    `mvn test`

   
# How to generate javadoc
## 1. Using `javadoc`:
1. In project root directory run:<br>
   `javadoc -d docs -sourcepath src/main/java -subpackages gemstones`
2. Navigate to `/docs` folder.

## 2. Using maven:
1. In project root directory run:<br>
   `mvn javadoc:javadoc`
2. Navigate to `/target/reports/apidocs` folder.