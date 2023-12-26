package com.Accio;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet("/Search")

public class Search extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException{
        //getting keyword from front end
        String keyword=request.getParameter("keyword");
        //setting up connection to database
        Connection connection=DatabaseConnection.getConnection();
        try {
            //store query of user
            PreparedStatement preparedStatement= connection.prepareStatement("Insert Into history values(? ,?);");
            preparedStatement.setString(1,keyword);
            preparedStatement.setString(2,"http://localhost:8080/SearchEngine/Search?keyword="+keyword);
            preparedStatement.executeUpdate();
            //getting results after running the ranking query
            ResultSet resultSet = connection.createStatement().executeQuery("select pageid, pagelink, (length(lower(pagetext))-length(replace(lower(pagetext),'" + keyword.toLowerCase() + "','')))/length('" + keyword.toLowerCase() + "') as countoccurence from pages order by countoccurence limit 30;");
            ArrayList<SearchResult> results = new ArrayList<>();
            //transferring value from result set to results arraylist
            while (resultSet.next()) {
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(resultSet.getString("pageid"));
                searchResult.setLink(resultSet.getString("pagelink"));
                results.add(searchResult);
            }
            //displaying results arraylist in console
            for(SearchResult result:results){
                System.out.println(result.getTitle()+"\n"+result.getLink()+"\n");
            }
            request.setAttribute("results",results);
            request.getRequestDispatcher("Search.jsp").forward(request,response);

            response.setContentType("text/html");
            PrintWriter out = response.getWriter();
        }
        catch(SQLException | ServletException sqlException){
            sqlException.printStackTrace();
        }

    }
}
