package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.PIDCommand;
import org.team1294.firstpowerup.robot.Robot;

/**
 * The goal of this command is to provide the ability to drive in teleop with ArcadeDrive, like
 * {@link ArcadeDriveCommand}, but with heading compensation - that is, if the driver is
 * intentionally trying to drive straight, do the best we can using gyro input to keep the robot on
 * course.
 *
 * @author Austin Jenchi (timtim17)
 */
public class TeleopDriveCommand extends PIDCommand {
    private static final double TURN_DEADBAND = 0.1;
    private static final double kP = 1.0;
    private static final double kI = 0.0;
    private static final double kD = 0.0;
    private static final double kTolerance = 0.01;

    private enum TurnMode {OFF, ASSIST}

    private TurnMode currentMode;

    public TeleopDriveCommand() {
        super("Teleop Drive Command", kP, kI, kD);
        requires(Robot.driveSubsystem);

        currentMode = TurnMode.OFF;

        getPIDController().setAbsoluteTolerance(kTolerance);
        getPIDController().disable();
    }

    @Override
    protected void initialize() {
        // do nothing?
    }

    @Override
    protected void execute() {
        double joyTurnIn = Robot.oi.getDriveLeftX();
        boolean shouldBeOn = joyTurnIn <= TURN_DEADBAND;

        switch (currentMode) {
            case OFF:
                if (shouldBeOn) {
                    setSetpoint(Robot.driveSubsystem.getHeading());
                    getPIDController().enable();
                    currentMode = TurnMode.ASSIST;
                } else {
                    // this is a bit lazy, but usePIDOutput uses a given output from the PID
                    // to turn with the drive subsystem... so instead of rewriting it treat
                    // the joystick input as if it were output from the PID
                    usePIDOutput(joyTurnIn);
                }
                break;

            case ASSIST:
                if (!shouldBeOn) {
                    getPIDController().disable();
                    usePIDOutput(joyTurnIn);
                    currentMode = TurnMode.OFF;
                }
                break;
        }
    }

    @Override
    protected void end() {
        Robot.driveSubsystem.stop();
    }

    @Override
    protected boolean isFinished() {
        return false;
    }

    @Override
    protected double returnPIDInput() {
        return Robot.driveSubsystem.getHeading();
    }

    @Override
    protected void usePIDOutput(double output) {
        double joyForward = Robot.oi.getDriveLeftY();
        Robot.driveSubsystem.arcadeDrive(joyForward, output);
    }
}
