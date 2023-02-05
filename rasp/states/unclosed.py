from __future__ import annotations

import time

import state
import states.idle as idle

class Unclosed(state.State):

    BLINK_TIME_INTERVAL = 0.33

    def __init__(self) -> None:
        super().__init__()
        self.last_blink = time.monotonic()
        self.led_enabled = False

    def update(self) -> None:
        if not self.rasp.is_open():
            self.rasp.change_state(idle.Idle())
        else:
            t = time.monotonic()
            if t - self.last_blink >= self.BLINK_TIME_INTERVAL:
                if self.led_enabled: self.rasp.disable_led()
                else: self.rasp.enable_red_led()

                self.last_blink = t
                self.led_enabled = not self.led_enabled
            time.sleep(0.01)

