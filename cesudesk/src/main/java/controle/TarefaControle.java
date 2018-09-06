/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import DAO.ModuloDAO;
import DAO.ProjetoDAO;
import DAO.TarefaDAO;
import DAO.TipoTarefaDAO;
import DAO.TriagemDAO;
import DAO.UsuarioDAO;
import entidades.Execucao;
import entidades.Modulo;
import entidades.Projeto;
import entidades.Tarefa;
import entidades.TipoTarefa;
import entidades.Triagem;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.springframework.security.core.context.SecurityContextHolder;
import util.ConverterGenerico;

/**
 *
 * @author lucas
 */
@ManagedBean(name = "TarefaMB")
@SessionScoped
public class TarefaControle implements Serializable {

    private Tarefa tarefa = new Tarefa();
    @EJB
    private TarefaDAO tarefaDAO;
    @EJB
    private ProjetoDAO projetoDAO;
    @EJB
    private UsuarioDAO usuarioDAO;
    @EJB
    private TriagemDAO triagemDAO;
    @EJB
    private ModuloDAO moduloDAO;
    @EJB
    private TipoTarefaDAO tipoTarefaDAO;

    private Converter projetoConverter;
    private Converter usuarioConverter;
    private Converter triagemConverter;
    private Converter tipoTarefaConverter;
    private Converter moduloConverter;
    private Tarefa tarefaSelecionada;
    private Usuario resp_triagem;
    private Triagem triagem;
    private List<Tarefa> lista_projeto = new ArrayList<Tarefa>();
    private List<Usuario> lista_usuarios = new ArrayList<Usuario>();
    private String btDetalhar;

    public String salvar() {
        tarefaDAO.save(tarefa);
        tarefaSelecionada = new Tarefa();
        validaBotoes();
        return "/aplicacao/tarefa/lista_tarefa";

    }

    public void novo() {
        tarefa = new Tarefa();
        btDetalhar = "false";
        tarefa.setTriagens(new ArrayList<Triagem>());
        triagem = new Triagem();
        tarefaSelecionada = new Tarefa();
    }

    public Converter getProjetoConverter() {
        if (projetoConverter == null) {
            projetoConverter = new ConverterGenerico(projetoDAO);
        }
        return projetoConverter;
    }

    public Converter getModuloConverter() {
        if (moduloConverter == null) {
            moduloConverter = new ConverterGenerico(moduloDAO);
        }
        return moduloConverter;
    }

    public Converter getTipoTarefaConverter() {
        if (tipoTarefaConverter == null) {
            tipoTarefaConverter = new ConverterGenerico(tipoTarefaDAO);
        }
        return tipoTarefaConverter;
    }

    public void addTriagem() {
        tarefa.getTriagens().add(triagem);
        triagem = new Triagem();
    }

    public Converter getUsuarioConverter() {
        if (usuarioConverter == null) {
            usuarioConverter = new ConverterGenerico(usuarioDAO);
        }
        return usuarioConverter;
    }

    public Converter getTriagemConverter() {
        if (triagemConverter == null) {
            triagemConverter = new ConverterGenerico(triagemDAO);
        }
        return triagemConverter;
    }

    public void setTriagemConverter(Converter triagemConverter) {
        this.triagemConverter = triagemConverter;
    }

    public void cancelar() {
        tarefa = new Tarefa();
        tarefaSelecionada = new Tarefa();
    }

    public List<Usuario> todos() {
        lista_usuarios = usuarioDAO.findAll();
        return lista_usuarios;
    }

    public List<Triagem> getMinhasTriagensTarefa() {
        Usuario usu = getUsuarioSsesao();
        List<Triagem> minhas_triagens_tarefa = tarefaDAO.listar_triagens_usuario(usu);
        return minhas_triagens_tarefa;
    }

    public void iniciaTarefa(ActionEvent e) {
        tarefa = (Tarefa) e.getComponent().getAttributes().get("tarefa");

    }

    public List<Tarefa> getLista() {
        List<Tarefa> lista = new ArrayList<Tarefa>();
        lista = tarefaDAO.findAll();
        return lista;
    }

    public void onRowSelect(SelectEvent event) {
        tarefaSelecionada = new Tarefa();
        tarefaSelecionada = (Tarefa) event.getObject();
        validaBotoes();
    }

