import http.client
import json

# Definição do payload com as credenciais necessárias
payload = """{
  "person_id": "SUA_PERSON_ID",
  "client_id": "SEU_CLIENT_ID",
  "client_secret": "SEU_CLIENT_SECRET"
}"""

# Cabeçalhos da requisição
headersAuth = {
    'Content-Type': "application/json",
}

# Estabelecendo conexão com o endpoint de autenticação do API Flow
connAuth = http.client.HTTPSConnection("router.apiflow.com.br")

# Requisição POST para gerar o token de acesso
connAuth.request("POST", "/customer/api/Auth/Client", payload, headersAuth)

# Resposta do servidor
responseAuth = connAuth.getresponse()
print("Retorno da autenticação")
print("Status:", responseAuth.status)

# Decodificando e obtendo o token de acesso
data = responseAuth.read().decode("utf-8")
token = json.loads(data)["token"]
print("Token:", token)

# Requisição à uma chamada de api disponivel no Api Flow
connGateway = http.client.HTTPSConnection("gtw.apiflow.com.br")

headersGateway = { 'Authorization': "Bearer "+ token }
connGateway.request("POST", "/gtw-8e0cdb967e6645ff955a20412faf5885/hi?name=SEU_NOME", headers=headersGateway)

#verificando a resposta da API
responseGateway = connGateway.getresponse()
print("Retorno da requisição à API")
print("Status:", responseGateway.status)


data = responseGateway.read().decode("utf-8")
print(data)