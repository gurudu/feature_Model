
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.shape.VLineTo;
import javafx.scene.shape.MoveTo;
import javafx.stage.Stage;
import javafx.scene.shape.Path;
import javafx.scene.shape.Line;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.Circle;
import javafx.scene.control.ColorPicker;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;


public class NFP extends Application {
   
	
    @Override
    public void start(Stage primaryStage) {

        Group root = new Group();
        Draw draw=new Draw();
        
        root.getChildren().add(
        		draw.semiRing(300, 300, 80, 55, Color.rgb(211,200,222), Color.WHITE,false));
        root.getChildren().add(
        		draw.semiRing(300, 300, 105, 80,Color.rgb(191,177,207), Color.WHITE,false));
        
        root.getChildren().add(
        		draw.semiRing(300, 300, 130, 105, Color.rgb(165,146,188), Color.WHITE,false));
        root.getChildren().add(
        		draw.semiRing(300, 300, 155, 130,Color.rgb(146,122,174), Color.WHITE,false));
        
        root.getChildren().add(
        		draw.semiRing(300, 300, 180, 155, Color.rgb(124,96,158), Color.WHITE,false));
        root.getChildren().add(
        		draw.semiRing(300, 300, 205, 180,Color.rgb(96,74,123), Color.WHITE,false));
        
     
            
        root.getChildren().add(
        		draw.semiRing(300, 300, 80, 55, Color.rgb(211,200,222), Color.WHITE,true));
        root.getChildren().add(
        		draw.semiRing(300, 300, 105,80, Color.rgb(191,177,207), Color.WHITE,true));
        
        root.getChildren().add(
        		draw.semiRing(300, 300, 130, 105, Color.rgb(165,146,188), Color.WHITE,true));
        root.getChildren().add(
        		draw.semiRing(300, 300, 155, 130, Color.rgb(146,122,174), Color.WHITE,true));
        
        root.getChildren().add(
        		draw.semiRing(300, 300, 180, 155, Color.rgb(124,96,158), Color.WHITE,true));
        root.getChildren().add(
        		draw.semiRing(300, 300, 205, 180, Color.rgb(96,74,123), Color.WHITE,true));
        
        root.getChildren().add(
        		draw.arc(26, Color.rgb(185,205,229),300,300,234,234,170,110));
        root.getChildren().add(
        		draw.arc(26,Color.rgb(183,222,232),300,300,234,234,84,102));
        root.getChildren().add(
        		draw.arc(26, Color.rgb(230,185,184),300,300,234,234,270,180));
        root.getChildren().add(
        		draw.semiRing(300, 300,264,234, Color.rgb(228,108,10), Color.WHITE,true));
        root.getChildren().add(
        		draw.arc(26, Color.WHITE,300,300,276,300,115,115));
       
   
        root.getChildren().add(
        		draw.rect(69,300,22,21,Color.rgb(185,205,229),null,0));
        
        root.getChildren().add(
        		draw.line(300,300,66,300,Color.WHITE,2,false));
        root.getChildren().add(
        		draw.line(300,300,300,534,Color.WHITE,2,false));
        root.getChildren().add(
        		draw.line(300,300,300,66,Color.WHITE,2,false));
        
        root.getChildren().add(
        		draw.drawText(246,163,7,null,null,190,103,45, "Security"));
        
        root.getChildren().add(
        		draw.drawText(0,0,0,null,null,579,444,0,"Qualitative NFPs"));
        root.getChildren().add(
        		draw.drawText(0,0,0,null,null,579,475,0, "Quantitative NFPs"));
        root.getChildren().add(
        		draw.drawText(0,0,0,null,null,579,506,0,"Functional Features"));
        
        root.getChildren().add(
        		draw.drawText(0,0,0,null,null,579,537,0,"NFPs Interdependencies"));
        root.getChildren().add(
        		draw.drawText(0,0,0,null,null,282,289,0,"Cost"));
       
        root.getChildren().add(
        		draw.rect(537,439,26,35,Color.rgb(183,222,232),null,0));
        root.getChildren().add(
        		draw.rect(537,470,26,35,Color.rgb(185,205,229),null,0));
        root.getChildren().add(
        		draw.rect(537,501,26,35,Color.rgb(230,185,184),null,0));
        root.getChildren().add(
        		draw.rect(537,532,26,35,Color.rgb(228,108,10),null,0));
        
        root.getChildren().add(
        		draw.rect(400,144,26,73,Color.WHITE,Color.BLACK,2));
        root.getChildren().add(
        		draw.drawText(390,158,7,null,null,405,148,0,"Sensor"));
        Scene scene = new Scene(root, 300, 250);
        
        scene.setOnMousePressed(new EventHandler<MouseEvent>( ) {
      	   public void handle (MouseEvent mouseEvent) {
      	   System.out.println("X: " + mouseEvent.getX() + " Y: " + mouseEvent.getY());
      	   }
      	   });

        primaryStage.setScene(scene);
        primaryStage.show();
    }
  
    public static void main(String[] args) {
        launch(args);
    }
}