package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;
import frc.robot.subsystems.Arm.ProcessingType;

public class Turret extends Subsystem {

    private CANSparkMax turretMotor;
    private CANSparkMax slaveTurretMotor;

    private RelativeEncoder turretEncoder;
    private SparkMaxPIDController turretController;

    private ProcessingType currentProcessingType = ProcessingType.AUTO_CLOSED_LOOP;

    Turret(){
        turretMotor = new CANSparkMax(Constants.TurretConstants.TURRET_MASTER_ID, MotorType.kBrushless);
        turretEncoder = turretMotor.getEncoder();

        turretController = turretMotor.getPIDController();
        turretController.setP(Constants.TurretConstants.TURRET_KP);
        turretController.setI(Constants.TurretConstants.TURRET_KI);
        turretController.setD(Constants.TurretConstants.TURRET_KD);
        turretController.setFF(Constants.TurretConstants.TURRET_KF);
        turretController.setOutputRange(Constants.TurretConstants.TURRET_MIN_OUTPUT, Constants.TurretConstants.TURRET_MAX_OUTPUT);
        turretController.setIZone(Constants.TurretConstants.TURRET_KIZONE);
    }

    public static Turret turret = null;
    public static Turret getInstance(){
        if(turret == null){
            turret = new Turret();
        }
        return turret;
    }

    private PeriodicIO periodicIO = new PeriodicIO();

    public static class PeriodicIO{
        public double turretAngle;
        public double turretVelocity;
        public double degreeSetpoint;
        public double turretDemand;
        public IdleMode neutralMode;
    }

    @Override
    public void writePeriodicOutputs() {
        turretMotor.set(periodicIO.turretDemand);
        calculateAllMeasurement();

        if(!(turretMotor.getFault(FaultID.kSensorFault)) && currentProcessingType == ProcessingType.AUTO_CLOSED_LOOP || currentProcessingType == ProcessingType.BY_HAND){
            calculateClosedLoopDemands();
        }
        else{
            setDesiredProcessingType(ProcessingType.MANUAL);
        }
    }

    @Override
    public void readPeriodicInputs() {
        SmartDashboard.putNumber("Turret Angle", periodicIO.turretAngle);
        SmartDashboard.putNumber("Turret Velocity", periodicIO.turretVelocity);
        SmartDashboard.putNumber("Turret Demand", periodicIO.turretDemand);
        SmartDashboard.putString("Turret Neutral Mode", periodicIO.neutralMode.toString());
    }

    public void setDesiredProcessingType(ProcessingType processingType){
        currentProcessingType = processingType;
    }

    public void calculateClosedLoopDemands(){
        double degreeSetpoint = Constants.TurretConstants.TURRET_GEAR * periodicIO.degreeSetpoint / 360;
        turretController.setReference(degreeSetpoint, ControlType.kPosition);
    }

    public void setSetpointAutoClosedLoop(double degreeSetpoint){
        //degreeSetpoint = configureSetpointValue(degreeSetpoint);
        periodicIO.degreeSetpoint = degreeSetpoint;
    }
    
    // For manual control
    public void controlTurret(double yAxis, double xAxis){
        if(currentProcessingType == ProcessingType.BY_HAND){
            // Get radian value from joystick and convert it to degree
            setSetpointByHand(Math.toDegrees(Math.atan2(yAxis, xAxis)));
        }
        else if(currentProcessingType == ProcessingType.MANUAL){
            setManual(yAxis);
        }
    }

    private void setManual(double turretDemand){
        periodicIO.turretDemand = turretDemand;
    }

    private void setSetpointByHand(double turretSetpoint){
        setSetpointAutoClosedLoop(turretSetpoint);
    }

    // Turret Validations for mechanical limits (for getAngle method)
    /*public double configureSetpointValue(double degreeSetpoint){
        // We need to check if the setpoint is in the mechanical limits
        double alternativeDegreeSetpoint = degreeSetpoint + 360;
        double alternativeDistance = Math.abs(alternativeDegreeSetpoint - periodicIO.turretAngle);
        double normalDistance = Math.abs(degreeSetpoint - periodicIO.turretAngle);
        // True -> degreeSetpoint | False -> alternativeDegreeSetpoint
        boolean x = normalDistance < alternativeDistance;
        double finalSetpoint = x ? degreeSetpoint : alternativeDegreeSetpoint;

        if(finalSetpoint > Constants.TurretConstants.TURRET_LIMIT){
            if(x==true){
                return alternativeDegreeSetpoint;
            }
        }

        return finalSetpoint;
    }*/

    // Turret PIDF
    public boolean turretAngleAtSetpoint(){
        return false;
    }

    public boolean isEncoderFaultExist(){
        return turretMotor.getFault(FaultID.kSensorFault);
    }

    // Information data about the turret
    public void calculateAllMeasurement(){
        periodicIO.turretAngle = turretAngle();
        periodicIO.turretVelocity = Math.abs(getTurretVelocity());
    }

    public double getTurretVelocity(){
        return turretEncoder.getVelocity() / 60;
    }

    public double turretAngle(){
        return getTurretPosition() * Constants.TurretConstants.K_TURRET_TICKS2DEGREE;
    }

    public double getTurretPosition(){
        return turretEncoder.getPosition();
    }

    // Motor Controllers Modes        
    public void openBrakeMode(){
        setNeutralMode(IdleMode.kBrake);
        periodicIO.neutralMode = IdleMode.kBrake;
    }
    
    public void openCoastMode(){
        setNeutralMode(IdleMode.kCoast);
        periodicIO.neutralMode = IdleMode.kCoast;
    }
    
    private void setNeutralMode(IdleMode mode){
        turretMotor.setIdleMode(mode);
        slaveTurretMotor.setIdleMode(mode);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        turretMotor.stopMotor();
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
