package org.ruibu.estore.etl

import org.ruibu.estore.core.EstoreApp
import org.tresamigos.smv._

/**
 * Created by bzhang on 6/12/15.
 */

object Product extends SmvModule("Discribe Product") {

  override def version() = 0

  override def requiresDS() = Seq(
    EstoreApp.stk,
    EstoreApp.brand,
    EstoreApp.cat1,
    EstoreApp.cat2,
    EstoreApp.cat3,
    EstoreApp.cat5,
    EstoreApp.cat6,
    EstoreApp.cat7,
    EstoreApp.cat8
  )

  override def run(i: runParams) = {
    val srdd = i(EstoreApp.stk)
    import srdd.sqlContext.implicits._

    srdd.selectMinus("CAT4_ID").
      joinByKey(i(EstoreApp.brand), Seq("BRAND_ID"), "leftouter").renameField("NAME" -> "BRAND_NAME").
      joinByKey(i(EstoreApp.cat1), Seq("CAT1_ID"), "leftouter").renameField("NAME" -> "CAT1_NAME").
      joinByKey(i(EstoreApp.cat2), Seq("CAT2_ID"), "leftouter").renameField("NAME" -> "CAT2_NAME").
      joinByKey(i(EstoreApp.cat3), Seq("CAT3_ID"), "leftouter").renameField("NAME" -> "CAT3_NAME").
      joinByKey(i(EstoreApp.cat5), Seq("CAT5_ID"), "leftouter").renameField("NAME" -> "CAT5_NAME").
      joinByKey(i(EstoreApp.cat6), Seq("CAT6_ID"), "leftouter").renameField("NAME" -> "CAT6_NAME").
      joinByKey(i(EstoreApp.cat7), Seq("CAT7_ID"), "leftouter").renameField("NAME" -> "CAT7_NAME").
      joinByKey(i(EstoreApp.cat8), Seq("CAT8_ID"), "leftouter").renameField("NAME" -> "CAT8_NAME")

  }
}