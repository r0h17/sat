FROM node

RUN npm install -g http-server

COPY app /web
WORKDIR /web


EXPOSE 8000

#CMD [ "http-server" ]
CMD ["http-server", "-a", "0.0.0.0", "-p", "8000", "-c-1", "."]
