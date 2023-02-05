from __future__ import annotations

import rasp

if __name__ == '__main__':
    rasp = rasp.Rasp()

    try:
        while True:
            rasp.update()
    except KeyboardInterrupt:
        pass
    except Exception as e:
        print(e)
    finally:
        rasp.cleanup()

