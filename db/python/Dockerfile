FROM python:3.12-slim

RUN apt-get update && apt-get install -y 

RUN pip install mysql-connector-python firebase-admin

COPY . /app

WORKDIR /app

CMD ["python", "pruebas_fcm.py"]