    public Usuario getUsuarioSsesao() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(login);
        Usuario user = usuarioDAO.pesquisa(login);
        return user;
    }

    public void onRowUnselect(UnselectEvent event) {
        tarefaSelecionada = new Tarefa();
        validaBotoes();
    }

    public void validaBotoes() {
        btDetalhar = "false";
        if (tarefaSelecionada.getCd_tarefa() > 0) {
            btDetalhar = "true";
        } else {
            btDetalhar = "false";
        }
    }

    public void setProjetoConverter(Converter projetoConverter) {
        this.projetoConverter = projetoConverter;
    }

    public void setUsuarioConverter(Converter usuarioConverter) {
        this.usuarioConverter = usuarioConverter;
    }

    public void getEditar(ActionEvent e) {
        tarefa = (Tarefa) e.getComponent().getAttributes().get("tarefa");
        triagem = new Triagem();
    }

    public void excluir(ActionEvent e) {
        tarefa = (Tarefa) e.getComponent().getAttributes().get("tarefa");
        tarefaDAO.delete(tarefa);
    }

    public void removerTriagem(ActionEvent e) {
        Triagem tri = (Triagem) e.getComponent().getAttributes().get("execucao");
        tarefa.getTriagens().remove(tri);
    }

    public void setLista(List<Tarefa> lista_projeto) {
        this.lista_projeto = lista_projeto;
    }

    public TarefaDAO getTarefaDAO() {
        return tarefaDAO;
    }

    public void setTarefaDAO(TarefaDAO tarefaDAO) {
        this.tarefaDAO = tarefaDAO;
    }

    public Tarefa getTarefa() {
        return tarefa;
    }

    public void setTarefa(Tarefa tarefa) {
        this.tarefa = tarefa;
    }

    public List<SelectItem> getProjetos() {
        List<SelectItem> lista_projetoProjetos = new ArrayList<SelectItem>();
        for (Projeto e : projetoDAO.findAll()) {
            lista_projetoProjetos.add(new SelectItem(e, e.getDesc_projeto()));
        }
        return lista_projetoProjetos;
    }

    public List<SelectItem> getUsuarios() {
        List<SelectItem> lista_usuarioss = new ArrayList<SelectItem>();
        for (Usuario e : usuarioDAO.findAll()) {
            lista_usuarioss.add(new SelectItem(e, e.getNm_usuario()));
        }
        return lista_usuarioss;
    }

    public void setProjetoDAO(ProjetoDAO projetoDAO) {
        this.projetoDAO = projetoDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public void setLista_projeto(List<Tarefa> lista_projeto) {
        this.lista_projeto = lista_projeto;
    }

    public List<Triagem> getLista_triagens() {
        return tarefa.getTriagens();
    }

    public void setLista_usuarios(List<Usuario> lista_usuarios) {
        this.lista_usuarios = lista_usuarios;
    }

    public Triagem getTriagem() {
        return triagem;
    }

    public void setTriagem(Triagem triagem) {
        this.triagem = triagem;
    }

    public Tarefa getItemSelecionado() {
        return tarefaSelecionada;
    }

    public void setItemSelecionado(Tarefa tarefaSelecionada) {
        this.tarefaSelecionada = tarefaSelecionada;
    }

    public Usuario getResp_triagem() {
        return resp_triagem;
    }

    public void setResp_triagem(Usuario resp_triagem) {
        this.resp_triagem = resp_triagem;
    }

    public TriagemDAO getTriagemDAO() {
        return triagemDAO;
    }

    public void setTriagemDAO(TriagemDAO triagemDAO) {
        this.triagemDAO = triagemDAO;
    }

    public ProjetoDAO getProjetoDAO() {
        return projetoDAO;
    }

    public Tarefa getTarefaSelecionada() {
        return tarefaSelecionada;
    }

    public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
        this.tarefaSelecionada = tarefaSelecionada;
    }

    public String getBtDetalhar() {
        return btDetalhar;
    }

    public void setBtDetalhar(String btDetalhar) {
        this.btDetalhar = btDetalhar;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public List<Tarefa> getLista_projeto() {
        return lista_projeto;
    }

    public List<Usuario> getLista_usuarios() {
        return lista_usuarios;
    }

    public void setTipoTarefaConverter(Converter tipoTarefaConverter) {
        this.tipoTarefaConverter = tipoTarefaConverter;
    }

    public ModuloDAO getModuloDAO() {
        return moduloDAO;
    }

    public void setModuloDAO(ModuloDAO moduloDAO) {
        this.moduloDAO = moduloDAO;
    }

    public TipoTarefaDAO getTipoTarefaDAO() {
        return tipoTarefaDAO;
    }

    public void setTipoTarefaDAO(TipoTarefaDAO tipoTarefaDAO) {
        this.tipoTarefaDAO = tipoTarefaDAO;
    }

    public List<SelectItem> getTipos_tarefa() {
        List<SelectItem> tipos_tarefa = new ArrayList<SelectItem>();
        for (TipoTarefa e : tipoTarefaDAO.findAll()) {
            tipos_tarefa.add(new SelectItem(e, e.getDesc_tipotarefa()));
        }
        return tipos_tarefa;
    }

    public List<SelectItem> getModulos_tarefa() {
        List<SelectItem> modulos_tarefa = new ArrayList<SelectItem>();
        for (Modulo e : moduloDAO.findAll()) {
            modulos_tarefa.add(new SelectItem(e, e.getDesc_modulo()));
        }
        return modulos_tarefa;
    }

    public void setModuloConverter(Converter moduloConverter) {
        this.moduloConverter = moduloConverter;
    }

}
