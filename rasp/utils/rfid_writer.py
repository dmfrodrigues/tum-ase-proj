from mfrc522 import SimpleMFRC522
import RPi.GPIO as GPIO

import argparse

parser = argparse.ArgumentParser(prog="RFID Writer")
parser.add_argument('data', type=str)

if __name__ == "__main__":
    reader = SimpleMFRC522()
    args = parser.parse_args()
    try:
        rfid, text = reader.write(args.data)
        if text == args.data:
            print("Success")
        else:
            print("Failure")
    finally:
        GPIO.cleanup()
