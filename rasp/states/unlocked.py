from __future__ import annotations

import time

import state
import states.idle as idle
import states.open as open

class Unlocked(state.State):

    # UNLOCK_PERIOD = 10 # number of seconds to stay unlocked

    def on_state_enter(self) -> None:
        self.rasp.enable_green_led()

    def update(self) -> None:
        if self.rasp.is_open():
            self.rasp.change_state(open.Open())
        # else:
        #     time.sleep(0.01)
        #     if time.monotonic() - self.rasp.last_change >= self.UNLOCK_PERIOD:
        #         self.rasp.change_state(idle.Idle())

