// Team 20148 Micropachycephalosaurus
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "MainTeleOp", group = "")
public class MainTeleOp extends LinearOpMode {

    // This function is executed when this Op Mode is selected from the Driver Station.
    @Override
    public void runOpMode() {

        double cm1_target;
        double cm2_target;
        double cm3_target;
        double cm4_target;
        double turningPower;
        double powerLimiter = 0.45;

        int armPitchTarget = 0;
        double armPitchMax = 1000;
        int armExtenderTarget = 0;
        double armExtenderMax = 2000;
        int armPitchVelo = 100;
        int armExtenderVelo = 100;



        DcMotor cm1 = hardwareMap.dcMotor.get("chm1");
        DcMotor cm2 = hardwareMap.dcMotor.get("chm2");
        DcMotor cm3 = hardwareMap.dcMotor.get("chm3");
        DcMotor cm4 = hardwareMap.dcMotor.get("chm4");

        DcMotor intakeMotor = hardwareMap.dcMotor.get("intakeMotor");
        DcMotorEx armExtender = (DcMotorEx) hardwareMap.dcMotor.get("armExtender");
        DcMotorEx armPitch = (DcMotorEx) hardwareMap.dcMotor.get("armPitch");
        Servo pixelBayServo = hardwareMap.servo.get("pixelBayServo");

        cm1.setDirection(DcMotorSimple.Direction.REVERSE);
        cm2.setDirection(DcMotorSimple.Direction.FORWARD);
        cm3.setDirection(DcMotorSimple.Direction.REVERSE);
        cm4.setDirection(DcMotorSimple.Direction.REVERSE);
        armPitch.setDirection(DcMotorSimple.Direction.REVERSE);
        armExtender.setDirection(DcMotorSimple.Direction.REVERSE);

        armPitch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armExtender.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        armPitch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        armExtender.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        if (opModeIsActive()) {

            armPitch.setTargetPosition(0);
            armPitch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armExtender.setTargetPosition(0);
            armExtender.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            armPitch.setPower(0.85);
            armExtender.setPower(0.5);

            while (opModeIsActive()) {
                // Control Hub
                // Back left:cm1, back right:cm2
                // Front left:cm3, front right:cm4

                // Expansion Hub:
                // 0: armPitch
                // 1: intakeMotor
                // 2:
                // 3: armExtension

                // Servo (CH):
                // 0: pixelBayServo
                // 1: pixelBayCoverServo

                //turning script
                double rotate = 0;
                boolean rotating;
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
                }

                // straight script
                boolean straight = false;
                double straightPwr;

                if (gamepad1.right_trigger > 0) {
                    straight = true;
                    straightPwr = gamepad1.right_trigger;
                } else if (gamepad1.left_trigger > 0) {
                    straight = true;
                    straightPwr = -gamepad1.left_trigger;
                } else {
                    straightPwr = 0;
                }


                // Math for full rotational movement (anywhere with the joystick)
                if ((Math.abs(gamepad1.left_stick_x) + Math.abs(gamepad1.left_stick_y) > 0.15) || rotating || straight) {
                    if (gamepad1.left_stick_x >= 0 && -gamepad1.left_stick_y >= 0) {
                        // Quadrant I
                        cm1_target = Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (-straightPwr);
                        cm2_target = -Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (-straightPwr);
                        cm3_target = Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm4_target = -Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                    } else if (gamepad1.left_stick_x < 0 && -gamepad1.left_stick_y > 0) {
                        // Quadrant II
                        cm1_target = -Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (-straightPwr);
                        cm2_target = Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (-straightPwr);
                        cm3_target = -Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm4_target = Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                    } else if (gamepad1.left_stick_x <= 0 && -gamepad1.left_stick_y <= 0) {
                        // Quadrant III
                        cm1_target = -Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (-straightPwr);
                        cm2_target = Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (-straightPwr);
                        cm3_target = -Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm4_target = Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
                    } else if (gamepad1.left_stick_x > 0 && -gamepad1.left_stick_y < 0) {
                        // Quadrant IV
                        cm1_target = Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (-straightPwr);
                        cm2_target = -Math.pow(gamepad1.left_stick_x, 2) + Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (-straightPwr);
                        cm3_target = Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (-rotate) + (straightPwr);
                        cm4_target = -Math.pow(gamepad1.left_stick_x, 2) - Math.pow(gamepad1.left_stick_y, 2) + (rotate) + (straightPwr);
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


                // intake script
                if (gamepad2.right_trigger > 0) {
                    intakeMotor.setPower(gamepad2.right_trigger);
                } else if (gamepad2.left_trigger > 0) {
                    intakeMotor.setPower(-gamepad2.left_trigger);
                } else {
                    intakeMotor.setPower(0);
                }


                // arm pitch
                if (gamepad2.left_stick_y > 0 && armPitchTarget < armPitchMax) {
                        armPitchTarget += 15;
                        armPitch.setTargetPosition(armPitchTarget);
                } else if (gamepad2.left_stick_y < 0 && armPitchTarget > 0) {
                    armPitchTarget -= 10;
                    armPitch.setTargetPosition(armPitchTarget);
                }

                // arm extension
                if (gamepad2.right_bumper && armExtenderTarget < armExtenderMax) {
                    armExtenderTarget += 15;
                    armExtender.setTargetPosition(armExtenderTarget);
                } else if (gamepad2.left_bumper && armExtenderTarget > -100) {
                    armExtenderTarget -= 10;
                    armExtender.setTargetPosition(armExtenderTarget);
                }


                // Add telemetry data, so we can observe what is happening on the Driver app
//                telemetry.addData("cm1", cm1_target);
//                telemetry.addData("cm2", cm2_target);
//                telemetry.addData("cm3", cm3_target);
//                telemetry.addData("cm4", cm4_target);
//                telemetry.addData("rotating", rotating);
//                telemetry.addData("intake_power", intakeMotor.getPower());
                telemetry.addData("armPitchTarget", armPitchTarget);
                telemetry.addData("armPitchPosition", armPitch.getCurrentPosition());
                telemetry.addData("armPitchVelo", armPitch.getVelocity());
                telemetry.addData("armExtenderTarget", armExtenderTarget);
                telemetry.addData("armExtenderPosition", armExtender.getCurrentPosition());
                telemetry.addData("armPitchVelo", armPitch.getVelocity());
                telemetry.addData("frpower", cm4.getPower());
                telemetry.addData("flpower", cm3.getPower());
                telemetry.addData("brpower", cm2.getPower());
                telemetry.addData("blpower", cm1.getPower());
                telemetry.addData("rt", gamepad1.right_trigger);
                telemetry.update();
            }
        }
    }
}