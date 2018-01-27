# frc2018 [![Build Status](https://travis-ci.org/FRC-1294/frc2018.svg?branch=master)](https://travis-ci.org/FRC-1294/frc2018) [![CodeFactor](https://www.codefactor.io/repository/github/frc-1294/frc2018/badge)](https://www.codefactor.io/repository/github/frc-1294/frc2018)

This repository contains the code for FRC team 1294, Top Gun, for the 2018
_FIRST_ Robotics Competition game, _FIRST_ POWER UP :arrow_upper_right:.

This project uses [GradleRIO](https://www.github.com/Open-RIO/GradleRIO), a
plugin that allows FRC projects to be built with Gradle. New to 2018 is
[EmbededTools](https://www.github.com/JacisNonsense/EmbeddedTools), which
handles all of the logic when deploying to the roboRIO and ***any coprocessors***,
including the Raspberry PI. Thus if we wanted too, we could store our vision
code (see [FRC-1294/frc2016vision](https://www.github.com/FRC-1294/frc2016vision))
within the same project as our robot code, just in a different submodule (however,
looking at the 2018 game it seems like a coprocessor won't be necessary).

There are also a few changes to WPILib, including a new dashboard, Shuffleboard,
and the removal of the old JRE installer. Instead of requiring a separate JRE
installation after reimaging the RIO, the JRE is now installed as a part 
of the deploy step. More information about WPILib updates is available at 
[WPILib Screensteps](http://wpilib.screenstepslive.com/s/currentCS/m/getting_started/l/801080-new-for-2018).

## Robot Code

We use standard WPILibJ for our robot code. Our robot is command based, 
meaning each system is structured as a `Subsystem`, and behaviors are 
separated into `Command`s. Depending on the game we may have subsystems for 
driving (i.e. `DriveSubsystem`), a game mech (i.e. `ShooterSubsystem`), or 
other things like "spacial awareness" (NavX + Vision, 
`SpacialAwarenessSubsystem`). More  information about command based programming 
with WPILib is available at [WPILib Screensteps](http://wpilib.screenstepslive.com/s/currentCS/m/java/c/88893).

## Important Commands

A Gradle wrapper script is included in the project, so installing Gradle 
manually is unnecessary. It is recommended to use PowerShell on Windows so 
that Mac/Linux style commands work without format changes. However, if you 
insist on using CMD, any commands with a `./` (i.e. `./gradlew build`) would 
have the `./` removed (i.e. `gradlew build`).

Setup IntelliJ IDEA: `./gradlew idea`

Setup Eclipse: `./gradlew eclipse`

Launch ShuffleBoard (new SmartDashboard): `./gradlew shuffleboard`

Launch SmartDashboard: `./gradlew smartDashboard`

Build Robot & Coprocessor JARs: `./gradlew build`

Deploy to roboRIO & Raspberry Pi: `./gradlew deploy`

View RioLog Output: `./gradlew riolog`

***Run commands with the `--ofline` flag when connected to the robot, or if you
otherwise don't have internet!***
