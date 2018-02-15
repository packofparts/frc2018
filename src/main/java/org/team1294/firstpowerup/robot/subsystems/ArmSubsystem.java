package org.team1294.firstpowerup.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.AnalogPotentiometer;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team1294.firstpowerup.robot.RobotMap;
import org.team1294.firstpowerup.robot.commands.DriveArmWithJoystickCommand;

/**
 * @author Abhinav Diddee (heatblast016)
 */
public class ArmSubsystem extends Subsystem {
    private TalonSRX armMotor;
    private TalonSRX wristMotor;
    private AnalogPotentiometer armPot;
    private AnalogPotentiometer wristPot;
    private Wrist currentStatus;

    private enum Wrist {
        IN(0), OUT(10);

        private final double angle;

        Wrist(double angle) {
            this.angle = angle;
        }
    }

    public ArmSubsystem() {
        super("Arm Subsystem");
        armMotor = new TalonSRX(RobotMap.TALON_ARM);
        wristMotor = new TalonSRX(RobotMap.TALON_WRIST);

        armPot = new AnalogPotentiometer(RobotMap.SENSOR_POT);
        wristPot = new AnalogPotentiometer(RobotMap.WRIST_POT);

        currentStatus = Wrist.IN;
    }

    public void toggleWristDeploy() {
        if (currentStatus == Wrist.OUT) {
            currentStatus = Wrist.IN;
        } else {
            currentStatus = Wrist.OUT;
        }
        wristMotor.set(ControlMode.Position, currentStatus.angle);
    }

    public void setArmHeight(double height) {
        armMotor.set(ControlMode.PercentOutput, height);
    }

    public void driveArmPercentOut(double percent) {
        armMotor.set(ControlMode.PercentOutput, percent);
    }

    public double getPotOutputAngle(AnalogPotentiometer pot) {
        return 72.0*pot.get();
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new DriveArmWithJoystickCommand());
    }

    public void setArmSoftLimits(int reverseSoftLimit, int forwardSoftLimit) {
        armMotor.configReverseSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT_PERIODIC);
        armMotor.configReverseSoftLimitThreshold(reverseSoftLimit, RobotMap.CTRE_TIMEOUT_PERIODIC);

        armMotor.configForwardSoftLimitEnable(true, RobotMap.CTRE_TIMEOUT_PERIODIC);
        armMotor.configForwardSoftLimitThreshold(forwardSoftLimit, RobotMap.CTRE_TIMEOUT_PERIODIC);
    }
}
