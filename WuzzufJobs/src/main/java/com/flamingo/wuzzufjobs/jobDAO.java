
package com.flamingo.wuzzufjobs;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import smile.data.DataFrame;
import tech.tablesaw.api.Table;
/**
 *
 * @author noham
 */
public interface jobDAO {
    
    public void readCSV ();
    public List<Job> getJobListDF();
    public List<Job> getJobList() ;
    public DataFrame getJobsDataFrame() ;
    public Table getJobsTable(); 
    public List <Job> cleanData ();
    public Map<String, Integer> countJobsCompany() ;
    public String plotData();
    public String plotLastComData();
    public HashMap<String, Integer> popularJobTitles();
    public String plotPopTitle();
    public HashMap<String, Integer> MostPopArea();
    public String plotMPArea() ;
    public HashMap<String, Integer> Skills();
    
    
}
