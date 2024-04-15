<?php
include 'funciones.php';

$request = $_SERVER['REQUEST_URI'];

switch ($request) {
    case '/register' :
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            handleRegister();
        } else {
            http_response_code(405);
            echo "Método no permitido";
        }
        break;
    case '/login' :
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            handleLogin();
        } else {
            http_response_code(405);
            echo "Método no permitido";
        }
        break;
    case '/getname' :
        if ($_SERVER['REQUEST_METHOD'] === 'POST') {
            getName();
        } else {
            http_response_code(405);
            echo "Método no permitido";
        }
        break;
    default:
        http_response_code(404);
        echo "Página no encontrada";
        break;
}
?>