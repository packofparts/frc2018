package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.ConditionalCommand;
import org.team1294.firstpowerup.robot.Robot;

public class AutoDeliverCrateCommand extends ConditionalCommand {
    public AutoDeliverCrateCommand() {
        super("AutoDeliverCrateCommand", new ActuallyDeliverACubeCommand());
    }

    @Override
    protected boolean condition() {
        CommandGroup parent = getGroup();
        if (parent instanceof AutoSidePositionCommand) {
            return ((AutoSidePositionCommand) parent).getShouldDeliverCube();
        } else if (parent instanceof AutoShortSideDeliverSimple) {
            return ((AutoShortSideDeliverSimple) parent).shouldDeliverCube();
        } else if (parent instanceof AutoSideDeliverSimple) {
            return ((AutoSideDeliverSimple) parent).shouldDeliverCube();
        }
        return false;
    }

    private static class ActuallyDeliverACubeCommand extends Command {

        public ActuallyDeliverACubeCommand() {
            super("Auto Deliver Cube");
            requires(Robot.intakeSubsystem);
            setTimeout(3);
        }

        @Override
        protected void initialize() {
            Robot.intakeSubsystem.driveIntake(-1.0);
        }

        @Override
        protected void end() {
            Robot.intakeSubsystem.stop();
        }

        @Override
        protected boolean isFinished() {
            return isTimedOut();
        }
    }
}
