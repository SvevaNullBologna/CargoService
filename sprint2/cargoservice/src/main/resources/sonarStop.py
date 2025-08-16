import RPi.GPIO as GPIO

try:
    print("Pulizia dei pin GPIO...")
    GPIO.cleanup()
    print("Pin GPIO puliti.")

except Exception as e:
    print(f"Errore durante la pulizia dei pin: {e}")