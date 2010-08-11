----------------------------------------------------------
Spring DI Styles webinar companion project
----------------------------------------------------------


Introduction
------------

This project is build using Gradle (http://gradle.org).

To execute build system tasks, simply run the 'gradle wrapper' script
appropriate to your platform.  You'll notice these scripts are available in the
root of this project, meaning there is nothing for you to download or install.

    For Windows:  `gradlew.bat`
    For OSX/*nix: `gradlew`

The remainder of this document will simply refer these scripts as 'gradlew'.


Discover available tasks:
-------------------------

    gradlew --tasks
        or
    gradlew -t


Build the project:
------------------

    gradlew build

Note that 'build' includes compilation, testing, and packaging. For Maven users,
`gradlew build` is roughly equivalent to `mvn package`.

Of course, the first time you execute this task, Gradle will download all
project dependencies.  As a convenience, the build script is configured to look
first in your ~/.m2/repository before fetching artifacts from maven central.


Import the project into Eclipse/STS:
------------------------------------

    gradlew eclipse

to create .project and .classpath files. this command will also retrive and link
dependency source jars. After this is complete, you can import the project as
follows:

    File->Import->Existing Projects Into Workspace

If usins STS (http://www.springsource.com/developer/sts), you'll also want to
enable the Spring Project Nature by right clicking on the distyles project and
selecting:

    Spring Tools->Add Spring Project Nature


Import the project into IntellJ IDEA:
-------------------------------------

    gradlew idea

to create .iml, .ipr, and .iws files


Run the interactive Teller UI:
------------------------------

    gradlew ui

and you will be prompted for input.


