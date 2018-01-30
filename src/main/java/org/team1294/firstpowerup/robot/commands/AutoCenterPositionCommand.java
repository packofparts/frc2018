package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCenterPositionCommand extends CommandGroup {

    public AutoCenterPositionCommand() {

        // drive straight ahead far enough to make it easy to turn
        addSequential(new AutoDriveCommand(0.1, 0, 0.5, 0.25));

        // drive towards the desired side
        final String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.charAt(0) == 'L') {
            addSequential(new AutoDriveCommand(0, 315, 0.5, 0.5));
            addSequential(new AutoDriveCommand(1.0, 315, 0.5, 0.25)); // todo tune this distance
        } else {
            addSequential(new AutoDriveCommand(0, 45, 0.5, 0.5));
            addSequential(new AutoDriveCommand(1.0, 45, 0.5, 0.25)); // todo tune this distance
        }

        // turn towards the vision target
        addSequential(new AutoDriveCommand(0, 0, 0.5, 0.5));

        // engage the vision system approach
        addSequential(new AutoVisionTargetCommand(1.0));

        // deliver the crate
        // todo
    }

}
