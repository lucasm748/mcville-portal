/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import DAO.ProjetoDAO;
import DAO.TriagemDAO;
import DAO.ExecucaoDAO;
import DAO.TarefaDAO;
import DAO.UsuarioDAO;
import entidades.Projeto;
import entidades.Triagem;
import entidades.Execucao;
import entidades.Tarefa;
import entidades.Usuario;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import org.springframework.security.core.context.SecurityContextHolder;
import util.ConverterGenerico;

/**
 *
 * @author lucas
 */
@ManagedBean(name = "TriagemMB")
@SessionScoped
public class TriagemControle implements Serializable {

    private Triagem triagem = new Triagem();
    @EJB
    private TriagemDAO triagemDAO;
    @EJB
    private ProjetoDAO projetoDAO;
    @EJB
    private UsuarioDAO usuarioDAO;
    @EJB
    private ExecucaoDAO execucaoDAO;
    @EJB
    private TarefaDAO tarefaDAO;

    private Converter projetoConverter;
    private Converter usuarioConverter;
    private Triagem itemSelecionado;
    private Usuario resp_execucao;
    private String resultado_triagem;
    private Execucao execucao;
    private Execucao execucaoaux;
    private List<Triagem> lista_projeto = new ArrayList<Triagem>();
    private List<Usuario> lista_usuarios = new ArrayList<Usuario>();
    private List<Execucao> lista_execucoes = new ArrayList<Execucao>();
    private Tarefa tar;
    private String qt_horas_gastas;

    public TriagemControle() {
        this.resultado_triagem = "true";
    }

    public String salvar() {
        triagem.setQt_horas_triagem(CalculaTempo());
        if (!triagem.getExecucoes().isEmpty()) {
            if (!triagem.getTp_statustriagem().equals("Fechada")) {
                triagem.setTp_statustriagem("Andamento");
            }
        }
        triagemDAO.save(triagem);
        itemSelecionado = new Triagem();
        return "/aplicacao/triagem/lista_triagem";
    }

    public String CalculaTempo() {
        Long diferenca = Long.valueOf(0);
        Long diferenca_aux = Long.valueOf(0);
        String resultado = "0:00";
        if (!triagem.getExecucoes().isEmpty()) { //se tiver execução
            for (Execucao ex : triagem.getExecucoes()) {
                diferenca_aux = ex.getDh_fimexecucao().getTime() - ex.getDh_inicioexecucao().getTime();
                diferenca = diferenca_aux + diferenca;
            }
            if (diferenca > 359940000) {
                resultado = String.format("%02d:%02d", diferenca / 3600000, (diferenca / 60000) % 60);
            } else {
                resultado = String.format("%03d:%02d", diferenca / 3600000, (diferenca / 60000) % 60);
            }
        }
        return resultado;
    }

    public void novo() {
        triagem = new Triagem();
        itemSelecionado = new Triagem();

    }

    public void novaExecucao() {
        execucao = new Execucao();
        execucao.setDh_inicioexecucao(new Date());
    }

    public String getIniciarAgora(ActionEvent e) {
        itemSelecionado = (Triagem) e.getComponent().getAttributes().get("triagem");
        execucao = new Execucao();
        execucao.setDh_inicioexecucao(new Date());
        execucao.setDh_fimexecucao(null);
        execucao.setNr_triagem(itemSelecionado.getIdTriagem().intValue());
        itemSelecionado.getExecucoes().add(execucao);
        execucaoDAO.save(execucao);
        execucao = new Execucao();
        return "/aplicacao/tarefa/edita_triagem";
    }

    public String getIniciarApos(ActionEvent e) {
        itemSelecionado = (Triagem) e.getComponent().getAttributes().get("triagem");
        execucao = new Execucao();
        execucaoaux = new Execucao();
        Usuario usu = itemSelecionado.getUsuario();
        execucaoaux = execucaoDAO.getUltimaExecUsuario(usu);
        execucao.setDh_inicioexecucao(execucaoaux.getDh_fimexecucao());
        String pagina = "/aplicacao/tarefa/edita_execucao";
        return pagina;
    }

    public void getPausar(ActionEvent e) {
        itemSelecionado = (Triagem) e.getComponent().getAttributes().get("triagem");
        execucao = new Execucao();
        for (Execucao ex : itemSelecionado.getExecucoes()) {
            if (ex.getDh_fimexecucao().toString().isEmpty()) {
                ex.setDh_fimexecucao(new Date());
                execucao = ex;
                break;
            } else {
                FacesMessage msg = new FacesMessage("Não existe execução em andamento para pausar! ID triagem: ".concat(itemSelecionado.getIdTriagem().toString()));
            }
        }
        execucaoDAO.save(execucao);
        triagem.setQt_horas_triagem(CalculaTempo()); // calcula total de tempo gasto até o momento
        execucaoDAO.save(execucao);
        triagemDAO.save(triagem);
    }

    public String getEncerrar(ActionEvent e) {
        itemSelecionado = (Triagem) e.getComponent().getAttributes().get("triagem");
        String pagina = "/aplicacao/tarefa/edita_triagem";
        if (itemSelecionado.getExecucoes().isEmpty()) {
            FacesMessage msg = new FacesMessage("Não existem execuções desta triagem, por favor faça o apontamento! ID triagem: ".concat(itemSelecionado.getIdTriagem().toString()));
            FacesContext.getCurrentInstance().addMessage(null, msg);
            System.out.println(msg);
        } else {
            pagina = "/aplicacao/tarefa/edita_execucao";
            resultado_triagem = "false";

        }
        return pagina;
    }

