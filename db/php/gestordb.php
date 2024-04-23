<?php
include 'funciones.php';

$request = $_SERVER['REQUEST_URI'];
error_log("Request: " . $request);

switch ($request) {
    case '/register' :
        error_log("Register called");
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            handleRegister();
        } else {
            http_response_code(405);
            echo "Método no permitido";
        }
        break;
    case '/login' :
        error_log("Login called");
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            handleLogin();
        } else {
            http_response_code(405);
            echo "Método no permitido";
        }
        break;
    case '/getnameandphoto' :
        error_log("getnameandphoto called");
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            getName();
        } else {
            http_response_code(405);
            echo "Método no permitido";
        }
        break;
    case '/uploadimage':
        error_log("uploadimage called");
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            saveImage();
        } else {
            http_response_code(405);
            echo "Método no permitido";
        }
    default:
        error_log("404 not found" . $request);
        http_response_code(404);
        echo "Página no encontrada";
        break;
}
?>