package controller;

import java.util.Arrays;
import java.util.List;

public class AnalisadorLexico {

    private final String reservada = "Palavra Reservada";
    private final String digito = "Número";
    private final String erro = "Erro";
    private final String id = "Identificador";
    static final String DIGITOS = "[0-9]+";

    static List<String> palavrasReservadas = Arrays.asList("div", "or", "and", "not", "if", "then", "else", "of", "Record", "while", "do", "begin", "end", "writeln",
            "readln", "read", "const", "write", "var", "array", "function", "procedure", "program", "true", "false", "char", "integer", "boolean", "Uses");

    static String IDENTIFICADOR = "[a-zA-Z_]+([0-9_]*[a-zA-Z]*)*";

    private static boolean verificarPalavraReservada(String palavra) {

        for (int i = 0; i < palavrasReservadas.size() - 1; i++) {
            if (palavrasReservadas.get(i).equalsIgnoreCase(palavra)) {
                return true;
            }
        }
        return false;
    }

    public static boolean verificarDigito(String palavra) {
        return (palavra.matches(DIGITOS));
    }

    static boolean verificarIdenticadores(String palavra) {
        return (palavra.matches(IDENTIFICADOR));
    }

    public String validar(String palavra) {
        String lexema = "";
        if (palavra.endsWith(" ")) {
            lexema = lexema.substring(0, palavra.length() - 1);
        } else {
            lexema = palavra;
        }
        if (verificarPalavraReservada(lexema)) {

            return reservada;

        } else if (verificarDigito(lexema)) {

            return digito;

        } else if (verificarIdenticadores(lexema)) {

            return id;

        } else {

            return erro;
        }
    }
}
