from __future__ import annotations

import time

import state
import states.rfid_rejected as rej
import states.unlocked as unl

class RequestAuth(state.State):

    def update(self) -> None:
        time.sleep(2)
        if self.rasp.current_tag == 594722564577: # TODO: Implement actual request
            self.rasp.change_state(unl.Unlocked())
        else:
            self.rasp.change_state(rej.RfidRejected())


