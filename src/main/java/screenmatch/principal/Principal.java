package screenmatch.principal;

import screenmatch.model.DadosEpisodio;
import screenmatch.model.DadosSerie;
import screenmatch.model.DadosTemporada;
import screenmatch.model.Episodio;
import screenmatch.service.ConsumoApi;
import screenmatch.service.ConverteDados;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private final String ENDERECO = "https://www.omdbapi.com/?t=";
    private final String API_KEY = "&apikey=d3a5146b";
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverteDados conversor = new ConverteDados();
    private Scanner scanner = new Scanner(System.in);

    public void exibeMenu() throws IOException, InterruptedException {
        System.out.println("\nDigite uma serie para busca:");
        var busca = scanner.nextLine().replace(" ", "+");

        var json = consumoApi.obterDados(ENDERECO+busca+API_KEY);
        DadosSerie dadosSerie = conversor.obterDados(json, DadosSerie.class);
        System.out.println(dadosSerie);

        List<DadosTemporada> temporadas = new ArrayList();//Criando lista de temporadas

		for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
			json = consumoApi.obterDados(ENDERECO+busca+"&season="+i+API_KEY);
		    DadosTemporada dadosTemporada = conversor.obterDados(json, DadosTemporada.class);
		    temporadas.add(dadosTemporada);
		}
		temporadas.forEach(System.out::println);

        System.out.println("\nTodos episodios:\n");

        for (int i = 1; i < dadosSerie.totalTemporadas() ; i++) {
            List<DadosEpisodio> episodiosTemporada = temporadas.get(i).episodios();
            for (int j = 1; j < episodiosTemporada.size() ; j++) {
                System.out.println(j + "- " + episodiosTemporada.get(j).titulo() + ", Nota: " + episodiosTemporada.get(j).avaliacao() + ", Data: " + episodiosTemporada.get(j).dataLancamento());
            }
        }

        System.out.println("\n Top 5 episodios mais bem avaliados:\n");

        List<DadosEpisodio> EpisodiosMaisBemAvaliados = temporadas.stream()
                .flatMap(t -> t.episodios().stream())
                .peek(e -> System.out.println("Achatamento de estrutura " + e))
                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                .peek(e -> System.out.println("Filtrando " + e))
                .sorted(Comparator.comparing(DadosEpisodio::avaliacao).reversed())
                .peek(e -> System.out.println("Ordenando " + e))
                .limit(5)
                .peek(e -> System.out.println("Definindo um limite " + e))
                        .toList();


        System.out.println("\nConvertendo em OBJ Episodio...\n");

        List<Episodio> episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(e -> new Episodio(t.temporada(), e)))
                        .collect(Collectors.toList());

       episodios.forEach(System.out::println);

        System.out.println("\nDigite o nome de um EP para buscarmos: \n");

        String textoDigitado = scanner.nextLine();

        Optional<Episodio> epBuscado = episodios.stream()
                .filter(e -> e.getTitulo().toUpperCase().contains(textoDigitado.toUpperCase()))
                .findFirst();

        if(epBuscado.isPresent()) {
            System.out.println("Episodio encontrado!");
            System.out.println("Titulo: " + epBuscado.get().getTitulo() + " Temporada: " + epBuscado.get().getTemporada());
        } else {
            System.out.println("Desculpe, nao encontramos este episodio!");
        }

        System.out.println("Media de avaliacao por temporada");

        Map<Integer, Double> mediaTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() !=null && e.getAvaliacao() >= 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporada, Collectors.averagingDouble(Episodio::getAvaliacao)));

        System.out.println(mediaTemporada);

//
//       //Filtrando por data
//
//        System.out.println("\nFiltre sua busca por data de lancamento, digite um ano:\n");
//
//        var ano = scanner.nextInt();
//        LocalDate data = LocalDate.of(ano, 1, 1);
//        LocalDate mes = LocalDate.of(ano, 12, 1);
//
//        episodios.stream()
//                .filter(e -> e.getDataLancamento() != null && e.getDataLancamento().isAfter(data) && e.getDataLancamento().isBefore(mes))
//                .forEach(e -> System.out.println("Temporada: " + e.getTemporada() + " Titulo: " + e.getTitulo() + " Data lancamento: " + e.getDataLancamento()));


    }
}

