/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * ProdutoCad.java
 *
 * Created on 25/07/2011, 20:39:45
 */
package visao;

import java.awt.event.ActionEvent;
import modelo.Produto;
import modelo.GrupoProduto;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import controle.ProdutoControle;
import controle.GrupoProdutoControle;
import controle.JMoneyField;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import javax.swing.JFormattedTextField;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import sun.security.krb5.internal.TGSRep;

/**
 *
 * @author Jaime
 */
public final class ProdutoCad extends javax.swing.JDialog {

    private List<Produto> listaprod = new ArrayList<Produto>();
    private List<GrupoProduto> listaGrup = new ArrayList<GrupoProduto>();
    //Objeto global do cadastro.
    private Produto prod;
    private GrupoProduto grup;
    private ProdutoControle controle;
    private GrupoProdutoControle gcontrole;

    ProdutoCad() {
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
            campoDescricao.setEditable(false);
            comboProduto.setEnabled(false);
            campoCusto.setEditable(false);
            campoEstoque.setEditable(false);
            campoLucro.setEditable(false);
            campoMaximo.setEditable(false);
            campoMinimo.setEditable(false);
            campoVenda.setEditable(false);
            painelTabela.setEnabled(false);
            fFIltro.setEnabled(true);
            tabela.setEnabled(true);
            painelDados.setVisible(false);
        } else if (acao.equals("novo") || acao.equals("alterar")) {
            btNovo.setEnabled(false);
            btSalvar.setEnabled(true);
            btExcluir.setEnabled(false);
            btAlterar.setEnabled(false);
            btCancelar.setEnabled(true);
            btSair.setEnabled(false);
            campoDescricao.setEditable(true);
            comboProduto.setEnabled(true);
            campoCusto.setEditable(true);
            campoEstoque.setEditable(true);
            campoLucro.setEditable(true);
            campoMaximo.setEditable(true);
            campoMinimo.setEditable(true);
            campoVenda.setEditable(true);
            painelTabela.setEnabled(true);
            tabela.setEnabled(false);
            fFIltro.setEnabled(false);
            painelDados.setVisible(true);

        } else if (acao.equals("selecionado")) {
            btNovo.setEnabled(true);
            btSalvar.setEnabled(false);
            btExcluir.setEnabled(true);
            btAlterar.setEnabled(true);
            btCancelar.setEnabled(true);
            btSair.setEnabled(true);
            campoDescricao.setEditable(false);
            comboProduto.setEnabled(false);
            campoCusto.setEditable(false);
            campoEstoque.setEditable(false);
            campoLucro.setEditable(false);
            campoMaximo.setEditable(false);
            campoMinimo.setEditable(false);
            campoVenda.setEditable(false);
            painelTabela.setEnabled(true);
            tabela.setEnabled(true);
            fFIltro.setEnabled(true);
            painelDados.setVisible(true);
        }
    }

    //Valida os campos obrigatórios de um cadastro, 
    //retornando um boolean true quando verdadeiro ou false quando tiver algum campo em branco.
    public Boolean validaCampos() {
        if (campoDescricao.getText().equals("")) {
            //exibe uma mensagem para o usuario.
            JOptionPane.showMessageDialog(null, "O campo Descricao da Produto é obrigatório!");
            campoDescricao.grabFocus();
            return false;
            
        } else if (comboProduto.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "A combo Grupo é obrigatório!");
            comboProduto.grabFocus();
            return false;
        } else if (comboUnidade.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "A combo Unidade é obrigatório!");
            comboProduto.grabFocus();
            return false;

        } else if (campoVenda.getText() == null) {
            JOptionPane.showMessageDialog(null, "O Material precisa ter preço!");
            campoCusto.grabFocus();
            return false;
        } else {
            return true;
        }
    }

    // Monta uma combo com seus itens.
    public void montaCombo() {
        comboProduto.removeAllItems();
        listaGrup = gcontrole.listaTodos();
        for (GrupoProduto g : listaGrup) {
            comboProduto.addItem(g.getDescricao());
        }

    }

    public Produto getprod() {
        return prod;
    }

    public void setprod(Produto prod) {
        this.prod = prod;
    }

    public List<Produto> getListaprod() {
        return listaprod;
    }

    public void setListaprod(List<Produto> listaprod) {
        this.listaprod = listaprod;
    }

    //Método construtor, método com mesmo Descricao da Classe, é o primeiro método a ser
    //executado quando abrimos a tela.
    public ProdutoCad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
       

        controle = ProdutoControle.getInstance();
        gcontrole = GrupoProdutoControle.getInstance();
        montaTabela();
        //monta a combo de Grupado.
        montaCombo();
        //valida os botões com a ação inicio.
        validaBotoes("inicio");



    }

    //Monta a tabela com todas as Produtos cadastradas no banco.
    public void montaTabela() {
        listaprod = controle.listaFiltrando(fFIltro.getText(), comboTipo.getSelectedIndex());
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.addColumn("ID");
        modelo.addColumn("Descricao");
        modelo.addColumn("Grupo");
        for (Produto prod : listaprod) {
            modelo.addRow(new Object[]{prod.getId(), prod.getDescricao(), prod.getGrupo().getDescricao()});
        }
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
        jLabel5 = new javax.swing.JLabel();
        fFIltro = new javax.swing.JTextField();
        comboTipo = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela = new javax.swing.JTable();
        painelDados = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        campoDescricao = new javax.swing.JTextField();
        campoCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        comboProduto = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        campoEstoque = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        campoMinimo = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        campoMaximo = new javax.swing.JTextField();
        comboUnidade = new javax.swing.JComboBox();
        campoCusto = new controle.JMoneyField();
        campoLucro = new controle.JMoneyField();
        campoVenda = new controle.JMoneyField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("GESTÃO DE PRODUTOS");
        addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                formPropertyChange(evt);
            }
        });

        btSalvar.setText("Salvar");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
            }
        });

        btSair.setText("Sair");
        btSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSairActionPerformed(evt);
            }
        });

        btExcluir.setText("Excluir");
        btExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btExcluirActionPerformed(evt);
            }
        });

        btAlterar.setText("Alterar");
        btAlterar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btAlterarActionPerformed(evt);
            }
        });

        btCancelar.setText("Cancelar");
        btCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btCancelarActionPerformed(evt);
            }
        });

        btNovo.setText("Novo");
        btNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btNovoActionPerformed(evt);
            }
        });

        painelTabela.setBorder(javax.swing.BorderFactory.createTitledBorder("Tabela"));

        jLabel5.setText("Pesquisa");

        fFIltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fFIltroActionPerformed(evt);
            }
        });
        fFIltro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                fFIltroKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                fFIltroKeyTyped(evt);
            }
        });

        comboTipo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Nome", "ID" }));

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
        });
        jScrollPane1.setViewportView(tabela);

        javax.swing.GroupLayout painelTabelaLayout = new javax.swing.GroupLayout(painelTabela);
        painelTabela.setLayout(painelTabelaLayout);
        painelTabelaLayout.setHorizontalGroup(
            painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTabelaLayout.createSequentialGroup()
                .addGroup(painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, painelTabelaLayout.createSequentialGroup()
                        .addComponent(jLabel5)
                        .addGap(5, 5, 5)
                        .addComponent(fFIltro, javax.swing.GroupLayout.PREFERRED_SIZE, 285, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(74, Short.MAX_VALUE))
        );
        painelTabelaLayout.setVerticalGroup(
            painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelTabelaLayout.createSequentialGroup()
                .addGroup(painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelTabelaLayout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel5))
                    .addGroup(painelTabelaLayout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(painelTabelaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(fFIltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(comboTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelDados.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados"));

        jLabel4.setText("Código");

        jLabel2.setText("Descrição");

        campoCodigo.setEditable(false);

        jLabel3.setText("Grupo");

        comboProduto.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Unidade");

        jLabel6.setText("Estoque");

        jLabel7.setText("P. Custo");

        jLabel8.setText("Lucro (%)");

        jLabel9.setText("P. Venda");

        jLabel11.setText("Estoque Minimo");

        jLabel12.setText("Estoque Maximo");

        comboUnidade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "UN", "FL", "KIT", "KG", "GR", "LT", "FR", "FD", "FRS", "JG" }));

        campoLucro.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoLucroFocusLost(evt);
            }
        });
        campoLucro.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoLucroKeyTyped(evt);
            }
        });

        campoVenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                campoVendaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoVendaFocusLost(evt);
            }
        });
        campoVenda.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                campoVendaInputMethodTextChanged(evt);
            }
        });
        campoVenda.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                campoVendaPropertyChange(evt);
            }
        });
        campoVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                campoVendaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout painelDadosLayout = new javax.swing.GroupLayout(painelDados);
        painelDados.setLayout(painelDadosLayout);
        painelDadosLayout.setHorizontalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(painelDadosLayout.createSequentialGroup()
                                .addComponent(comboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 212, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addGap(18, 18, 18)
                                .addComponent(comboUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(campoDescricao)))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campoCusto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addComponent(campoMinimo))
                        .addGap(41, 41, 41)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(campoMaximo)
                            .addComponent(campoLucro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(37, 37, 37)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel9)
                                .addComponent(jLabel6)
                                .addComponent(campoEstoque)))))
                .addContainerGap(153, Short.MAX_VALUE))
            .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelDadosLayout.createSequentialGroup()
                    .addGap(66, 66, 66)
                    .addComponent(campoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(447, 447, 447)))
        );
        painelDadosLayout.setVerticalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addContainerGap(14, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(campoDescricao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(comboProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(comboUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(campoMinimo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoMaximo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel12)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(campoLucro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelDadosLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(campoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(200, Short.MAX_VALUE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btNovo)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btSalvar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btExcluir)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btAlterar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btSair, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(48, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(painelDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(painelTabela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {btAlterar, btCancelar, btExcluir, btNovo, btSair, btSalvar});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btSair)
                    .addComponent(btCancelar)
                    .addComponent(btAlterar)
                    .addComponent(btExcluir)
                    .addComponent(btSalvar)
                    .addComponent(btNovo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelDados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(painelTabela, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //Ação do botão salvar
    private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
        //Verifico se o metodo validaCampos retorna Verdadeiro, se sim salvo a Produto.
        if (validaCampos() == true) {
            prod = new Produto();
            if (campoCodigo.getText().equals("")) {
                prod.setId(null);
            } else {
                prod.setId(Long.parseLong(campoCodigo.getText()));
            }
            prod.setDescricao(campoDescricao.getText());
            prod.setGrupo(listaGrup.get(comboProduto.getSelectedIndex()));
            prod.setUnidade(comboUnidade.getSelectedItem().toString());
            prod.setEstoque(Integer.parseInt(campoEstoque.getText()));
            String luc = campoLucro.getText().replace(".", "");
            luc = luc.replace(",", ".");
            prod.setLucro(new BigDecimal(luc));
            String prec = campoVenda.getText().replace(".", "");
            prec = prec.replace(",", ".");
            prod.setPreco(new BigDecimal(prec));
            prod.setQuantidadeMax(Integer.parseInt(campoMaximo.getText()));
            prod.setQuantidadeMin(Integer.parseInt(campoMinimo.getText()));
            String cust = campoCusto.getText().replace(".", "");
            cust = cust.replace(",", ".");
            prod.setCusto(new BigDecimal(cust));

            controle.salvar(prod);
            montaTabela();
            limpaCampos();
            // TODO add your handling code here:
            validaBotoes("inicio");
        }
    }//GEN-LAST:event_btSalvarActionPerformed

    //Ação do botão Sair.
    private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
        //Fecha a tela.
        this.dispose();
        // TODO add your handling code here:
    }//GEN-LAST:event_btSairActionPerformed

    //Ação do Botão Excluir.
    private void btExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btExcluirActionPerformed
        int i = (JOptionPane.showConfirmDialog(null, "Deseja exluir o registro?",
                "Atenção", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null));

        if (i == 0) {
            setprod(listaprod.get(tabela.getSelectedRow()));
            controle.delete(prod);
            montaTabela();
            validaBotoes("inicio");

        } else if (i == 1) {
            limpaCampos();
            validaBotoes("inicio");
        } else {
            JOptionPane.showMessageDialog(null, "Erro crítico");
            limpaCampos();
            validaBotoes("inicio");

        }


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

    }//GEN-LAST:event_btCancelarActionPerformed

    public void limpaCampos() {
        //seta o texto do campo como vazio.
        campoDescricao.setText("");
        campoCusto.setText("");
        campoEstoque.setText("");
        campoLucro.setText("");
        campoMaximo.setText("");
        campoMinimo.setText("");
        campoVenda.setText("");
        comboTipo.setSelectedItem(null);
        //seta o item selecionado como null. Limpa a combo.
        comboProduto.setSelectedItem(null);
        campoCodigo.setText("");
    }

    //Ação do botão Novo.
    private void btNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btNovoActionPerformed

        //Instâncio um novo objeto Produto para o atributo prod.
        prod = new Produto();
        limpaCampos();
        validaBotoes("novo");
        campoDescricao.grabFocus();
        // TODO add your handling code here:
    }//GEN-LAST:event_btNovoActionPerformed

    //Ação ao clicar em um registro na tabela.
    private void tabelaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaMouseClicked
        validaBotoes("selecionado");
        prod = listaprod.get(tabela.getSelectedRow());
        campoCodigo.setText(String.valueOf(prod.getId()));
        campoDescricao.setText(prod.getDescricao());
        comboProduto.setSelectedItem(prod.getGrupo().getDescricao());
        comboTipo.setSelectedItem(prod.getUnidade());
        campoEstoque.setText(prod.getEstoque().toString());
        campoLucro.setText(String.valueOf(prod.getLucro()));
        campoMaximo.setText(prod.getQuantidadeMax().toString());
        campoMinimo.setText(prod.getQuantidadeMin().toString());
        campoCusto.setText(String.valueOf(prod.getCusto()));
        campoVenda.setText(String.valueOf(prod.getPreco()));
        campoCodigo.setText(prod.getPreco().toString());




    }//GEN-LAST:event_tabelaMouseClicked

private void fFIltroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fFIltroKeyTyped
}//GEN-LAST:event_fFIltroKeyTyped

private void fFIltroKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_fFIltroKeyReleased
    montaTabela();// TODO add your handling code here:
}//GEN-LAST:event_fFIltroKeyReleased

private void fFIltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fFIltroActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_fFIltroActionPerformed

private void campoLucroFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoLucroFocusLost
    String ccusto = campoCusto.getText().replace(".", "");
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

private void campoVendaInputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_campoVendaInputMethodTextChanged
}//GEN-LAST:event_campoVendaInputMethodTextChanged

