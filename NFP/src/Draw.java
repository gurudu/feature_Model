

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.FillRule;
import javafx.scene.shape.Line;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import javafx.scene.shape.VLineTo;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;

public class Draw {

	
public Node rect(int CenterX,int centerY,int height,int width,Color fillColor,Color strkColor,int strkWidth){
	    	Rectangle r = new Rectangle();
	    	  r.setX(CenterX);
	          r.setY(centerY); 
	          r.setHeight(height);
	          r.setWidth(width);
	    	  r.setFill(fillColor);
	    	  r.setStrokeWidth(strkWidth);
	    	  r.setStroke(strkColor);
	    	return r;
	    }
public Node drawText(int circleX, int circleY,int radius,Color strkColor,Color fillColor,int textX,int textY, int angle,String text) {
	
	 Pane pane = new Pane();
    Circle c=new Circle();
    c.setCenterX(circleX-5);
    c.setCenterY(circleY);
    c.setRadius(radius);
    c.setStroke(strkColor);
    c.setStrokeWidth(2);
    c.setFill(fillColor);
   Text textNode = new Text(text);
   if(text=="Qualitative"||text=="Quantitative"||text=="Zones"||text=="LEGEND"||
   		text=="Relation Types"||text=="Functional Features"||text=="Non-Functional Features")
   textNode.setFont(Font.font("Calibri", FontWeight.BOLD,21));
   else{
   textNode.setFont(Font.font ("Calibri",19));
   }
   textNode.setBoundsType(TextBoundsType.VISUAL);
   textNode.relocate(textX,textY);
   textNode.getTransforms().add(new Rotate(angle));
   pane.getChildren().add(textNode);
   pane.getChildren().add(c);
   return pane;
}

	public Node line(int startX, int startY, int endX, int endY, Color strkColor, int strkWidth,boolean b) {

	    Line line = new Line();
	   line.setStartX(startX);
	   line.setStartY(startY);
	   line.setEndX(endX);
	   line.setEndY(endY);
	   line.setStroke(strkColor);
	   line.setStrokeWidth(strkWidth);
	   if(b)
	   line.getStrokeDashArray().add(8d);
	   return line;
	}
	public Node arc(int strkWidth, Color strkColor, int centerX, int centerY,
			int radiusX, int radiusY, int strtAngle, int strtLength) {
		Arc arc = new Arc();
		arc.setFill(null);

		arc.setType(ArcType.OPEN);
		arc.setStrokeWidth(strkWidth);
		arc.setStroke(strkColor);
		arc.setStrokeType(StrokeType.INSIDE);

		arc.setCenterX(centerX);
		arc.setCenterY(centerY);
		arc.setRadiusX(radiusX);
		arc.setRadiusY(radiusY);
		arc.setStartAngle(strtAngle);
		arc.setLength(strtLength);
		return arc;
	}

	public Path semiRing(double centerX, double centerY, double radius,
			double innerRadius, Color bgColor, Color strkColor, boolean b) {
		Path path = new Path();
		path.setFill(bgColor);
		path.setStroke(strkColor);
		if (radius <= 205)
			path.setStrokeWidth(2);
		else
			path.setStrokeWidth(4);
		path.setFillRule(FillRule.EVEN_ODD);

		MoveTo moveTo = new MoveTo();
		moveTo.setX(centerX);
		moveTo.setY(centerY + innerRadius);

		ArcTo arcToInner = new ArcTo();
		arcToInner.setX(centerX);
		arcToInner.setY(centerY - innerRadius);
		arcToInner.setRadiusX(innerRadius);
		arcToInner.setRadiusY(innerRadius);
		arcToInner.setSweepFlag(b);

		MoveTo moveTo2 = new MoveTo();
		moveTo2.setX(centerX);
		moveTo2.setY(centerY + innerRadius);

		VLineTo vLineToRightLeg = new VLineTo();
		vLineToRightLeg.setY(centerY + radius);

		ArcTo arcTo = new ArcTo();
		arcTo.setX(centerX);
		arcTo.setY(centerY - radius);
		arcTo.setRadiusX(radius);
		arcTo.setRadiusY(radius);
		arcTo.setSweepFlag(b);

		VLineTo vLineToLeftLeg = new VLineTo();
		vLineToLeftLeg.setY(centerY - innerRadius);

		path.getElements().add(moveTo);
		path.getElements().add(arcToInner);
		path.getElements().add(moveTo2);
		path.getElements().add(vLineToRightLeg);
		path.getElements().add(arcTo);
		path.getElements().add(vLineToLeftLeg);
		return path;
	}
	
}
