async function main() {
  const bearerToken = await obterBearerToken();

  if (bearerToken === null) {
    console.log(
      'Não foi possível obter o Bearer Token. Encerrando o programa.'
    );
    return;
  }

  // Exemplo: API de testes do ApiFlow
  const apiUrl =
    'https://gtw.apiflow.com.br/gtw-8e0cdb967e6645ff955a20412faf5885/hi?name=SEU_NOME';

  try {
    const response = await fetch(apiUrl, {
      method: 'POST',
      headers: {
        'Authorization': `Bearer ${bearerToken}`,
        'Content-Type': 'application/json',
      },
      timeout: 10000,
    });

    if (response.ok) {
      const content = await response.text();
      console.log('Resposta da API:');
      console.log(content);

        document.getElementById('output').innerHTML += '<pre>' + content + '</pre>';

    } else {
      console.log(`Erro: ${response.status}`);
      const errorContent = await response.text();
      console.log(errorContent);

      document.getElementById('output').innerHTML += '<pre>' + errorContent + '</pre>';
    }
  } catch (error) {
    console.error(`Erro ao chamar a API: ${error.message}`);
    console.error(error);
  }
}

async function obterBearerToken() {
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
    const payloadAuth = {
      client_id: clientId,
      client_secret: clientSecret,
      person_id: personId,
    };

    // Fazer requisição POST
    const response = await fetch(authUrl, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
      },
      body: JSON.stringify(payloadAuth),
      timeout: 10000,
    });

    if (!response.ok) {
      console.log(`Erro ao obter o token: ${response.status}`);
      const errorContent = await response.text();
      console.log(errorContent);

      document.getElementById('output').innerHTML += '<pre>' + errorContent + '</pre>';
      return null;
    }

    console.log('Token obtido com sucesso!');
    const responseContent = await response.text();

    // Fazer parse do JSON
    const authContent = JSON.parse(responseContent);
    const bearerToken = authContent.token;

    return bearerToken;
  } catch (error) {
    console.error(`Erro ao obter o token: ${error.message}`);
    console.error(error);
    return null;
  }
}

// Executar a função principal
main().catch((error) => {
  console.error('Erro não capturado:', error);
});