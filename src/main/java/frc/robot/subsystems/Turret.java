package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;
import frc.robot.lib.frc254.util.SynchronousPIDF;


// NOTE : Also should be setted velocity
public class Turret extends Subsystem {

    private CANSparkMax masterTurretMotor;
    private CANSparkMax slaveTurretMotor;

    private RelativeEncoder turretEncoder;
    private SynchronousPIDF turretPidf;

    private ProcessingType currentProcessingType = ProcessingType.AUTO_CLOSED_LOOP;

    public enum ProcessingType{
        AUTO_CLOSED_LOOP,
        BY_HAND,
        MANUAL
    }


    
    Turret(){
        masterTurretMotor = new CANSparkMax(Constants.TurretConstants.TURRET_MASTER_ID, MotorType.kBrushless);
        slaveTurretMotor = new CANSparkMax(Constants.TurretConstants.TURRET_SLAVE_ID, MotorType.kBrushless);
        slaveTurretMotor.follow(masterTurretMotor);

        turretEncoder = masterTurretMotor.getEncoder();
        turretPidf = new SynchronousPIDF(Constants.TurretConstants.TURRET_KP, Constants.TurretConstants.TURRET_KI, Constants.TurretConstants.TURRET_KD, Constants.TurretConstants.TURRET_KF);
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
        public double turretDemand;
        public IdleMode neutralMode;
    }

    @Override
    public void writePeriodicOutputs() {
        masterTurretMotor.set(periodicIO.turretDemand);
        calculateAllMeasurement();

        if(!isEncoderFaultExist() && currentProcessingType == ProcessingType.AUTO_CLOSED_LOOP || currentProcessingType == ProcessingType.BY_HAND){
            periodicIO.turretDemand = turretPidf.calculate(periodicIO.turretAngle, periodicIO.turretDemand);
        }
    }

    @Override
    public void readPeriodicInputs() {
        SmartDashboard.putNumber("Turret Angle", periodicIO.turretAngle);
        SmartDashboard.putNumber("Turret Velocity", periodicIO.turretVelocity);
        SmartDashboard.putNumber("Turret Demand", periodicIO.turretDemand);
        SmartDashboard.putString("Turret Neutral Mode", periodicIO.neutralMode.toString());
    }

    public void setProcessingType(ProcessingType processingType){
        currentProcessingType = processingType;
    }

    public void calculateClosedLoopDemands(){
        periodicIO.turretDemand = turretPidf.calculate(periodicIO.turretAngle, periodicIO.turretDemand);
    }

    public void setSetpointAutoClosedLoop(double degreeSetpoint){
        degreeSetpoint = turretSetpointValid(degreeSetpoint);
        turretPidf.setSetpoint(degreeSetpoint);
    }
    
    // For manual control
    public void controlTurret(double turretInput){
        if(currentProcessingType == ProcessingType.BY_HAND){
            setSetpointByHand(turretInput);
        }
        else if(currentProcessingType == ProcessingType.MANUAL){
            setManual(turretInput);
        }
    }

    private void setManual(double turretDemand){
        periodicIO.turretDemand = turretDemand;
    }

    private void setSetpointByHand(double turretSetpoint){
        setSetpointAutoClosedLoop(turretSetpoint);
    }

    // Turret Validations for mechanical limits
    public double turretSetpointValid(double degreeSetpoint){
        return 0;
    }

    // Turret PIDF
    public boolean turretAngleAtSetpoint(){
        return turretPidf.onTarget(3);
    }

    public boolean isEncoderFaultExist(){
        return masterTurretMotor.getFault(FaultID.kSensorFault);
    }

    // Information data about the turret
    public void calculateAllMeasurement(){
        periodicIO.turretAngle = getTurretAngle();
        periodicIO.turretVelocity = getTurretVelocity();
    }

    public double getTurretVelocity(){
        return turretEncoder.getVelocity() / 60;
    }

    public double getTurretAngle(){
        double turretAngle = getTurretPpr() * Constants.TurretConstants.K_TURRET_TICKS2DEGREE;
        periodicIO.turretAngle = turretAngle;
        return turretAngle;
    }

    public double getTurretPpr(){
        return turretEncoder.getCountsPerRevolution() / 4;
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
        masterTurretMotor.setIdleMode(mode);
        slaveTurretMotor.setIdleMode(mode);
    }

    @Override
    public void stop() {
        // TODO Auto-generated method stub
        masterTurretMotor.stopMotor();
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
