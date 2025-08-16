import RPi.GPIO as GPIO
import time

LED_PIN = 12  # BCM 12 = pin fisico 32
GPIO.setmode(GPIO.BCM)
GPIO.setup(LED_PIN, GPIO.OUT)

# Accendi LED
GPIO.output(LED_PIN, GPIO.HIGH)
time.sleep(0.3)  # tempo acceso (mezzo secondo)
GPIO.output(LED_PIN, GPIO.LOW)
time.sleep(0.3)
GPIO.output(LED_PIN, GPIO.HIGH)
time.sleep(0.3)  # tempo acceso (mezzo secondo)
# Spegni LED
GPIO.output(LED_PIN, GPIO.LOW)

GPIO.cleanup()  # Libera i pin
