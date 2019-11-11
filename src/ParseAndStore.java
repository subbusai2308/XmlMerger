import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ParseAndStore {
	public static void main(String[] args)
			throws ParserConfigurationException, SAXException, IOException, SQLException, ClassNotFoundException {
		// Get Document Builder
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection(
				"jdbc:mysql://localhost:3306/[dbname]?autoReconnect=true&useSSL=false", "[username]", "[password]");

		// Build Document
		Document document = builder.parse(new File("persondata.xml"));

		// Normalize the XML Structure; It's just too important !!
		document.getDocumentElement().normalize();

		// Here comes the root node
		Element root = document.getDocumentElement();
		System.out.println(root.getNodeName());

		// Get all employees
		NodeList nList = document.getElementsByTagName("person");
		System.out.println("============================");

		for (int temp = 0; temp < nList.getLength(); temp++) {
			Node node = nList.item(temp);
			System.out.println(""); // Just a separator
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				// Print each employee's detail
				Element eElement = (Element) node;
				String name = eElement.getAttribute("name").toString();
				String addre = eElement.getElementsByTagName("address").item(0).getTextContent().toString();
				String phone = eElement.getElementsByTagName("phonenumber").item(0).getTextContent().toString();
				String salary = eElement.getElementsByTagName("salary").item(0).getTextContent().toString();
				String pension = eElement.getElementsByTagName("pension").item(0).getTextContent().toString();
				System.out.println("Employee id : " + name);
				System.out.println("Address : " + addre);
				System.out.println("Phone number : " + phone);
				System.out.println("Salary : " + salary);
				System.out.println("Pension: " + pension);
				PreparedStatement stmt = con.prepareStatement("insert into person values(?,?,?,?,?)");
				stmt.setString(1, name);
				stmt.setString(2, addre);
				stmt.setString(3, phone);
				stmt.setString(4, salary);
				stmt.setString(5, pension);
				stmt.executeUpdate();
			}
		}
	}
}
