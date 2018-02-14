package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team1294.firstpowerup.robot.RobotMap;

/**
 * @author Abhinav Diddee (heatblast016) */
public class ArmSubsystem extends Subsystem {
    private TalonSRX motor;
    private TalonSRX wristmotor;
    private AnalogPotentiometer potentiometer;
    private AnalogPotentiometer wrist_pot;
    public ArmSubsystem() {
        super("Arm Subsystem");
        motor = new TalonSRX(RobotMap.TALON_ARM);
        wristmotor = new TalonSRX(RobotMap.TALON_WRIST);

        potentiometer = new AnalogPotentiometer(RobotMap.SENSOR_POT);
        wrist_pot = new AnalogPotentiometer(RobotMap.WRIST_POT);
    }
    public void setWristangle(double angle)
    {
        wristmotor.set(ControlMode.Position, angle);
    }
    public void setArmHeight(double height) {
        motor.set(ControlMode.PercentOutput, height);
    }

    public double getPotOutputAngle(AnalogPotentiometer pot) {
        return 72.0*pot.get();
    }
    @Override
    public void periodic() {
        // do nothing
    }

    @Override
    protected void initDefaultCommand() {
//        setDefaultCommand();
    }
}
