package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoSidePositionCommand extends CommandGroup {
    private final String side;
    private final AutoDriveCommand turnTowardsWall;
    private final AutoDriveCommand driveTowardsWall;
    private final AutoDriveCommand driveDownfield;
    private final AutoDriveCommand turnTowardsDelivery;
    private final AutoDriveCommand driveTowardsDelivery;
    private final AutoDeliverCrateCommand deliverCrateCommand;
    private boolean shouldDeliverCube;

    public AutoSidePositionCommand(String side) {
        super("AutoSidePositionCommand " + side);
        this.side = side;

        // drive straight ahead far enough to make it easy to turn
        addSequential(new PrintCommand("drive away from alliance wall"));
        addSequential(new AutoDriveCommand(0.1, 0, 0.75, 0.75));

        // turn towards the wall (heading gets set in initialize)
        turnTowardsWall = new AutoDriveCommand(0, 0, 0.75, 0.75);
        addSequential(new PrintCommand("turn towards wall"));
        addSequential(turnTowardsWall);

        // drive towards the wall (heading gets set in initialize)
        driveTowardsWall = new AutoDriveCommand(1, 0, 0.75, 0.75);
        addSequential(new PrintCommand("drive towards wall"));
        addSequential(driveTowardsWall);

        // turn downfield
        addSequential(new PrintCommand("turn downfield"));
        addSequential(new AutoDriveCommand(0, 0, 0.75, 0.75));

        // drive downfield until abeam the crate delivery location (distance gets set in initialize)
        addSequential(new PrintCommand("drive downfield"));
        driveDownfield = new AutoDriveCommand(0, 0, 0.75, 0.75);
        addSequential(driveDownfield);

        // turn towards the crate delivery location (heading gets set in initialize)
        addSequential(new PrintCommand("turn towards delivery"));
        turnTowardsDelivery = new AutoDriveCommand(0, 0, 0.75, 0.75);
        addSequential(turnTowardsDelivery);

        // drive towards the crate delivery location ((heading and distance gets set in initialize)
        addSequential(new PrintCommand("drive towards delivery"));
        driveTowardsDelivery = new AutoDriveCommand(0, 0, 0.75, 0.75);
        addSequential(driveTowardsDelivery);

        // deliver the crate
        deliverCrateCommand = new AutoDeliverCrateCommand();
        addSequential(deliverCrateCommand);
    }

    @Override
    protected void initialize() {
        final String gameData = DriverStation.getInstance().getGameSpecificMessage();
        final String switchSide = gameData.substring(0, 1);
        final String scaleSide = gameData.substring(1, 2);
        System.out.println(side+switchSide);
        shouldDeliverCube = true;

        if (side.equals("L")) {
            turnTowardsWall.setHeading(315);
            driveTowardsWall.setHeading(315);
            turnTowardsDelivery.setHeading(90);
            driveTowardsDelivery.setHeading(90);
        } else {
            turnTowardsWall.setHeading(45);
            driveTowardsWall.setHeading(45);
            turnTowardsDelivery.setHeading(270);
            driveTowardsDelivery.setHeading(270);
        }

        // todo tune these distances
        final double downfieldPastAutoLineDistance = 0.5;

        final double downfieldToSwitchDistance = 3.0;
        final double deliverToSwitchDistance = 0.5;

        final double downfieldToScaleDistance = 2.0;
        final double deliverToScaleDistance = 0.5;

        if (switchSide.equals(side)) {
            driveDownfield.setDistance(downfieldToSwitchDistance);
            driveTowardsDelivery.setDistance(deliverToSwitchDistance);
        } else if (scaleSide.equals(side)) {
            driveDownfield.setDistance(downfieldToScaleDistance);
            driveTowardsDelivery.setDistance(deliverToScaleDistance);
        } else {
            driveDownfield.setDistance(downfieldPastAutoLineDistance);
            turnTowardsDelivery.setHeading(0);
            driveTowardsDelivery.setDistance(0);
            shouldDeliverCube = false;
        }
    }

    public boolean getShouldDeliverCube() {
        return shouldDeliverCube;
    }
}
