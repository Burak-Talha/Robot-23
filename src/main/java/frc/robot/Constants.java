package frc.robot;

import com.pathplanner.lib.PathConstraints;

import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.util.Units;

public class Constants {

    public static class AutoConstants{
        public static final double kRamseteB = 2;
        public static final double kRamseteZeta = 0.7;

        public static final double KP = 0;
        public static final double KI = 0;
        public static final double KD = 0;
        public static final double KF = 0;
        
        public static final double KS_VOLTS = 0.22;
        public static final double KV_VOLT_SECONDS_PER_METER = 1.98;
        public static final double KA_VOLT_SECONDS_SQUARED_PER_METER = 0.2;

        public static final PathConstraints DEF_PATH_CONSTRAINTS = new PathConstraints(5, 3.75);
    }

    public static class JoystickConstants{
        public static final int DRIVER_CONTROLLER_PORT = 0;
        public static final int OPERATOR_CONTROLLER_PORT = 1;

        public static final double DEADBAND = 0.2;
        public static final double SLEW_RATE_LIMIT_GAIN = 0.5;
    }

    public static class DriveConstants{
        public static final int MASTER_LEFT_ID = 2;
        public static final int SLAVE_LEFT_ID = 3;
        public static final int MASTER_RIGHT_ID = 4;
        public static final int SLAVE_RIGHT_ID = 5;

        public static final double BALANCE_KP = 0;
        public static final double BALANCE_KI = 0;
        public static final double BALANCE_KD = 0;
        public static final double BALANCE_KF = 0;

        public static final double LEFT_KP = 0;
        public static final double LEFT_KI = 0;
        public static final double LEFT_KD = 0;
        public static final double LEFT_KF = 0;

        public static final double RIGHT_KP = 0;
        public static final double RIGHT_KI = 0;
        public static final double RIGHT_KD = 0;
        public static final double RIGHT_KF = 0;

        public static final double WHEEL_M_RADIUS = Units.inchesToMeters(3.2);
        public static final double WHEEL_PERIMETER = 2 * Math.PI * WHEEL_M_RADIUS;
        public static final double REDUCTION_RATE = 10.72;
        public static final double KDRIVETICK2METER = WHEEL_PERIMETER / REDUCTION_RATE;

        public static final double MAX_WHEEL_RPM = 150;

        public static final DifferentialDriveKinematics DIFFERENTIAL_DRIVE_KINEMATICS = new DifferentialDriveKinematics(0.27);
    }

    public static class ArmConstants{

        // Shoulder
        public static final int MASTER_ROTATABLE_ID = 4;
        public static final double SHOULDER_UP_LIMIT = 0;
        public static final double SHOULDER_DOWN_LIMIT = 0;
        public static final double k_ARM_ANGLE_OFFSET = 30;
        public static final double SHOULDER_PPR = 48;
        public static final double BY_HAND_DEGREES_OFFSET = 0.001;
        public static final double ANGLE_OF_MOVEMENT = 240;
        // PID
            public static final double SHOULDER_KIZONE = 0;
            public static final double SHOULDER_KP = 0;
            public static final double SHOULDER_KI = 0;
            public static final double SHOULDER_KD = 0;
            public static final double SHOULDER_KF = 0;
            public static final double SHOULDER_MIN_OUTPUT = 0;
            public static final double SHOULDER_MAX_OUTPUT = 0;

        // Extensible
        public static final int MASTER_EXTENSION_ID = 6;
        public static final double MAX_ARM_LENGTH = 1.5;
        public static final double DEFAULT_ARM_LENGTH = 1.2;
        public static final double DEFAULT_HEIGHT = 0.5;
        public static final double ARM_DISTANCE_PER_REVOLUTION = 12.56;// 2.PI.r
        public static final double EXTENSIBLE_UP_LIMIT = 0;
        public static final double EXTENSIBLE_DOWN_LIMIT = 0;
        public static final double RANGE_OF_MOVEMENT = 0.4;
            // PID
            public static final double EXTENSIBLE_KIZONE = 0;
            public static final double EXTENSIBLE_MIN_OUTPUT = -1; 
            public static final double EXTENSIBLE_MAX_OUTPUT = 1;
            public static final double EXTENSIBLE_KP = 0;
            public static final double EXTENSIBLE_KI = 0;
            public static final double EXTENSIBLE_KD = 0;
            public static final double EXTENSIBLE_KF = 0;
        // Calculations
        public static final double K_ARM_POSITION2DEGREE = 360;
        public static final double K_ARM_HEIGHT_M = 0.40;
        public static final double K_EXTENSIBLE_TICK2METER = 0;
    }

    public static class TurretConstants{

        public static final int TURRET_MASTER_ID = 8;
        public static final int TURRET_SLAVE_ID = 9;

        public static final double TURRET_KP = 0;
        public static final double TURRET_KI = 0;
        public static final double TURRET_KD = 0;
        public static final double TURRET_KF = 0;
        public static final double TURRET_KIZONE = 0;
        public static final double TURRET_MIN_OUTPUT = 0;
        public static final double TURRET_MAX_OUTPUT = 0;

        public static final double TURRET_PERIMETER = 115;
        public static final double PROPULSION_PERIMETER = 9;
        public static final double TURRET_GEAR = TURRET_PERIMETER / PROPULSION_PERIMETER;

        public static final double K_TURRET_TICKS2DEGREE = 360;
    }

    public static class IntakeConstants{
        public static final int INTAKE_MASTER_ID = 7;
        public static final double INTAKE_RPM_RATE = 400 / 20;
        public static final double INTAKE_CONFIDANCE_DOWN_LIMIT = 0.5;

        public static final double INTAKE_KP = 0;
        public static final double INTAKE_KI = 0;
        public static final double INTAKE_KD = 0;
        public static final double INTAKE_KF = 0;
    }

    public static class VisionConstants{
        public static String CAM_NAME = "clementine_vision";
        public static final Transform3d CAMERA_TO_ROBOT = new Transform3d(new Translation3d(0.3, 0, 0.20), new Rotation3d());
    }
    
}
