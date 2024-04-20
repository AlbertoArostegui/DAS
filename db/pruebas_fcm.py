import firebase_admin
from firebase_admin import credentials, messaging

cred = credentials.Certificate("C:\\Users\\alberto\\Downloads\\entrega-segunda-firebase-adminsdk-0ohhs-c7b7877573.json")
firebase_admin.initialize_app(cred)

registration_token = "flyNv2m9RDG2aM1tlchno_:APA91bEGsFtcjBicFBmfYH9wiRJgagzWtkauhNC7fO_BE0AMRThf-PNrLAzLESA8PoCbvzFNf-5V8i01xGtbD8yFwwZRHn_N9ChE-FcGgCMXCKSfZqLy13xCrXgEWxfJK7mDGMr7HhUK"

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