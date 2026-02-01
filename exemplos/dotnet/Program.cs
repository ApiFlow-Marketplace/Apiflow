using System.Net.Http;
using System.Net.Http.Headers;
using System.Net.Http.Json;

class Program
{
    static async Task Main(string[] args)
    {
        var bearerToken = await obterBearerToken();
        
        if (bearerToken == null)
        {
            Console.WriteLine("Não foi possível obter o Bearer Token. Encerrando o programa.");
            return;
        }

        // Criar cliente HTTP
        var client = new HttpClient();
        
        // Configurar o header Authorization com Bearer Token
        client.DefaultRequestHeaders.Authorization = 
            new AuthenticationHeaderValue("Bearer", bearerToken);
        
        // Exemplo: API de testes do ApiFlow
        string apiUrl = "https://gtw.apiflow.com.br/gtw-8e0cdb967e6645ff955a20412faf5885/hi?name=SEU_NOME";
        
        try
        {
            var response = await client.PostAsync(apiUrl, null);
            
            if (response.IsSuccessStatusCode)
            {
                var content = await response.Content.ReadAsStringAsync();
                Console.WriteLine("Resposta da API:");
                Console.WriteLine(content);
            }
            else
            {
                Console.WriteLine($"Erro: {response.StatusCode}");
                Console.WriteLine(await response.Content.ReadAsStringAsync());
            }
        }
        catch (Exception ex)
        {
            Console.WriteLine($"Erro ao chamar a API: {ex.Message}");
        }
    }

    public async static Task<string?> obterBearerToken()
    {
        // Credenciais do APIFlow
        // Obtenha em: https://www.apiflow.com.br/docs/content/step-by-step/Create-Credentials


        // ********* ESTE CÓDIGO É APENAS UM EXEMPLO. MANTENHA SUAS CREDENCIAIS SEGURAS E NÃO AS EXPONHA EM CÓDIGO-FONTE **********
        string person_id = "SEU_PERSON_ID_AQUI";
        string client_id = "SEU_CLIENT_ID_AQUI";
        string client_secret = "SEU_CLIENT_SECRET_AQUI";

        
        var clientAuth = new HttpClient();
        var authUrl = "https://router.apiflow.com.br/customer/api/Auth/Client";

        var payloadAuth = new {
            client_id=client_id,
            client_secret=client_secret,
            person_id=person_id
        };


        var responseAuth = await clientAuth.PostAsJsonAsync<dynamic>(authUrl, payloadAuth);

        if (!responseAuth.IsSuccessStatusCode)
        {
            Console.WriteLine($"Erro ao obter o token: {responseAuth.StatusCode}");
            Console.WriteLine(await responseAuth.Content.ReadAsStringAsync());
            return null;
        }
        else
        {
            Console.WriteLine("Token obtido com sucesso!");
            var authContent = await responseAuth.Content.ReadFromJsonAsync<Dictionary<string, string>>();
            string bearerToken = authContent["token"];

            return bearerToken;
        }
    }
}


