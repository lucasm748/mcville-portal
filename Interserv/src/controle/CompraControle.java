/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import modelo.Compra;
import modelo.ItensCompra;
import modelo.Venda;
import modelo.Pessoa;
import modelo.Produto;
import modelo.ItensVenda;

/**
 *
 * @author jaime
 */
public class CompraControle {

    private DaoGenerico dao;
    private static CompraControle instance;

    public CompraControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized CompraControle getInstance() {
        if (instance == null) {
            instance = new CompraControle();
        }
        return instance;
    }

    public void salvar(Compra obj, int atualiza) {
       
        if (atualiza == 0){

        for (ItensCompra ic : obj.getItensCompra()) {
            System.out.println("teste1:" + ic);
            Produto p = ic.getProduto();
            System.out.println("teste2:" + p.getEstoque() + " + " + ic.getQuantidade());
            p.setEstoque(p.getEstoque() + ic.getQuantidade());
            p.setCusto(ic.getValorProd());
            p.setLucro(ic.getValorLucro());
            p.setPreco(ic.getValorVendaProd());
            System.out.println("Novo preço de Custo: " + ic.getProduto().getCusto());
            dao.save(p);
            
        }}else{
            for (ItensCompra ic : obj.getItensCompra()) {
            System.out.println("teste1:" + ic);
            Produto p = ic.getProduto();
            p.setCusto(ic.getValorProd());
            p.setLucro(ic.getValorLucro());
            p.setPreco(ic.getValorVendaProd());
            System.out.println("Novo preço de Custo: " + ic);
            dao.save(p);
            System.out.println("Estoque não aumentado");
                       }
        dao.save(obj);
    }
    }

    public List<Compra> listaTodos() {
        return dao.list(Compra.class);
    }

    public void deletar(Compra obj) {


        for (ItensCompra ic : obj.getItensCompra()) {
            System.out.println("teste1:" + ic);
            Produto p = ic.getProduto();
            System.out.println("teste2:" + p.getEstoque() + " - " + ic.getQuantidade());
            Integer aux;
            aux = p.getEstoque() - ic.getQuantidade();
            System.out.println(aux);
            p.setEstoque(aux);
            dao.save(p);
        }

        dao.delete(obj);
    }

    public List<Pessoa> listaTodosPessoa() {
        return dao.list(Pessoa.class);
    }

    public List<Produto> listaTodosProduto() {
        return dao.list(Produto.class);
    }

    public List<Compra> listaFiltrando(String filtro, int tipo) {
        return dao.listCriterio(Compra.class, "fornecedor", filtro);
    }

    public List<Compra> ListaData(Date dataCompra) {
        return dao.listaData(Compra.class, "dataCompra", dataCompra);
    }

    public List<Compra> listaID(Long id, int filtro) {
        if (filtro == 0) {
            return dao.listaID(Compra.class, "id", id);
        } else {
            return dao.listaID(Compra.class, "pessoa", id);
        }
    }
    
    public List<Compra> listapCli(Long id){
        return dao.listaSQL(id);
    }

    public boolean estoque(Produto p, Integer qtd) {

        if (((p.getEstoque()) - qtd) <= 0) {
            return false;
        } else {
            return true;
        }
    }
}
