/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ClienteCad.java
 *
 * Created on 05/10/2011, 21:12:36
 */
package visao;

import modelo.Cidade;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import controle.CidadeControle;
import controle.PControle;
import controle.PFControle;
import controle.PJControle;
import modelo.PessoaF;
import modelo.PessoaJ;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author aluno
 */
public class PessoaCad extends javax.swing.JDialog {

    private List<Cidade> listaCid = new ArrayList<Cidade>();
    private List<PessoaF> listaPF = new ArrayList<PessoaF>();
    private List<PessoaJ> listaPJ = new ArrayList<PessoaJ>();
    private PessoaF pf;
    private PessoaJ pj;
    private CidadeControle cidcontrole;
    private PFControle pfControle;
    private PJControle pjControle;
    public int i;
    public int a;
    //-------- Valida CPF ou CNPJ
    public PControle pcontrole;
    private PControle pControle;

    private DefaultFormatterFactory formato(String mask) {
        MaskFormatter comFoco = null, withoutFocus = null;
        try {
            comFoco = new MaskFormatter(mask);
        } catch (Exception pe) {
        }
        DefaultFormatterFactory factory = new DefaultFormatterFactory(comFoco, comFoco);
        return factory;
    }

    public void validaBotoes(String acao) {
        if (acao.equals("inicio")) {
            //setEnabled habilita ou desabilita o botão ou tabela.
            btNovo.setEnabled(true);
            btSalvar.setEnabled(false);
            btExcluir.setEnabled(false);
            btAlterar.setEnabled(false);
            btCancelar.setEnabled(false);
            btSair.setEnabled(true);
            campoNome.setEditable(false);
            comboCidade.setEnabled(false);
            campoDoc3.setEditable(false);
            campoDoc2.setEditable(false);
            campoEndereco.setEditable(false);
            campoCelular.setEditable(false);
            campoCep.setEditable(false);
            campoEmail2.setEditable(false);
            campoTelefone.setEditable(false);
            comboCidade.setEnabled(false);
            painelTabela.setEnabled(false);
            fFiltro.setEnabled(true);
            tabela.setEnabled(true);
            painelDados.setVisible(false);
            btInicia.setEnabled(false);
            comboTipo.setEnabled(true);
            comboPFPJ.setEnabled(false);
        } else if (acao.equals("novo") || acao.equals("alterar")) {
            btNovo.setEnabled(false);
            btSalvar.setEnabled(true);
            btExcluir.setEnabled(false);
            btAlterar.setEnabled(false);
            btCancelar.setEnabled(true);
            btSair.setEnabled(false);
            campoNome.setEditable(true);
            comboCidade.setEnabled(true);
            campoDoc3.setEditable(true);
            campoDoc2.setEditable(true);
            campoEndereco.setEditable(true);
            campoCelular.setEditable(true);
            campoCep.setEditable(true);
            campoEmail2.setEditable(true);
            campoTelefone.setEditable(true);
            painelTabela.setEnabled(false);
            tabela.setEnabled(false);
            fFiltro.setEnabled(false);
        } else if (acao.equals("selecionado")) {

            btNovo.setEnabled(true);
            btSalvar.setEnabled(false);
            btExcluir.setEnabled(true);
            btAlterar.setEnabled(true);
            btCancelar.setEnabled(true);
            btSair.setEnabled(true);
            campoNome.setEditable(false);
            comboCidade.setEnabled(false);
            campoDoc3.setEditable(false);
            campoDoc2.setEditable(false);
            campoEndereco.setEditable(false);
            campoCelular.setEditable(false);
            campoCep.setEditable(false);
            campoEmail2.setEditable(false);
            campoTelefone.setEditable(false);
            painelTabela.setEnabled(true);
            tabela.setEnabled(true);
            fFiltro.setEnabled(true);
        }
    }

