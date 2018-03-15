package cmp1144.pucgoias.com.culturebooks.model;

import java.util.ArrayList;

public class Carrinho {

    private ArrayList<Livro> livrosNoCarrinho = new ArrayList<>();
    private Double total;
    public Pessoa pessoa;

    public void setLivrosNoCarrinho(Livro l) {
        this.livrosNoCarrinho.add(l);
    }

    public Carrinho() {
        this.total = 0.00;

    }

    public ArrayList<Livro> getLivrosNoCarrinho() {
        return livrosNoCarrinho;
    }

    public void calculaValorCompra(){
        Double totalAtual = 0.00;
        for(Livro l : livrosNoCarrinho){
            totalAtual += l.getValorTotal();
        }
        this.total = totalAtual;
    }


    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
