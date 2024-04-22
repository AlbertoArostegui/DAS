import firebase_admin
import mysql.connector
from firebase_admin import credentials, messaging
from time import sleep

sleep(2.5)

cred = credentials.Certificate("entrega-segunda-firebase-adminsdk-0ohhs-c7b7877573.json")
firebase_admin.initialize_app(cred)

mysql_conn = mysql.connector.connect(
    host="db",
    user="das_user",
    password="root",
    database="das"
)

cursor = mysql_conn.cursor()
cursor.execute("SELECT token FROM Usuario WHERE notificaciones = TRUE")
rows = cursor.fetchall()

# Recuperamos los tokens de todos los usuarios que han activado las notificaciones
tokens = []
for row in rows:
    token = row[0]
    print(token)
    tokens.append(token)

# Construct a message payload
message = messaging.MulticastMessage(
    data={
        "title": "TIEMPO",
        "body": "El tiempo en Bilbo es de 20ºC y soleado"
    },
    tokens=tokens,
)

# Send the message
response = messaging.send_multicast(message)

print('{0} messages were sent successfully'.format(response.success_count))
token = 'dvpSzXQOSm60YrGXHmpXzl:APA91bE74QYHULA5XfF6_MLvkSCnxTGp2fZmxLnX5N4VSfFUEC7zEG6gghE18H1Er2Cr-zvo5GPDteciuSv1981I-Vk9SLlU0EZQ5oNF-YBNVrbUuRLRCoNsOk9NXO5TNfm6axZCHJJp'
message = messaging.Message(
    data={
        "title": "TIEMPO",
        "body": "El tiempo en Bilbo es de 20ºC y soleado"
    },
    token=token,
)