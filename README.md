
# APIFlow.com.br

  

## O que é APIFlow? 

O [**API Flow**](https://www.apiflow.com.br) é uma plataforma que conecta consumidores e fornecedores de APIs em um único ambiente.  
Ela reúne um catálogo confiável de soluções prontas para uso, permitindo que empresas e profissionais:  
  
- **Consumam APIs** de forma ágil, segura e com contratação simplificada, pagando apenas pelo uso.  
- **Ofereçam APIs** já desenvolvidas, ampliando sua visibilidade e monetizando cada utilização.  
  
O API Flow simplifica integrações, impulsiona a inovação e transforma APIs em oportunidades de negócio.
  

## Características Principais  

-  **Autenticação por Cliente**: Utiliza credenciais (Client ID, Client Secret, Person ID) para autenticação

-  **Gateway de APIs**: Disponibiliza um gateway centralizado para roteamento de requisições

-  **Suporte Multiplataforma**: Oferece exemplos em várias linguagens (Java, DotNet, JavaScript, Python, Dart/Flutter, .NET, PHP, Curl, WGet entre outras)

-  **Documentação Completa**: Fornece documentação passo a passo para criar credenciais e integrar

  

## Endpoints Principais  

-  **Autenticação**: `https://router.apiflow.com.br/customer/api/Auth/Client`

-  **Gateway**: `https://gtw.apiflow.com.br/` (com rotas específicas)

  

## Fluxo de Autenticação  

1. Enviar credenciais (client_id, client_secret, person_id) para o endpoint de autenticação

2. Receber um token (bearer token) em resposta

3. Usar o token nos headers das requisições para o gateway

  

## Segurança 

- ⚠️ **Importante**: Nunca exponha suas credenciais em código-fonte

- Utilize variáveis de ambiente ou arquivos de configuração seguros

- O token de autenticação deve ser incluído no header `Authorization: Bearer <token>`

## Exemplos nas principais linguagens de programação

  - Há diversos exemplos de implementação em múltiplas linguagens disponíveis neste repositório  

	1. dart/flutter : [/dart-flutter](./dart-flutter)
	2. dotnet : [/dotnet](./dotnet)
	3. java : [/java](./java)
	4. javascript : [/javascript](./javascript)
	5. python : [/python](./python)


## Recursos Adicionais
- Documentação oficial: https://www.apiflow.com.br/docs/content/step-by-step/Create-Credentials