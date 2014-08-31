package com.java;

/**
 * Created by Antenor on 12/08/10014.
 */
public class EquacaoReta {
    private static int tamanhoX = 31;
    private static int tamanhoY = 31;
    private static String[][] tela = new String[tamanhoX][tamanhoY];

    public static void main(String args[]) {
        desenharMatriz(tela);
        int valorXInicial = 5;
        int valorYInicial = 8;
        int valorXFinal = 9;
        int valorYFinal = 11;
        int raio = 10;
//        desenhar(-1, -1);
//        mostrar(tela);
//        System.out.println("Reta1 (5,8), (9,11)");
//        Bresenham(valorXInicial, valorYInicial, valorXFinal, valorYFinal);
//        System.out.println();
//        tela = new String[100][100];
//
//        System.out.println("Reta2 (3,10), (9,7)");
//        valorXInicial = 3;
//        valorYInicial = 10;
//        valorXFinal = 9;
//        valorYFinal = 7;
//        Bresenham(valorXInicial, valorYInicial, valorXFinal, valorYFinal);
//        System.out.println();
//        tela = new String[100][100];
//
//        System.out.println("Reta3 (10,7), (5,12)");
//        valorXInicial = 10;
//        valorYInicial = 7;
//        valorXFinal = 5;
//        valorYFinal = 12;
//        Bresenham(valorXInicial, valorYInicial, valorXFinal, valorYFinal);
//        ForcaBruta(valorXInicial, valorYInicial, valorXFinal, valorYFinal);
//        DDA(valorXInicial, valorYInicial, valorXFinal, valorYFinal);
        circuferencia(raio);
    }

    private static void circuferencia(int raio) {
        int variavelX = 0;
        int variavelY = raio;
        int diametro = 1 - raio;
        int deltaE = 3;
        int deltaSE = -2*raio + 5;

        desenhaCirculo(variavelX, variavelY);

        while(variavelY > variavelX){
            if(diametro < 0){
                diametro += deltaE;
                deltaE += 2;
                deltaSE += 2;
            }else{
                diametro += deltaSE;
                deltaE += 2;
                deltaSE += 4;
                variavelY--;
            }
            variavelX++;
            desenhaCirculo(variavelX, variavelY);
        }
        mostrar(tela);
    }

    private static void desenhaCirculo(int variavelX, int variavelY) {
        desenhar(variavelX, variavelY);
        desenhar(variavelX, -variavelY);
        desenhar(-variavelX, variavelY);
        desenhar(-variavelX, -variavelY);
        desenhar(variavelY, variavelX);
        desenhar(variavelY, -variavelX);
        desenhar(-variavelY, variavelX);
        desenhar(-variavelY, -variavelX);
    }

    private static void Bresenham(int valorXInicial, int valorYInicial, int valorXFinal, int valorYFinal) {
        int variableX = valorXInicial;
        int variableY = valorYInicial;
        int differenceX = valorXFinal - valorXInicial;
        int differenceY = valorYFinal - valorYInicial;
        int error = 2*differenceY - differenceX;
        int inclinationEast = 2*differenceY;
        int inclinationNorthEast = 2 * (differenceY-differenceX);
        desenhar(variableX, variableY);
        while(variableX < valorXFinal){
            if(error < 0){
                error = error + inclinationEast;
                variableX = variableX + 1;
            }else{
                error = error + inclinationNorthEast;
                variableX = variableX + 1;
                variableY = variableY + 1;
            }
            desenhar(variableX, variableY);
        }
        mostrar(tela);
    }

    private static void desenhar(int pontoX, int pontoY){
        pontoX = pontoX + tamanhoX/2;
        pontoY = tamanhoY/2 - pontoY;
        tela[pontoY][pontoX] = "0";
    }

    private static void desenharMatriz(String[][] tela) {
        for(int i=0;i<tamanhoY;i++){
            for(int j=0;j<tamanhoX;j++){
                if(tela[i][j] == null){
                    tela[i][j] = "*";
                }
            }
        }
    }

    private static void mostrar(String[][] tela) {
        for(int i=0;i<tamanhoY;i++){
            for(int j=0;j<tamanhoX;j++){
                System.out.print(tela [i][j]);
            }
            System.out.println();
        }
    }

    private static void ForcaBruta(double valorXInicial, double valorYInicial, double valorXFinal, double valorYFinal) {

        double coeficienteReta = (valorYFinal - valorYInicial) / (valorXFinal - valorXInicial);

        double b = valorYInicial - (coeficienteReta * valorXInicial);

        double valorY;

        double valorX = valorXInicial;

        while (valorX != valorXFinal) {
            valorY = valorX * coeficienteReta + b;
            double valorYArredondado = Math.round(valorX * coeficienteReta + b);
            System.out.println("ValorY: " + valorY + " - " + "Valor Y Arredondado: " + valorYArredondado);
            valorX++;
        }
    }

    private static void DDA(double valorXInicial, double valorYInicial, double valorXFinal, double valorYFinal) {
        double coeficienteReta = (valorYFinal - valorYInicial) / (valorXFinal - valorXInicial);
        double b = valorYInicial - (coeficienteReta * valorXInicial);
        double valorY;
        double valorX = valorXInicial;
        double pontoAnterior = valorYInicial;
        boolean multiplicaUmaVez = true;

        int contador = (int) Math.round((valorXFinal - valorXInicial));

        //multiplicando por -1 caso seja < 0
        if(contador < 0){
            contador=(contador*-1);
        }

        if(valorXFinal < valorXInicial){
            for(int i=contador;i>=0;i--) {
                if(multiplicaUmaVez){
                    multiplicaUmaVez = false;
                    valorY = valorX * coeficienteReta + b;
                    pontoAnterior = valorY;
                }else{
                    valorY = pontoAnterior + coeficienteReta;
                    pontoAnterior = valorY;
                }
                System.out.println("Valor X: " + valorX + " ValorY: " + valorY);
                valorX--;
            }
        }else{
            for(int i=0;i<contador;i++) {
                if(multiplicaUmaVez){
                    multiplicaUmaVez = false;
                    valorY = valorX * coeficienteReta + b;
                    pontoAnterior = valorY;
                }else{
                    valorY = pontoAnterior + coeficienteReta;
                    pontoAnterior = valorY;
                }
                System.out.println("Valor X: " + valorX + " ValorY: " + valorY);
                valorX++;
            }
        }

    }
}
