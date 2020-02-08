FROM node:alpine

WORKDIR /usr/src/app

# install dependencies
COPY package*.json ./
RUN npm install

# copy app source code
COPY . .

CMD ["npm", "start"]
EXPOSE 3000

