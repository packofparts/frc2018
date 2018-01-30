package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;

public class AutoCenterPositionCommand extends CommandGroup {

    private final AutoDriveCommand turnTowardsCorrectSide;
    private final AutoDriveCommand driveTowardsCorrectSide;

    public AutoCenterPositionCommand() {
        // drive straight ahead far enough to make it easy to turn
        addSequential(new AutoDriveCommand(0.1, 0, 0.5, 0.25));

        // turn towards the desired side (heading gets set in initialize)
        turnTowardsCorrectSide = new AutoDriveCommand(0, 0, 0.5, 0.5);
        addSequential(turnTowardsCorrectSide);

        // drive towards the desired side (heading gets set in initialize)
        driveTowardsCorrectSide = new AutoDriveCommand(1.0, 0, 0.5, 0.25); // todo tune this distance
        addSequential(driveTowardsCorrectSide);

        // turn towards the vision target
        addSequential(new AutoDriveCommand(0, 0, 0.5, 0.5));

        // engage the vision system approach
        addSequential(new AutoVisionTargetCommand(1.0)); // todo tune this distance

        // deliver the crate
        // todo
    }

    @Override
    protected void initialize() {
        final String gameData = DriverStation.getInstance().getGameSpecificMessage();
        if (gameData.charAt(0) == 'L') {
            turnTowardsCorrectSide.setHeading(315);
            driveTowardsCorrectSide.setHeading(315);
        } else {
            turnTowardsCorrectSide.setHeading(45);
            driveTowardsCorrectSide.setHeading(45);
        }
    }
}
