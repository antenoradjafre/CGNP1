package com.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class GraphicLine extends JComponent{
    private static int tamanhoX = 800;
    private static int tamanhoY = 800;
    private static int valorXInitial;
    private static int valorYInitial;
    private static int valorXFinal;
    private static int valorYFinal;
    private static int circRadius;

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
        final GraphicLine graphicLine = new GraphicLine();
        graphicLine.setPreferredSize(new Dimension(tamanhoX, tamanhoY));
        testFrame.getContentPane().add(graphicLine, BorderLayout.CENTER);
        JPanel buttonsPanel = new JPanel();
        addButtons(buttonsPanel, graphicLine);


        testFrame.getContentPane().add(buttonsPanel, BorderLayout.SOUTH);


        testFrame.pack();
        testFrame.setVisible(true);
    }

    private static void addButtons(JPanel buttonsPanel, final GraphicLine graphicLine) {
        JLabel labelForXInitial = new JLabel("X Initial");
        JTextField xInitial = new JTextField("",5);
        JLabel labelForYInitial = new JLabel("Y Initial");
        JTextField yInitial = new JTextField("",5);
        JLabel labelForXFinal = new JLabel("X Final");
        JTextField xFinal = new JTextField("",5);
        JLabel labelForYFinal = new JLabel("Y Final");
        JTextField yFinal = new JTextField("",5);
        JLabel labelForRadius = new JLabel("Radius");
        JTextField radius = new JTextField("",5);
        buttonsPanel.add(labelForXInitial);
        buttonsPanel.add(xInitial);
        buttonsPanel.add(labelForYInitial);
        buttonsPanel.add(yInitial);
        buttonsPanel.add(labelForXFinal);
        buttonsPanel.add(xFinal);
        buttonsPanel.add(labelForYFinal);
        buttonsPanel.add(yFinal);
        buttonsPanel.add(labelForRadius);
        buttonsPanel.add(radius);
        //DDA
        JButton dda = new JButton("DDA");
        buttonsPanel.add(dda);
        addActionListenerDDA(xInitial,yInitial, xFinal, yFinal, dda, graphicLine);

        //Forca Bruta
        JButton bruteForce = new JButton("Forca Bruta");
        buttonsPanel.add(bruteForce);
        addActionListenerBruteForce(xInitial, yInitial, xFinal, yFinal, bruteForce, graphicLine);

        //Bresenham
        JButton bresenham = new JButton("Bresenham");
        buttonsPanel.add(bresenham);
        addActionListenerBresenham(xInitial, yInitial, xFinal, yFinal, bresenham, graphicLine);

        //Circunferencia
        JButton circunferencia = new JButton("Circunferencia");
        buttonsPanel.add(circunferencia);
        addActionListenerCircunferencia(radius, circunferencia, graphicLine);

        //Clear
        JButton clearButton = new JButton("Clear");

        buttonsPanel.add(clearButton);

        clearButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                graphicLine.clearLines();
            }
        });

    }

    private static void addActionListenerBresenham(final JTextField xInitial, final JTextField yInitial, final JTextField xFinal, final JTextField yFinal, JButton bresenham, final GraphicLine graphicLine) {
        bresenham.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valorXInitial = Integer.parseInt(xInitial.getText());
                valorXFinal = Integer.parseInt(xFinal.getText());
                valorYInitial = Integer.parseInt(yInitial.getText());
                valorYFinal = Integer.parseInt(yFinal.getText());
                bresenham(valorXInitial, valorYInitial, valorXFinal, valorYFinal, graphicLine);
            }
        });
    }

    private static void bresenham(int valorXInitial, int valorYInitial, int valorXFinal, int valorYFinal, GraphicLine graphicLine) {
        int variableX = valorXInitial;
        int variableY = valorYInitial;
        int differenceX = valorXFinal - valorXInitial;
        int differenceY = valorYFinal - valorYInitial;
        int error = 2*differenceY - differenceX;
        int inclinationEast = 2*differenceY;
        int inclinationNorthEast = 2 * (differenceY-differenceX);
        graphicLine.addLine(convertToMatrixX(variableX), convertToMatrixY(variableY), convertToMatrixX(variableX), convertToMatrixY(variableY));
        while(variableX < valorXFinal){
            if(error < 0){
                error = error + inclinationEast;
                variableX = variableX + 1;
            }else{
                error = error + inclinationNorthEast;
                variableX = variableX + 1;
                variableY = variableY + 1;
            }
            graphicLine.addLine(convertToMatrixX(variableX), convertToMatrixY(variableY), convertToMatrixX(variableX), convertToMatrixY(variableY));
        }
    }

    private static void addActionListenerBruteForce(final JTextField xInitial, final JTextField yInitial, final JTextField xFinal, final JTextField yFinal, JButton bruteForce, final GraphicLine graphicLine) {
        bruteForce.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valorXInitial = Integer.parseInt(xInitial.getText());
                valorXFinal = Integer.parseInt(xFinal.getText());
                valorYInitial = Integer.parseInt(yInitial.getText());
                valorYFinal = Integer.parseInt(yFinal.getText());
                bruteForce(valorXInitial, valorYInitial, valorXFinal, valorYFinal, graphicLine);
            }
        });
    }

    private static void addActionListenerCircunferencia(final JTextField radius, JButton circunferencia, final GraphicLine graphicLine) {
        circunferencia.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                circRadius = Integer.parseInt(radius.getText());
                circunference(circRadius, graphicLine);
            }
        });
    }

    private static void circunference(int radius, GraphicLine graphicLine) {
        int variableX = 0;
        int variableY = radius;
        int diametro = 1 - radius;
        int deltaE = 3;
        int deltaSE = -2*radius + 5;

        drawCircle(variableX, variableY, graphicLine);

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
            drawCircle(variableX, variableY, graphicLine);
        }
    }

    private static void drawCircle(int valorXInitial, int valorYInitial, GraphicLine graphicLine) {
        //The Convert To Matrix Method is called to transform the coordinate into a center 0,0 coordinate
        graphicLine.addLine(convertToMatrixX(valorXInitial), convertToMatrixY(valorYInitial),convertToMatrixX(valorXInitial), convertToMatrixY(valorYInitial));
        graphicLine.addLine(convertToMatrixX(valorXInitial), convertToMatrixY(-valorYInitial), convertToMatrixX(valorXInitial), convertToMatrixY(-valorYInitial));
        graphicLine.addLine(convertToMatrixX(-valorXInitial), convertToMatrixY(valorYInitial), convertToMatrixX(-valorXInitial), convertToMatrixY(valorYInitial));
        graphicLine.addLine(convertToMatrixX(-valorXInitial), convertToMatrixY(-valorYInitial), convertToMatrixX(-valorXInitial), convertToMatrixY(-valorYInitial));
        graphicLine.addLine(convertToMatrixY(valorYInitial), convertToMatrixX(valorXInitial), convertToMatrixY(valorYInitial), convertToMatrixX(valorXInitial));
        graphicLine.addLine(convertToMatrixY(valorYInitial), convertToMatrixX(-valorXInitial), convertToMatrixY(valorYInitial), convertToMatrixX(-valorXInitial));
        graphicLine.addLine(convertToMatrixY(-valorYInitial), convertToMatrixX(valorXInitial), convertToMatrixY(-valorYInitial), convertToMatrixX(valorXInitial));
        graphicLine.addLine(convertToMatrixY(-valorYInitial), convertToMatrixX(-valorXInitial), convertToMatrixY(-valorYInitial), convertToMatrixX(-valorXInitial));
    }

    private static void addActionListenerDDA(final JTextField xInitial, final JTextField yInitial, final JTextField xFinal, final JTextField yFinal, JButton dda, final GraphicLine graphicLine) {
        dda.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                valorXInitial = Integer.parseInt(xInitial.getText());
                valorXFinal = Integer.parseInt(xFinal.getText());
                valorYInitial = Integer.parseInt(yInitial.getText());
                valorYFinal = Integer.parseInt(yFinal.getText());
                DDA(valorXInitial, valorYInitial, valorXFinal, valorYFinal, graphicLine);
            }
        });
    }

    private static void DDA(int valorXInitial, int valorYInitial, int valorXFinal, int valorYFinal, GraphicLine graphicLine) {
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
            graphicLine.addLine(convertToMatrixX(variableX), convertToMatrixY(variableY), convertToMatrixX(variableX), convertToMatrixY(variableY));
            variableX += deltaX;
            variableY += deltaY;
        }
    }

    private static void bruteForce(int valorXInitial, int valorYInitial, int valorXFinal, int valorYFinal, GraphicLine graphicLine) {
        int differenceX = invertSignalNegativePoints(valorXFinal - valorXInitial);
        int differenceY = invertSignalNegativePoints(valorYFinal - valorYInitial);
        int variableX;
        int variableY;

        int lineCoefficient = differenceY / differenceX;

        int beta = valorYInitial - (lineCoefficient * valorXInitial);

        variableX = valorXInitial;

        while (variableX != valorXFinal) {
            variableY = variableX * lineCoefficient + beta;
            graphicLine.addLine(convertToMatrixX(variableX), convertToMatrixY(variableY),convertToMatrixX(variableX), convertToMatrixY(variableY));
            variableX++;
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