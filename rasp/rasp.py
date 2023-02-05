from __future__ import annotations

import time

from mfrc522 import SimpleMFRC522
import RPi.GPIO as GPIO

import state
import states.idle as idle
from raspconfig import RaspConf
from api import BoxApi

class Rasp:

    GREEN_PIN = 6
    RED_PIN = 5

    SENSOR_PIN = 26

    def __init__(self, configFile: str = ".conf") -> None:
        self.state = None
        self.last_change = None
        self.current_tag = None

        self.config = RaspConf.fromFile(configFile)
        self.api = BoxApi(self.config)

        # Hardware init
        GPIO.setmode(GPIO.BCM)
        GPIO.setwarnings(True)
        GPIO.setup(self.GREEN_PIN, GPIO.OUT, initial=GPIO.LOW)
        GPIO.setup(self.RED_PIN, GPIO.OUT, initial=GPIO.LOW)
        GPIO.setup(self.SENSOR_PIN, GPIO.IN, pull_up_down=GPIO.PUD_UP)

        self.reader = SimpleMFRC522()

        self.change_state(idle.Idle())

    # State methods
    def change_state(self, state: state.State) -> None:
        print(self.state.__class__.__name__, ' => ', state.__class__.__name__)
        self.state = state
        state.set_rasp(self)
        state.on_state_enter()
        self.last_change = time.monotonic()

    def update(self) -> None:
        self.state.update()


    # Hardware methods
    def is_open(self) -> bool:
        return GPIO.input(self.SENSOR_PIN)
    
    def enable_green_led(self) -> None:
        GPIO.output(self.GREEN_PIN, GPIO.HIGH)
        GPIO.output(self.RED_PIN, GPIO.LOW)

    def enable_red_led(self) -> None:
        GPIO.output(self.GREEN_PIN, GPIO.LOW)
        GPIO.output(self.RED_PIN, GPIO.HIGH)
    
    def enable_both_led(self) -> None:
        GPIO.output(self.GREEN_PIN, GPIO.HIGH)
        GPIO.output(self.RED_PIN, GPIO.HIGH)

    def disable_led(self) -> None:
        GPIO.output(self.GREEN_PIN, GPIO.LOW)
        GPIO.output(self.RED_PIN, GPIO.LOW)

    def read_tag(self) -> tuple[int, str]:
        return self.reader.read()

    def cleanup(self) -> None:
        GPIO.cleanup()
