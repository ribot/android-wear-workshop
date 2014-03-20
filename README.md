Android Boilerplate [![Build Status](http://ribot.plus.com:17892/buildStatus/icon?job=AndroidBoilerplate)](http://ribot.plus.com:17892/job/AndroidBoilerplate/)
===================

This boilerplate is used as the basis for Android projects at ribot. It uses the Gradle build system and features the Ribot Gradle plugin which is used as a conviniance to build, install and run the application and also give shortcuts for running tests and lint. It is already setup with a test project using robotium and has two example tests. There are also instructions to get the project running under the Jenkins CI server.

Follow all these instructions to setup the new project.

Project setup
-------------

1. Download this [repository as a zip](https://github.com/ribot/android-boilerplate/archive/master.zip)
2. Change the package name
	- In `app/build.gradle` file, `packageName` and `testPackageName`
	- In the `MainActivity.java` file and the directory structure
	- In the `MainActivityTest.java` file and the directory structure
3. Change the root project name in `settings.gradle`
4. Optionally, in `app/build.gradle` add the signing config to enable release versions

Git setup
---------

1. Run `git init`, then `git add -A` and then `git commit -m "Initial commit"` to setup a new local git repo
2. Setup a new project on [Github](http://github.com/ribot) and follow the instructions for pushing from an existing repository
3. If your project uses git submodules, uncomment the `initSubmodule` task a the end of the `app/build.gradle` file.

Jenkins setup
-------------

1. Login to the [Jenkins CI server](http://ribot.plus.com:17892/) and choose *New Job* in the left menu
2. Give it a name with no spaces or special characters and choose *free-style software project*
3. Tick *Enable project-based security* and type the usernames of everyone on the project. Tick the appropriate permissions for each user
4. Choose *git* under the source control section and enter the Github repository URL (eg. git@github.com:ribot/android-boilerplate.git)
5. Under *Build triggers* tick *Poll SCM* and enter `0 */3 * * *` in the *Schedule* section
	- We're actually going to make Github tell Jenkins when to build but this option is required to make that work. This will make it poll at least once ever 3 hours for changes just incase
6. Click *add build* step and choose *Execute shell*. Enter:<pre>./gradlew ciDebug</pre> or if your project uses submodules <pre>./gradlew initSubmodule ciDebug</pre> Note that the name of the ci task may change if you have different flavours.
7. Add an *Email notification* post build step with the project leads email address, *Send e-mail for every unstable build* unticked and *Send separate e-mails to individuals who broke the build* ticked
8. Go to the project page on Github, press *settings* in the right hand menu and then *service hooks*. In this list choose *Jenkins (Git plugin)* and then enter the Jenkins server URL (http://ribot.plus.com:17892/),tick the *active* box and press update settings.

Widget Setup
------------
Note: The current version of the Ribot Gradle plugin doesn't have widget integration.

1. Change the package name in `BuildStatusWidget.wdgt/Info.plist` file to something unique to your project
2. Change the port number in `BuildStatusWidget.wdgt/server.rb` and `rakefile.rb` to the same project unique number
3. Change the project title in the `<h1>` in the `BuildStatusWidget.wdgt/main.html` file
4. Copy the widget bundle onto the Jenkins server (the Mac Mini)
	- The easist way to do this is to screen share with the Mini and drag the bundle onto the desktop via the screen share
5. Double click and install the widget on the Jenkins server machine
6. Add the widget to the Dashboard then while dragging it press F12 to detatch it and have it floating on the screen all the time
