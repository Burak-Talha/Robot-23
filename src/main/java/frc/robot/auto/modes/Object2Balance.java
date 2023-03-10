// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.auto.modes;

import com.pathplanner.lib.commands.FollowPathWithEvents;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.auto.AutoUtil;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class Object2Balance extends SequentialCommandGroup {

  //FollowPathWithEvents followPathWithEvents = new FollowPathWithEvents("Object2Balance");
  

  /** Creates a new Object2Balance. */
  public Object2Balance() {
    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    //addCommands(AutoUtil.followTrajectoryCommand(followPathWithEvents.getTrajectory(), true));
  }

}
