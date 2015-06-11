package org.ruibu.estore.etl

import org.ruibu.estore.core._
import org.tresamigos.smv._
import org.apache.spark.sql.functions._

/**
 * ETL Modules for inventory calculation
 */


object TranDaily extends SmvModule("Tran daily summary by store by product") {

  override def version() = 0;
  override def requiresDS() = Seq(EstoreApp.trn);
 
  override def run(i: runParams) = {
    val srdd = i(EstoreApp.trn)
    import srdd.sqlContext.implicits._
    
    srdd.where($"STK_QTY" >= 0 && $"STK_QTY" <= 10000).
      selectPlus(
        columnIf($"MOVE_FLG" === "I", lit(1), lit(-1)) * $"STK_QTY" as "qty"
      ).smvGroupBy("STORE_ID", "STK_ID", "DOC_DATE").aggWithKeys(sum($"qty") as "qty")
  }
}

object Inventory extends SmvModule("Recover daily inventory from tran data") {

  override def version() = 0;
  override def requiresDS() = Seq(TranDaily)
 
  override def run(i: runParams) = {
    val srdd = i(TranDaily)
    import srdd.sqlContext.implicits._
    
    srdd.
      smvGroupBy("STORE_ID", "STK_ID").
      runAgg("DOC_DATE")(
        $"STORE_ID", $"STK_ID", $"DOC_DATE", $"qty", 
        sum($"qty") as "invqty")
  }
}

