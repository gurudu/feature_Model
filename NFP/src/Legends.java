

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Legends extends Application {
	public static void main(String[] args) {
	       Application.launch(args);
	   }
	
public void start(Stage primaryStage) {
      

    Group root = new Group();
    Draw draw=new Draw();
    
    //Non-Functional legends
    root.getChildren().add(
    		draw.rect(149,89,220,390,Color.WHITE,Color.BLACK,2));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,222,12,0, "Non-Functional Features"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,149,65,0, "LEGEND"));
    
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,161,102,0, "Quantitative"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,309,102,0, "Zones"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,390,102,0, "Qualitative"));
    
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,194,140,0, "Low"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,194,265,0, "High"));
 
    
    root.getChildren().add(
    		draw.rect(316,138,22,22,Color.rgb(211,200,222),null,0));
    root.getChildren().add(
    		draw.rect(316,162,22,22,Color.rgb(191,177,207),null,0));
    root.getChildren().add(
    		draw.rect(316,186,22,22,Color.rgb(165,146,188),null,0));
    
    root.getChildren().add(
    		draw.rect(316,210,22,22,Color.rgb(146,122,174),null,0));
    root.getChildren().add(
    		draw.rect(316,234,22,22,Color.rgb(124,96,158),null,0));
    root.getChildren().add(
    		draw.rect(316,258,22,22,Color.rgb(96,74,123),null,0));
    
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,393,140,0, "High positive"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,393,165,0, "Medium positive"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,393,188,0, "Low positive"));
    
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,393,211,0, "Low negative"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,393,234,0, "Medium negative"));
    root.getChildren().add(draw.drawText(0, 0,0,null,null,393,258,0, "High negative"));
    
    root.getChildren().add(
    		draw.line(207,163,207,255,Color.BLACK,2,true));
    root.getChildren().add(
    		draw.line(199,247,207,255,Color.BLACK,2,false));
    root.getChildren().add(
    		draw.line(215,247,207,255,Color.BLACK,2,false));
    
    //Functional legends
    
    root.getChildren().add(
    		draw.rect(149,450,220,390,Color.WHITE,Color.BLACK,2));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,222,366,0, "Functional Features"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,149,428,0, "LEGEND"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,171,463,0, "Zones"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,349,463,0, "Relation Types"));

    root.getChildren().add(
    		draw.rect(183,502,22,22,Color.rgb(0,100,45),null,0));
    root.getChildren().add(
    		draw.rect(183,526,22,22,Color.rgb(217,192,7),null,0));
    root.getChildren().add(
    		draw.rect(183,550,22,22,Color.rgb(248,223,36),null,0));
    
    root.getChildren().add(
    		draw.rect(183,574,22,22,Color.rgb(251,235,117),null,0));
    root.getChildren().add(
    		draw.rect(183,598,22,22,Color.rgb(253,243,173),null,0));
    root.getChildren().add(
    		draw.rect(183,622,22,22,Color.rgb(192,0,0),null,0));
    

    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,217,504,0, "Requires"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,217,529,0, "Encourages"));
  
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,217,602,0, "Discourages"));
    root.getChildren().add(
    		draw.drawText(0, 0,0,null,null,217,627,0, "Excludes"));
    
    root.getChildren().add(
    		draw.line(253,593,253,552,Color.BLACK,2,true));
    root.getChildren().add(
    		draw.line(247,560,253,552,Color.BLACK,2,false));
    root.getChildren().add(
    		draw.line(260,560,253,552,Color.BLACK,2,false));
    

    root.getChildren().add(
    		draw.drawText(364,515,7,Color.BLACK,Color.WHITE,383,508,0, "Formalized"));
    root.getChildren().add(
    		draw.drawText(364,515,3,Color.BLACK,Color.BLACK,0,0,0, ""));
    
    root.getChildren().add(
    		draw.drawText(364,556,7,Color.BLACK,Color.WHITE,383,549,0, "Inferred"));
    
    Polygon polygon = new Polygon();
    polygon.getPoints().addAll(new Double[]{
        363.0, 556.0,
        356.0, 562.0,
        356.0, 549.0 });

    root.getChildren().add(polygon);
    
    root.getChildren().add(
    		draw.drawText(364,597,7,Color.BLACK,Color.WHITE,383,590,0, "Undefined"));
    
   

    Scene scene = new Scene(root, 300, 250);
    scene.setOnMousePressed(new EventHandler<MouseEvent>( ) {
  	   public void handle (MouseEvent mouseEvent) {
  	   System.out.println("X: " + mouseEvent.getX() + " Y: " + mouseEvent.getY());
  	   }
  	   });

    primaryStage.setScene(scene);
    primaryStage.show();
}
}