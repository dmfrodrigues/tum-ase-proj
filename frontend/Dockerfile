FROM node:alpine AS frontend

COPY ase-t15-frontend/package.json ./
COPY ase-t15-frontend/package-lock.json ./

RUN npm install -g serve

RUN npm install
COPY ase-t15-frontend/ ./

CMD ["npm", "start"]

FROM frontend as frontend-prod
RUN npm run build
CMD ["serve", "-s", "build"]
