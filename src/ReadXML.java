import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.soap.Text;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class ReadXML { 
	int x=7000; //市场开发管理7000 采购方式评审 16000 费就无聊处理 14000 物资额定 9000 项目投标管理 15500
	int y=25000;//25000 11000 14500 16500 11000
	public List xmlData(String url){
        List<Map<String, Object>> xmllist = new ArrayList<Map<String, Object>>();		
		File file1 = new File(url);
		String xmlpath=file1.toString();
		 try {
			  xmlpath = URLDecoder.decode(xmlpath, "utf-8");
		} catch (UnsupportedEncodingException e1) {
			      e1.printStackTrace();
		}
		SAXBuilder builder=new SAXBuilder(false);
		try {
			  Document doc=builder.build(xmlpath);
			  Element root=doc.getRootElement();			 
			  List shapeslist=root.getChildren();
			  //System.out.println(shapeslist.size());
			 
			 //for (int i = 0; i < shapeslist.size()-1; i++) {
				 for (int i = 0; i < shapeslist.size(); i++) {
				 //List tmplist = new ArrayList();
				 
				  Element shapes = (Element) shapeslist.get(i);  
				  List shapelist=shapes.getChildren();
				  for (Iterator iter = shapelist.iterator(); iter.hasNext();) {
					  Map aMap = new HashMap<String, Object>();
					  Element shape = (Element) iter.next();
					  String ID = shape.getAttributeValue("ID");
					  //String NameU= shape.getAttributeValue("NameU");					  
					  //System.out.println("id: "+ID);
					  aMap.put("ID", ID);
					  aMap.put("UniqueID", shape.getAttributeValue("UniqueID"));
					  //aMap.put("NameU", NameU);
					  
					String NameU=shape.getAttributeValue("NameU");
				    aMap.put("NameU", NameU.substring(0, NameU.lastIndexOf(".")));
					
					  
					  //tmplist.add(ID);
					  //System.out.println("NameU:"+aMap.get("NameU").toString());
					  //aMap.put("NameU", NameU);
					  List celllist=shape.getChildren();
					  for (Iterator iter1 = celllist.iterator(); iter1.hasNext();) {
						  Element Cell = (Element) iter1.next();
						  //System.out.println(Cell.getName());
						  if(Cell.getName().equals("Cell")){
							  String NN=Cell.getAttributeValue("N");
							  //System.out.println(NN);
							  String VV=Cell.getAttributeValue("V");
							  
							  if(NN.equals("PinX")){
								  //System.out.println("PinX:"+VV);
								  Double pinx_temp=Double.parseDouble(VV)*96-x;
								  aMap.put("PinX", pinx_temp);
							  }
							  if(NN.equals("PinY")){
								  //System.out.println("PinY:"+VV);
								  Double piny_temp=Double.parseDouble(VV)*96-y;
								  aMap.put("PinY", piny_temp);
							  }
							  if(NN.equals("Width")){
								  //System.out.println("Width:"+VV);
								  Double width_temp=Double.parseDouble(VV)*96;
								 aMap.put("Width", width_temp);
							  }
							  
							  if(NN.equals("Height")){
								  //System.out.println("Height:"+VV);
								  Double heigh_temp=Double.parseDouble(VV)*96;
								  aMap.put("Height", heigh_temp);
							  }
							  if(NN.equals("BeginX")){
								  //System.out.println("BeginX:"+VV);
								  Double Beginx_temp=Double.parseDouble(VV)*96-x;
								  aMap.put("BeginX", Beginx_temp);
							  }
							  if(NN.equals("BeginY")){
								  //System.out.println("BeginY:"+VV);
								  Double BeginY_temp=Double.parseDouble(VV)*96-y;
								  aMap.put("BeginY", BeginY_temp);
							  }
							  if(NN.equals("EndX")){
								  //System.out.println("EndX:"+VV);
								  Double EndX_temp=Double.parseDouble(VV)*96-x;
								  aMap.put("EndX", EndX_temp);
							  }
							  if(NN.equals("EndY")){
								 // System.out.println("EndY:"+VV);
								  Double EndY_temp=Double.parseDouble(VV)*96-y;
								  aMap.put("EndY", EndY_temp);
							  }
							  if(NN.equals("TxtPinY")){
								  //System.out.println("BeginY:"+VV);
								 Double TxtPinY_temp=0.0;
								  if(aMap.containsKey("BeginY")){
									  TxtPinY_temp=Double.parseDouble(VV)*96+Double.valueOf(aMap.get("BeginY").toString());
								  }else {
									  TxtPinY_temp=Double.parseDouble(VV)*96;
								}
								  //Double TxtPinY_temp=Double.parseDouble(VV)*96+Double.valueOf(aMap.get("BeginY").toString());
								  aMap.put("TxtPinY", TxtPinY_temp);
							  }
							  if(NN.equals("TxtPinX")){
								  //System.out.println("BeginY:"+VV);
								  Double TxtPinX_temp=0.0;
								  if(aMap.containsKey("BeginX")){
									  TxtPinX_temp=Double.parseDouble(VV)*96+Double.valueOf(aMap.get("BeginX").toString());
								  }else {
									  TxtPinX_temp=Double.parseDouble(VV)*96;
								}
								  //Double TxtPinX_temp=Double.parseDouble(VV)*96+Double.valueOf(aMap.get("BeginX").toString());
								  aMap.put("TxtPinX", TxtPinX_temp);
							  }
							  if(NN.equals("LineColor")){
								  //System.out.println("PinY:"+VV);
								  aMap.put("LineColor", VV);
							  }
							  if(NN.equals("FillForegnd")){
								  //System.out.println("PinY:"+VV);
								  aMap.put("FillForegnd", VV);
							  }
							  if(NN.equals("FillBkgnd")){
								  //System.out.println("PinY:"+VV);
								  aMap.put("FillBkgnd", VV);
							  }
							  if(NN.equals("Relationships")){
								  //System.out.println("PinY:"+VV);
								  //System.out.println("Rrrr:");
								  String FF=Cell.getAttributeValue("F");
								  String R=FF.substring(FF.indexOf(".")+1, FF.lastIndexOf("!"));
								 // System.out.println("Rrrr:"+R);
								  aMap.put("Relationships", R);
							  }
							  if(NN.equals("BegTrigger")){
									 // System.out.println("EndY:"+VV);
								  String FF = Cell.getAttributeValue("F");
								  String R=FF.substring(FF.indexOf(".")+1, FF.lastIndexOf("!"));
									  aMap.put("BegTrigger", R);
								  }
							  if(NN.equals("EndTrigger")){
									 // System.out.println("EndY:"+VV);
								  String FF = Cell.getAttributeValue("F");
								  String R=FF.substring(FF.indexOf(".")+1, FF.lastIndexOf("!"));
									  aMap.put("EndTrigger", R);
								  }	
							  /*if(NN.equals("TxtHeight")){
									 // System.out.println("EndY:"+VV);
								  
							     Double height_temp=Double.parseDouble(aMap.get("Height").toString());
							     Double piny_temp=Double.parseDouble(aMap.get("PinY").toString());
							     float up =(float)(piny_temp+height_temp/2);
							     float down =(float)(piny_temp-height_temp/2);
									  aMap.put("UpY",String.valueOf(up));
									  aMap.put("DownY", String.valueOf(down));
								  }	*/
							  /*System.out.println(aMap.get("ID").toString());
							  System.out.println(aMap.get("Height").toString());
								     Double height_temp=Double.parseDouble(aMap.get("Height").toString());
							  System.out.println("222");
								     Double piny_temp=Double.parseDouble(aMap.get("PinY").toString());
								     float up =(float)(piny_temp+height_temp/2);
								     float down =(float)(piny_temp-height_temp/2);
										  aMap.put("UpY",String.valueOf(up));
										  aMap.put("DownY", String.valueOf(down));*/
							
							     
						  }
						  if(Cell.getName().equals("Text")){
							  //System.out.println("Text:"+Cell.getText().trim());
							  aMap.put("Text", Cell.getText().trim());
						  }	
						  /*if(Cell.getName().equals("Relationships")){
							  //System.out.println("Text:"+Cell.getText().trim());
							  System.out.println("Rrrr:");
							  String FF=Cell.getAttributeValue("F");
							  String R=FF.substring(FF.indexOf(".")+1, FF.lastIndexOf("!"));
							  System.out.println("Rrrr:"+R);
							  aMap.put("Relationships", R);
						  }	*/
						  if(Cell.getName().equals("Section")){
							  int point_number=0;
							  //System.out.println("Text:"+Cell.getText().trim());
							  String NN=Cell.getAttributeValue("N");
							  if(NN.equals("Geometry")){
								 
									 // System.out.println("EndY:"+VV);
								  List ListRow=Cell.getChildren();
								  //System.out.println(ListRow.size());
								  for (Iterator iter2 = ListRow.iterator(); iter2.hasNext();){
									  Element Row = (Element) iter2.next();
									  List Movellist=Row.getChildren();									  
									  for (Iterator iter3 = Movellist.iterator(); iter3.hasNext();){
										  Element cell = (Element) iter3.next();
										  String ix=Row.getAttributeValue("IX");
										  String NNN=cell.getAttributeValue("N");
										  String T=Row.getAttributeValue("T");
										  double V=Double.valueOf(cell.getAttributeValue("V"))*96;
										  double VV=0.0;
										  if(NNN.equals("X")&&aMap.containsKey("BeginX")){
											  VV=V+Double.valueOf(aMap.get("BeginX").toString());
										  }else if(NNN.equals("X")&&aMap.containsKey("BeginY")){
											 VV=V+Double.valueOf(aMap.get("BeginY").toString());
										}else {
											VV=V;
										}
										  //String V=cell.getAttributeValue("V");
										  //System.out.println(T+NNN+V);
										  point_number=Integer.parseInt(ix);
										  aMap.put(ix+NNN, VV);
										 // System.out.println(NNN+":"+V);
									  }
								  }
								  //aMap.put("Section", Cell);
								  }	
							  aMap.put("line_point_number",point_number);//拐点个数
							  
							  //System.out.println("..........."+":"+aMap.get("ID")+"...."+point_number);
						  }
					  }
					  //System.out.println(aMap.get("ID").toString());
					  //System.out.println(aMap.get("Height").toString());
					  Double height_temp=0.0;
					  try {
						  height_temp=Double.parseDouble(aMap.get("Height").toString());
					} catch (Exception e) {
						// TODO: handle exception
						height_temp=Double.parseDouble("0");
					}
						     //Double height_temp=Double.parseDouble(aMap.get("Height").toString());
					 // System.out.println("222");
						     Double piny_temp=Double.parseDouble(aMap.get("PinY").toString());
						     float up =(float)(piny_temp+height_temp/2);
						     float down =(float)(piny_temp-height_temp/2);
								  aMap.put("UpY",String.valueOf(up));
								  aMap.put("DownY", String.valueOf(down));
					  xmllist.add(aMap);
				  }
				  
				  
				  System.out.println(xmllist.size());
			  }
		
			} catch (JDOMException e) {
		    e.printStackTrace();
			} catch (IOException e) {
			}
		return xmllist;
	}	
}
