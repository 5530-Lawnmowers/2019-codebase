/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import frc.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.sensors.PigeonIMU;

import edu.wpi.first.wpilibj.command.Subsystem;

/**
 * Add your docs here.
 */
public class Drivetrain extends Subsystem {

  public static WPI_TalonSRX frontRightTSRX  = new WPI_TalonSRX(RobotMap.FR);
  public static WPI_TalonSRX frontLeftTSRX  = new WPI_TalonSRX(RobotMap.FL);
  public static WPI_TalonSRX backRightTSRX  = new WPI_TalonSRX(RobotMap.BR);
  public static WPI_TalonSRX backLeftTSRX  = new WPI_TalonSRX(RobotMap.BL);

  public static PigeonIMU pigeon = new PigeonIMU(15);

  

  
	
	public void initDefaultCommand() {
		// setDefaultCommand(new MySpecialCommand());
	
	}
}
