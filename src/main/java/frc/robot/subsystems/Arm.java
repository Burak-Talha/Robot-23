package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkMaxPIDController;
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

    public enum ValidationType{
        EXTENSIBLE,
        ROTATABLE
    }

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
        // TODO Auto-generated method stub
        
        rotatableMaster.set(periodicIO.rotatableDemand);
        extensibleMotor.set(periodicIO.extensibleDemand);
        calculateAllMeasurement();
        calculateDemands();
    }

    @Override
    public void readPeriodicInputs() {
        // Example Usage
        SmartDashboard.putNumber("Arm Angle :", periodicIO.currentShoulderAngle);
        SmartDashboard.putNumber("Extensible Meter :", periodicIO.currentExtensibleMeter);
        SmartDashboard.putString("Motor Modes :", periodicIO.neutralMode.toString());
    }

        private void calculateDemands(){
            periodicIO.rotatableDemand = rotatablePidf.calculate(periodicIO.currentShoulderAngle, Timer.getFPGATimestamp());
            periodicIO.extensibleDemand = extensiblePidf.calculate(periodicIO.currentExtensibleMeter, Timer.getFPGATimestamp());
        }

        public void setSetpointPIDValues(double shoulderAngleSetpoint, double extensibleMeterSetpoint){
            shoulderAngleSetpoint = validatePIDSetpoints(ValidationType.ROTATABLE, shoulderAngleSetpoint);
            extensibleMeterSetpoint = validatePIDSetpoints(ValidationType.EXTENSIBLE, extensibleMeterSetpoint);

            rotatablePidf.setSetpoint(shoulderAngleSetpoint);
            extensiblePidf.setSetpoint(extensibleMeterSetpoint);
        }

        // Validation Of Setpoints

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

    public double getRotatableEncoderPpr(){
        return rotatableEncoder.getCountsPerRevolution() / 4;
    }

    public double getExtensibleEncoderPpr(){
        return extensibleEncoder.getCountsPerRevolution() / 4;
    }

    private void calculateAllMeasurement(){
        shoulderAngle();
        extensionDistance();
    }

    private void shoulderAngle(){
        periodicIO.currentShoulderAngle = getRotatableEncoderPpr() * Constants.ArmConstants.K_ARMTICK2DEGREE;
    }

    private void extensionDistance(){
        periodicIO.currentExtensibleMeter = getExtensibleEncoderPpr() * Constants.ArmConstants.K_EXTENSIBLE_TICK2METER;
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
