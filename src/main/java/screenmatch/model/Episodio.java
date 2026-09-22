package screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episodio {
    String titulo;
    Integer temporada;
    Double avaliacao;
    LocalDate dataLancamento;

    public Episodio(Integer temporada, DadosEpisodio dadosEpisodio) {
        this.temporada = temporada;
        this.titulo = dadosEpisodio.titulo();

        try {
            this.avaliacao = Double.parseDouble(dadosEpisodio.avaliacao());
        }catch (NumberFormatException eex) {
            this.avaliacao = null;
        }

        try {
            this.dataLancamento = LocalDate.parse(dadosEpisodio.dataLancamento());
        } catch (NullPointerException | DateTimeParseException   ex) {
            this.dataLancamento =  null;
        }

    }


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Integer getTemporada() {
        return temporada;
    }

    public void setDataLancamento(LocalDate dataLancamento) {
        this.dataLancamento = dataLancamento;
    }

    public LocalDate getDataLancamento() {
        return dataLancamento;
    }

    public void setTemporada(Integer temporada) {
        this.temporada = temporada;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    @Override
    public String toString() {
        return "Temporada: " + temporada +
                " - Título: " + titulo +
                " - Avaliação: " + avaliacao +
                " - Data: " + dataLancamento;
    }
}
