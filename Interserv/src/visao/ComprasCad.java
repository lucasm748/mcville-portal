/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * CompraCad.java
 *
 * Created on 25/07/2011, 20:39:45
 */
package visao;

import controle.JMoneyField;
import controle.PControle;
import controle.PFControle;
import controle.PJControle;
import controle.CompraControle;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import javax.swing.JTabbedPane;
import modelo.Compra;
import modelo.PessoaJ;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;
import modelo.ItensCompra;
import modelo.PessoaF;
import modelo.Produto;
import controle.ProdutoControle;
import dao.Conexao;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.SwingUtilities;
import modelo.PessoaJ;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author Jaime
 */
public class ComprasCad extends javax.swing.JDialog {

    public int fj;
    private List<Compra> listaVe = new ArrayList<Compra>();
    private List<Produto> listaProd = new ArrayList<Produto>();
    private List<ItensCompra> listaIV = new ArrayList<ItensCompra>();
    private List<PessoaJ> listaJ = new ArrayList<PessoaJ>();
    private PJControle pjcontrole;
    private Compra Compra;
    private CompraControle vcontrole;
    private ProdutoControle prodcontrole;
    private ItensCompra ic;
    private PessoaJ fornecedor;

    public String soma(String valor1, String valor2) {

        NumberFormat format = new DecimalFormat("0.#E0");

        double v1 = Double.parseDouble(valor1);
        double v2 = Double.parseDouble(valor2);
        double res = v1 + v2;
        return format.format(res);
    }

    public void validaBotoes(String acao) {
        if (acao.equals("inicio")) {
            comboPessoa.setEnabled(false);
            btNovo.setEnabled(true);
            btSalvar.setEnabled(false);
            btExcluir.setEnabled(false);
            btAlterar.setEnabled(false);
            btCancelar.setEnabled(false);
            btSair.setEnabled(true);
            campoData.setEnabled(false);
            painelTabela.setEnabled(false);
            campoQuantidade.setEnabled(false);
            campoValorItem.setEnabled(false);
            campoLucro.setEnabled(false);
            campoVenda.setEnabled(false);
            tabela.setEnabled(true);
            painelMestre.setVisible(false);
            comboProdutos.setEnabled(false);
            btAdicionar.setEnabled(false);
            btRemover.setEnabled(false);
            painelTabela.setVisible(true);
            lblEstoque.setVisible(false);
            btImprime.setEnabled(false);

        } else if (acao.equals("novo") || acao.equals("alterar")) {
            btNovo.setEnabled(false);
            btSalvar.setEnabled(true);
            btExcluir.setEnabled(false);
            btAlterar.setEnabled(false);
            btCancelar.setEnabled(true);
            btSair.setEnabled(false);
            campoData.setEnabled(true);
            comboPessoa.setEnabled(true);
            painelTabela.setEnabled(false);
            tabela.setEnabled(false);
            painelMestre.setVisible(true);
            comboProdutos.setEnabled(false);
            btAdicionar.setEnabled(false);
            btRemover.setEnabled(false);
            painelTabela.setVisible(false);
            campoQuantidade.setEnabled(true);
            campoValorItem.setEnabled(true);
            comboProdutos.setEnabled(true);
            btAdicionar.setEnabled(true);
            btRemover.setEnabled(true);
            comboProdutos.setSelectedItem(null);
            lblEstoque.setText(null);
            campoLucro.setEnabled(true);
            campoVenda.setEnabled(true);
            btImprime.setEnabled(false);
        } else if (acao.equals("selecionado")) {
            btNovo.setEnabled(true);
            btSalvar.setEnabled(false);
            btExcluir.setEnabled(true);
            btAlterar.setEnabled(true);
            btCancelar.setEnabled(true);
            btSair.setEnabled(true);
            campoData.setEnabled(false);
            comboPessoa.setEnabled(false);
            painelTabela.setEnabled(true);
            tabela.setEnabled(true);
            painelMestre.setVisible(true);
            comboProdutos.setEnabled(false);
            campoQuantidade.setEnabled(false);
            btAdicionar.setEnabled(false);
            btRemover.setEnabled(false);
            campoValorItem.setEnabled(false);
            painelTabela.setVisible(true);
            campoLucro.setEnabled(false);
            campoVenda.setEnabled(false);
            btImprime.setEnabled(true);
        }
    }

