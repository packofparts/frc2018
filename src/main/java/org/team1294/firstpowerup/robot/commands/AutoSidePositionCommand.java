package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoSidePositionCommand extends CommandGroup {

    public AutoSidePositionCommand(String side) {
        final String gameData = DriverStation.getInstance().getGameSpecificMessage();
        final String scaleSide = gameData.substring(1, 1);
        final String switchSide = gameData.substring(0, 0);

        // drive straight ahead far enough to make it easy to turn
        addSequential(new DriveStraightCommand(0.1, 0)); // todo tune this distance

        // drive towards the wall
        if (side.equals("L")) {
            addSequential(new TurnToHeadingCommand(315));
            addSequential(new DriveStraightCommand(0.5, 315)); // todo tune this distance
        } else {
            addSequential(new TurnToHeadingCommand(45));
            addSequential(new DriveStraightCommand(0.5, 45)); // todo tune this distance
        }

        // turn downfield
        addSequential(new TurnToHeadingCommand(0));

        if (scaleSide.equals(side)) {
            // drive downfield until abeam the scale
            addSequential(new DriveStraightCommand(2.0, 0)); // todo tune this distance

            // drive towards the scale
            if (side.equals("L")) {
                addSequential(new TurnToHeadingCommand(90));
                addSequential(new DriveStraightCommand(0.5, 90)); // todo tune this distance
            } else {
                addSequential(new TurnToHeadingCommand(270));
                addSequential(new DriveStraightCommand(0.5, 270)); // todo tune this distance
            }

            // deliver the crate
            // todo
        } else if (switchSide.equals(side)) {
            // drive downfield until abeam the switch
            addSequential(new DriveStraightCommand(1.0, 0)); // todo tune this distance

            // drive towards the switch
            if (side.equals("L")) {
                addSequential(new TurnToHeadingCommand(90));
                addSequential(new DriveStraightCommand(0.5, 90)); // todo tune this distance
            } else {
                addSequential(new TurnToHeadingCommand(270));
                addSequential(new DriveStraightCommand(0.5, 270)); // todo tune this distance
            }

            // deliver the crate
            // todo
        } else {
            // drive downfield until past the auto line
            addSequential(new DriveStraightCommand(1.0, 0)); // todo tune this distance
        }


    }

}
