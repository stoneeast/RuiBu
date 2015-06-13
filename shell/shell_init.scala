import org.apache.spark.sql._, functions._ 
import org.apache.spark.rdd.RDD
import org.tresamigos.smv._
//import org.ruibu.estore._, core._ , etl._, adhoc._
import org.ruibu.estore._, core._ , etl._

val app = new EstoreApp("Estore App", Seq("-d", "None"), Option(sc))
val sqlContext = app.sqlContext; import sqlContext.implicits._
  
// create the init object "i" rather than create initialization at top level
// because shell would launch a separate command for each evalutaion which
// slows down startup considerably.
// keeping object name short to make the contents easy to access.
object i {
  import java.io.{File, PrintWriter}
  
  implicit class ShellSrddHelper(srdd: DataFrame) {
    def save(path: String) = {
      var helper = new org.tresamigos.smv.SmvDFHelper(srdd)
      helper.saveAsCsvWithSchema(path)(CsvAttributes.defaultCsvWithHeader)
    }
    
    def savel(path: String) = {
      var res = srdd.collect.map{r => r.mkString(",")}.mkString("\n")
      val pw = new PrintWriter(new File(path))
      pw.println(res)
      pw.close()
    } 
  }
  
  implicit class ShellRddHelper(rdd: RDD[String]) {
    def savel(path: String) = {
      var res = rdd.collect.mkString("\n")
      val pw = new PrintWriter(new File(path))
      pw.println(res)
      pw.close()
    }
  }

  //-------- some helpful functions
  def s(ds: SmvDataSet) = app.resolveRDD(ds)
  def j() = app.genJSON()
  
  def open(fullPath: String)(implicit ca: CsvAttributes) = {
    sqlContext.csvFileWithSchema(fullPath)
  }
  
  def findSchema(path: String, n: Int = 100000)(implicit csvAttributes: CsvAttributes) = {
    val schema = sqlContext.discoverSchemaFromFile(path, n)
    val outpath = SmvSchema.dataPathToSchemaPath(path)
    schema.saveToLocalFile(outpath)
  }
  
  val p = EstoreApp
}

import i._
