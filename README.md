# ASEDelivery

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
| `/db-admin`              | -          | DB admin                           |
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
