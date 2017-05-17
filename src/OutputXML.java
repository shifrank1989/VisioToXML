import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class OutputXML {
	/*private List<String> shapes = new ArrayList<String>();  
    //初始化即将显示的在xml中的元素...  
    public void initList(){  
    	shapes.add("property");  
    	shapes.add("Text");      	  
    }  */ 
	String in="E://page7.xml";
	String out="E:\\page7OUT_sc.xml";
	String temp_ele="shape";
    public void BuildXML(){  
        String Range_width="6.889763779527552";
        String Range_heigh="7.874015748031511";
        float Range_Left_X = 0;
		float Range_Righ_X = 0;
		float Range_Up_Y = 0;
		float Range_Dw_Y = 0;
		
        //initList();  
        ReadXML readXML=new ReadXML();
        List xList=readXML.xmlData(in);
        //创建根节点...  
        Element root = new Element("modular_id").setText("NNNNN");
        
        //将根节点添加到文档中...  
        Document Doc  = new Document(root); 
        Element process_file_id = new Element("process_file_id").setText("process_file_id");
        root.addContent(process_file_id);
        //Element processElement =new Element("processElement");
    	 
       // Element processLine =new Element("processLine");
        //
        //Element processElement_chil=new Element("processElement_chil");
        //Element processLine_chil=new Element("processLine_chil");
        //获取范围框四角坐标
        for(int i = 0; i < xList.size(); i++){ 
        	Map<String, Object> map = (Map<String, Object>) xList.get(i);
        	Set<String> set = map.keySet();
        	List<String> keyList = new ArrayList<String>();
        	keyList.addAll(set);
        	/*//测试
        	if(map.get("ID").equals("8")){
        		System.out.println("8的downy："+map.get("DownY"));
        	}
        	if(map.get("ID").equals("10")){
        		System.out.println("10的upy："+map.get("UpY"));
        	}*/
        	//
        	try {
        		if(map.get("Width").equals(Range_width)&&map.get("Height").equals(Range_heigh)){
            		Range_Left_X=(float)(Double.valueOf(map.get("PinX").toString())-Double.valueOf(map.get("Width").toString())/2);
            		Range_Righ_X=(float)(Double.valueOf(map.get("PinX").toString())+Double.valueOf(map.get("Width").toString())/2);
            		Range_Up_Y=(float)(Double.valueOf(map.get("PinY").toString())+Double.valueOf(map.get("Height").toString())/2);
            		Range_Dw_Y=(float)(Double.valueOf(map.get("PinY").toString())-Double.valueOf(map.get("Height").toString())/2);
            	}
			} catch (Exception e) {
				// TODO: handle exception
			}
        	
        }
        //获取范围内所有ID
       // List ProcessElement_list_id=ProcessElement(xList, Range_Left_X, Range_Righ_X, Range_Up_Y, Range_Dw_Y);
        //for(int j = 0; j < ProcessElement_list_id.size(); j++){
        	//System.out.println("LISTSIZE"+ProcessElement_list_id.size());
        	//String ProcessElement_id=ProcessElement_list_id.get(j).toString();
        	for(int m = 0; m < xList.size(); m++){
        		String work="ttt";
        		String file="ttt";
            	Map<String, Object> map = (Map<String, Object>) xList.get(m);
            	Set<String> set = map.keySet();
            	List<String> keyList = new ArrayList<String>();
            	keyList.addAll(set);
            	Boolean b=true;
            	//Boolean a=false;
            	for(int q = 0; q < xList.size(); q++){
                	Map<String, Object> temp_map = (Map<String, Object>) xList.get(q);
                	Set<String> temp_set = temp_map.keySet();
                	List<String> temp_keyList = new ArrayList<String>();
                	temp_keyList.addAll(temp_set);
               
                	if(map.get("UpY").equals(temp_map.get("DownY"))){
                		b=false;//如果不是第一级，b为假
                		
                	}
            	}
            	/*if(map.get("ID").equals("13")){
        			//System.out.println(b);
        		}*/
            	if(b&&!map.get("NameU").equals("Full bracket")&&!map.get("NameU").equals("Dynamic connector")){
            		/*if(map.get("Text").equals("市场营销策略")){
            			System.out.println("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            		}*/
            		Element processElement_chil=new Element("process_element");
            		StringBuffer explain=new StringBuffer("ttt") ;
                	processElement_chil.addContent(new Element("key").setText(map.get("ID").toString()));
                	if(map.get("NameU").equals("Start/End")){
                		processElement_chil.addContent(new Element("category").setText("Start"));	
                	}else if(map.get("NameU").equals("Decision")){
                		processElement_chil.addContent(new Element("category").setText("JudgmentCode"));
                	}else if(map.get("NameU").equals("Subprocess")){
                		processElement_chil.addContent(new Element("category").setText("SonProcessCode"));
                	}else {
                		processElement_chil.addContent(new Element("category").setText("CommonCode"));
					}
                	
                	processElement_chil.addContent(new Element("background").setText("#ffffcc"));
                	//关联
                	for(int K=0;K<keyList.size();K++){
                		if(keyList.get(K).equals("Relationships")){
                			String Relationships_id=map.get("Relationships").toString();
                			//System.out.println("R:"+Relationships_id);
                			for(int h = 0; h < xList.size(); h++){ 
                            	Map<String, Object> map_temp_7 = (Map<String, Object>) xList.get(h);
                            	Set<String> set_temp_7 = map_temp_7.keySet();
                            	List<String> keyList_temp_7 = new ArrayList<String>();
                            	keyList_temp_7.addAll(set_temp_7);
                            	if(Relationships_id.equals(map_temp_7.get("ID"))&&map_temp_7.get("NameU").equals("Full bracket")){
                            		explain.append(map_temp_7.get("Text").toString()+";");
                            	}
                			}
                		}
                	}
                	//
                	String DownY_0=map.get("DownY").toString();
                	Double pinx_temp_0=Double.parseDouble(map.get("PinX").toString());
                	Double piny_temp_0=Double.parseDouble(map.get("PinY").toString());
                	Double Width_temp_0=Double.parseDouble(map.get("Width").toString());
                	Double Heigh_temp_0=Double.parseDouble(map.get("Height").toString());
                	Double Heigh_temp_1=0.0;
                	Double Heigh_temp_2=0.0;
                	Double pinx_temp_1=0.0;
                	Double pinx_temp_2=0.0;
                	Double piny_temp_1=0.0;
                	Double piny_temp_2=0.0;
                	int Sum=1;
                	for(int h = 0; h < xList.size(); h++){ 
                    	Map<String, Object> map_temp = (Map<String, Object>) xList.get(h);
                    	Set<String> set_temp = map_temp.keySet();
                    	List<String> keyList_temp = new ArrayList<String>();
                    	keyList_temp.addAll(set_temp);

                    	if(map_temp.get("UpY").equals(DownY_0)){
                    		Sum+=1;
                    		for(int K=0;K<keyList_temp.size();K++){
                        		if(keyList_temp.get(K).equals("Relationships")){
                        			String Relationships_id=map_temp.get("Relationships").toString();
                        			for(int o = 0; o < xList.size(); o++){ 
                                    	Map<String, Object> map_temp_5 = (Map<String, Object>) xList.get(o);
                                    	Set<String> set_temp_5 = map_temp_5.keySet();
                                    	List<String> keyList_temp_5 = new ArrayList<String>();
                                    	keyList_temp_5.addAll(set_temp_5);
                                    	//String DownY_1=map_temp_5.get("DownY").toString();
                                    	/*if(Relationships_id.equals("27")){
                                    		System.out.println(map_temp_5.get("Text").toString());
                                    	}*/
                                    	
                                    	if(Relationships_id.equals(map_temp_5.get("ID"))&&map_temp_5.get("NameU").equals("Full bracket")){
                                    		explain.append(map_temp_5.get("Text").toString()+";");
                                    	}
                        			}
                        		}
                        	}
                    		pinx_temp_1=Double.parseDouble(map_temp.get("PinX").toString());
                    		piny_temp_1=Double.parseDouble(map_temp.get("PinY").toString());
                    		Heigh_temp_1=Double.parseDouble(map_temp.get("Height").toString());
                    		String DownY_1=map_temp.get("DownY").toString(); 
                    		work=map_temp.get("Text").toString();
                    		for(int f = 0; f < xList.size(); f++){ 
                            	Map<String, Object> map_temp_2 = (Map<String, Object>) xList.get(f);
                            	Set<String> set_temp_2 = map_temp_2.keySet();
                            	List<String> keyList_temp_2 = new ArrayList<String>();
                            	keyList_temp_2.addAll(set_temp_2);
                            	
                            	
                            	/*if(map_temp.get("ID").equals("11")&&map_temp_2.get("ID").equals("13")){
                            		System.out.println("13的upy："+map_temp_2.get("UpY"));
                            		System.out.println("DownY_1："+DownY_1);
                            	}*/
                            	
                            	
                            	if(map_temp_2.get("UpY").equals(DownY_1)&&map_temp_2.get("NameU").toString().equals("Document")){
                            		Sum+=1;
                            		for(int K=0;K<keyList_temp_2.size();K++){
                                		if(keyList_temp_2.get(K).equals("Relationships")){
                                			String Relationships_id=map_temp_2.get("Relationships").toString();
                                			for(int r = 0; r < xList.size(); r++){ 
                                            	Map<String, Object> map_temp_4 = (Map<String, Object>) xList.get(r);
                                            	Set<String> set_temp_4 = map_temp_4.keySet();
                                            	List<String> keyList_temp_4 = new ArrayList<String>();
                                            	keyList_temp_4.addAll(set_temp_4);
                                            	if(Relationships_id.equals(map_temp_4.get("ID"))&&map_temp_4.get("NameU").equals("Full bracket")){
                                            		explain.append(map_temp_4.get("Text").toString()+";");
                                            	}
                                			}
                                		}
                                	}
                            		pinx_temp_2=Double.parseDouble(map_temp_2.get("PinX").toString());
                            		piny_temp_2=Double.parseDouble(map_temp_2.get("PinY").toString());
                            		Heigh_temp_2=Double.parseDouble(map_temp_2.get("Height").toString());
                            		file=map_temp_2.get("Text").toString();
                            		
                            	}
                            	
                    		}               		 
                    	}
                	}
                	double pinx=(pinx_temp_0+pinx_temp_1+pinx_temp_2)/Sum-Width_temp_0/2;
                	double piny=(piny_temp_0+piny_temp_1+piny_temp_2)/Sum-(Heigh_temp_0+Heigh_temp_1+Heigh_temp_2)/2;
                	
                	processElement_chil.addContent(new Element("loc").setText(String.valueOf((int)pinx)+" "+String.valueOf((int)piny)));
                	Element location = new Element("location");
                	location.addContent(new Element("position_x").setText(String.valueOf((int)pinx)));
                	location.addContent(new Element("position_y").setText(String.valueOf((int)piny)));
                	processElement_chil.addContent(location);
                	Element text = new Element("text");
                	String textpost="s";
                	if(map.get("Text").toString()==null){
                		textpost="ssssssss";
                	}else{
                		textpost=map.get("Text").toString();	
                	}
                	text.addContent(new Element("post").setText(textpost));
                	text.addContent(new Element("department").setText(textpost));
                	text.addContent(new Element("work").setText(work));
                	text.addContent(new Element("file").setText(file));
                	if(map.get("NameU").equals("Subprocess")){
                		text.addContent(new Element("son_process_name").setText(textpost));
                	}else{
                		text.addContent(new Element("son_process_name").setText("jjjjjjj"));
                	}
                	
                	processElement_chil.addContent(text);
                	processElement_chil.addContent(new Element("explain").setText(explain.toString()));
                	root.addContent(processElement_chil);

            	}
            	//root.addContent(processElement.detach());
            }
        	//连接线
        	for(int i = 0; i < xList.size(); i++){        		
            	Map<String, Object> map = (Map<String, Object>) xList.get(i);
            	Set<String> set = map.keySet();
            	List<String> keyList = new ArrayList<String>();
            	keyList.addAll(set);
            	if(map.get("NameU").equals("Dynamic connector")){
            		Element processLine_chil=new Element("process_line");
                	processLine_chil.addContent(new Element("key").setText(map.get("ID").toString()));            
                	processLine_chil.addContent(new Element("background").setText("#33cc99"));
                	processLine_chil.addContent(new Element("connect_before_element").setText("aaaaaa"));
                	processLine_chil.addContent(new Element("connect_before_element").setText(map.get("BegTrigger").toString()));
                	processLine_chil.addContent(new Element("connect_after_element").setText("aaaaaa"));
                	processLine_chil.addContent(new Element("connect_after_element").setText(map.get("EndTrigger").toString()));
                	double BeginX=Double.parseDouble(map.get("BeginX").toString());
                	double BeginY=Double.parseDouble(map.get("BeginY").toString());
                	double EndX=Double.parseDouble(map.get("EndX").toString());
                	double EndY=Double.parseDouble(map.get("EndY").toString());
                	int point_number=Integer.parseInt(map.get("line_point_number").toString());
                	//StringBuffer loc_str = null;
                	String loc_str_x;
                	String loc_str_y;
                	for(int j=0;j<point_number;j++){
                		String temp_number=String.valueOf(j+1);
                		loc_str_x=map.get(temp_number+"X").toString();
                		loc_str_y=map.get(temp_number+"Y").toString();
                		Element loc_x=new Element("loc").setText(loc_str_x.substring(0,loc_str_x.indexOf(".")));
                		Element loc_y=new Element("loc").setText(loc_str_y.substring(0,loc_str_y.indexOf(".")));
                		Element location_1=new Element("location");
                    	location_1.addContent(new Element("position_x").setText(loc_str_x.substring(0,loc_str_x.indexOf("."))));
                    	location_1.addContent(new Element("position_y").setText(loc_str_y.substring(0,loc_str_y.indexOf("."))));
                		processLine_chil.addContent(loc_x);
                		processLine_chil.addContent(loc_y);
                		processLine_chil.addContent(location_1);
                	}
                	String arrow_direction;
                	if(map.get(String.valueOf(point_number-1)+"X").toString().equals(map.get(String.valueOf(point_number)+"X").toString())){
                		if(Double.valueOf(map.get(String.valueOf(point_number-1)+"Y").toString()).compareTo(Double.valueOf(map.get(String.valueOf(point_number)+"Y").toString()))<0){
                			arrow_direction="up";
                		}else{
                			arrow_direction="down";
                		}
                	}else {
                		if(Double.valueOf(map.get(String.valueOf(point_number-1)+"X").toString()).compareTo(Double.valueOf(map.get(String.valueOf(point_number)+"X").toString()))<0){
                			arrow_direction="right";
                		}else{
                			arrow_direction="left";
                		}
					}
                	
                	/*Element loc=new Element("loc").setText(String.valueOf((int)BeginX)+","+String.valueOf((int)BeginY)+","+String.valueOf((int)EndX)+","+String.valueOf((int)EndY));
                	Element location_1=new Element("location");
                	location_1.addContent(new Element("position_x").setText(String.valueOf((int)BeginX)));
                	location_1.addContent(new Element("position_y").setText(String.valueOf((int)BeginY)));
                	Element location_2=new Element("location");
                	location_2.addContent(new Element("position_x").setText(String.valueOf((int)EndX)));
                	location_2.addContent(new Element("position_y").setText(String.valueOf((int)EndY)));*/
                	Element text=new Element("text");
                	for(int j = 0; j < keyList.size(); j++){
                		if(keyList.get(j).equals("Text")){
                			text.addContent(new Element("info").setText(map.get("Text").toString()));
                		}
                		
                	}
                	double TxtPinX=Double.parseDouble(map.get("PinX").toString())+Double.parseDouble(map.get("TxtPinX").toString());
                	double TxtPinY=Double.parseDouble(map.get("PinY").toString())+Double.parseDouble(map.get("TxtPinY").toString());
                	text.addContent(new Element("position_x").setText(String.valueOf((int)TxtPinX)));
                	text.addContent(new Element("position_y").setText(String.valueOf((int)TxtPinY)));
                	Element arrow_direction_t=new Element("arrow_direction").setText(arrow_direction);
                	//processLine_chil.addContent(loc);
                	//processLine_chil.addContent(location_1);
                	//processLine_chil.addContent(location_2);
                	processLine_chil.addContent(text);
                	processLine_chil.addContent(arrow_direction_t);
                	root.addContent(processLine_chil) ;              	
            	}
            	
            	//root.addContent(processLine.detach());
            	
        	}
        //}
                 
            XMLOutputter XMLOut = new XMLOutputter(FormatXML());  
            try {  
                XMLOut.output(Doc, new FileOutputStream(out));  
            } catch (FileNotFoundException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
       
          
    } 
    
    
    //得到范围内的模块ID
    public  ArrayList  ProcessElement(List xList,float LeftX,float RightX,float UpY,float DownY){
    	 ArrayList  ProcessElement_id_list = new ArrayList();
    	for(int i = 0; i < xList.size(); i++){ 
        	Map<String, Object> map = (Map<String, Object>) xList.get(i);
        	Set<String> set = map.keySet();
        	List<String> keyList = new ArrayList<String>();
        	keyList.addAll(set);
        	
        		float PinX=Float.parseFloat(map.get("PinX").toString());
        		float PinY=Float.parseFloat(map.get("PinY").toString());
            	if(LeftX-PinX<0&&RightX-PinX>0&&UpY-PinY>0&&DownY-PinY<0){
            		String ProcessElement_id=map.get("ID").toString();
            		//System.out.println(ProcessElement_id);
            		ProcessElement_id_list.add(ProcessElement_id);
            	}
			        	
    	}
    	//System.out.println("LISTSIZE"+ProcessElement_id_list.size());
    	return ProcessElement_id_list;
    }
    
    public Format FormatXML(){  
        //格式化生成的xml文件，如果不进行格式化的话，生成的xml文件将会是很长的一行...  
        Format format = Format.getCompactFormat();  
        format.setEncoding("utf-8");  
        format.setIndent(" ");  
        return format;  
    }  
      
    public static void main(String[] args){  
        try{  
        	OutputXML mXml = new OutputXML();  
            System.out.println("正在生成xml文件.....");  
            mXml.BuildXML();  
            System.out.println("成功生成....."); 
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
}
