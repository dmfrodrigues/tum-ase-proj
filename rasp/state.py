from __future__ import annotations

import rasp

class State:

    def set_rasp(self, rasp: rasp.Rasp) -> None:
        self.rasp = rasp

    def on_state_enter(self) -> None:
        pass

    def update(self) -> None:
        pass

