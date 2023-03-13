package frc.robot.auto;

import com.pathplanner.lib.PathConstraints;
import com.pathplanner.lib.PathPlanner;
import com.pathplanner.lib.PathPlannerTrajectory;
import com.pathplanner.lib.commands.PPRamseteCommand;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.controller.RamseteController;
import edu.wpi.first.math.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.Constants;
import frc.robot.RobotState;
import frc.robot.subsystems.Drive;

public class AutoUtil {

    // Assuming this method is part of a drivetrain subsystem that provides the necessary methods
    public static Command followTrajectoryCommand(PathPlannerTrajectory traj, boolean isFirstPath) {
        return new SequentialCommandGroup(
            new PPRamseteCommand(
                traj,
                RobotState.getInstance()::robotPositionPose2d,
                new RamseteController(Constants.AutoConstants.kRamseteB, Constants.AutoConstants.kRamseteZeta),
                new SimpleMotorFeedforward(Constants.AutoConstants.KA_VOLT_SECONDS_SQUARED_PER_METER, Constants.AutoConstants.KV_VOLT_SECONDS_PER_METER, Constants.AutoConstants.KS_VOLTS),
                Constants.DriveConstants.DIFFERENTIAL_DRIVE_KINEMATICS,
                Drive.getInstance()::wheelSpeeds,
                new PIDController(Constants.AutoConstants.KP, Constants.AutoConstants.KI, Constants.AutoConstants.KD),
                new PIDController(Constants.AutoConstants.KP, Constants.AutoConstants.KI, Constants.AutoConstants.KD),
                Drive.getInstance()::tankDrive,
                Drive.getInstance()
            ));
    }

    public static PathPlannerTrajectory createTrajectory(String pathName, PathConstraints constraints) {
        return PathPlanner.loadPath(pathName, constraints);
    }

    public static PathPlannerTrajectory createTrajectory(String pathName) {
        return PathPlanner.loadPath(pathName, Constants.AutoConstants.DEF_PATH_CONSTRAINTS);
    }

}
