import RPi.GPIO as GPIO
import time

# Impostazione della modalità dei pin e definizione dei pin
GPIO.setmode(GPIO.BCM)
TRIG = 16
ECHO = 18

print("Inizializzazione del sensore di distanza...")

try:
    # Configurazione dei pin
    GPIO.setup(TRIG, GPIO.OUT)
    GPIO.setup(ECHO, GPIO.IN)

    # Assicurati che il pin TRIG sia spento
    GPIO.output(TRIG, False)
    print("In attesa che il sensore si stabilizzi...")
    time.sleep(2)
    print("Sensore pronto per la rilevazione.")

except Exception as e:
    print(f"Errore durante l'inizializzazione: {e}")