    //Valida os campos obrigatórios de um cadastro, 
    //retornando um boolean true quando verdadeiro ou false quando tiver algum campo em branco.
    public Boolean validaCampos() {
        if (campoNome.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo nome/razão é obrigatório!");
            campoNome.grabFocus();
            return false;
        } else if (comboCidade.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Selecione a cidade");
            return false;
        } else if (campoEndereco.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo endereço é Obrigatório!");
            campoEndereco.grabFocus();
            return false;
        } else if (campoDoc3.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo CPF/CNPJ é Obrigatório!");
            campoDoc3.grabFocus();
            return false;
        } else if (campoDoc2.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "O campo RG/Inscrição é Obrigatório!");
            campoDoc2.grabFocus();
            return false;
        } else {
            return true;
        }
    }

    public void limpaCampos() {
        campoCelular.setText("");
        campoCep.setText("");
        campoData.setDate(null);
        campoDoc3.setText("");
        campoDoc2.setText("");
        campoEmail2.setText("");
        campoNome.setText("");
        campoEndereco.setText("");
        campoTelefone.setText("");
        comboCidade.setSelectedItem(null);
        campoCodigo.setText("");
        comboCidade.setSelectedItem(null);
    }

    // Monta uma combo com seus itens.
    public void montaCombo() {
        comboCidade.removeAllItems();
        listaCid = cidcontrole.listaTodos();
        for (Cidade cid : listaCid) {
            comboCidade.addItem(cid.getNome() + " - " + cid.getEstado().getSigla());
        }
    }
    //Monta a tabela com todas as cidades cadastradas no banco.

    public void montaTabela() {
            listaPF = pfControle.listaFiltrando(fFiltro.getText(), comboTipo.getSelectedIndex());
            listaPJ = pjControle.listaFiltrando(fFiltro.getText(), comboTipo.getSelectedIndex());
        
        DefaultTableModel modeloPF = new DefaultTableModel();
        modeloPF.addColumn("ID");
        modeloPF.addColumn("Nome");
        modeloPF.addColumn("CPF");
        modeloPF.addColumn("RG");        
        modeloPF.addColumn("Telefone");
      
        DefaultTableModel modeloPJ = new DefaultTableModel();
        modeloPJ.addColumn("ID");
        modeloPJ.addColumn("Razão");
        modeloPJ.addColumn("CNPJ");
        modeloPF.addColumn("Inscricao Estadual");        
        modeloPJ.addColumn("Telefone");
       
        for (PessoaF pf : listaPF) {
            modeloPF.addRow(new Object[]{pf.getId(), pf.getNome(), pf.getCpf(), pf.getRg(), pf.getTelefone()});
        }
        for (PessoaJ pj : listaPJ) {

            modeloPJ.addRow(new Object[]{pj.getId(), pj.getNome(), pj.getCnpj(), pj.getInscricao(), pj.getTelefone()});

        }

        if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
            tabela.setModel(modeloPF);
        } else if (comboPesquisaPFPJ.getSelectedIndex() == 1) {
            tabela.setModel(modeloPJ);
        } else {
            JOptionPane.showMessageDialog(null, "erro!");
        }

    
    }
    public PessoaCad() {
    }

    public PessoaCad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        icones();
        cidcontrole = CidadeControle.getInstance();
        pjControle = PJControle.getInstance();
        pfControle = PFControle.getInstance();
        pControle = PControle.getInstance();
        painelDados.setVisible(false);
        comboPFPJ.setEnabled(false);
        btInicia.setEnabled(false);
        montaTabela();
        validaBotoes("inicio");
        campoCelular.setFormatterFactory(formato("##-####-####"));
        campoTelefone.setFormatterFactory(formato("##-####-####"));
        campoCep.setFormatterFactory(formato("##.###-###"));

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        painelDados = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        rotuloNome = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        rotuloData = new javax.swing.JLabel();
        rotuloDoc1 = new javax.swing.JLabel();
        rotuloDoc2 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        campoCodigo = new javax.swing.JTextField();
        campoNome = new javax.swing.JTextField();
        campoEndereco = new javax.swing.JTextField();
        comboCidade = new javax.swing.JComboBox();
        campoDoc2 = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        campoEmail2 = new javax.swing.JTextField();
        campoDoc3 = new javax.swing.JFormattedTextField();
        campoCep = new javax.swing.JFormattedTextField();
        campoTelefone = new javax.swing.JFormattedTextField();
        campoCelular = new javax.swing.JFormattedTextField();
        campoData = new com.toedter.calendar.JDateChooser();
        btInicia = new javax.swing.JButton();
        btNovo = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        comboPFPJ = new javax.swing.JComboBox();
        paineiPesquisa = new javax.swing.JPanel();
        painelTabela = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        fFiltro = new javax.swing.JTextField();
        comboTipo = new javax.swing.JComboBox();
        comboPesquisaPFPJ = new javax.swing.JComboBox();
        btFiltra = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GESTÃO DE PESSOAS");

        painelDados.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));
        painelDados.setFont(new java.awt.Font("Tahoma", 1, 11));

        jLabel1.setText("ID");

        rotuloNome.setText("Nome");

        jLabel3.setText("Endereco");

        jLabel4.setText("Cidade");

        jLabel5.setText("Cep");

        jLabel6.setText("Telefone");

        jLabel7.setText("Celular");

        rotuloData.setText("Data");

        rotuloDoc1.setText("Doc1");

        rotuloDoc2.setText("Doc2");

        jLabel11.setText("email");

        campoCodigo.setEditable(false);

        comboCidade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        campoDoc3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoDoc3FocusLost(evt);
            }
        });

        javax.swing.GroupLayout painelDadosLayout = new javax.swing.GroupLayout(painelDados);
        painelDados.setLayout(painelDadosLayout);
        painelDadosLayout.setHorizontalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rotuloNome)
                            .addComponent(jLabel1)
                            .addComponent(campoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(campoEndereco, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(campoNome, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelDadosLayout.createSequentialGroup()
                                .addGap(5, 5, 5)
                                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(painelDadosLayout.createSequentialGroup()
                                        .addComponent(jLabel4)
                                        .addGap(153, 153, 153)
                                        .addComponent(jLabel5))
                                    .addGroup(painelDadosLayout.createSequentialGroup()
                                        .addGap(220, 220, 220)
                                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(5, 5, 5)
                                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(painelDadosLayout.createSequentialGroup()
                                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(campoDoc3, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(rotuloDoc1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rotuloDoc2)
                                            .addComponent(campoDoc2, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)))))
                            .addGroup(painelDadosLayout.createSequentialGroup()
                                .addComponent(comboCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(campoCep, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(460, 460, 460))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6)
                            .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelDadosLayout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addGap(105, 105, 105)
                                .addComponent(rotuloData))
                            .addGroup(painelDadosLayout.createSequentialGroup()
                                .addGap(2, 2, 2)
                                .addComponent(campoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(campoData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(353, Short.MAX_VALUE))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addContainerGap(684, Short.MAX_VALUE))
                    .addComponent(campoEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        painelDadosLayout.setVerticalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(painelDadosLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelDadosLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(painelDadosLayout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rotuloNome))
                    .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(rotuloDoc1)
                        .addComponent(rotuloDoc2)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDoc3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDoc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(comboCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(campoCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(campoEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(jLabel7)
                    .addComponent(rotuloData))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(campoData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btInicia.setText("Inicia");
        btInicia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btIniciaActionPerformed(evt);
            }
        });

        btNovo.setToolTipText("Insere um registro");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        btSalvar.setToolTipText("Salva alterações");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btExcluir.setToolTipText("Remove o registro");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btAlterar.setToolTipText("Altera o registro");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btCancelar.setToolTipText("Cancela edição");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        btSair.setToolTipText("Fecha o formulário");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        comboPFPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Pessoa Física", "Pessoa Jurídica" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(btNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(506, 506, 506))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addComponent(comboPFPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btInicia))))
                    .addComponent(painelDados, javax.swing.GroupLayout.PREFERRED_SIZE, 730, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAlterar, btCancelar, btExcluir, btNovo, btSair, btSalvar});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btSair, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(btExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(btSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
                    .addComponent(btNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(comboPFPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btInicia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(painelDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        painelTabela.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabela"));

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
        tabela.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                tabelaMouseEntered(evt);
            }
        });
        jScrollPane1.setViewportView(tabela);

        javax.swing.GroupLayout painelTabelaLayout = new javax.swing.GroupLayout(painelTabela);
        painelTabela.setLayout(painelTabelaLayout);
        painelTabelaLayout.setHorizontalGroup(
            painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 604, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(85, Short.MAX_VALUE))
        );
        painelTabelaLayout.setVerticalGroup(
            painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTabelaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setText("Pesquisa");

        fFiltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fFiltroKeyReleased(evt);
            }
        });

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "RG/Inscrição Estadual", "CPF/CNPJ" }));
        comboTipo.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboTipoItemStateChanged(evt);
            }
        });

        comboPesquisaPFPJ.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "PF", "PJ" }));
        comboPesquisaPFPJ.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboPesquisaPFPJItemStateChanged(evt);
            }
        });

        btFiltra.setText("Filtra");
        btFiltra.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btFiltraActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout paineiPesquisaLayout = new javax.swing.GroupLayout(paineiPesquisa);
        paineiPesquisa.setLayout(paineiPesquisaLayout);
        paineiPesquisaLayout.setHorizontalGroup(
            paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paineiPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paineiPesquisaLayout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboPesquisaPFPJ, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(113, 113, 113)
                        .addComponent(btFiltra))
                    .addComponent(painelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        paineiPesquisaLayout.setVerticalGroup(
            paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paineiPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFiltra)
                    .addComponent(comboPesquisaPFPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(fFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(paineiPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 657, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(97, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paineiPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(38, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed
    comboPFPJ.setEnabled(true);
    btInicia.setEnabled(true);
    btNovo.setEnabled(false);
    btSalvar.setEnabled(false);
    btExcluir.setEnabled(false);
    btAlterar.setEnabled(false);
    btCancelar.setEnabled(true);
    btSair.setEnabled(false);
    painelDados.setVisible(false);
    limpaCampos();

}//GEN-LAST:event_btNovoActionPerformed

private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
    int a = (JOptionPane.showConfirmDialog(null, "Deseja exluir o registro?",
            "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null));

    if (i == 0) {
        if (a == 0) {
            setPf(listaPF.get(tabela.getSelectedRow()));
            pfControle.delete(pf);
            montaTabela();
            validaBotoes("inicio");

        } else if (a == 1) {
            limpaCampos();
            validaBotoes("inicio");
        } else {
            JOptionPane.showMessageDialog(null, "Erro crítico");
            limpaCampos();
            validaBotoes("inicio");

        }
    } else if (i == 1) {
        if (a == 0) {
            setPj(listaPJ.get(tabela.getSelectedRow()));
            pjControle.delete(pj);
            montaTabela();
            validaBotoes("inicio");

        } else if (a == 1) {
            limpaCampos();
            validaBotoes("inicio");
        } else {
            JOptionPane.showMessageDialog(null, "Erro crítico");
            limpaCampos();
            validaBotoes("inicio");

        }



    }
}//GEN-LAST:event_btExcluirActionPerformed

private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
    //Chamo o ValidaBotões com a ação alterar.
    if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 0;
    } else if (comboPesquisaPFPJ.getSelectedIndex() == 1) {
        i = 1;
    }
    validaBotoes("alterar");
}//GEN-LAST:event_btAlterarActionPerformed

private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
    limpaCampos();
    painelDados.setVisible(false);
    comboPFPJ.setEnabled(false);
    btInicia.setEnabled(false);

    validaBotoes("inicio");
    // TODO add your handling code here:
}//GEN-LAST:event_btCancelarActionPerformed

private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
    //Fecha a tela.
    this.dispose();
    // TODO add your handling code here:
}//GEN-LAST:event_btSairActionPerformed

private void btIniciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btIniciaActionPerformed

    if (comboPFPJ.getSelectedIndex() == 0) {
        i = 0;
        limpaCampos();
        campoDoc3.setFormatterFactory(formato("###.###.###-##"));
        painelDados.setVisible(true);
        rotuloData.setText("Aniversário");
        rotuloDoc1.setText("CPF");
        rotuloDoc2.setText("RG");
        validaBotoes("novo");
        montaCombo();
        montaTabela();
        campoNome.grabFocus();

    } else if (comboPFPJ.getSelectedIndex() == 1) {
        i = 1;
        limpaCampos();
        campoDoc3.setFormatterFactory(formato("##.###.###.####-##"));
        painelDados.setVisible(true);
        rotuloData.setText("Constituição");
        rotuloDoc1.setText("CNPJ");
        rotuloDoc2.setText("Inscrição Estadual");
        rotuloNome.setText("Razão Social");
        validaBotoes("novo");
        montaCombo();
        montaTabela();
        campoNome.grabFocus();
    } else {
        JOptionPane.showMessageDialog(null, "erro!" + comboPFPJ.getSelectedIndex());
    }
}//GEN-LAST:event_btIniciaActionPerformed

private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
    if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 0;
        campoDoc3.setFormatterFactory(formato("###.###.###-##"));
        pf = listaPF.get(tabela.getSelectedRow());
        campoCodigo.setText(String.valueOf(pf.getId()));
        campoNome.setText(pf.getNome());
        campoTelefone.setText(pf.getTelefone());
        campoCelular.setText(pf.getCelular());
        campoCep.setText(pf.getCep());
        campoDoc3.setText(pf.getCpf());
        campoDoc2.setText(pf.getRg());
        campoData.setDate(pf.getDataPes());
        campoEndereco.setText(pf.getEndereco());
        campoEmail2.setText(pf.getEmail());
        comboCidade.setSelectedItem(pf.getCidade().getNome());
        validaBotoes("selecionado");
        montaCombo();

        painelDados.setVisible(true);



    } else if (comboPesquisaPFPJ.getSelectedIndex() == 1) {
        i = 1;
        campoDoc3.setFormatterFactory(formato("##.###.###.####-##"));
        pj = listaPJ.get(tabela.getSelectedRow());
        campoCodigo.setText(String.valueOf(pj.getId()));
        campoNome.setText(pj.getNome());
        comboCidade.setSelectedItem(pj.getCidade().getNome());
        campoEndereco.setText(pj.getEndereco());
        campoTelefone.setText(pj.getTelefone());
        campoCelular.setText(pj.getCelular());
        campoCep.setText(pj.getCep());
        campoDoc3.setText(pj.getCnpj());
        campoDoc2.setText(pj.getInscricao());
        campoData.setDate(pj.getDataPes());
        campoEmail2.setText(pj.getEmail());
        validaBotoes("selecionado");
        montaCombo();
        painelDados.setVisible(true);



    } else {
        JOptionPane.showMessageDialog(null, "Filtro inválido");
        comboPesquisaPFPJ.grabFocus();
    }
}//GEN-LAST:event_tabelaMouseClicked