    public void geraCompra(Long esta) {
        try {
            System.out.println("A id da Compra é : " + esta);
            Map parametros = new HashMap();
            parametros.put("FILTROCOMPRA", esta);
            URL arquivo = getClass().getResource("/relatorios/NotaCompra.jasper");
            JasperReport relatorio = (JasperReport) JRLoader.loadObject(arquivo);
            JasperPrint impressao = JasperFillManager.fillReport(relatorio, parametros, Conexao.getConnection());
            JasperViewer visualizador = new JasperViewer(impressao, false);
            visualizador.setTitle("NOTA DE COMPRA");
            visualizador.setAlwaysOnTop(true);
            visualizador.setVisible(true);

            this.dispose();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //Valida os campos obrigatórios de um cadastro, 
    //retornando um boolean true quando verdadeiro ou false quando tiver algum campo em branco.

    public Boolean validaCampos() {
        if (comboPessoa.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "A combo Fornecedor é obrigatório!");
            return false;
        } else {
            return true;
        }
    }

    public void icones() {
        btNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/add.png")));
        btNovo.setToolTipText("Inserir um registro");
        btNovo.setSize(30, 30);
        btNovo.setText("");
        btAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/altera.png")));
        btAlterar.setToolTipText("Alterar um registro");
        btAlterar.setSize(30, 30);
        btAlterar.setText("");
        btCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/deleteazul.png")));
        btCancelar.setToolTipText("Cancela alterações");
        btCancelar.setSize(30, 30);
        btCancelar.setText("");
        btExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png")));
        btExcluir.setToolTipText("Remover um registro");
        btExcluir.setSize(30, 30);
        btExcluir.setText("");
        btSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/door_in.png")));
        btSair.setToolTipText("Fechar o formulário");
        btSair.setSize(30, 30);
        btSair.setText("");
        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/accept.png")));
        btSalvar.setToolTipText("Salva alterações no registro");
        btSalvar.setSize(30, 30);
        btSalvar.setText("");
        btImprime.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/Printer.png")));
        btImprime.setToolTipText("Imprime esta nota");
        btImprime.setSize(30, 30);
        btImprime.setText("");


        btAdicionar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/add.png")));
        btRemover.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png")));
    }

    public void montaComboCli() {
        comboPessoa.removeAllItems();
        listaJ = pjcontrole.listaTodos();

        for (PessoaJ pj : listaJ) {
            comboPessoa.addItem(pj.getNome());
        }

    }

    public Compra getVe() {
        return Compra;
    }

    public void setCompra(Compra ve) {
        this.Compra = Compra;
    }

    public List<Compra> getListaVe() {
        return listaVe;
    }

    public void setListaVe(List<Compra> listaVe) {
        this.listaVe = listaVe;
    }

    public List<ItensCompra> getListaIV() {
        return listaIV;
    }

    public List<ItensCompra> setListaIV() {
        return this.listaIV;
    }

    //Método construtor, método com mesmo nome da Classe, é o primeiro método a Compra
    //executado quando abrimos a tela.
    public ComprasCad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        vcontrole = CompraControle.getInstance();
        prodcontrole = ProdutoControle.getInstance();
        pjcontrole = PJControle.getInstance();
        montaTabela();
        montaComboItens();
        validaBotoes("inicio");
        icones();
        montaComboCli();
        campoVlTotal.setEditable(false);
        campoTotal1.setEditable(false);




    }

