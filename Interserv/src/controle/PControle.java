/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.DaoGenerico;
import dao.DaoHibernateGenerico;
import java.util.List;
import modelo.Pessoa;


/**
 *
 * @author jaime
 */
public class PControle {

    private DaoGenerico dao;
    private static PControle instance;

    public PControle() {
        dao = new DaoHibernateGenerico();
    }

    public static synchronized PControle getInstance() {
        if (instance == null) {
            instance = new PControle();
        }
        return instance;
    }

    public void salvar(Pessoa obj) {
        //Executa as regras de negócio
        dao.save(obj);
    }

    public List<Pessoa> listaTodos() {
        return dao.list(Pessoa.class);
    }

    
    public void delete(Pessoa Pes) {
        dao.delete(Pes);
    }

    public List<Pessoa> listaFiltrando(String filtro, int tipo) {
        if (tipo == 0) {
            return dao.listCriterio(Pessoa.class, "nome", filtro);
       
        } else if (tipo == 2) {
            return dao.listCriterio(Pessoa.class, "telefone", filtro);
        } else if (tipo == 3) {
            return dao.listCriterio(Pessoa.class, "celular", filtro);
        } else {
            return dao.listCriterio(Pessoa.class, "email", filtro);
        }


    }
       public static boolean valida_CpfCnpj(final String CNPJ_CPF) {
        if ((CNPJ_CPF).equals("00000000000")) return false;
        else if ((CNPJ_CPF).equals("11111111111")) return false;
        else if ((CNPJ_CPF).equals("22222222222")) return false;
        else if ((CNPJ_CPF).equals("33333333333")) return false;
        else if ((CNPJ_CPF).equals("44444444444")) return false;
        else if ((CNPJ_CPF).equals("55555555555")) return false;
        else if ((CNPJ_CPF).equals("66666666666")) return false;
        else if ((CNPJ_CPF).equals("77777777777")) return false;
        else if ((CNPJ_CPF).equals("88888888888")) return false;
        else if ((CNPJ_CPF).equals("99999999999")) return false;
        else if (CNPJ_CPF.length() == 11) { //CPF

            int acumulador1 = 0;
            int acumulador2 = 0;
            int resto = 0;

            StringBuffer resultado = new StringBuffer();

            String digitoVerificadorCPF = CNPJ_CPF.substring((CNPJ_CPF.length() - 2),
                    CNPJ_CPF.length());

            for (int i = 1; i < (CNPJ_CPF.length() - 1); i++) {
                acumulador1 += (11 - i) * Integer.valueOf(CNPJ_CPF.substring((i - 1), i));
                acumulador2 += (12 - i) * Integer.valueOf(CNPJ_CPF.substring((i - 1), i));
            }

            resto = acumulador1 % 11;

            if (resto < 2) {
                acumulador2 += 2;
                resultado.append(2);
            } else {
                acumulador2 += 2 * (11 - resto);
                resultado.append((11 - resto));
            }

            resto = acumulador2 % 11;

            if (resto < 2) {
                resultado.append(0);
            } else {
                resultado.append((11 - resto));
            }

            return resultado.toString().equals(digitoVerificadorCPF);
        } else if (CNPJ_CPF.length() == 14) { //CNPJ

            int acumulador = 0;
            int digito = 0;
            StringBuffer CNPJ = new StringBuffer();
            char[] CNPJCharArray = CNPJ_CPF.toCharArray();

            CNPJ.append(CNPJ_CPF.substring(0, 12));

            for (int i = 0; i < 4; i++) {
                if (((CNPJCharArray[i] - 48) >= 0)
                        && ((CNPJCharArray[i] - 48) <= 9)) {
                    acumulador += (CNPJCharArray[i] - 48) * (6 - (i + 1));
                }
            }

            for (int i = 0; i < 8; i++) {
                if (((CNPJCharArray[i + 4] - 48) >= 0)
                        && ((CNPJCharArray[i + 4] - 48) <= 9)) {
                    acumulador += (CNPJCharArray[i + 4] - 48) * (10 - (i + 1));
                }
            }

            digito = 11 - (acumulador % 11);

            CNPJ.append((digito == 10 || digito == 11) ? "0" : digito);

            acumulador = 0;

            for (int i = 0; i < 5; i++) {
                if (((CNPJCharArray[i] - 48) >= 0)
                        && ((CNPJCharArray[i] - 48) <= 9)) {
                    acumulador += (CNPJCharArray[i] - 48) * (7 - (i + 1));
                }
            }

            for (int i = 0; i < 8; i++) {
                if (((CNPJCharArray[i + 5] - 48) >= 0)
                        && ((CNPJCharArray[i + 5] - 48) <= 9)) {
                    acumulador += (CNPJCharArray[i + 5] - 48) * (10 - (i + 1));
                }
            }

            digito = 11 - (acumulador % 11);

            CNPJ.append((digito == 10 || digito == 11) ? "0" : digito);

            return CNPJ.toString().equals(CNPJ_CPF);
        }

        return false;
    }
}
