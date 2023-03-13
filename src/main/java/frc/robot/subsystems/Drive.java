package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.ControlType;
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
    private final CANSparkMax leftMaster;
    private final CANSparkMax leftSlave;
    private final CANSparkMax rightMaster;
    private final CANSparkMax rightSlave;

    // Sensors
    private final AHRS navx;
    private final Encoder leftEncoder;
    private final Encoder rightEncoder;

    private final SynchronousPIDF balancePidf;

    // Drive
    private final DifferentialDrive differentialDrive;

    private DriveMode currentDriveMode = DriveMode.SMOOTH;

    public enum DriveMode{
        SMOOTH,
        BALANCE
    }

    Drive(){
        // Initialization
        leftMaster = new CANSparkMax(Constants.DriveConstants.MASTER_LEFT_ID, MotorType.kBrushless);
        leftSlave = new CANSparkMax(Constants.DriveConstants.SLAVE_LEFT_ID, MotorType.kBrushless);
        rightMaster = new CANSparkMax(Constants.DriveConstants.MASTER_RIGHT_ID, MotorType.kBrushless);
        rightSlave = new CANSparkMax(Constants.DriveConstants.SLAVE_RIGHT_ID, MotorType.kBrushless);

        navx = new AHRS(Port.kMXP);
        rightEncoder = new Encoder(Constants.DriveConstants.RIGHT_ENCODER_A, Constants.DriveConstants.RIGHT_ENCODER_B);
        leftEncoder = new Encoder(Constants.DriveConstants.LEFT_ENCODER_A, Constants.DriveConstants.LEFT_ENCODER_B);

        differentialDrive = new DifferentialDrive(leftMaster, rightMaster);

        // Follow Master's
        leftSlave.follow(leftMaster);
        rightSlave.follow(rightMaster);

        // Reverse right side!
        rightMaster.setInverted(true);
        rightSlave.setInverted(true);
        
        balancePidf = new SynchronousPIDF(Constants.DriveConstants.BALANCE_KP, Constants.DriveConstants.BALANCE_KI, Constants.DriveConstants.BALANCE_KD, Constants.DriveConstants.BALANCE_KF);
        balancePidf.setSetpoint(0.0);
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
        setMotor(periodicIO.wheelSpeeds);
    }

    public void cleverDrive(double speed, double rotation){
        switch(currentDriveMode){
            case SMOOTH:
                // Smooth Drive
                arcadeDrive(speed, rotation);
                break;
            case BALANCE:
                // Balance Drive
                balanceDrive();
                break;
        }
    }

    public void setDriveMode(DriveMode driveMode){
        currentDriveMode = driveMode;
    }

    // Driver Methods
    private void arcadeDrive(double speed, double rotation){
       periodicIO.wheelSpeeds = DifferentialDrive.arcadeDriveIK(speed, rotation, false);
    }

    private void balanceDrive(){
        double wheelSpeed = balancePidf.calculate(getPitch(), Timer.getFPGATimestamp());
        periodicIO.wheelSpeeds = new WheelSpeeds(wheelSpeed, wheelSpeed);
    }

    public void tankDrive(double leftSpeed, double rightSpeed){
        periodicIO.wheelSpeeds.left = leftSpeed;
        periodicIO.wheelSpeeds.right = rightSpeed;
    }

    private void setMotor(WheelSpeeds wheelSpeeds){
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
        leftEncoder.reset();
        rightEncoder.reset();
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
        periodicIO.leftMeterDistance = leftEncoder.get() * Constants.DriveConstants.KDRIVETICK2METER;
        return periodicIO.leftMeterDistance;
    }

    public double getRightEncoderMeter(){
        periodicIO.rightMeterDistance = rightEncoder.get() * Constants.DriveConstants.KDRIVETICK2METER;
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
        return new DifferentialDriveWheelSpeeds(leftEncoder.getRate(), rightEncoder.getRate());
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
