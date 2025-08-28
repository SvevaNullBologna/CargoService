# -------------------------------------------------------------
# ledPython25.py
# Key-point: we can manage a Led connected on the GPIO pin 25
# using the python language.
#	sudo python ledPython25.py
# -------------------------------------------------------------
import RPi.GPIO as GPIO 
import time

'''
----------------------------------
CONFIGURATION
----------------------------------
'''
GPIO.setmode(GPIO.BCM)
GPIO.setup(12,GPIO.OUT)

'''
----------------------------------
main activity
----------------------------------
'''
#while True:
for i in range(1, 4):
    GPIO.output(12,GPIO.HIGH)
    time.sleep(0.15)
    GPIO.output(12,GPIO.LOW)
    time.sleep(0.15)
    