    public TarefaDAO getTarefaDAO() {
        return tarefaDAO;
    }

    public void setTarefaDAO(TarefaDAO tarefaDAO) {
        this.tarefaDAO = tarefaDAO;
    }

    public Converter getProjetoConverter() {
        if (projetoConverter == null) {
            projetoConverter = new ConverterGenerico(projetoDAO);
        }
        return projetoConverter;
    }

    public Converter getUsuarioConverter() {
        if (usuarioConverter == null) {
            usuarioConverter = new ConverterGenerico(usuarioDAO);
        }
        return usuarioConverter;
    }

    public List<Triagem> getTriagens_tarefa() {
        List<Triagem> triagens_tarefa = new ArrayList<Triagem>();
        triagens_tarefa = tar.getTriagens();
        return triagens_tarefa;
    }

    public Tarefa getTar() {
        return tar;
    }

    public void addExecucao() {
        triagem.getExecucoes().add(execucao);
        execucao = new Execucao();
    }

    public void removeExecucao(ActionEvent e) {
        Execucao ex = (Execucao) e.getComponent().getAttributes().get("execucao");
        triagem.getExecucoes().remove(ex);
    }

    public void setTar(Tarefa tar) {
        this.tar = tar;
    }

    public Usuario getUsuarioSsesao() {
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println(login);
        Usuario user = usuarioDAO.pesquisa(login);
        return user;
    }

    public List<Tarefa> getAtribuidas_usuario() {
        Usuario usu = getUsuarioSsesao();
        List<Tarefa> atribuidas_usuario = tarefaDAO.listar_atribuidas(usu);
        return atribuidas_usuario;
    }

    public void cancelar() {
        triagem = new Triagem();
        itemSelecionado = new Triagem();
    }

    public List<Usuario> todos() {
        lista_usuarios = usuarioDAO.findAll();
        return lista_usuarios;
    }

    public void setProjetoConverter(Converter projetoConverter) {
        this.projetoConverter = projetoConverter;
    }

    public void setUsuarioConverter(Converter usuarioConverter) {
        this.usuarioConverter = usuarioConverter;
    }

    public void editar(ActionEvent e) {
        resultado_triagem = "true";
        triagem = (Triagem) e.getComponent().getAttributes().get("triagem");
        itemSelecionado = new Triagem();
        novaExecucao();
    }

    public void getCarregar_tarefa(ActionEvent e) {
        tar = (Tarefa) e.getComponent().getAttributes().get("tarefa");
        tar = tarefaDAO.getById(tar.getCd_tarefa());
        itemSelecionado = new Triagem();
    }

    public void excluir(ActionEvent e) {
        triagem = (Triagem) e.getComponent().getAttributes().get("triagem");
        triagemDAO.delete(triagem);
        itemSelecionado = new Triagem();
    }

    public List<Triagem> getLista() {
        return triagemDAO.findAll();
    }

    public void setLista(List<Triagem> lista_projeto) {
        this.lista_projeto = lista_projeto;
    }

    public TriagemDAO getTriagemDAO() {
        return triagemDAO;
    }

    public void setTriagemDAO(TriagemDAO triagemDAO) {
        this.triagemDAO = triagemDAO;
    }

    public Triagem getTriagem() {
        return triagem;
    }

    public void setTriagem(Triagem triagem) {
        this.triagem = triagem;
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

    public List<SelectItem> getUsuariosAux() {
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

    public void setLista_projeto(List<Triagem> lista_projeto) {
        this.lista_projeto = lista_projeto;
    }

    public List<Execucao> getLista_execucoes() {
        return triagem.getExecucoes();
    }

    public void setLista_triagens(List<Execucao> lista_execucoes) {
        this.lista_execucoes = lista_execucoes;
    }

    public void setLista_usuarios(List<Usuario> lista_usuarios) {
        this.lista_usuarios = lista_usuarios;
    }

    public Execucao getExecucao() {
        return execucao;
    }

    public void setExecucao(Execucao execucao) {
        this.execucao = execucao;
    }

    public Triagem getItemSelecionado() {
        return itemSelecionado;
    }

    public void setItemSelecionado(Triagem itemSelecionado) {
        this.itemSelecionado = itemSelecionado;
    }

    public Usuario getResp_execucao() {
        return resp_execucao;
    }

    public void setResp_execucao(Usuario resp_execucao) {
        this.resp_execucao = resp_execucao;
    }

    public ExecucaoDAO getExecucaoDAO() {
        return execucaoDAO;
    }

    public void setExecucaoDAO(ExecucaoDAO execucaoDAO) {
        this.execucaoDAO = execucaoDAO;
    }

    public ProjetoDAO getProjetoDAO() {
        return projetoDAO;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public List<Triagem> getLista_projeto() {
        return lista_projeto;
    }

    public List<Usuario> getLista_usuarios() {
        return lista_usuarios;
    }

    public Execucao getExecucaoaux() {
        return execucaoaux;
    }

    public void setExecucaoaux(Execucao execucaoaux) {
        this.execucaoaux = execucaoaux;
    }

    public String getResultado_triagem() {
        return resultado_triagem;
    }

    public void setResultado_triagem(String resultado_triagem) {
        this.resultado_triagem = resultado_triagem;
    }

    public String getQt_horas_gastas() {
        return qt_horas_gastas;
    }

    public void setQt_horas_gastas(String qt_horas_gastas) {
        this.qt_horas_gastas = qt_horas_gastas;
    }

}
