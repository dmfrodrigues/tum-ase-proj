# ASEDelivery

- **Project name:** ASEDelivery
- **Short description:** System to manage a [parcel locker](https://en.wikipedia.org/wiki/Parcel_locker#Packstation) service, based on [DHL Packstation](https://www.dhl.de/en/privatkunden/pakete-empfangen/an-einem-abholort-empfangen/packstation.html)
- **Environment:** Linux, Docker, Raspberry Pi
- **Tools:** Python, Java, Spring Boot, MongoDB, React
- **Institution:** [TUM](https://www.tum.de/en/)
- **Course:** [ASE](https://campus.tum.de/tumonline/ee/ui/ca2/app/desktop/#/slc.tm.cp/student/courses/950635036?$ctx=design=ca;lang=en&$scrollTo=toc_overview) (Advanced Topics of Software Engineering Cloud-Based Data Processing)
- **Project grade:** 1.0/1.0 (max grade)
- **Group:** 15
- **Group members:** 
    - [Diogo Miguel Ferreira Rodrigues](https://github.com/dmfrodrigues) (<dmfrodrigues2000@gmail.com>)
    - [João Lucas Silva Martins](https://github.com/joaolucasmartins)
    - [Tiago Duarte da Silva](https://github.com/tiagodusilva)
    - [Martin Möhle](https://github.com/MartinMoehle)

## Deploying

To deploy a development version of this project, clone this repository and then run
```bash
docker-compose up --build
```

The deployment will be available at http://localhost:8000.

| Path                     | Service    | Description                        |
|--------------------------|------------|------------------------------------|
| `/api/auth`              | `auth`     | Authentication service             |
| `/api`                   | `delivery` | Delivery service                   |
| `/swagger-ui/index.html` | `delivery` | Delivery service API documentation |
| `/`                      | `frontend` | React client                       |

Some credentials:

| Username  | Password | Role       |
|-----------|----------|------------|
| admin     | 1234     | Dispatcher |
| dmfr      | 1234     | Dispatcher |
| lucas     | 5678     | Deliverer  |
| martin    | 5678     | Deliverer  |
| mocho     | 9012     | Customer   |
| garching1 | 3456     | Box        |

## Production

The production environment can be accessed via http://138.246.237.201; the credentials are the same as in local deployment.
