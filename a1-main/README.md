
<b>To run the maven package</b>
cd path/to/dir/with/pom
mvn package
cd target
java -jar studentfinder.jar "id"

<b>To run the CLI application </b>
Find or download derby-10.14.2.0.jar
Copy and paste into the lib directory
java -cp "target/classes:lib/*" nz.ac.wgtn.swen301.assignment1.cli.FindStudentDetails "id0"

<b>Discussion</b>
My design is not prone to memory leaks because all jdbc connections are closed after use and only one instance of each student and degree are created. They are dereferenced afterwards use.