private void btFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFiltraActionPerformed

    if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 0;


    } else if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 1;


    }

    montaTabela();
}//GEN-LAST:event_btFiltraActionPerformed
private void fFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fFiltroKeyReleased

    if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 0;
        if (comboTipo.getSelectedIndex() == 0) {
            a = 0;
        } else {
            a = 1;
        }

    } else if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 1;
        if (comboTipo.getSelectedIndex() == 0) {
            a = 0;
        } else {
            a = 1;
        }

    }

    montaTabela();
}//GEN-LAST:event_fFiltroKeyReleased
private void comboPesquisaPFPJItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPesquisaPFPJItemStateChanged
    if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 0;


    } else if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 1;


    }

    montaTabela();

}//GEN-LAST:event_comboPesquisaPFPJItemStateChanged
private void comboTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTipoItemStateChanged

    if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 0;


    } else if (comboPesquisaPFPJ.getSelectedIndex() == 0) {
        i = 1;


    }

    montaTabela();
}//GEN-LAST:event_comboTipoItemStateChanged

private void campoDoc3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoDoc3FocusLost
}//GEN-LAST:event_campoDoc3FocusLost

private void tabelaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseEntered
    // TODO add your handling code here:
}//GEN-LAST:event_tabelaMouseEntered

private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
    if (validaCampos() == true) {
        if (i == 0) {
            PessoaF pf = new PessoaF();
            String cpcpf = campoDoc3.getText();
            cpcpf = cpcpf.replace(".", "");
            cpcpf = cpcpf.replace("-", "");

            if (pcontrole.valida_CpfCnpj(cpcpf) == false) {
                JOptionPane.showMessageDialog(null, "CPF Inválido");
                campoDoc3.grabFocus();
            } else {

                if (campoCodigo.getText().equals("")) {
                    pf.setId(null);
                } else {
                    pf.setId(Long.parseLong(campoCodigo.getText()));
                }

                pf.setNome(campoNome.getText());
                pf.setEndereco(campoEndereco.getText());
                String cpf = campoDoc3.getText();
                cpf = cpf.replace(".", "");
                cpf = cpf.replace("-", "");
                pf.setCpf(cpf);
                pf.setRg(campoDoc2.getText());
                pf.setEmail(campoEmail2.getText());
                pf.setCelular(campoCelular.getText().replace("-", ""));
                pf.setCidade(listaCid.get(comboCidade.getSelectedIndex()));
                pf.setTelefone(campoTelefone.getText().replace("-", ""));
                String cep = campoCep.getText();
                cep = cep.replace("-", "");
                cep = cep.replace(".", "");
                pf.setCep(cep);
                pf.setDataPes(campoData.getDate());
                pfControle.salvar(pf);


               

            }
                limpaCampos();
                montaTabela();
                validaBotoes("inicio");
                campoDoc3.setFormatterFactory(null);


        } else if (i == 1) {
            PessoaJ pj = new PessoaJ();
            String cnpj = campoDoc3.getText();
            cnpj = cnpj.replace(".", "");
            cnpj = cnpj.replace("-", "");
            cnpj = cnpj.replace("/", "");
            if (pcontrole.valida_CpfCnpj(cnpj) == false) {
                JOptionPane.showMessageDialog(null, "CPF Inválido");
                campoDoc3.grabFocus();
            } else {
                if (campoCodigo.getText().equals("")) {
                    pj.setId(null);
                } else {
                    pj.setId(Long.parseLong(campoCodigo.getText()));
                }
                pj.setNome(campoNome.getText());
                pj.setEndereco(campoEndereco.getText());
                pj.setCnpj(cnpj);
                pj.setInscricao(campoDoc2.getText());
                pj.setEmail(campoEmail2.getText());
                pj.setDataPes(campoData.getDate());
                pj.setCelular(campoCelular.getText().replace("-", ""));
                pj.setCidade(listaCid.get(comboCidade.getSelectedIndex()));
                pj.setTelefone(campoTelefone.getText().replace("-", ""));
                String cep = campoCep.getText();
                cep = cep.replace("-", "");
                cep = cep.replace(".", "");
                pj.setCep(cep);


                pjControle.salvar(pj);

                limpaCampos();
                montaTabela();
                validaBotoes("inicio");


            }
        }
    }

}//GEN-LAST:event_btSalvarActionPerformed

    public List<PessoaF> getListaPF() {
        return listaPF;


    }

    public void setListaPF(List<PessoaF> listaPF) {
        this.listaPF = listaPF;


    }

    public List<PessoaJ> getListaPJ() {
        return listaPJ;


    }

    public void setListaPJ(List<PessoaJ> listaPJ) {
        this.listaPJ = listaPJ;


    }

    public PessoaF getPf() {
        return pf;


    }

    public void setPf(PessoaF pf) {
        this.pf = pf;


    }

    public PessoaJ getPj() {
        return pj;


    }

    public void setPj(PessoaJ pj) {
        this.pj = pj;


    }

    public void icones() {
        btNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/add.png")));
        btAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/altera.png")));
        btCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/deleteazul.png")));
        btExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/delete.png")));
        btSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/door_in.png")));
        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/accept.png")));

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                final PessoaCad dialog = new PessoaCad(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btAlterar;
    private javax.swing.JButton btCancelar;
    private javax.swing.JButton btExcluir;
    private javax.swing.JButton btFiltra;
    private javax.swing.JButton btInicia;
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFormattedTextField campoCelular;
    private javax.swing.JFormattedTextField campoCep;
    private javax.swing.JTextField campoCodigo;
    private com.toedter.calendar.JDateChooser campoData;
    private javax.swing.JTextField campoDoc2;
    private javax.swing.JFormattedTextField campoDoc3;
    private javax.swing.JTextField campoEmail2;
    private javax.swing.JTextField campoEndereco;
    private javax.swing.JTextField campoNome;
    private javax.swing.JFormattedTextField campoTelefone;
    private javax.swing.JComboBox comboCidade;
    private javax.swing.JComboBox comboPFPJ;
    private javax.swing.JComboBox comboPesquisaPFPJ;
    private javax.swing.JComboBox comboTipo;
    private javax.swing.JTextField fFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPanel paineiPesquisa;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelTabela;
    private javax.swing.JLabel rotuloData;
    private javax.swing.JLabel rotuloDoc1;
    private javax.swing.JLabel rotuloDoc2;
    private javax.swing.JLabel rotuloNome;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
