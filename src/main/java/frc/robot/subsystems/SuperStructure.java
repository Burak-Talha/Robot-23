package frc.robot.subsystems;

import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotState;
import frc.robot.lib.frc254.Subsystem;
import frc.robot.lib.frc7682.Target;
import frc.robot.lib.frc7682.TargetFinder;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;
import frc.robot.lib.frc7682.TargetFinder.ObjectType;
import frc.robot.lib.frc7682.TargetFinder.TargetType;

public class SuperStructure extends Subsystem{

    private RobotState robotState = RobotState.getInstance();
    private TargetFinder targetFinder = TargetFinder.getInstance();
    private Arm arm = Arm.getInstance();
    private Turret turret = Turret.getInstance();
    private Drive drive = Drive.getInstance();
    private Infrastructure infrastructure = Infrastructure.getInstance();

    public enum SystemState{
        GETTING,
        POSTING,
        TURTLING,
        BALANCING,
        MANUAL
    }

    private SystemState systemState = SystemState.MANUAL;

    private static SuperStructure superStructure = null;

    public static SuperStructure getInstance(){
        if(superStructure == null){
            superStructure = new SuperStructure();
        }
        return superStructure;
    }

    @Override
    public void writePeriodicOutputs() {
        // TODO Auto-generated method stub
        robotState.update(drive.getRotation2d(), drive.getLeftEncoderMeter(), drive.getRightEncoderMeter());
        targetFinder.update(robotState.armPositionPose3d());
    }

    private PeriodicIO periodicIO = new PeriodicIO();

    public class PeriodicIO{
        public double shoulderAngleSetpoint;
        public double turretAngleSetpoint;
        public double extensibleSetpoint;
        
        // Shows the remaining x,y,z axes to the target
        public double x;
        public double y;
        public double z;
    }

    // General PID Setpoint methods
    private void calculateAllSystemSetpoint(){
        calculateTurretSetpointForTarget();
        calculateArmSetpointForTarget();
    }
    
    // Calculations for the arm
    private void calculateArmSetpointForTarget(){
        double x = periodicIO.x;
        double z = periodicIO.z;

        periodicIO.shoulderAngleSetpoint = Math.toDegrees(Math.atan2(z, Math.abs(x)));
        periodicIO.extensibleSetpoint = Math.sqrt(Math.pow(z, 2) + Math.pow(x, 2));
    }

    private void calculateTurretSetpointForTarget(){
        periodicIO.turretAngleSetpoint = Math.atan2(periodicIO.y, periodicIO.x) - drive.getAngle();
    }

    // Base x, y, z calculator | Should work periodically
    private void calculateDistanceToTarget(DesiredPosition desiredPosition, ObjectType objectType){
        Target currentTarget = targetFinder.bestTarget(objectType, desiredPosition);
        Pose3d currentArmPosition = robotState.armPositionPose3d();
        
        periodicIO.x = currentTarget.target.getX() - currentArmPosition.getX();
        periodicIO.y = currentTarget.target.getY() - robotState.robotPositionPose2d().getY();
        periodicIO.z = currentTarget.target.getZ() - currentArmPosition.getZ();
    }

    public void setTargetFinderTargetType(TargetType targetType){
        targetFinder.changeTargetType(targetType);
    }

    // General information methods

    public void chooseMode(){
        if(DriverStation.getAlliance().name() == "Red" && robotState.robotPositionPose2d().getX() > 8.5){
            setTargetFinderTargetType(TargetType.POST);
        } else if(DriverStation.getAlliance().name() == "Blue" && robotState.robotPositionPose2d().getX() < 8.5){
            setTargetFinderTargetType(TargetType.POST);
        }else if(DriverStation.getAlliance().name() == "Red" && robotState.robotPositionPose2d().getX() < 8.5){
            setTargetFinderTargetType(TargetType.GET);
        } else if(DriverStation.getAlliance().name() == "Blue" && robotState.robotPositionPose2d().getX() > 8.5){
            setTargetFinderTargetType(TargetType.GET);
        }
    }

    public boolean isShoulderReady(){
        return arm.rotatableAtSetpoint();
    }

    public boolean isTurretReady(){
        return turret.turretAngleAtSetpoint();
    }

    public boolean isExtensibleReady(){
        return arm.extensibleAtSetpoint();
    }

    public double getTargetDistanceToArm(){
        return Math.sqrt(Math.pow(periodicIO.x, 2) + Math.pow(periodicIO.z, 2)) - arm.extensibleDistance();
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
