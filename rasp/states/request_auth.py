from __future__ import annotations

import time

import state
import states.rfid_rejected as rej
import states.unlocked as unl

class RequestAuth(state.State):

    def update(self) -> None:
        if self.rasp.api.canRfidOpenBox(self.rasp.current_tag):
            self.rasp.change_state(unl.Unlocked())
        else:
            self.rasp.change_state(rej.RfidRejected())

