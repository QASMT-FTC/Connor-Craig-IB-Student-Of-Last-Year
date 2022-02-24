/*
Copyright 2022 FIRST Tech Challenge Team FTC

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and
associated documentation files (the "Software"), to deal in the Software without restriction,
including without limitation the rights to use, copy, modify, merge, publish, distribute,
sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial
portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT
NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
*/
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.Blinker;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Gyroscope;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * This file contains an minimal example of a Linear "OpMode". An OpMode is a 'program' that runs in either
 * the autonomous or the teleop period of an FTC match. The names of OpModes appear on the menu
 * of the FTC Driver Station. When an selection is made from the menu, the corresponding OpMode
 * class is instantiated on the Robot Controller and executed.
 *
 * This particular OpMode just executes a basic Tank Drive Teleop for a PushBot
 * It includes all the skeletal structure that all linear OpModes contain.
 *
 * Remove a @Disabled the on the next line or two (if present) to add this opmode to the Driver Station OpMode list,
 * or add a @Disabled annotation to prevent this OpMode from being added to the Driver Station
 */
@Autonomous

public class TheNewAuto extends LinearOpMode {
    private Blinker connor_Craig_IB_Student_Of_Last_Year;
    private HardwareDevice duckEyes;
    private Servo servoLeft;
    private Servo servoRight;
    private DcMotor yeshwantArms;
    private Servo yeshwantFingers;
    private DcMotor yeshwantFlag;
    private DcMotor yeshwantSpin;
    private Blinker yeshwant_The_Duck_Snr;
    private DcMotor backLeft;
    private DcMotor backRight;
    private DcMotor extender;
    private DcMotor frontLeft;
    private DcMotor frontRight;
    private Gyroscope imu_1;
    private Gyroscope imu;


    @Override
    public void runOpMode() {
        connor_Craig_IB_Student_Of_Last_Year = hardwareMap.get(Blinker.class, "Connor Craig IB Student Of Last Year");
        duckEyes = hardwareMap.get(HardwareDevice.class, "DuckEyes");
        servoLeft = hardwareMap.get(Servo.class, "ServoLeft");
        servoRight = hardwareMap.get(Servo.class, "ServoRight");
        yeshwantArms = hardwareMap.get(DcMotor.class, "YeshwantArms");
        yeshwantFingers = hardwareMap.get(Servo.class, "YeshwantFingers");
        yeshwantFlag = hardwareMap.get(DcMotor.class, "YeshwantFlag");
        yeshwantSpin = hardwareMap.get(DcMotor.class, "YeshwantSpin");
        yeshwant_The_Duck_Snr = hardwareMap.get(Blinker.class, "Yeshwant The Duck Snr");
        backLeft = hardwareMap.get(DcMotor.class, "backLeft");
        backRight = hardwareMap.get(DcMotor.class, "backRight");
        extender = hardwareMap.get(DcMotor.class, "extender");
        frontLeft = hardwareMap.get(DcMotor.class, "frontLeft");
        frontRight = hardwareMap.get(DcMotor.class, "frontRight");
        imu_1 = hardwareMap.get(Gyroscope.class, "imu 1");
        imu = hardwareMap.get(Gyroscope.class, "imu");

        telemetry.addData("Status", "Initialized");
        telemetry.update();
        // Wait for the game to start (driver presses PLAY)
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            telemetry.addData("Status", "Running");
            telemetry.update();
            Move(300);
            Turn(90);
            Move(600);

        }
    }

    private void Move(int distance) {
        backRight.setTargetPosition(CalculateRevolutions(distance));
        backLeft.setTargetPosition(CalculateRevolutions(distance));
        frontRight.setTargetPosition(CalculateRevolutions(distance));
        frontLeft.setTargetPosition(CalculateRevolutions(distance));
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setPower(1);
        backLeft.setPower(1);
        frontRight.setPower(1);
        frontLeft.setPower(1);
        while (frontLeft.isBusy()) {
        }
        backRight.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        frontLeft.setPower(0);
    }

    /**
     * Describe this function...
     */
    private void Turn(double degrees) {
        // Clockwise is positive
        backRight.setTargetPosition(CalculateTurn(degrees));
        backLeft.setTargetPosition(CalculateTurn(degrees));
        frontRight.setTargetPosition(CalculateTurn(degrees));
        frontLeft.setTargetPosition(CalculateTurn(degrees));
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        frontLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backRight.setPower(0.6);
        backLeft.setPower(0.6);
        frontRight.setPower(0.6);
        frontLeft.setPower(0.6);
        while (frontLeft.isBusy()) {
        }
        backRight.setPower(0);
        backLeft.setPower(0);
        frontRight.setPower(0);
        frontLeft.setPower(0);
    }

    /**
     * This function calculates the amount of revolutions motor needs to move
     */
    private int CalculateRevolutionsARM(int distance) {
        // Set the pulses per rotation to calculate real
        // revolutions
        int PPR = 1660;
        // Set the diameter of the robot
        int diameter = 90;
        // Initialises variables to be calculated
        double circumference = Math.PI * diameter;
        double revolutions = distance / circumference;
        return (int) Math.round(revolutions * PPR);
    }

    /**
     * This function calculates the amount of revolutions motor needs to move
     */
    private int CalculateRevolutions(int distance) {
        // Set the pulses per rotation to calculate real
        // revolutions
        int PPR = 1660;
        // Set the body length of the robot
        int axleLength = 300;
        // Set the diameter of the robot
        int diameter = 400;
        // Initialises variables to be calculated
        double circumference = Math.PI * diameter;
        double revolutions = distance / circumference;
        return (int) Math.round(revolutions * PPR);
    }

    /**
     * This function calculates the amount of revolutions motor needs to move
     */
    private int CalculateTurn(double degrees) {
        double turnDegree;

        // Calculate turning distance relative to 360 degrees
        turnDegree = degrees / 360;
        // Set the pulses per rotation to calculate real
        // revolutions
        int PPR = 1660;
        // Set the turning axle length of the robot
        int axleLength = 300;
        // Set the diameter of the robot
        int diameter = axleLength;
        // Initialises variables to be calculated
        double circumference = Math.PI * diameter;
        double revolutions = turnDegree * circumference;
        return (int) Math.round(revolutions * PPR);
    }
}
