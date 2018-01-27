package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team1294.firstpowerup.robot.Robot;

public class DriveStraightCommand extends CommandGroup{
    private final DriveStraightDriveCommand driveStraightDriveCommand;
    private final DriveStraightForwardCommand driveStraightForwardCommand;
    private final DriveStraightTurnCommand driveStraightTurnCommand;
    private double forwardRate;
    private double turnRate;

    public DriveStraightCommand(final double distance){
        super("Drive straight " + distance + "m");

        requires(Robot.driveSubsystem);

        driveStraightDriveCommand = new DriveStraightDriveCommand();
        driveStraightForwardCommand = new DriveStraightForwardCommand(distance);
        driveStraightTurnCommand = new DriveStraightTurnCommand();

        addParallel(driveStraightDriveCommand);
        addParallel(driveStraightForwardCommand);
        addParallel(driveStraightTurnCommand);

        setTimeout(15);
    }

    @Override
    protected void initialize() {
        // do nothing
    }

    @Override
    protected void end() {
        // do nothing
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || (driveStraightForwardCommand.onTarget() && driveStraightTurnCommand.onTarget());
    }

    public double getForwardRate() {
        return forwardRate;
    }

    public void setForwardRate(double forwardRate) {
        this.forwardRate = forwardRate;
    }

    public double getTurnRate() {
        return turnRate;
    }

    public void setTurnRate(double turnRate) {
        this.turnRate = turnRate;
    }

}
