<?php
$DB_SERVER="db"; #la dirección del servidor
$DB_USER="das_user"; #el usuario para esa base de datos
$DB_PASS="root"; #la clave para ese usuario
$DB_DATABASE="das"; 

function handleRegister() {
    global $DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE;
    $con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
    #Comprobamos conexión
    if (mysqli_connect_errno()) {
        echo 'Error de conexion: ' . mysqli_connect_error();
        exit();
    }

    $data = json_decode(file_get_contents('php://input'), true);
    $nombre = $data["nombre"];
    $username = $data["username"];
    $password = $data["password"];
    $token = $data["token"];
    $notificaciones = $data["notificaciones"];

    error_log("Nombre: " . $nombre);
    error_log("Username: " . $username);
    error_log("Password: " . $password);
    error_log("Token: " . $token);
    error_log("Notificaciones: " . $notificaciones);

    $sql = "INSERT INTO Usuario (nombre, username, password, notificaciones, token) VALUES ('$nombre', '$username', '$password', $notificaciones, '$token')";
    if ($con->query($sql) === TRUE) {
        $response = array("status" => "ok");
        header('Content-Type: application/json');
        echo json_encode($response);
    } else {
        $response = array("status" => "error", "message" => $con->error);
        header('Content-Type: application/json');
        echo json_encode($response);
    }

    $con->close();
}

function handleLogin() {
    global $DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE;
    $con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
    #Comprobamos conexión
    if (mysqli_connect_errno()) {
        echo 'Error de conexion: ' . mysqli_connect_error();
        exit();
    }

    $data = json_decode(file_get_contents('php://input'), true);
    $username = $data["username"];
    $password = $data["password"];

    error_log("Username: " . $username);
    error_log("Password: " . $password);
    
    $sql = "SELECT * FROM Usuario WHERE username='$username' AND password='$password'";
    $res = $con->query($sql);

    if ($res->num_rows > 0) {
        $response = array("status" => "ok");
        header('Content-Type: application/json');
        echo json_encode($response);
    } else {
        $response = array("status" => "error", "message" => "User or password are incorrect");
        header('Content-Type: application/json');
        echo json_encode($response);
    }

    $con->close();
}
function getName() {
    global $DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE;
    $con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
    #Comprobamos conexión
    if (mysqli_connect_errno()) {
        echo 'Error de conexion: ' . mysqli_connect_error();
        exit();
    }

    $data = json_decode(file_get_contents('php://input'), true);
    $username = $data["username"];
    $password = $data["password"];

    $sql = "SELECT nombre FROM Usuario WHERE username='$username'";
    $res = $con->query($sql);

    if ($res->num_rows > 0) {
        $nombre = $res->fetch_assoc()["nombre"];
        $response = array("status" => "ok", "nombre" => $nombre);
        header('Content-Type: application/json');
        echo json_encode($response);
    } else {
        $response = array("status" => "error", "message" => "Usuario o contraseña incorrectos");
        header('Content-Type: application/json');
        echo json_encode($response);
    }

    $con->close();   
}

?>