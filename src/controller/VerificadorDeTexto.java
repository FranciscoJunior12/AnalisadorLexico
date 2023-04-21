package controller;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Token;
import view.TelaPrincipal;


public class VerificadorDeTexto {

    private ArrayList<Token> lista;
    //Para controlar os comentarios em linha e multi-linha
    private boolean comentario1;
    private boolean comentario2;
    private boolean comentario3;

    private boolean write;
    private boolean literal;
    //Variavel auxiliar usada no metodo analisarPalavra, armazena a posicao de cada carater para verificar de trata-se de um simbolo especial
    private Integer posicaoEspecial;
    private String verificar;
    private final String SIMBOLOS = "'-+:*=><()[]=.,;..";

    public void analisarCodigo(String codigoFonte) {

        lista = new ArrayList<>();

        //Separando o codigo fonte em linhas e armazena no Array linha.
        String[] linhas = codigoFonte.split("\n");

        //Iterar sobre o Array que armzena as linhas
        for (int i = 0; i < linhas.length; i++) {

            String nrLinha = String.valueOf(i + 1);
            String[] palavra = linhas[i].split(" ");
            Integer j = 0;
            comentario1 = false;
            //Iterar sobre as linhas, a fim de classificar cada palavra
            for (; j < palavra.length; j++) {

                //Verificando se trata-se  das palavras reservadas write ou writeln
                if ((palavra[j].equalsIgnoreCase("write") || palavra[j].equalsIgnoreCase("writeln")) && !comentario1 & !comentario2 & !comentario3 & !literal & !write) {
                    write = true;
                    this.analisarPalavra(palavra[j], nrLinha);
                } else {

                    if (write & palavra[j].length() >= 2 & !comentario2 & !comentario3) {

                        if (palavra[j].equals("('") || palavra[j].substring(0, 2).equals("('")) {
                            literal = true;
                        } else {
                            write = false;
                            this.analisarPalavra(palavra[j], nrLinha);
                        }
                        if (palavra[j].substring(0, 2).equals("('")) {
                            this.salvarLiteral(nrLinha, palavra[j].substring(2, palavra[j].length()));
                        }
                    } else {

                        if (literal & !comentario2 & !comentario3) {
                            if ((palavra[j].equals("')") || palavra[j].endsWith("')"))) {
                                literal = false;
                                write = false;
                            } else {
                                this.salvarLiteral(palavra[i], nrLinha);
                            }
                            if (palavra[j].endsWith("')")) {
                                this.salvarLiteral(nrLinha, palavra[j].substring(0, palavra[j].length() - 2));
                            }
                        } else {

                            if (comentario3) {
                                if (palavra[j].equals("}")) {
                                    comentario3 = false;
                                } else if (palavra[j].endsWith("}")) {
                                    comentario3 = false;
                                }
                            } else {

                                if (comentario2) {
                                    if (palavra[j].equals("*)")) {
                                        comentario2 = false;
                                    } else if (palavra[j].endsWith("*)")) {
                                        comentario2 = false;
                                    }
                                } else {

                                    if (palavra[j].length() >= 2) {
//a+b;

                                        if (palavra[j].substring(0, 2).equals("//")) {
                                            comentario1 = true;

                                        } else if (palavra[j].substring(0, 2).equals("(*")) {
                                            comentario2 = true;

                                        } else if (palavra[j].substring(0, 1).equals("{")) {
                                            comentario3 = true;
                                        } else if (!comentario1 & !comentario2 & !comentario3) {
                                            this.analisarPalavra(palavra[j], nrLinha);
                                        }
                                    } else {

                                        if (palavra[j].equals("{")) {
                                            comentario3 = true;
                                        }
                                        if (!comentario1 & !comentario2 & !comentario3) {
                                            this.analisarPalavra(palavra[j], nrLinha);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        this.preencherTabela(lista);
    }

    public void analisarPalavra(String palavra, String nrLinha) {
        
        posicaoEspecial = 0;
        for (int i = 0; i < palavra.length(); i++) {
            if (SIMBOLOS.contains(palavra.charAt(i) + "")) {

                verificar = palavra.substring(posicaoEspecial, i);
                posicaoEspecial = i + 1;

                if (!verificar.isEmpty()) {
                    salvarToken(nrLinha, verificar);
                }
                salvarSimbolo(nrLinha, String.valueOf(palavra.charAt(i)));
            } else if (i == palavra.length() - 1) {
                verificar = palavra.substring(posicaoEspecial, i + 1);
                salvarToken(nrLinha, verificar);
            }
        }
    }

    public void salvarToken(String nrLinha, String palavra) {
        String classificacao = new AnalisadorLexico().validar(palavra);
        Token token = new Token(palavra, classificacao, nrLinha);
        lista.add(token);
    }

    public void salvarSimbolo(String nrLinha, String palavra) {
        Token token = new Token(palavra, "Simbolo Especial", nrLinha);
        lista.add(token);
    }

    public void salvarLiteral(String nrLinha, String palavra) {
        Token token = new Token(palavra, "Literal", nrLinha);
        lista.add(token);
    }


    
      public double tempoFinal;

    public void preencherTabela(List<Token>  listaToken) {
        DefaultTableModel model2 = (DefaultTableModel) TelaPrincipal.jTable1.getModel();
//        List<Token> listaToken = new ArrayList();

     
        Object rowData[] = new Object[3];
        for (int a = 0; a < listaToken.size(); a++) {

            if (listaToken.get(a) != null) {
                rowData[0] = listaToken.get(a).getLinhaToken();
                rowData[1] = listaToken.get(a).getNomeToken();
                rowData[2] = listaToken.get(a).getTipoToken();
                model2.addRow(rowData);
//                System.out.println(listaToken);

            }

        }
        tempoFinal = System.currentTimeMillis() - TelaPrincipal.tempoInicial;
        TelaPrincipal.tempo.setText(Double.toString(tempoFinal));

    }


}
