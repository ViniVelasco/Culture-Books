package cmp1144.pucgoias.com.culturebooks.model;

public class Livro {

    private String titulo;
    private String imagem;
    private Integer quantidade;
    private Double valorUnitario;
    private Double valorTotal;

    public Livro() {
    }

    public Livro(Livro livro) {
        this.titulo = livro.getTitulo();
        this.imagem = livro.getImagem();
        this.quantidade = livro.getQuantidade();
        this.valorUnitario = livro.getValorUnitario();
        this.valorTotal = livro.getValorTotal();
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    @Override
    public String toString() {
        return this.titulo;
    }
}
