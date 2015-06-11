package org.ruibu.estore.etl

import org.ruibu.estore.core._
import org.tresamigos.smv._
import org.apache.spark.sql.functions._

/**
 * ETL Module on Transactions
 */

object TranRetail extends SmvModule("Tran data on POS out and Customer Return only") {

  override def version() = 0;
  override def requiresDS() = Seq(EstoreApp.trn);
 
  override def run(i: runParams) = {
    val srdd = i(EstoreApp.trn)
    import srdd.sqlContext.implicits._
    
    srdd.where($"INVMOVE_ID".in(
      lit("POSOUT"), 
      lit("RNCN")))
  }
}

object TranInvtrn extends SmvModule("Tran data on INVTRNIN/OUT only") {

  override def version() = 0;
  override def requiresDS() = Seq(EstoreApp.trn);
 
  override def run(i: runParams) = {
    val srdd = i(EstoreApp.trn)
    import srdd.sqlContext.implicits._
    
    srdd.where($"INVMOVE_ID".in(
      lit("INVTRNIN"), 
      lit("INVTRNOUT")))
  }
}


object TranOther extends SmvModule("Tran data on all other INVMOVE_ID") {

  override def version() = 0;
  override def requiresDS() = Seq(EstoreApp.trn);
 
  override def run(i: runParams) = {
    val srdd = i(EstoreApp.trn)
    import srdd.sqlContext.implicits._
    
    srdd.where(! $"INVMOVE_ID".in(
      lit("POSOUT"), 
      lit("RNCN"),
      lit("INVTRNIN"), 
      lit("INVTRNOUT")))
  }
}


