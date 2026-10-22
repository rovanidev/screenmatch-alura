package br.com.alura.screenmatch.service;

import br.com.alura.screenmatch.model.DadosSerie;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class TranslateService {

    public static String translate(String str) {
           ConsumoApi consumoApi = new ConsumoApi();
           ConverteDados converteDados = new ConverteDados();
           ObjectMapper objectMapper = new ObjectMapper();

           //Using the URL Enconder to fix a url removing the spaces and criptografing.
            String strCodificado = URLEncoder.encode(str, StandardCharsets.UTF_8);
            String URL= "https://api.mymemory.translated.net/get?q="+strCodificado+"!&langpair=en%7Cpt-BR";
        try {
            var traducao = objectMapper.readTree(consumoApi.obterDados(URL));

            return traducao
                    .path("responseData")
                    .path("translatedText")
                    .asText();

        } catch (JsonProcessingException | IllegalArgumentException | IllegalStateException e) {
            throw new RuntimeException("Não foi possível realizar a tradução da Sinopse");
        }


    }

}
