package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team1294.firstpowerup.robot.RobotMap;
import org.team1294.firstpowerup.robot.commands.DriveClimbMotorCommand;

/**
 * @author Austin Jenchi (timtim17)
 * @author Katharine Lundblad (klundblad)
 */
public class ClimbSubsystem extends Subsystem {
    private TalonSRX talon;

    public ClimbSubsystem() {
        super("Climb subsystem");
        talon = new TalonSRX(RobotMap.TALON_CLIMB);
        talon.configForwardLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
        talon.configReverseLimitSwitchSource(LimitSwitchSource.FeedbackConnector, LimitSwitchNormal.NormallyOpen, 10);
    }

    public void driveTalon(double output) {
        talon.set(ControlMode.PercentOutput, output);
    }

    public void stop() {
        driveTalon(0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveClimbMotorCommand());
    }
}
