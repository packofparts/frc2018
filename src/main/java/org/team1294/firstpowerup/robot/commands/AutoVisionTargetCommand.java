package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import org.team1294.firstpowerup.robot.Robot;
import org.team1294.firstpowerup.robot.vision.VisionProcessingResult;

public class AutoVisionTargetCommand extends CommandGroup {

    private final AutoForwardPIDCommand autoForwardPIDCommand;
    private final AutoTurnPIDCommand autoTurnPIDCommand;

    private double forwardRate;
    private double turnRate;

    /**
     * Auto drive a distance towards the vision target
     * @param distance the distance in meters to drive
     */
    public AutoVisionTargetCommand(double distance) {
        super("AutoVisionTargetCommand(" + distance + ")");

        autoForwardPIDCommand = new AutoForwardPIDCommand(rate -> this.forwardRate = rate, distance, 0.25);
        autoTurnPIDCommand = new AutoTurnPIDCommand(output -> this.turnRate = output, 0.25);

        addParallel(autoForwardPIDCommand);
        addParallel(autoTurnPIDCommand);

        setTimeout(15);
    }

    @Override
    protected void initialize() {
        // do nothing
    }

    @Override
    protected void execute() {
        // do vision processing
        VisionProcessingResult visionProcessingResult = Robot.visionSubsystem.detectSwitch();

        // if the target was acquired, adjust the setpoint
        if (visionProcessingResult.isTargetAcquired()) {
            autoTurnPIDCommand.setSetpoint(visionProcessingResult.getHeadingToTurn());
        }

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
}
