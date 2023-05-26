package com.example.exchanger;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.*;
import java.util.*;
import java.text.SimpleDateFormat;

public class Request {
    private String authKey;
    private String searchDate;
    private String dataType;

    public JSONArray json;
    public HttpsURLConnection connection;

    Request() throws IOException {
        authKey = "nIZ1d1tQZvRXyLAUxwPN793pAspRInNF";
        searchDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
        dataType = "AP01";
        json = new JSONArray();

        URL url = new URL("https://www.koreaexim.go.kr/site/program/financial/exchangeJSON?authkey="
                + authKey + "&searchdate=" + searchDate + "&data=" + dataType);
        connection = (HttpsURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setConnectTimeout(5000);
        connection.setReadTimeout(5000);
    }

    private String getTime() {
        Date requested_time = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd  HH:mm:ss");
        return format.format(requested_time);
    }

    public void getJSON() throws IOException {
        JSONArray array_temp = new JSONArray();

        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            //successful connection
            try (BufferedReader bufferedReader
                         = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                JSONParser parser = new JSONParser();
                try {
                    while ((line = bufferedReader.readLine()) != null) {
                        array_temp = (JSONArray) parser.parse(line);
                    }
                } catch (ParseException parseException) {
                    System.out.println("Parsing Failed");
                }
                for (Object o : array_temp) {
                    json.add(o);
                }
            }
        }
        if (!array_temp.isEmpty()) {
            SaveData(array_temp);
        }
        CallLatestData();
    }

    public void SaveData(JSONArray array_temp) {
        try (FileWriter file = new FileWriter("data.json")) {
            file.write(array_temp.toJSONString());
            file.flush();
            file.close();
        } catch (IOException ioException) {
            System.out.println("Data Save Failed");
        }
    }

    public void CallLatestData() {
        try (FileReader reader = new FileReader("data.json")) {
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(reader);
            json = (JSONArray) obj;
            reader.close();
        } catch (IOException ioException) {
            System.out.println("Data Calling Failed");
        } catch (ParseException parseException) {
            System.out.println("Parsing Saved Data Failed");
        }
    }

    public static String getExchangeRate(String s) throws IOException {
        Request request = new Request();
        request.getJSON();

        if (s.equals("JPY") || s.equals("IDR")) {
            s += "(100)";
        }
        for (Object o : request.json) {
            JSONObject jsobj = (JSONObject) o;
            if (jsobj.get("cur_unit") == null) {
                continue;
            } else if (jsobj.get("cur_unit").equals(s)) {
                return jsobj.get("deal_bas_r").toString();
            }
        }
        return null;
    }
}

