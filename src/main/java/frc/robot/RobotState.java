package frc.robot;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.lib.frc7682.ArmOdometry;
import frc.robot.subsystems.Arm;
import frc.robot.subsystems.Drive;
import frc.robot.subsystems.Turret;
import frc.robot.subsystems.Vision;

public class RobotState{

    private ArmOdometry armOdometry;
    private DifferentialDrivePoseEstimator differentialDrivePoseEstimator;
    private Arm arm;
    private Turret turret;
    private Vision vision;
    private Drive drive;

    // Starter position with SmartDashboard
    //private Pose2d startPosition = 

    private static RobotState robotState = null;
    SendableChooser<Pose2d> startPositionChooser;
    Translation2d initialTranslation2d;

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
        drive = Drive.getInstance();
        armOdometry = new ArmOdometry();
        differentialDrivePoseEstimator =
         new DifferentialDrivePoseEstimator(
                                            Constants.DriveConstants.DIFFERENTIAL_DRIVE_KINEMATICS,
                                            gyroAngle,
                                            leftEncoderMeter,
                                            rightEncoderMeter,
                                            pose2d);

        startPositionChooser = new SendableChooser<>();
        startPositionChooser.setDefaultOption("Red mid : ", new Pose2d());
        startPositionChooser.addOption("Red up : ", new Pose2d());
        startPositionChooser.addOption("Red down : ", new Pose2d());

        startPositionChooser.addOption("Blue up : ", new Pose2d());
        startPositionChooser.addOption("Blue mid : ", new Pose2d());
        startPositionChooser.addOption("Blue down : ", new Pose2d());

    
    drive.resetAll();
    double coeffecient = 0;
    if(DriverStation.getAlliance() == Alliance.Blue){
      coeffecient+=180;
    }

    try{
        if(vision.photonCamera.getLatestResult().getBestTarget() != null){
        differentialDrivePoseEstimator.resetPosition(new Rotation2d(0), 0, 0,
                                                    new Pose2d(
                                                    vision.getEstimatedGlobalPose().get().estimatedPose.getTranslation().toTranslation2d(),
                                                    new Rotation2d(Math.toRadians(drive.getRotation2d().getDegrees()+coeffecient))));
        }
        else{
            differentialDrivePoseEstimator.resetPosition(new Rotation2d(0), 0, 0, startPositionChooser.getSelected());
        }
      }catch(Exception exception){
        exception.printStackTrace();
      }


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
        return differentialDrivePoseEstimator.getEstimatedPosition();
    }
}