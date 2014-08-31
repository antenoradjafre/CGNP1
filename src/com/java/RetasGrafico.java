package com.java;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.*;

public class RetasGrafico extends JComponent{
    private static int tamanhoX = 800;
    private static int tamanhoY = 800;
    private static int valorXInitial;
    private static int valorYInitial;
    private static int valorXFinal;
    private static int valorYFinal;
    private static int circRaid;

    private static class Line{
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    private final LinkedList<Line> lines = new LinkedList<Line>();

    public void addLine(int x1, int x2, int x3, int x4) {
        addLine(x1, x2, x3, x4, Color.black);
    }

    public void addLine(int x1, int x2, int x3, int x4, Color color) {
        lines.add(new Line(x1,x2,x3,x4, color));
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Line line : lines) {
            g.setColor(line.color);
            g.drawLine(line.x1, line.y1, line.x2, line.y2);
        }
    }

    public static void main(String[] args) {

        JFrame testFrame = new JFrame();
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        final RetasGrafico comp = new RetasGrafico();
        comp.setPreferredSize(new Dimension(tamanhoX, tamanhoY));
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        addButtons(buttonsPanel, comp);


        testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);


        testFrame.pack();
        testFrame.setVisible(true);
    }

    private static void addButtons(JPanel buttonsPanel, final RetasGrafico comp) {
        JLabel labelForXInitial = new JLabel("X Initial");
        JTextField xInitial = new JTextField("",5);
        JLabel labelForYInitial = new JLabel("Y Initial");
        JTextField yInitial = new JTextField("",5);
        JLabel labelForXFinal = new JLabel("X Final");
        JTextField xFinal = new JTextField("",5);
        JLabel labelForYFinal = new JLabel("Y Final");
        JTextField yFinal = new JTextField("",5);
        JLabel labelForRaid = new JLabel("Raid");
        JTextField raid = new JTextField("",5);
        buttonsPanel.add(labelForXInitial);
        buttonsPanel.add(xInitial);
        buttonsPanel.add(labelForYInitial);
        buttonsPanel.add(yInitial);
        buttonsPanel.add(labelForXFinal);
        buttonsPanel.add(xFinal);
        buttonsPanel.add(labelForYFinal);
        buttonsPanel.add(yFinal);
        buttonsPanel.add(labelForRaid);
        buttonsPanel.add(raid);
        //DDA
        JButton dda = new JButton("DDA");
        buttonsPanel.add(dda);
        addActionListenerDDA(xInitial,yInitial, xFinal, yFinal, dda, comp);

        JButton forcaBruta = new JButton("Forca Bruta");
        JButton bresenham = new JButton("Bresenham");

        //Circunferencia
        JButton circunferencia = new JButton("Circunferencia");
        buttonsPanel.add(circunferencia);
        addActionListenerCircunferencia(raid, circunferencia, comp);

        //Clear
        JButton clearButton = new JButton("Clear");

        buttonsPanel.add(clearButton);

        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                comp.clearLines();
            }
        });

    }

    private static void addActionListenerCircunferencia(final JTextField raid, JButton circunferencia, final RetasGrafico comp) {
        circunferencia.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                circRaid = Integer.parseInt(raid.getText());
                circunference(circRaid, comp);
            }
        });
    }

    private static void circunference(int raid, RetasGrafico comp) {
        int variableX = 0;
        int variableY = raid;
        int diametro = 1 - raid;
        int deltaE = 3;
        int deltaSE = -2*raid + 5;

        drawCircle(variableX, variableY, comp);

        while(variableY > variableX){
            if(diametro < 0){
                diametro += deltaE;
                deltaE += 2;
                deltaSE += 2;
            }else{
                diametro += deltaSE;
                deltaE += 2;
                deltaSE += 4;
                variableY--;
            }
            variableX++;
            drawCircle(variableX, variableY, comp);
        }
    }

    private static void drawCircle(int valorXInitial, int valorYInitial, RetasGrafico comp) {
        valorXInitial = convertToMatrixX(valorXInitial);
        valorYInitial = convertToMatrixY(valorYInitial);
        comp.addLine(valorXInitial, valorYInitial,valorXInitial, -valorYInitial);
        comp.addLine(valorXInitial, -valorYInitial, -valorXInitial, valorYInitial);
        comp.addLine(-valorXInitial, valorYInitial, -valorXInitial, -valorYInitial);
        comp.addLine(-valorXInitial, -valorYInitial, valorYInitial, valorXInitial);
        comp.addLine(valorYInitial, valorXInitial, valorYInitial, -valorXInitial);
        comp.addLine(valorYInitial, -valorXInitial, -valorYInitial, valorXInitial);
        comp.addLine(-valorYInitial, valorXInitial, -valorYInitial, -valorXInitial);
        comp.addLine(-valorYInitial, -valorXInitial, valorXInitial, valorYInitial);
    }

    private static void addActionListenerDDA(final JTextField xInitial, final JTextField yInitial, final JTextField xFinal, final JTextField yFinal, JButton dda, final RetasGrafico comp) {
        dda.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valorXInitial = Integer.parseInt(xInitial.getText());
                valorXFinal = Integer.parseInt(xFinal.getText());
                valorYInitial = Integer.parseInt(yInitial.getText());
                valorYFinal = Integer.parseInt(yFinal.getText());
                DDA(valorXInitial, valorYInitial, valorXFinal, valorYFinal, comp);
            }
        });
    }

    private static void DDA(int valorXInitial, int valorYInitial, int valorXFinal, int valorYFinal, RetasGrafico comp) {
        int differenceX = invertSignalNegativePoints(valorXFinal - valorXInitial);
        int differenceY = invertSignalNegativePoints(valorYFinal - valorYInitial);
        int lenght;
        int deltaX;
        int deltaY;
        int variableX;
        int variableY;

        if(differenceX >= differenceY){
            lenght = differenceX;
        }else{
            lenght = differenceY;
        }

        deltaX = differenceX/lenght;
        deltaY = differenceY/lenght;

        variableX = (int) (valorXInitial + 0.5 * deltaX);
        variableY = (int) (valorYInitial + 0.5 * deltaY);

        for(int i=0;i<lenght;i++){
            comp.addLine(convertToMatrixX(valorXInitial), convertToMatrixY(valorYInitial), convertToMatrixX(variableX), convertToMatrixY(variableY));
            valorXInitial = variableX;
            valorYInitial = variableY;
            variableX += deltaX;
            variableY += deltaY;
        }
    }

    private static int convertToMatrixY(int variableY) {
        return tamanhoY / 2 - variableY;
    }

    private static int convertToMatrixX(int variableX) {
        return variableX + tamanhoX / 2;
    }

    private static int invertSignalNegativePoints(int i) {
        return i < 0 ? -1*i: i;
    }
}