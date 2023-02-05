from __future__ import annotations

import time

import state
import states.idle as idle

class RfidRejected(state.State):

    def on_state_enter(self) -> None:
        self.rasp.enable_red_led()

    def update(self) -> None:
        time.sleep(5)
        self.rasp.change_state(idle.Idle())

