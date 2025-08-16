import RPi.GPIO as GPIO


LED_PIN = 12
GPIO.setmode(GPIO.BCM)

GPIO.setup(LED_PIN, GPIO.OUT)

GPIO.output(LED_PIN, GPIO.HIGH)

GPIO.cleanup()
