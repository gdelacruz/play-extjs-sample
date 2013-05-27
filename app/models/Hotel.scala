package models;

import anorm._
import anorm.SqlParser._
import play.api.db._
import play.api.Play.current

case class Hotel(name: String, website: String, phoneNumber: String, city: String, country: String, rooms: Int, beds: Int, stars: Int)

object Hotel {
  val hotel = {
    get[String]("name") ~
      get[String]("website") ~
      get[String]("phoneNumber") ~
      get[String]("city") ~
      get[String]("country") ~
      get[Int]("rooms") ~
      get[Int]("beds") ~
      get[Int]("stars") map {
        case name ~ website ~ phoneNumber ~ city ~ country ~ rooms ~ beds ~ stars => Hotel(name, website, phoneNumber, city, country, rooms, beds, stars)
      }
  }

  def toParams(h: Hotel): Seq[(Any, ParameterValue[_])] = Seq(
    "name" -> h.name,
    "website" -> h.website,
    "phoneNumber" -> h.phoneNumber,
    "city" -> h.city,
    "country" -> h.country,
    "rooms" -> h.rooms,
    "beds" -> h.beds,
    "stars" -> h.stars)

  def all(): List[Hotel] = DB.withConnection { implicit c =>
    SQL("select * from Hotel").as(hotel *)
  }

  def create(hotel: Hotel) {
    DB.withConnection { implicit c =>
      SQL("insert into Hotel " + toParams(h)).on(h).execute.executeUpdate()
    }
  }

  def delete(name: String) {
    DB.withConnection { implicit c =>
      SQL("delete from Hotel where name = {name}").on(
        'name -> name).executeUpdate()
    }

  }
}