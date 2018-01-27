package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class DriveStraightCommand extends CommandGroup{
    private final DriveStraightDriveCommand driveStraightDriveCommand;
    private final DriveStraightForwardCommand driveStraightForwardCommand;
    private final DriveStraightTurnCommand driveStraightTurnCommand;
    private double forwardRate;
    private double turnRate;

    public DriveStraightCommand(final double distance){
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
        super.initialize();
    }

    @Override
    protected void end() {
        super.end();
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
