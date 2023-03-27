package frc.robot;

import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;

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
    private PhotonPoseEstimator photonPoseEstimator;
    private Arm arm;
    private Turret turret;
    private Vision vision;

    // Starter position with SmartDashboard
    //private Pose2d startPosition = 

    private static RobotState robotState = null;

    public static RobotState getInstance(){
        if(robotState == null){
            robotState = new RobotState(new Rotation2d(), 0, 0, new Pose2d());
        }
        return robotState;
    }

    public RobotState(Rotation2d gyroAngle, double leftEncoderMeter, double rightEncoderMeter, Pose2d pose2d){
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
        photonPoseEstimator = new PhotonPoseEstimator(FieldConstants.APRIL_TAG_FIELD_LAYOUT, PoseStrategy.AVERAGE_BEST_TARGETS, vision.photonCamera, Constants.VisionConstants.CAMERA_TO_ROBOT);
    }

    public static class PeriodicIO{
        public double turretAngle;
        public double shoulderAngle;
        public double armLength;
        /* Transformations */
    }

    public void update(Rotation2d gyroAngle, double leftDistance, double rightDistance){
        differentialDrivePoseEstimator.update(gyroAngle, leftDistance, rightDistance);
        armOdometry.update(turret.turretAngle(), arm.shoulderAngle(), arm.extensibleDistance());
    }

    // Arm odometry based-on chassis
    public Pose3d robotArmPose3d(){
        return new Pose3d(armPositionPose3d().getX() + robotPositionPose2d().getX(), armPositionPose3d().getY() + robotPositionPose2d().getY(), armPositionPose3d().getZ(), armPositionPose3d().getRotation());
    }

    // Only Arm Odometry
    public Pose3d armPositionPose3d(){
        return armOdometry.getEstimatedPosition();
    }

    // Only Chassis odometry
    public Pose2d robotPositionPose2d(){
        Pose2d photonPose = photonPoseEstimator.update().orElseThrow().estimatedPose.toPose2d();
        return photonPose.interpolate(differentialDrivePoseEstimator.getEstimatedPosition(), 0.4);
    }
}