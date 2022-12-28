FROM node:alpine

COPY ase-t15-frontend/package.json ./
COPY ase-t15-frontend/package-lock.json ./

RUN npm install
COPY ase-t15-frontend/ ./

CMD ["npm", "start"]
