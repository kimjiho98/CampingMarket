package com.spring.web.market.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


import org.springframework.ui.Model;


import com.spring.web.market.vo.Market;

import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

@Controller 
@RequestMapping("/market")
@Slf4j
public class MarketController {
   @Autowired
	public MarketService ms;
   
   @Autowired
   public HttpSession session;

   @GetMapping("/home") //홈으로 이동 
   public String main(HttpSession session, Model m)
   {
	   this.session = session;
	   List<Map<String,Object>> list = ms.getform();
	   m.addAttribute("list",list);
	  
	   int i=0;
	   if( i == 0 )
	   {
		i++;
		String category = "bed";
		List<Map<String,Object>> sendlist=ms.main_deepinfo(category);
		m.addAttribute("sendlist",sendlist);
	   }
	   return "thymeleaf/market/Markethome";
	   
   }
 
   @GetMapping("/sellform") //판매글로이동 
   public String sale()
   {	  
	   return "thymeleaf/market/sellform";
   }
   
   @GetMapping("/main_deepinfo") // 처음 메인 카테고리 눌럿을때 상품 정보들
   public String main_deepinfo(@RequestParam("category")String category ,Model m)
   {
	   List<Map<String,Object>> sendlist=ms.main_deepinfo(category);
	   m.addAttribute("sendlist",sendlist);
	   return "thymeleaf/market/Markethome";
   }
   
   @PostMapping("/save") //파일저장기능 
   @ResponseBody
   public Map<String,Boolean> save(Market market , @RequestParam("fname")MultipartFile[] file)
   {  
	   System.out.println(market);
	   ms.addMarketform(market);
	   Map<String,Boolean> map=new HashMap<>();
	   if(file[0].getSize()!=0)
	   { 
		  int row= ms.addMattach(market, file);
	      if(row>0)
	      {   boolean checked= true;
	      		map.put("completed", checked);
	    	  return map;
	      }
	      else 
	      {
	    	  boolean checked=false;
	    	  map.put("completed", checked);
	    	  return map;
	      }
	   }
	   boolean checked= true;
 		map.put("completed", checked);
	   return map;
   }
 @GetMapping("purchase") //초기 구매페이지 
 public String purchasepage(Model m)
 {	
	 List<Map<String, Object>> mlist=ms.purchase_list();
	 m.addAttribute("list",mlist);
	 return "thymeleaf/market/Purchase";
 }
 
 @PostMapping("deepinfo") //상세검색로직 
 public String deepinfo(Market market, Model m)
 {		
	 List<Map<String,Object>> mlist=ms.deepinfo(market);
	 m.addAttribute("list",mlist);
	 
	return "thymeleaf/market/Purchase";
 }
 	@GetMapping("detail") //상품정보
	public String detail(Market market,Model m)
	{
		List<Map<String,Object>> mlist=ms.detail(market);
		List<Map<String,Object>> nlist=ms.detail_all(market);
		m.addAttribute("lists",mlist);
		m.addAttribute("nlist",nlist);
		return "thymeleaf/market/Detail";
    }
 	@PostMapping("orderby_recent") //2차 분류 카테고리 
 	public String orderbylist_recent(Market market,Model m ,@RequestParam("num") int num)
 	{		
 		
		if(num==0) // 최근순 
 		{  
			List<Map<String,Object>> orderlist = ms.orderbylist_recent(market);
			m.addAttribute("list",orderlist);	
 		}
 		
		if(num==1) // 오래된순
		{
			List<Map<String,Object>> orderlist = ms.orderbylist_oldest(market);
			m.addAttribute("list",orderlist);
		}
 		
 		return "thymeleaf/market/Purchase";
	}
 	@PostMapping("orderby_price") // 2차 분류 카테고리
 	public String orderby_price(Market market, Model m , @RequestParam("value") String value)
 	{  
 		
 		if(value.equals("low"))
 		{ 
 			
 			List<Map<String,Object>> orderlist = ms.orderbylist_lowprice(market);
			m.addAttribute("list",orderlist);	
			
 		}
 		
 		if(value.equals("high"))
 		{
 			List<Map<String,Object>> orderlist = ms.orderbylist_highprice(market);
			m.addAttribute("list",orderlist);
			
 		}
 		return "thymeleaf/market/Purchase";
 	}
 	@PostMapping("delete")
 	@ResponseBody
 	public String delete(Market market) // 삭제기능 
 	{
 		ms.delete(market);
 		return "thymeleaf/market/Detail";
 	}
}

