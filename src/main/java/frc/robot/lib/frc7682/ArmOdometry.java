package frc.robot.lib.frc7682;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Twist3d;

public class ArmOdometry {

    private Pose3d armPosition;
    private Twist3d twist3;
    private double height;

    public ArmOdometry(){
        armPosition = new Pose3d();
    }

    public void setExtraHeight(double newHeight){
        height = newHeight;
    }

    public void update(Pose2d currentRobotPose, double turretAngle, double shoulderAngle, double armLength){
        double armX = (currentRobotPose.getX() + Math.cos(turretAngle) * (Math.cos(shoulderAngle) * armLength));
        double armY = currentRobotPose.getY() + Math.sin(turretAngle) * (Math.cos(shoulderAngle) * armLength);
        double armZ = (Math.sin(shoulderAngle) * armLength) + height;
        armPosition = new Pose3d(armX, armY, armZ, new Rotation3d());
    }

    public Pose3d getEstimatedPosition(){
        return armPosition;
    }

    public void reset(){}
    
}
