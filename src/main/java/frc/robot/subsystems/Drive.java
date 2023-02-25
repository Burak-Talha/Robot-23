package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.estimator.DifferentialDrivePoseEstimator;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;

public class Drive extends Subsystem{

    // Controllers
    private final CANSparkMax leftMaster;
    private final CANSparkMax leftSlave;
    private final CANSparkMax rightMaster;
    private final CANSparkMax rightSlave;

    // Sensors
    private final AHRS navx;
    private final Encoder leftEncoder;
    private final Encoder rightEncoder;

    // Drive
    private final DifferentialDrive differentialDrive;
    private final DifferentialDrivePoseEstimator differentialDrivePoseEstimator;

    private static Drive drive = null;


    Drive(){
        // Initialization
        leftMaster = new CANSparkMax(0, MotorType.kBrushless);
        leftSlave = new CANSparkMax(1, MotorType.kBrushless);
        rightMaster = new CANSparkMax(2, MotorType.kBrushless);
        rightSlave = new CANSparkMax(3, MotorType.kBrushless);

        navx = new AHRS(Port.kMXP);
        rightEncoder = new Encoder(0, 1);
        leftEncoder = new Encoder(2, 3);

        differentialDrive = new DifferentialDrive(leftMaster, rightMaster);
        differentialDrivePoseEstimator =
        new DifferentialDrivePoseEstimator(Constants.DriveConstants.DIFFERENTIAL_DRIVE_KINEMATICS,
                                            getRotation2d(),
                                            getLeftEncoderMeter(),
                                            getRightEncoderMeter(),
                                            new Pose2d());

        // Follow Master's
        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);

        // Reverse right side!
        rightMaster.setInverted(true);
        rightSlave.setInverted(true);
        
    }

    public static final Drive getInstance(){
        if(drive == null){
            drive = new Drive();
        }
        return drive;
    }

    public static class PeriodicIO {
        double leftDemand;
        double rightDemand;
        double leftEncoder;
        double rightEncoder;
        double currentAngle;
        WheelSpeeds wheelSpeeds;
    }

    private final PeriodicIO periodicIO = new PeriodicIO();

    @Override
    // Optional design pattern for caching periodic reads to avoid hammering the HAL/CAN.
    public void readPeriodicInputs() {
        // Example Usage
        SmartDashboard.putNumber("Robot Pose X:", differentialDrivePoseEstimator.getEstimatedPosition().getX());
        SmartDashboard.putNumber("Robot Pose Y:", differentialDrivePoseEstimator.getEstimatedPosition().getY());
    }

    @Override
    // Optional design pattern for caching periodic writes to avoid hammering the HAL/CAN.
    public void writePeriodicOutputs() {
        differentialDrivePoseEstimator.update(getRotation2d(), getLeftEncoderMeter(), getRightEncoderMeter());
        setMotor(periodicIO.wheelSpeeds);
    }


    // Driver Methods
    public void arcadeDrive(double speed, double rotation){
       periodicIO.wheelSpeeds = DifferentialDrive.arcadeDriveIK(speed, rotation, false);
    }

    public void tankDrive(double leftSpeed, double rightSpeed){
        periodicIO.wheelSpeeds.left = leftSpeed;
        periodicIO.wheelSpeeds.right = rightSpeed;
    }

    public void setMotor(WheelSpeeds wheelSpeeds){
        leftMaster.set(wheelSpeeds.left);
        rightMaster.set(wheelSpeeds.right);
    }

    // Motor Controllers Configurations
    public IdleMode getMode(){
        return leftMaster.getIdleMode();
    }

    public void restoreFactory(){
        leftMaster.restoreFactoryDefaults();
        leftSlave.restoreFactoryDefaults();
        rightMaster.restoreFactoryDefaults();
        rightSlave.restoreFactoryDefaults();
    }

    public void stopSystem(){
        leftMaster.stopMotor();
        leftSlave.stopMotor();
        rightMaster.stopMotor();
        rightSlave.stopMotor();
    }

    public void openBrakeMode(){
        setNeutralMode(IdleMode.kBrake);
    }

    public void openCoastMode(){
        setNeutralMode(IdleMode.kCoast);
    }

    private void setNeutralMode(IdleMode mode){
        leftMaster.setIdleMode(mode);
        leftSlave.setIdleMode(mode);
        
        rightMaster.setIdleMode(mode);
        rightSlave.setIdleMode(mode);
    }

    // Pose Transactions
    public Pose2d getPose(){
        return differentialDrivePoseEstimator.getEstimatedPosition();
    }

    public void addVisionForPose(Pose2d pose2d, double timestamp){
        differentialDrivePoseEstimator.addVisionMeasurement(pose2d, timestamp);
    }

    public void resetRobotPose(Rotation2d angle, double leftEncoderMeter, double rightEncoderMeter, Pose2d newPose){
        differentialDrivePoseEstimator.resetPosition(angle, leftEncoderMeter, rightEncoderMeter, newPose);        
    }

    public void resetRobotPose(){
        differentialDrivePoseEstimator.resetPosition(new Rotation2d(), 0, 0, new Pose2d());
    }

    // Encoder Informations
    public double getEncodersAvgMeter(){
        return (getLeftEncoderMeter() + getRightEncoderMeter()) / 2;
    }

    public double getLeftEncoderMeter(){
        return leftEncoder.get() * Constants.DriveConstants.KDRIVETICK2METER;
    }

    public double getRightEncoderMeter(){
        return rightEncoder.get() * Constants.DriveConstants.KDRIVETICK2METER;
    }

    // IMU Informations
    public double getAngle(){
        return navx.getAngle();
    }

    public Rotation2d getRotation2d(){
        return navx.getRotation2d();
    }

    // Get Pitch, Roll, Yaw
    public double getYaw(){
        return navx.getYaw();
    }

    public double getPitch(){
        return navx.getPitch();
    }

    public double getRoll(){
        return navx.getRoll();
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public boolean checkSystem() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void outputTelemetry() {
        // TODO Auto-generated method stub
        
    }
    
}
