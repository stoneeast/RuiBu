package org.ruibu.estore.core

import org.apache.spark.SparkContext
import org.tresamigos.smv._

/**
 * Driver for all LungCancer applications.
 * The app name is taken as a parameter to allow individual modules to override the app name
 * in their standalone run mode.
 */
class EstoreApp(_appName: String,
                  _args: Seq[String],
                  _sc: Option[SparkContext] = None
                  )
      extends SmvApp(_appName, _args, _sc) {

  val num_partitions = sys.env.getOrElse("PARTITIONS", "64")
  sqlContext.setConf("spark.sql.shuffle.partitions", num_partitions)
  
  override val rejectLogger = new SCRejectLogger(sc, 3)
  override def getModulePackages() = Seq(
    "org.ruibu.estore.etl",
    "org.ruibu.estore.adhoc"
  )
}

object EstoreApp {
  val caBar = new CsvAttributes(delimiter = '|', hasHeader = true)
  
  /********************************************************************
   * Product
   ********************************************************************/
  val stk = SmvCsvFile("input/prod/stkmas.txt", caBar) 
  def main(args: Array[String]) {
    new EstoreApp("EstoreApp", args).run()
  }
}