    //Monta a tabela com todas as Compras cadastradas no banco.
    public void montaTabela() {
        listaVe = vcontrole.listaTodos();
        //Crio um objeto Modelo padrão de tabela.
        DefaultTableModel modelo = new DefaultTableModel();
        //adiciono as colunas neste modelo.
        modelo.addColumn("ID");
        modelo.addColumn("Cliente");
        modelo.addColumn("Valor Total");
        //percorro a lista de Compras "listaVe" e para cada Compra da 
        //lista é adicionado uma linha no modelo.
        for (Compra ve : listaVe) {
            modelo.addRow(new Object[]{ve.getId(), ve.getFornecedor().getNome(), ve.getValorTotal()});
        }
        //adiciono este modelo criano na tabela da interface.
        tabela.setModel(modelo);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btSalvar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btNovo = new javax.swing.JButton();
        painelTabela = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        campoBuscar = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        btBuscar = new javax.swing.JToggleButton();
        painelMestre = new javax.swing.JTabbedPane();
        painelVenda = new javax.swing.JPanel();
        campoData = new com.toedter.calendar.JDateChooser();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        comboPessoa = new javax.swing.JComboBox();
        jLabel8 = new javax.swing.JLabel();
        campoCodigo = new javax.swing.JTextField();
        campoVlTotal = new controle.JMoneyField();
        painelDados = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tabelaItens = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        comboProdutos = new javax.swing.JComboBox();
        campoQuantidade = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        btAdicionar = new javax.swing.JButton();
        btRemover = new javax.swing.JButton();
        campoValorItem = new controle.JMoneyField();
        jInternalFrame1 = new javax.swing.JInternalFrame();
        jLabel1 = new javax.swing.JLabel();
        campoTotal1 = new controle.JMoneyField();
        lblEstoque = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        campoLucro = new controle.JMoneyField();
        jLabel10 = new javax.swing.JLabel();
        campoVenda = new controle.JMoneyField();
        btImprime = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("LANÇAMENTO DE COMPRAS");

        btSalvar.setToolTipText("Salva alterações");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btSair.setToolTipText("Fecha este formulário");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        btExcluir.setToolTipText("Remove o dado selecionado");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btAlterar.setToolTipText("Altera o registro selecionado");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btCancelar.setToolTipText("Cancela as alterações");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        btNovo.setToolTipText("Insere um novo registro");
        btNovo.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/add.png"))); // NOI18N
        btNovo.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/add.png"))); // NOI18N
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        painelTabela.setBorder(javax.swing.BorderFactory.createTitledBorder("Últimos Lançamentos"));

        tabela.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabela.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        jLabel11.setText("Pesquisa por id");

        btBuscar.setText("Buscar");
        btBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelTabelaLayout = new javax.swing.GroupLayout(painelTabela);
        painelTabela.setLayout(painelTabelaLayout);
        painelTabelaLayout.setHorizontalGroup(
            painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 516, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelTabelaLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addGap(89, 89, 89)
                        .addComponent(campoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btBuscar)))
                .addContainerGap(292, Short.MAX_VALUE))
        );
        painelTabelaLayout.setVerticalGroup(
            painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelTabelaLayout.createSequentialGroup()
                .addGroup(painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(btBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jLabel5.setText("Data da Compra");

        jLabel6.setText("Fornecedor");

        jLabel7.setText("Valor Total");

        comboPessoa.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboPessoa.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPessoaItemStateChanged(evt);
            }
        });

        jLabel8.setText("Código");

        campoCodigo.setEditable(false);

        campoVlTotal.setForeground(new java.awt.Color(0, 51, 102));
        campoVlTotal.setFont(new java.awt.Font("Tahoma", 1, 14));

        javax.swing.GroupLayout painelVendaLayout = new javax.swing.GroupLayout(painelVenda);
        painelVenda.setLayout(painelVendaLayout);
        painelVendaLayout.setHorizontalGroup(
            painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVendaLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoVlTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoData, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, 299, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(404, Short.MAX_VALUE))
        );
        painelVendaLayout.setVerticalGroup(
            painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelVendaLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(campoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(campoData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(comboPessoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelVendaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(campoVlTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(94, Short.MAX_VALUE))
        );

        painelMestre.addTab("Dados da Compra", painelVenda);

        painelDados.setEnabled(false);

        tabelaItens.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tabelaItens.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaItensMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tabelaItens);

        jLabel2.setText("Produto");

        comboProdutos.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        comboProdutos.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboProdutosItemStateChanged(evt);
            }
        });

        jLabel3.setText("Quantidade");

        jLabel4.setText("Custo Unitário");

        btAdicionar.setToolTipText("Adicionar produto");
        btAdicionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAdicionarActionPerformed(evt);
            }
        });

        btRemover.setToolTipText("Remover produto");
        btRemover.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btRemoverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jInternalFrame1Layout = new javax.swing.GroupLayout(jInternalFrame1.getContentPane());
        jInternalFrame1.getContentPane().setLayout(jInternalFrame1Layout);
        jInternalFrame1Layout.setHorizontalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jInternalFrame1Layout.setVerticalGroup(
            jInternalFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14));
        jLabel1.setText("Total: ");

        campoTotal1.setEditable(false);
        campoTotal1.setDisabledTextColor(new java.awt.Color(0, 0, 255));
        campoTotal1.setFont(new java.awt.Font("Tahoma", 1, 14));

        jLabel9.setText("Lucro (%)");

        campoLucro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoLucroFocusLost(evt);
            }
        });

        jLabel10.setText("Preço de Venda");

        campoVenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoVendaFocusLost(evt);
            }
        });

        javax.swing.GroupLayout painelDadosLayout = new javax.swing.GroupLayout(painelDados);
        painelDados.setLayout(painelDadosLayout);
        painelDadosLayout.setHorizontalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addGap(738, 738, 738)
                .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelDadosLayout.createSequentialGroup()
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelDadosLayout.createSequentialGroup()
                                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelDadosLayout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel4)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoValorItem, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jLabel9)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(campoLucro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelDadosLayout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(23, 23, 23)
                                        .addComponent(comboProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, 377, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(painelDadosLayout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel10)
                                        .addGap(18, 18, 18)
                                        .addComponent(campoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelDadosLayout.createSequentialGroup()
                                    .addGap(10, 10, 10)
                                    .addComponent(jScrollPane2))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelDadosLayout.createSequentialGroup()
                                    .addGap(446, 446, 446)
                                    .addComponent(jLabel1)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(campoTotal1, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(68, 68, 68))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(btAdicionar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btRemover))))
        );
        painelDadosLayout.setVerticalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jInternalFrame1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(comboProdutos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(lblEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(campoQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(campoValorItem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(campoLucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(campoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btRemover, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btAdicionar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(campoTotal1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(82, 82, 82))
        );

        painelMestre.addTab("Mercadorias", painelDados);

        btImprime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btImprimeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterar, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btImprime, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(painelMestre, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAlterar, btCancelar, btExcluir, btImprime, btNovo, btSair, btSalvar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(btSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                        .addComponent(btExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                        .addComponent(btCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
                        .addComponent(btNovo, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                        .addComponent(btAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btImprime, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelMestre, javax.swing.GroupLayout.PREFERRED_SIZE, 295, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {btAlterar, btCancelar, btExcluir, btImprime, btNovo, btSair, btSalvar});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Ação do botão salvar
    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        Integer atualiza;
        if (validaCampos() == true) {

            if (campoCodigo.getText().equals("")) {
                Compra.setId(null);
                atualiza = 0;
            } else {
                Compra.setId(Long.parseLong(campoCodigo.getText()));
                atualiza = 1;
            }
            Compra.setDataCompra(campoData.getDate());
            Compra.setFornecedor(listaJ.get(comboPessoa.getSelectedIndex()));
            String valor = campoVlTotal.getText().replace(".", "");
            valor = valor.replace(",", ".");
            Compra.setValorTotal(new BigDecimal(valor));
            vcontrole.salvar(Compra, atualiza);
            int i = (JOptionPane.showConfirmDialog(null, "Deseja imprimir esta Compra?",
                    "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null));

            if (i == 0) {
                geraCompra(Compra.getId());
            }
            limpaCampos();
            //Chama o método montaTabela para atualizar a tabela com o novo registro ou com o regsitro alterado.
            montaTabela();
            //Chama o método validaBotões com ação inicio.
            validaBotoes("inicio");
    }//GEN-LAST:event_btSalvarActionPerformed
    }
    //Ação do botão Sair.
    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        //Fecha a tela.
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btSairActionPerformed

    //Ação do Botão Excluir.
    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed

        //Recupero a Compra selecionada na tabela e atribuo no objeto global Compra.
        setCompra(listaVe.get(tabela.getSelectedRow()));
        vcontrole.deletar(Compra);
        limpaCampos();
        montaTabela();

        validaBotoes("inicio");
        // TODO add your handling code here:
    }//GEN-LAST:event_btExcluirActionPerformed

    //Ação do botão alterar.
    private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
        //Chamo o ValidaBotões com a ação alterar.
        validaBotoes("alterar");
    }//GEN-LAST:event_btAlterarActionPerformed

    //Ação do botão Cancelar.
    private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
        limpaCampos();
        validaBotoes("inicio");
        // TODO add your handling code here:
    }//GEN-LAST:event_btCancelarActionPerformed

    //Limpa os campos da interface
    public void limpaCampos() {
        campoData.setDate(null);
        campoCodigo.setText("");
        campoQuantidade.setText("");
        campoTotal1.setText("");
        campoVlTotal.setText("");
        campoValorItem.setText("");


    }

    //Ação do botão Novo.
    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed

        Compra = new Compra();
        limpaCampos();
        validaBotoes("novo");
        montaTabelaItens();
        montaComboCli();


    }//GEN-LAST:event_btNovoActionPerformed

    //Ação ao clicar em um registro na tabela.
    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        validaBotoes("selecionado");

        Compra = listaVe.get(tabela.getSelectedRow());
        campoCodigo.setText(String.valueOf(Compra.getId()));
        comboPessoa.setSelectedItem(Compra.getFornecedor().getId());
        campoData.setDate(Compra.getDataCompra());
        montaTabelaItens();
        campoVlTotal.setText(String.valueOf(Compra.getValorTotal().setScale(2, RoundingMode.UP)));
        campoLucro.setText("");
        campoVenda.setText("");
        
    }//GEN-LAST:event_tabelaMouseClicked

private void btAdicionarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAdicionarActionPerformed

    ItensCompra ic = new ItensCompra();
    Produto p = listaProd.get(comboProdutos.getSelectedIndex());
    Integer qtd = Integer.parseInt(campoQuantidade.getText());
    ic.setProduto(listaProd.get(comboProdutos.getSelectedIndex()));
    ic.setQuantidade(Integer.parseInt(campoQuantidade.getText()));
    String valor = campoValorItem.getText().replace(".", "");
    valor = valor.replace(",", ".");
    String lucro = campoLucro.getText().replace(".", "");
    lucro = lucro.replace(",", ".");
    String venda = campoVenda.getText().replace(".", "");
    venda = venda.replace(",", ".");
    ic.setValorProd(new BigDecimal(valor));
    ic.setValorLucro(new BigDecimal(lucro));
    ic.setValorVendaProd(new BigDecimal(venda));
    ic.setSubTotal((new BigDecimal(valor).multiply(new BigDecimal(campoQuantidade.getText()))));
    Compra.getItensCompra().add(ic);
    lblEstoque.setText(null);
    montaTabelaItens();
    calculaTotal(ic);

    // TODO add your handling code here:
}//GEN-LAST:event_btAdicionarActionPerformed

private void comboProdutosItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboProdutosItemStateChanged
    try {
        Produto produto = listaProd.get(comboProdutos.getSelectedIndex());
        campoValorItem.setText(String.valueOf((produto.getCusto())));
        campoLucro.setText(String.valueOf((produto.getLucro())));
        campoVenda.setText(String.valueOf((produto.getPreco())));
        lblEstoque.setVisible(true);
        lblEstoque.setText(String.valueOf(produto.getEstoque()));
    } catch (Exception e) {
        campoValorItem.setText("0,00");
    }


    // TODO add your handling code here:
}//GEN-LAST:event_comboProdutosItemStateChanged

private void comboPessoaItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPessoaItemStateChanged
}//GEN-LAST:event_comboPessoaItemStateChanged

private void btRemoverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btRemoverActionPerformed
    Compra.getItensCompra().remove(tabelaItens.getSelectedRow());
    montaTabelaItens();
    calculaCompra();

}//GEN-LAST:event_btRemoverActionPerformed

    public void calculaCompra() {
        BigDecimal valor = new BigDecimal("0");
        for (Iterator<ItensCompra> it = Compra.getItensCompra().iterator(); it.hasNext();) {
            ItensCompra itens = it.next();
            valor = valor.add(itens.getSubTotal());
        }
        campoVlTotal.setText(String.valueOf(valor));
        campoTotal1.setText(String.valueOf(valor));
    }

private void tabelaItensMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaItensMouseClicked
}//GEN-LAST:event_tabelaItensMouseClicked

private void campoLucroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoLucroFocusLost
  String ccusto = campoValorItem.getText().replace(".", "");
    ccusto = ccusto.replace(",", ".");
    String clucro = campoLucro.getText().replace(".", "");
    clucro = clucro.replace(",", ".");

    Double custo = Double.parseDouble(ccusto);
    Double lucro = Double.parseDouble(clucro);

    Double dvenda = (custo * lucro) / 100;

    dvenda = dvenda + custo;
    BigDecimal avenda = new BigDecimal(dvenda).setScale(2, BigDecimal.ROUND_UP);

    campoVenda.setText(avenda.toEngineeringString());
}//GEN-LAST:event_campoLucroFocusLost

