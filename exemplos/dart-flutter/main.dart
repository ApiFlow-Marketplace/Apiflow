import 'package:http/http.dart' as http;
import 'dart:convert';

void main() async {
  final bearerToken = await obterBearerToken();

  if (bearerToken == null) {
    print('Não foi possível obter o Bearer Token. Encerrando o programa.');
    return;
  }

  // Exemplo: API de testes do ApiFlow
  const apiUrl =
      'https://gtw.apiflow.com.br/gtw-8e0cdb967e6645ff955a20412faf5885/hi?name=SEU_NOME';

  try {
    final response = await http.post(
      Uri.parse(apiUrl),
      headers: {
        'Authorization': 'Bearer $bearerToken',
        'Content-Type': 'application/json',
      },
    );

    if (response.statusCode >= 200 && response.statusCode < 300) {
      print('Resposta da API:');
      print(response.body);
    } else {
      print('Erro: ${response.statusCode}');
      print(response.body);
    }
  } catch (ex) {
    print('Erro ao chamar a API: $ex');
  }
}

Future<String?> obterBearerToken() async {
  // Credenciais do APIFlow
  // Obtenha em: https://www.apiflow.com.br/docs/content/step-by-step/Create-Credentials

  // ********* ESTE CÓDIGO É APENAS UM EXEMPLO. MANTENHA SUAS CREDENCIAIS SEGURAS E NÃO AS EXPONHA EM CÓDIGO-FONTE **********
  const personId = 'SEU_PERSON_ID_AQUI';
  const clientId = 'SEU_CLIENT_ID_AQUI';
  const clientSecret = 'SEU_CLIENT_SECRET_AQUI';

  try {
    const authUrl =
        'https://router.apiflow.com.br/customer/api/Auth/Client';

    // Criar payload JSON
    final payloadAuth = {
      'client_id': clientId,
      'client_secret': clientSecret,
      'person_id': personId,
    };

    // Fazer requisição POST
    final response = await http.post(
      Uri.parse(authUrl),
      headers: {'Content-Type': 'application/json'},
      body: jsonEncode(payloadAuth),
    );

    if (response.statusCode < 200 || response.statusCode >= 300) {
      print('Erro ao obter o token: ${response.statusCode}');
      print(response.body);
      return null;
    } else {
      print('Token obtido com sucesso!');

      // Fazer parse do JSON
      final authContent = jsonDecode(response.body) as Map<String, dynamic>;
      final bearerToken = authContent['token'] as String;

      return bearerToken;
    }
  } catch (ex) {
    print('Erro ao obter o token: $ex');
    return null;
  }
}