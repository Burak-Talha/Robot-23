package frc.robot;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import frc.robot.lib.frc7682.ArmOdometry;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class RobotState{

    private ArmOdometry armOdometry;
    private DifferentialDrivePoseEstimator differentialDrivePoseEstimator;
    private Arm arm;
    private Turret turret;
    private Vision vision;

    RobotState(Rotation2d gyroAngle, double leftEncoderMeter, double rightEncoderMeter, Pose2d pose2d){
        arm = Arm.getInstance();
        turret = Turret.getInstance();
        vision = Vision.getInstance();
        armOdometry = new ArmOdometry();
        differentialDrivePoseEstimator =
         new DifferentialDrivePoseEstimator(
                                            Constants.DriveConstants.DIFFERENTIAL_DRIVE_KINEMATICS,
                                            gyroAngle,
                                            leftEncoderMeter,
                                            rightEncoderMeter,
                                            pose2d);
    }

    public static class PeriodicIO{
        public double turretAngle;
        public double shoulderAngle;
        public double armLength;
        /* Transformations */
    }

    public void update(Rotation2d gyroAngle, double leftDistance, double rightDistance){
        differentialDrivePoseEstimator.update(gyroAngle, leftDistance, rightDistance);
        armOdometry.update(differentialDrivePoseEstimator.getEstimatedPosition(), turret.getAngle(), arm.getShoulderAngle(), arm.getExtensionDistance());
    }

    public Pose3d armPositionPose3d(){
        return armOdometry.getEstimatedPosition();
    }

    public Pose2d robotPositionPose2d(){
        return differentialDrivePoseEstimator.getEstimatedPosition();
    }

    /*  
     *      ODOMETRY
     * Differential Drive Pose Estimator
     * Arm Pose to robot pose(do calculations for arm length and height)
     *
     *  
     *      CONSTRAINTS
     * robot arm shoulder velocity, acceleration
     * differential constraints 
     * 
     * --------
     *      Transformations
     * robot pose -> turret pose
     * turret pose -> robot end effector position
     * 
     * robot end effector position -> target | Transformations
    */




    
}
