package org.team1294.firstpowerup.robot.subsystems;

import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.team1294.firstpowerup.robot.RobotMap;
import org.team1294.firstpowerup.robot.commands.DriveClimbMotorCommand;

/**
 * @author Austin Jenchi (timtim17)
 */
public class CelescoPotTestSubsystem extends Subsystem {
    private AnalogPotentiometer pot;

    public CelescoPotTestSubsystem() {
        pot = new AnalogPotentiometer(RobotMap.SENSOR_POT);
    }

    public double getPotOutput() {
        return pot.get();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Potentiometer", getPotOutput());
    }

    @Override
    protected void initDefaultCommand() {
//        setDefaultCommand();
    }
}
