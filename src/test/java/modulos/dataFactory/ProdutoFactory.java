package modulos.dataFactory;

import java.util.ArrayList;
import java.util.List;

import pogo.Componente;
import pogo.Produto;

public class ProdutoFactory {
    public static Produto criarProdutoComum(String nome, double valor, String urlMock) {
        Produto produto = new Produto();
        produto.setProdutoNome(nome);
        produto.setProdutoValor(valor);
        produto.setProdutoUrlMock(urlMock);

        List<String> cores = new ArrayList<>();
        cores.add("preto");
        cores.add("Amarelo");
        produto.setProdutoCores(cores);

        List<Componente> componentes = new ArrayList<>();
        Componente componente = new Componente();
        componente.setComponenteNome("Pessoa");
        componente.setComponenteQuantidade(1);
        componentes.add(componente);

        produto.setComponentes(componentes);

        return produto;
    }
}
