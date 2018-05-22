![Timber](logo.png)

This is a logger with a small, extensible API which provides utility on top of Android's normal
`Log` class.

I copy this class into all the little apps I make. I'm tired of doing it. Now it's a library.

Behavior is added through `Tree` instances. You can install an instance by calling `Timber.plant`.
Installation of `Tree`s should be done as early as possible. The `onCreate` of your application is
the most logical choice.

The `DebugTree` implementation will automatically figure out from which class it's being called and
use that class name as its tag. Since the tags vary, it works really well when coupled with a log
reader like [Pidcat][1].

There are no `Tree` implementations installed by default because every time you log in production, a
puppy dies.


Usage
-----

Two easy steps:

 1. Install any `Tree` instances you want in the `onCreate` of your application class.
 2. Call `Timber`'s static methods everywhere throughout your app.

Check out the sample app in `timber-sample/` to see it in action.



FileLoggingTree
---------------

This tree can be planted to record the logs in a particular file. Upon initialization of FileLoggingTree, user has to provide a **TreeName**. This tree name is internally used to deligate the logs that use this treeName to this FileLoggingTree. Additionally this FileLoggingTree provides that facility of *displaying/hiding its logs in the logcat*

Following are the features provided by this FileLoggingTree:

1. Assigning treeName to delegate all the logs using treeName to respective FileLoggingTrees
2. Logging file name
3. Logging file path
4. Time stamp format to be used in the logging file
5. Time zone for time stamp
6. List of logging priorities that must be recorded in the log file. Use null if all the logging priorities should be recorded in the log file.
7. Observer callbacks for the completion of disk operation

NOTE
----

Non file logging trees don't take the tree names into consideration so if you have planted 2 *DebugTree*(s) with different names, they both will show logs in the logcat irrespective if the treeName was used or not, therefore *you will see duplicate logs in the logcat*.

*Therefore it's recommended to plant only 1 DebugTree in the entire application whereas multiple FileLoggingTrees can be planted in an application provided each of them use different treeNames.*

Lint
----

Timber ships with embedded lint rules to detect problems in your app.

 *  **TimberArgCount** (Error) - Detects an incorrect number of arguments passed to a `Timber` call for
    the specified format string.

        Example.java:35: Error: Wrong argument count, format string Hello %s %s! requires 2 but format call supplies 1 [TimberArgCount]
            Timber.d("Hello %s %s!", firstName);
            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 *  **TimberArgTypes** (Error) - Detects arguments which are of the wrong type for the specified format string.

        Example.java:35: Error: Wrong argument type for formatting argument '#0' in success = %b: conversion is 'b', received String (argument #2 in method call) [TimberArgTypes]
            Timber.d("success = %b", taskName);
                                     ~~~~~~~~
 *  **TimberTagLength** (Error) - Detects the use of tags which are longer than Android's maximum length of 23.

        Example.java:35: Error: The logging tag can be at most 23 characters, was 35 (TagNameThatIsReallyReallyReallyLong) [TimberTagLength]
            Timber.tag("TagNameThatIsReallyReallyReallyLong").d("Hello %s %s!", firstName, lastName);
            ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 *  **LogNotTimber** (Warning) - Detects usages of Android's `Log` that should be using `Timber`.

        Example.java:35: Warning: Using 'Log' instead of 'Timber' [LogNotTimber]
            Log.d("Greeting", "Hello " + firstName + " " + lastName + "!");
                ~

 *  **StringFormatInTimber** (Warning) - Detects `String.format` used inside of a `Timber` call. Timber
    handles string formatting automatically.

        Example.java:35: Warning: Using 'String#format' inside of 'Timber' [StringFormatInTimber]
            Timber.d(String.format("Hello, %s %s", firstName, lastName));
                     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 *  **BinaryOperationInTimber** (Warning) - Detects string concatenation inside of a `Timber` call. Timber
    handles string formatting automatically and should be preferred over manual concatenation.

        Example.java:35: Warning: Replace String concatenation with Timber's string formatting [BinaryOperationInTimber]
            Timber.d("Hello " + firstName + " " + lastName + "!");
                     ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

 *  **TimberExceptionLogging** (Warning) - Detects the use of null or empty messages, or using the exception message
    when logging an exception.

        Example.java:35: Warning: Explicitly logging exception message is redundant [TimberExceptionLogging]
             Timber.d(e, e.getMessage());
                         ~~~~~~~~~~~~~~


Download
--------

```groovy
implementation 'com.jakewharton.timber:wingoku-timber:4.6.11'
```


License
-------

    Copyright 2013 Jake Wharton

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.



 [1]: http://github.com/JakeWharton/pidcat/
 [snap]: https://oss.sonatype.org/content/repositories/snapshots/
