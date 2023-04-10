package com.spring.web.market.service;

import java.io.File;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.spring.web.market.vo.M_Attach;
import com.spring.web.market.vo.Market;


import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MarketService {
     @Autowired
     private MarketMapper dao;  
     @Autowired
     ResourceLoader resourceloader;

     private Path fileStroageLocation;
     
	 public void addMarketform(Market market)
	 {
	 	        		
		    dao.addmarketform(market);
	 }
	 
	 public int addMattach(Market market ,MultipartFile[] file)
	 {   
		 
		 
		 try {
			 String title= market.getTitle();
			 List<M_Attach> list =new ArrayList<>();
			 this.fileStroageLocation = Paths.get("./src/main/resources/static/market/personal/"+market.getTitle()).toAbsolutePath().normalize();
	         Files.createDirectories(this.fileStroageLocation);
	         
			  if(file[0].getSize()!=0)
			  {  
				  int unum =market.getUnum();
				  for(int i=(file.length-1) ; i>=0 ; i--)
				  {   
					  String filename= null;
					  if(i==(file.length-1)) filename= "thumnail"+file[i].getOriginalFilename();
					  else filename= file[i].getOriginalFilename();
					  M_Attach mattach = new M_Attach(); 
					  mattach.setFname(filename);
					  mattach.setUnum(unum);
					  list.add(mattach);
		              Path targetLocation = this.fileStroageLocation.resolve(filename);
		              Files.copy(file[i].getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

				  }
				  
				  int rows =dao.addmattach(list);
				  return rows;
			  }
			  
			  
	 		}
		 catch(Exception e) 
		 {
				// TODO Auto-generated catch block
				e.printStackTrace();
		 }
		 	return 0;					 		 
	 }
	 
	public List<Map<String,Object>> getform()
	{
		List<Map<String,Object>> listdao=dao.getform();
		List<Map<String,Object>> mlist = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<listdao.size(); i++)
		{
			map= listdao.get(i);
			Market m =new Market();
			M_Attach a=new M_Attach();
			Map<String,Object> maps=new HashMap<>();
			
			m.setMnum(((BigDecimal)map.get("MNUM")).intValue());
			m.setTitle((String)map.get("TITLE"));  
			m.setPrice(((String)map.get("PRICE")));
			a.setFname(((String) map.get("FNAME"))); 							
			maps.put("m", m);
			maps.put("a", a);
			mlist.add(maps);
		}
		
		return mlist;
	}
	
	public List<Map<String,Object>> purchase_list()
	{
		List<Map<String,Object>> listdao=dao.purchase_list();
		List<Map<String,Object>> mlist = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<listdao.size(); i++)
		{
			map= listdao.get(i);
			Market m =new Market();
			M_Attach a=new M_Attach();
			Map<String,Object> maps=new HashMap<>();
			
			m.setTitle((String)map.get("TITLE"));
			m.setMnum(((BigDecimal)map.get("MNUM")).intValue());
			m.setPrice(((String)map.get("PRICE")));
			m.setRegion(((String)map.get("REGION")));
			a.setFname(((String) map.get("FNAME"))); 							
			maps.put("m", m);
			maps.put("a", a);
			mlist.add(maps);
		}
		return mlist;
	}
	public List<Map<String,Object>> deepinfo(Market market)
	{
		List<Map<String,Object>> listdao = dao.deepinfo(market);;
		List<Map<String,Object>> mlist = new ArrayList<>();
		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<listdao.size(); i++)
		{
			map= listdao.get(i);
			Market m =new Market();
			M_Attach a=new M_Attach();
			Map<String,Object> maps=new HashMap<>();
			
			m.setTitle((String)map.get("TITLE"));
			m.setMnum(((BigDecimal)map.get("MNUM")).intValue());
			m.setPrice(((String)map.get("PRICE")));
			m.setRegion(((String)map.get("REGION")));
			a.setFname(((String) map.get("FNAME"))); 							
			maps.put("m", m);
			maps.put("a", a);
			mlist.add(maps);
		}
		return mlist;
	}
	public List<Map<String,Object>> detail(Market market)
	{
		List<Map<String,Object>> listdao = dao.detail_photo(market);
		List<Map<String,Object>> mlist = new ArrayList<>();
		
		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<listdao.size(); i++)
		{
			map= listdao.get(i);
			
			Market m =new Market();
			M_Attach a=new M_Attach();
			
			Map<String,Object> maps=new HashMap<>();			
					
			m.setTitle((String)map.get("TITLE"));
			a.setFname((String)map.get("FNAME"));
			maps.put("m", m);
			maps.put("a", a);
			mlist.add(maps);
		}
				
		return mlist;
	}
	public List<Map<String,Object>> detail_all(Market market)
	{
		List<Map<String,Object>> listdao = dao.detail_all(market);
		List<Map<String,Object>> nlist = new ArrayList<>();
		
		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<listdao.size(); i++)
		{
			if(i==0)
			{
				map= listdao.get(i);
				
				Market m =new Market();
				M_Attach a=new M_Attach();
				
				Map<String,Object> maps=new HashMap<>();	
			
				m.setAuthor((String)map.get("AUTHOR"));
				m.setCategory((String)map.get("CATEGORY"));
				m.setContent((String)map.get("CONTENT"));
				m.setMnum(((BigDecimal)map.get("MNUM")).intValue());
				m.setPrice((String)map.get("PRICE"));		
				Timestamp out = (java.sql.Timestamp)map.get("REGDATE");
				m.setRegdate(new java.sql.Date(out.getTime()));
				m.setRegion((String)map.get("REGION"));	
				m.setUnum(((BigDecimal)map.get("UNUM")).intValue());	
				m.setTitle((String)map.get("TITLE"));							
				a.setFname(((String)map.get("FNAME")));
				maps.put("m", m);
				maps.put("a", a);
				nlist.add(maps); 
					
			}
		}
		return nlist;
	}
	public List<Map<String,Object>> main_deepinfo(@RequestParam("category")String category)
	{
		List<Map<String,Object>> mlist =dao.main_deepinfo(category);
		List<Map<String,Object>> sendlist = new ArrayList<>();

		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<mlist.size(); i++)
		{
			map= mlist.get(i);
			Market sendm =new Market();
			M_Attach senda=new M_Attach();
			
			Map<String,Object> sendmap=new HashMap<>();
			senda.setFname(((String)map.get("FNAME")));
			sendm.setTitle((String)map.get("TITLE"));
			sendm.setPrice((String)map.get("PRICE"));
			sendm.setRegion((String)map.get("REGION"));
			sendm.setMnum(((BigDecimal)map.get("MNUM")).intValue());
			sendmap.put("sendm", sendm);
			sendmap.put("senda", senda);
			sendlist.add(sendmap); 			
		}
		return sendlist;
	}
	
	public List<Map<String,Object>> orderbylist_recent(Market market) //최근순
	{			
		if(market.getCategory().equals("undefined"))
		{
			market.setCategory("");
		}
		List<Map<String,Object>> orderbylist =dao.orderbylist_recent(market);
		List<Map<String,Object>> orderlist = new ArrayList<>();  
 		
		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<orderbylist.size(); i++)
		{
			map= orderbylist.get(i);
			Market m =new Market();
			M_Attach a=new M_Attach();
			Map<String,Object> maps=new HashMap<>();
			
			m.setTitle((String)map.get("TITLE"));
			m.setMnum(((BigDecimal)map.get("MNUM")).intValue());
			m.setPrice(((String)map.get("PRICE")));
			m.setRegion(((String)map.get("REGION")));
			a.setFname(((String) map.get("FNAME"))); 							
			maps.put("m", m);
			maps.put("a", a);
			orderlist.add(maps); 			
		}
		return orderlist;
	}
	public List<Map<String,Object>> orderbylist_oldest(Market market) //오래된순
	{	
		if(market.getCategory().equals("undefined"))
		{
			market.setCategory("");
		}
		List<Map<String,Object>> orderbylist =dao.orderbylist_oldest(market);
		List<Map<String,Object>> orderlist = new ArrayList<>();  
 		
		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<orderbylist.size(); i++)
		{
			map= orderbylist.get(i);
			Market m =new Market();
			M_Attach a=new M_Attach();
			Map<String,Object> maps=new HashMap<>();
			
			m.setTitle((String)map.get("TITLE"));
			m.setMnum(((BigDecimal)map.get("MNUM")).intValue());
			m.setPrice(((String)map.get("PRICE")));
			m.setRegion(((String)map.get("REGION")));
			a.setFname(((String) map.get("FNAME"))); 							
			maps.put("m", m);
			maps.put("a", a);
			orderlist.add(maps); 			
		}
		return orderlist;
	}
	
	public List<Map<String,Object>> orderbylist_lowprice(Market market)
	{   

		if(market.getCategory().equals("undefined"))
		{
			market.setCategory("");
		}
		List<Map<String,Object>> orderbylist =dao.orderbylist_lowprice(market);
		List<Map<String,Object>> orderlist = new ArrayList<>(); 
		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<orderbylist.size(); i++)
		{
			map= orderbylist.get(i);
			Market m =new Market();
			M_Attach a=new M_Attach();
			Map<String,Object> maps=new HashMap<>();
			
			m.setTitle((String)map.get("TITLE"));
			m.setMnum(((BigDecimal)map.get("MNUM")).intValue());
			
			BigDecimal price = (BigDecimal) map.get("PRICE");
			String priceString = price.toString()+"원";
			m.setPrice(priceString);
			
			m.setRegion(((String)map.get("REGION")));
			a.setFname(((String) map.get("FNAME"))); 							
			maps.put("m", m);
			maps.put("a", a);
			orderlist.add(maps); 			
		}
		return orderlist;
	}
	
	public List<Map<String,Object>> orderbylist_highprice(Market market) 
	{
		if(market.getCategory().equals("undefined"))
		{
			market.setCategory("");
		}
		List<Map<String,Object>> orderbylist =dao.orderbylist_highprice(market);
		List<Map<String,Object>> orderlist = new ArrayList<>(); 
		Map<String,Object> map = new HashMap<>();
		for(int i=0; i<orderbylist.size(); i++)
		{
			map= orderbylist.get(i);
			Market m =new Market();
			M_Attach a=new M_Attach();
			Map<String,Object> maps=new HashMap<>();
			
			m.setTitle((String)map.get("TITLE"));
			m.setMnum(((BigDecimal)map.get("MNUM")).intValue());
			
			BigDecimal price = (BigDecimal) map.get("PRICE");
			String priceString = price.toString()+"원";
			m.setPrice(priceString);
			
			m.setRegion(((String)map.get("REGION")));
			a.setFname(((String) map.get("FNAME"))); 							
			maps.put("m", m);
			maps.put("a", a);
			orderlist.add(maps); 			
		}
		return orderlist;
	}
	public boolean delete(Market market)
	{
		try {
				Boolean deleted=dao.delete(market);
				Boolean deleted_mattach = dao.delete_mattach(market);
				
				this.fileStroageLocation = Paths.get("./src/main/resources/static/market/personal/"+market.getTitle()).toAbsolutePath().normalize();

				// 해당 경로 내의 모든 파일 및 폴더 삭제
				Files.walk(this.fileStroageLocation)
				     .sorted(Comparator.reverseOrder())
				     .map(Path::toFile)
				     .forEach(File::delete);
				return true;
			}
		catch (Exception e) {
	        e.printStackTrace();
	        return false;
		}		
	}
}
