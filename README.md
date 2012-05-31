Simple projects containing the same application configured in different ways
using Spring (and in one case without Spring at all).

Clone the repo in [typical fashion](http://help.github.com), or download a
[zip of the sources](https://github.com/cbeams/distyles/zipball/master).

Browse through the [slides](http://cbeams.github.com/distyles) or watch the
original one-hour [webinar](http://www.youtube.com/watch?v=dJh84cjMY3E) on
YouTube. Either one will give you a good sense of what each project intends
to demonstrate.

Build and test everything and run the `TransferScript` main methods with

    gradlew run test

Import the projects into Eclipse with

    gradlew eclipse

and then File->Import->Existing Projects Into Workspace. For IDEA users, it's
`gradlew idea`.

Once you're looking at the code, just browse around. Review how
`DefaultTransferService` works. Look at each of the following and see how they
differ across projects:

* `TransferScript`
* `TransferServiceTests`
* `app-config.xml`

Explore, experiment, have fun!
