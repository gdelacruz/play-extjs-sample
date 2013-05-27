package controllers;

import models.Hotel
import play.mvc.Controller
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonParser
import dto.HotelsDTO
import dto.ResponseDTO;
import play.api.mvc.Action

class Hotels extends Controller {
	
	def getHotels: List[Hotel] = Action{
		val gson = new Gson();
		HotelsDTO dto = gson.fromJson(params.all().get("body")[0], HotelsDTO.class);
		
		return dto!=null ? dto.data : null;
	}
	
	def list(limit: Int, start: Int) = Action {
		val hotels = Hotel.all();
		renderJSON(new ResponseDTO(true, "", hotels));
	}

	def create = Action{
		val hotels = getHotels
		
		if (hotels != null) {
		  hotels.foreach(h => Hotel.create(h))
			
		renderJSON(new ResponseDTO(true, "The hotel(s) was create successfully", hotels));
		}
		
	}

	def update = Action {
		val hotels = getHotels
		
		if (hotels != null) {
			hotels.foreach(h=> Hotel.update(h))

			renderJSON(new ResponseDTO(true, "The hotel(s) was updated successfully", hotels));
		}
	}

	def delete = Action {
		val parser = new JsonParser();
		val data = parser.parse(params.all().get("body")[0]);
		val ids = data.getAsJsonObject().get("data").getAsJsonArray();

		if(data!= null){
		    ids.foreach(h=> Hotel.delete(h))
			renderJSON(new ResponseDTO(true, "The hotel was deleted successfully", null));
		}
	}
}
