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
   * Products
   ********************************************************************/
  val stk = SmvCsvFile("input/prod/stkmas.txt", caBar)
  val brand = SmvCsvFile("input/prod/brand.txt", caBar)
  val cat1 = SmvCsvFile("input/prod/cat1.txt", caBar)
  val cat2 = SmvCsvFile("input/prod/cat2.txt", caBar)
  val cat3 = SmvCsvFile("input/prod/cat3.txt", caBar)
  val cat5 = SmvCsvFile("input/prod/cat5.txt", caBar)
  val cat6 = SmvCsvFile("input/prod/cat6.txt", caBar)
  val cat7 = SmvCsvFile("input/prod/cat7.txt", caBar)
  val cat8 = SmvCsvFile("input/prod/cat8.txt", caBar)

  /********************************************************************
   * Transactions
   ********************************************************************/
  val trn = SmvCsvFile("input/trnx/transaction.txt", caBar)
  val store = SmvCsvFile("input/trnx/STORE.txt", caBar)
  val invmv = SmvCsvFile("input/trnx/INVMOVEID.txt", caBar)

  def main(args: Array[String]) {
    new EstoreApp("EstoreApp", args).run()
  }
}

