package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.FaultID;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.lib.frc254.Subsystem;
import frc.robot.lib.frc254.util.SynchronousPIDF;

public class Arm extends Subsystem{

    private final CANSparkMax rotatableMaster;
    private final CANSparkMax rotatableSlave;
    private final CANSparkMax extensibleMotor;

    private RelativeEncoder extensibleEncoder;
    private RelativeEncoder rotatableEncoder;

    private SynchronousPIDF extensiblePidf;
    private SynchronousPIDF rotatablePidf;

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
        rotatableSlave = new CANSparkMax(Constants.ArmConstants.SLAVE_ROTATABLE_ID, MotorType.kBrushless);
        extensibleMotor = new CANSparkMax(Constants.ArmConstants.MASTER_EXTENSION_ID, MotorType.kBrushless);

        extensibleEncoder = extensibleMotor.getEncoder();
        rotatableEncoder = rotatableMaster.getEncoder();

        extensiblePidf = new SynchronousPIDF(Constants.ArmConstants.EXTENSIBLE_KP, Constants.ArmConstants.EXTENSIBLE_KI, Constants.ArmConstants.EXTENSIBLE_KD, Constants.ArmConstants.EXTENSIBLE_KF);
        rotatablePidf = new SynchronousPIDF(Constants.ArmConstants.SHOULDER_KP, Constants.ArmConstants.SHOULDER_KI, Constants.ArmConstants.SHOULDER_KD, Constants.ArmConstants.SHOULDER_KF);
        rotatableSlave.follow(rotatableMaster);
    }

    public static class PeriodicIO {
        double extensibleDemand;
        double rotatableDemand;
        double currentShoulderAngle;
        double currentExtensibleMeter;
        IdleMode neutralMode;
    }

    @Override
    public void writePeriodicOutputs() {
        rotatableMaster.set(periodicIO.rotatableDemand);
        extensibleMotor.set(periodicIO.extensibleDemand);
        calculateAllMeasurement();
        
        if(!isEncoderFaultExist() && currentProcessingType == ProcessingType.AUTO_CLOSED_LOOP || currentProcessingType == ProcessingType.BY_HAND){
            calculateClosedLoopDemands();
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

        public void calculateClosedLoopDemands(){
            periodicIO.rotatableDemand = rotatablePidf.calculate(periodicIO.currentShoulderAngle, Timer.getFPGATimestamp());
            periodicIO.extensibleDemand = extensiblePidf.calculate(periodicIO.currentExtensibleMeter, Timer.getFPGATimestamp());
        }

        // 3 main method for any usage case
        public void setMotor(double rotatableDemand, double extensibleDemand){
            periodicIO.rotatableDemand = rotatableDemand;
            periodicIO.extensibleDemand = extensibleDemand;
        }

        public void setSetpointByHandClosedLoop(double rotatableInput, double extensibleInput){
            double rotatableDegreeSetpoint = Constants.ArmConstants.k_ARM_ANGLE_OFFSET + periodicIO.currentShoulderAngle + rotatableInput * Constants.ArmConstants.ANGLE_OF_MOVEMENT;
            double extensibleMeterSetpoint = Constants.ArmConstants.DEFAULT_ARM_LENGTH + periodicIO.currentExtensibleMeter + extensibleInput * Constants.ArmConstants.RANGE_OF_MOVEMENT;

            setSetpointAutoClosedLoop(rotatableDegreeSetpoint, extensibleMeterSetpoint);
        }

        public void setSetpointAutoClosedLoop(double shoulderAngleSetpoint, double extensibleMeterSetpoint){
            shoulderAngleSetpoint = validatePIDSetpoints(ValidationType.ROTATABLE, shoulderAngleSetpoint);
            extensibleMeterSetpoint = validatePIDSetpoints(ValidationType.EXTENSIBLE, extensibleMeterSetpoint);

            rotatablePidf.setSetpoint(shoulderAngleSetpoint);
            extensiblePidf.setSetpoint(extensibleMeterSetpoint);
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
            rotatableSlave.restoreFactoryDefaults();
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
            rotatableSlave.setIdleMode(mode);
            extensibleMotor.setIdleMode(mode);
        }

    
    // Getters
    public double getRotatableEncoderPpr(){
        return rotatableEncoder.getCountsPerRevolution() / 4;
    }

    public double getExtensibleEncoderPpr(){
        return extensibleEncoder.getCountsPerRevolution() / 4;
    }

    public boolean isEncoderFaultExist(){
        return rotatableMaster.getFault(FaultID.kSensorFault) || extensibleMotor.getFault(FaultID.kSensorFault);
    }

    private void calculateAllMeasurement(){
        shoulderAngle();
        extensibleDistance();
    }

    public double shoulderAngle(){
        periodicIO.currentShoulderAngle = getRotatableEncoderPpr() * Constants.ArmConstants.K_ARMTICK2DEGREE;
        return periodicIO.currentShoulderAngle;
    }

    public double extensibleDistance(){
        periodicIO.currentExtensibleMeter = getExtensibleEncoderPpr() * Constants.ArmConstants.K_EXTENSIBLE_TICK2METER;
        return periodicIO.currentExtensibleMeter;
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
