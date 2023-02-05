from __future__ import annotations

import json

class RaspConf:

    def __init__(self, api: str, id: str, username: str, password: str, address: str) -> None:
        self.api = api
        self.id = id
        self.username = username
        self.password = password
        self.address = address


    @staticmethod
    def fromFile(filename: str = '.conf'):
        fp = open(filename, "r")
        conf = json.load(fp)
        fp.close()
        return RaspConf(conf['api'], conf['boxId'], conf['boxUsername'], conf['boxPassword'], conf['boxAddress'])
