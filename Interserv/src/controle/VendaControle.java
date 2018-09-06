/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import modelo.Venda;
import modelo.Pessoa;
import modelo.Produto;
import modelo.ItensVenda;

/**
 *
 * @author jaime
 */
public class VendaControle {

    private DaoGenerico dao;
    private static VendaControle instance;

    public VendaControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized VendaControle getInstance() {
        if (instance == null) {
            instance = new VendaControle();
        }
        return instance;
    }

    public void salvar(Venda obj, int atualiza) {
       
        if (atualiza == 0){

        for (ItensVenda iv : obj.getItensVenda()) {
            System.out.println("teste1:" + iv);
            Produto p = iv.getProduto();
            System.out.println("teste2:" + p.getEstoque() + " - " + iv.getQuantidade());
            p.setEstoque(p.getEstoque() - iv.getQuantidade());
            dao.save(p);
            
        }}else{
        System.out.println("Estoque não baixado");
        }
        dao.save(obj);
    }

    public List<Venda> listaTodos() {
        return dao.list(Venda.class);
    }

    public void deletar(Venda obj) {


        for (ItensVenda iv : obj.getItensVenda()) {
            System.out.println("teste1:" + iv);
            Produto p = iv.getProduto();
            System.out.println("teste2:" + p.getEstoque() + " + " + iv.getQuantidade());
            Integer aux;
            aux = p.getEstoque() + iv.getQuantidade();
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

    public List<Venda> listaFiltrando(String filtro, int tipo) {
        return dao.listCriterio(Venda.class, "pessoa", filtro);
    }

    public List<Venda> ListaData(Date dataVenda) {
        return dao.listaData(Venda.class, "dataVenda", dataVenda);
    }

    public List<Venda> listaID(Long id, int filtro) {
        if (filtro == 0) {
            return dao.listaID(Venda.class, "id", id);
        } else {
            return dao.listaID(Venda.class, "pessoa", id);
        }
    }
    
    public List<Venda> listapCli(Long id){
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
