<%@ page import="java.sql.*"%>

<%  
String s=request.getParameter("val");  
if(s==null || s.trim().equals("")){  
out.print("Please enter name");  
}else{  
try{  

	Class.forName("com.mysql.cj.jdbc.Driver");  
	Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/[dbname]?autoReconnect=true&useSSL=false","[username]","[password]");
	s = s
		    .replace("!", "!!")
		    .replace("%", "!%")
		    .replace("_", "!_")
		    .replace("[", "![");
		PreparedStatement pstmt = con.prepareStatement(
		        "SELECT * FROM person");
		ResultSet rs=pstmt.executeQuery();  
		while(rs.next()){  
		if(s.contains(rs.getString(1)+"")||s.contains(rs.getString(2)+"")||s.contains(rs.getString(3)+"")||s.contains(rs.getString(4)+"")||s.contains(rs.getString(5)+""))
		out.print(rs.getString(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4)+" "+rs.getString(5)+"\n");  
		}  
	con.close();  
}catch(Exception e){e.printStackTrace();}  
}  
%>
