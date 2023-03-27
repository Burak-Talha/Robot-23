package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.drive.DifferentialDrive.WheelSpeeds;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;
import frc.robot.lib.frc254.util.SynchronousPIDF;

public class Drive extends Subsystem{

    // Controllers
    private CANSparkMax leftMaster;
    private CANSparkMax leftSlave;
    private CANSparkMax rightMaster;
    private CANSparkMax rightSlave;

    // Sensors
    private AHRS navx;
    private Encoder leftEncoder1 = new Encoder(0,1);
    private Encoder rightEncoder1 = new Encoder(2,3);
    private final RelativeEncoder leftEncoder = leftMaster.getEncoder();
    private final RelativeEncoder rightEncoder = rightMaster.getEncoder();

    private SynchronousPIDF balancePidf;

    private SynchronousPIDF rotationSynchronousPIDF;
    private SynchronousPIDF speedSynchronousPIDF;

    // Drive
    private DifferentialDrive differentialDrive;


    // PID Controllers for velocity based drive
    private SparkMaxPIDController leftMasterPidController = leftMaster.getPIDController();
    private SparkMaxPIDController rightMasterPidController = rightMaster.getPIDController();
    private SparkMaxPIDController leftSlavePidController = leftSlave.getPIDController();
    private SparkMaxPIDController rightSlavePidController = rightSlave.getPIDController();

    private DriveMode currentDriveMode = DriveMode.MANUAL;

    public enum DriveMode{
        MANUAL,
        VELOCITY,
        BALANCE
    }

    Drive(){
        // Initialization
        leftMaster = new CANSparkMax(Constants.DriveConstants.MASTER_LEFT_ID, MotorType.kBrushless);
        leftSlave = new CANSparkMax(Constants.DriveConstants.SLAVE_LEFT_ID, MotorType.kBrushless);
        rightMaster = new CANSparkMax(Constants.DriveConstants.MASTER_RIGHT_ID, MotorType.kBrushless);
        rightSlave = new CANSparkMax(Constants.DriveConstants.SLAVE_RIGHT_ID, MotorType.kBrushless);

        navx = new AHRS(Port.kMXP);

        differentialDrive = new DifferentialDrive(leftMaster, rightMaster);

        // Follow Master's
        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);

        // Reverse right side!
        rightMaster.setInverted(true);
        rightSlave.setInverted(true);
        
        // Balancing PID
        balancePidf = new SynchronousPIDF(Constants.DriveConstants.BALANCE_KP, Constants.DriveConstants.BALANCE_KI, Constants.DriveConstants.BALANCE_KD, Constants.DriveConstants.BALANCE_KF);
        balancePidf.setSetpoint(0.0);

        rotationSynchronousPIDF = new SynchronousPIDF(Constants.DriveConstants.LEFT_KP, Constants.DriveConstants.LEFT_KI, Constants.DriveConstants.LEFT_KD, Constants.DriveConstants.LEFT_KF);
        speedSynchronousPIDF = new SynchronousPIDF(Constants.DriveConstants.RIGHT_KP, Constants.DriveConstants.RIGHT_KI, Constants.DriveConstants.RIGHT_KD, Constants.DriveConstants.RIGHT_KF);


    // Tele-Op Drive pid
        // Left Side
        leftMasterPidController.setP(Constants.DriveConstants.LEFT_KP);
        leftMasterPidController.setI(Constants.DriveConstants.LEFT_KI);
        leftMasterPidController.setD(Constants.DriveConstants.LEFT_KD);
        leftMasterPidController.setFF(Constants.DriveConstants.LEFT_KF);

        leftSlavePidController.setP(Constants.DriveConstants.LEFT_KP);
        leftSlavePidController.setI(Constants.DriveConstants.LEFT_KI);
        leftSlavePidController.setD(Constants.DriveConstants.LEFT_KD);
        leftSlavePidController.setFF(Constants.DriveConstants.LEFT_KF);

        // Right Side
        rightMasterPidController.setP(Constants.DriveConstants.RIGHT_KP);
        rightMasterPidController.setI(Constants.DriveConstants.RIGHT_KI);
        rightMasterPidController.setD(Constants.DriveConstants.RIGHT_KD);
        rightMasterPidController.setFF(Constants.DriveConstants.RIGHT_KF);

