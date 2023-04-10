package com.spring.web.market.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import com.spring.web.market.vo.M_Attach;
import com.spring.web.market.vo.Market;



@Mapper
public interface MarketMapper {

	public int addmarketform(Market market);
	public int addmattach(List<M_Attach> list);
	public List<Map<String,Object>> getform ();
	public List<Map<String,Object>> purchase_list();
	public List<Map<String,Object>> deepinfo(Market market);
	public List<Map<String,Object>> detail_photo(Market market);
	public List<Map<String,Object>> detail_all(Market market);
	public List<Map<String,Object>> main_deepinfo(@RequestParam("category")String category);
	public List<Map<String,Object>> orderbylist_recent(Market market);
	public List<Map<String,Object>> orderbylist_oldest(Market market);
	public List<Map<String,Object>> orderbylist_lowprice(Market market);
	public List<Map<String,Object>> orderbylist_highprice(Market market);
	public boolean delete(Market market);
	public boolean delete_mattach(Market market);
}
