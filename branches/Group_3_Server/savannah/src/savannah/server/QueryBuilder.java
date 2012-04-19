package savannah.server;

import java.util.ArrayList;
import java.util.List;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.xpath.XPath;

public class QueryBuilder {
	public ArrayList<String> buildQueries(Document xml) throws JDOMException
	{
		ArrayList<String> out = new ArrayList<String>();
		String[] tables = {"Apps","HasGuardian","HasSubDepartment","ListOfApps","AuthUsers",
				"Profiles","Departments","HasDepartment","MediaProfileAccess","Media","HasTag","Tags","MediaDepartmentAccess","HasLink"};
		//Get all entries
		XPath xp = XPath.newInstance("sw6ml/*/*");
		List<Object> nodes = xp.selectNodes(xml);
		for(Object e : nodes)
		{
			Element entry = (Element)e;
			//Retrieve the table name
			String table = entry.getParentElement().getName();
			//Retrieve the crud type
			String type = entry.getAttributeValue("action");
			//Retrieve all children of the current Entry
			List<Element> vals = entry.getChildren();
			String[] str;
			if(type.equals("update"))
			{
				str = new String[vals.size()+4];
			}
			else
			{
				str = new String[vals.size()+2];
			}
			str[1] = table;
			str[0] = type;
			int i = 2;
			for(Element s: vals)
			{
				if(type.equals("update"))
				{
					str[i] = s.getName();
					i++;
				}
				str[i] = addApos(s.getText(),s.getAttributeValue("type"));
				i++;
			}		
			out.add(buildQuery(str));		
		}
		return out;
	}
	
	private String buildQuery(String[] str)
	{	
		/*
		 * These are the different crud types, create,delete and update, The way this is implemented
		 * will require certain preconditions on how the xml is formed.
		 * Current implementation only supports simple create, delete and update queries.
		 * READ: NYI
		 */

		//Create require all tags or some values will be placed wrong in the sql query.
		if(str[0].equals("create"))
		{
			StringBuilder builder = new StringBuilder();
			String tmp = "";
			for(int i = 2;i < str.length;i++)
			{
				tmp = str[i];
				builder.append(tmp);
				if(tmp != str[str.length-1])
				{
					builder.append(",");
				}
			}
			return "INSERT INTO " + str[1] + " values("+ builder.toString() +");";
		}
		//Delete will only need 1 tag, the unique identifier of the table.
		else if(str[0].equals("delete"))
		{
			return "DELETE FROM " + str[1] + " where idUsers=" + str[2] + ";";
		}
		//Update will require two tags, the tag that is being updated, and the unique identifier of the table.
		else if(str[0].equals("update"))
		{	
			//FIXME This will only work properly if the unique identifier of a table is the first tag in the xml document
			//FIXME and it will only be able to update one attribute at a time, is this the way we want it to work?
			return "UPDATE " + str[1] +" SET " + str[4] +"="+str[5]+" WHERE " + str[2] + "="+str[3]+";";
		}
		//This return should never be reachable, a JDOMException should occur if the xml document is not well formed.
		return "";	
	}
	
	//Query builder for CRUD:Read
	public ArrayList<String> buildQueries(String s)
	{
		String profiles = null;
		String cog = null;
		ArrayList<String> out = new ArrayList<String>();
		if(s.contains("profiles"))
		{
			profiles = s.substring(s.indexOf('=',s.indexOf("profiles="))+1, s.indexOf('&', s.indexOf("profiles=")));
			String[] sarray = profiles.split(",");
			for(int i = 0;i < sarray.length-1;i = i+2)
			{	
				out.addAll(buildGetProfileQueries(sarray[i],sarray[i+1]));
			}
		}
		String cogs = "childrenOfGuardian";
		if(s.contains(cogs))
		{	
			cog = s.substring(s.indexOf('=',s.indexOf(cogs+"="))+1,s.indexOf('&', s.indexOf(cogs+"=")));
			String[] sarray = cog.split(",");
			for(String str : sarray)
			{
				out.add(buildGetCogQueries(str));
			}
		}	
		return out;
	}
	//TODO TIs is some shizzle, to fix soon
	//INCOMPLETE 
	//Query builder continued, for CRUD:Read
	private ArrayList<String> buildGetProfileQueries(String cert,String id)
	{
		ArrayList<String> out = new ArrayList<String>();
		out.add("SELECT * FROM AuthUsers where certificate='"+cert+"';");
		out.add("SELECT * from Profile where idProfile=(select idUser from AuthUsers where certificate='"+cert+"';");
		return out;
	}
	
	private String buildGetCogQueries(String id)
	{
		return id;
	}
	
	private String addApos(String str,String type)
	{
		//Dirty implementation
		if(type.equals("string"))
		{
			return "'"+str+"'";
		}
		else { return str ;}
	}
	
}