        rightSlavePidController.setP(Constants.DriveConstants.RIGHT_KP);
        rightSlavePidController.setI(Constants.DriveConstants.RIGHT_KI);
        rightSlavePidController.setD(Constants.DriveConstants.RIGHT_KD);
        rightSlavePidController.setFF(Constants.DriveConstants.RIGHT_KF);
    }

    private static Drive drive = null;
    public static final Drive getInstance(){
        if(drive == null){
            drive = new Drive();
        }
        return drive;
    }

    public static class PeriodicIO {
        double rightMeterDistance;
        double leftMeterDistance;
        double currentYaw;
        WheelSpeeds wheelSpeeds;
    }

    private final PeriodicIO periodicIO = new PeriodicIO();

    @Override
    // Optional design pattern for caching periodic reads to avoid hammering the HAL/CAN.
    public void readPeriodicInputs() {
        // Example Usage
        SmartDashboard.putNumber("Left Encoder", periodicIO.leftMeterDistance);
        SmartDashboard.putNumber("Right Encoder", periodicIO.rightMeterDistance);
        SmartDashboard.putNumber("Yaw", periodicIO.currentYaw);
    }

    @Override
    // Optional design pattern for caching periodic writes to avoid hammering the HAL/CAN.
    public void writePeriodicOutputs() {
        // Example Usage
        leftMaster.set(periodicIO.wheelSpeeds.left);
        rightMaster.set(periodicIO.wheelSpeeds.right);

        if(leftMaster.getFault(FaultID.kSensorFault) || rightMaster.getFault(FaultID.kSensorFault)){
            setDriveMode(DriveMode.MANUAL);
        }
    }

    public void cleverDrive(double speed, double rotation){
        switch(currentDriveMode){
            case MANUAL:
                // Smooth Drive
                arcadeDrive(speed, rotation);
            case BALANCE:
                // Balance Drive
                balanceDrive();
            case VELOCITY:
                // Velocity Drive
                velocityDrive(speed, rotation);
        }
    }

    public void setDriveMode(DriveMode driveMode){
        currentDriveMode = driveMode;
    }

    // Driver Methods
    private void arcadeDrive(double speed, double rotation){
       periodicIO.wheelSpeeds = DifferentialDrive.arcadeDriveIK(speed, rotation, false);
       tankDrive(wheelSpeeds().leftMetersPerSecond, wheelSpeeds().rightMetersPerSecond);
    }

    private void balanceDrive(){
        double wheelSpeed = balancePidf.calculate(getPitch(), Timer.getFPGATimestamp());
        tankDrive(wheelSpeed, wheelSpeed);
    }

    private void velocityDrive(double speed, double rotation){
        WheelSpeeds wheelSpeeds = DifferentialDrive.arcadeDriveIK(speed, rotation, false);
        // Limit in RPM units
        wheelSpeeds.left *= Constants.DriveConstants.MAX_WHEEL_RPM;
        wheelSpeeds.right *= Constants.DriveConstants.MAX_WHEEL_RPM;
        leftMasterPidController.setReference(wheelSpeeds.left, ControlType.kVelocity);
        leftSlavePidController.setReference(wheelSpeeds.left, ControlType.kVelocity);
        rightMasterPidController.setReference(wheelSpeeds.right, ControlType.kVelocity);
        rightSlavePidController.setReference(wheelSpeeds.right, ControlType.kVelocity);
    }

    // For Autonomous
    public void straightAutoDrive(double degreeSetpoint, double meterSetpoint){
        rotationSynchronousPIDF.setSetpoint(degreeSetpoint);
        speedSynchronousPIDF.setSetpoint(meterSetpoint);

        double rotation = rotationSynchronousPIDF.calculate(getYaw(), Timer.getFPGATimestamp());
        double speed = speedSynchronousPIDF.calculate(periodicIO.leftMeterDistance, Timer.getFPGATimestamp());
        differentialDrive.arcadeDrive(speed, rotation);
    }

    public void rotationAutoDrive(double degreeSetpoint){
        rotationSynchronousPIDF.setSetpoint(degreeSetpoint);
        double rotation = rotationSynchronousPIDF.calculate(getYaw(), Timer.getFPGATimestamp());
        differentialDrive.arcadeDrive(0, rotation);
    }

    public void tankDrive(double leftSpeed, double rightSpeed){
        periodicIO.wheelSpeeds.left = leftSpeed;
        periodicIO.wheelSpeeds.right = rightSpeed;
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

    public void setMaxOutputDifferential(double maxOutput){
        differentialDrive.setMaxOutput(maxOutput);
    }

    // Motor Controllers Modes
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

    // Sensors Configurations
    public void resetEncoders(){
        leftMaster.getEncoder().setPosition(0);
        rightMaster.getEncoder().setPosition(0);
    }

    public void resetGyro(){
        navx.reset();
    }

    public void resetAll(){
        resetEncoders();
        resetGyro();
    }

    // Encoder Informations
    public double getEncodersAvgMeter(){
        return (getLeftEncoderMeter() + getRightEncoderMeter()) / 2;
    }

    public double getLeftEncoderMeter(){
        periodicIO.leftMeterDistance = leftEncoder.getPosition() * Constants.DriveConstants.KDRIVETICK2METER;
        return periodicIO.leftMeterDistance;
    }

    public double getRightEncoderMeter(){
        periodicIO.rightMeterDistance = rightEncoder.getPosition() * Constants.DriveConstants.KDRIVETICK2METER;
        return periodicIO.rightMeterDistance;
    }

    // IMU Informations
    public double getAngle(){
        return Math.abs(navx.getAngle() % 360);
    }

    public Rotation2d getRotation2d(){
        return navx.getRotation2d();
    }

    // Get Pitch, Roll, Yaw
    public double getYaw(){
        periodicIO.currentYaw = navx.getYaw();
        return periodicIO.currentYaw;
    }

    public double getPitch(){
        return navx.getPitch();
    }

    public double getRoll(){
        return navx.getRoll();
    }

    public DifferentialDriveWheelSpeeds wheelSpeeds(){
        return new DifferentialDriveWheelSpeeds(leftEncoder1.getRate(), rightEncoder1.getRate());
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        differentialDrive.stopMotor();
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
