package org.team1294.firstpowerup.robot.subsystems;

import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.team1294.firstpowerup.robot.RobotMap;
import org.team1294.firstpowerup.robot.commands.ExampleCommand;

/**
 * An example {@link Subsystem}. A Subsystem represents a system on the
 * robot, and all of the hardware that makes up that system. The subsystem
 * allows access for {@link edu.wpi.first.wpilibj.command.Command Commands}
 * to manipulate the hardware through methods.
 */
public class ExampleSubsystem extends Subsystem {
    private Spark exampleTalon;

    public ExampleSubsystem() {
        super("Example Subsystem");
        exampleTalon = new Spark(RobotMap.EXAMPLE_MOTOR_CONTROLLER);
    }

    public void exampleRunForwardFullSpeed() {
        exampleTalon.set(1.0);
    }

    public void exampleStop() {
        exampleTalon.set(0.0);
    }

    @Override
    protected void initDefaultCommand() {
        setDefaultCommand(new ExampleCommand());
    }
}
