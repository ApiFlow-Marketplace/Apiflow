package com.apiflow;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class Main {
    public static void main(String[] args) throws Exception {
        String bearerToken = obterBearerToken();
        
        if (bearerToken == null) {
            System.out.println("Não foi possível obter o Bearer Token. Encerrando o programa.");
            return;
        }

        // Exemplo: API de testes do ApiFlow
        String apiUrl = "https://gtw.apiflow.com.br/gtw-8e0cdb967e6645ff955a20412faf5885/hi?name=SEU_NOME";
        
        try {
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            
            // Configurar o header Authorization com Bearer Token
            connection.setRequestProperty("Authorization", "Bearer " + bearerToken);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            
            int statusCode = connection.getResponseCode();
            
            if (statusCode >= 200 && statusCode < 300) {
                String content = readResponse(connection);
                System.out.println("Resposta da API:");
                System.out.println(content);
            } else {
                System.out.println("Erro: " + statusCode);
                String errorContent = readErrorResponse(connection);
                System.out.println(errorContent);
            }
            
            connection.disconnect();
        } catch (Exception ex) {
            System.out.println("Erro ao chamar a API: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    public static String obterBearerToken() {
        // Credenciais do APIFlow
        // Obtenha em: https://www.apiflow.com.br/docs/content/step-by-step/Create-Credentials

        // ********* ESTE CÓDIGO É APENAS UM EXEMPLO. MANTENHA SUAS CREDENCIAIS SEGURAS E NÃO AS EXPONHA EM CÓDIGO-FONTE **********
        String person_id = "SEU_PERSON_ID_AQUI";
        String client_id = "SEU_CLIENT_ID_AQUI";
        String client_secret = "SEU_CLIENT_SECRET_AQUI";

        try {
            String authUrl = "https://router.apiflow.com.br/customer/api/Auth/Client";

            // Criar payload JSON
            JsonObject payloadAuth = new JsonObject();
            payloadAuth.addProperty("client_id", client_id);
            payloadAuth.addProperty("client_secret", client_secret);
            payloadAuth.addProperty("person_id", person_id);

            String jsonPayload = payloadAuth.toString();

            // Fazer requisição POST
            URL url = new URL(authUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setDoOutput(true);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);

            // Enviar payload
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input = jsonPayload.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);
            }

            int statusCode = connection.getResponseCode();

            if (statusCode < 200 || statusCode >= 300) {
                System.out.println("Erro ao obter o token: " + statusCode);
                String errorContent = readErrorResponse(connection);
                System.out.println(errorContent);
                return null;
            } else {
                System.out.println("Token obtido com sucesso!");
                String responseContent = readResponse(connection);

                // Fazer parse do JSON
                Gson gson = new Gson();
                JsonObject authContent = gson.fromJson(responseContent, JsonObject.class);
                String bearerToken = authContent.get("token").getAsString();

                connection.disconnect();
                return bearerToken;
            }
        } catch (Exception ex) {
            System.out.println("Erro ao obter o token: " + ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    private static String readResponse(HttpURLConnection connection) throws Exception {
        try (Scanner scanner = new Scanner(connection.getInputStream(), StandardCharsets.UTF_8)) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    private static String readErrorResponse(HttpURLConnection connection) throws Exception {
        try (Scanner scanner = new Scanner(connection.getErrorStream(), StandardCharsets.UTF_8)) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        }
    }
}
