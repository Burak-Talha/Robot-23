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
import frc.robot.lib.frc254.util.Units;

public class Arm extends Subsystem{

    private final CANSparkMax rotatableMaster;
    private final CANSparkMax extensibleMotor;

    private RelativeEncoder extensibleEncoder;
    private RelativeEncoder rotatableEncoder;

    private SparkMaxPIDController extensibleController;
    private SparkMaxPIDController rotatableController;

    public enum ProcessingType{
        AUTO_CLOSED_LOOP,
        BY_HAND,
        MANUAL
    }

    public enum ValidationType{
        EXTENSIBLE,
        ROTATABLE
    }
    
    private ProcessingType currentProcessingType = ProcessingType.AUTO_CLOSED_LOOP;
    private PeriodicIO periodicIO = new PeriodicIO();

    public static Arm arm = null;

    public static Arm getInstance(){
        if(arm == null){
            arm = new Arm();
        }
        return arm;
    }

    Arm(){
        rotatableMaster = new CANSparkMax(Constants.ArmConstants.MASTER_ROTATABLE_ID, MotorType.kBrushless);
        extensibleMotor = new CANSparkMax(Constants.ArmConstants.MASTER_EXTENSION_ID, MotorType.kBrushless);

        extensibleEncoder = extensibleMotor.getEncoder();
        rotatableEncoder = rotatableMaster.getEncoder();

        // Configuring the PID Controllers
        extensibleController = extensibleMotor.getPIDController();
        rotatableController = rotatableMaster.getPIDController();

        extensibleController.setP(Constants.ArmConstants.EXTENSIBLE_KP);
        extensibleController.setI(Constants.ArmConstants.EXTENSIBLE_KI);
        extensibleController.setD(Constants.ArmConstants.EXTENSIBLE_KD);
        extensibleController.setIZone(Constants.ArmConstants.EXTENSIBLE_KIZONE);
        extensibleController.setFF(Constants.ArmConstants.EXTENSIBLE_KF);
        extensibleController.setOutputRange(Constants.ArmConstants.EXTENSIBLE_MIN_OUTPUT, Constants.ArmConstants.EXTENSIBLE_MAX_OUTPUT);

        rotatableController.setP(Constants.ArmConstants.SHOULDER_KP);
        rotatableController.setI(Constants.ArmConstants.SHOULDER_KI);
        rotatableController.setD(Constants.ArmConstants.SHOULDER_KD);
        rotatableController.setIZone(Constants.ArmConstants.SHOULDER_KIZONE);
        rotatableController.setFF(Constants.ArmConstants.SHOULDER_KF);
        rotatableController.setOutputRange(Constants.ArmConstants.SHOULDER_MIN_OUTPUT, Constants.ArmConstants.SHOULDER_MAX_OUTPUT);
    }

    public static class PeriodicIO {
        double extensibleDemand;
        double rotatableDemand;
        double currentShoulderAngle;
        double currentExtensibleMeter;
        double shoulderAngleSetpoint;
        double extensibleMeterSetpoint;
        IdleMode neutralMode;
    }

    @Override
    public void writePeriodicOutputs() {
        rotatableMaster.set(periodicIO.rotatableDemand);
        extensibleMotor.set(periodicIO.extensibleDemand);
        calculateAllMeasurement();

        // if sensors is ok and we want the  control manually we should control those situations
        if(!(rotatableMaster.getFault(FaultID.kSensorFault) || extensibleMotor.getFault(FaultID.kSensorFault)) && currentProcessingType == ProcessingType.AUTO_CLOSED_LOOP || currentProcessingType == ProcessingType.BY_HAND){
            calculateClosedLoopDemands();
        }
        else{
            setDesiredProcessingType(ProcessingType.MANUAL);
        }
    }

    @Override
    public void readPeriodicInputs() {
        // Example Usage
        SmartDashboard.putNumber("Arm Angle :", periodicIO.currentShoulderAngle);
        SmartDashboard.putNumber("Extensible Meter :", periodicIO.currentExtensibleMeter);
        SmartDashboard.putString("Arm Mode :", currentProcessingType.toString());
        SmartDashboard.putString("Arm Motor Modes :", periodicIO.neutralMode.toString());
    }

        public void setDesiredProcessingType(ProcessingType type){
            currentProcessingType = type;
        }

        private void calculateClosedLoopDemands(){
            rotatableController.setReference(periodicIO.shoulderAngleSetpoint, ControlType.kPosition);
            extensibleController.setReference(periodicIO.extensibleMeterSetpoint, ControlType.kPosition);
        }

