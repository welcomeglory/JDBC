package DB;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ItemDAO{
	private static ItemDAO instance = new ItemDAO();
	
	public static ItemDAO getInstance() {
		return instance;
	}
	//아이템 가져오기
	public Vector<String> getItem() throws SQLException{
		Vector<Item> dbitemlist = getAllItem();
		Vector<String> itemlist = new Vector<String>();
		for(Item item : dbitemlist) {
			itemlist.add(item.getItem_name());
		}
		return itemlist;
	}
	//모든 상품 가져오기
	public Vector<Item> getAllItem() throws SQLException{
		Vector<Item> list = new Vector<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String price = null;
		String sql = "select * from item";
		
		try {
			conn = DBconnect.connect();
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				Item item = new Item();
				item.setId(rs.getInt("id"));
				item.setItem_name(rs.getString("item_name"));
				item.setItem_stock(rs.getInt("item_stock"));
				item.setItem_price(rs.getInt("item_price"));
				list.add(item);				
			}
		}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		DBconnect.close();
    	}
		return list;		
	}
	//상품 이름으로 가격 가져오기
	public String getprice(String item_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String price = null;
		String sql = "select item_price from item where item_name=?";
		
		try {
			conn = DBconnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item_name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				price = Integer.toString(rs.getInt("item_price"));
			}
		}catch(Exception e) {
    		e.printStackTrace();
    	}finally {
    		DBconnect.close();
    	}
		return price;		
	}
	//수량가져오기	
	public String getStock(String item_name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String stock = null;
		String sql = "select item_stock from item where item_name=?";
		
		try {
			conn = DBconnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item_name);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				stock = Integer.toString(rs.getInt("item_stock"));
			}
		}catch(SQLException throwables) {
			throwables.printStackTrace();
    	}finally {
    		DBconnect.close();
    	}
		return stock;		
	}
	//id로 찾아오기
	//수량 업데이트
	public void updateStock(String total, String stock, String name) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update item set item_stock = ? - ? where item_name = ?";
		
		try {
			conn = DBconnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, total);
			pstmt.setString(2, stock);
			pstmt.setString(3, name);
			pstmt.executeQuery();
		}catch(Exception e) {
			e.printStackTrace();
    	}finally {
    		DBconnect.close();
    	}	
	}
	//상품 추가
	public boolean insertStock(Item item) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "insert into item('item_name','item_stock','item_price') values(?,?,?)";
		boolean result = false;
		
		try {
			conn = DBconnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_stock());
			pstmt.setInt(3, item.getItem_price());
			int r = pstmt.executeUpdate();
			System.out.println("return result = " + r);
			if( r > 0 ) {
				result = true;
			}			
		}catch(Exception e) {
			e.printStackTrace();
    	}finally {
    		DBconnect.close();
    	}	
		return result;
	}
	//상품수정
	public boolean updateitem(Item item) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "update item set item_name = ?,item_stock = ?,item_price = ? where(id=?)";
		boolean result = false;
		
		try {
			conn = DBconnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, item.getItem_name());
			pstmt.setInt(2, item.getItem_stock());
			pstmt.setInt(3, item.getItem_price());
			pstmt.setInt(4, item.getId());

			int r = pstmt.executeUpdate();
			if( r > 0 ) {
				result = true;
			}			
		}catch(Exception e) {
			e.printStackTrace();
    	}finally {
    		DBconnect.close();
    	}	
		return result;
	}
	//상품삭제
	public boolean DeleteItem(int id) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		String sql = "delete from item where(id=?)";
		boolean result = false;
		
		try {
			conn = DBconnect.connect();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);

			int r = pstmt.executeUpdate();
			if( r > 0 ) {
				result = true;
			}			
		}catch(Exception e) {
			e.printStackTrace();
    	}finally {
    		DBconnect.close();
    	}	
		return result;
	}
	// 해당 상품에 속하는 모든 제품 가져오기
	public Vector<String> getProductsByItem(String itemName) throws SQLException {
	    Vector<String> productList = new Vector<>();
	    Connection conn = null;
	    PreparedStatement pstmt = null;
	    ResultSet rs = null;
	    String sql = "SELECT item_name FROM item WHERE item_name = ?";

	    try {
	        conn = DBconnect.connect();
	        pstmt = conn.prepareStatement(sql);
	        pstmt.setString(1, itemName);
	        rs = pstmt.executeQuery();

	        while (rs.next()) {
	            String productName = rs.getString("item_name");
	            productList.add(productName);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } finally {
	        DBconnect.close();
	    }
	    return productList;
	}

	
}
