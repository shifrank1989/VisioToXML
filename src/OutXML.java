
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.IconifyAction;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.omg.CORBA.PUBLIC_MEMBER;

public class OutXML {

	String IN_url="D://page2.xml";
	String OUT_url="D:\\page2OUT_sc.xml";
	String temp_ele="shape";
	int mod_width=210;
	int mod_heigh=56;
			
	Map map_con=new HashMap();
	public String get_category(String nameU){
		String category = "CommonCode";
		nameU=nameU.substring(0,nameU.indexOf("."));
		if(nameU.equals("Start/End")){
			category="Start";
		}else if (nameU.equals("Decision")) {
			category="JudgmentCode";
		}else if (nameU.equals("Subprocess")) {
			category="SonProcessCode";
		}
		return category;
	}
	 public String get_father_id(String id){
			String father_id = null;
			Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("Type").equals("Group")){ 
	        		int child_number=Integer.valueOf(map.get("child_number").toString());
	        		//System.out.println("子节点数:"+child_number);
	        		
	        		for(int j=0;j<child_number;j++){
	        			//System.out.println("子节点id:"+map.get(String.valueOf(j+1)+"childID").toString());
	        			if(map.get(String.valueOf(j+1)+"childID").toString().equals(id)){
	        				father_id=map.get("ID").toString();
	        			}
	        			
	        		}
	        	}
	        	
			}
			return father_id;
		}
	
    public void BuildXML(){         
        Read readXML=new Read();
        List xList=readXML.xmlData(IN_url);
        //创建根节点...  
        Element root = new Element("modular_id").setText("NNNNN");        
        //将根节点添加到文档中...  
        Document Doc  = new Document(root); 
        Element process_file_id = new Element("process_file_id").setText("process_file_id");
        root.addContent(process_file_id); 
        //System.out.println(xList.size());
        for(int i=0;i<xList.size();i++){
        	String category="CommonCode";
        	String post="ttt";
        	String department="ttt";
        	String work="ttt";
    		String file="ttt";
    		String explain="ttt";
    		String position_x;
    		String position_y;
    		String son_process_name="ttt";
    		Map<String, Object> map = (Map<String, Object>) xList.get(i);
        	Set<String> set = map.keySet();
        	List<String> keyList = new ArrayList<String>();
        	keyList.addAll(set);
        	if(map.get("Type").equals("Group")){        		
        		Element processElement_chil=new Element("process_element");
        		//position_x=String.valueOf((int)(Double.valueOf(map.get("PinX").toString())-Double.valueOf(map.get("Width").toString())/2));
        		//position_y=String.valueOf((int)(Double.valueOf(map.get("PinY").toString())-Double.valueOf(map.get("Height").toString())/2));
        		//double position_x_temp=Double.valueOf(map.get("PinX").toString());
        		//double position_y_temp=Double.valueOf(map.get("PinY").toString());
        		//position_x=String.valueOf((int)(position_x_temp)-mod_width/2);
        		//position_y=String.valueOf((int)(position_y_temp)-mod_width/2);
        		position_x=map.get("X_json").toString();
        		position_y=map.get("Y_json").toString();
        		int child_number=Integer.valueOf(map.get("child_number").toString());
        		
        		if(child_number==1){
        			//System.out.println("======"+map.get("1"+"childNameU").toString());
        			if(get_category(map.get("1"+"childNameU").toString()).equals("Start")){
        				work=map.get("1"+"childText").toString();
        				category="Start";
        			}else if (get_category(map.get("1"+"childNameU").toString()).equals("JudgmentCode")) {
        				work=map.get("1"+"childText").toString();
        				category="JudgmentCode";
					}else if (get_category(map.get("1"+"childNameU").toString()).equals("SonProcessCode")) {
						son_process_name=map.get("1"+"childText").toString();
						category="SonProcessCode";
					}
        		}else if(child_number==2){
        			if(Double.valueOf(map.get("1"+"childPinY").toString()).compareTo(Double.valueOf(map.get("2"+"childPinY").toString()))<0){
        				post=map.get("1"+"childText").toString();
        				work=map.get("2"+"childText").toString();
        			}else {
        				post=map.get("2"+"childText").toString();
        				work=map.get("1"+"childText").toString();
					}
        		}else if (child_number==3) {
					if(Double.valueOf(map.get("1"+"childPinY").toString()).compareTo(Double.valueOf(map.get("2"+"childPinY").toString()))<0){
						if(Double.valueOf(map.get("3"+"childPinY").toString()).compareTo(Double.valueOf(map.get("1"+"childPinY").toString()))<0){
							post=map.get("3"+"childText").toString();
	        				work=map.get("1"+"childText").toString();
	        				file=map.get("2"+"childText").toString();
						}else if(Double.valueOf(map.get("2"+"childPinY").toString()).compareTo(Double.valueOf(map.get("3"+"childPinY").toString()))<0){
							post=map.get("1"+"childText").toString();
	        				work=map.get("2"+"childText").toString();
	        				file=map.get("3"+"childText").toString();
						}else {
							post=map.get("1"+"childText").toString();
	        				work=map.get("3"+"childText").toString();
	        				file=map.get("2"+"childText").toString();
						}
					}else if (Double.valueOf(map.get("1"+"childPinY").toString()).compareTo(Double.valueOf(map.get("2"+"childPinY").toString()))>0) {
						if(Double.valueOf(map.get("3"+"childPinY").toString()).compareTo(Double.valueOf(map.get("1"+"childPinY").toString()))>0){
							post=map.get("2"+"childText").toString();
	        				work=map.get("1"+"childText").toString();
	        				file=map.get("3"+"childText").toString();
						}else if(Double.valueOf(map.get("2"+"childPinY").toString()).compareTo(Double.valueOf(map.get("3"+"childPinY").toString()))>0){
							post=map.get("3"+"childText").toString();
	        				work=map.get("2"+"childText").toString();
	        				file=map.get("1"+"childText").toString();
						}else {
							post=map.get("2"+"childText").toString();
	        				work=map.get("3"+"childText").toString();
	        				file=map.get("1"+"childText").toString();
						}
					}
				}
        		for(int j=0;j<xList.size();j++){
        			Map<String, Object> map_temp = (Map<String, Object>) xList.get(j);
                	Set<String> set_temp = map_temp.keySet();
                	List<String> keyList_temp = new ArrayList<String>();
                	keyList_temp.addAll(set_temp);
                	if(map_temp.get("Type").equals("Shape")&&map_temp.get("NameU").equals("Full bracket")){
                		if(map.get("ID").equals(map_temp.get("Relationships"))){
                			explain=map_temp.get("Text").toString();
                		}
                	}
        		}
        		//
        		if(map.get("ID").equals("75")){
        			
        		}
        		//
        		processElement_chil.addContent(new Element("key").setText(map.get("ID").toString()));
        		processElement_chil.addContent(new Element("category").setText(category));	
        		processElement_chil.addContent(new Element("background").setText("#ffffcc"));
        		processElement_chil.addContent(new Element("loc").setText(position_x+" "+position_y));
            	Element location = new Element("location");
            	location.addContent(new Element("position_x").setText(position_x));
            	location.addContent(new Element("position_y").setText(position_y));
            	processElement_chil.addContent(location);
            	Element text = new Element("text");
            	//String textpost="s";           
            	text.addContent(new Element("post").setText(post));
            	text.addContent(new Element("department").setText(post));
            	text.addContent(new Element("work").setText(work));
            	text.addContent(new Element("file").setText(file));
            	text.addContent(new Element("son_process_name").setText(son_process_name));
            	processElement_chil.addContent(text);
            	processElement_chil.addContent(new Element("explain").setText(explain.toString()));
            	root.addContent(processElement_chil);
        	}else if(map.get("Type").equals("Shape")&&map.get("NameU").equals("Dynamic connector")){
        		Element processLine_chil=new Element("process_line");
        		int point_number=Integer.parseInt(map.get("line_point_number").toString());
        		//开始的方向
            	String begin_direction;
            	String arrow_direction;
            	String BegT;
            	String EndT;
            	String BegTrigger;
            	String EndTrigger;
            	String Beg_x_new;
            	String End_x_new;
            	String Beg_y_new;
            	String End_y_new;
            	double x_ratio=1;
            	double y_ratio=1;
            	if(map.get(String.valueOf(1)+"X").toString().equals(map.get(String.valueOf(2)+"X").toString())){
            		if(Double.valueOf(map.get(String.valueOf(1)+"Y").toString()).compareTo(Double.valueOf(map.get(String.valueOf(2)+"Y").toString()))<0){
            			begin_direction="down";
            		}else{
            			begin_direction="up";
            		}
            	}else {
            		if(Double.valueOf(map.get(String.valueOf(1)+"X").toString()).compareTo(Double.valueOf(map.get(String.valueOf(2)+"X").toString()))<0){
            			begin_direction="right";
            		}else{
            			begin_direction="left";
            		}
				}
            	//结束的箭头方向            	
            	if(map.get(String.valueOf(point_number-1)+"X").toString().equals(map.get(String.valueOf(point_number)+"X").toString())){
            		if(Double.valueOf(map.get(String.valueOf(point_number-1)+"Y").toString()).compareTo(Double.valueOf(map.get(String.valueOf(point_number)+"Y").toString()))<0){
            			arrow_direction="down";
            		}else{
            			arrow_direction="up";
            		}
            	}else {
            		if(Double.valueOf(map.get(String.valueOf(point_number-1)+"X").toString()).compareTo(Double.valueOf(map.get(String.valueOf(point_number)+"X").toString()))<0){
            			arrow_direction="right";
            		}else{
            			arrow_direction="left";
            		}
				}
            	BegT=map.get("BegTrigger").toString();
            	EndT=map.get("EndTrigger").toString();
        		BegTrigger=get_father_id(BegT);
        		EndTrigger=get_father_id(EndT);
        		
        		processLine_chil.addContent(new Element("key").setText(map.get("ID").toString()));            
            	processLine_chil.addContent(new Element("background").setText("#33cc99"));
            	processLine_chil.addContent(new Element("connect_before_element").setText(BegTrigger));
            	processLine_chil.addContent(new Element("connect_after_element").setText(EndTrigger));
        		
            	Beg_x_new=get_BeginX_new(begin_direction, BegTrigger);
        		End_x_new=get_EndX_new(arrow_direction, EndTrigger);
        		Beg_y_new=get_BeginY_new(begin_direction, BegTrigger);
        		End_y_new=get_EndY_new(arrow_direction, EndTrigger);
        		x_ratio=x_ratio(map.get("BeginX").toString(), map.get("EndX").toString(), Beg_x_new, End_x_new);
        		y_ratio=y_ratio(map.get("BeginY").toString(), map.get("EndY").toString(), Beg_y_new, End_y_new);
        		String loc_str_x;
            	String loc_str_y;
            	for(int j=0;j<point_number;j++){
            		String temp_number=String.valueOf(j+1);
            		double loc_x_dou=Double.valueOf((map.get(temp_number+"X").toString()))*x_ratio;
            		double loc_y_dou=Double.valueOf((map.get(temp_number+"Y").toString()))*y_ratio;
            		int loc_x_int=(int)(loc_x_dou+Double.valueOf(Beg_x_new));
            		int loc_y_int=(int) (loc_y_dou+Double.valueOf(Beg_y_new));
            		//loc_str_x=map.get(temp_number+"X").toString();
            		//loc_str_y=map.get(temp_number+"Y").toString();
            		//Element loc_x=new Element("loc").setText(loc_str_x.substring(0,loc_str_x.indexOf(".")));
            		//Element loc_y=new Element("loc").setText(loc_str_y.substring(0,loc_str_y.indexOf(".")));
            		Element loc_x=new Element("loc").setText(String.valueOf(loc_x_int));
            		Element loc_y=new Element("loc").setText(String.valueOf(loc_y_int));
            		Element location_1=new Element("location");
                	location_1.addContent(new Element("position_x").setText(String.valueOf(loc_x_int)));
                	location_1.addContent(new Element("position_y").setText(String.valueOf(loc_y_int)));
            		processLine_chil.addContent(loc_x);
            		processLine_chil.addContent(loc_y);
            		processLine_chil.addContent(location_1);
            	}
            	Element text=new Element("text");
            	for(int j = 0; j < keyList.size(); j++){
            		if(keyList.get(j).equals("Text")){
            			text.addContent(new Element("info").setText(map.get("Text").toString()));
            		}
            		
            	}
            	double TxtPinX=Double.parseDouble(map.get("TxtPinX").toString());
            	double TxtPinY=Double.parseDouble(map.get("TxtPinY").toString());
            	text.addContent(new Element("position_x").setText(String.valueOf((int)TxtPinX)));
            	text.addContent(new Element("position_y").setText(String.valueOf((int)TxtPinY)));
            	/*processLine_chil.addContent(new Element("key").setText(map.get("ID").toString()));            
            	processLine_chil.addContent(new Element("background").setText("#33cc99"));
            	processLine_chil.addContent(new Element("connect_before_element").setText(BegTrigger));
            	processLine_chil.addContent(new Element("connect_after_element").setText(EndTrigger));*/
            	processLine_chil.addContent(text);
            	processLine_chil.addContent(new Element("arrow_direction").setText(arrow_direction));
            	root.addContent(processLine_chil) ; 
            	
        	}
        	
        }
                         
            XMLOutputter XMLOut = new XMLOutputter(FormatXML());  
            try {  
                XMLOut.output(Doc, new FileOutputStream(OUT_url));  
            } catch (FileNotFoundException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            } catch (IOException e) {  
                // TODO Auto-generated catch block  
                e.printStackTrace();  
            }  
       
          
    } 
   
    //
    public String get_BeginX_new(String direction,String fatherid){
    	String Begin_x_new = null;
    	if(direction.equals("up")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		
	        		Begin_x_new=String.valueOf(up_cen_x(map));
	        	}
			}
    	}else if(direction.equals("down")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		Begin_x_new=String.valueOf(down_cen_x(map));
	        		
	        	}
			}
    	}
    	else if(direction.equals("right")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		Begin_x_new=String.valueOf(right_cen_x(map));
	        		
	        	}
			}
    	}
    	else if(direction.equals("left")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		Begin_x_new=String.valueOf(left_cen_x(map));
	        		left_cen_x(map);
	        	}
			}
    	}
    	return Begin_x_new;
    }
    public String get_EndX_new(String direction,String fatherid){
    	String End_x_new = null;
    	if(direction.equals("up")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		End_x_new=String.valueOf(down_cen_x(map));
	        	}
			}
    	}else if(direction.equals("down")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		End_x_new=String.valueOf(up_cen_x(map));
	        		
	        	}
			}
    	}
    	else if(direction.equals("right")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		End_x_new=String.valueOf(left_cen_x(map));
	        		
	        	}
			}
    	}
    	else if(direction.equals("left")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		End_x_new=String.valueOf(right_cen_x(map));
	        		left_cen_x(map);
	        	}
			}
    	}
    	return End_x_new;
    }
    
    public String get_EndY_new(String direction,String fatherid){
    	String End_y_new = null;
    	if(direction.equals("up")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		int diff_y=(mod_heigh/2)*(Integer.valueOf(map.get("child_number").toString())-1);	
	        		End_y_new=String.valueOf(down_cen_y(map)+diff_y);
	        	}
			}
    	}else if(direction.equals("down")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		int diff_y=(mod_heigh/2)*(Integer.valueOf(map.get("child_number").toString())-1);	
	        		End_y_new=String.valueOf(up_cen_y(map)-diff_y);
	        		
	        	}
			}
    	}
    	else if(direction.equals("right")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		End_y_new=String.valueOf(left_cen_y(map));
	        		
	        	}
			}
    	}
    	else if(direction.equals("left")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		End_y_new=String.valueOf(right_cen_y(map));
	        		//left_cen_y(map);
	        	}
			}
    	}
    	return End_y_new;
    }
    
    
    public String get_BeginY_new(String direction,String fatherid){
    	String Begin_y_new = null;
    	if(direction.equals("up")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		int diff_y=(mod_heigh/2)*(Integer.valueOf(map.get("child_number").toString())-1);	        		
	        		Begin_y_new=String.valueOf(up_cen_y(map)-diff_y);
	        	}
			}
    	}else if(direction.equals("down")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		int diff_y=(mod_heigh/2)*(Integer.valueOf(map.get("child_number").toString())-1);	
	        		Begin_y_new=String.valueOf(down_cen_y(map)+diff_y);
	        		
	        	}
			}
    	}
    	else if(direction.equals("right")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		Begin_y_new=String.valueOf(right_cen_y(map));
	        		
	        	}
			}
    	}
    	else if(direction.equals("left")){
    		Read readXML=new Read();
			List xList=readXML.xmlData(IN_url);
			for(int i=0;i<xList.size();i++){
				Map<String, Object> map = (Map<String, Object>) xList.get(i);
	        	Set<String> set = map.keySet();
	        	List<String> keyList = new ArrayList<String>();
	        	keyList.addAll(set);
	        	if(map.get("ID").equals(fatherid)){
	        		Begin_y_new=String.valueOf(left_cen_y(map));
	        		left_cen_x(map);
	        	}
			}
    	}
    	return Begin_y_new;
    }
    public double x_ratio(String Beg_x_old,String End_x_old,String Beg_x_new,String End_x_new){
    	double x_ratio=1;
    	x_ratio=(Double.valueOf(Beg_x_new)-Double.valueOf(End_x_new))/(Double.valueOf(Beg_x_old)-Double.valueOf(End_x_old));
    	return x_ratio;
    }
    public double y_ratio(String Beg_y_old,String End_y_old,String Beg_y_new,String End_y_new){
    	double y_ratio=1;
    	y_ratio=(Double.valueOf(Beg_y_new)-Double.valueOf(End_y_new))/(Double.valueOf(Beg_y_old)-Double.valueOf(End_y_old));
    	return y_ratio;
    }
    public double up_cen_x(Map map){
    	double up_cen_x=0.0;
    	//double pinx=Double.valueOf(map.get("PinX").toString());    	
    	//up_cen_x=pinx;  
    	int temp_x_ul=Integer.valueOf(map.get("X_json").toString());
    	up_cen_x=temp_x_ul+mod_width/2;
    	return up_cen_x;
    }
    public double up_cen_y(Map map){
    	double up_cen_y=0.0;   	
    	//double piny=Double.valueOf(map.get("PinY").toString());   	
    	//double heigh=Double.valueOf(map.get("Height").toString());    	   	
    	//up_cen_y=piny-mod_heigh/2;
    	int temp_y_ul=Integer.valueOf(map.get("Y_json").toString());
    	up_cen_y=temp_y_ul;
    	return up_cen_y;
    }
    public double down_cen_x(Map map){
    	double down_cen_x=0.0;
    	//double pinx=Double.valueOf(map.get("PinX").toString());   	
    	//down_cen_x=pinx;    
    	int temp_x_ul=Integer.valueOf(map.get("X_json").toString());
    	down_cen_x=temp_x_ul+mod_width/2;
    	return down_cen_x;
    }
    public double down_cen_y(Map map){
    	double down_cen_y=0.0;    	
    	//double piny=Double.valueOf(map.get("PinY").toString());   	
    	//double heigh=Double.valueOf(map.get("Height").toString());    	 	
    	//down_cen_y=piny+mod_heigh/2;
    	int temp_y_ul=Integer.valueOf(map.get("Y_json").toString());
    	down_cen_y=temp_y_ul+mod_heigh;
    	return down_cen_y;
    }
    public double left_cen_x(Map map){
    	double left_cen_x=0.0;
    	//double pinx=Double.valueOf(map.get("PinX").toString());    	
    	//double width=Double.valueOf(map.get("Width").toString());   	  	
    	//left_cen_x=pinx-mod_width/2;
    	int temp_x_ul=Integer.valueOf(map.get("X_json").toString());
    	left_cen_x=temp_x_ul;
    	return left_cen_x;
    }
    public double left_cen_y(Map map){
    	double left_cen_y=0.0;    	
    	//double piny=Double.valueOf(map.get("PinY").toString());    	
    	//left_cen_y=piny; 
    	int temp_y_ul=Integer.valueOf(map.get("Y_json").toString());
    	left_cen_y=temp_y_ul+mod_heigh/2;
    	return left_cen_y;
    }
    public double right_cen_x(Map map){
    	double right_cen_x=0.0;
    	//double pinx=Double.valueOf(map.get("PinX").toString());    	
    	//double width=Double.valueOf(map.get("Width").toString());       	
    	//right_cen_x=pinx+mod_width/2;
    	int temp_x_ul=Integer.valueOf(map.get("X_json").toString());
    	right_cen_x=temp_x_ul+mod_width;
    	return right_cen_x;
    }
    public double right_cen_y(Map map){
    	double right_cen_y=0.0;    	
    	//double piny=Double.valueOf(map.get("PinY").toString());    	
    	//right_cen_y=piny;  
    	int temp_y_ul=Integer.valueOf(map.get("Y_json").toString());
    	right_cen_y=temp_y_ul+mod_heigh/2;
    	return right_cen_y;
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
        	OutXML mXml = new OutXML();  
            System.out.println("正在生成xml文件.....");  
            mXml.BuildXML();  
            System.out.println("成功生成....."); 
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    }  
}

