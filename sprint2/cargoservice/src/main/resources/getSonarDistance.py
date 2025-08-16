import RPi.GPIO as GPIO
import time

# Impostazione della modalità dei pin e definizione dei pin
GPIO.setmode(GPIO.BCM)
TRIG = 16
ECHO = 18

print("Sensore di distanza in funzione...")

# Configura il pin TRIG come OUTPUT per inviare segnali.
GPIO.setup(TRIG, GPIO.OUT)
# Configura il pin ECHO come INPUT per ricevere segnali.
GPIO.setup(ECHO, GPIO.IN)

try:
    # Assicurati che il pin TRIG sia spento inizialmente
    GPIO.output(TRIG, False)
    print("In attesa che il sensore si stabilizzi...")
    time.sleep(2)
    print("Sensore pronto per la rilevazione.")

    while True:
        # Invia un impulso di 10 microsecondi
        GPIO.output(TRIG, True)
        time.sleep(0.00001)
        GPIO.output(TRIG, False)

        pulse_start = time.time()
        pulse_end = time.time()

        # Leggi l'inizio del segnale di ritorno
        while GPIO.input(ECHO) == 0:
            pulse_start = time.time()

        # Leggi la fine del segnale di ritorno
        while GPIO.input(ECHO) == 1:
            pulse_end = time.time()

        # Calcola la durata e la distanza
        pulse_duration = pulse_end - pulse_start
        distance = pulse_duration * 17150
        distance = round(distance, 2)

        print(f"Distanza: {distance} cm")
        time.sleep(1) # Attendi 1 secondo prima della prossima misurazione

finally:
    GPIO.cleanup()
    print("Pulizia dei pin GPIO completata.")