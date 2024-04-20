<?php
function sendFCM($token, $title, $message) {
    $url = "https://fcm.googleapis.com/fcm/send";
    $msg = array(
        "to" => $token,
        "data" => array(
            "mensaje" => "Mi nuevo mensaje",
            "fecha" => date("d-m-Y")
        ),
        "notification" => array(
            "title" => $title,
            "body" => $message,
            "icon" => "ic_stat_ic_notification"
        )
    );
    $headers = array(
        "Authorization: key=g5F1L9RYZUcq7-QiNCQG4uTF-pc9vWw4O59ldEgxF-Y",
        "Content-Type: application/json"
    );
    $msgJson = json_encode($msg);
    $ch = curl_init();
    curl_setopt($ch, CURLOPT_URL, $url);
    curl_setopt($ch, CURLOPT_POST, true);
    curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
    curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
    curl_setopt($ch, CURLOPT_POSTFIELDS, $msgJson);
    $res = curl_exec($ch);
    if (curl_errno($ch)) { 
        print curl_error($ch); 
    } 
    error_log($res);
    curl_close($ch);
}

sleep(5);
while (true) {

    $token = "flyNv2m9RDG2aM1tlchno_:APA91bEGsFtcjBicFBmfYH9wiRJgagzWtkauhNC7fO_BE0AMRThf-PNrLAzLESA8PoCbvzFNf-5V8i01xGtbD8yFwwZRHn_N9ChE-FcGgCMXCKSfZqLy13xCrXgEWxfJK7mDGMr7HhUK";
    $title = "Notificación";
    $message = "Este es un mensaje de prueba";

    sendFCM($token, $title, $message);
    sleep(60);
}

?>