private void formPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_formPropertyChange
// TODO add your handling code here:
}//GEN-LAST:event_formPropertyChange

private void campoVendaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_campoVendaPropertyChange
// TODO add your handling code here:
}//GEN-LAST:event_campoVendaPropertyChange

private void campoVendaFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoVendaFocusGained
// TODO add your handling code here:
}//GEN-LAST:event_campoVendaFocusGained

private void campoVendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoVendaFocusLost
  String ccusto = campoCusto.getText().replace(".", "");
    ccusto = ccusto.replace(",", ".");
    String cvenda = campoVenda.getText().replace(".", "");
    cvenda = cvenda.replace(",", ".");

    Double custo = Double.parseDouble(ccusto);
    Double venda = Double.parseDouble(cvenda);

    Double dlucro = (venda - custo);
    dlucro = (dlucro / custo) * 100;

    BigDecimal alucro = new BigDecimal(dlucro).setScale(2, BigDecimal.ROUND_UP);

    campoLucro.setText(alucro.toEngineeringString());    // TODO add your handling code here:
}//GEN-LAST:event_campoVendaFocusLost

private void campoLucroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoLucroKeyTyped
// TODO add your handling code here:
}//GEN-LAST:event_campoLucroKeyTyped

private void campoVendaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_campoVendaKeyTyped

     // TODO add your handling code here:
}//GEN-LAST:event_campoVendaKeyTyped

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                final ProdutoCad dialog = new ProdutoCad(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {

                    @Override
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
    private javax.swing.JButton btNovo;
    private javax.swing.JButton btSair;
    private javax.swing.JButton btSalvar;
    private javax.swing.JTextField campoCodigo;
    private controle.JMoneyField campoCusto;
    private javax.swing.JTextField campoDescricao;
    private javax.swing.JTextField campoEstoque;
    private controle.JMoneyField campoLucro;
    private javax.swing.JTextField campoMaximo;
    private javax.swing.JTextField campoMinimo;
    private controle.JMoneyField campoVenda;
    private javax.swing.JComboBox comboProduto;
    private javax.swing.JComboBox comboTipo;
    private javax.swing.JComboBox comboUnidade;
    private javax.swing.JTextField fFIltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelTabela;
    private javax.swing.JTable tabela;
    // End of variables declaration//GEN-END:variables
}
