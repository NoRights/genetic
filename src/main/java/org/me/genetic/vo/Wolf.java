package org.me.genetic.vo;

import org.me.genetic.Genetic;
import org.me.genetic.tools.GeneticUtils;

import java.awt.*;
import java.util.Collections;
import java.util.List;

import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;

public class Wolf {

    private List<Line> lines;
    private Color color;
    private int score = 1000;

    private Wolf(List<Line> lines, Color color){
        this.color=color;
        this.lines=lines;
    }

    public static Wolf createWolf(List<Line> lines){
        return new Wolf(lines,BLUE);
    }

    public static Wolf createWolf(List<Line> lines,Color color){
        return new Wolf(lines,color);
    }

    public static Wolf zeroWolf(){
        return Wolf.createWolf(Collections.singletonList(Line.zeroLine()),BLACK);
    }

    public void hunt(Graphics g, Sheep sheep){
        drawLines(g);
        calculateScore(sheep);
    }

    public void drawLines(Graphics g){
        g.setColor(color);
        lines
            .forEach(line -> line.draw(g));
    }

    public void recalculateLines(){
        lines.get(0).x1(Genetic.width/2);
        lines.get(0).y1(Genetic.height/2);
        lines.get(0).recalculateLine();

        for(int i = 1; i<10; i++){
            lines
                .set(i,Line.createLine(lines.get(i-1).x2(),lines.get(i-1).y2(),lines.get(i).angle()));
        }
    }

    public List<Line> getLines(){
        return lines;
    }

    public Color getColor() {
        return color;
    }

    public int getScore() {
        return score;
    }

    public void color(Color color){
        this.color = color;
    }

    private void calculateScore(Sheep sheep) {
        Line lastLine = lines.stream().reduce((a,b)->b).orElseGet(()->Line.zeroLine());
        this.score = GeneticUtils.calculateHypotenuse(lastLine.x2()- sheep.x(),lastLine.y2()-sheep.y());
    }

}