private void campoVendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoVendaFocusLost
  String ccusto = campoValorItem.getText().replace(".", "");
    ccusto = ccusto.replace(",", ".");
    String cvenda = campoVenda.getText().replace(".", "");
    cvenda = cvenda.replace(",", ".");

    Double custo = Double.parseDouble(ccusto);
    Double venda = Double.parseDouble(cvenda);

    Double dlucro = (venda - custo);
    dlucro = (dlucro / custo) * 100;

    BigDecimal alucro = new BigDecimal(dlucro).setScale(2, BigDecimal.ROUND_UP);

    campoLucro.setText(alucro.toEngineeringString());  
}//GEN-LAST:event_campoVendaFocusLost

private void btBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btBuscarActionPerformed
    Long id = Long.parseLong(campoBuscar.getText());
    listaVe = vcontrole.listaID(id, 0);

    DefaultTableModel modelo = new DefaultTableModel();
    //adiciono as colunas neste modelo.
    modelo.addColumn("ID");
    modelo.addColumn("Cliente");
    modelo.addColumn("Valor Total");
    //percorro a lista de vendas "listaVe" e para cada venda da 
    //lista é adicionado uma linha no modelo.
    for (Compra ve : listaVe) {
        modelo.addRow(new Object[]{ve.getId(), ve.getFornecedor().getNome(), ve.getValorTotal()});
    }
    //adiciono este modelo criano na tabela da interface.
    tabela.setModel(modelo);
}//GEN-LAST:event_btBuscarActionPerformed