        public void controlArm(double rotatable, double extensible){
            if(currentProcessingType == ProcessingType.MANUAL){
                setManual(rotatable, extensible);
            }else if(currentProcessingType == ProcessingType.BY_HAND){
                setSetpointByHandClosedLoop(rotatable, extensible);
            }
        }

        public void setManual(double rotatable, double extensible){
            periodicIO.rotatableDemand = rotatable;
            periodicIO.extensibleDemand = extensible;
        }

        public void setSetpointByHandClosedLoop(double rotatableInput, double extensibleInput){
            // This logic goes incrementally like this : current degree = 50, snapshotRequest = 10 , then the setpoint will be 60
            double rotatableDegreeSetpoint = Constants.ArmConstants.k_ARM_ANGLE_OFFSET + periodicIO.currentShoulderAngle + rotatableInput * Constants.ArmConstants.ANGLE_OF_MOVEMENT;
            double extensibleMeterSetpoint = Constants.ArmConstants.DEFAULT_ARM_LENGTH + periodicIO.currentExtensibleMeter + extensibleInput * Constants.ArmConstants.RANGE_OF_MOVEMENT;

            setSetpointAutoClosedLoop(rotatableDegreeSetpoint, extensibleMeterSetpoint);
        }

        public void setSetpointAutoClosedLoop(double shoulderAngleSetpoint, double extensibleMeterSetpoint){
            shoulderAngleSetpoint = validatePIDSetpoints(ValidationType.ROTATABLE, shoulderAngleSetpoint);
            extensibleMeterSetpoint = validatePIDSetpoints(ValidationType.EXTENSIBLE, extensibleMeterSetpoint);
            periodicIO.shoulderAngleSetpoint = shoulderAngleSetpoint / 360; // Will be add gear ratio's
            periodicIO.extensibleMeterSetpoint = Units.meter_to_centimeter(extensibleMeterSetpoint) / Constants.ArmConstants.ARM_DISTANCE_PER_REVOLUTION;
        }

        // Validation Of PID Setpoints for mechanical limits
        private double validatePIDSetpoints(ValidationType type, double desiredSetpoint){
            if(type == ValidationType.EXTENSIBLE){
                return validExtensibleSetpoint(desiredSetpoint);
            }
            return validRotatableSetpoint(desiredSetpoint);
        }

        private double validExtensibleSetpoint(double setpointValue){
            return clampValue(Constants.ArmConstants.EXTENSIBLE_UP_LIMIT, Constants.ArmConstants.EXTENSIBLE_DOWN_LIMIT, setpointValue);
        }
        
        private double validRotatableSetpoint(double setpointValue){
            return clampValue(Constants.ArmConstants.SHOULDER_UP_LIMIT, Constants.ArmConstants.SHOULDER_DOWN_LIMIT, setpointValue);
        }

        private double clampValue(double maxValue, double minValue, double desiredSetpoint){
           return Math.max(minValue, Math.min(desiredSetpoint, maxValue));
        }
        
        // Motor Controllers Configurations
        public IdleMode getExtensibleMotorMode(){
            return extensibleMotor.getIdleMode();
        }

        public IdleMode getRotatableMotorMode(){
            return rotatableMaster.getIdleMode();
        }
    
        public void restoreFactory(){
            extensibleMotor.restoreFactoryDefaults();
            rotatableMaster.restoreFactoryDefaults();
        }
    
        private void stopSystem(){
            extensibleMotor.stopMotor();
            rotatableMaster.stopMotor();
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
            rotatableMaster.setIdleMode(mode);
            extensibleMotor.setIdleMode(mode);
        }


    // PID controller validation methods
    public boolean rotatableAtSetpoint(){
        // Tolerance : in degrees
        return false;
    }

    public boolean extensibleAtSetpoint(){
        // Tolerance : in (m)
        return false;
    }
    
    // Getters
    public boolean isEncoderFaultExist(){
        return rotatableMaster.getFault(FaultID.kSensorFault) || extensibleMotor.getFault(FaultID.kSensorFault);
    }

    private void calculateAllMeasurement(){
        periodicIO.currentShoulderAngle = shoulderAngle();
        periodicIO.currentExtensibleMeter = extensibleDistance();
    }

    public double shoulderAngle(){
        return rotatableEncoder.getPosition() * Constants.ArmConstants.K_ARM_POSITION2DEGREE;
    }

    public double extensibleDistance(){
        return extensibleEncoder.getPosition() * Constants.ArmConstants.K_EXTENSIBLE_TICK2METER;
    }

    @Override
    public void stop() {
        stopSystem();
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
