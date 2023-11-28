package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class MainTeleOp extends LinearOpMode {

    private DcMotor cm1;
    private DcMotor cm2;
    private DcMotor cm3;
    private DcMotor cm4;
    private DcMotor intakeMotor;
    private DcMotorEx armExtender;
    private DcMotorEx armPitch;
    private Servo pixelBayServo;

    public void move(int x, int y) {

    }

    // This function is executed when this Op Mode is selected from the Driver Station.
    public void runOpMode() {

        double cm1_target;
        double cm2_target;
        double cm3_target;
        double cm4_target;
        double turningPower;
        double powerLimiter = 0.60;


        cm1 = hardwareMap.dcMotor.get("chm1");
        cm2 = hardwareMap.dcMotor.get("chm2");
        cm3 = hardwareMap.dcMotor.get("chm3");
        cm4 = hardwareMap.dcMotor.get("chm4");


        cm1.setDirection(DcMotorSimple.Direction.REVERSE);
        cm2.setDirection(DcMotorSimple.Direction.FORWARD);
        cm3.setDirection(DcMotorSimple.Direction.FORWARD);
        cm4.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();
        if (opModeIsActive()) {

            while (opModeIsActive()) {
                // Back left:cm1, back right:cm2
                // Front left:cm3, front right:cm4

                //turning script
                double rotate = 0;
                boolean rotating = false;
                turningPower = 0.85;
                int slowDriveLimiter = 4;

                // If either our the bumpers are pressed, rotate the robot in that direction.
                if (gamepad1.right_bumper) {
                    rotating = true;
                    rotate = -turningPower;
                } else if (gamepad1.left_bumper) {
                    rotating = true;
                    rotate = turningPower;
                } else {
                    rotating = false;
                    turningPower = 0;
                }

                // straight script
                boolean straight = false;
                double straightPwr = 0;

                if (gamepad1.right_trigger > 0) {
                    straight = true;
                    straightPwr = gamepad1.right_trigger;
                } else if (gamepad1.left_trigger > 0) {
                    straight = true;
                    straightPwr = -gamepad1.left_trigger;
                } else {
                    straight = false;
                    straightPwr = 0;
                }


                // Math for full rotational movement (anywhere with the joystick)
                if ((Math.abs(gamepad1.left_stick_x) + Math.abs(gamepad1.left_stick_y) > 0.15) || rotating || straight) {
                    if (gamepad1.left_stick_x >= 0 && -gamepad1.left_stick_y >= 0) {
                        // Quadrant I
                        cm1_target = Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                        cm2_target = -Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm3_target = Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm4_target = -Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                    } else if (gamepad1.right_stick_x >= 0 && -gamepad1.right_stick_y >= 0) {
                        // Quadrant I
                        cm1_target = (Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr)) / slowDriveLimiter;
                        cm2_target = (-Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr)) / slowDriveLimiter;
                        cm3_target = (Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr)) /slowDriveLimiter;
                        cm4_target = (-Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr))/slowDriveLimiter;
                    } else if (gamepad1.left_stick_x < 0 && -gamepad1.left_stick_y > 0) {
                        // Quadrant II
                        cm1_target = -Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                        cm2_target = Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm3_target = -Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm4_target = Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                    } else if (gamepad1.right_stick_x < 0 && -gamepad1.right_stick_y > 0) {
                        // Quadrant II slow speed
                        cm1_target = (-Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr)) / slowDriveLimiter;
                        cm2_target = (Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr)) / slowDriveLimiter;
                        cm3_target = (-Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr)) / slowDriveLimiter;
                        cm4_target = (Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr)) / slowDriveLimiter;
                    } else if (gamepad1.left_stick_x <= 0 && -gamepad1.left_stick_y <= 0) {
                        // Quadrant III
                        cm1_target = -Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                        cm2_target = Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm3_target = -Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm4_target = Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                    } else if (gamepad1.right_stick_x <= 0 && -gamepad1.right_stick_y <= 0) {
                        // Quadrant III slow speed
                        cm1_target = (-Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr))/ slowDriveLimiter;
                        cm2_target = (Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr)) / slowDriveLimiter;
                        cm3_target = (-Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr)) / slowDriveLimiter;
                        cm4_target = (Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr)) / slowDriveLimiter;
                    } else if (gamepad1.left_stick_x > 0 && -gamepad1.left_stick_y < 0) {
                        // Quadrant IV
                        cm1_target = Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                        cm2_target = -Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm3_target = Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm4_target = -Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                    } else if (gamepad1.right_stick_x > 0 && -gamepad1.right_stick_y < 0) {
                        // Quadrant IV slower speed
                        cm1_target = (Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr)) / slowDriveLimiter;
                        cm2_target = (-Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr)) / slowDriveLimiter;
                        cm3_target = (Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr)) / slowDriveLimiter;
                        cm4_target = (-Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr)) / slowDriveLimiter;
                    } else {
                        cm1_target = rotate + straightPwr;
                        cm2_target = -rotate + straightPwr;
                        cm3_target = -rotate + straightPwr;
                        cm4_target = rotate + straightPwr;
                    }
                } else {
                    // Reset our motors
                    cm1_target = 0;
                    cm2_target = 0;
                    cm3_target = 0;
                    cm4_target = 0;
                }

                // Set the power for our motors (and apply power limiters)
                cm1.setPower(cm1_target * powerLimiter);
                cm2.setPower(cm2_target * powerLimiter);
                cm3.setPower(cm3_target * powerLimiter);
                cm4.setPower(cm4_target * powerLimiter);

                // Add telemetry data, so we can observe what is happening on the Driver app
                telemetry.addData("cm1", cm1_target);
                telemetry.addData("cm2", cm2_target);
                telemetry.addData("cm3", cm3_target);
                telemetry.addData("cm4", cm4_target);
                telemetry.addData("rotating", rotating);
                telemetry.update();
            }
        }
    }
}