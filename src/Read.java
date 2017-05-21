
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.AttributedCharacterIterator.Attribute;
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

public class Read { 
	String Shape_ID;
	String UniqueID;
	String Type;
	int inch2px_ratio=96;
	int asix_move_artio=1100;
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
			  List level_0_list=root.getChildren();
			  //对0级遍历
			  for (int i = 0; i < level_0_list.size(); i++) {
				 Element level_0_element=(Element)level_0_list.get(i);
				 List level_1_list=level_0_element.getChildren();
				 //对1级遍历
				 for(Iterator iter_1 = level_1_list.iterator(); iter_1.hasNext();){
					 Map Met_Map=new HashMap<String, Object>();
					 Element level_1_element=(Element)iter_1.next();
					 Shape_ID=level_1_element.getAttributeValue("ID");
					 UniqueID=level_1_element.getAttributeValue("UniqueID");
					 Type=level_1_element.getAttributeValue("Type");
					 Met_Map.put("ID", Shape_ID);
					 Met_Map.put("UniqueID", UniqueID);
					 Met_Map.put("Type", Type);
					if(Type.equals("Shape")){
						String NameU=level_1_element.getAttributeValue("NameU");
						String NameUn=NameU.substring(0, NameU.lastIndexOf("."));
						Met_Map.put("NameU", NameUn);
						if(NameUn.equals("Dynamic connector")){
							List level_2_list_line=level_1_element.getChildren();
							for(Iterator iter_2 = level_2_list_line.iterator(); iter_2.hasNext();){
								Element level_2_element=(Element)iter_2.next();
								if(level_2_element.getName().equals("Cell")){
									String N=level_2_element.getAttributeValue("N");								  
									String V=level_2_element.getAttributeValue("V");
									if(N.equals("PinX")){
										  //System.out.println("PinX:"+VV);
										  Double pinx_temp=Double.parseDouble(V)*inch2px_ratio;
										  Met_Map.put("PinX", pinx_temp);
									  }
									  if(N.equals("PinY")){
										  //System.out.println("PinY:"+VV);
										  Double piny_temp=Double.parseDouble(V)*inch2px_ratio;
										  Met_Map.put("PinY", -1*piny_temp+asix_move_artio);
									  }
									  if(N.equals("BeginX")){
										  //System.out.println("PinX:"+VV);
										  Double pinx_temp=Double.parseDouble(V)*inch2px_ratio;
										  Met_Map.put("BeginX", pinx_temp);
									  }
									  if(N.equals("BeginY")){
										  //System.out.println("PinX:"+VV);
										  Double piny_temp=Double.parseDouble(V)*inch2px_ratio;
										  Met_Map.put("BeginY", piny_temp);
									  }
									  if(N.equals("EndX")){
										  //System.out.println("PinX:"+VV);
										  Double pinx_temp=Double.parseDouble(V)*inch2px_ratio;
										  Met_Map.put("EndX", pinx_temp);
									  }
									  if(N.equals("EndY")){
										  //System.out.println("PinX:"+VV);
										  Double piny_temp=Double.parseDouble(V)*inch2px_ratio;
										  Met_Map.put("EndY", piny_temp);
									  }
									if(N.equals("BegTrigger")){
										 String F = level_2_element.getAttributeValue("F");
										  String R=F.substring(F.indexOf(".")+1, F.lastIndexOf("!"));
										  Met_Map.put("BegTrigger", R);
									  }
									if(N.equals("EndTrigger")){
										 String F = level_2_element.getAttributeValue("F");
										  String R=F.substring(F.indexOf(".")+1, F.lastIndexOf("!"));
										  Met_Map.put("EndTrigger", R);
									  }
									if(N.equals("TxtPinY")){
										  //System.out.println("BeginY:"+VV);
										 Double TxtPinY_temp=0.0;
										  if(Met_Map.containsKey("PinY")){
											  TxtPinY_temp=Double.parseDouble(V)*inch2px_ratio+Double.valueOf(Met_Map.get("PinY").toString());
										  }else {
											  TxtPinY_temp=Double.parseDouble(V)*inch2px_ratio;
										}
										  //Double TxtPinY_temp=Double.parseDouble(VV)*96+Double.valueOf(aMap.get("BeginY").toString());
										  Met_Map.put("TxtPinY", -1*TxtPinY_temp+asix_move_artio);
									  }
									  if(N.equals("TxtPinX")){
										  //System.out.println("BeginY:"+VV);
										  Double TxtPinX_temp=0.0;
										  if(Met_Map.containsKey("PinX")){
											  TxtPinX_temp=Double.parseDouble(V)*inch2px_ratio+Double.valueOf(Met_Map.get("PinX").toString());
										  }else {
											  TxtPinX_temp=Double.parseDouble(V)*inch2px_ratio;
										}
										  //Double TxtPinX_temp=Double.parseDouble(VV)*96+Double.valueOf(aMap.get("BeginX").toString());
										  Met_Map.put("TxtPinX", TxtPinX_temp);
									  }
									  
								}
								if(level_2_element.getName().equals("Text")){
									  //System.out.println("Text:"+Cell.getText().trim());
									  Met_Map.put("Text", level_2_element.getText().trim());
								  }	
								 if(level_2_element.getName().equals("Section")){
									  int point_number=0;
									  //System.out.println("Text:"+Cell.getText().trim());
									  String N=level_2_element.getAttributeValue("N");
									  if(N.equals("Geometry")){											
										  List List_Row=level_2_element.getChildren();
										  //System.out.print-rowln(ListRow.size());
										  for (Iterator iter_row = List_Row.iterator(); iter_row.hasNext();){
											  Element Row = (Element) iter_row.next();
											  List Movellist=Row.getChildren();									  
											  for (Iterator iter3 = Movellist.iterator(); iter3.hasNext();){
												  Element level_3_element = (Element) iter3.next();
												  String ix=Row.getAttributeValue("IX");
												  String NNN=level_3_element.getAttributeValue("N");
												  String T=Row.getAttributeValue("T");
												  double V=Double.valueOf(level_3_element.getAttributeValue("V"))*inch2px_ratio;
												  double VV=0.0;
												  if(NNN.equals("X")&&Met_Map.containsKey("BeginX")){
													  //VV=V+Double.valueOf(Met_Map.get("BeginX").toString());
													  VV=V;
												  }else if(NNN.equals("Y")&&Met_Map.containsKey("BeginY")){
													 //VV=-1*V+Double.valueOf(Met_Map.get("BeginY").toString());
													 VV=-1*V;
												}else {
													VV=V;
												}
												  //String V=cell.getAttributeValue("V");
												  //System.out.println(T+NNN+V);
												  point_number=Integer.parseInt(ix);
												  Met_Map.put(ix+NNN, VV);
												 // System.out.println(NNN+":"+V);
											  }
										  }
										  
										  }	
									  Met_Map.put("line_point_number",point_number);//拐点个数
									  
									  
								  }
							}
						}else if(NameUn.equals("Full bracket")){
							List level_2_list_bracket=level_1_element.getChildren();
							for(Iterator iter_2 = level_2_list_bracket.iterator(); iter_2.hasNext();){
								Element level_2_element=(Element)iter_2.next();
								if(level_2_element.getName().equals("Cell")){
									String N=level_2_element.getAttributeValue("N");								  
									String V=level_2_element.getAttributeValue("V");
									if(N.equals("Relationships")){
										  //System.out.println("PinY:"+VV);
										  //System.out.println("Rrrr:");
										  String F=level_2_element.getAttributeValue("F");
										  String R=F.substring(F.indexOf(".")+1, F.lastIndexOf("!"));
										 // System.out.println("Rrrr:"+R);
										  Met_Map.put("Relationships", R);
									  }
								}
								if(level_2_element.getName().equals("Text")){
									  //System.out.println("Text:"+Cell.getText().trim());
									  Met_Map.put("Text", level_2_element.getText().trim());
								  }	
								
							}
						}
					}else if(Type.equals("Group")){
						//对组合下的2级遍历
						StringBuffer child_id=new StringBuffer();
						List level_2_list=level_1_element.getChildren();
						for(Iterator iter_2 = level_2_list.iterator(); iter_2.hasNext();){
							Element level_2_element=(Element)iter_2.next();
							if(level_2_element.getName().equals("Cell")){
								 String N=level_2_element.getAttributeValue("N");								  
								 String V=level_2_element.getAttributeValue("V");
								 if(N.equals("PinX")){
									  //System.out.println("PinX:"+VV);
									  Double pinx_temp=Double.parseDouble(V)*inch2px_ratio;
									  Met_Map.put("PinX", pinx_temp);
								  }
								  if(N.equals("PinY")){
									  //System.out.println("PinY:"+VV);
									  Double piny_temp=Double.parseDouble(V)*inch2px_ratio;
									  Met_Map.put("PinY", -1*piny_temp+asix_move_artio);
								  }
								  if(N.equals("Width")){
									  //System.out.println("Width:"+VV);
									  Double width_temp=Double.parseDouble(V)*inch2px_ratio;
									  Met_Map.put("Width", width_temp);
								  }								  
								  if(N.equals("Height")){
									  //System.out.println("Height:"+VV);
									  Double heigh_temp=Double.parseDouble(V)*inch2px_ratio;
									  Met_Map.put("Height", heigh_temp);
								  }
								  
							}else if(level_2_element.getName().equals("Shapes")){
								List level_3_list=level_2_element.getChildren();
								int child_number=0;
								for(Iterator iter_3 = level_3_list.iterator(); iter_3.hasNext();){
									child_number=child_number+1;
									Element level_3_element=(Element)iter_3.next();
									child_id.append(level_3_element.getAttributeValue("ID")+",");
									List level_4_list=level_3_element.getChildren();
									Met_Map.put(String.valueOf(child_number)+"childNameU", level_3_element.getAttributeValue("NameU"));
									Met_Map.put(String.valueOf(child_number)+"childID", level_3_element.getAttributeValue("ID"));
									System.out.println(Met_Map.get("ID")+"包含:"+child_number+":"+level_3_element.getAttributeValue("ID"));
									for(Iterator iter_4 = level_4_list.iterator(); iter_4.hasNext();){
										
										Element level_4_element=(Element)iter_4.next();
										//Met_Map.put(String.valueOf(child_number)+"childNameU", level_3_element.getAttributeValue("NameU"));
										//Met_Map.put(String.valueOf(child_number)+"childID", level_3_element.getAttributeValue("ID"));
										//System.out.println(Met_Map.get("ID")+"包含:"+child_number+":"+level_3_element.getAttributeValue("ID"));
										if(level_4_element.getName().equals("Cell")){
											String N=level_4_element.getAttributeValue("N");								  
											 String V=level_4_element.getAttributeValue("V");
											 if(N.equals("PinX")){
												  //System.out.println("PinX:"+VV);
												  Double pinx_temp=Double.parseDouble(V)*inch2px_ratio;
												  Met_Map.put(String.valueOf(child_number)+"childPinX", pinx_temp);
											  }
											  if(N.equals("PinY")){
												  //System.out.println("PinY:"+VV);
												  Double piny_temp=Double.parseDouble(V)*inch2px_ratio;
												  Met_Map.put(String.valueOf(child_number)+"childPinY", -1*piny_temp+asix_move_artio);
											  }
											  if(N.equals("Width")){
												  //System.out.println("Width:"+VV);
												  Double width_temp=Double.parseDouble(V)*inch2px_ratio;
												  Met_Map.put(String.valueOf(child_number)+"childWidth", width_temp);
											  }								  
											  if(N.equals("Height")){
												  //System.out.println("Height:"+VV);
												  Double heigh_temp=Double.parseDouble(V)*inch2px_ratio;
												  Met_Map.put(String.valueOf(child_number)+"childHeight", heigh_temp);
											  }
										}else if(level_4_element.getName().equals("Text")){
											Met_Map.put(String.valueOf(child_number)+"childText", level_4_element.getText().trim());
										}
										
									}
									System.out.println(child_number);
									Met_Map.put("child_number", child_number);
								}
							}
						}
					}					 
					xmllist.add(Met_Map);
				 }
			  }
			  

		
			} catch (JDOMException e) {
		    e.printStackTrace();
			} catch (IOException e) {
			}
		return xmllist;
	}	
}

