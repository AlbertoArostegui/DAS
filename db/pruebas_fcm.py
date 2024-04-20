import firebase_admin
from firebase_admin import credentials, messaging

cred = credentials.Certificate(".json")
firebase_admin.initialize_app(cred)

registration_token = ""

# Construct a message payload
message = messaging.Message(
    data={
        "title": "Hello",
        "body": "This is a Firebase Cloud Messaging test message!"
    },
    token=registration_token,
)

# Send the message
response = messaging.send(message)

print("Message successfully sent:", response)
