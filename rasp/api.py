from __future__ import annotations

from requests import Session, Response
from requests_toolbelt.multipart.encoder import MultipartEncoder

from raspconfig import RaspConf


class BoxApi:
    def __init__(self, raspConf: RaspConf) -> None:
        print("Initializing API")
        self.conf = raspConf
        self.hostUrl = raspConf.api

        self._setup()
        print("Box successfully authenticated")

    def _setup(self):
        self.session = Session()
        self.session.auth = (self.conf.username, self.conf.password)
        # self.token = getCSRFToken()
        r = self._request("POST", "/auth")
        if not r.ok:
            raise Exception("Box failed to authenticate with server")

    def _request(self, method: str, url: str, headers:str=None, **kwargs) -> Response:
        if headers == None:
            headers = self._getBaseHeaders(kwargs)

        if method == 'GET':
            return self.session.get(self.hostUrl + url, headers=headers, **kwargs)
        elif method == 'POST':
            return self.session.post(self.hostUrl + url, headers=headers, **kwargs)
 
    def _getBaseHeaders(self, requestKwargs: dict):
        contentType = None
        if 'json' in requestKwargs:
            contentType = 'application/json'
        elif 'data' in requestKwargs:
            contentType = requestKwargs['data'].content_type

        headers = {
            # "X-XSRF-TOKEN": self.token
        }
        if contentType != None:
            headers['Content-Type'] = contentType

        return headers
    
    def canRfidOpenBox(self, rfid):
        mp_encoder = MultipartEncoder(
            fields={ 'token': str(rfid) }
        )
        
        r = self._request("POST", f"/box/{self.conf.id}/open", data=mp_encoder)

        if (not r.ok) and r.status_code != 403:
            raise Exception(f"Request failed: {r.status_code}")
        
        return r.text == 'true'
