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

import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import modelo.Cidade;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import controle.CidadeControle;
import controle.PJControle;
import controle.EmpresaControle;
import controle.PControle;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.MaskFormatter;
import modelo.Empresa;

/**
 *
 * @author aluno
 */
public class EmpresaCad extends javax.swing.JDialog {

    private List<Cidade> listaCid = new ArrayList<Cidade>();
    private List<Empresa> listaemp = new ArrayList<Empresa>();
    private CidadeControle cidcontrole;
    private EmpresaControle eControle;
    private Empresa emp;
    private PControle pcontrole;
    //-------- Valida CPF ou CNPJ

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

            btSalvar.setEnabled(false);
            btAlterar.setEnabled(true);
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
            campoFantasia.setEnabled(false);
            campoNumero.setEnabled(false);



        } else if (acao.equals("novo") || acao.equals("alterar")) {

            btSalvar.setEnabled(true);
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
            campoFantasia.setEnabled(true);
            campoNumero.setEnabled(true);

        } else if (acao.equals("selecionado")) {


            btSalvar.setEnabled(false);
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
            campoFantasia.setEnabled(false);
            campoNumero.setEnabled(false);

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

        campoDoc3.setText("");
        campoDoc2.setText("");
        campoEmail2.setText("");
        campoNome.setText("");
        campoEndereco.setText("");
        campoTelefone.setText("");
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

    public EmpresaCad() {
    }

    public EmpresaCad(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        cidcontrole = CidadeControle.getInstance();
        eControle = EmpresaControle.getInstance();
        montaCombo();

        validaBotoes("selecionado");
        icones();
        campoCelular.setFormatterFactory(formato("##-####-####"));
        campoTelefone.setFormatterFactory(formato("##-####-####"));
        campoCep.setFormatterFactory(formato("##.###-###"));
        listaemp = eControle.listaTodos();
        emp = listaemp.get(0);
        campoCelular.setText(emp.getTelefone2());
        campoCep.setText(emp.getCep());
        campoCodigo.setText(emp.getId().toString());
        campoDoc2.setText(emp.getInscricao());
        campoDoc3.setText(emp.getCnpj());
        campoEmail2.setText(emp.getEmail());
        campoEndereco.setText(emp.getEndereoo());
        campoFantasia.setText(emp.getFantasia());
        campoNome.setText(emp.getNome());
        campoNumero.setText(emp.getNumero());
        campoTelefone.setText(emp.getTelefone());


    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        btSalvar = new javax.swing.JButton();
        btAlterar = new javax.swing.JButton();
        btCancelar = new javax.swing.JButton();
        btSair = new javax.swing.JButton();
        campoTelefone = new javax.swing.JFormattedTextField();
        campoCep = new javax.swing.JFormattedTextField();
        campoDoc3 = new javax.swing.JFormattedTextField();
        campoEmail2 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        rotuloNome = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        campoNumero = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        campoCelular = new javax.swing.JFormattedTextField();
        campoFantasia = new javax.swing.JTextField();
        campoCodigo = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        rotuloDoc2 = new javax.swing.JLabel();
        rotuloDoc1 = new javax.swing.JLabel();
        campoEndereco = new javax.swing.JTextField();
        comboCidade = new javax.swing.JComboBox();
        campoDoc2 = new javax.swing.JTextField();
        campoNome = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("DADOS DO USUARIO DO SISTEMA");

        btSalvar.setToolTipText("Salvar Registro");
        btSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSalvarActionPerformed(evt);
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
                .addComponent(btSalvar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btAlterar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btCancelar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btSair)
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btSalvar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btAlterar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btCancelar, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
                    .addComponent(btSair, javax.swing.GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE))
                .addContainerGap())
        );

        campoDoc3.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                campoDoc3FocusLost(evt);
            }
        });

        jLabel5.setText("CEP");

        jLabel4.setText("Cidade");

        jLabel3.setText("Endereco");

        rotuloNome.setText("Nome");

        jLabel8.setText("Nome Fantasia");

        jLabel9.setText("Numero");

        campoCodigo.setEditable(false);

        jLabel11.setText("email");

        jLabel7.setText("Telefone Alternativo");

        jLabel6.setText("Telefone");

        rotuloDoc2.setText("Inscrição Estadual");

        rotuloDoc1.setText("CNPJ");

        comboCidade.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("ID");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoTelefone, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rotuloNome, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(campoEmail2, javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(rotuloDoc1)
                                            .addComponent(campoDoc3, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(101, 101, 101)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(rotuloDoc2)
                                                .addGap(44, 44, 44))
                                            .addComponent(campoDoc2, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(campoFantasia, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(campoEndereco, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel9)
                                                .addComponent(campoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(comboCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel4))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jLabel5)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel7)
                                                        .addGroup(layout.createSequentialGroup()
                                                            .addGap(2, 2, 2)
                                                            .addComponent(campoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                    .addComponent(campoCep, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                                .addComponent(campoNome, javax.swing.GroupLayout.Alignment.LEADING))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoCodigo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(36, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rotuloNome)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoNome, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rotuloDoc2)
                    .addComponent(rotuloDoc1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoDoc2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoDoc3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(campoNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(campoCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel11))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(campoCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(campoEmail2, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void icones() {
        btAlterar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/altera.png")));
        btAlterar.setToolTipText("Alterar um registro");
        btAlterar.setSize(30, 30);
        btAlterar.setText("");
        btCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/deleteazul.png")));
        btCancelar.setToolTipText("Cancela alterações");
        btCancelar.setSize(30, 30);
        btCancelar.setText("");
        btSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/door_in.png")));
        btSair.setToolTipText("Fechar o formulário");
        btSair.setSize(30, 30);
        btSair.setText("");
        btSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icones/accept.png")));
        btSalvar.setToolTipText("Salva alterações no registro");
        btSalvar.setSize(30, 30);
        btSalvar.setText("");
    }
private void btSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSalvarActionPerformed
    if (validaCampos() == true) {
        Empresa emp = new Empresa();
        String ccnpj = campoDoc3.getText();
        ccnpj = ccnpj.replace(".", "");
        ccnpj = ccnpj.replace("-", "");
        if (campoCodigo.getText().equals("")) {
            emp.setId(null);
        } else {
            emp.setId(Long.parseLong(campoCodigo.getText()));
        }


        if (pcontrole.valida_CpfCnpj(ccnpj) == false) {
            JOptionPane.showMessageDialog(null, "CNPJ Inválido");
            campoDoc3.grabFocus();
        } else {




            emp.setNome(campoNome.getText());
            emp.setEndereoo(campoEndereco.getText());
            emp.setCnpj(ccnpj);
            emp.setInscricao(campoDoc2.getText());
            emp.setEmail(campoEmail2.getText());
            emp.setTelefone2(campoCelular.getText().replace("-", ""));
            emp.setCidade(listaCid.get(comboCidade.getSelectedIndex()));
            emp.setTelefone(campoTelefone.getText().replace("-", ""));
            String cep = campoCep.getText();
            emp.setFantasia(campoFantasia.getName());
            emp.setNumero(campoNumero.getText());
            cep = cep.replace("-", "");
            cep = cep.replace(".", "");
            emp.setCep(cep);
            eControle.salvar(emp);
            //numero e fantasia

            limpaCampos();
            validaBotoes("selecionado");

        }




    }


}//GEN-LAST:event_btSalvarActionPerformed

private void btAlterarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btAlterarActionPerformed

    validaBotoes("alterar");
}//GEN-LAST:event_btAlterarActionPerformed

private void btCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btCancelarActionPerformed
    limpaCampos();
    validaBotoes("selecionado");
    // TODO add your handling code here:
}//GEN-LAST:event_btCancelarActionPerformed

private void btSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btSairActionPerformed
    //Fecha a tela.
    this.dispose();
    // TODO add your handling code here:
}//GEN-LAST:event_btSairActionPerformed

private void campoDoc3FocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_campoDoc3FocusLost
}//GEN-LAST:event_campoDoc3FocusLost

    public List<Empresa> getListaemp() {
        return listaemp;


    }

    public void setListaPF(List<Empresa> listaemp) {
        this.listaemp = listaemp;


    }

    public Empresa getemp() {
        return emp;


    }

    public void setemp(Empresa emp) {
        this.emp = emp;


    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                final EmpresaCad dialog = new EmpresaCad(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btSair;
    private javax.swing.JButton btSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JFormattedTextField campoCelular;
    private javax.swing.JFormattedTextField campoCep;
    private javax.swing.JTextField campoCodigo;
    private javax.swing.JTextField campoDoc2;
    private javax.swing.JFormattedTextField campoDoc3;
    private javax.swing.JTextField campoEmail2;
    private javax.swing.JTextField campoEndereco;
    private javax.swing.JTextField campoFantasia;
    private javax.swing.JTextField campoNome;
    private javax.swing.JTextField campoNumero;
    private javax.swing.JFormattedTextField campoTelefone;
    private javax.swing.JComboBox comboCidade;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel rotuloDoc1;
    private javax.swing.JLabel rotuloDoc2;
    private javax.swing.JLabel rotuloNome;
    // End of variables declaration//GEN-END:variables
}
