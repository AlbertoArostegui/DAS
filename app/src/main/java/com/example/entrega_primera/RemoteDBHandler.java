package com.example.entrega_primera;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Constraints;
import androidx.work.Data;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RemoteDBHandler extends Worker {
    private String dir = "http://192.168.1.10:8080/";
    public RemoteDBHandler(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    //Dispatcher. Aqui llegan peticiones asincronas con un "tag" y segun sea el tag se hace una cosa u otra
    public Result doWork() {

        String tag = getInputData().getString("tag");
        if (tag != null) {
            if (tag.equals("register")) {
                String username = getInputData().getString("username");
                String password = getInputData().getString("password");
                String nombre = getInputData().getString("nombre");
                String token = getInputData().getString("token");
                Integer notificaciones = getInputData().getInt("notificaciones", 0);
                return registerUser(username, password, nombre, token, notificaciones);
            } else if (tag.equals("login")) {
                String username = getInputData().getString("username");
                String password = getInputData().getString("password");
                return login(username, password);
            } else if (tag.equals("getname")) {
                String username = getInputData().getString("username");
                return getName(username);
            }
        }
        return Result.failure();
    }

    private Result registerUser(String username, String password, String nombre, String token, Integer notificaciones) {

        dir += "register";
        HttpURLConnection urlConn = null;
        System.out.println("Connecting to " + dir + " to register user...");

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        json.put("nombre", nombre);
        json.put("token", token);
        json.put("notificaciones", notificaciones);

        try {
            urlConn = (HttpURLConnection) new URL(dir).openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");

            OutputStream out = urlConn.getOutputStream();
            out.write(json.toJSONString().getBytes());
            out.flush();
            out.close();

            int status = urlConn.getResponseCode();
            String msg = urlConn.getResponseMessage();
            System.out.println("Status: " + status);
            System.out.println("Message: " + msg);
            if (status == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, res = "";
                while ((line = bufferedReader.readLine()) != null) {
                    res += line;
                }
                inputStream.close();

                JSONParser parser = new JSONParser();
                JSONObject jsonResp = (JSONObject) parser.parse(res);

                if (jsonResp.get("status").equals("ok")) {
                    return Result.success();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return Result.failure();
    }

    private Result login(String username, String password) {

        dir += "login";
        HttpURLConnection urlConn = null;
        System.out.println("Connecting to " + dir + " to log in...");

        JSONObject json = new JSONObject();
        json.put("username", username);
        json.put("password", password);
        try {
            urlConn = (HttpURLConnection) new URL(dir).openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");
            System.out.println(json.toJSONString());

            OutputStream out = urlConn.getOutputStream();
            out.write(json.toJSONString().getBytes());
            out.flush();
            out.close();

            int statusCode = urlConn.getResponseCode();
            String msg = urlConn.getResponseMessage();
            System.out.println("Status: " + statusCode);
            System.out.println("Message: " + msg);
            if (statusCode == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, res = "";
                while ((line = bufferedReader.readLine()) != null) {
                    res += line;
                }
                inputStream.close();

                JSONParser parser = new JSONParser();
                System.out.println("res: " + res);
                JSONObject jsonResp = (JSONObject) parser.parse(res);
                System.out.println("jsonResp: " + jsonResp);

                String status = (String) jsonResp.get("status");
                System.out.println("status: " + status);

                if (status.equals("ok")) {
                    System.out.println("Status is ok: " + status);
                    Data datos = new Data.Builder()
                            .putString("status", status)
                            .build();
                    return Result.success(datos);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            Data datos = new Data.Builder()
                    .putString("status", "error")
                    .build();
            return Result.failure(datos);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Data datos = new Data.Builder()
                .putString("status", "error")
                .build();
        return Result.failure(datos);
    }

    private Result getName(String username) {

        dir += "getname";
        HttpURLConnection urlConn = null;
        System.out.println("Connecting to " + dir + " to get a name...");

        JSONObject json = new JSONObject();
        json.put("username", username);

        try {
            urlConn = (HttpURLConnection) new URL(dir).openConnection();
            urlConn.setRequestMethod("POST");
            urlConn.setDoOutput(true);
            urlConn.setRequestProperty("Content-Type", "application/json");
            urlConn.setRequestProperty("Accept", "application/json");

            OutputStream out = urlConn.getOutputStream();
            out.write(json.toJSONString().getBytes());
            out.flush();
            out.close();

            int status = urlConn.getResponseCode();
            String msg = urlConn.getResponseMessage();
            System.out.println("Status: " + status);
            System.out.println("Message: " + msg);
            if (status == 200) {
                BufferedInputStream inputStream = new BufferedInputStream(urlConn.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
                String line, res = "";
                while ((line = bufferedReader.readLine()) != null) {
                    res += line;
                }
                inputStream.close();

                JSONParser parser = new JSONParser();
                JSONObject jsonResp = (JSONObject) parser.parse(res);

                if (jsonResp.get("status").equals("ok")) {
                    Data datos = new Data.Builder()
                            .putString("nombre", (String) jsonResp.get("nombre"))
                            .build();
                    return Result.success(datos);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Result.failure();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return Result.failure();
    }
}
