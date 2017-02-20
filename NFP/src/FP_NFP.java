

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

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
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextBoundsType;
import javafx.scene.transform.Rotate;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;


import java.util.ArrayList;
import java.util.Iterator;
//m,b
public class FP_NFP extends Application {

	@Override
	public void start(Stage primaryStage) {
		ArrayList<String> featureList = null;

		try {
			featureList = new ArrayList<String>();

			File fXmlFile = new File(
					"C:/Users/Hari/workspace_featureIDE/Chart/model.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = null;

			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = null;
			doc = dBuilder.parse(fXmlFile);

			doc.getDocumentElement().normalize();
			System.out.println("Root element :"
					+ doc.getDocumentElement().getNodeName());
			
			NodeList nList = doc.getElementsByTagName("feature");
			System.out.println("and has size ..." + nList.getLength());

			for (int temp = 0; temp < nList.getLength(); temp++) {

				org.w3c.dom.Node nNode = nList.item(temp);
				/*System.out.println("\nCurrent Element :" +
				   nNode.getNodeName());*/
				if (nNode.getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					System.out.println("feature : "+ eElement.getAttribute("name"));
					featureList.add(eElement.getAttribute("name"));
				}
			}

			Group root = new Group();
			Draw draw=new Draw();

			root.getChildren().add(
					draw.semiRing(300, 300, 80, 55, Color.rgb(0, 100, 45),
							Color.WHITE, false));
			root.getChildren().add(
					draw.semiRing(300, 300, 105, 80, Color.rgb(217, 192, 7),
							Color.WHITE, false));

			root.getChildren().add(
					draw.semiRing(300, 300, 130, 105, Color.rgb(248, 223, 36),
							Color.WHITE, false));
			root.getChildren().add(
					draw.semiRing(300, 300, 155, 130, Color.rgb(251, 235, 117),
							Color.WHITE, false));

			root.getChildren().add(
					draw.semiRing(300, 300, 180, 155, Color.rgb(253, 243, 173),
							Color.WHITE, false));
			root.getChildren().add(
					draw.semiRing(300, 300, 205, 180, Color.rgb(192, 0, 0),
							Color.WHITE, false));

			root.getChildren().add(
					draw.semiRing(300, 300, 80, 55, Color.rgb(211, 200, 222),
							Color.WHITE, true));
			root.getChildren().add(
					draw.semiRing(300, 300, 105, 80, Color.rgb(191, 177, 207),
							Color.WHITE, true));

			root.getChildren().add(
					draw.semiRing(300, 300, 130, 105, Color.rgb(165, 146, 188),
							Color.WHITE, true));
			root.getChildren().add(
					draw.semiRing(300, 300, 155, 130, Color.rgb(146, 122, 174),
							Color.WHITE, true));

			root.getChildren().add(
					draw.semiRing(300, 300, 180, 155, Color.rgb(124, 96, 158),
							Color.WHITE, true));
			root.getChildren().add(
					draw.semiRing(300, 300, 205, 180, Color.rgb(96, 74, 123),
							Color.WHITE, true));

			root.getChildren().add(
					draw.arc(26, Color.rgb(185, 205, 229), 300, 300, 234, 234, 170,
							110));
			root.getChildren().add(
					draw.arc(26, Color.rgb(183, 222, 232), 300, 300, 234, 234, 84,
							102));
			root.getChildren().add(
					draw.arc(26, Color.rgb(230, 185, 184), 300, 300, 234, 234, 270,
							180));

			root.getChildren().add(
					draw.rect(67, 300, 22, 22, Color.rgb(185, 205, 229),
							null,0));

			root.getChildren().add(
					draw.line(300, 300, 66, 300, Color.WHITE,2,false)		
			);
			root.getChildren().add(
					draw.line(300, 300, 300, 534, Color.WHITE, 
					2,false));
			root.getChildren().add(
					draw.line(300, 300, 300, 66, Color.WHITE, 
					2,false));
			
			int i = 0;
			for (int j= 0; j<featureList.size(); j++) {
				if(i%2 == 0)
					i = j*20;
				else
					i = -j*20;
				root.getChildren().add(
						draw.drawText(380,150+i,7 ,null,null, 390, 141+i,0,featureList.get(j)));
			}
			

			/*root.getChildren().add(
					drawText(246, 163, 7, 190, 103, 45, "Security"));*/

			root.getChildren().add(
					draw.drawText(0, 0, 0,null,null, 579, 444, 0, "Qualitative NFPs"));
			root.getChildren().add(
					draw.drawText(0, 0, 0, null,null,579, 475, 0, "Quantitative NFPs"));
			root.getChildren().add(
					draw.drawText(0, 0, 0, null,null,579, 506, 0, "Functional Features"));
			root.getChildren().add(
					draw.drawText(0, 0, 0, null,null,276, 288, 0, "Sensor"));

			System.out.println("----------------------------");

			root.getChildren().add(
					draw.rect(537, 439, 26, 35, Color.rgb(183, 222, 232),null,0));
			root.getChildren().add(
					draw.rect(537, 470, 26, 35, Color.rgb(185, 205, 229),null,0));
			root.getChildren().add(
					draw.rect(537, 501, 26, 35, Color.rgb(230, 185, 184),null,0));

			Scene scene = new Scene(root, 300, 250);
			scene.setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent mouseEvent) {
					System.out.println("X: " + mouseEvent.getX() + " Y: "
							+ mouseEvent.getY());
				}
			});
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}



	public static void main(String[] args) {
		launch(args);
	}
}