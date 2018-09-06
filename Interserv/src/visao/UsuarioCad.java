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

import java.awt.Dialog;
import modelo.Cidade;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import controle.CidadeControle;
import controle.PControle;
import controle.PFControle;
import controle.PJControle;
import controle.UsuarioControle;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import modelo.Usuario;

/**
 *
 * @author aluno
 */
public class UsuarioCad extends javax.swing.JDialog {

    private List<Cidade> listaCid = new ArrayList<Cidade>();
    private List<Usuario> listaUser = new ArrayList<Usuario>();
    private CidadeControle cidcontrole;
    private UsuarioControle uControle;
    private Usuario user;
    //-------- Valida CPF ou CNPJ

    public PControle pcontrole;

    public UsuarioCad(Dialog owner) {
        super(owner);
    }

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
            //setEditable habilita ou desabilita os campos.
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
            comboTipo.setEnabled(false);
            campoSenha.setEnabled(false);
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
            campoSenha.setEnabled(true);
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
            campoSenha.setEnabled(false);
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
        }else if (campoSenha.getText().equals("")){
            JOptionPane.showMessageDialog(null,"O usuario deve cadastrar uma senha!");
            campoSenha.grabFocus();
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
        campoSenha.setText("");

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
        listaUser = uControle.listaFiltrando(fFiltro.getText(), comboTipo.getSelectedIndex());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Nome");
        modelo.addColumn("CPF");
        modelo.addColumn("Telefone");
        modelo.addColumn("Celular");
        modelo.addColumn("email");
        for (Usuario f : listaUser) {
            modelo.addRow(new Object[]{f.getId(), f.getNome(), f.getCpf(), f.getTelefone(), f.getCelular(), f.getEmail()});
        }

        tabela.setModel(modelo);
    }

    public UsuarioCad() {
    }

    public UsuarioCad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        cidcontrole = CidadeControle.getInstance();
        uControle = UsuarioControle.getInstance();
        painelDados.setVisible(false);
        montaTabela();
        validaBotoes("inicio");
        icones();
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
        campoSenha = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        btNovo = new javax.swing.JButton();
        btSalvar = new javax.swing.JButton();
        btExcluir = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
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
        setTitle("GESTÃO DE USUÁRIOS");

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

        rotuloDoc1.setText("CPF");

        rotuloDoc2.setText("RG");

        jLabel11.setText("email");

        campoCodigo.setEditable(false);

        comboCidade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        try {
            campoDoc3.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        campoDoc3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoDoc3FocusLost(evt);
            }
        });

        jLabel8.setText("Senha");

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
                        .addContainerGap(806, Short.MAX_VALUE))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(campoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(625, Short.MAX_VALUE))))
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
                        .addComponent(jLabel11))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(campoData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btNovo.setToolTipText("Novo Registro");
        btNovo.setDisabledIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/add.png"))); // NOI18N
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        btSalvar.setToolTipText("Salvar Registro");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btExcluir.setToolTipText("Remover Registro");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btAlterar.setToolTipText("Alterar Registro");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btCancelar.setToolTipText("Cancelar edição");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        btSair.setToolTipText("Sair do formulário");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancelar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSair))
                    .addComponent(painelDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSair, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btNovo, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
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
                .addContainerGap(43, Short.MAX_VALUE))
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

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "NOME", "CPF/CNPJ", "RG/INSCRIÇÃO", "TELEFONE", "CELULAR" }));
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
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, paineiPesquisaLayout.createSequentialGroup()
                .addContainerGap(461, Short.MAX_VALUE)
                .addComponent(comboPesquisaPFPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addComponent(btFiltra)
                .addGap(150, 150, 150))
            .addGroup(paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(paineiPesquisaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(paineiPesquisaLayout.createSequentialGroup()
                            .addGap(17, 17, 17)
                            .addComponent(painelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(paineiPesquisaLayout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addGap(18, 18, 18)
                            .addComponent(fFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(271, 271, 271)))
        );
        paineiPesquisaLayout.setVerticalGroup(
            paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(paineiPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btFiltra)
                    .addComponent(comboPesquisaPFPJ, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(179, Short.MAX_VALUE))
            .addGroup(paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(paineiPesquisaLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(paineiPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(fFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(painelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(14, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(paineiPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 741, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(paineiPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
    }
private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed

    btNovo.setEnabled(false);
    btSalvar.setEnabled(false);
    btExcluir.setEnabled(false);
    btAlterar.setEnabled(false);
    btCancelar.setEnabled(true);
    btSair.setEnabled(false);
    painelDados.setVisible(false);
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

}//GEN-LAST:event_btNovoActionPerformed

private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
    if (validaCampos() == true) {
        Usuario pf = new Usuario();
        String cpcpf = campoDoc3.getText();
        cpcpf = cpcpf.replace(".", "");
        cpcpf = cpcpf.replace("-", "");
        if (campoCodigo.getText().equals("")) {
            pf.setId(null);
        } else {
            pf.setId(Long.parseLong(campoCodigo.getText()));
        }
 
 
        if (pcontrole.valida_CpfCnpj(cpcpf) == false) {
            JOptionPane.showMessageDialog(null, "CPF Inválido");
            campoDoc3.grabFocus();
        }else{


       

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
        pf.setSenha(campoSenha.getText());
        uControle.salvar(pf);


        limpaCampos();
        montaTabela();
        validaBotoes("inicio");

        }




    }


}//GEN-LAST:event_btSalvarActionPerformed

private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
    int a = (JOptionPane.showConfirmDialog(null, "Deseja exluir o registro?",
            "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null));


    if (a == 0) {
        setUser(listaUser.get(tabela.getSelectedRow()));
        uControle.delete(user);
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

}//GEN-LAST:event_btExcluirActionPerformed

private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed
    //Chamo o ValidaBotões com a ação alterar.

    validaBotoes("alterar");
}//GEN-LAST:event_btAlterarActionPerformed

private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
    limpaCampos();
    painelDados.setVisible(false);
    validaBotoes("inicio");
    // TODO add your handling code here:
}//GEN-LAST:event_btCancelarActionPerformed

private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
    //Fecha a tela.
   this.dispose();
    // TODO add your handling code here:
}//GEN-LAST:event_btSairActionPerformed

private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked

    campoDoc3.setFormatterFactory(formato("###.###.###-##"));
    user = listaUser.get(tabela.getSelectedRow());
    campoCodigo.setText(String.valueOf(user.getId()));
    campoNome.setText(user.getNome());
    campoSenha.setText(user.getSenha());
    campoTelefone.setText(user.getTelefone());
    campoCelular.setText(user.getCelular());
    campoCep.setText(user.getCep());
    campoDoc3.setText(user.getCpf());
    campoDoc2.setText(user.getRg());
    campoData.setDate(user.getDataPes());
    campoEndereco.setText(user.getEndereco());
    campoEmail2.setText(user.getEmail());
    comboCidade.setSelectedItem(user.getCidade().getNome());
    validaBotoes("selecionado");
    montaCombo();
    painelDados.setVisible(true);
}//GEN-LAST:event_tabelaMouseClicked

private void btFiltraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btFiltraActionPerformed


    montaTabela();
}//GEN-LAST:event_btFiltraActionPerformed
private void fFiltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fFiltroKeyReleased


    montaTabela();
}//GEN-LAST:event_fFiltroKeyReleased
private void comboPesquisaPFPJItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboPesquisaPFPJItemStateChanged


    montaTabela();

}//GEN-LAST:event_comboPesquisaPFPJItemStateChanged
private void comboTipoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboTipoItemStateChanged



    montaTabela();
}//GEN-LAST:event_comboTipoItemStateChanged

private void campoDoc3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoDoc3FocusLost
}//GEN-LAST:event_campoDoc3FocusLost

private void tabelaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseEntered
    // TODO add your handling code here:
}//GEN-LAST:event_tabelaMouseEntered

    public List<Usuario> getListaUser() {
        return listaUser;


    }

    public void setListaPF(List<Usuario> listaUser) {
        this.listaUser = listaUser;


    }

    public Usuario getUser() {
        return user;


    }

    public void setUser(Usuario user) {
        this.user = user;


    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                final UsuarioCad dialog = new UsuarioCad(new javax.swing.JFrame(), true);
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
    private javax.swing.JTextField campoSenha;
    private javax.swing.JFormattedTextField campoTelefone;
    private javax.swing.JComboBox comboCidade;
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
    private javax.swing.JLabel jLabel8;
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
