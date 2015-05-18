package org.ruibu.estore.etl

import org.ruibu.estore.core._
import org.tresamigos.smv._

/**
 * ETL Module Example
 */

object EtlExample extends SmvModule("ETL Example") {

  override def version() = 0;
  override def requiresDS() = Seq(EstoreApp.stk);
 
  override def run(i: runParams) = {
    val srdd = i(EstoreApp.stk)
    import srdd.sqlContext.implicits._
    
    srdd.select(
      "BRAND_ID"
      )
  }
}
