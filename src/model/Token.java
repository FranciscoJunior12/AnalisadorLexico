/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 * @author Augusto Chissano
 * @author Edilson Ricardo
 * @author Francisco Junior
 */
public class Token {

    private String nomeToken, tipoToken, linhaToken;

    public Token(String nomeToken, String tipoToken, String linhaToken) {
        this.nomeToken = nomeToken;
        this.tipoToken = tipoToken;
        this.linhaToken = linhaToken;
    }

    public String getNomeToken() {
        return nomeToken;
    }

    public void setNomeToken(String nomeToken) {
        this.nomeToken = nomeToken;
    }

    public String getTipoToken() {
        return tipoToken;
    }

    public void setTipoToken(String tipoToken) {
        this.tipoToken = tipoToken;
    }

    public String getLinhaToken() {
        return linhaToken;
    }

    public void setLinhaToken(String linhaToken) {
        this.linhaToken = linhaToken;
    }

    public static void main(String[] args) {
        String texto = "write('ola";
//        String[] a = texto.split(" ");
//        System.out.println(texto.length());
//        for (int i = 0; i < a.length; i++) {
//
//            System.out.println(a[i]);
//
//        }

        String a = texto.substring(6,6);
        System.out.println(a);
        
    }

}
