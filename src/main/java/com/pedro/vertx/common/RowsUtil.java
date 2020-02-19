package com.pedro.vertx.common;

import io.vertx.core.json.JsonObject;
import io.vertx.reactivex.sqlclient.Row;
import io.vertx.reactivex.sqlclient.RowSet;

import java.util.ArrayList;
import java.util.List;

public class RowsUtil {

  public static List<JsonObject> row2List(RowSet<Row> result) {
    if (result.size() == 0)
      return new ArrayList<>(0);
    List<JsonObject> res = new ArrayList(result.size());
    JsonObject json = new JsonObject();
    List<String> columnsNames = result.columnsNames();
    result.forEach(it -> {
      columnsNames.forEach(name -> {
        Object value = it.getValue(name);
        json.put(name, value);
      });
      res.add(json);
    });
    return res;
  }
}
