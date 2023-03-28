package frc.robot;

import java.util.Arrays;
import java.util.List;
import edu.wpi.first.apriltag.AprilTag;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.util.Units;
import frc.robot.lib.frc7682.Target;
import frc.robot.lib.frc7682.TargetFinder.DesiredPosition;
import frc.robot.lib.frc7682.TargetFinder.ObjectType;
import frc.robot.lib.frc7682.TargetFinder.TargetType;

public class FieldConstants {

        // FMwill constants
        public static final List<AprilTag> aprilTags =
        List.of(
          new AprilTag(1,
              new Pose3d(
              Units.inchesToMeters(610.77),
              Units.inchesToMeters(42.19),
              Units.inchesToMeters(18.22),
              new Rotation3d(0.0, 0.0, Math.PI))),
              
          new AprilTag(2,
            new Pose3d(
                Units.inchesToMeters(610.77),
                Units.inchesToMeters(108.19),
                Units.inchesToMeters(18.22),
                new Rotation3d(0.0, 0.0, Math.PI))),

          new AprilTag(3,
            new Pose3d(
                Units.inchesToMeters(610.77),
                Units.inchesToMeters(174.19), // FIRST's diagram has a typo (it says 147.19)
                Units.inchesToMeters(18.22),
                new Rotation3d(0.0, 0.0, Math.PI))),

          new AprilTag(4,
            new Pose3d(
                Units.inchesToMeters(636.96),
                Units.inchesToMeters(265.74),
                Units.inchesToMeters(27.38),
                new Rotation3d(0.0, 0.0, Math.PI))),

          new AprilTag(5,
            new Pose3d(
                Units.inchesToMeters(14.25),
                Units.inchesToMeters(265.74),
                Units.inchesToMeters(27.38),
                new Rotation3d())),

          new AprilTag(6,
            new Pose3d(
                Units.inchesToMeters(40.45),
                Units.inchesToMeters(174.19), // FIRST's diagram has a typo (it says 147.19)
                Units.inchesToMeters(18.22),
                new Rotation3d())),

          new AprilTag(7,
          new Pose3d(
              Units.inchesToMeters(40.45),
              Units.inchesToMeters(108.19),
              Units.inchesToMeters(18.22),
              new Rotation3d())),

          new AprilTag(8,
          new Pose3d(
              Units.inchesToMeters(40.45),
              Units.inchesToMeters(42.19),
              Units.inchesToMeters(18.22),
              new Rotation3d()))
      );

    public static final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = new AprilTagFieldLayout(aprilTags, 16.5, 8);

    public static class LoadingZone{
        
    public Rotation3d loadingZoneRotation = new Rotation3d(0, 0, 0);

    // Target rows are designed by scoring table opposite
        // Contains red alliance target positions
    public static final List<Target> BLUE_TARGETS = Arrays.asList(
                                                            // Cone targets
                                                            // 1st row
                                                            new Target(new Pose3d(new Translation3d(), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.448, 4.991, 1.164), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(), new Rotation3d(0.882, 4.991, -0.860)), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.448, 4.991, 1.164), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 3nd row
                                                            new Target(new Pose3d(new Translation3d(0.882, 3.873, 0.860), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.448, 3.874, 1.164), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(0.882, 3.873, 0.860), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.448, 3.874, 1.164), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 4th row
                                                            new Target(new Pose3d(new Translation3d(0.883, 3.314, 0.860), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.449, 3.315,1.164), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(0.883, 3.314, 0.860), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.449, 3.315,1.164), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 6th row
                                                            new Target(new Pose3d(new Translation3d(0.885, 2.196, 0.860), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.450, 2.197, 1.164), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(0.885, 2.196, 0.860), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.450, 2.197, 1.164), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 7th row
                                                            new Target(new Pose3d(new Translation3d(0.887, 1.636, 0.860), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.451, 1.638, 1.164), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(0.887, 1.636, 0.860), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.451, 1.638, 1.164), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 8th row
                                                            new Target(new Pose3d(new Translation3d(0.894, 0.512, 0.860), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.459, 0.517, 1.164), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(0.894, 0.512, 0.860), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.459, 0.517, 1.164), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),

