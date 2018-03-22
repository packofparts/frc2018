package org.team1294.firstpowerup.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.team1294.firstpowerup.robot.Robot;
import org.team1294.firstpowerup.robot.subsystems.ArmSubsystem;

/**
 * @author Austin Jenchi (timtim17)
 *
 * This is for driving the arm with a joystick. However, in theory the movement should be stopped by
 * the Talon's soft limits set in {@link SetArmHeightCommand}.
 */
public class DriveArmWithJoystickCommand extends Command {
    private static final double DEADZONE = 0.1;
    private static final double POS_RATE_OF_CHANGE = 50;

    public DriveArmWithJoystickCommand() {
        super("Drive arm with joystick");
        requires(Robot.armSubsystem);
        Robot.armSubsystem.setArmSoftLimits(44,770);
//        Robot.armSubsystem.setExtendSoftLimits(0, 0);
    }

    @Override
    protected void execute() {
        double value = Robot.oi.getArmY();
        if (Math.abs(value) < DEADZONE) {
            Robot.armSubsystem.driveArmPercentOut(0);
        } else {
            Robot.armSubsystem.driveArmPercentOut(value);
            if(Robot.armSubsystem.getArmHeight() < 0 && Robot.armSubsystem.getArmHeight() > 0)
            {Robot.armSubsystem.driveExtendPercentOut(-0.3);}
            else if(Robot.armSubsystem.getArmHeight() < 0 && Robot.armSubsystem.getArmHeight() > 0)
            {Robot.armSubsystem.driveExtendPercentOut(0.3);}
        }

        int bumpervalue = Robot.oi.getBumpers();
        /* int armPos = armMotor.getSelectedSensorPosition(0);
        if (armPos > LEGAL_LOW && armPos < LEGAL_HIGH) { // && !limitSwitchClosed) {
            extendMotor.set(ControlMode.PercentOutput, -0.1);
        } else */ if(bumpervalue == 1) {
//            driveExtendPercentOut(-0.6);
            Robot.armSubsystem.changeExtendPos(POS_RATE_OF_CHANGE);
        } else if(bumpervalue == 2 && Robot.armSubsystem.getExtensionSensorValue() < ArmSubsystem.Telescope.IN.distance) {
//            driveExtendPercentOut(0.6);
            Robot.armSubsystem.changeExtendPos(-POS_RATE_OF_CHANGE);
        }

        // some quick code to test the wrist
        double pov = Robot.oi.getGMPOV();
        if (pov == 0) {
            Robot.armSubsystem.driveWristPercentOut(0.4);
        } else if (pov == 180) {
            Robot.armSubsystem.driveWristPercentOut(-0.4);
        } else {
            Robot.armSubsystem.driveWristPercentOut(0);
        }
    }

    @Override
    protected boolean isFinished() {
        return false;
    }
}
