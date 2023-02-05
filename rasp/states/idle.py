from __future__ import annotations

import state
import states.request_auth as req

class Idle(state.State):

    def on_state_enter(self) -> None:
        self.rasp.current_tag = None
        self.rasp.disable_led()

    def update(self) -> None:
        tag, _ = None, None
        while tag == None:
            _, tag = self.rasp.read_tag()
        self.rasp.current_tag = tag
        self.rasp.change_state(req.RequestAuth())

