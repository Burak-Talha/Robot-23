package frc.robot.lib.frc7682;

import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;
import frc.robot.lib.frc7682.TargetFinder.ObjectType;
import frc.robot.lib.frc7682.TargetFinder.TargetType;

public class Target {

    public Pose3d target;
    public ObjectType objectType;
    public TargetType targetType;
    DesiredPosition desiredPosition;
    public boolean isCompleted = false;

    public Target(Pose3d target, ObjectType objectType, TargetType targetType, DesiredPosition desiredPosition){
        this.target = target;
        this.objectType = objectType;
        this.targetType = targetType;
        this.desiredPosition = desiredPosition;
    }

    public Target(){}
}
