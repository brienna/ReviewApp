import java.sql.*;
import java.util.*;

/**
 *1. Start mysql.
 *2. https://stackoverflow.com/questions/4093603/how-do-i-find-out-my-mysql-url-host-port-and-username
 *3. In terminal, `create database appReviews` then `use appReviews`
 *4. Load sql: `\. /Users/Brienna/Documents/Coursework/Master's Program/Summer 2018/ISTE 612/Team Project/612project.sql`
 *5. Downloaded mysql-connector file from https://dev.mysql.com/downloads/file/?id=477058 because of https://stackoverflow.com/questions/17484764/java-lang-classnotfoundexception-com-mysql-jdbc-driver-in-eclipse
 *6. javac -cp mysql-connector-java-8.0.11.jar Clusterizer.java Row.java
 *7. java -cp .:mysql-connector-java-8.0.11.jar Clusterizer
 */
public class Clusterizer {
   static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
   static final String DB_URL = "jdbc:mysql://localhost:3306/appReviews?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&useSSL=false"; //replace this with your db
   static final String password = "YOUR PASSWORD FOR MYSQL"; // change this

   //replace with your corresponding credentials
   static final String USER = "root";
   static final String PASS = PASSWORD;
   private ArrayList<Row> dataRows = new ArrayList<Row>();


   public static void main(String [] args){
	   Clusterizer c = new Clusterizer();
	   c.init();
     c.showSomeData();

   }

   public Clusterizer(){
   }

   /**
    * Ignore this method. You can delete it. It was for data exploration and is very disorganized.
    *
    */
   public void showSomeData() {
    // Show number of reviews
    int numOfReviews = 0;
      for (Row dataRow : dataRows) {
        numOfReviews++;
      }
      System.out.println("Number of reviews: " + numOfReviews);

      // Show info distinct app names reviewed,
      // num of apps,
      // and how many reviews each app has
      System.out.println("\nWhich apps are reviewed?");
      HashMap<String, Integer> appReviewCount = new HashMap<String, Integer>();
      // Gather info for Sheep-O-block
      int infoGivsShp = 0;
      int infoSeeksShp = 0;
      int featRequestsShp = 0;
      int hasBugsShp = 0;
      int sentScoresShp = 0;
      int empties = 0;
      int sheepDupes = 0;
      ArrayList<String> sheeps = new ArrayList<String>();
      for (Row dataRow : dataRows) {
        String name = dataRow.getAppName();
        if (!appReviewCount.containsKey(name)) {
          appReviewCount.put(name, 1);
        } else {
          appReviewCount.put(name, appReviewCount.get(name) + 1);
        }

        if (name.equals("Sheep-O-block")) {
        String infoGiv = dataRow.getInfoGiv();
        String infoSeek = dataRow.getInfoSeek();
        String featRequest = dataRow.getFeatRequest();
        String hasBug = dataRow.getHasBug();
        int sentScore = Integer.parseInt(dataRow.getSentScore());
        String text = dataRow.getText().trim();
        if (!sheeps.contains(text)) {
          sheeps.add(text);
        } else {
          sheepDupes++;
        }

            if (infoGiv.equals("1")) {
              infoGivsShp++;
            }
            if (infoSeek.equals("1")) {
              infoSeeksShp++;
            }
            if (featRequest.equals("1")) {
              featRequestsShp++;
            }

            // Count hasBug
            if (hasBug.equals("1")) {
              hasBugsShp++;
            }

            // Record sentiment
            sentScoresShp += sentScore;


            if (text.equals("")) {
              empties++;
            }

        }
      }
      for (String appName : appReviewCount.keySet()) {
        System.out.println(appName + ": " + appReviewCount.get(appName));
      }

      System.out.println("\n For Sheep-0-block...");
      System.out.println("info giving: " + infoGivsShp);
      System.out.println("info seeking: " + infoSeeksShp);
      System.out.println("feature requests: " + featRequestsShp);
      System.out.println("bug reports: " + hasBugsShp);
      System.out.println("sentiment score: " + sentScoresShp);
      System.out.println("empties: " + empties);
      System.out.println("dupes: " + sheepDupes);

      System.out.println("\nNum of apps reviewed: " + appReviewCount.size());

      // Count number of labelled data
      int labelled = 0;
      int infoGivs = 0;
      int infoSeeks = 0;
      int featRequests = 0;
      int hasBugs = 0;
      int sentScores;
      for (Row dataRow : dataRows) {
        String infoGiv = dataRow.getInfoGiv();
        String infoSeek = dataRow.getInfoSeek();
        String featRequest = dataRow.getFeatRequest();
        String hasBug = dataRow.getHasBug();
        String sentScore = dataRow.getSentScore();

        if (infoGiv != null && infoSeek != null && featRequest != null && hasBug != null && sentScore != null) {
          labelled++;

          // Count infoGiving
          if (infoGiv.equals("1")) {
            infoGivs++;
          }

          // Count infoSeeking
          if (infoSeek.equals("1")) {
            infoSeeks++;
          }

          // Count featureRequests
          if (featRequest.equals("1")) {
            featRequests++;
          }

          // Count hasBug
          if (hasBug.equals("1")) {
            hasBugs++;
          }


        }
      }
      System.out.println("Labelled: " + labelled);
      System.out.println("info giving: " + infoGivs);
      System.out.println("info seeking: " + infoSeeks);
      System.out.println("feature requests: " + featRequests);
      System.out.println("bug reports: " + hasBugs);


      // Count number of duplicate/empty documents
      ArrayList<String> reviews = new ArrayList<String>();
      int duplicates = 0;
      int emptyDocs = 0;
      for (Row dataRow : dataRows) {
        String text = dataRow.getText();
        if (text.trim().equals("")) {
          emptyDocs++;
        } else if (!reviews.contains(text)) {
          reviews.add(text);
        } else {
          duplicates++;
        }
      }
      System.out.println("Duplicates: " + duplicates);
      System.out.println("Empty documents: " + emptyDocs);
   }

