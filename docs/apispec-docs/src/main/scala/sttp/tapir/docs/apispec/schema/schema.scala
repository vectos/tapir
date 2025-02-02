package sttp.tapir.docs.apispec

import sttp.tapir.{SchemaType => TSchemaType, Schema => TSchema}

package object schema {
  private[docs] type ObjectSchema = (TSchemaType.SObjectInfo, TSchema[_])
  private[docs] type ObjectKey = String

  private[docs] def calculateUniqueKeys[T](ts: Iterable[T], toName: T => String): Map[T, String] = {
    case class Assigment(nameToT: Map[String, T], tToKey: Map[T, String])
    ts
      .foldLeft(Assigment(Map.empty, Map.empty)) { case (Assigment(nameToT, tToKey), t) =>
        val key = uniqueName(toName(t), n => !nameToT.contains(n) || nameToT.get(n).contains(t))

        Assigment(
          nameToT + (key -> t),
          tToKey + (t -> key)
        )
      }
      .tToKey
  }
}
