from __future__ import annotations

import time

import state
import states.idle as idle
import states.unclosed as unclosed

class Open(state.State):

    TIME_TO_CLOSE = 10  # Time until LED starts blinking

    def on_state_enter(self) -> None:
        self.rasp.disable_led()

    def update(self) -> None:
        if self.rasp.is_open():
            if time.monotonic() - self.rasp.last_change >= self.TIME_TO_CLOSE:
                self.rasp.change_state(unclosed.Unclosed())
            else:
                time.sleep(0.01)
        else:
            self.rasp.change_state(idle.Idle())

