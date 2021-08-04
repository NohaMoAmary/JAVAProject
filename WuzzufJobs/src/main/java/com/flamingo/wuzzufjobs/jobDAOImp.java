/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.flamingo.wuzzufjobs;

import java.io.File;
import org.apache.commons.csv.CSVFormat;
import smile.data.DataFrame;
import smile.data.Tuple;
import smile.io.Read;
import java.io.IOException;
import java.net.URISyntaxException;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import tech.tablesaw.api.*;    
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;



/**
 *
 * @author noham
 */
public class jobDAOImp implements jobDAO{
    private DataFrame wuzzafJobsDF ;
    private Table wuzzafJobsTb;
    private List<Job> jobList ;
    private List<Job> jobList2 ;
    Map<String, String> summary = new HashMap<>();
    Map <String , Integer> CountJobs = new LinkedHashMap<>();
    Map <String , Integer> popTitle = new LinkedHashMap<>();
    Map <String , Integer> popArea = new LinkedHashMap<>();
    Map <String , Integer> Skill = new LinkedHashMap<>();
    String fname ="C:/Users/noham/Desktop/Wuzzuf_Jobs.csv"; 
    @Override
    public void readCSV() {
        //to Table saw 
        wuzzafJobsTb = null;
        try {
            wuzzafJobsTb = Table.read ().csv (fname);
        } catch (IOException ex) {
            Logger.getLogger(jobDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        //to Dataframe
        CSVFormat format = CSVFormat.DEFAULT.withFirstRecordAsHeader ();
        DataFrame df = null;
        
        try {
        df = Read.csv (fname, format);
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(jobDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        wuzzafJobsDF = df.select ("Title", "Company", "Location", "Type", "Level","YearsExp","Country","Skills");
    }
    @Override
    public DataFrame getJobsDataFrame() {
        readCSV();
        return wuzzafJobsDF;
    }
    @Override
    public Table getJobsTable() {
        return wuzzafJobsTb;
    }
    @Override
    public List<Job> getJobList() {
        jobList = new ArrayList <Job>();
        //readCSV();       
        for (Row row : wuzzafJobsTb) {   
            
         String s = row.getString("Skills");  // returns each string in the named column
         String [] temp2 ;
         temp2 = s.split(",") ;
         Job j = new Job (row.getString("Title"),row.getString( "Company"),row.getString("Location"),row.getString ("Type"),row.getString("Level") ,row.getString("YearsExp"), row.getString ("Country"),temp2);
         jobList.add(j);
         
     }
       
        return jobList;
    }
    
    @Override
    public List<Job> getJobListDF() {
        jobList2 = new ArrayList <Job>();
        //readCSV();
        ListIterator<Tuple> iterator = wuzzafJobsDF.stream ().collect (Collectors.toList ()).listIterator ();
        while (iterator.hasNext ()) {
            Tuple t = iterator.next ();
            String temp =t.get ("Skills").toString();
            String [] temp2 ;
            temp2 = temp.split(",") ;
            Job j = new Job ((String) t.get ("Title"),(String) t.get ( "Company"),(String) t.get ("Location"),(String) t.get ("Type"),(String) t.get ("Level") ,(String) t.get ("YearsExp"), (String) t.get ("Country"),temp2);
            jobList2.add (j);
            
        }
        return jobList2;
    }
    /////////////////////////////////////////////////////////////////////////////////////
   
    ////////////////////////////////////////////////////////
    //cleare all null and duplicat 
    @Override
    public List<Job> cleanData()
    {
    wuzzafJobsDF =wuzzafJobsDF.omitNullRows(); 
    jobList2 = new ArrayList <Job>();
        
    ListIterator<Tuple> iterator = wuzzafJobsDF.stream ().collect (Collectors.toList ()).listIterator ();
        while (iterator.hasNext ()) {
            Tuple t = iterator.next ();
            String temp =t.get ("Skills").toString();
            String [] temp2 ;
            temp2 = temp.split(",") ;
            Job j = new Job ((String) t.get ("Title"),(String) t.get ( "Company"),(String) t.get ("Location"),(String) t.get ("Type"),(String) t.get ("Level") ,(String) t.get ("YearsExp"), (String) t.get ("Country"),temp2);
            jobList2.add (j);
            
        }
    return jobList2;
    }
    

   @Override
    public Map<String, Integer> countJobsCompany() {
        readCSV();
        jobList2 = getJobListDF();
        jobList2 =cleanData();
        if (!jobList2.isEmpty())
        {
         for (Job j :jobList2)
         {
             String comp = j.getCompany();
             Integer count = CountJobs.get(comp);
             if (count ==null)
             {
              count = 1;
             }else{
             count ++ ;
             }
             CountJobs.put(comp, count);
         }
        }
        //return CountJobs;
        
         // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
               new LinkedList<Map.Entry<String, Integer> >(CountJobs.entrySet());
 
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Collections.reverse(list);
        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
    
    @Override
     public String plotData() {
       Map<String, Integer> x =countJobsCompany();
       // setting up iterator.
       Iterator it = x.entrySet().iterator();
       DefaultPieDataset dataset = new DefaultPieDataset();
       // iterating every set of entry in the HashMap. 
	while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        dataset.setValue((Comparable) pair.getKey(), (Number) pair.getValue());
        }
    
        JFreeChart chart = ChartFactory.createPieChart(
            "task 5: Count the jobs for each company",  // chart title
            dataset,             // data
            false,               // include legend
            true,
            false
        );

        try {
            ChartUtilities.saveChartAsPNG(new File ("D:/img/finalPie.png"), chart,3000, 3000);
        } catch (IOException ex) {
            Logger.getLogger(jobDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Thread.sleep(6000);
        } catch (InterruptedException ex) {
            Logger.getLogger(jobDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "finalPie.png";
        }
////////////////////////////////////////////////////////////////////////////////
     @Override
     public String plotLastComData() {
        Map<String, Integer> x =countJobsCompany();
        Iterator it = x.entrySet().iterator();
        DefaultPieDataset dataset = new DefaultPieDataset();
        int count =0 ;
        // iterating every set of entry in the HashMap. 
	while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        dataset.setValue((Comparable) pair.getKey(), (Number) pair.getValue());
        if (count >10){break ;}
        count ++;
        }
    
        JFreeChart chart = ChartFactory.createPieChart(
            " top 10 Count jobs for each company",  // chart title
            dataset,             // data
            true,               // include legend
            true,
            false
        );

        try {
            ChartUtilities.saveChartAsPNG(new File ("D:/img/top10Comp.png"), chart,700, 500);
        } catch (IOException ex) {
            Logger.getLogger(jobDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException ex) {
            Logger.getLogger(jobDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
       return "top10Comp.png";
        }
////////////////////////////////////////////////////////////////////////////////
    //Find out What are it the most popular job titles
    @Override
     public HashMap<String, Integer> popularJobTitles() {
        readCSV();
        jobList2 = getJobListDF();
        jobList2 =cleanData();
        if (!jobList2.isEmpty())
        {
         for (Job j :jobList2)
         {
             String comp = j.getTitle();
             Integer count = popTitle.get(comp);
             if (count ==null)
             {
              count = 1;
             }else{
             count ++ ;
             }
             popTitle.put(comp, count);
         }
        }
        
         // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
               new LinkedList<Map.Entry<String, Integer> >(popTitle.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Collections.reverse(list);
        // put data from sorted list to hashmap
        int count=0;
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            if (count <10)
            {
            temp.put(aa.getKey(), aa.getValue());
            }
            else {break ;}
            count++ ;
        }
        return temp ;
     }
////////////////////////////////////////////////////////////////////////////////
     @Override
     public String plotPopTitle() {
        Map<String, Integer> x =popularJobTitles();
        Iterator it = x.entrySet().iterator();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // iterating every set of entry in the HashMap. 
	while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        dataset.addValue((Number) pair.getValue(),(Comparable) pair.getKey(),(Comparable) pair.getKey());
        }
         JFreeChart chart=ChartFactory.createBarChart(  
            "the most popular job titles", //Chart Title  
            "Title", // Category axis  
            "how many ", // Value axis  
            dataset,  
            PlotOrientation.VERTICAL,  
            true,true,false  
        );  

        try {
            ChartUtilities.saveChartAsPNG(new File ("D:/img/popTitle.png"), chart,700, 500);
        } catch (IOException ex) {
            Logger.getLogger(jobDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return "popTitle.png";
        }
////////////////////////////////////////////////////////////////////////////////
////////////////////////////////////////////////////////////////////////////////
//Find out the most popular areas?
     
    @Override
    public HashMap<String, Integer> MostPopArea() {
        readCSV();
        jobList2 = getJobListDF();
        jobList2 =cleanData();
        if (!jobList2.isEmpty())
        {
         for (Job j :jobList2)
         {
             String comp = j.getLocation();
             Integer count = popArea.get(comp);
             if (count ==null)
             {
              count = 1;
             }else{
             count ++ ;
             }
             popArea.put(comp, count);
         }
        }
        
         // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
               new LinkedList<Map.Entry<String, Integer> >(popArea.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            @Override
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Collections.reverse(list);
        // put data from sorted list to hashmap
        int count=0;
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            if (count <10)
            {
            temp.put(aa.getKey(), aa.getValue());
            }
            else {break ;}
            count++ ;
        }
        return temp ;
     }
////////////////////////////////////////////////////////////////////////////////
     @Override
     public String plotMPArea() {
        Map<String, Integer> x =MostPopArea();
        Iterator it = x.entrySet().iterator();
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        // iterating every set of entry in the HashMap. 
	while (it.hasNext()) {
        Map.Entry pair = (Map.Entry)it.next();
        dataset.addValue((Number) pair.getValue(),(Comparable) pair.getKey(),(Comparable) pair.getKey());
        }
         JFreeChart chart=ChartFactory.createBarChart(  
            "the most popular job Area", //Chart Title  
            "Location", // Category axis  
            "number of Companies ", // Value axis  
            dataset,  
            PlotOrientation.VERTICAL,  
            true,true,false  
        );  

        try {
            ChartUtilities.saveChartAsPNG(new File ("D:/img/popArea.png"), chart,500, 300);
        } catch (IOException ex) {
            Logger.getLogger(jobDAOImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "popArea.png";
        }
////////////////////////////////////////////////////////////////////////////////
    @Override
    public HashMap<String, Integer> Skills() {
        readCSV();
        jobList2 = getJobListDF();
        jobList2 =cleanData();
        List<String> allSkils= new ArrayList<String>() ;
        for (Job j :jobList2)
         {
             String[]comp = j.getSkills();
             for (String s:comp)
             {
               allSkils.add(s);
             }
         }
        for (String s:allSkils)
             {
             Integer count = Skill.get(s);
             if (count ==null)
             {
              count = 1;
             }else{
             count ++ ;
             }
             Skill.put(s, count);
         }
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
               new LinkedList<Map.Entry<String, Integer> >(Skill.entrySet());
        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });
        Collections.reverse(list);
        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;


    } 
////////////////////////////////////////////////////////////////////////////////


}