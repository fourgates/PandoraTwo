package hello.music.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hello.music.model.Song;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/music")
public class MusicController {
	
	@RequestMapping("/pandora")
    public @ResponseBody List<Song> getPandoraSongs(){
    	List<Song> songs = new ArrayList<Song>();
    	Map<String, String> titleMap = new HashMap<String, String>();
    	int startIndex = 0;
    	try {
			while(populatePandora(songs, startIndex++, titleMap));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println(startIndex);
    	return songs;
    }
    public boolean populatePandora(List<Song> songs, int startIndex, Map<String, String> titleMap) throws IOException{
    	Document doc = null;
    	doc = Jsoup.connect("http://www.pandora.com/content/station_track_thumbs?stationId=507311396773831111&page=true&posFeedbackStartIndex=" + startIndex
				+ "&posSortAsc=false&posSortBy=date&cachebuster%3A=Sun%20Jan%2003%202016%2016:00:01%20GMT-0500%20(EST").get();
		if (doc == null) {
			return false;
		}
		
		Elements links = null;
		links = doc.select("a[href]");
		if (links == null || links.size() ==0) {
			return false;
		}
		int count = 1;
		String title = null;
		String artist = null;

		for(int i=0; i<links.size(); i++){
			//System.out.println(titleMap.size());
			Element element =  links.get(i);
			String text = element.text();
			if (count++ % 2 != 0) {
				title = text;
			}
			else{ 
				artist = element.text();
				// System.out.println(titleMap.get(title));
				if (!titleMap.containsKey(title)) {
					artist = text;
					//System.out.println("Artist: " + artist + " Title: "+ title);
					titleMap.put(new String(title), new String(artist));
					//System.out.println(titleMap.get(title));
					Song song = new Song(artist, title);
					songs.add(song);
				}
			}
		}
		return true;
    }
}
