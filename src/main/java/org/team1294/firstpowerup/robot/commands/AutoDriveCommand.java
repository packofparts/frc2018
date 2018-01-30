package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team1294.firstpowerup.robot.Robot;

public class AutoDriveCommand extends CommandGroup {

    private final AutoForwardPIDCommand autoForwardPIDCommand;
    private final AutoTurnPIDCommand autoTurnPIDCommand;
    private boolean setHeadingInInitialize;

    private double forwardRate;
    private double turnRate;

    /**
     * Auto drive a distance on the current heading
     * @param distance the distance in meters to drive
     */
    public AutoDriveCommand(final double distance) {
        this(distance, 0);
        setHeadingInInitialize = true;
    }

    /**
     * Auto drive a distance and heading
     * @param distance the distance in meters to drive
     * @param heading the heading to drive
     */
    public AutoDriveCommand(final double distance, final double heading) {
        this(distance, heading, 1);
    }

    /**
     * Auto drive a distance and heading with a max velocity
     * @param distance the distance in meters to drive
     * @param heading the heading to drive
     * @param velocity the max velocity to drive (-1.0 to 1.0)
     */
    public AutoDriveCommand(final double distance, final double heading, final double velocity) {
        this(distance, heading, velocity, 1);
    }

    /**
     * Auto drive a distance and heading with max velocity and turn rate
     * @param distance the distance in meters to drive
     * @param heading the heading to drive
     * @param velocity the max velocity (-1.0 to 1.0)
     * @param turnRate the max turn rate (-1.0 to 1.0)
     */
    public AutoDriveCommand(final double distance, final double heading, final double velocity, final double turnRate) {
        super("AutoDriveCommand(" + heading + ", " + distance + ", " + velocity + ", " + turnRate + ")");
        setHeadingInInitialize = false;

        requires(Robot.driveSubsystem);

        autoForwardPIDCommand = new AutoForwardPIDCommand(rate -> this.forwardRate = rate, distance, velocity);
        autoTurnPIDCommand = new AutoTurnPIDCommand(output -> this.turnRate = output, heading, turnRate);

        addParallel(autoForwardPIDCommand);
        addParallel(autoTurnPIDCommand);

        setTimeout(15);
    }

    @Override
    protected void initialize() {
        if (setHeadingInInitialize) {
            autoTurnPIDCommand.setSetpoint(Robot.driveSubsystem.getHeading());
        }
    }

    @Override
    protected void execute() {
        Robot.driveSubsystem.arcadeDrive(forwardRate, turnRate);
    }

    @Override
    protected boolean isFinished() {
        return isTimedOut() || (autoForwardPIDCommand.onTarget() && autoTurnPIDCommand.onTarget());
    }

    @Override
    protected void end() {
        // do nothing
    }

    public void setHeading(final double heading) {
        autoTurnPIDCommand.setSetpoint(heading);
    }

    public void setDistance(final double distance) {
        autoForwardPIDCommand.setSetpoint(distance);
    }
}
