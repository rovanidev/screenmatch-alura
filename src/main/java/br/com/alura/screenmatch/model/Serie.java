package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.Locale;
import java.util.Optional;
import java.util.OptionalDouble;

public class Serie {
    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria categoria;
    private String atores;
    private String poster;
    private String sinopse;

    public Serie(DadosSerie dadosSerie) {
        this.titulo = titulo;
        this.totalTemporadas = totalTemporadas;
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        this.categoria = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        this.atores = atores;
        this.poster = poster;
        this.sinopse = sinopse;
    }

}