   public void init(){
      Connection conn = null;
      Statement stmt = null;

       try {
           Class.forName(JDBC_DRIVER);
           conn = DriverManager.getConnection(DB_URL, USER, PASS);

           stmt = conn.createStatement();
           String sql = "SELECT * FROM review";
           ResultSet rs = stmt.executeQuery(sql);
           while(rs.next()) {
        	   int id = rs.getInt("id");
        	   String appName = rs.getString("app_name");
        	   String versionNum = rs.getString("version");
        	   String userName = rs.getString("userid");
        	   String date = rs.getString("date");
        	   int rating = rs.getInt("rating");
        	   String title = rs.getString("title");
        	   String text = rs.getString("text");
        	   String infoGiv = rs.getString("has_information_giving");
        	   String infoSeek = rs.getString("has_information_seeking");
        	   String featureReq = rs.getString("has_feature_Request");
        	   String bugReport = rs.getString("has_bug_report");
        	   String sentScore = rs.getString("sentiment_score");
        	   Row r = new Row(id,appName,versionNum,userName,date,rating,title,text,infoGiv,infoSeek,featureReq,bugReport,sentScore);
        	   	dataRows.add(r);
           }
           rs.close();
       }
       catch(SQLException se){
    	   se.printStackTrace();

       } catch(Exception e) {
    	   e.printStackTrace();
       } finally{
    	      //finally block used to close resources
    	      try{
    	         if(stmt!=null)
    	            conn.close();
    	      }catch(SQLException se){
    	      }// do nothing
    	      try{
    	         if(conn!=null)
    	            conn.close();
    	      }catch(SQLException se){
    	         se.printStackTrace();
    	      }//end finally try
    	   } //end try

   }

}