                                                            // Cube Targets
                                                            // 2nd row
                                                            new Target(new Pose3d(new Translation3d(1.091, 4.647, 0.517), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.646, 4.647, 0.820), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(1.091, 4.647, 0.517), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.646, 4.647, 0.820), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 5th row
                                                            new Target(new Pose3d(new Translation3d(1.092, 2.970, 0.517), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.647, 2.970, 0.820), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(1.092, 2.970, 0.517), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.647, 2.970, 0.820), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 9th row
                                                            new Target(new Pose3d(new Translation3d(1.093, 1.293, 0.517), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.647, 1.294, 0.820), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(1.093, 1.293, 0.517), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(0.647, 1.294, 0.820), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),

                                                            new Target(aprilTags.get(5).pose.relativeTo(new Pose3d(0, 0.675, 0.238, new Rotation3d())), ObjectType.UNKNOWN, TargetType.GET, DesiredPosition.UP),
                                                            new Target(aprilTags.get(6).pose.relativeTo(new Pose3d(0, -0.675, 0.238, new Rotation3d())), ObjectType.UNKNOWN, TargetType.GET, DesiredPosition.UP)
                                                            );
    public static final List<Target> RED_TARGETS = Arrays.asList(
                                                            // Cone targets
                                                            // 1st row
                                                            new Target(new Pose3d(new Translation3d(15.803, 4.963, 0.866), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.235, 4.963, 1.170), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(15.803, 4.963, 0.866), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.235, 4.963, 1.170), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 3nd row
                                                            new Target(new Pose3d(new Translation3d(15.803, 3.845, 0.866), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.236, 3.844, 1.170), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(15.803, 3.845, 0.866), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.236, 3.844, 1.170), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 4th row
                                                            new Target(new Pose3d(new Translation3d(15.803, 3.285, 0.866), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(15.803, 3.285, 0.866), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 6th row
                                                            new Target(new Pose3d(new Translation3d(15.804, 2.166, 0.866), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.235, 2.166, 1.170), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(15.804, 2.166, 0.866), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.235, 2.166, 1.170), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 7th row
                                                            new Target(new Pose3d(new Translation3d(15.804, 1.607, 0.866), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.236, 1.607, 1.170), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(15.804, 1.607, 0.866), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.236, 1.607, 1.170), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 8th row
                                                            new Target(new Pose3d(new Translation3d(15.804, 0.488,0.866), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.236, 0.488,1.170), new Rotation3d()), ObjectType.CONE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(15.804, 0.488,0.866), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.236, 0.488,1.170), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),

                                                            // Cube Targets
                                                            // 2nd row
                                                            new Target(new Pose3d(new Translation3d(16.003, 4.629, 0.523), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.447, 4.629, 0.826), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(16.003, 4.629, 0.523), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.447, 4.629, 0.826), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 5th row
                                                            new Target(new Pose3d(new Translation3d(16.003, 2.952, 0.523), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.447, 2.952, 0.826), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(16.003, 2.952, 0.523), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.447, 2.952, 0.826), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),
                                                            // 9th row
                                                            new Target(new Pose3d(new Translation3d(16.003, 1.275, 0.523), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.447, 1.275, 0.826), new Rotation3d()), ObjectType.CUBE, TargetType.POST, DesiredPosition.UP),
                                                            new Target(new Pose3d(new Translation3d(16.003, 1.275, 0.523), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.MID),
                                                            new Target(new Pose3d(new Translation3d(16.447, 1.275, 0.826), new Rotation3d()), ObjectType.UNKNOWN, TargetType.POST, DesiredPosition.UP),

                                                            new Target(aprilTags.get(6).pose.relativeTo(new Pose3d(0, 0.675, 0.238, new Rotation3d())), ObjectType.UNKNOWN, TargetType.GET, DesiredPosition.UP),
                                                            new Target(aprilTags.get(6).pose.relativeTo(new Pose3d(0, -0.675, 0.238, new Rotation3d())), ObjectType.UNKNOWN, TargetType.GET, DesiredPosition.UP)
                                                            );
    }
}