private void btImprimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btImprimeActionPerformed
    geraCompra(Compra.getId());
}//GEN-LAST:event_btImprimeActionPerformed
    private void calculaTotal(ItensCompra ic) {
        String valor1 = campoVlTotal.getText().replace(".", "");
        valor1 = valor1.replace(",", ".");
        BigDecimal valorTotal = new BigDecimal(valor1);
        valorTotal = valorTotal.add(ic.getSubTotal());
        campoVlTotal.setText(String.valueOf(valorTotal));
        campoTotal1.setText(String.valueOf(valorTotal));
        limpaCamposItens();
    }

    private void montaComboItens() {
        comboProdutos.removeAllItems();
        listaProd = prodcontrole.listaTodos();
        for (Produto p : listaProd) {
            comboProdutos.addItem(p.getDescricao());
        }

    }

    public boolean isCellEditable(int rowIndex, int mColIndex) {
        return false;
    }

    private void limpaCamposItens() {
        comboProdutos.setSelectedItem(null);
        campoQuantidade.setText("");
        campoValorItem.setText("");
    }

    private void montaTabelaItens() {

        DefaultTableModel modelo = new DefaultTableModel();

        modelo.addColumn("ID");
        modelo.addColumn("Produto");
        modelo.addColumn("Quantidade");
        modelo.addColumn("Valor");
        modelo.addColumn("SubTotal");

        //percorro a lista de Servicos "listaVe" e para cada Servico da 

        for (ItensCompra ic : Compra.getItensCompra()) {
            modelo.addRow(new Object[]{ic.getId(), ic.getProduto().getDescricao(), ic.getQuantidade(), ic.getValorProd(), ic.getSubTotal()});
        }
        //adiciono este modelo criano na tabela da interface.
        tabelaItens.setModel(modelo);
        tabelaItens.isCellEditable(0, 0);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                final ComprasCad dialog = new ComprasCad(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    public void windowClosing(java.awt.event.WindowEvent e) {
                        dialog.dispose();
                    }
                });
                dialog.setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btAdicionar;
    private javax.swing.JButton btAlterar;
    private javax.swing.JToggleButton btBuscar;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btImprime;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btRemover;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btSalvar;
    private javax.swing.JTextField campoBuscar;
    private javax.swing.JTextField campoCodigo;
    private com.toedter.calendar.JDateChooser campoData;
    private controle.JMoneyField campoLucro;
    private javax.swing.JTextField campoQuantidade;
    private controle.JMoneyField campoTotal1;
    private controle.JMoneyField campoValorItem;
    private controle.JMoneyField campoVenda;
    private controle.JMoneyField campoVlTotal;
    private javax.swing.JComboBox comboPessoa;
    private javax.swing.JComboBox comboProdutos;
    private javax.swing.JInternalFrame jInternalFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblEstoque;
    private javax.swing.JPanel painelDados;
    private javax.swing.JTabbedPane painelMestre;
    private javax.swing.JPanel painelTabela;
    private javax.swing.JPanel painelVenda;
    private javax.swing.JTable tabela;
    private javax.swing.JTable tabelaItens;
    // End of variables declaration//GEN-END:variables
}
