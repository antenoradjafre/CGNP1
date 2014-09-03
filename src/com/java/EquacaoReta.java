package com.java;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Antenor on 12/08/2014.
 */
public class EquacaoReta extends JPanel{
    private static int tamanhoX = 31;
    private static int tamanhoY = 31;
    private static int valorXInicial = 10;
    private static int valorYInicial = 10;
    private static int valorXFinal = 150;
    private static int valorYFinal = 150;
    private static String[][] tela = new String[tamanhoX][tamanhoY];
    private static Graphics graphics;

    public EquacaoReta(){
        super();
    }

    public static void main(String args[]) {
//        desenharMatriz(tela);
        int raio = 10;
        Bresenham(valorXInicial, valorYInicial, valorXFinal, valorYFinal);
        ForcaBruta(valorXInicial, valorYInicial, valorXFinal, valorYFinal);
//        circuferencia(raio);
        construirJPanel("TESTE");


        DDA(valorXInicial, valorYInicial, valorXFinal, valorYFinal, graphics);
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

    private static void DDA(int valorXInicial, int valorYInicial, int valorXFinal, int valorYFinal, Graphics graphics) {
        construirJPanel("DDA");
        int coeficienteReta = (valorYFinal - valorYInicial) / (valorXFinal - valorXInicial);
        int b = valorYInicial - (coeficienteReta * valorXInicial);
        int valorY;
        int valorX = valorXInicial;
        int pontoAnterior = valorYInicial;
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
                graphics.drawLine(valorXInicial, valorYInicial, valorX, valorY);
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
                graphics.drawLine(valorXInicial, valorYInicial, valorX, valorY);
                System.out.println("Valor X: " + valorX + " ValorY: " + valorY);
                valorX++;
            }
        }

    }

    private static void construirJPanel(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200,200);

        EquacaoReta panel = new EquacaoReta();

        frame.setContentPane(panel);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
            g.drawLine(valorXInicial, valorYInicial, valorXFinal, valorYFinal);
        }
    }