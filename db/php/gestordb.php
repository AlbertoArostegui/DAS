<?php
$DB_SERVER="db"; #la dirección del servidor
$DB_USER="das_user"; #el usuario para esa base de datos
$DB_PASS="root"; #la clave para ese usuario
$DB_DATABASE="das"; 
# Se establece la conexión:
if ($_SERVER['REQUEST_METHOD'] === 'GET') {

    $con = mysqli_connect($DB_SERVER, $DB_USER, $DB_PASS, $DB_DATABASE);
    #Comprobamos conexión
    if (mysqli_connect_errno()) {
        echo 'Error de conexion: ' . mysqli_connect_error();
        exit();
    }

    $sql = "SELECT * FROM Usuario";
    $res = $con->query($sql);

    if ($res->num_rows > 0) {
        $data = array();
        while($row = $res->fetch_assoc()) {
            $data = $row;
        }
        header('Content-Type: application/json');
        echo json_encode($data);
    } else {
        echo "0 results";
    }

    $con->close();
} else {
    http_response_code(405);
    echo "Método no permitido";
}

?>